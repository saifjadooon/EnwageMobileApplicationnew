package com.ezshifa.aihealthcare.network

import android.content.Context
import com.example.envagemobileapplication.Utils.Constants


class ApiUtils {
    companion object {
        fun getAPIService(context: Context): ApiInterface {
            return ApiClient.getClient(context, Constants.BASE_URL_STAGING)
                .create(ApiInterface::class.java)
        }
    }
}