package tool.devp.demochat.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(context: Context, avatar: Any?) {
    Glide.with(context).load(avatar).apply(RequestOptions().centerCrop()).into(this)
}

fun ImageView.loadImageChat(context: Context, image: Any) {
    Glide.with(context)
            .load(image)
            .into(this)
}