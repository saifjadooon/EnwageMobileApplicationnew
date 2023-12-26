package com.example.envagemobileapplication.Models.RequestModels


data class sendMesageReqModel(
    val to: List<String>,
    val body: String
)