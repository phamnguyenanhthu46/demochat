package tool.devp.demochat.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.disposables.CompositeDisposable
import tool.devp.demochat.base.BaseViewModel
import tool.devp.demochat.common.DemoChatApp
import tool.devp.demochat.data.ChatRoomRemoteDataSource
import tool.devp.demochat.data.MessageRemoteDataSource
import tool.devp.demochat.data.model.ChatRoomModel
import tool.devp.demochat.data.model.MessageModel
import tool.devp.demochat.data.model.UserModel
import tool.devp.demochat.extension.getAppDir
import tool.devp.demochat.extension.getImagePathFromUri
import tool.devp.demochat.presentation.activities.ChatRoomActivity.Companion.REQUEST_CODE_PICK_PICTURE
import tool.devp.demochat.presentation.activities.ChatRoomActivity.Companion.REQUEST_CODE_TAKE_PICTURE
import tool.devp.demochat.presentation.schedulers.SchedulerProvider
import tool.devp.demochat.presentation.uimodel.MessageUiModel
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class ChatRoomViewModel(application: Application, private val roomRemote: ChatRoomRemoteDataSource, private val messageRemote: MessageRemoteDataSource) : BaseViewModel(application), MessageRemoteDataSource.MessageListener {
    var TAG = ChatRoomViewModel::class.java.name
    val titleString = MutableLiveData<String>()
    val newMessage = MutableLiveData<MessageUiModel>()
    val messages = MutableLiveData<List<MessageUiModel>>()
    val toBottom = MutableLiveData<Boolean>()
    var isLoadingMoreItems = false
    var hasMoreItem = true
    private var mRoomId: String? = null
    var senderId: String = DemoChatApp.INTANCE.store.userInfo!!.email!!
    private var imagePatch: String? = null
    private val subscriptions = CompositeDisposable()

    /**
     * start from list user
     */
    fun start(taget: UserModel) {
        titleString.value = taget.userName
        DemoChatApp.INTANCE.store.userInfo?.let {
            getChatroom(listOf(it.email!!, taget.email!!))
        }
    }

    /**
     * start from list conversation
     */
    fun start(room: ChatRoomModel) {

    }

    fun sendMessage(content: String) {
       postMessage(content, MessageModel.TYPE.TEXT.value)
    }

    fun postMessage(content: String, typye: Int){
        MessageModel(
                id = "",
                content = content,
                messageType = typye,
                senderID = senderId,
                timeTemp = Calendar.getInstance().time
        ).apply {
            postMessage(this)
        }
    }

    private fun postMessage(mes: MessageModel) {
        mRoomId?.let {
            subscriptions.add(
                    messageRemote.postMessage(it, mes)
                            .subscribeOn(SchedulerProvider.io())
                            .observeOn(SchedulerProvider.ui())
                            .subscribe(
                                    {},
                                    {
                                        Log.d(TAG, "")
                                    }
                            )
            )
        }
    }

    @SuppressLint("CheckResult")
    fun getChatroom(users: List<String>) {
        subscriptions.add(
                roomRemote.getChatRoom(users)
                        .doOnNext {
                            if (it.isPresent) {
                                mRoomId = it.get().id
                                messageRemote.subscribeMessage(mRoomId!!, this)
                            }
                        }
                        .flatMap {
                            if (it.isPresent) {
                                messageRemote.messages(it.get().id, null)
                            } else {
                                roomRemote.createChatRoom(newRoom(users))
                            }
                        }
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .subscribe(
                                {
                                    onSuccess(it)
                                },
                                {
                                    Log.d(TAG, "")
                                }
                        )
        )
    }

    fun loadMoreItems() {
        if (isLoadingMoreItems && !hasMoreItem) return
        isLoadingMoreItems = true
        mRoomId?.let {
            subscriptions.add(
                    messageRemote.messages(it, messages.value?.firstOrNull()?.id)
                            .subscribeOn(SchedulerProvider.io())
                            .observeOn(SchedulerProvider.ui())
                            .subscribe(
                                    {
                                        onSuccess(it)
                                    },
                                    {
                                        Log.d(TAG, "")
                                    }
                            )

            )
        }
    }

    private fun newRoom(users: List<String>): ChatRoomModel =
            ChatRoomModel(
                    id = "",
                    users = users
            )

    private fun onSuccess(output: Any) {
        when (output) {
            is ArrayList<*> -> {
                if (output.isEmpty()) {
                    hasMoreItem = false
                }
                isLoadingMoreItems = false
                messages.value = (output as ArrayList<MessageModel>).map {
                    MessageUiModel.newIntance(senderId, it)
                }.sortedBy { it.createdAt }

            }
            is String -> {
                mRoomId = output
            }
        }
    }

    private fun updalodPhoto(local: Uri?) {
        local?.let {
            val file = File(context.getImagePathFromUri(it))
            if (file.exists()) {
                uploadImageToFireStore(file.absolutePath) {
                    postMessage(it,MessageModel.TYPE.IMAGE.value)
                }
            }
        }
    }

    fun uploadImageToFireStore(patch: String, onSuccess: (url: String) -> Unit) {
        val storage = FirebaseStorage.getInstance("gs://demochatfirebase-d15e7.appspot.com")
        val storageRef = storage.reference

        var file = Uri.fromFile(File(patch))
        val fileRef = storageRef.child("images/${file.lastPathSegment}")
        var uploadTask = fileRef.putFile(file)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.d(TAG, "")
                }
            }
            return@Continuation fileRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onSuccess.invoke( task.result.toString())
                Log.d(TAG, "")
            }
        }
    }

    fun createUri(): Uri {
        var directory = File(context.getAppDir(), "${System.currentTimeMillis()}.jpg")
        imagePatch = directory.absolutePath
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Uri.fromFile(directory)
        } else {
            FileProvider.getUriForFile(context, context.packageName + ".provider", directory)
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                imagePatch?.let {
                    postMessage(it,MessageModel.TYPE.IMAGE.value)
                }
            }
        }
        if (requestCode == REQUEST_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                updalodPhoto(data?.data)
            }
        }
    }

    override fun onMessageLocal(items: List<MessageModel>) {
        items.forEach {
            newMessage.value = MessageUiModel.newIntance(senderId, it).apply {
                status = MessageUiModel.STATUS.PENDING.value
            }
        }
    }

    override fun onMessageServer(items: List<MessageModel>) {
        items.forEach {
            newMessage.value = MessageUiModel.newIntance(senderId, it).apply {
                status = MessageUiModel.STATUS.SUCCESS.value
            }
        }
    }
}