package tool.devp.demochat.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import tool.devp.demochat.data.MessageRemoteDataSource
import tool.devp.demochat.data.model.MessageModel

interface MessageRepository {
    fun postMessage(roomId: String, model: MessageModel): Completable

    fun messages(roomId: String, firstItem: String?): Observable<List<MessageModel>>

    fun deleteMessage(roomId: String,id: String): Completable

    fun subscribeMessage(roomId: String, firstItem: String?, listener: MessageRemoteDataSource.MessageListener)

    fun getLastRead(roomId: String,last: String): Observable<String>

    fun setLastRead(roomId: String,last: String): Completable
}