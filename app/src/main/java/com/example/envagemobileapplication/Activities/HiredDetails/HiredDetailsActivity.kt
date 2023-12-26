package com.example.envagemobileapplication.Activities.HiredDetails

import BaseActivity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.HiredDetails.HiredDetailsFragments.CandidateSalaryDetails
import com.example.envagemobileapplication.Activities.HiredDetails.HiredDetailsFragments.JobSalaryDetails
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityHiredDetailsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HiredDetailsActivity : BaseActivity() {
    var global = com.example.envagemobileapplication.Utils.Global
    private var selectedItem: View? = null
    lateinit var binding: ActivityHiredDetailsBinding
    lateinit var loader: Loader
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiredDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        clicklisteners()
        //   networkCalls()
        setupHorizontalScrollView()


    }

    override fun onBackPressed() {
        showDialog()
        super.onBackPressed()
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            finish()// Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun networkCalls() {
        getjobCandidates()
    }

    private fun getjobbyJobId() {
        try {

            var jobid = "1841"
            loader.show()
            ApiUtils.getAPIService(this).GetJobbyJobId(
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


            var jobid = "054ce4cb-71b1-4fb0-9211-77d03227ab1e"
            loader.show()
            ApiUtils.getAPIService(this).GetCandidteJobBy(
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

    private fun clicklisteners() {
        binding.ivCross.setOnClickListener {
            showDialog()
        }
    }

    private fun initViews() {

        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken().toString()

    }


    private fun setupHorizontalScrollView() {


        var itemList: ArrayList<String> = ArrayList()
        itemList.add("Job Salary Detail")
        itemList.add("Salary Details")

        for (itemText in itemList) {
            val itemView = layoutInflater.inflate(R.layout.hsv_candidates_item, null)

            val textView = itemView.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Job Salary Detail") {
                // Set the background for "Profile summary" by default
                itemView.setBackgroundResource(R.drawable.ic_bg_underline)
                selectedItem = itemView
            }

            textView.text = itemText

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    // Reset the background of the previously selected item
                    selectedItem?.setBackgroundResource(R.drawable.btn_white_radius)

                    itemView.setBackgroundResource(R.drawable.ic_bg_underline)
                    selectedItem = itemView

                    val clickedText = itemText

                    val left = itemView.left

                    binding.horizontalScrollView.scrollTo(left, 0)

                    itemView.requestFocus()

                    if (clickedText == "Job Salary Detail") {
                        replaceFragment(JobSalaryDetails())
                    } else if (clickedText.equals("Salary Details")) {
                        replaceFragment(CandidateSalaryDetails())
                    }
                }
            })

            binding.linearlist.addView(itemView)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_hired_summary, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } catch (e: Exception) {
        }

    }
}