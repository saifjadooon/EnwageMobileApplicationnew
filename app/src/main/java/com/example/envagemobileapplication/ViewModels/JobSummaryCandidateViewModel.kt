package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionCandidateCandidate
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionGetOnlineCandidate
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandiStatuskeyPipeline.GetCandiStatuskeyPipeline
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.GetJobCandidates
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.OnlineApplicantsResponse
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobSummaryCandidateViewModel : ViewModel() {


    val LDgetCandidateStatusKeyPipeline: LiveData<GetCandiStatuskeyPipeline> get() = MLDgetCandidateStatusKeyPipeline
    private val MLDgetCandidateStatusKeyPipeline = MutableLiveData<GetCandiStatuskeyPipeline>()


    val LDgetOnlineCandidates: LiveData<OnlineApplicantsResponse>
        get() = MLDgetOnlineCandidates

    private val MLDgetOnlineCandidates = MutableLiveData<OnlineApplicantsResponse>()

    val LDgetJObCandidates: LiveData<GetJobCandidates>
        get() = MLDgetJObCandidates

    private val MLDgetJObCandidates = MutableLiveData<GetJobCandidates>()

    val LDgetallSkills: LiveData<GetAllSkillsResponse>
        get() = MLDgetallSkills

    private val MLDgetallSkills = MutableLiveData<GetAllSkillsResponse>()

    val LDgetJobHeaderSummary: LiveData<GetJobHeaderSummary>
        get() = MLDgetJobHeaderSummary

    private val MLDgetJobHeaderSummary = MutableLiveData<GetJobHeaderSummary>()

    val LDviewBottomSheet: LiveData<String>
        get() = MLDviewBottomSheet

    private val MLDviewBottomSheet = MutableLiveData<String>()

    val LDshowJobCandidateKebabmenuBottomSheet: LiveData<String>
        get() = MLDshowJobCandidateKebabmenuBottomSheet

    private val MLDshowJobCandidateKebabmenuBottomSheet = MutableLiveData<String>()

    val LDhideBottomSheet: LiveData<String>
        get() = MLDhideBottomSheet

    private val MLDhideBottomSheet = MutableLiveData<String>()

    val LDhideSummaryDetailBottomSheet: LiveData<String>
        get() = MLDhideSummaryDetailBottomSheet

    private val MLDhideSummaryDetailBottomSheet = MutableLiveData<String>()

    val LDviewCandidateDetailBottomSheet: LiveData<String>
        get() = MLDviewCandidateDetailBottomSheet

    private val MLDviewCandidateDetailBottomSheet = MutableLiveData<String>()


    /////===============>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<===============//////////////

    fun getOnlineApplicants(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionGetOnlineCandidate
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getOnlineApplicants(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<OnlineApplicantsResponse> {
                        override fun onResponse(
                            call: Call<OnlineApplicantsResponse>,
                            response: Response<OnlineApplicantsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDgetOnlineCandidates.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<OnlineApplicantsResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun showBottomSheet(applicantId: Int) {
        MLDviewBottomSheet.postValue(applicantId.toString())
    }

    fun showJobCandidateKebabmenuBottomSheet(b: Boolean) {
        MLDshowJobCandidateKebabmenuBottomSheet.postValue(b.toString())
    }

    fun showClientDetailBottomSheet(applicantId: Int) {
        MLDviewCandidateDetailBottomSheet.postValue(applicantId.toString())
    }

    fun getAllSkills(context: Activity, token: String, applicantID: Int?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getAllSkills(
                    applicantID!!,
                    token,
                )
                    .enqueue(object : Callback<GetAllSkillsResponse> {
                        override fun onResponse(
                            call: Call<GetAllSkillsResponse>,
                            response: Response<GetAllSkillsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDgetallSkills.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetAllSkillsResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

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

    fun hidebottomSheet() {
        MLDhideBottomSheet.postValue("true")
    }

    fun hideSummaryDetailbottomSheet() {
        MLDhideSummaryDetailBottomSheet.postValue("true")
    }


    fun getJobCandidates(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionCandidateCandidate
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getjobcandidatess(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobCandidates> {
                        override fun onResponse(
                            call: Call<GetJobCandidates>,
                            response: Response<GetJobCandidates>
                        ) {
                            if (response.body() != null) {

                                MLDgetJObCandidates.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetJobCandidates>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
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

                        override fun onFailure(
                            call: Call<GetCandiStatuskeyPipeline>,
                            t: Throwable
                        ) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }
    }


}