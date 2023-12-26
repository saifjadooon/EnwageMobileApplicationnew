package com.example.envagemobileapplication.Oauth

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val USER_NAME = "user_name"
        private const val PASSWORD = "password"
        private const val PROFILE_PIC = "profileimage"
        private const val USER_EMAIL = "email"
        private const val LOGGEDIN_USER_NAME = "user_name"
        private const val LOGGEDIN_First_name = "first_name"
        private const val LOGGEDIN_Last_name = "last_name"

    }

    fun saveFirstName(first_name: String) {
        editor.putString(LOGGEDIN_First_name, first_name)
        editor.apply()
    }

    fun getuserFirstName(): String? {
        return sharedPreferences.getString(LOGGEDIN_First_name, null)
    }

    fun saveLastName(last_name: String) {
        editor.putString(LOGGEDIN_Last_name, last_name)
        editor.apply()
    }

    fun getuserLastName(): String? {
        return sharedPreferences.getString(LOGGEDIN_Last_name, null)
    }


    fun saveUsername(user_name: String) {
        editor.putString(USER_NAME, user_name)
        editor.apply()
    }

    fun getuserName(): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }

    fun savePassword(password: String) {
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(PASSWORD, null)
    }

    fun saveAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun saveRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN_KEY, token)
        editor.apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun saveProfiepic(profilepic: String) {
        editor.putString(PROFILE_PIC, profilepic)
        editor.apply()
    }

    fun getProfilePic(): String? {
        return sharedPreferences.getString(PROFILE_PIC, null)
    }

    fun saveUserEmail(userEmail: String) {
        editor.putString(USER_EMAIL, userEmail)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, null)
    }


    fun saveLoggedInUserName(userName: String) {
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }


    fun clearTokens() {
        editor.remove(ACCESS_TOKEN_KEY)
        editor.remove(REFRESH_TOKEN_KEY)
        editor.apply()
    }


}