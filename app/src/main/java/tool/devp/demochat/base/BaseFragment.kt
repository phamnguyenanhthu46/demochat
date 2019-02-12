package tool.devp.demochat.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.common.AppSnackbar
import tool.devp.demochat.presentation.schedulers.SchedulerProvider

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    private val subscriptions = CompositeDisposable()

    private lateinit var internalViewModel: VM

    protected val viewModel
        get() = internalViewModel

    protected open val snackBarRootView: View
        get() = view!!

    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewModel = onCreateViewModel()
        subscriptions.add(viewModel.getSnackBarConfig()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showSnackBar))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes(), container, false)

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
    abstract fun onCreateViewModel(): VM

    private fun showSnackBar(config: AppSnackbar.Config) {
        AppSnackbar.make(snackBarRootView, config).show()
    }
}