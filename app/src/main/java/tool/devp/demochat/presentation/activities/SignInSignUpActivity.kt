package tool.devp.demochat.presentation.activities

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_signin_signup.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseActivity
import tool.devp.demochat.extension.obtainViewModel
import tool.devp.demochat.presentation.fragments.LoginFragment
import tool.devp.demochat.presentation.fragments.RegisterFragment
import tool.devp.demochat.presentation.viewmodels.SignInSignUpViewModel

class SignInSignUpActivity : BaseActivity<SignInSignUpViewModel>() {


    override val snackBarRootView: View
        get() = root

    override fun onCreateViewModel(): SignInSignUpViewModel = obtainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)
        initView()
    }

    private fun initView(){
        replaceFragmentInsideFragment(R.id.container, LoginFragment.newIntance(),false)
    }

    fun register(){
        replaceFragmentInsideFragment(R.id.container, RegisterFragment.newIntance(),true)
    }
}
