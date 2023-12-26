package com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels

data class AddJobSalaryDetailReqModel(
    val markup: Int,
    val minPayRate: Double,
    val minBillRate: Double,
    val maxPayRate: Double,
    val maxBillRate: Double,
    val targetPayRate: Double,
    val targetBillRate: Double,
    val overTimeMultiplier: Double,
    val overTimeType: String,
    val overTimeMarkup: Int,
    val overTimePayRate: Double,
    val overTimeBillRate: Double,
    val doubleTimeMultiplier: Double,
    val doubleTimeType: String,
    val doubleTimeMarkup: Int,
    val doubleTimePayRate: Double,
    val doubleTimeBillRate: Double,
    val frequency: String
)
