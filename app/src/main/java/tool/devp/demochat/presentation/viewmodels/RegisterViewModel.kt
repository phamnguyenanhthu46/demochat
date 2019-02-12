package tool.devp.demochat.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseViewModel
import tool.devp.demochat.common.DemoChatApp
import tool.devp.demochat.common.Optional
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.data.repository.UserRepository
import tool.devp.demochat.extension.isEmail
import tool.devp.demochat.presentation.custom.SingleLiveEvent
import tool.devp.demochat.presentation.schedulers.SchedulerProvider
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel.SignException.Companion.INVALID_CONFIRM_PASSWORD
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel.SignException.Companion.INVALID_EMAIL
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel.SignException.Companion.INVALID_PASSWORD
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel.SignException.Companion.INVALID_USER_NAME
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel.SignException.Companion.MAIL_EXIT
import java.util.*

@Suppress("UNUSED_EXPRESSION")
class RegisterViewModel(application: Application,
                        var userRepository: UserRepository) : BaseViewModel(application) {
    val isAttemptable = SingleLiveEvent<Boolean>()
    val emailAddress = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val repassword = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val gender = MutableLiveData<Int>()
    val loading = MutableLiveData<Boolean>()

    val success = MutableLiveData<Boolean>()

    private val subscriptions = CompositeDisposable()

    private fun updateAttempable() {
        isAttemptable.value = !emailAddress.value.isNullOrBlank()
                && !username.value.isNullOrBlank()
                && !password.value.isNullOrBlank()
                && !repassword.value.isNullOrBlank()
                && !lastName.value.isNullOrBlank()
                && gender.value != null
    }

    private fun verify() {
        if (username.value?.contains(" ") != false) {
            throw SignException(INVALID_USER_NAME, context.resources.getString(R.string.error_user_name_invalid))
        }

        if (password.value?.contains(" ") != false
                || password.value?.length ?: 0 < 8) {
            throw SignException(INVALID_PASSWORD, context.resources.getString(R.string.error_password_invalid))
        }
        if (repassword.value?.contains(" ") != false
                || repassword.value?.length ?: 0 < 8) {
            throw throw SignException(INVALID_PASSWORD, context.resources.getString(R.string.error_password_invalid))
        }

        if (emailAddress.value?.isEmail != true) {
            throw SignException(INVALID_EMAIL, context.resources.getString(R.string.error_invalid_email))
        }

        if (!password.value.equals(repassword.value, false)) {
            throw SignException(INVALID_CONFIRM_PASSWORD, context.resources.getString(R.string.error_pass_not_match))
        }
    }

    @SuppressLint("CheckResult")
    fun register() {
        loading.value = true
        subscriptions.add(
                Observable.just(this)
                        .doOnNext { verify() }
                        .flatMap {
                            userRepository.getUser(emailAddress.value!!)
                        }
                        .doOnNext {
                            if (!it.isEmpty) {
                                throw SignException(SignException.MAIL_EXIT, null)
                            }
                        }
                        .flatMap {
                            userRepository.createUser(getPayload())
                        }
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .doFinally{
                            loading.value = false
                        }
                        .subscribe(
                                {
                                    onRegisterSuccess()
                                },
                                {
                                    handleEror(it)
                                }
                        )
        )
    }

    private fun getPayload(): UserModel =
            UserModel(
                    id = "",
                    userName = username.value!!,
                    pass = password.value!!,
                    createAt = Calendar.getInstance().time,
                    displayName = "",
                    age = "",
                    gender = gender.value!!,
                    avatar = "",
                    email = emailAddress.value!!,
                    phone = phoneNumber.value,
                    country = "",
                    timestamp = Calendar.getInstance().time
            )

    private fun onRegisterSuccess() {
        DemoChatApp.INTANCE?.store?.userInfo =
                UserModel(
                        id = "",
                        userName = username.value!!,
                        pass = password.value!!,
                        createAt = Calendar.getInstance().time,
                        displayName = "",
                        age = "",
                        gender = gender.value!!,
                        avatar = "",
                        email = emailAddress.value!!,
                        phone = phoneNumber.value,
                        country = "",
                        timestamp = Calendar.getInstance().time
                )
        success.value = true
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    private fun handleEror(e: Throwable) {
        if (e is SignException) {
            when (e.code) {
                INVALID_USER_NAME -> {
                    showSnackBarError(R.string.error_user_name_invalid)
                }
                INVALID_EMAIL -> {
                    showSnackBarError(R.string.error_invalid_email)
                }
                INVALID_PASSWORD -> {
                    showSnackBarError(R.string.error_password_invalid)
                }
                INVALID_CONFIRM_PASSWORD -> {
                    showSnackBarError(R.string.error_pass_not_match)
                }
                MAIL_EXIT -> {
                    showSnackBarError(R.string.error_email_exit)
                }
            }
        }
    }

    class SignException(var code: Int, message: String?) : Exception(message) {
        companion object {
            const val INVALID_USER_NAME = 0
            const val INVALID_EMAIL = 2
            const val INVALID_PASSWORD = 3
            const val INVALID_CONFIRM_PASSWORD = 4
            const val MAIL_EXIT = 5
        }
    }
}