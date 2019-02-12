package tool.devp.demochat.presentation.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseViewModel
import tool.devp.demochat.common.DemoChatApp
import tool.devp.demochat.common.Optional
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.data.repository.UserRepository
import tool.devp.demochat.presentation.schedulers.SchedulerProvider

@Suppress("UNUSED_EXPRESSION")
class LoginViewModel(application: Application,
                     var userRepository: UserRepository) : BaseViewModel(application) {
    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()
    val error = MutableLiveData<Boolean>()
    val success = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val subscriptions = CompositeDisposable()

    fun login() {
        loading.value = true
        subscriptions.add(
                userRepository.login(email.value!!, pass.value!!)
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .doFinally {  loading.value = false }
                        .subscribe(
                                {
                                    onResult(it)
                                },
                                {
                                    handleError(it)
                                }
                        )
        )
    }

    private fun onResult(op: Optional<UserModel?>) {
        if (op.isEmpty) {
            showSnackBarError(R.string.error_mail_pass_incorrect)
        } else {
            DemoChatApp.INTANCE?.store?.userInfo = op.get()
            success.value = true
        }
    }

    private fun handleError(t: Throwable){
        Log.d("PhamDinhTuan", "")
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}