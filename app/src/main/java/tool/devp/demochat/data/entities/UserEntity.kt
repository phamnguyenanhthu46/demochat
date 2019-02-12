package tool.devp.demochat.data.entities

import com.google.firebase.firestore.ServerTimestamp
import tool.devp.demochat.data.model.UserModel
import java.util.*

data class UserEntity(
        @ServerTimestamp
        var createAt: Date? = null,

        var userName: String? = null,

        var pass: String? = null,

        var displayName: String? = null,

        var age: String? = null,

        var gender: Int? = 0,

        var avatar: String? = null,

        var email: String? = null,

        var phone: String? = null,

        var country: String? = null,

        var timestamp: Date? = null
) {
    companion object {
        fun newInstance(model: UserModel): UserEntity =
                UserEntity(
                        createAt = null,
                        userName = model.userName,
                        pass = model.pass,
                        displayName = model.displayName,
                        age = model.age,
                        gender = model.gender,
                        avatar = model.avatar,
                        email = model.email,
                        phone = model.phone,
                        country = model.country,
                        timestamp = model.timestamp
                )
    }

    enum class GENDER(var value: Int) {
        MALE(1),
        FEMALE(2)
    }
}