package com.example.envagemobileapplication.Models.RequestModels

data class SortDirectionGetOnlineCandidate(
    val applicantFilter: List<Any>,
    val jobId: Int,
    val pageIndex: Int,
    val pageSize: Int,
    val searchText: String,
    val sortBy: String,
    val sortDirection: Int
)

