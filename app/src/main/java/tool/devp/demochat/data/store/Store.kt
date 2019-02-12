package tool.devp.demochat.data.store

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import tool.devp.demochat.data.model.UserModel

class Store(context: Context) {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? by lazy(prefs::edit)

    fun delete() {
        prefs.edit().clear().apply()
    }

    fun getStringValue(key: String): String? {
        return prefs.getString(key, "")
    }

    fun setStringValue(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun getIntValue(key: String): Int {
        return prefs!!.getInt(key, 0)
    }

    fun setIntValue(key: String, value: Int) {
        editor!!.putInt(key, value)
        editor!!.apply()
    }

    fun removeKey(key: String) {
        editor!!.remove(key)
        editor!!.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun setBooleanValue(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.apply()
    }


    fun getBooleanValue(key: String): Boolean {
        return prefs!!.getBoolean(key, false)
    }

    fun putBoolean(permission: String, b: Boolean) {
        editor!!.putBoolean(permission, b)
        editor!!.apply()
    }

    fun getBoolean(permission: String, b: Boolean): Boolean {
        return prefs!!.getBoolean(permission, b)
    }

    /**
     * User Preferences
     */
    var userInfo: UserModel?
        get() {
            val json = prefs.getString(KEY_USER_INFO, "")
            if (json.isNotBlank()) {
                return gson.fromJson(json,UserModel::class.java)
            }
            return null
        }
        set(value) {
            setStringValue(KEY_USER_INFO, gson.toJson(value))
        }

    companion object {
        private const val PREF_NAME = "demo.chat.sharePreferences"
        private const val KEY_USER_INFO = "demo.chat.user"
    }


}
