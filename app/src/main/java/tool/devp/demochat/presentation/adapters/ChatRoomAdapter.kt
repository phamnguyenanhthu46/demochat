package tool.devp.demochat.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tool.devp.demochat.R
import tool.devp.demochat.data.model.MessageModel
import tool.devp.demochat.presentation.viewmodels.ChatRoomViewModel

class ChatRoomAdapter(var viewModel: ChatRoomViewModel, var messages: ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = LayoutInflater.from(parent.context).run {
        when (viewType) {
            VIEW_TYPE_TEXT_SENT -> TextSentHolder(inflate(R.layout.item_chat_text_me, parent, false))
            VIEW_TYPE_TEXT_RECCIEVE -> TextRecieveHolder(inflate(R.layout.item_chat_text_other, parent, false))
            VIEW_TYPE_IMAGE_SENT -> ImageSentHolder(inflate(R.layout.item_chat_image_me, parent, false))
            VIEW_TYPE_IMAGE_RECCIEVE -> ImageRecieveHolder(inflate(R.layout.item_chat_image_other, parent, false))
            else -> throw IllegalStateException("unknown type: $viewType")
        }
    }


    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        when (holder) {
            is TextSentHolder -> {}
            is TextRecieveHolder -> {}
            is ImageSentHolder -> {}
            is ImageRecieveHolder -> {}
        }
    }

    abstract class TextHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract class ImageHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class TextSentHolder(view: View) : TextHolder(view)

    inner class TextRecieveHolder(view: View) : TextHolder(view)

    inner class ImageSentHolder(view: View) : ImageHolder(view)

    inner class ImageRecieveHolder(view: View) : ImageHolder(view)

    companion object {
        const val VIEW_TYPE_TEXT_SENT = 0
        const val VIEW_TYPE_TEXT_RECCIEVE = 1
        const val VIEW_TYPE_IMAGE_SENT = 2
        const val VIEW_TYPE_IMAGE_RECCIEVE = 3
    }
}