package tool.devp.demochat.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import tool.devp.demochat.data.model.UserModel

interface UserRepository {
    fun createUser(model: UserModel): Completable

    fun getUser(id: String): Observable<UserModel>

}