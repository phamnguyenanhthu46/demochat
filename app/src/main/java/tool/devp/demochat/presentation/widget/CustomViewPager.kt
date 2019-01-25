package tool.devp.demochat.presentation.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager  @JvmOverloads constructor(mContext: Context,
                                                 attributeSet: AttributeSet? = null)
    : ViewPager(mContext, attributeSet) {
    var enableSwipe: Boolean = false
    var smoothScroll: Boolean = true

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!enableSwipe) return false

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (!enableSwipe) return false

        return super.onTouchEvent(ev)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, smoothScroll)
    }
}