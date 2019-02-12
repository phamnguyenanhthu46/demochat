package tool.devp.demochat.presentation.fragments

import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.initViewModel
import tool.devp.demochat.presentation.viewmodels.FriendViewModel

class FriendFragment: BaseFragment<FriendViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_friend_list

    override fun onCreateViewModel(): FriendViewModel = initViewModel()

    companion object {
        const val TAG = "MyPageFragment"

        private var INSTANCE: FriendFragment? = null

        fun getInstance(): FriendFragment {
            if (INSTANCE == null)
                INSTANCE = FriendFragment()
            return INSTANCE!!
        }
    }
}