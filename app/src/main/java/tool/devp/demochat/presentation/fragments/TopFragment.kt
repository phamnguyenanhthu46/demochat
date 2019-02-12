package tool.devp.demochat.presentation.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_top.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.initViewModel
import tool.devp.demochat.presentation.activities.ChatRoomActivity
import tool.devp.demochat.presentation.adapters.ListUserAdapter
import tool.devp.demochat.presentation.viewmodels.TopViewModel

class TopFragment : BaseFragment<TopViewModel>() {
    private var userAdapter: ListUserAdapter? = null

    override fun layoutRes(): Int = R.layout.fragment_top

    override fun onCreateViewModel(): TopViewModel = initViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_top, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListUser()
        viewModel.apply {
            users.observe(this@TopFragment, Observer {
                it?.let {users ->
                    userAdapter?.addData(users)
                }
            })
            onChatClick.observe(this@TopFragment, Observer {
                it?.let {
                    ChatRoomActivity.start(this@TopFragment.context!!,it)
                }
            })
            start()
        }
    }

    private fun initListUser(){
        userAdapter = ListUserAdapter(context!!, viewModel, ArrayList()).apply {
            rcvUser.adapter = this
        }
    }

    companion object {
        const val TAG = "TopFragment"

        private var INSTANCE: TopFragment? = null

        fun getInstance(): TopFragment {
            if (INSTANCE == null)
                INSTANCE = TopFragment()
            return INSTANCE!!
        }
    }

}