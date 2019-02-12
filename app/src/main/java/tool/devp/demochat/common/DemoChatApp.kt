package tool.devp.demochat.common

import android.app.Application
import tool.devp.demochat.data.store.Store

class DemoChatApp : Application() {
    lateinit var store: Store
    override fun onCreate() {
        super.onCreate()
        INTANCE = this
        store = Store(this)
    }

    companion object {
        lateinit var INTANCE: DemoChatApp
    }
}