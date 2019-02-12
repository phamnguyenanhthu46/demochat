package tool.devp.demochat.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.support.annotation.StringRes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import tool.devp.demochat.common.AppSnackbar

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context = application.applicationContext

    private val snackBarConfig: PublishSubject<AppSnackbar.Config> = PublishSubject.create<AppSnackbar.Config>()
    fun getSnackBarConfig(): Observable<AppSnackbar.Config> = snackBarConfig

    protected fun showSnackBarError(@StringRes res: Int) {
        snackBarConfig.onNext(AppSnackbar.Config.error(res))
    }
}