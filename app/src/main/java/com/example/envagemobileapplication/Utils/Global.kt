package com.example.envagemobileapplication.Utils

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ProgressBar
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobAdressDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobBasicDetailRequestModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobSalaryDetailReqModel
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.SkillsRequestModels
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Data
import okhttp3.MultipartBody

public class Global {

    companion object {

        var offerlettertemplateid: String = ""
        var descriptionforOfferLetter: MultipartBody.Part? = null
        var lastnameforofferletter: String? = ""
        var firstnameforofferletter: String? = ""
        var jobGuidforOfferLetter: String? = ""
        var candidateEmailAdress: String? = null
        var loggedinuserDetails: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp.Data? =
            null
        var jobidForOfferLetter: Any? = ""
        var candidateIdForOfferLetter: Int? = 0
        var index: Int = 0
        var jobtypelist: ArrayList<String> = ArrayList()
        var isfirstTimeFragmentLoaded: Boolean = true
        var getcandidatjobResp: CandidateJobGetJobResponse = CandidateJobGetJobResponse()
        var getjobbyjoid: GetJobByJobIdResponse = GetJobByJobIdResponse()
        var arraylistSelectedSkills: ArrayList<JobSkill> = ArrayList()
        var estimatedhours: String = ""
        var jobstatius: String = ""
        var jobtype: String = ""
        var jobskilllslist: ArrayList<SkillsRequestModels> = ArrayList()
        var descriptiontext: String = ""
        var noofWorkingDays: String = ""
        var enddate: String = ""
        var jobtitle: String? = ""
        var jobHeaderSummary: GetJobHeaderSummary? = null
        var jobGuid: String? = ""
        var AllOnlineSkillsPosition: Int = 0
        var position: Int = 0
        var GlobalJobID: Int? = 0
        var AllOnlineSkills: GetAllSkillsResponse? = null
        var onlineApplicantDetails: Data? = null
        var jobId: String = "0"
        var applicantid: String = "0"
        var isfrombackpress: Boolean = false
        var startdate: String = ""
        var headcount: String = ""
        var weekdaysConcatinated: String = ""
        var jobskills: JobSkill? = null
        var addjobSalaryDetailReqModel: AddJobSalaryDetailReqModel? = null
        var addJobAddressDetailModel: AddJobAdressDetailsReqModel? = null
        var addJobDetailModel: AddJobDetailsReqModel? = null
        var basicDetailReqModel: AddJobBasicDetailRequestModel? = null
        var filterclickedStatusItemList: ArrayList<String> = ArrayList()
        var onBoardingStatusList: ArrayList<Datum> = ArrayList()
        var JobStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
            ArrayList()
        var progressDialog: ProgressDialog? = null

        var context: Context? = null
        fun showProgressDialog(activity: Activity?, message: String?) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage(message)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
            val progressbar = progressDialog!!.findViewById<ProgressBar>(R.id.progress)
            progressbar.indeterminateDrawable.setColorFilter(
                Color.parseColor("#d32e33"),
                PorterDuff.Mode.SRC_IN
            )
        }

        fun hideProgressDialog() {
            if (progressDialog!!.isShowing) {
                progressDialog!!.hide()
            }
        }
    }


}