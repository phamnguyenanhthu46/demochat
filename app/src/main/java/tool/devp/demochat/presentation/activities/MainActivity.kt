package tool.devp.demochat.presentation.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_nav_bottom.*
import tool.devp.demochat.R
import tool.devp.demochat.presentation.adapters.MainAdapter
import tool.devp.demochat.presentation.factory.MainFragmentFactory
import tool.devp.demochat.presentation.fragments.ChatListFragment
import tool.devp.demochat.presentation.fragments.FriendFragment
import tool.devp.demochat.presentation.fragments.MyPageFragment
import tool.devp.demochat.presentation.fragments.TopFragment

class MainActivity : AppCompatActivity() {
    private var mainAdapter: MainAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupMainPage()
        onSelectTab()
    }

    private fun setupMainPage() {
        val fragments = ArrayList<Fragment>()
        fragments.add(MainFragmentFactory[TopFragment::class.java])
        fragments.add(MainFragmentFactory[FriendFragment::class.java])
        fragments.add(MainFragmentFactory[ChatListFragment::class.java])
        fragments.add(MainFragmentFactory[MyPageFragment::class.java])
        vpMain.run {
            mainAdapter = MainAdapter(fragments, supportFragmentManager)
            offscreenPageLimit = 4
            smoothScroll = false
            adapter = mainAdapter
        }
    }

    private fun onSelectTab() {
        rgNavMain.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rbTop -> {
                    vpMain.currentItem = 0
                }
                R.id.rbFriend -> {
                    vpMain.currentItem = 1
                }
                R.id.rbChatList -> {
                    vpMain.currentItem = 2
                }
                R.id.rbMyPage -> {
                    vpMain.currentItem = 3
                }
            }
        }
    }
}
