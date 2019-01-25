package tool.devp.demochat.presentation.fragments

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.extension.addOnTextChangedListener
import tool.devp.demochat.extension.obtainViewModel
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel

class RegisterFragment: BaseFragment<RegisterViewModel>(){
    override fun layoutRes(): Int = R.layout.fragment_register
    override fun onCreateViewModel(): RegisterViewModel = obtainViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setActions()
    }

    private fun initView(){
        title.text = resources.getString(R.string.tvRegister)
    }

    private fun setActions(){
        edUserName.apply {
            addOnTextChangedListener{s, _, _, _ ->
                viewModel.username.value = s.toString()
            }
        }
        edPass.apply {
            addOnTextChangedListener{s, _, _, _ ->
                viewModel.password.value = s.toString()
            }
        }
        edConfirmPass.apply {
            addOnTextChangedListener{s, _, _, _ ->
                viewModel.repassword.value = s.toString()
            }
        }
        edMail.apply {
            addOnTextChangedListener{s, _, _, _ ->
                viewModel.emailAddress.value = s.toString()
            }
        }
        edPhoneNumber.apply {
            addOnTextChangedListener{s, _, _, _ ->
                viewModel.phoneNumber.value = s.toString()
            }
        }
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
    companion object {
        fun newIntance(): RegisterFragment = RegisterFragment()
    }
}