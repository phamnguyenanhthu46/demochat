package tool.devp.demochat.presentation.factory

import android.app.Activity
import android.app.Fragment
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import tool.devp.demochat.presentation.viewmodels.LoginViewModel
import tool.devp.demochat.presentation.viewmodels.RegisterViewModel
import tool.devp.demochat.presentation.viewmodels.SignInSignUpViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {
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
                        LoginViewModel(application)
                    }
                    isAssignableFrom(RegisterViewModel::class.java) -> {
                        RegisterViewModel(application)
                    }
                    else -> throw IllegalStateException("unknown view model: $modelClass")
                }
            } as T


    companion object {
        fun getInstance(activity: Context): ViewModelFactory = ViewModelFactory(
                activity
//                GenreRemoteDataSource.getInstance(activity),
//                AdvertisementRemoteDataSource.getInstance(activity),
//                UserRemoteDataSource.getInstance(activity),
//                ChatRoomRepository.getInstance(activity),
//                BookingRepository.getInstance(),
//                AuthRemoteDataSource.getInstance(),
//                CouponListRemoteDataSource.getInstance(),
//                LeafletsRemoteDataSource.getInstance(),
//                SceneRemoteDataSource.getInstance(activity),
//                TimeSaleRemoteDataSource.getInstance(),
//                TweetRequestRemoteDataSource.getInstance(),
//                TockenRemoteDataSource.getInstance(),
//                NewsRemoteDataSource.getInstance(),
//                GoingOutRemoteDataSource.getInstance(),
//                ResetPassRemoteDataSource.getInstance(),
//                WorkDataSource.getInstance(),
//                FirebaseUserRemoteDatasource.getInstance(),
//                AdLeafletsRemoteDataSource.getInstance()
        )
    }
}