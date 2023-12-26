package com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels

data class AddJobBasicDetailRequestModel(
    val positionName: String,
    val clientId: Int,
    val payrollPayGroupId: Int,
    val jobId: Int,
    val industryId: Int,
    val jobNature: String,
    val useTemplate: String,
    val jobTemplateId: String
)
