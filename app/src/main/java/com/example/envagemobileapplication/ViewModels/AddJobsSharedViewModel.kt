package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.ClientInfo
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.PaygroupRequestModel
import com.example.envagemobileapplication.Models.RequestModels.getCustomTemplateRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddJobResponse.AddJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse.CustomTemplateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.GetIndustryListResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.PayGroupResponse
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.FragmentAddjobSalaryDetailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddJobsSharedViewModel : ViewModel() {
    var estimatedhours: String = ""
    var jobstatius: String = ""
    var jobtype: String = ""
    var noofWorkingDays: String = ""
    var weekdaysConcatinated: String = ""
    var headcount: String = ""
    var endDate: String = ""
    var startdate: String = ""
    var arraylistSelectedSkills: ArrayList<JobSkill> = ArrayList()
    var country: String = ""
    var location: String = ""
    var state: String = ""
    var city: String = ""
    var zipcode: String = ""
    var address2: String? = ""
    var addrress1: String? = ""
    var doubletimetype: String = ""
    var ovetimetype: String = ""
    var frequency: String = ""
    var doubletimebillrate: String = ""
    var doubletimepayrate: String = ""
    var doubletimeMarkupPercentage: String = "2.0"
    var doubletimeMultiplier: String = ""
    var overtimebillrate: String = ""
    var ovetimepayrate: String = ""
    var overtimemarkuppercentage: String = ""
    var overtimemultiplier: String = "1.5"
    var targetbillrate: String = ""
    var targetPayrate: String = ""
    var maxbillrate: String = ""
    var maxpayrate: String = ""
    var minbillrate: String = ""
    var minpayrate: String = ""
    var markuppercentage: String? = ""
    var arraylistskills: ArrayList<JobSkill> = ArrayList()
    var skillCountText: String = ""
    var selectedPositions: BooleanArray = booleanArrayOf()
    var currency: String = ""
    var isStartDateFieldEnabled: Boolean = false
    var isEndDateFieldEnabled: Boolean = false

    //login user
    val LDcustomTemplate: LiveData<CustomTemplateResponse>
        get() = MLDcustomTemplate
    private val MLDcustomTemplate = MutableLiveData<CustomTemplateResponse>()

    val LDpayGroup: LiveData<PayGroupResponse>
        get() = MLDpayGroup
    private val MLDpayGroup = MutableLiveData<PayGroupResponse>()

    val LDgetIndustry: LiveData<GetIndustryListResponse>
        get() = MLDgetIndustry
    private val MLDgetIndustry = MutableLiveData<GetIndustryListResponse>()

    val LDdismissBottomsheet: LiveData<ClientInfo>
        get() = MLDdismissBottomsheet
    private val MLDdismissBottomsheet = MutableLiveData<ClientInfo>()

    val LDAddJob: LiveData<AddJobResponse>
        get() = MLDAddJob
    private val MLDAddJob = MutableLiveData<AddJobResponse>()

///=======>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<===============================\\\

    fun getCustomTemplates(
        clientid: getCustomTemplateRequestModel,
        token: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getcustomTemplate(
                    token, clientid
                )
                    .enqueue(object : Callback<CustomTemplateResponse> {
                        override fun onResponse(
                            call: Call<CustomTemplateResponse>,
                            response: Response<CustomTemplateResponse>
                        ) {
                            if (response.body() != null) {
                                MLDcustomTemplate.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<CustomTemplateResponse>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun searchPayGroupApi(context: Context, token: String, model: PaygroupRequestModel) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).searchPaygroup(
                    token, model
                )
                    .enqueue(object : Callback<PayGroupResponse> {
                        override fun onResponse(
                            call: Call<PayGroupResponse>,
                            response: Response<PayGroupResponse>
                        ) {
                            if (response.body() != null) {
                                MLDpayGroup.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<PayGroupResponse>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getindustry(context: Context, token: String) {


        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getIndustry(
                    token
                )
                    .enqueue(object : Callback<GetIndustryListResponse> {
                        override fun onResponse(
                            call: Call<GetIndustryListResponse>,
                            response: Response<GetIndustryListResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetIndustry.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetIndustryListResponse>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun dismissBottomsheet(selectedClientname: ClientInfo) {
        MLDdismissBottomsheet.postValue(selectedClientname)
    }

    fun addJobApi(
        binding: FragmentAddjobSalaryDetailBinding,
        context: Context,
        token: String,
        jopbDescriptionfinal: MultipartBody.Part,
        positionNamefinal: MultipartBody.Part,
        clientIdfinal: MultipartBody.Part,
        payrollPayGroupIdfinal: MultipartBody.Part,
        jobIdfinal: MultipartBody.Part,
        industryIdfinal: MultipartBody.Part,
        jobNaturefinal: MultipartBody.Part,
        address1final: MultipartBody.Part,
        address2final: MultipartBody.Part,
        countryfinal: MultipartBody.Part,
        zipcodefinal: MultipartBody.Part,
        cityfinal: MultipartBody.Part,
        statefinal: MultipartBody.Part,
        locationfinal: MultipartBody.Part,
        headcountfinal: MultipartBody.Part,
        jobTypefinal: MultipartBody.Part,
        startDatefinal: MultipartBody.Part,
        endDatefinal: MultipartBody.Part,
        currencyfinal: MultipartBody.Part,
        minimumSalaryfinal: MultipartBody.Part,
        maximumSalaryfinal: MultipartBody.Part,
        workingDaysNofinal: MultipartBody.Part,
        estimatedHoursfinal: MultipartBody.Part,
        workingDaysfinal: MultipartBody.Part,
        jobStatusIdfinal: MultipartBody.Part,
        jobSkillsfinal: MultipartBody.Part,
        experienceRequired: MultipartBody.Part,
        useTemplate: MultipartBody.Part,
        jobTemplateId: MultipartBody.Part,
        markupfinal: MultipartBody.Part,
        minPayRatefinal: MultipartBody.Part,
        minBillRatefinal: MultipartBody.Part,
        maxPayRatefinal: MultipartBody.Part,
        maxBillRatefinal: MultipartBody.Part,
        targetPayRatefinal: MultipartBody.Part,
        targetBillRatefinal: MultipartBody.Part,
        overTimeMultiplierfinal: MultipartBody.Part,
        overTimeTypefinall: MultipartBody.Part,
        overTimeMarkupfinal: MultipartBody.Part,
        overTimePayRatefinal: MultipartBody.Part,
        overTimeBillRatefinal: MultipartBody.Part,
        doubleTimeMultiplierfinal: MultipartBody.Part,
        doubleTimeTypefinal: MultipartBody.Part,
        doubleTimeMarkupfinal: MultipartBody.Part,
        doubleTimePayRatefinal: MultipartBody.Part,
        doubleTimeBillRatefinal: MultipartBody.Part,
        frequencyfinal: MultipartBody.Part,
        applicationFormIdfinal: MultipartBody.Part,
        showSalaryfinal: MultipartBody.Part,
        showNaturefinal: MultipartBody.Part,
        showClientfinal: MultipartBody.Part,
        showIndustryfinal: MultipartBody.Part,
        showAddressfinal: MultipartBody.Part,
        showTypefinal: MultipartBody.Part,
        showSkillsfinal: MultipartBody.Part,
        showShiftfinal: MultipartBody.Part,
        publishfinal: MultipartBody.Part
    ) {

        viewModelScope.launch {
            ApiUtils.getAPIService(context).AddJob(
                token,
                jopbDescriptionfinal,
                positionNamefinal,
                clientIdfinal,
                payrollPayGroupIdfinal,
                jobIdfinal,
                industryIdfinal,
                jobNaturefinal,
                address1final,
                address2final,
                countryfinal,
                zipcodefinal,
                cityfinal,
                statefinal,
                locationfinal,
                headcountfinal,
                jobTypefinal,
                startDatefinal,
                endDatefinal,
                currencyfinal,
                minimumSalaryfinal,
                maximumSalaryfinal,
                workingDaysNofinal,
                estimatedHoursfinal,
                workingDaysfinal,
                jobStatusIdfinal,
                jobSkillsfinal,
                experienceRequired,
                useTemplate,
                jobTemplateId,
                markupfinal,
                minPayRatefinal,
                minBillRatefinal,
                maxPayRatefinal,
                maxBillRatefinal,
                targetPayRatefinal,
                targetBillRatefinal,
                overTimeMultiplierfinal,
                overTimeTypefinall,
                overTimeMarkupfinal,
                overTimePayRatefinal,
                overTimeBillRatefinal,
                doubleTimeMultiplierfinal,
                doubleTimeTypefinal,
                doubleTimeMarkupfinal,
                doubleTimePayRatefinal,
                doubleTimeBillRatefinal,
                frequencyfinal,
                applicationFormIdfinal,
                showSalaryfinal,
                showNaturefinal,
                showClientfinal,
                showIndustryfinal,
                showAddressfinal,
                showTypefinal,
                showSkillsfinal,
                showShiftfinal,
                publishfinal
            )
                .enqueue(object : Callback<AddJobResponse> {
                    override fun onResponse(
                        call: Call<AddJobResponse>,
                        response: Response<AddJobResponse>
                    ) {

                        if (response.body() != null) {


                            MLDAddJob.postValue(response.body())

                        } else {

                            binding.btnnext.isEnabled = true
                            val rootView = binding.root
                            val message = "Invalid parameters"
                            val duration = Snackbar.LENGTH_SHORT

                            val snackbar = Snackbar.make(rootView, message, duration)
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.red
                                )
                            )
                            snackbar.show()
                            Log.i("errormsg", response.message())
                        }
                    }

                    override fun onFailure(call: Call<AddJobResponse>, t: Throwable) {

                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        }


    }


}