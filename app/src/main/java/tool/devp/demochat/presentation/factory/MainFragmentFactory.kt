package tool.devp.demochat.presentation.factory

import android.support.v4.app.Fragment
import tool.devp.demochat.presentation.fragments.ChatListFragment
import tool.devp.demochat.presentation.fragments.FriendFragment
import tool.devp.demochat.presentation.fragments.MyPageFragment
import tool.devp.demochat.presentation.fragments.TopFragment

object MainFragmentFactory {

    private val instances = mutableMapOf<Class<*>, Fragment>()

    operator fun <T : Fragment> get(fragmentClass: Class<T>): Fragment =
            instances[fragmentClass] ?: fragmentClass.run {
                when {
                    isAssignableFrom(TopFragment::class.java) -> TopFragment.getInstance()
                    isAssignableFrom(FriendFragment::class.java) -> FriendFragment.getInstance()
                    isAssignableFrom(ChatListFragment::class.java) -> ChatListFragment.getInstance()
                    isAssignableFrom(MyPageFragment::class.java) -> MyPageFragment.getInstance()
                    else -> throw IllegalArgumentException("unknown class : ${fragmentClass.canonicalName}")
                }
            }.apply {
                instances[fragmentClass] = this
            }
}