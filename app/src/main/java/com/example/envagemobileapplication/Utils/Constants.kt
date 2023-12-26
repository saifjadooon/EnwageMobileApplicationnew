package com.example.envagemobileapplication.Utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.envagemobileapplication.Activities.MainActivity
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientSocialMedium
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Constants {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(inputDate: String): String {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

            try {
                val date =
                    LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                return outputFormatter.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle parsing error
                return ""
            }
        }

        @Suppress("DEPRECATION")
        fun formatDateForOlderVersions(inputDate: String): String {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

            try {
                val date = inputFormatter.parse(inputDate)
                return outputFormatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                Log.d("formatDate", e.toString())
                return e.toString()
            }
        }

        var candidateInterviewdId: Int? = 0
        var candidateJobStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.Datum> =
            ArrayList()
        var candidateJobHiredId: Int = 0
        var jobId: Int = 0
        var candidateName: String = ""
        var candidateId: String? = ""
        var isfromBackpress: Boolean = false
        var statusclickedColor: String? = ""
        var clientPocId: Int? = 0
        var Candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record> =
            ArrayList()
        var ownerName: Int = 0
        var socialmedialist: ArrayList<ClientSocialMedium> = ArrayList()
        var clientSummaryResp: ClientHeaderSummaryResponse = ClientHeaderSummaryResponse()
        var isfromClientsummaryJobsList: Boolean = false
        var clientName: String = ""
        var isOnboardingStatusUpdatedfromClientSummaryList: Boolean = false
        var clientid: Int? = 0
        lateinit var thisss: Context
        var ReqCode: String = ""
        lateinit var cfm: FragmentManager
        var StatusClickedClientId: Int? = 0
        var StatusClickedJobId: Int? = 0
        var StatusClickedName: String? = ""
        var StatusclickedPosition: Int? = 0
        var filterList: ArrayList<Int> = ArrayList()
        var onBoardingStatusList: ArrayList<Datum> = ArrayList()
        var jobStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
            ArrayList()
        var ClientList: ArrayList<Record> = ArrayList()
        var JobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
            ArrayList()
        var context: MainActivity? = null
        var token: String? = ""

        //sso base url
        //val BASE_URL = "https://devssoapi.9ostech.com/"
        val BASE_URL = "https://stagingssoapi.9ostech.com/"

        //devgateway url

        val BASE_URL_devgateway = "https://devgateway.enwage.com/"
        val BASE_URL_STAGING = "https://staginggateway.enwage.com/"

        val domain = "devapp.enwage.com"

        fun formatHintWithRedAsterisk(hint: String): CharSequence {
            val spannable = SpannableStringBuilder(hint)
            val indexOfAsterisk = hint.indexOf('*')

            if (indexOfAsterisk >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    indexOfAsterisk,
                    indexOfAsterisk + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            return spannable
        }
    }


}