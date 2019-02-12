package tool.devp.demochat.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import tool.devp.demochat.R
import tool.devp.demochat.data.model.MessageModel
import tool.devp.demochat.extension.*
import tool.devp.demochat.presentation.uimodel.MessageUiModel
import tool.devp.demochat.presentation.viewmodels.ChatRoomViewModel

class ChatRoomAdapter(var viewModel: ChatRoomViewModel, var messages: ArrayList<MessageUiModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = LayoutInflater.from(parent.context).run {
        when (viewType) {
            VIEW_TYPE_TEXT_SENT -> TextSentHolder(inflate(R.layout.item_chat_text_me, parent, false))
            VIEW_TYPE_TEXT_RECCIEVE -> TextRecieveHolder(inflate(R.layout.item_chat_text_other, parent, false))
            VIEW_TYPE_IMAGE_SENT -> ImageSentHolder(inflate(R.layout.item_chat_image_me, parent, false))
            else -> ImageRecieveHolder(inflate(R.layout.item_chat_image_other, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        var mess = messages[position]
        if (position > 0) {
            val previousMsg = messages[position - 1]
            mess.isNewDay = !previousMsg.createdAt.hasTheSameDayAs(mess.createdAt)
        }
        return when (mess.messageType) {
            MessageModel.TYPE.TEXT.value -> {
                if (mess.isCreateByMe) {
                    VIEW_TYPE_TEXT_SENT
                } else {
                    VIEW_TYPE_TEXT_RECCIEVE
                }
            }
            MessageModel.TYPE.IMAGE.value -> {
                if (mess.isCreateByMe) {
                    VIEW_TYPE_IMAGE_SENT
                } else {
                    VIEW_TYPE_IMAGE_RECCIEVE
                }
            }
            else -> throw IllegalStateException("unknown type")
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextSentHolder -> {
                holder.binData(messages[position])
            }
            is TextRecieveHolder -> {
                holder.binData(messages[position])
            }
            is ImageSentHolder -> {
                holder.binData(messages[position])
            }
            is ImageRecieveHolder -> {
                holder.binData(messages[position])
            }
        }
    }

    fun addOrUpdate(mess: MessageUiModel) {
        var index = messages.indexOfFirst { it.id == mess.id }
        if (index != -1) {
            messages[index] = mess
            notifyItemChanged(index)
        } else {
            messages.add(mess)
            notifyItemInserted(messages.count() - 1)
        }
    }

    abstract inner class TextHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent = itemView.findViewById<TextView>(R.id.speech)
        val time = itemView.findViewById<TextView>(R.id.createdAt)

        abstract fun binData(mess: MessageUiModel)
    }

    abstract inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgContent = itemView.findViewById<ImageView>(R.id.content)
        var time = itemView.findViewById<TextView>(R.id.createdAt)

        abstract fun binData(mess: MessageUiModel)
    }

    inner class TextSentHolder(view: View) : TextHolder(view) {
        private val imgSuccess = itemView.findViewById<ImageView>(R.id.imgSuccess)
        override fun binData(mess: MessageUiModel) {
            tvContent.text = mess.content
            time.text = mess.createdAt?.converDateString(TIME_FORMAT)
            if (mess.status == MessageUiModel.STATUS.PENDING.value) {
                imgSuccess.invisible()
            } else {
                imgSuccess.visible()
            }
        }
    }

    inner class TextRecieveHolder(view: View) : TextHolder(view) {
        override fun binData(mess: MessageUiModel) {
            tvContent.text = mess.content
            time.text = mess.createdAt?.converDateString(TIME_FORMAT)
        }
    }

    inner class ImageSentHolder(view: View) : ImageHolder(view) {
        val imgSuccess = itemView.findViewById<ImageView>(R.id.imgSuccess)
        override fun binData(mess: MessageUiModel) {
            imgContent.loadImage(itemView.context, mess.content)
            time.text = mess.createdAt?.converDateString(TIME_FORMAT)
            if (mess.status == MessageUiModel.STATUS.PENDING.value) {
                imgSuccess.invisible()
            } else {
                imgSuccess.visible()
            }
        }
    }

    inner class ImageRecieveHolder(view: View) : ImageHolder(view) {
        override fun binData(mess: MessageUiModel) {
            imgContent.loadImage(itemView.context, mess.content)
            time.text = mess.createdAt?.converDateString(TIME_FORMAT)
        }
    }

    companion object {
        const val VIEW_TYPE_TEXT_SENT = 0
        const val VIEW_TYPE_TEXT_RECCIEVE = 1
        const val VIEW_TYPE_IMAGE_SENT = 2
        const val VIEW_TYPE_IMAGE_RECCIEVE = 3
    }
}