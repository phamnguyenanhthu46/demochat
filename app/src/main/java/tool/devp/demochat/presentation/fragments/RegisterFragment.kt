package tool.devp.demochat.presentation.fragments

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseFragment
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.extension.*
import tool.devp.demochat.presentation.activities.MainActivity
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel

class RegisterFragment : BaseFragment<RegisterViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_register
    override fun onCreateViewModel(): RegisterViewModel = initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            success.observe(this@RegisterFragment, Observer {
                it?.let { register ->
                    if (register) {
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()
                    }
                }
            })
            loading.observe(this@RegisterFragment, Observer {
                it?.let { process ->
                    if (process) {
                        viewLoading.visible()
                    } else {
                        viewLoading.gone()
                    }
                }
            })
        }
        initView()
        setActions()
    }

    private fun initView() {
        tvTitle.text = resources.getString(R.string.tvRegister)
    }

    private fun setActions() {
        edUserName.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.username.value = s.toString()
            }
        }
        edPass.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.password.value = s.toString()
            }
        }
        edConfirmPass.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.repassword.value = s.toString()
            }
        }
        edMail.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.emailAddress.value = s.toString()
            }
        }
        edPhoneNumber.apply {
            addOnTextChangedListener { s, _, _, _ ->
                viewModel.phoneNumber.value = s.toString()
            }
        }
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        rgGender.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rdBoy -> {
                    viewModel.gender.value = UserEntity.GENDER.MALE.value
                }
                R.id.rbGirl -> {
                    viewModel.gender.value = UserEntity.GENDER.FEMALE.value
                }
            }
        }
        btnRegister.setOnClickListener {
            viewModel.register()
        }
    }

    companion object {
        fun newIntance(): RegisterFragment = RegisterFragment()
    }
}