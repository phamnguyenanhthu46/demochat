package tool.devp.demochat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import tool.devp.demochat.data.converter.FirebaseConverter
import tool.devp.demochat.data.entities.MessageEntity
import tool.devp.demochat.data.model.MessageModel
import tool.devp.demochat.data.repository.MessageRepository

class MessageRemoteDataSource(database: FirebaseFirestore,
                              private val converter: FirebaseConverter<MessageModel>) : MessageRepository {
    private val reference = database.collection(COLLECTION_MESSAGE)
    private var messageRegistration: ListenerRegistration? = null

    override fun postMessage(roomId: String, model: MessageModel): Completable =
            Completable.create { emitter ->
                reference.document(roomId).collection(COLLECTION_MESSAGE_OF_ROOM).document()
                        .set(MessageEntity.newInstance(model))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }


    override fun messages(roomId: String, firstItem: String?): Observable<List<MessageModel>> =
            Single.create<List<MessageModel>> { emiter ->
                var query = reference.document(roomId).collection(COLLECTION_MESSAGE_OF_ROOM)
                        .orderBy(ATTR_CREATE_AT, Query.Direction.DESCENDING)
                if (firstItem != null) {
                    reference.document(roomId).collection(COLLECTION_MESSAGE_OF_ROOM).document(firstItem)
                            .get()
                            .addOnSuccessListener {
                                query.startAfter(it)
                                        .limit(LIMIT.toLong())
                                        .get()
                                        .addOnSuccessListener { querySanpshot ->
                                            querySanpshot?.let {
                                                emiter.onSuccess(converter.deserializes(it))
                                            }
                                        }
                                        .addOnFailureListener {
                                            emiter.onError(it)
                                        }
                            }
                } else {
                    query.limit(LIMIT.toLong())
                            .get()
                            .addOnSuccessListener { querySanpshot ->
                                querySanpshot?.let {
                                    emiter.onSuccess(converter.deserializes(it))
                                }

                            }
                            .addOnFailureListener {
                                emiter.onError(it)
                            }
                }

            }.toObservable()


    override fun deleteMessage(roomId: String, id: String): Completable =
            Completable.create { emitter ->
                reference.document(roomId).collection(COLLECTION_MESSAGE_OF_ROOM).document(id).delete()
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }

    override fun subscribeMessage(roomId: String, firstItem: String?, listener: MessageListener) {
        messageRegistration?.remove()
        var collectionRef = reference.document(roomId).collection(COLLECTION_MESSAGE_OF_ROOM)
        if (firstItem == null) {
            messageRegistration = collectionRef.orderBy(ATTR_CREATE_AT, Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, e ->
                        snapshot?.let {
                            if ( snapshot.metadata.hasPendingWrites()){
                                listener.onMessageLocal( it.documentChanges.map { converter.deserialize(it) })
                            }else {
                                listener.onMessageServer( it.documentChanges.map { converter.deserialize(it) })
                            }
                        }
                    }
        } else {
            collectionRef.document(firstItem)
                    .get()
                    .addOnSuccessListener {
                        messageRegistration = collectionRef.orderBy(ATTR_CREATE_AT, Query.Direction.ASCENDING)
                                .startAfter(it)
                                .addSnapshotListener { snapshot, e ->
                                    snapshot?.let {
                                        if ( snapshot.metadata.hasPendingWrites()){
                                            listener.onMessageLocal( it.documentChanges.map { converter.deserialize(it) })
                                        }else {
                                            listener.onMessageServer( it.documentChanges.map { converter.deserialize(it) })
                                        }
                                    }
                                }
                    }
        }

    }

    override fun getLastRead(roomId: String, last: String): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastRead(roomId: String, last: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface MessageListener {
        fun onMessageLocal(items: List<MessageModel>)

        fun onMessageServer(items: List<MessageModel>)
    }

    companion object {
        private var INSTANCE: MessageRemoteDataSource? = null
        fun getInstance(firebaseDatabase: FirebaseFirestore, converter: FirebaseConverter<MessageModel>): MessageRemoteDataSource =
                INSTANCE ?: MessageRemoteDataSource(firebaseDatabase, converter).also {
                    INSTANCE = it
                }

        const val TAG = "CIRemoteDataSource"
        const val COLLECTION_MESSAGE = "MessagesOfRooms"
        const val COLLECTION_MESSAGE_OF_ROOM = "Messages"
        const val ATTR_CREATE_AT = "createdAt"
        const val LIMIT = 20
    }
}