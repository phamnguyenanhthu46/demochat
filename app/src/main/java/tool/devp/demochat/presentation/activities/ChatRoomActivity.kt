package tool.devp.demochat.presentation.activities

import android.os.Bundle
import android.view.View
import tool.devp.demochat.base.BaseActivity
import tool.devp.demochat.presentation.viewmodels.ChatRoomViewModel

class ChatRoomActivity: BaseActivity<ChatRoomViewModel>() {
    override val snackBarRootView: View
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreateViewModel(): ChatRoomViewModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}