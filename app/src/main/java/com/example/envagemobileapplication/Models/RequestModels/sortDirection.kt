package com.example.envagemobileapplication.Models.RequestModels


data class sortDirection(
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String,
    val tileStatusId: Int

    /*  val pageIndex = 1
      val  pageSize = 25
      val searchText = ""
      val sortBy = "CreatedDate"
      val sortDirection = 1
      val tileStatusId = -1*/
)