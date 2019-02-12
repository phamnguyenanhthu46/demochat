package tool.devp.demochat.presentation.uimodel

import tool.devp.demochat.data.model.MessageModel
import java.util.*

class MessageUiModel(
        var id: String,

        var content: String,

        var createdAt: Date? = null,

        var messageType: Int = 0,

        var isCreateByMe: Boolean = false,

        var action: String? = null,

        var timeTemp: Date?,

        var isNewDay: Boolean = true,

        var status: Int = 0
) {
    enum class STATUS(var value: Int){
        PENDING(1),
        SUCCESS(2),
    }
    companion object {
        fun newIntance(id: String, model: MessageModel): MessageUiModel =
                MessageUiModel(
                        id = model.id,
                        content = model.content,
                        createdAt = if(model.createdAt != null) model.createdAt else model.timeTemp,
                        messageType = model.messageType,
                        isCreateByMe = (id == model.senderID),
                        action = model.action,
                        timeTemp = model.timeTemp
                )
    }
}