package tool.devp.demochat.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

fun Context.getColorByResource(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)

fun Context.getDrawableByResource(@DrawableRes res: Int): Drawable? = ContextCompat.getDrawable(this, res)