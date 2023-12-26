package com.example.envagemobileapplication.Fragments.BottomSheet

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ConvertCandidateResponse.ConvertCandidateResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.OnlineApplicantBottomSheetBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URLEncoder
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OnlineApplicantBottomSheet : BottomSheetDialogFragment() {
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    lateinit var loader: Loader
    var global = com.example.envagemobileapplication.Utils.Global
    var token = ""
    var position: Int = 0
    lateinit var tokenManager: TokenManager
    lateinit var binding: OnlineApplicantBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = OnlineApplicantBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = com.example.envagemobileapplication.Utils.Global.position
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()

        var jobsummary = com.example.envagemobileapplication.Utils.Global.jobHeaderSummary

        binding.tvCLientName.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                jobsummary!!.data.jobInfo.clientName,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }
        binding.tvEmail.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                global.onlineApplicantDetails!!.records.get(position).email,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        binding.tvRaceEthnicity.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                global.onlineApplicantDetails!!.records.get(position).ethnicity,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }
        if (!jobsummary!!.data.jobInfo.clientName.isNullOrEmpty()) {
            binding.tvCLientName.setText(jobsummary!!.data.jobInfo.clientName)


        } else {
            binding.tvCLientName.setText("-")
        }



        binding.tvJobName.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                jobsummary!!.data.jobInfo.positionName,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }
        binding.tvFirstName.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                global.onlineApplicantDetails!!.records.get(position).firstName,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        binding.tvLastName.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                global.onlineApplicantDetails!!.records.get(position).lastName,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        binding.tvAddress.setOnLongClickListener {
            val toast = Toast.makeText(
                requireContext(),
                global.onlineApplicantDetails!!.records.get(position).address,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }
        if (!jobsummary!!.data.jobInfo.positionName.isNullOrEmpty()) {
            binding.tvJobName.setText(jobsummary!!.data.jobInfo.positionName)

            binding.tvJobName.setText(jobsummary!!.data.jobInfo.positionName)
        } else {
            binding.tvJobName.setText("-")
        }
        if (jobsummary!!.data.jobInfo.clientProfilePicture != null && jobsummary!!.data.jobInfo.clientProfilePicture != "") {
            Picasso.get().load(jobsummary!!.data.jobInfo.clientProfilePicture)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(binding.ivClientProfilePic)
        } else {
            Picasso.get().load(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(binding.ivClientProfilePic)
        }

        var skillsList: ArrayList<String> = ArrayList()
        for (i in 0 until global.AllOnlineSkills!!.data.size) {

            skillsList.add(global.AllOnlineSkills!!.data.get(i).skillName)

        }

        for (itemText in skillsList) {
            val itemView = layoutInflater.inflate(R.layout.skills_item_online_candidate, null)

            val textView = itemView.findViewById<TextView>(R.id.iv_title)

            textView.text = itemText

            binding.linearlist.addView(itemView)
        }

        if (!global.onlineApplicantDetails!!.records.get(position).firstName.isNullOrEmpty()) {
            binding.tvFirstName.setText(global.onlineApplicantDetails!!.records.get(position).firstName)
        } else {
            binding.tvFirstName.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).lastName.isNullOrEmpty()) {
            binding.tvLastName.setText(global.onlineApplicantDetails!!.records.get(position).lastName)
        } else {
            binding.tvLastName.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).dateOfBirth.isNullOrEmpty()) {

            var dob =
                convertDateFormat(global.onlineApplicantDetails!!.records.get(position).dateOfBirth)
            binding.tvDob.setText(dob)
        } else {
            binding.tvDob.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).gender.isNullOrEmpty()) {
            binding.tvGender.setText(global.onlineApplicantDetails!!.records.get(position).gender)
        } else {
            binding.tvGender.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).ethnicity.isNullOrEmpty()) {
            binding.tvRaceEthnicity.setText(global.onlineApplicantDetails!!.records.get(position).ethnicity)
        } else {
            binding.tvRaceEthnicity.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).address.isNullOrEmpty()) {
            binding.tvAddress.setText(global.onlineApplicantDetails!!.records.get(position).address)
        } else {
            binding.tvAddress.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).phoneNumber.isNullOrEmpty()) {
            val formattedPhone =
                formatPhoneNumber(global.onlineApplicantDetails!!.records.get(position).phoneNumber)
            binding.tvPhone.setText(formattedPhone.toString())
        } else {
            binding.tvPhone.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).email.isNullOrEmpty()) {
            binding.tvEmail.setText(global.onlineApplicantDetails!!.records.get(position).email)
        } else {
            binding.tvEmail.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).graduationYear.isNullOrEmpty()) {
            binding.tvGraduationYear.setText(global.onlineApplicantDetails!!.records.get(position).graduationYear)
        } else {
            binding.tvGraduationYear.setText("-")
        }

        if (!global.onlineApplicantDetails!!.records.get(position).yearsOfExperience.isNullOrEmpty()) {
            binding.tvYearOfExp.setText(global.onlineApplicantDetails!!.records.get(position).yearsOfExperience)
        } else {
            binding.tvYearOfExp.setText("-")
        }
        if (global.onlineApplicantDetails!!.records.get(position).currentSalary != null

        ) {

            val salary = global.onlineApplicantDetails!!.records.get(position).currentSalary
            val usLocale = Locale("en", "US")
            val currencyFormat = NumberFormat.getCurrencyInstance(usLocale)
            val formattedSalary = currencyFormat.format(salary)


            // Set the formatted salary in the TextView
            binding.tvCurrentSalary.text = formattedSalary
            /*  binding.tvCurrentSalary.setText(
                  "$" + global.onlineApplicantDetails!!.records.get(
                      position
                  ).currentSalary.toString()
              )*/
        } else {
            binding.tvCurrentSalary.setText("-")
        }
        if (global.onlineApplicantDetails!!.records.get(position).expectedSalary != null

        ) {


            val salary = global.onlineApplicantDetails!!.records.get(position).expectedSalary
            val usLocale = Locale("en", "US")
            val currencyFormat = NumberFormat.getCurrencyInstance(usLocale)
            val formattedSalary = currencyFormat.format(salary)

            binding.tvExpectedSalary.setText(
                formattedSalary
            )
        } else {
            binding.tvExpectedSalary.setText("-")
        }
        if (global.onlineApplicantDetails!!.records.get(position).legallyWorkable != null) {
            binding.tvLegallyAlowedworkinUSa.setText("Yes")
        } else {
            binding.tvLegallyAlowedworkinUSa.setText("No")
        }

        if (!global.onlineApplicantDetails!!.records.get(position).ssnNumber.isNullOrEmpty()) {
            val formattedSSn =
                formatPhoneNumber(global.onlineApplicantDetails!!.records.get(position).ssnNumber)
            binding.tvSSn.setText(formattedSSn.toString())
        } else {
            binding.tvSSn.setText("-")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).language.isNullOrEmpty()) {
            binding.tvPrefferedLanguage.setText(global.onlineApplicantDetails!!.records.get(position).language.toString())
        } else {
            binding.tvPrefferedLanguage.setText("-")
        }

        if (global.onlineApplicantDetails!!.records.get(position).transportAvailability == true) {
            binding.tvTransport.setText("Yes")
        } else {
            binding.tvTransport.setText("No")
        }
        if (!global.onlineApplicantDetails!!.records.get(position).shift.isNullOrEmpty()) {
            binding.tvShiftIntrested.setText(global.onlineApplicantDetails!!.records.get(position).shift.toString())
        } else {
            binding.tvShiftIntrested.setText("-")
        }
        if (global.onlineApplicantDetails!!.records.get(position).coverLetter != null
        ) {
            binding.tvCoverLetter.visibility = View.VISIBLE
            binding.viewAttachment.visibility = View.VISIBLE
            binding.tvCoverLetter.setText(global.onlineApplicantDetails!!.records.get(position).coverLetter.toString())
        } else {
            binding.tvCoverLetter.visibility = View.GONE
            binding.viewAttachment.visibility = View.GONE
            binding.tvCoverLetter.setText("-")
        }


        binding.ivResume.setOnClickListener {
            try {
                var resume = global.onlineApplicantDetails!!.records.get(position).resume
                val encodedFileName = URLEncoder.encode(resume.toString(), "UTF-8")
                val fileUrl =
                    "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=$encodedFileName"

                Log.i("fileurllatest", fileUrl)
                /*  val fileUrl =
                      "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=Uploads/Applicants/1220/History%20of%20Pakistan%20India_e067126d-08b3-4886-9c16-9d82157b83da.pdf"*/
                downloadFile(requireContext(), fileUrl)
            } catch (e: Exception) {

            }

        }

        binding.extraAttachments.setOnClickListener {
            try {
                var extraDocument =
                    global.onlineApplicantDetails!!.records.get(position).extraDocument
                val encodedFileName = URLEncoder.encode(extraDocument.toString(), "UTF-8")
                val fileUrl =
                    "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename= $encodedFileName"

                Log.i("fileurllatest", fileUrl)
                /*  val fileUrl =
                      "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=Uploads/Applicants/1220/History%20of%20Pakistan%20India_e067126d-08b3-4886-9c16-9d82157b83da.pdf"*/
                downloadFile(requireContext(), fileUrl)
            } catch (e: Exception) {
            }
        }

        binding.ivConvertToCandidate.setOnClickListener {

            showDialog()

        }
        binding.ivConvertToCandidateBank.setOnClickListener {
            showCandiDateBankDialog()

        }
    }

    fun convertDateFormat(inputDate: String): String {
        // Define the input and output date formats
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Parse the input date string
        val date = inputFormat.parse(inputDate)

        // Format the date into the desired output format
        return outputFormat.format(date ?: Date())
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val cleanedNumber = phoneNumber.replace("\\D".toRegex(), "") // Remove non-digits
        return if (cleanedNumber.length == 10) {
            "${cleanedNumber.substring(0, 3)}-${
                cleanedNumber.substring(
                    3,
                    6
                )
            }-${cleanedNumber.substring(6)}"
        } else {
            cleanedNumber // Return the cleaned number if it doesn't have 10 digits
        }
    }

    /* fun downloadFile(context: Context, fileUrl: String) {
         // Extract the file name from the file URL
         val fileName = Uri.parse(fileUrl).lastPathSegment ?: "file"

         // Create a request for the DownloadManager
         val request = DownloadManager.Request(Uri.parse(fileUrl))
             .setTitle(fileName)
             .setDescription("Downloading file")
             .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
             .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

         // Get the DownloadManager service
         val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

         // Enqueue the download request
         manager.enqueue(request)
     }*/

/*

    fun downloadFile(context: Context, fileUrl: String) {
        // Extract the file name from the file URL
        val fileName = Uri.parse(fileUrl).lastPathSegment ?: "file"

        // Create a request for the DownloadManager
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle(fileName)
            .setDescription("Downloading file")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        // Get the DownloadManager service
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // Enqueue the download request
        val downloadId = manager.enqueue(request)

        // Optionally, you can listen for download completion
        // and then handle the downloaded file accordingly
        // (e.g., open the file using appropriate viewer)
        // Uncomment the following lines if you want to listen for completion

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val downloadedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadedId == downloadId) {
                    // Handle the downloaded file here
                    handleDownloadedFile(context, fileName)
                }
            }
        }
        context.registerReceiver(receiver, filter)

    }
*/

    fun downloadFile(context: Context, fileUrl: String) {
        // Extract the file name from the file URL
        var fileName = Uri.parse(fileUrl).lastPathSegment ?: "file"

        // Ensure that the fileName contains the correct file extension
        if (!fileName.contains(".")) {
            fileName += ".pdf"  // Adjust this based on the file type
        }

        // Append a timestamp to make the filename unique
        val uniqueFileName = "${fileName}_${System.currentTimeMillis()}"

        // Create a request for the DownloadManager
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle(uniqueFileName)
            .setDescription("Downloading file")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uniqueFileName)

        // Get the DownloadManager service
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // Enqueue the download request
        val downloadId = manager.enqueue(request)

        // Optionally, you can listen for download completion
        // and then handle the downloaded file accordingly
        // (e.g., open the file using the appropriate viewer)
        // Uncomment the following lines if you want to listen for completion

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val downloadedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadedId == downloadId) {
                    // Handle the downloaded file here
                    handleDownloadedFile(context, uniqueFileName)
                }
            }
        }
        context.registerReceiver(receiver, filter)
    }

    private fun handleDownloadedFile(context: Context?, fileName: String) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(
            FileProvider.getUriForFile(
                requireContext()!!,
                requireContext().packageName + ".provider",
                file
            ), "*/*"
        )
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION

        try {
            requireActivity().startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No app found to open this file", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getMimeType(file: File): String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.toUri().toString())
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase())
        }
        return null
    }

    private fun converttoCandidateBank() {
        loader.show()

        var applicantid = global.onlineApplicantDetails!!.records.get(position).applicantId

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
                                    viewModel.hideSummaryDetailbottomSheet()
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

        var applicantid = global.onlineApplicantDetails!!.records.get(position).applicantId
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
                                    viewModel.hideSummaryDetailbottomSheet()
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
}