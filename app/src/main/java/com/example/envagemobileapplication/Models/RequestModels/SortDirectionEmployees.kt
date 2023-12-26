package com.example.envagemobileapplication.Models.RequestModels

data class SortDirectionEmployees (
    val employeeFilters: List<Any>,
    val pageIndex: Int,
    val pageSize: Int,
    val searchText: String,
    val sortBy: String,
    val sortDirection: Int,
    val tileStatusId: Int
)
