package tool.devp.demochat.data.repository

import com.google.common.base.Optional
import io.reactivex.Observable
import tool.devp.demochat.data.model.ChatRoomModel

interface ChatRoomRepository {
    /**
     * Retrieves the chat room by an id.
     */
    fun getChatRoom(roomId: String): Observable<ChatRoomModel>

    /**
     * Retrieves room by [userId] and [shopId]
     */
    fun getChatRoom(list: List<String>): Observable<Optional<ChatRoomModel?>>

    /**
     * Retrieves rooms of Shop
     * param [shopId]
     */

    fun createChatRoom(room: ChatRoomModel): Observable<String>

}