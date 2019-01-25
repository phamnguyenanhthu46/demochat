package tool.devp.demochat.presentation.fragments

import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.obtainViewModel
import tool.devp.demochat.presentation.viewmodels.ChatListViewModel

class ChatListFragment: BaseFragment<ChatListViewModel>(){

    override fun layoutRes(): Int  = R.layout.fragment_chat_list

    override fun onCreateViewModel(): ChatListViewModel = obtainViewModel()

    companion object {
        const val TAG = "MyPageFragment"

        private var INSTANCE: ChatListFragment? = null

        fun getInstance(): ChatListFragment {
            if (INSTANCE == null)
                INSTANCE = ChatListFragment()
            return INSTANCE!!
        }
    }
}