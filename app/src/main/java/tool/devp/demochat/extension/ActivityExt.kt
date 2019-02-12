package tool.devp.demochat.extension

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import tool.devp.demochat.presentation.factory.ViewModelFactory
import java.io.File

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

fun Activity.checkPermission(): Boolean{
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) + ContextCompat
                    .checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, permissions, 1)
        false
    } else {
        true
    }
}

fun Context.getAppDir(): File {
    var file = File(Environment.getExternalStorageDirectory(), "DemoChat")
    if (!file.exists()) {
        if (!file.mkdir())
            file = this.cacheDir
    }

    return file
}