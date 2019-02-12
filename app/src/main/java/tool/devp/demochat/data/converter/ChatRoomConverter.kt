package tool.devp.demochat.data.converter

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import tool.devp.demochat.data.entities.ChatRoomEntity
import tool.devp.demochat.data.model.ChatRoomModel

class ChatRoomConverter : FirebaseConverter<ChatRoomModel> {
    override fun deserialize(dataSnapshot: DocumentSnapshot): ChatRoomModel = with(dataSnapshot) {
        ChatRoomModel.newInstance(toObject(ChatRoomEntity::class.java)!!).apply {
            id = this@with.id
        }
    }

    override fun deserializes(query: QuerySnapshot): List<ChatRoomModel> = with(query) {
        map {
            ChatRoomModel.newInstance(it.toObject(ChatRoomEntity::class.java)).apply {
                id = it.id
            }
        }
    }

    override fun deserialize(query: DocumentChange): ChatRoomModel = with(query) {
        ChatRoomModel.newInstance(document.toObject(ChatRoomEntity::class.java)).apply {
            id = document.id
        }
    }
}