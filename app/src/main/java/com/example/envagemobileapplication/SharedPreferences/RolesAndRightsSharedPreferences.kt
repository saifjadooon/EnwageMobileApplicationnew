import android.content.Context
import android.content.SharedPreferences
import com.example.envagemobileapplication.Models.RequestModels.UserRight
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RolesAndRightsSharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserRightsPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUserRights(userRightsList: List<UserRight>) {
        val json = gson.toJson(userRightsList)
        sharedPreferences.edit().putString("userRightsList", json).apply()
    }

    fun getUserRights(): List<UserRight> {
        val json = sharedPreferences.getString("userRightsList", null)
        return if (json != null) {
            val type = object : TypeToken<List<UserRight>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}