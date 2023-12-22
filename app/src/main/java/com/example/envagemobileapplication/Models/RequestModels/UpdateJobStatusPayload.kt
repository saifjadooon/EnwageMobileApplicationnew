package com.example.envagemobileapplication.Models.RequestModels

data class UpdateJobStatusPayload(
    val OfferedSalary: String?,
    val billRate: String?,
    val candidateGUID: String,
    val doubleBillRate: String,
    val doublePayRate: String,
    val jobId: Int,
    val joiningDate: String,
    val overtimeBillRate: String,
    val overtimePayRate: String,
    val payRate: String,
    val statusId: String,
)