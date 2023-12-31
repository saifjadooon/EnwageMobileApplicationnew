package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.GetCustomJobTemplateReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.GetOfferLetterTemplates
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getEmailTemplateResponse.GetEmailTemplateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Getjobbyid
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendOfferLetterViewModel : ViewModel() {
    val LDofferLetterTemplate: LiveData<GetOfferLetterTemplates>
        get() = MLDofferLetterTemplate
    private val MLDofferLetterTemplate = MutableLiveData<GetOfferLetterTemplates>()


    val LDEmailTemplate: LiveData<GetEmailTemplateResponse>
        get() = MLDEmailTemplate
    private val MLDEmailTemplate = MutableLiveData<GetEmailTemplateResponse>()


    val LDGetJobByJobId: LiveData<Getjobbyid>
        get() = MLDGetJobByJobId
    private val MLDGetJobByJobId = MutableLiveData<Getjobbyid>()


    //=============>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<///////////


    fun getCustomTemplates(context: Context, token: String?, model: GetCustomJobTemplateReqModel) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getofferLetterTemplates(
                    token.toString(),
                    model,

                    )
                    .enqueue(object : Callback<GetOfferLetterTemplates> {
                        override fun onResponse(
                            call: Call<GetOfferLetterTemplates>,
                            response: Response<GetOfferLetterTemplates>
                        ) {
                            if (response.body() != null) {

                                MLDofferLetterTemplate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetOfferLetterTemplates>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }


    fun getEmailTemplates(context: Context, token: String?) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getEmailTemplates(
                    token.toString()
                )
                    .enqueue(object : Callback<GetEmailTemplateResponse> {
                        override fun onResponse(
                            call: Call<GetEmailTemplateResponse>,
                            response: Response<GetEmailTemplateResponse>
                        ) {
                            if (response.body() != null) {

                                MLDEmailTemplate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetEmailTemplateResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getjobbyJobid(context: Context, token: String?, jobguid: String) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobbyId(
                    token.toString(), jobguid
                )
                    .enqueue(object : Callback<Getjobbyid> {
                        override fun onResponse(
                            call: Call<Getjobbyid>,
                            response: Response<Getjobbyid>
                        ) {
                            if (response.body() != null) {

                                MLDGetJobByJobId.postValue(response.body())

                            }
                        }
                        override fun onFailure(call: Call<Getjobbyid>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

}