package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Activities.MainActivity
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryF
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryJobsF
import com.example.envagemobileapplication.Fragments.Dashboard.ClientsF
import com.example.envagemobileapplication.Fragments.Dashboard.JobsF
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionCandidates
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionEmployees
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.RequestModels.sortDirection
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.GetCandidatesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.GetClients
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.GetCompanyOnboardingStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.GetEmployeesRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.GetJobsStatusesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp.GetLoggedinUserDetails
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel : ViewModel() {
    var loader: Loader = Loader(Constants.context as Context)
    lateinit var Clientsbundle: Bundle
    val LDLogoutUser: LiveData<String>
        get() = LogoutUserMLD

    private val LogoutUserMLD = MutableLiveData<String>()


    val LDgetClients: LiveData<GetClients>
        get() = MLDgetClients

    private val MLDgetClients = MutableLiveData<GetClients>()

    val LDgetJobs: LiveData<GetJobsResponse>
        get() = MLDGetJobs

    private val MLDGetJobs = MutableLiveData<GetJobsResponse>()

    val LDgetCandidates: LiveData<GetCandidatesResponse>
        get() = MLDGetCandidates

    private val MLDGetCandidates = MutableLiveData<GetCandidatesResponse>()

    val LDgetCompanyOnBoardingStatus: LiveData<GetCompanyOnboardingStatusResponse>
        get() = MLDgetCompanyOnBoardingStatus

    private val MLDgetCompanyOnBoardingStatus =
        MutableLiveData<GetCompanyOnboardingStatusResponse>()

    val LDgetJobStatusResponse: LiveData<GetJobsStatusesResponse>
        get() = MLDgetJobStatusResponse

    private val MLDgetJobStatusResponse =
        MutableLiveData<GetJobsStatusesResponse>()

    val LDgetLoggedinClientDetail: LiveData<GetLoggedinUserDetails>
        get() = MLDgetLoggedinClientDetail
    private val MLDgetLoggedinClientDetail =
        MutableLiveData<GetLoggedinUserDetails>()

    val LDgetEmployees: LiveData<GetEmployeesRes>
        get() = MLDGetEmployees

    private val MLDGetEmployees = MutableLiveData<GetEmployeesRes>()

    //--------------->>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<///
    fun logoutUser(tokenManager: TokenManager) {
        tokenManager.clearTokens()
        LogoutUserMLD.postValue("TokenCleared")
    }

    fun getEmployees(context: Context, accessToken: String?, model: SortDirectionEmployees) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getEmployees(
                    model,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetEmployeesRes> {
                        override fun onResponse(
                            call: Call<GetEmployeesRes>,
                            response: Response<GetEmployeesRes>
                        ) {
                            if (response.body() != null) {

                                MLDGetEmployees.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetEmployeesRes>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getClients(
        context: Activity,
        accessToken: String?,
        sortDirection: sortDirection,
        isfromCompnyStatus: Any?,
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getClients(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetClients> {
                        override fun onResponse(
                            call: Call<GetClients>,
                            response: Response<GetClients>
                        ) {
                            if (response.body() != null) {

                                if (isfromCompnyStatus == true) {
                                    /*       com.example.envagemobileapplication.Utils.Global.hideProgressDialog()*/
                                    val clientDataList: ArrayList<Record> = ArrayList()
                                    for (i in 0 until response.body()!!.data.records.size) {
                                        clientDataList.add(response.body()!!.data.records.get(i))
                                    }

                                    Constants.ClientList = clientDataList
                                    //ClientsF.adapter.notifyDataSetChanged()
                                    Clientsbundle = Bundle()
                                    //   Clientsbundle.putSerializable("clientsListData", clientDataList)

                                    if (Constants.isOnboardingStatusUpdatedfromClientSummaryList == true) {
                                        var bundle: Bundle = Bundle()
                                        replaceFragmentclientsSummary(
                                            ClientSummaryF(),
                                            bundle
                                        )

                                        Constants.isOnboardingStatusUpdatedfromClientSummaryList =
                                            false

                                    } else {
                                        try {
                                            loader.hide()
                                            replaceFragment(ClientsF(), Clientsbundle)

                                        } catch (e: Exception) {
                                        }

                                    }

                                } else {
                                    MLDgetClients.postValue(response.body())
                                }

                            }
                        }

                        override fun onFailure(call: Call<GetClients>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getallOnBoardingStatuses(
        context: MainActivity,
        accessToken: String?
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetCompanyOnboardingStatusResponse(

                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetCompanyOnboardingStatusResponse> {
                        override fun onResponse(
                            call: Call<GetCompanyOnboardingStatusResponse>,
                            response: Response<GetCompanyOnboardingStatusResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetCompanyOnBoardingStatus.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetCompanyOnboardingStatusResponse>,
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

    fun replaceFragment(fragment: Fragment, bundle: Bundle) {

        val fragmentManager: FragmentManager = Constants.cfm

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, fragment)

        fragment.arguments = bundle

        transaction.commit()
        loader.hide()

    }

    fun replaceFragmentclientsSummary(fragment: Fragment, bundle: Bundle) {

        val fragmentManager: FragmentManager = Constants.cfm

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.nav_client_summary, fragment)

        fragment.arguments = bundle

        transaction.commit()
    }

    fun getLoggedInUserDetails(
        context: MainActivity,
        accessToken: String?
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetLoggedInUserDetails(

                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetLoggedinUserDetails> {
                        override fun onResponse(
                            call: Call<GetLoggedinUserDetails>,
                            response: Response<GetLoggedinUserDetails>
                        ) {
                            if (response.body() != null) {
                                MLDgetLoggedinClientDetail.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetLoggedinUserDetails>,
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

    fun getJobs(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionJobs,
        isfromJobBottomSheet: Boolean,


        ) {

        loader.show()
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
                            loader.hide()
                            if (response.body() != null) {

                                if (isfromJobBottomSheet == true) {
                                    if (response.body()?.data != null) {

                                        var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
                                            ArrayList()

                                        for (i in 0 until response.body()?.data?.records!!.size) {


                                            jobsList.add(response.body()?.data?.records!!.get(i))
                                        }
                                        Constants.JobsList = jobsList

                                        var bundle: Bundle = Bundle()
                                        if (Constants.isfromClientsummaryJobsList == true) {


                                            replaceFragmentclientsSummary(
                                                ClientSummaryJobsF(),
                                                bundle
                                            )

                                        } else {
                                            replaceFragment(JobsF(), bundle)
                                        }

                                    }

                                } else {
                                    MLDGetJobs.postValue(response.body())
                                }


                            } else {
                                loader.hide()
                            }
                        }

                        override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                            loader.hide()
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                loader.hide()
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getallJobStatuses(
        context: MainActivity,
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

    fun getCandidates(context: Activity, accessToken: String?, model: SortDirectionCandidates) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getCandidates(
                    model,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetCandidatesResponse> {
                        override fun onResponse(
                            call: Call<GetCandidatesResponse>,
                            response: Response<GetCandidatesResponse>
                        ) {
                            if (response.body() != null) {

                                MLDGetCandidates.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetCandidatesResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

}