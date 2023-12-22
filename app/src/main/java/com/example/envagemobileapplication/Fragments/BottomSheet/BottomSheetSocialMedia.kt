package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.SocialMediaAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientSocialMedium
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.databinding.BottomSheetSocialMediaBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetSocialMedia : BottomSheetDialogFragment() {
    lateinit var socialMediaList: ArrayList<ClientSocialMedium>
    lateinit var adapter: SocialMediaAdapter
    lateinit var binding: BottomSheetSocialMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetSocialMediaBinding.inflate(inflater, container, false)
        getClientHeaderSummaryforsocialmedia()

        binding.ivClose.setOnClickListener {

            dismiss()
        }
        binding.ivDone.setOnClickListener {

            dismiss()
        }
        return binding.root
    }

    private fun setupSocialMediaAdapter() {
        binding!!.rvSocialMedia.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            context
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvSocialMedia.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = SocialMediaAdapter(
            requireContext(),
            Constants.socialmedialist
        )
        binding!!.rvSocialMedia.adapter = adapter
    }

    private fun getClientHeaderSummaryforsocialmedia() {
        //  loader.show()
        socialMediaList = ArrayList()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId =
            Constants.clientid // Replace with the actual client ID

        try {
            ApiUtils.getAPIService(requireContext()).GetClientHeaderSummary(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {

                        if (response != null) {
                            Constants.clientSummaryResp = response.body()!!
                            try {
                                for (i in 0 until response.body()!!.data.clientSocialMedia.size) {
                                    socialMediaList.add(
                                        response.body()!!.data.clientSocialMedia.get(
                                            i
                                        )
                                    )
                                }
                            } catch (e: Exception) {
                            }
                            Constants.socialmedialist = socialMediaList
                            setupSocialMediaAdapter()
                            //loader.hide()
                        }

                    }

                    override fun onFailure(call: Call<ClientHeaderSummaryResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Toast.makeText(
                requireContext(), ex.toString(), Toast.LENGTH_LONG
            ).show()
        }

    }
}