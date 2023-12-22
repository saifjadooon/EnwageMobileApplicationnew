package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionClientContacts
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.GetClientContactsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.GetJobsStatusesResponse
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientSummaryViewModel : ViewModel() {

    val LDgetContacts: LiveData<GetClientContactsResponse>
        get() = MLDGetContacts

    private val MLDGetContacts = MutableLiveData<GetClientContactsResponse>()

    val LDgetJobs: LiveData<GetJobsResponse>
        get() = MLDGetJobs

    private val MLDGetJobs = MutableLiveData<GetJobsResponse>()

    val LDgetJobStatusResponse: LiveData<GetJobsStatusesResponse>
        get() = MLDgetJobStatusResponse

    private val MLDgetJobStatusResponse =
        MutableLiveData<GetJobsStatusesResponse>()

    //--------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<///

    fun getJobs(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionJobs,
        isfromJobBottomSheet: Boolean,


        ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getJobs(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobsResponse> {
                        override fun onResponse(
                            call: Call<GetJobsResponse>,
                            response: Response<GetJobsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDGetJobs.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getContacts(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionClientContacts,


        ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getClientContacts(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetClientContactsResponse> {
                        override fun onResponse(
                            call: Call<GetClientContactsResponse>,
                            response: Response<GetClientContactsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDGetContacts.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetClientContactsResponse>,
                            t: Throwable
                        ) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }


    fun getallJobStatuses(
        context: Activity,
        accessToken: String?
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobStatuses(

                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobsStatusesResponse> {
                        override fun onResponse(
                            call: Call<GetJobsStatusesResponse>,
                            response: Response<GetJobsStatusesResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobStatusResponse.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetJobsStatusesResponse>,
                            t: Throwable
                        ) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun searchDataApi() {
        TODO("Not yet implemented")
    }

}