package com.example.envagemobileapplication.Activities.HiredDetails.HiredDetailsFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentJobSalaryDetailsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobSalaryDetails : Fragment() {
    lateinit var loader: Loader
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: FragmentJobSalaryDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentJobSalaryDetailsBinding.inflate(inflater, container, false)
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        if (isAdded) {
            networkCalls()
        }


        return binding.root

    }

    private fun networkCalls() {
        getjobCandidates()


    }

    private fun getjobbyJobId() {
        try {
            var jobid = "1693"
            loader.show()
            ApiUtils.getAPIService(requireContext()).GetJobbyJobId(
                token, jobid
            )
                .enqueue(object : Callback<GetJobByJobIdResponse> {
                    override fun onResponse(
                        call: Call<GetJobByJobIdResponse>,
                        response: Response<GetJobByJobIdResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            global.getjobbyjoid = response.body()!!

                            if (response.body()!!.data.billingDetails.markup != null) {
                                binding.tvMarkupPercentage.setText(response.body()!!.data.billingDetails.markup.toString())
                            } else {
                                binding.tvMarkupPercentage.setText("Not Provided")
                            }
                            if (response.body()!!.data.billingDetails.minPayRate != null) {
                                binding.tvMinPayRate.setText(response.body()!!.data.billingDetails.minPayRate.toString())
                                binding.tvOTDTMinpayRate.setText(response.body()!!.data.billingDetails.minPayRate.toString())
                            } else {

                                binding.tvOTDTMinpayRate.setText("Not Provided")
                                binding.tvMinPayRate.setText("Not Provided")
                            }
                            if (response.body()!!.data.billingDetails.minBillRate != null) {
                                binding.tvMinBillRate.setText(response.body()!!.data.billingDetails.minBillRate.toString())
                            } else {
                                binding.tvMinBillRate.setText("Not Provided")
                            }
                            if (response.body()!!.data.billingDetails.maxPayRate != null) {
                                binding.tvMaxPayRate.setText(response.body()!!.data.billingDetails.maxPayRate.toString())
                            } else {
                                binding.tvMaxPayRate.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.maxBillRate != null) {
                                binding.tvMaxBillRate.setText(response.body()!!.data.billingDetails.maxBillRate.toString())
                            } else {
                                binding.tvMaxBillRate.setText("Not Provided")
                            }


                            if (response.body()!!.data.billingDetails.targetPayRate != null) {
                                binding.tvTargetPayRate.setText(response.body()!!.data.billingDetails.targetPayRate.toString())
                            } else {
                                binding.tvTargetPayRate.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.targetBillRate != null) {

                                binding.tvTargetBillRate.setText(response.body()!!.data.billingDetails.targetBillRate.toString())
                            } else {
                                binding.tvTargetBillRate.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.frequency != null) {

                                binding.tvFrequecny.setText(response.body()!!.data.billingDetails.frequency.toString())
                            } else {
                                binding.tvFrequecny.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.overtimeType != null) {

                                binding.tvOTRule.setText(response.body()!!.data.billingDetails.overtimeType.toString())
                            } else {
                                binding.tvOTRule.setText("Not Provided")
                            }


                            if (response.body()!!.data.billingDetails.overtimeMultiplier != null) {

                                binding.tvOtMultiplier.setText(response.body()!!.data.billingDetails.overtimeMultiplier.toString())
                            } else {
                                binding.tvOtMultiplier.setText("Not Provided")
                            }


                            if (response.body()!!.data.billingDetails.overtimePayRate != null) {


                                binding.tvOtPayRate.setText(response.body()!!.data.billingDetails.overtimePayRate.toString())
                            } else {
                                binding.tvOtPayRate.setText("Not Provided")
                            }


                            if (response.body()!!.data.billingDetails.overtimeBillRate != null) {
                                binding.tvoTBillRate.setText(response.body()!!.data.billingDetails.overtimeBillRate.toString())
                            } else {
                                binding.tvoTBillRate.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.doubletimeMultiplier != null) {
                                binding.tvDtMultiplier.setText(response.body()!!.data.billingDetails.doubletimeMultiplier.toString())

                            } else {
                                binding.tvDtMultiplier.setText("Not Provided")
                            }

                            if (response.body()!!.data.billingDetails.doubletimePayRate != null) {
                                binding.tvDtPayRate.setText(response.body()!!.data.billingDetails.doubletimePayRate.toString())

                            } else {
                                binding.tvDtPayRate.setText("Not Provided")
                            }


                            if (response.body()!!.data.billingDetails.doubletimeBillRate != null) {
                                binding.tvDTBillRate.setText(response.body()!!.data.billingDetails.doubletimeBillRate.toString())


                            } else {
                                binding.tvDTBillRate.setText("Not Provided")
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetJobByJobIdResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }

    private fun getjobCandidates() {
        try {

            var jobid = "9e06c39f-d789-4bd1-88a4-9ce91161425e"
            loader.show()
            ApiUtils.getAPIService(requireContext()).GetCandidteJobBy(
                token, jobid
            )
                .enqueue(object : Callback<CandidateJobGetJobResponse> {
                    override fun onResponse(
                        call: Call<CandidateJobGetJobResponse>,
                        response: Response<CandidateJobGetJobResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            global.getcandidatjobResp = response.body()!!
                            getjobbyJobId()
                        }
                    }

                    override fun onFailure(call: Call<CandidateJobGetJobResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }

}