package com.example.envagemobileapplication.Activities.Candidates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Fragments.BottomSheet.BSCandidateJobStatusF
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetStatusFragment
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.ActivityCandidateJobStatusChangeBinding
import com.squareup.picasso.Picasso

class CandidateJobStatusChange : AppCompatActivity() {


    var bsCandidateJobStatusF: BSCandidateJobStatusF = BSCandidateJobStatusF()
    lateinit var binding: ActivityCandidateJobStatusChangeBinding
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: CandidatesProfileSumViewModel by viewModels()


    var jobstatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.Datum> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCandidateJobStatusChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observers()
        clickListeners()


    }

    private fun clickListeners() {
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnCross.setOnClickListener {
            finish()
        }

        binding.cvStatusChange.setOnClickListener {
            if (bsCandidateJobStatusF.isAdded()) {
                return@setOnClickListener
            } else {
                bsCandidateJobStatusF.show(supportFragmentManager, bsCandidateJobStatusF.tag)
            }
        }

        viewModel.getJobDetailsById(
            this,
            token,
            Constants.jobId
        )

        viewModel.getCandidateJobStatuses(
            this,
            token
        )

        viewModel.getCandidateStausKeyPipeline(
            this,
            token
        )

    }

    private fun observers() {

        getJobDetailsById()
        getCandidateJobStatuses()
        getcandidateStatusKeyPipeline()

    }

    private fun getcandidateStatusKeyPipeline() {
        viewModel.LDgetCandidateStatusKeyPipeline.observe(this) {
            loader.hide()

            if (it.data != null) {
                try {
                    Constants.candidateJobHiredId = it.data.candidateHired
                }catch (e:Exception){
                    Toast.makeText(this, "exception in fetching candidateHired", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCandidateJobStatuses() {
        viewModel.LDgetCandidateJobStatuses.observe(this) {
            loader.hide()

            if (it.data != null) {
                jobstatusesList.addAll(it.data)
                Constants.candidateJobStatusList = jobstatusesList
            } else {
                Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getJobDetailsById() {
        viewModel.LDgetJobDetailsById.observe(this) {
            loader.hide()


            try {
                if (it.data != null) {

                    val jobData = it.data
                    if (jobData.clientProfilePicture != "") {
                        Glide.with(this)
                            .load(jobData.clientProfilePicture)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(binding.ivJobProfile)
                    }

                    if (jobData.positionName != null) {
                        binding.tvPositionName.text = jobData.positionName
                    } else {
                        binding.tvPositionName.text = "Not Provided"
                    }

                    if (jobData.clientName != null) {
                        binding.tvClientName.text = jobData.clientName + " | "
                    } else {
                        binding.tvPositionName.text = "Not Provided"
                    }

                    if (jobData.jobNature != null) {
                        binding.tvJobNature.text = jobData.jobNature.toString() + " - "
                    } else {
                        binding.tvPositionName.text = "Not Provided"
                    }

                    if (jobData.jobType != null) {
                        binding.tvJobType.text = jobData.jobType
                    } else {
                        binding.tvPositionName.text = "Not Provided"
                    }




                    binding.tvMuPercentage.text = jobData.billingDetails.markup.toString() + "%"
                    binding.tvMinPayrate.text = "$" + jobData.billingDetails.minPayRate.toString()
                    binding.tvMinBillrate.text = "$" + jobData.billingDetails.minBillRate.toString()
                    binding.tvMaxPayrate.text = "$" + jobData.billingDetails.maxPayRate.toString()
                    binding.tvMaxBillrate.text = "$" + jobData.billingDetails.maxBillRate.toString()
                    binding.tvMaxBillrate.text = "$" + jobData.billingDetails.maxBillRate.toString()
                    binding.tvMaxBillrate.text = "$" + jobData.billingDetails.maxBillRate.toString()
                    binding.tvTargetPayrate.text = "$" + jobData.billingDetails.targetPayRate.toString()
                    binding.tvTargetBillrate.text ="$" + jobData.billingDetails.targetBillRate.toString()
                    binding.tvFrequency.text = jobData.billingDetails.frequency.toString()


                    // For OT
                    binding.tvOtRule.text = jobData.billingDetails.overtimeType
                    binding.tvotMinPayrate.text = "$" + jobData.billingDetails.minPayRate.toString()
                    binding.tvOtMultiplier.text = jobData.billingDetails.overtimeMultiplier.toString() + "x"
                    binding.tvOtPayrate.text = "$" + jobData.billingDetails.overtimePayRate.toString()
                    binding.tvOtBillrate.text = "$" + jobData.billingDetails.overtimeBillRate.toString()
                    binding.tvDtMultiplier.text = jobData.billingDetails.doubletimeMultiplier.toString() + "x"
                    binding.tvDtPayrate.text = "$" + jobData.billingDetails.doubletimePayRate.toString()
                    binding.tvDtBillrate.text = "$" + jobData.billingDetails.doubletimeBillRate.toString()

                } else {
                    Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
              Log.d("ExceptionCJob",": "+e.toString())
            }
        }
    }

    private fun initViews() {

        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken()!!

    }


}