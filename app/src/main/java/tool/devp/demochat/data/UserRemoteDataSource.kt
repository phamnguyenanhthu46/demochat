package tool.devp.demochat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import tool.devp.demochat.common.Optional
import tool.devp.demochat.data.converter.FirebaseConverter
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.data.repository.UserRepository

class UserRemoteDataSource(database: FirebaseFirestore,
                           private val converter: FirebaseConverter<UserModel>) : UserRepository {

    private val reference = database.collection(COLLECTION_USER)
    override fun createUser(model: UserModel): Observable<Completable> =
            Single.create<Completable> { emitter ->
                reference.document(model.email!!)
                        .set(UserEntity.newInstance(model))
                        .addOnSuccessListener {
                            emitter.onSuccess(Completable.complete())
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()


    override fun getUser(id: String): Observable<Optional<UserModel?>> =
            Single.create<Optional<UserModel?>> { emitter ->
                reference.document(id)
                        .get()
                        .addOnSuccessListener { document ->
                            document?.let {
                                if (it.exists()) {
                                    emitter.onSuccess(Optional.of(converter.deserialize(it)))
                                } else {
                                    emitter.onSuccess(Optional.of(null))
                                }
                            }
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()

    override fun login(email: String, pass: String): Observable<Optional<UserModel?>> =
            Single.create<Optional<UserModel?>> { emitter ->
                reference.whereEqualTo(ARRT_EMAIL, email)
                        .whereEqualTo(ARRT_PASS, pass)
                        .get()
                        .addOnSuccessListener {
                            if (!it.isEmpty) {
                                emitter.onSuccess(Optional.of(converter.deserializes(it).first()))
                            }
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }

            }.toObservable()

    override fun getListUser(): Observable<List<UserModel>> =
            Single.create<List<UserModel>> { emitter ->
                reference.orderBy(ARRT_CREATE_AT, Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener {
                            emitter.onSuccess(converter.deserializes(it))
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
            }.toObservable()


    companion object {
        const val COLLECTION_USER = "Users"
        const val ARRT_EMAIL = "email"
        const val ARRT_PASS = "pass"
        const val ARRT_CREATE_AT = "createAt"
        private var INSTANCE: UserRemoteDataSource? = null

        fun getInstance(firebaseDatabase: FirebaseFirestore, converter: FirebaseConverter<UserModel>): UserRemoteDataSource =
                INSTANCE ?: UserRemoteDataSource(firebaseDatabase, converter).also {
                    INSTANCE = it
                }
    }
}