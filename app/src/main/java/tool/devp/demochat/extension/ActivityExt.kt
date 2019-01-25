package tool.devp.demochat.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import tool.devp.demochat.presentation.factory.ViewModelFactory

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.obtainViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(this.context!!)).get(T::class.java)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}