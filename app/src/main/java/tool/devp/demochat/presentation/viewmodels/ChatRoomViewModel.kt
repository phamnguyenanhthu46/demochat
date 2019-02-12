package tool.devp.demochat.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
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
import java.io.File

class ChatRoomViewModel(application: Application, private val roomRemote: ChatRoomRemoteDataSource, private val messageRemote: MessageRemoteDataSource) : BaseViewModel(application) {
    val titleString = MutableLiveData<String>()
    lateinit var mRoomId: String
    private var imagePatch: String? = null
    private val subscriptions = CompositeDisposable()

    fun start(taget: UserModel) {
        titleString.value = taget.userName
        DemoChatApp.INTANCE.store.userInfo?.let {
            getChatroom(listOf(it.email!!, taget.email!!))
        }
    }

    fun start(room: ChatRoomModel) {

    }

    fun sendMessage(content: String) {

    }

    @SuppressLint("CheckResult")
    fun getChatroom(users: List<String>) {
        subscriptions.add(
                roomRemote.getChatRoom(users)
                        .doOnNext {
                            if (it.isPresent) mRoomId = it.get().id
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
                                    Log.d("PhamDinhTuan", "")
                                }
                        )
        )
    }

    private fun newRoom(users: List<String>): ChatRoomModel =
            ChatRoomModel(
                    id = "",
                    users = users
            )

    private fun onSuccess(output: Any) {
        when (output) {
            is ArrayList<*> -> {
                Log.d("PhamDinhTuan", "")
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
                uploadImageToFireStore(file.absolutePath)
            }
        }
    }

    fun uploadImageToFireStore(patch: String){
        val storage = FirebaseStorage.getInstance("gs://demochatfirebase-d15e7.appspot.com")
        val storageRef = storage.reference

        var file = Uri.fromFile(File(patch))
        val fileRef = storageRef.child("images/${file.lastPathSegment}")
        var uploadTask = fileRef.putFile(file)

        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.d("PhamDinhTuan","")
                }
            }
            return@Continuation fileRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("PhamDinhTuan","")
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

            }
        }
        if (requestCode == REQUEST_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                updalodPhoto(data?.data)
            }
        }
    }

}