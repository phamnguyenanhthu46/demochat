package tool.devp.demochat.presentation.fragments

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.*
import tool.devp.demochat.presentation.activities.MainActivity
import tool.devp.demochat.presentation.activities.SignInSignUpActivity
import tool.devp.demochat.presentation.viewmodels.LoginViewModel

class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_login

    override fun onCreateViewModel(): LoginViewModel = initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
        viewModel.apply {
            success.observe(this@LoginFragment, Observer {
                it?.let { suc ->
                    if (suc) {
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()
                    }
                }
            })
            loading.observe(this@LoginFragment, Observer {
                it?.let { process ->
                    if (process) {
                        viewLoading.visible()
                    } else {
                        viewLoading.gone()
                    }
                }
            })
        }
    }

    private fun setActions() {
        tvRegister.setOnClickListener {
            (activity as SignInSignUpActivity).register()
        }
        btnLogin.setOnClickListener {
            viewModel.login()
        }
        edMail.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.email.value = s.toString()
            }
        }
        edPass.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.pass.value = s.toString()
            }
        }
    }

    companion object {
        fun newIntance(): LoginFragment = LoginFragment()
    }
}