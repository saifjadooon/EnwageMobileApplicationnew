package com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels

import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import okhttp3.MultipartBody


data class AddJobDetailsReqModel(
    val description: MultipartBody.Part,
    val headcount: Int,
    val jobType: String,
    val startDate: String,
    val endDate: String,
    val currency: String,
    val minimumSalary: Int,
    val maximumSalary: Int,
    val workingDaysNo: Int,
    val estimatedHours: Int,
    val workingDays: String,
    val jobStatusId: Int,
    val jobSkills: List<JobSkill>
)

data class JobSkill(
    val skillId: Int,
    val name: String,
    val skillRating: String,
    val isDeleted: Boolean,
    val jobId: Int
)