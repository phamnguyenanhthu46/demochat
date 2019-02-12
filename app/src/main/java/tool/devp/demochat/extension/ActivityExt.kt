package tool.devp.demochat.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import tool.devp.demochat.presentation.factory.ViewModelFactory

inline fun <reified T : ViewModel> AppCompatActivity.initViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.initViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(this.context!!)).get(T::class.java)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}