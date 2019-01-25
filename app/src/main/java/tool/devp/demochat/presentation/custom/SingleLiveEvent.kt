package tool.devp.demochat.presentation.custom

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T>: MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, Observer<T>{ t->
            if(pending.compareAndSet(true, false)){
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    /**
     * Use for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call(){
        value = null
    }
}