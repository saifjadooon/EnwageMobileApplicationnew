package com.example.envagemobileapplication.Models.RequestModels


data class SortDirectionClientContacts(

    val clientContactFilters: List<Any>,
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String,
    val clientId: String?

)



