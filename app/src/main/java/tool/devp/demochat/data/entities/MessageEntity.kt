package tool.devp.demochat.data.entities

import com.google.firebase.firestore.ServerTimestamp
import tool.devp.demochat.data.model.MessageModel
import java.util.*

data class MessageEntity(
        var content: String? = null,
        @ServerTimestamp
        var createdAt: Date?= null,

        var messageType: Int= 0,

        var senderID: String? = null,

        var timeTemp: Date?= null

) {
    companion object {
        fun newInstance(mes: MessageModel): MessageEntity =
                MessageEntity(
                        content = mes.content,
                        createdAt = null,
                        messageType = mes.messageType,
                        senderID = mes.senderID,
                        timeTemp = mes.timeTemp)
    }
}