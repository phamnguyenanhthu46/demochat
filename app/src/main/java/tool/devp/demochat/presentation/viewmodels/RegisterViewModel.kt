package tool.devp.demochat.presentation.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import tool.devp.demochat.base.BaseViewModel
import tool.devp.demochat.presentation.custom.SingleLiveEvent

class RegisterViewModel(application: Application) : BaseViewModel(application) {
    val isAttemptable = SingleLiveEvent<Boolean>().apply { value = false }
    val emailAddress = MutableLiveData<String>().apply { observeForever { updateAttempable() } }
    val username = MutableLiveData<String>().apply { observeForever { updateAttempable() } }
    val password = MutableLiveData<String>().apply { observeForever { updateAttempable() } }
    val repassword = MutableLiveData<String>().apply { observeForever { updateAttempable() } }
    val phoneNumber = MutableLiveData<String>().apply {  }
    val lastName = MutableLiveData<String>().apply { observeForever { updateAttempable() } }
    val gender = MutableLiveData<String>().apply {
        value = ""
        observeForever { updateAttempable() }
    }

    private fun updateAttempable() {
        isAttemptable.value = !emailAddress.value.isNullOrBlank()
                && !username.value.isNullOrBlank()
                && !password.value.isNullOrBlank()
                && !repassword.value.isNullOrBlank()
                && !lastName.value.isNullOrBlank()
                && !gender.value.isNullOrBlank()
    }
}