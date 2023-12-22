package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyDomainResponse.GetCompanyDomainResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRights.GetRigths
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.TokenResponsee.Example
import com.example.envagemobileapplication.Oauth.OAuthClient
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.SharedPreferences.SSOSharedPreferences
import com.example.envagemobileapplication.Utils.Constants
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SharedLoginViewModel : ViewModel() {


    //login user
    val LDloginUser: LiveData<String>
        get() = LoginuserMLD
    private val LoginuserMLD = MutableLiveData<String>()

    //login user
    val LDLoginuserForSplashScreen: LiveData<String>
        get() = LoginuserMLDForSplashScreen
    private val LoginuserMLDForSplashScreen = MutableLiveData<String>()


    //company domain name
    val LDcompanyDomainName: LiveData<GetCompanyDomainResponse>
        get() = MLDcompanyDomainName
    private val MLDcompanyDomainName = MutableLiveData<GetCompanyDomainResponse>()

    //roles and rights
    val LDgetRights: LiveData<GetRigths>
        get() = MLDgetRights
    private val MLDgetRights = MutableLiveData<GetRigths>()


    fun loginUser(username: String, password: String, tokenManager: TokenManager) {
        viewModelScope.launch {
            try {
                val call = OAuthClient.getToken(username, password)
                call.enqueue(object : Callback<Example> {
                    override fun onResponse(call: Call<Example>, response: Response<Example>) {
                        if (response.isSuccessful) {
                            val tokenResponse = response.body()
                            val accessToken = tokenResponse?.accessToken
                            val refreshToken = tokenResponse?.refreshToken
                            var username = username
                            var password = password
                            Constants.token = accessToken
                            tokenManager.saveAccessToken(accessToken.toString())
                            tokenManager.saveRefreshToken(refreshToken.toString())
                            tokenManager.saveUsername(username)
                            tokenManager.savePassword(password)

                            LoginuserMLD.postValue("UserLoggedinSuccesfully")

                        } else {
                            LoginuserMLD.postValue("UserLoggedinFailed")

                        }
                    }

                    override fun onFailure(call: Call<Example>, t: Throwable) {
                        LoginuserMLD.postValue("UserLoggedinFailed")
                    }
                })
            } catch (e: Exception) {
            }
        }


    }

    fun getCompanybyDomainName(domain: String, companyPrefs: SSOSharedPreferences) {
        viewModelScope.launch {
            try {
                val call = OAuthClient.getCompanyDomainName(domain)
                call.enqueue(object : Callback<GetCompanyDomainResponse> {
                    override fun onResponse(
                        call: Call<GetCompanyDomainResponse>,
                        response: Response<GetCompanyDomainResponse>
                    ) {
                        if (response.isSuccessful) {
                            val response = response.body()
                            val companyId = response?.response?.companyId
                            val companyLogo = response?.response?.companyLogo
                            val companyFavIcon = response?.response?.companyFavIcon
                            val secondaryColor = response?.response?.secondaryColor
                            val primaryColor = response?.response?.primaryColor
                            val companyDomain = response?.response?.companyDomain

                            companyPrefs.saveCompanyData(
                                companyId.toString(),
                                companyLogo.toString(),
                                companyFavIcon.toString(),
                                secondaryColor.toString(),
                                primaryColor.toString(),
                                companyDomain.toString()
                            )

                            val savedCompanyId = companyPrefs.getCompanyId()

                            Log.i("companyid", savedCompanyId)

                            MLDcompanyDomainName.postValue(response)

                        } else {

                            LoginuserMLD.postValue("UserLoggedinFailed")

                        }
                    }

                    override fun onFailure(call: Call<GetCompanyDomainResponse>, t: Throwable) {
                        LoginuserMLD.postValue("UserLoggedinFailed")
                    }
                })
            } catch (e: Exception) {
            }
        }

    }

    fun getRights(requireContext: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .getRights(
                        token.toString()
                    )
                    .enqueue(object : Callback<GetRigths> {
                        override fun onResponse(
                            call: Call<GetRigths>,
                            response: Response<GetRigths>
                        ) {
                            if (response.body() != null) {
                                MLDgetRights.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetRigths>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }
    }

    fun loginUserForSplashScreen(username: String, password: String, tokenManager: TokenManager) {

        viewModelScope.launch {
            try {
                val call = OAuthClient.getToken(username, password)
                call.enqueue(object : Callback<Example> {
                    override fun onResponse(call: Call<Example>, response: Response<Example>) {
                        if (response.isSuccessful) {
                            val tokenResponse = response.body()
                            val accessToken = tokenResponse?.accessToken
                            val refreshToken = tokenResponse?.refreshToken
                            var username = username
                            var password = password
                            Constants.token = accessToken
                            tokenManager.saveAccessToken(accessToken.toString())
                            tokenManager.saveRefreshToken(refreshToken.toString())
                            tokenManager.saveUsername(username)
                            tokenManager.savePassword(password)


                            LoginuserMLDForSplashScreen.postValue("UserLoggedinSuccesfully")

                        } else {
                            LoginuserMLDForSplashScreen.postValue("UserLoggedinFailed")

                        }
                    }

                    override fun onFailure(call: Call<Example>, t: Throwable) {
                        LoginuserMLDForSplashScreen.postValue("UserLoggedinFailed")
                    }
                })
            } catch (e: Exception) {
            }
        }
    }


}
