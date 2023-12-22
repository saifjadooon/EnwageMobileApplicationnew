package com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels


data class GetCustomJobTemplateReqModel(
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String
)