package tool.devp.demochat.presentation.fragments

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.obtainViewModel
import tool.devp.demochat.presentation.activities.SignInSignUpActivity
import tool.devp.demochat.presentation.viewmodels.LoginViewModel

class LoginFragment: BaseFragment<LoginViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_login

    override fun onCreateViewModel(): LoginViewModel = obtainViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
    }

    private fun setActions(){
        tvRegister.setOnClickListener {
            (activity as SignInSignUpActivity).register()
        }
    }
    companion object {
        fun newIntance(): LoginFragment = LoginFragment()
    }
}