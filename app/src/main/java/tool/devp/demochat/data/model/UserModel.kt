package tool.devp.demochat.data.model

import tool.devp.demochat.data.entities.UserEntity
import java.util.*

data class UserModel(
        var id: String,
        var userName: String,
        var pass: String? = null,
        var createAt: Date,
        var displayName: String? = null,
        var age: String? = null,
        var gender: Int? = 0,
        var avatar: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var country: String? = null,
        var timestamp: Date
) {
    companion object {
        fun newInstance(entity: UserEntity): UserModel =
                UserModel(
                        id = "",
                        userName = entity.userName!!,
                        createAt = if (entity.createAt == null) entity.timestamp!! else entity.createAt!!,
                        displayName = entity.displayName,
                        age = entity.age,
                        gender = entity.gender,
                        avatar = entity.avatar,
                        email = entity.email,
                        phone = entity.phone,
                        country = entity.country,
                        timestamp = entity.timestamp!!
                )
    }
}