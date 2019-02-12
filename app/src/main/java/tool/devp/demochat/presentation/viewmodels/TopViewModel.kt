package tool.devp.demochat.presentation.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.base.BaseViewModel
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.data.repository.UserRepository
import tool.devp.demochat.presentation.schedulers.SchedulerProvider

class TopViewModel(application: Application,
                   var userRepository: UserRepository) : BaseViewModel(application) {
    private val subscriptions = CompositeDisposable()
    val users = MutableLiveData<List<UserModel>>()

    fun start() {
        subscriptions.add(
                userRepository.getListUser()
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .doFinally {

                        }
                        .subscribe(
                                {
                                    onSuccess(it)
                                },
                                {

                                }
                        )
        )
    }

    private fun onSuccess(list: List<UserModel>){
        users.value = list
    }
}