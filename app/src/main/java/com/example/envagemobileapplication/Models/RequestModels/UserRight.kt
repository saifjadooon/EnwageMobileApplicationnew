package com.example.envagemobileapplication.Models.RequestModels

data class UserRight(
    val userRightsId: Long,
    val userId: Long,
    val rightName: String,
    val moduleName: String,
    val roleId: Long,
    val roleName: String,
    val rightsId: Long,
    val isAllowed: Boolean,
    val subModuleName: String
)