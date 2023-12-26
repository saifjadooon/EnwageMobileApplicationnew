package com.example.envagemobileapplication.Utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder


@Suppress("DEPRECATION")
class SharedPrefs {
    companion object {
        private val SHARED_PREFS_FILE_NAME = "eHealthAI"
        val USER_DATA = "user_data"
        val LANGUAGE = "lang"
        val TOKEN = "Token"
        val FIREBASE_USER_DATA = "firebase_user_data"


        fun getLanguage(context: Context): String? {
            return getString(context, LANGUAGE)
        }

        fun setLanguage(context: Context, lang: String) {
            save(context, LANGUAGE, lang)
        }


        fun getToken(context: Context): String? {
            return getString(context, TOKEN)
        }

        fun setToken(context: Context, lang: String) {
            save(context, TOKEN, lang)
        }

        fun removeUserData(context: Context) {
            removeKey(context, USER_DATA)

        }

        fun deleteAll(context: Context) {
            getPrefs(context).edit().clear().commit()
        }


        fun getPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_MULTI_PROCESS)
        }


        //Save Booleans
        fun savePref(context: Context, key: String, value: Boolean) {
            getPrefs(context).edit().putBoolean(key, value).apply()
        }

        fun savePref(context: Context, key: String, value: String) {
            getPrefs(context).edit().putString(key, value).apply()
        }

        fun savePref(context: Context, key: String, value: Int) {
            getPrefs(context).edit().putInt(key, value).apply()
        }

        //Get Booleans
        fun getBoolean(context: Context, key: String): Boolean {
            return getPrefs(context).getBoolean(key, false)
        }

        //Get Booleans if not found return a predefined default value
        fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
            return getPrefs(context).getBoolean(key, defaultValue)
        }

        fun setFirstTimeLaunch(context: Context, key: String, value: Boolean) {
            getPrefs(context).edit().putBoolean(key, value).apply()
        }

        fun getIsFirstTimeLaunch(context: Context, key: String): Boolean {
            return getPrefs(context).getBoolean(key, true)
        }


        fun setUserLogin(context: Context, key: String, value: Boolean) {
            getPrefs(context).edit().putBoolean(key, value).apply()
        }

        fun getUserLogin(context: Context, key: String): Boolean {
            return getPrefs(context).getBoolean(key, false)
        }

        //Strings
        fun save(context: Context, key: String, value: String) {
            getPrefs(context).edit().putString(key, value).commit()
        }

        fun save(context: Context, key: String, value: Boolean) {
            getPrefs(context).edit().putBoolean(key, value).commit()
        }

        fun getString(context: Context, key: String): String? {
            return getPrefs(context).getString(key, "")
        }

        fun getString(context: Context, key: String, defaultValue: String): String? {
            return getPrefs(context).getString(key, defaultValue)
        }

        //Integers
        fun save(context: Context, key: String, value: Int) {
            getPrefs(context).edit().putInt(key, value).commit()
        }

        fun getInt(context: Context, key: String): Int {
            return getPrefs(context).getInt(key, 0)
        }

        fun getInt(context: Context, key: String, defaultValue: Int): Int {
            return getPrefs(context).getInt(key, defaultValue)
        }

        //Floats
        fun save(context: Context, key: String, value: Float) {
            getPrefs(context).edit().putFloat(key, value).commit()
        }

        fun getFloat(context: Context, key: String): Float {
            return getPrefs(context).getFloat(key, 0f)
        }

        fun getFloat(context: Context, key: String, defaultValue: Float): Float {
            return getPrefs(context).getFloat(key, defaultValue)
        }

        //Longs
        fun save(context: Context, key: String, value: Long) {
            getPrefs(context).edit().putLong(key, value).commit()
        }

        fun getLong(context: Context, key: String): Long {
            return getPrefs(context).getLong(key, 0)
        }

        fun getLong(context: Context, key: String, defaultValue: Long): Long {
            return getPrefs(context).getLong(key, defaultValue)
        }

        //StringSets
        fun save(context: Context, key: String, value: Set<String>) {
            getPrefs(context).edit().putStringSet(key, value).commit()
        }

        fun getStringSet(context: Context, key: String): Set<String>? {
            return getPrefs(context).getStringSet(key, null)
        }

        fun getStringSet(context: Context, key: String, defaultValue: Set<String>): Set<String>? {
            return getPrefs(context).getStringSet(key, defaultValue)
        }

        fun removeKey(context: Context, key: String) {
            getPrefs(context).edit().remove(key).commit()
        }


        fun <T> getObjectPref(context: Context, key: String, clazz: Class<T>?): T {
            return GsonBuilder().create().fromJson(getPrefs(context).getString(key, null), clazz)
        }

    }
}