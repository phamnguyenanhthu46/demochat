package tool.devp.demochat.data.model

import tool.devp.demochat.data.entities.ChatRoomEntity
import java.io.Serializable
import java.util.*

data class ChatRoomModel(
        var id: String,

        var users: List<String>? = null,

        var createdAt: Date? = null

) : Serializable {

    companion object {
        fun newInstance(entity: ChatRoomEntity): ChatRoomModel =
                ChatRoomModel(
                        id = "",
                        users = entity.users,
                        createdAt = entity.createdAt
                )
    }
}