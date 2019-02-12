package tool.devp.demochat.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

fun Context.getColorByResource(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)

fun Context.getDrawableByResource(@DrawableRes res: Int): Drawable? = ContextCompat.getDrawable(this, res)

fun Context.getImagePathFromUri(uri: Uri): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val resolver = this.contentResolver
    val c = resolver.query(uri, projection, null, null, null) ?: return ""
    c.moveToFirst()
    val index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    var path = c.getString(index)
    if (path == null) path = ""
    c.close()
    return path
}

fun Date?.hasTheSameDayAs(comp: Date?): Boolean =
        (this?.date == comp?.date && this?.month == comp?.month && this?.year == comp?.year)

fun Date.converDateString(timeformat: String): String {
    return try {
        var dateformat = SimpleDateFormat(timeformat)
        dateformat.format(this)
    } catch (e: Exception) {
        ""
    }
}

const val DATE_FORMAT = "yyyy/MM/dd"
const val TIME_FORMAT = "HH:mm"