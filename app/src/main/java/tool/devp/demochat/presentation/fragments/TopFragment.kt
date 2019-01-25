package tool.devp.demochat.presentation.fragments

import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.obtainViewModel
import tool.devp.demochat.presentation.viewmodels.TopViewModel

class TopFragment : BaseFragment<TopViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_top

    override fun onCreateViewModel(): TopViewModel = obtainViewModel()

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