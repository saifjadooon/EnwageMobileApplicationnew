package com.example.envagemobileapplication.Models.RequestModels

data class SortDirectionJobs(
    val clientId: String?,
    val jobFilters: List<Any>,
    val pageIndex: Int,
    val pageSize: Int,
    val searchText: String,
    val sortBy: String,
    val sortDirection: Int,
    val tileStatusId: Int

)


