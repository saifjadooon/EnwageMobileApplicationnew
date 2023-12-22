package com.example.envagemobileapplication.Oauth

import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyDomainResponse.GetCompanyDomainResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.TokenResponsee.Example
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRights.GetRigths
import com.example.envagemobileapplication.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object OAuthClient {

    private val BASE_URL = Constants.BASE_URL


    val okHttpClient = OkHttpClient.Builder()
        .authenticator(TokenAuthenticator(Constants.token.toString()))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    private val oauthService: OAuthService = retrofit.create(OAuthService::class.java)

    fun getToken(username: String, password: String): Call<Example> {
        return oauthService.getToken(
            "password",
            "SSOClient",
            "ssosecret",
            username,
            password
        )
    }

    fun getCompanyDomainName(token: String): Call<GetCompanyDomainResponse> {
        return oauthService.getCompanyByDomain(
            token
        )
    }

    fun getRights(): Call<GetRigths> {
        return oauthService.getRights(
        )
    }
}


