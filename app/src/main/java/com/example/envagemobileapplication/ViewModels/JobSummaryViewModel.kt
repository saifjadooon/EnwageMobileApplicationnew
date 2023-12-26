package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobSummaryViewModel : ViewModel() {

    val LDgetJobHeaderSummary: LiveData<GetJobHeaderSummary>
        get() = MLDgetJobHeaderSummary

    private val MLDgetJobHeaderSummary = MutableLiveData<GetJobHeaderSummary>()


    //=======>>>>>>>>>>><<<<<<<<<<<<<<<<<////


    fun getJobHeaderSummary(context: Activity, token: String, jobGuid: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobHeaderSummary(
                    token,
                    jobGuid!!
                )
                    .enqueue(object : Callback<GetJobHeaderSummary> {
                        override fun onResponse(
                            call: Call<GetJobHeaderSummary>,
                            response: Response<GetJobHeaderSummary>
                        ) {
                            if (response.body() != null) {

                                MLDgetJobHeaderSummary.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetJobHeaderSummary>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

}