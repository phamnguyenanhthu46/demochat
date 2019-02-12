package tool.devp.demochat.presentation.fragments

import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.initViewModel
import tool.devp.demochat.presentation.viewmodels.MyPageViewModel

class MyPageFragment: BaseFragment<MyPageViewModel>(){
    override fun layoutRes(): Int = R.layout.fragment_my_page

    override fun onCreateViewModel(): MyPageViewModel = initViewModel()

    companion object {
        const val TAG = "MyPageFragment"

        private var INSTANCE: MyPageFragment? = null

        fun getInstance(): MyPageFragment {
            if (INSTANCE == null)
                INSTANCE = MyPageFragment()
            return INSTANCE!!
        }
    }
}