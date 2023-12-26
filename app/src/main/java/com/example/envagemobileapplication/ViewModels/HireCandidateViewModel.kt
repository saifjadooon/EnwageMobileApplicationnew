package com.example.envagemobileapplication.ViewModels

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.UpdateCandidateStatusRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCandidateResponse.UpdateCandidateResponse
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HireCandidateViewModel : ViewModel() {
    val LDupdateCandidate: LiveData<UpdateCandidateResponse>
        get() = MLDupdateCandidate

    private val MLDupdateCandidate = MutableLiveData<UpdateCandidateResponse>()
    fun updateCandidateStatusApi(
        context: FragmentActivity,
        model: UpdateCandidateStatusRequestModel,
        token: String?
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).UpdateCandidateStatus(
                    model,
                    token.toString(),
                )
                    .enqueue(object : Callback<UpdateCandidateResponse> {
                        override fun onResponse(
                            call: Call<UpdateCandidateResponse>,
                            response: Response<UpdateCandidateResponse>
                        ) {

                            if (response.body() != null) {

                                MLDupdateCandidate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<UpdateCandidateResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }
}