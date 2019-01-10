package tool.devp.demochat.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {
    private lateinit var internalViewModel: VM
    protected abstract val snackBarRootView: View

    val viewModel: VM
        get() = internalViewModel

    abstract fun onCreateViewModel(): VM
    private val snackBarSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewModel = onCreateViewModel()

        snackBarSubscriptions.add(viewModel.getSnackBarConfig()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showSnackBar))
    }

}