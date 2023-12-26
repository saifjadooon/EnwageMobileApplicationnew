package com.example.envagemobileapplication.Models.RequestModels

import okhttp3.RequestBody

data class AddClientRequestModel(
    val description: RequestBody,
    val profilePicture: RequestBody,
    val name: String,
    val websiteUrl: String
)

