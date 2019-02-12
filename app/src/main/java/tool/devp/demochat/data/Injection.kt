package tool.devp.demochat.data

import com.google.firebase.firestore.FirebaseFirestore
import tool.devp.demochat.data.converter.ChatRoomConverter
import tool.devp.demochat.data.converter.MessageConverter
import tool.devp.demochat.data.converter.UserConverter

fun UserRemoteDataSource.Companion.getInstance() =
        getInstance(FirebaseFirestore.getInstance(), UserConverter())

fun ChatRoomRemoteDataSource.Companion.getInstance() =
        newInstance(FirebaseFirestore.getInstance(), ChatRoomConverter())

fun MessageRemoteDataSource.Companion.getInstance() =
        getInstance(FirebaseFirestore.getInstance(), MessageConverter())