package tool.devp.demochat.data.model

import tool.devp.demochat.data.entities.MessageEntity
import java.util.*

data class MessageModel(var id: String,

                        var content: String,

                        var createdAt: Date? = null,

                        var messageType: Int = 0,

                        var senderID: String? = null,

                        var senderName: String? = null,

                        var action: String? = null,

                        var timeTemp: Date?

) {
    enum class MESSAGE(var type: Int) {
        TEXT(1),
        IMAGE(2),
    }

    companion object {
        fun newInstance(mes: MessageEntity): MessageModel =
                MessageModel(id = "",
                        content = mes.content!!,
                        createdAt = mes.createdAt,
                        messageType = mes.messageType,
                        senderID = mes.senderID,
                        senderName = mes.senderName,
                        action = "",
                        timeTemp = mes.timeTemp)
    }

}