package tool.devp.demochat.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.common.AppSnackbar
import tool.devp.demochat.extension.transact
import tool.devp.demochat.presentation.schedulers.SchedulerProvider

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
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

    private fun showSnackBar(config: AppSnackbar.Config) {
        AppSnackbar.make(snackBarRootView, config).show()
    }

    fun replaceFragmentInsideFragment(containerViewId: Int, fragment: Fragment, addToStack: Boolean = false) {
        supportFragmentManager.transact {
            replace(containerViewId, fragment)
            if (addToStack) {
                addToBackStack(fragment::class.java.name)
//                setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out_30percent, R.anim.slide_left_in_30percent, R.anim.slide_right_out)
            }
        }
    }

    /**
     * You have to override [containerViewId] in order to make this function work
     */
    fun addFragmentInsideFragment(containerViewId: Int, fragment: Fragment, addToStack: Boolean = false) {
        supportFragmentManager.transact {
            add(containerViewId, fragment)
            if (addToStack) {
                addToBackStack(fragment::class.java.name)
//                setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out_30percent, R.anim.slide_left_in_30percent, R.anim.slide_right_out)
            }
        }
    }
}