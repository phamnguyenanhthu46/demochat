package tool.devp.demochat.data.converter

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import tool.devp.demochat.data.entities.MessageEntity
import tool.devp.demochat.data.model.MessageModel

class MessageConverter : FirebaseConverter<MessageModel> {
    override fun deserialize(dataSnapshot: DocumentSnapshot): MessageModel = with(dataSnapshot) {
        MessageModel.newInstance(toObject(MessageEntity::class.java)!!).apply {
            id = this@with.id
        }
    }


    override fun deserializes(query: QuerySnapshot): List<MessageModel> = with(query) {
        map {
            MessageModel.newInstance(it.toObject(MessageEntity::class.java)).apply {
                id = it.id
            }
        }
    }

    override fun deserialize(query: DocumentChange): MessageModel = with(query) {
        MessageModel.newInstance(document.toObject(MessageEntity::class.java)).apply {
            id = document.id
        }
    }
}