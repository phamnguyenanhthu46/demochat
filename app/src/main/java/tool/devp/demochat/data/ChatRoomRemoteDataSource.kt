package tool.devp.demochat.data

import com.google.common.base.Optional
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Single
import tool.devp.demochat.data.converter.FirebaseConverter
import tool.devp.demochat.data.entities.ChatRoomEntity
import tool.devp.demochat.data.model.ChatRoomModel

class ChatRoomRemoteDataSource(database: FirebaseFirestore,
                               private val converter: FirebaseConverter<ChatRoomModel>)  {
    private val reference = database.collection(COLLECTION_CHATROOM)
     fun getChatRoom(roomId: String): Observable<ChatRoomModel> =
            Single.create<ChatRoomModel> { emitter ->
                reference.document(roomId).get()
                        .addOnSuccessListener { document ->
                            document?.let {
                                if (it.exists())
                                    emitter.onSuccess(converter.deserialize(it))
                            }
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()


     fun getChatRoom(list: List<String>): Observable<Optional<ChatRoomModel>> =
            Single.create<Optional<ChatRoomModel>> { emitter ->
                reference.whereArrayContains(CHATROOM_FIELD_USER, list[0])
                        .get()
                        .addOnSuccessListener { documents ->
                            documents?.let { querySnapshot ->
                                var room = converter.deserializes(querySnapshot).filter { it.users!!.contains(list[1]) }.firstOrNull()
                                if (room != null) {
                                    emitter.onSuccess(Optional.of(room))
                                } else {
                                    emitter.onSuccess(Optional.fromNullable(null))
                                }
                            }

                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()



     fun createChatRoom(room: ChatRoomModel): Observable<String> =
            Single.create<String> { emitter ->
                reference.add(ChatRoomEntity.newIntance(room)).addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.let {
                            it?.id.let {
                                emitter.onSuccess(it ?: "")
                            }
                        }
                    }
                }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()


    companion object {
        const val COLLECTION_CHATROOM = "Rooms"
        const val CHATROOM_FIELD_USER = "users"
        fun newInstance(firebaseDatabase: FirebaseFirestore, converter: FirebaseConverter<ChatRoomModel>): ChatRoomRemoteDataSource =
                ChatRoomRemoteDataSource(firebaseDatabase,converter)
    }
}