package com.example.envagemobileapplication.Oauth

import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyDomainResponse.GetCompanyDomainResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRights.GetRigths
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.TokenResponsee.Example
import retrofit2.Call
import retrofit2.http.*

interface OAuthService {
    @FormUrlEncoded
    @POST("connect/token")
    fun getToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Example>


    @GET("api/Company/GetCompanyByDomainName")
    fun getCompanyByDomain(@Query("domain") domain: String?): Call<GetCompanyDomainResponse>

    @GET("api/v1/User/get-rights")
    fun getRights(): Call<GetRigths>
}