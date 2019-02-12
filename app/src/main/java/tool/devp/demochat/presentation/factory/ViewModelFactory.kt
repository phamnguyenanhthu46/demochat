package tool.devp.demochat.presentation.factory

import android.app.Activity
import android.app.Fragment
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import tool.devp.demochat.data.ChatRoomRemoteDataSource
import tool.devp.demochat.data.MessageRemoteDataSource
import tool.devp.demochat.data.UserRemoteDataSource
import tool.devp.demochat.data.getInstance
import tool.devp.demochat.data.repository.UserRepository
import tool.devp.demochat.presentation.viewmodels.*

class ViewModelFactory(context: Context,
                       private val userDataSource: UserRepository,
                       private val chatRoomRemoteDataSource: ChatRoomRemoteDataSource,
                       private val messageRemoteDataSource: MessageRemoteDataSource) : ViewModelProvider.NewInstanceFactory() {
    private val application = when (context) {
        is Activity -> context.application
        is Fragment -> context.activity.application
        else -> throw IllegalStateException("unknown apllication: $context")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(SignInSignUpViewModel::class.java) -> {
                        SignInSignUpViewModel(application)
                    }
                    isAssignableFrom(LoginViewModel::class.java) -> {
                        LoginViewModel(application,userDataSource)
                    }
                    isAssignableFrom(RegisterViewModel::class.java) -> {
                        RegisterViewModel(application,userDataSource)
                    }
                    isAssignableFrom(TopViewModel::class.java) -> {
                        TopViewModel(application, userDataSource)
                    }
                    isAssignableFrom(ChatListViewModel::class.java) -> {
                        ChatListViewModel(application)
                    }
                    isAssignableFrom(FriendViewModel::class.java) -> {
                        FriendViewModel(application)
                    }
                    isAssignableFrom(MyPageViewModel::class.java) -> {
                        MyPageViewModel(application)
                    }
                    isAssignableFrom(ChatRoomViewModel::class.java) -> {
                        ChatRoomViewModel(application,chatRoomRemoteDataSource,messageRemoteDataSource)
                    }
                    else -> throw IllegalStateException("unknown view model: $modelClass")
                }
            } as T


    companion object {
        fun getInstance(activity: Context): ViewModelFactory = ViewModelFactory(
                activity,
                UserRemoteDataSource.getInstance(),
                ChatRoomRemoteDataSource.getInstance(),
                MessageRemoteDataSource.getInstance()
        )
    }
}