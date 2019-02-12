package tool.devp.demochat.data.entities

import com.google.firebase.firestore.ServerTimestamp
import tool.devp.demochat.data.model.ChatRoomModel
import java.util.*

data class ChatRoomEntity(
        var users: List<String>? = null,
        @ServerTimestamp
        var createdAt: Date? = null
) {
        companion object {
            fun newIntance(model: ChatRoomModel): ChatRoomEntity = ChatRoomEntity(
                    users = model.users
            )
        }
}