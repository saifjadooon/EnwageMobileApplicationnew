package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.TokenResponsee

import com.google.gson.annotations.SerializedName


data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("refresh_token") val refreshToken: String
)