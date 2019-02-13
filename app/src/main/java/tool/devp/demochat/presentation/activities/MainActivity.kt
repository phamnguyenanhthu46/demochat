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
        setContentView(R.layout.act_main)
    }

}
