package tool.devp.demochat.data

import com.google.firebase.firestore.FirebaseFirestore
import tool.devp.demochat.data.converter.UserConverter

fun UserRemoteDataSource.Companion.getInstance() =
        getInstance(FirebaseFirestore.getInstance(), UserConverter())