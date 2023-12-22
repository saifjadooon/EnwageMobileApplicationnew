package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.CandidateJobStatusRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandiStatuskeyPipeline.GetCandiStatuskeyPipeline
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.GetRecentJobsRes
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidatesProfileSumViewModel  : ViewModel(){

    val LDgetJobDetailsById: LiveData<GetJobByJobIdResponse>
        get() = MLDgetJobDetailsById

    private val MLDgetJobDetailsById =
        MutableLiveData<GetJobByJobIdResponse>()

    val LDgetCandidateJobStatuses: LiveData<CandidateJobStatusRes> get() = MLDgetCandidateJobStatuses
    private val MLDgetCandidateJobStatuses = MutableLiveData<CandidateJobStatusRes>()


    val LDgetCandidateStatusKeyPipeline: LiveData<GetCandiStatuskeyPipeline> get() = MLDgetCandidateStatusKeyPipeline
    private val MLDgetCandidateStatusKeyPipeline = MutableLiveData<GetCandiStatuskeyPipeline>()


    val LDgetRecentJobs: LiveData<GetRecentJobsRes> get() = MLDgetRecentJobs
    private val MLDgetRecentJobs = MutableLiveData<GetRecentJobsRes>()



    /////////////////////////////////////////////////////////////////

    fun getJobDetailsById(
        context: Activity,
        accessToken: String?,
        jobId:Int
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobDetailsById(
                    accessToken.toString(),
                    jobId

                )
                    .enqueue(object : Callback<GetJobByJobIdResponse> {
                        override fun onResponse(
                            call: Call<GetJobByJobIdResponse>,
                            response: Response<GetJobByJobIdResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobDetailsById.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetJobByJobIdResponse>,
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


    fun getCandidateJobStatuses(requireContext: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .GetCandidateStatuses(
                        token.toString()
                    )
                    .enqueue(object : Callback<CandidateJobStatusRes> {
                        override fun onResponse(
                            call: Call<CandidateJobStatusRes>,
                            response: Response<CandidateJobStatusRes>
                        ) {
                            if (response.body() != null) {
                                MLDgetCandidateJobStatuses.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<CandidateJobStatusRes>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }
    }


    fun getCandidateStausKeyPipeline(requireContext: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .GetCandidateStatusKeyPipeline(
                        token.toString()
                    )
                    .enqueue(object : Callback<GetCandiStatuskeyPipeline> {
                        override fun onResponse(
                            call: Call<GetCandiStatuskeyPipeline>,
                            response: Response<GetCandiStatuskeyPipeline>
                        ) {
                            if (response.body() != null) {
                                MLDgetCandidateStatusKeyPipeline.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetCandiStatuskeyPipeline>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }
    }


    fun getRecentJobs(requireContext: Context, token: String?, searchName:String) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .GetRecentJobs(
                        token.toString(),
                        searchName
                    )
                    .enqueue(object : Callback<GetRecentJobsRes> {
                        override fun onResponse(
                            call: Call<GetRecentJobsRes>,
                            response: Response<GetRecentJobsRes>
                        ) {
                            if (response.body() != null) {
                                MLDgetRecentJobs.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetRecentJobsRes>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.d("exceptionrecent",ex.toString())
            }
        }
    }

}