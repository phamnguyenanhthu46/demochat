package tool.devp.demochat.data

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import tool.devp.demochat.data.converter.FirebaseConverter
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.data.repository.UserRepository

class UserRemoteDataSource(database: FirebaseFirestore,
                           private val converter: FirebaseConverter<UserModel>) : UserRepository {

    private val reference = database.collection(COLLECTION_USER)
    override fun createUser(model: UserModel): Completable =
            Completable.create{emitter ->
                reference.document()
                        .set(UserEntity.newInstance(model))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }


    override fun getUser(id: String): Observable<UserModel> =
            Single.create<UserModel>{emitter ->
                reference.document(id)
                        .get()
                        .addOnSuccessListener { document ->
                            document?.let {
                                emitter.onSuccess(converter.deserialize(it))
                            }
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()

    companion object {
        const val COLLECTION_USER = "Users"

        private var INSTANCE: UserRemoteDataSource? = null

        fun getInstance(firebaseDatabase: FirebaseFirestore, converter: FirebaseConverter<UserModel>): UserRemoteDataSource =
                INSTANCE ?: UserRemoteDataSource(firebaseDatabase, converter).also {
                    INSTANCE = it
                }
    }
}