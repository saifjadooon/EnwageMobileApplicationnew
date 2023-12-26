package com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels

data class AddJobAdressDetailsReqModel(
    val address1: String?,
    val address2: String,
    val country: String,
    val zipcode: String,
    val city: String,
    val state: String,
    val location: String
)
