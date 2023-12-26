package com.example.envagemobileapplication.SharedPreferences

import android.content.Context
import android.content.SharedPreferences


class SSOSharedPreferences(context: Context) {

    private val COMPANY_PREFERENCES = "CompanyPrefs"
    private val KEY_COMPANY_ID = "companyId"
    private val KEY_COMPANY_LOGO = "companyLogo"
    private val KEY_COMPANY_FAVICON = "companyFavIcon"
    private val KEY_SECONDARY_COLOR = "secondaryColor"
    private val KEY_PRIMARY_COLOR = "primaryColor"
    private val KEY_COMPANY_DOMAIN = "companyDomain"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(COMPANY_PREFERENCES, Context.MODE_PRIVATE)

    fun saveCompanyData(
        companyId: String, companyLogo: String, companyFavIcon: String,
        secondaryColor: String, primaryColor: String, companyDomain: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_COMPANY_ID, companyId)
        editor.putString(KEY_COMPANY_LOGO, companyLogo)
        editor.putString(KEY_COMPANY_FAVICON, companyFavIcon)
        editor.putString(KEY_SECONDARY_COLOR, secondaryColor)
        editor.putString(KEY_PRIMARY_COLOR, primaryColor)
        editor.putString(KEY_COMPANY_DOMAIN, companyDomain)
        editor.apply()
    }

    fun getCompanyId(): String {
        return sharedPreferences.getString(KEY_COMPANY_ID, "") ?: ""
    }

    fun getCompanyLogo(): String {
        return sharedPreferences.getString(KEY_COMPANY_LOGO, "") ?: ""
    }

    fun getCompanyFavIcon(): String {
        return sharedPreferences.getString(KEY_COMPANY_FAVICON, "") ?: ""
    }

    fun getSecondaryColor(): String {
        return sharedPreferences.getString(KEY_SECONDARY_COLOR, "") ?: ""
    }

    fun getPrimaryColor(): String {
        return sharedPreferences.getString(KEY_PRIMARY_COLOR, "") ?: ""
    }

    fun getCompanyDomain(): String {
        return sharedPreferences.getString(KEY_COMPANY_DOMAIN, "") ?: ""
    }

    fun clearCompanyData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}