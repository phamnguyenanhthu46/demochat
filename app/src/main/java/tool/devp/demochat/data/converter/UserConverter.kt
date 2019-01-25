package tool.devp.demochat.data.converter

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import tool.devp.demochat.data.entities.UserEntity
import tool.devp.demochat.data.model.UserModel

class UserConverter : FirebaseConverter<UserModel> {
    override fun deserialize(dataSnapshot: DocumentSnapshot): UserModel = with(dataSnapshot) {
        UserModel.newInstance(toObject(UserEntity::class.java)!!).apply {
            id = this@with.id
        }
    }

    override fun deserializes(query: QuerySnapshot): List<UserModel> = with(query) {
        map {
            UserModel.newInstance(it.toObject(UserEntity::class.java)).apply {
                id = it.id
            }
        }
    }

    override fun deserialize(query: DocumentChange): UserModel = with(query) {
        UserModel.newInstance(document.toObject(UserEntity::class.java)).apply {
            id = document.id
        }
    }

}