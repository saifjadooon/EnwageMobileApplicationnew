package com.example.envagemobileapplication.Fragments.BottomSheet

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ConvertCandidateResponse.ConvertCandidateResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.BottomSheetJobOnlineApplicantsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSheetJobOnlineAplicants : BottomSheetDialogFragment() {
    lateinit var loader: Loader
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var binding: BottomSheetJobOnlineApplicantsBinding
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetJobOnlineApplicantsBinding.inflate(inflater, container, false)


        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        loader = Loader(requireContext())
        binding.convertToCandidate.setOnClickListener {
            showDialog()
        }
        binding.addToCandidateBank.setOnClickListener {
            showCandiDateBankDialog()
        }
        binding.remove.setOnClickListener {
            showDeleteCandiDateDialog()
        }
        return binding.root

    }

    private fun clickListeners() {

    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("")
        alertDialogBuilder.setMessage("Are you sure you want to convert this applicant to Job Candidate?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Confirm") { dialog, which ->
            // Handle the OK button click (if needed)


            convertToCandidateApi()
            // dialog.dismiss()
            // Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun showCandiDateBankDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("")
        alertDialogBuilder.setMessage("Are you sure you want to add this applicant to Candidates?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Confirm") { dialog, which ->
            // Handle the OK button click (if needed)


            converttoCandidateBank()
            // dialog.dismiss()
            // Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun showDeleteCandiDateDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("")
        alertDialogBuilder.setMessage("Are you sure you want to delete this application?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Confirm") { dialog, which ->
            // Handle the OK button click (if needed)

            deleteCandidate()

            // converttoCandidateBank()
            // dialog.dismiss()
            // Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun deleteCandidate() {
        loader.show()

        var applicantid = com.example.envagemobileapplication.Utils.Global.applicantid
        try {
            lifecycleScope.launch {
                try {
                    ApiUtils.getAPIService(requireContext()).deleteOnlineCandidate(
                        applicantid.toInt(),
                        token.toString(),
                    )
                        .enqueue(object : Callback<ConvertCandidateResponse> {
                            override fun onResponse(
                                call: Call<ConvertCandidateResponse>,
                                response: Response<ConvertCandidateResponse>
                            ) {
                                loader.hide()
                                if (response.body() != null) {
                                    viewModel.hidebottomSheet()
                                    loader.hide()
                                }
                            }

                            override fun onFailure(
                                call: Call<ConvertCandidateResponse>,
                                t: Throwable
                            ) {
                                loader.hide()
                                Log.i("exceptionddsfdsfds", t.toString())

                            }
                        })
                } catch (ex: java.lang.Exception) {
                    loader.hide()
                    Log.i("exceptionddsfdsfds", ex.toString())
                }
            }


        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }

    }


    private fun converttoCandidateBank() {
        loader.show()

        var applicantid = com.example.envagemobileapplication.Utils.Global.applicantid
        try {
            lifecycleScope.launch {
                try {
                    ApiUtils.getAPIService(requireContext()).converttoCandidateBank(
                        applicantid.toInt(),
                        token.toString(),
                    )
                        .enqueue(object : Callback<ConvertCandidateResponse> {
                            override fun onResponse(
                                call: Call<ConvertCandidateResponse>,
                                response: Response<ConvertCandidateResponse>
                            ) {
                                loader.hide()
                                if (response.body() != null) {
                                    viewModel.hidebottomSheet()
                                    loader.hide()
                                }
                            }

                            override fun onFailure(
                                call: Call<ConvertCandidateResponse>,
                                t: Throwable
                            ) {
                                loader.hide()
                                Log.i("exceptionddsfdsfds", t.toString())

                            }
                        })
                } catch (ex: java.lang.Exception) {
                    loader.hide()
                    Log.i("exceptionddsfdsfds", ex.toString())
                }
            }


        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }

    private fun convertToCandidateApi() {

        loader.show()

        var applicantid = com.example.envagemobileapplication.Utils.Global.applicantid
        try {
            lifecycleScope.launch {
                try {
                    ApiUtils.getAPIService(requireContext()).convertCandidate(
                        applicantid.toInt(),
                        token.toString(),
                    )
                        .enqueue(object : Callback<ConvertCandidateResponse> {
                            override fun onResponse(
                                call: Call<ConvertCandidateResponse>,
                                response: Response<ConvertCandidateResponse>
                            ) {
                                loader.hide()
                                if (response.body() != null) {
                                    viewModel.hidebottomSheet()
                                    loader.hide()

                                }
                            }

                            override fun onFailure(
                                call: Call<ConvertCandidateResponse>,
                                t: Throwable
                            ) {
                                loader.hide()
                                Log.i("exceptionddsfdsfds", t.toString())

                            }
                        })
                } catch (ex: java.lang.Exception) {
                    loader.hide()
                    Log.i("exceptionddsfdsfds", ex.toString())
                }
            }


        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }
}
