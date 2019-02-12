package tool.devp.demochat.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import tool.devp.demochat.common.Optional
import tool.devp.demochat.data.model.UserModel

interface UserRepository {
    fun createUser(model: UserModel): Observable<Completable>

    fun getUser(id: String): Observable<Optional<UserModel?>>

    /**
     * get User by [email] ans [pass]
     */
    fun login(email: String, pass: String): Observable<Optional<UserModel?>>

    fun getListUser(): Observable<List<UserModel>>
}