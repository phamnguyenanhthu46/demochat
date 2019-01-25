package tool.devp.demochat.presentation.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainAdapter(private var fragments: List<Fragment>, frm: FragmentManager) : FragmentPagerAdapter(frm) {
    override fun getItem(index: Int): Fragment =
            fragments[index]

    override fun getCount(): Int = fragments.size

}