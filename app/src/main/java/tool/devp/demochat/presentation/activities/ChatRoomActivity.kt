package tool.devp.demochat.presentation.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import tool.devp.demochat.R
import tool.devp.demochat.base.BaseActivity
import tool.devp.demochat.data.model.ChatRoomModel
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.extension.addOnTextChangedListener
import tool.devp.demochat.extension.checkPermission
import tool.devp.demochat.extension.getAppDir
import tool.devp.demochat.extension.initViewModel
import tool.devp.demochat.presentation.adapters.ChatRoomAdapter
import tool.devp.demochat.presentation.viewmodels.ChatRoomViewModel
import java.io.File

class ChatRoomActivity : BaseActivity<ChatRoomViewModel>() {

    private var chatAdapter: ChatRoomAdapter? = null
    override val snackBarRootView: View
        get() = rootView

    override fun onCreateViewModel(): ChatRoomViewModel = initViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        viewModel.apply {
            titleString.observe(this@ChatRoomActivity, Observer {
                it?.let {
                    tvTitle.text = it
                }
            })
        }
        intent?.let {
            it.getSerializableExtra(KEY_TARGET_USER)?.let { it ->
                with(it as UserModel) {
                    viewModel.start(this)
                }
            }
            it.getSerializableExtra(KEY_CHATROOM)?.let { it ->
                with(it as ChatRoomModel) {
                    viewModel.start(this)
                }
            }
        }
        initView()
        setActions()
    }

    private fun setActions() {
        editText.run {
            addOnTextChangedListener { s, _, _, _ ->
                sendAction.isEnabled = s.isNotEmpty()
            }
        }
        cameraAction.setOnClickListener {
            if (checkPermission()) {
                takePicture()
            }
        }
        galleryAction.setOnClickListener {
            if (checkPermission()) {
                pickImage()
            }
        }
        sendAction.setOnClickListener {
            viewModel.sendMessage(editText.text.toString())
        }
    }

    private fun initView() {
        chatAdapter = ChatRoomAdapter(viewModel, arrayListOf()).apply {
            recyclerView.adapter = this
        }
    }

    private fun takePicture() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        i.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.createUri())
        val chooser = Intent.createChooser(i, "capture_by")
        startActivityForResult(
                chooser, REQUEST_CODE_TAKE_PICTURE)
    }

    private fun pickImage() {
        if (Build.VERSION.SDK_INT <= 19) {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }, REQUEST_CODE_PICK_PICTURE)
        } else {
            startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE_PICK_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val KEY_TARGET_USER = "target_user"
        const val KEY_CHATROOM = "chat_room"
        const val REQUEST_CODE_TAKE_PICTURE = 1
        const val REQUEST_CODE_PICK_PICTURE = 2

        fun start(context: Context, taget: UserModel) {
            var intent = Intent(context, ChatRoomActivity::class.java).apply {
                putExtra(KEY_TARGET_USER, taget)
            }
            context.startActivity(intent)
        }

        fun start(context: Context, room: ChatRoomModel) {
            var intent = Intent(context, ChatRoomActivity::class.java).apply {
                putExtra(KEY_CHATROOM, room)
            }
            context.startActivity(intent)
        }
    }
}