package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.envagemobileapplication.Adapters.BottomSheetFilterAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientResponse.AddClientResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.FragmentBottomSheerAddClientBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSheerAddCLientFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: BottomSheetFilterAdapter

    lateinit var binding: FragmentBottomSheerAddClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheerAddClientBinding.inflate(inflater, container, false)
        clickListeners()
        return binding.root

    }

    private fun clickListeners() {
        binding.btnAddClient.setOnClickListener {
           // AddClientApi()
        }

        binding.ivCamera.setOnClickListener {

            val bottomSheetFragment = BottomSheetSelectCameraGallery()
             bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

 /*   private fun AddClientApi() {
        var name = "testtttt"
        var websiteurl = "www.url.com"
        var description = stringToBinary("testing userrr")
        var profilePic = stringToBinary("dfdsfdsfdsfdsfdsfdsfdsf")
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()
        try {
            ApiUtils.getAPIService(requireContext()).AddClientData(
                token.toString(), name, websiteurl, description, profilePic
            )
                .enqueue(object : Callback<AddClientResponse> {
                    override fun onResponse(
                        call: Call<AddClientResponse>,
                        response: Response<AddClientResponse>
                    ) {
                        if (response.body() != null) {


                            // var tokenManager: TokenManager = TokenManager(requireContext())


                        }
                    }

                    override fun onFailure(call: Call<AddClientResponse>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }

    }*/

    fun stringToBinary(input: String): String {
        val binaryStringBuilder = StringBuilder()

        for (char in input) {
            val binaryString = Integer.toBinaryString(char.toInt())
            val paddedBinaryString =
                binaryString.padStart(8, '0') // Pad with zeros to make it 8 bits
            binaryStringBuilder.append(paddedBinaryString)
        }

        return binaryStringBuilder.toString()
    }

    fun main() {
        val inputString = "Hello"
        val binaryString = stringToBinary(inputString)
        println(binaryString)
    }

}