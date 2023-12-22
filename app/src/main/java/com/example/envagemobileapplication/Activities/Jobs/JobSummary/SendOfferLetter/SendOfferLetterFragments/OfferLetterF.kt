package com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter.SendOfferLetterFragments

import android.R
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.GetCustomJobTemplateReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GnrateOFerLeterRsp.GenerateOFferLetterResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.Data
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.DatePickerHelper
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.SendOfferLetterViewModel
import com.example.envagemobileapplication.databinding.FragmentOfferLetterBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class OfferLetterF : Fragment() {

    private var clientHeaderSummaryResp: ClientHeaderSummaryResponse? = null
    var descriptiontext: String = ""
    private var mEditor: RichEditor? = null
    private lateinit var datePickerHelper: DatePickerHelper
    private var customtemplateResponse: Data? = null
    private var jobdeetails: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Data? =
        null
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: SendOfferLetterViewModel by activityViewModels()
    lateinit var binding: FragmentOfferLetterBinding
    lateinit var customTemplateList: ArrayList<Record>
    var templateId = ""
    lateinit var description: MultipartBody.Part
    var global = com.example.envagemobileapplication.Utils.Global
    var firstname = ""
    var lastname = ""
    var joiningdate = ""
    var salary = ""
    var offerletterlink = ""
    var clientname = ""
    var clientindustry = ""
    var clientwebsite = ""
    var clientFacebook = ""
    var clientInstagram = ""
    var clientLinkedin = ""
    var clientAddress = ""
    var clientLocation = ""
    var clientCountry = ""
    var clientCity = ""
    var clientState = ""
    var clientZipCode = ""
    var clientPhoneNumber = ""
    var senderName = ""
    var senderDesignation = ""
    var positionname = ""
    var jobIndustry = ""
    var jobType = ""
    var jobNature = ""
    var jobFrequency = ""
    var jobweekDays = ""
    var jobestimatedhours = ""
    var jobaddress = ""
    var jobCountry = ""
    var jobcity = ""
    var jobstate = ""
    var jobzipcode = ""
    var joblocation = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentOfferLetterBinding.inflate(inflater, container, false)
        clicklisteners()
        initviews()
        observers()
        networkCalls()
        return binding.root
    }

    private fun networkCalls() {
        loader.show()
        val model = GetCustomJobTemplateReqModel(
            pageIndex = 1,
            pageSize = 9999,
            sortBy = "CreatedDate",
            sortDirection = 1,
            searchText = ""
        )
        viewModel.getCustomTemplates(requireContext(), tokenManager.getAccessToken(), model)
        loader.show()
        /*var jobguid = "b6a549db-63eb-40b6-a9da-fcf724a74d1c"*/
        var jobguid = global.jobHeaderSummary!!.data.jobInfo.guid!!
        viewModel.getjobbyJobid(requireContext(), tokenManager.getAccessToken(), jobguid)

    }

    private fun observers() {
        viewModel.LDofferLetterTemplate.observe(requireActivity()) {
            loader.hide()

            customtemplateResponse = it.data
            if (it.data != null) {

                customTemplateList =
                    ArrayList()
                for (i in 0 until it.data.records.size) {
                    customTemplateList.add(it.data.records.get(i))
                }

                var items: ArrayList<String> = ArrayList()
                items.add("Select Template")
                for (i in 0 until customTemplateList.size) {
                    items.add(customTemplateList.get(i).templateName)
                }


                try {
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        items
                    )
                    binding.spinnerSelectTemplate.setAdapter(adapter)
                    binding.spinnerSelectTemplate.setSelection(0)


                } catch (e: Exception) {

                }
            }
        }


        viewModel.LDGetJobByJobId.observe(requireActivity()) {
            loader.hide()

            jobdeetails = it.data
            if (jobdeetails != null) {
                joiningdate = jobdeetails!!.startDate.toString()
                salary = jobdeetails!!.minimumSalary.toString()
                jobweekDays = jobdeetails!!.workingDays
                jobestimatedhours = jobdeetails!!.estimatedHours.toString()
                var clientid = jobdeetails!!.clientId

                getClientHeaderSummary(clientid)
            }
        }
    }

    private fun initviews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        datePickerHelper = DatePickerHelper(requireContext())

        try {
            if (senderName != null) {
                senderName =
                    global.loggedinuserDetails!!.firstName + " " + global.loggedinuserDetails!!.lastName
            }

            if (senderDesignation != null) {
                senderDesignation =
                    global.loggedinuserDetails!!.designation.designationName.toString()
            }

            if (positionname != null) {
                positionname = global.jobHeaderSummary!!.data.jobInfo.positionName
            }

            if (jobIndustry != null) {
                jobIndustry = global.jobHeaderSummary!!.data.jobInfo.industryName.toString()
            }

            if (jobType != null) {
                jobType = global.jobHeaderSummary!!.data.jobInfo.jobType
            }

            if (jobType != null) {
                jobNature = global.jobHeaderSummary!!.data.jobInfo.jobNature
            }

            if (jobFrequency != null) {
                jobFrequency = global.jobHeaderSummary!!.data.jobInfo.jobFrequency
            }

            if (jobaddress != null) {
                jobaddress =
                    global.jobHeaderSummary!!.data.jobInfo.address1 + " " + global.jobHeaderSummary!!.data.jobInfo.address2
            }


            if (jobcity != null) {
                jobcity = global.jobHeaderSummary!!.data.jobInfo.city.toString()
            }


            if (jobstate != null) {
                jobstate = global.jobHeaderSummary!!.data.jobInfo.state.toString()
            }


            if (joblocation != null) {
                joblocation = global.jobHeaderSummary!!.data.jobInfo.location.toString()
            }

            firstname = "Saif"
            lastname = "jadoon"
            offerletterlink = "offer letter link"
            clientFacebook = "www.facebook.com"
            clientInstagram = "www.instagram.com"
            clientLinkedin = "www.facebook.com"
            if (jobCountry != null) {
                jobCountry = global.jobHeaderSummary!!.data.jobInfo.country.toString()
            }

            if (jobzipcode != null) {
                jobzipcode = global.jobHeaderSummary!!.data.jobInfo.zipcode.toString()
            }
        } catch (e: Exception) {
        }


        // setUpRtf()
    }

    private fun clicklisteners() {

        binding.etDescription!!.setOnTextChangeListener { text ->

            if (text.contains("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }


        }
        binding.btnnext.setOnClickListener {
            if (!descriptiontext.isNullOrBlank()) {
                val htmlContent = descriptiontext
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = RequestBody.create(mediaType, htmlContent)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "Description",
                        "description.html",
                        descriptionBody
                    )
                description = descriptionPart
            } else {

                val htmlContent = "<p></p>"
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = RequestBody.create(mediaType, htmlContent)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "description",
                        "description.html",
                        descriptionBody
                    )
                description = descriptionPart
                global.descriptionforOfferLetter = description
            }

            var candidateid = MultipartBody.Part.createFormData(
                "CandidateId",
                global.candidateIdForOfferLetter.toString()
            )
            var JobId =
                MultipartBody.Part.createFormData("JobId", global.jobidForOfferLetter.toString())
            var TemplateId = MultipartBody.Part.createFormData("TemplateId", templateId)
            var ClientLogo = MultipartBody.Part.createFormData("ClientLogo", "false")
            var ClientName = MultipartBody.Part.createFormData("ClientName", "false")
            var ClientAddress = MultipartBody.Part.createFormData("ClientAddress", "false")
            var ClientWebsite = MultipartBody.Part.createFormData("ClientWebsite", "false")
            var ClientFacebook = MultipartBody.Part.createFormData("ClientFacebook", "false")
            var ClientInstagram = MultipartBody.Part.createFormData("ClientInstagram", "false")
            var ClientLinkedin = MultipartBody.Part.createFormData("ClientLinkedin", "false")
            var ClientTwitter = MultipartBody.Part.createFormData("ClientTwitter", "false")
            var PoweredBy = MultipartBody.Part.createFormData("PoweredBy", "false")
            var ClientPoc = MultipartBody.Part.createFormData("ClientPoc", "false")
            var OfferLetterLink =
                MultipartBody.Part.createFormData("OfferLetterLink", description.toString())
            var validTill = MultipartBody.Part.createFormData("validTill", "10/11/2022")

            try {
                loader.show()

                ApiUtils.getAPIService(requireContext()).GenerateOFferLetterLink(
                    token.toString(),
                    candidateid,
                    JobId,
                    TemplateId,
                    ClientLogo,
                    ClientName,
                    ClientAddress,
                    ClientWebsite,
                    ClientFacebook,
                    ClientInstagram,
                    ClientLinkedin,
                    ClientTwitter,
                    PoweredBy,
                    ClientPoc,
                    OfferLetterLink,
                    validTill
                )
                    .enqueue(object : Callback<GenerateOFferLetterResponse> {
                        override fun onResponse(
                            call: Call<GenerateOFferLetterResponse>,
                            response: Response<GenerateOFferLetterResponse>
                        ) {
                            loader.hide()
                            if (response.body() != null) {

                                if (response.body()!!.data.get(0).guid != null) {

                                    replaceFragment(SendEmailF())
                                }

                            } else {
                                Log.i("errormsg", response.message())
                            }
                        }

                        override fun onFailure(
                            call: Call<GenerateOFferLetterResponse>,
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
        binding.TISelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })

        binding.spinnerSelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })

        binding.spinnerSelectTemplate.setOnItemClickListener { _, _, position, _ ->
            var selectedText = binding.spinnerSelectTemplate.text.toString()

            for (i in 0 until customtemplateResponse!!.records.size) {

                if (selectedText == customtemplateResponse!!.records.get(i).templateName.toString()) {
                    templateId = customtemplateResponse!!.records.get(i).offerTemplateId.toString()
                    global.offerlettertemplateid = templateId
                    var filename =
                        customtemplateResponse!!.records.get(i).templatePath.toString()
                    var baseurlnew =
                        "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                    try {
                        loadJobDescriptionContent(baseurlnew)
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            e.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    // binding.tvDescription.text = "No Description to Show"
                }
            }
        }

        binding.tvExpiryDate.setOnClickListener {

            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvExpiryDate)

        }
        binding . ccStartdate . setOnClickListener {
            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvExpiryDate)
        }
    }

    fun loadJobDescriptionContent(url: String) {
        Thread {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append("\n") // Append a newline to separate lines
                }

                val htmlContent = response.toString()

                requireActivity().runOnUiThread {
                    val spannedText: Spanned =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            TODO("VERSION.SDK_INT < N")
                        }
                    val description: String = spannedText.toString()

                    if (description.isNullOrEmpty()) {
                        // binding.etDescription.visibility = View.GONE
                    } else {
                        // Replace placeholders in the HTML content
                        val replacedHtmlContent = replacePlaceholders(htmlContent)

                        // Set the modified HTML content to HtmlTextView
                        binding.etDescription.html = replacedHtmlContent
                        //  binding.etDescription.contentDescription = spannedText
                    }
                }

                inputStream.close()
                urlConnection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors here
            }
        }.start()
    }

    private fun setUpRtf() {
        mEditor =
            requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.et_client_description) as RichEditor
        mEditor!!.setEditorHeight(110)
        mEditor!!.setEditorFontSize(14)
        mEditor!!.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(0, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor!!.setPlaceholder("Description")
        //mEditor.setInputEnabled(false);
        //  mPreview = findViewById<View>(R.id.preview) as TextView
        //   mEditor!!.setOnTextChangeListener { text -> mPreview!!.text = text }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_undo)
            .setOnClickListener { mEditor!!.undo() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_redo)
            .setOnClickListener { mEditor!!.redo() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_bold)
            .setOnClickListener { mEditor!!.setBold() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_italic)
            .setOnClickListener { mEditor!!.setItalic() }

        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_strikethrough)
            .setOnClickListener { mEditor!!.setStrikeThrough() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_underline)
            .setOnClickListener { mEditor!!.setUnderline() }

        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_txt_color)
            .setOnClickListener(
                object : View.OnClickListener {
                    private var isChanged = false
                    override fun onClick(v: View) {
                        mEditor!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                        isChanged = !isChanged
                    }
                })
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_bg_color)
            .setOnClickListener(
                object : View.OnClickListener {
                    private var isChanged = false
                    override fun onClick(v: View) {
                        mEditor!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                        isChanged = !isChanged
                    }
                })

        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_align_left)
            .setOnClickListener { mEditor!!.setAlignLeft() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_align_center)
            .setOnClickListener { mEditor!!.setAlignCenter() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_align_right)
            .setOnClickListener { mEditor!!.setAlignRight() }

        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_bullets)
            .setOnClickListener { mEditor!!.setBullets() }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_numbers)
            .setOnClickListener { mEditor!!.setNumbers() }

        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_link)
            .setOnClickListener {
                mEditor!!.insertLink(
                    "https://github.com/wasabeef",
                    "wasabeef"
                )
            }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.envagemobileapplication.R.id.nav_send_email, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun replacePlaceholders(htmlContent: String): String {
        // Replace placeholders with your desired values
        val replacedContent = htmlContent
            .replace("[First Name]", firstname)
            .replace("[Last Name]", lastname)
            .replace("[Joining Date]", joiningdate)
            .replace("[Salary]", salary)
            .replace("[Offer Letter Link]", offerletterlink)
            .replace("[Client Name]", clientname)
            .replace("[Client Industry]", clientindustry)
            .replace("[Client Website]", clientwebsite)
            .replace("[Client Facebook]", clientFacebook)
            .replace("[Client Instagram]", clientInstagram)
            .replace("[Client Linkedin]", clientLinkedin)
            .replace("[Client Address]", clientAddress)
            .replace("[Client Location]", clientLocation)
            .replace("[Client Country]", clientCountry)
            .replace("[Client City]", clientCity)
            .replace("[Client State]", clientState)
            .replace("[Client Zip Code]", clientZipCode)
            .replace("[Client Phone Number]", clientPhoneNumber)
            .replace("[Sender Name]", senderName)
            .replace("[Sender Designation]", senderDesignation)
            .replace("[Job Position Name]", positionname)
            .replace("[Job Industry]", jobIndustry)
            .replace("[Job Type]", jobType)
            .replace("[Job Nature]", jobNature)
            .replace("[Job Frequency]", jobFrequency)
            .replace("[Job Weekdays]", jobweekDays)
            .replace("[Job Estimated Hours]", jobestimatedhours)
            .replace("[Job Address]", jobaddress)
            .replace("[Job Country]", jobCountry)
            .replace("[Job City]", jobcity)
            .replace("[Job State]", jobstate)
            .replace("[Job Zip Code]", jobzipcode)
            .replace("[Job Location]", joblocation)

        // Add more replacements as needed
        return replacedContent
    }

    private fun getClientHeaderSummary(clientid: Int) {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId = clientid
        try {
            ApiUtils.getAPIService(requireContext()).GetClientHeaderSummary(
                token.toString(), clientId
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {
                        if (isAdded) {
                            if (response.body() != null) {
                                loader.hide()
                                clientHeaderSummaryResp = response.body()!!
                                clientname = clientHeaderSummaryResp!!.data.clientInfo.name
                                clientindustry =
                                    clientHeaderSummaryResp!!.data.clientInfo.industryName
                                clientwebsite = clientHeaderSummaryResp!!.data.clientInfo.websiteUrl
                                clientAddress =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddress1 + " " + clientHeaderSummaryResp!!.data.clientInfo.primaryAddress2
                                clientLocation = clientHeaderSummaryResp!!.data.clientInfo.location
                                clientCountry = clientHeaderSummaryResp!!.data.clientInfo.country
                                clientCity =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressCity
                                clientState =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressState
                                clientZipCode =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressZipcode
                                clientPhoneNumber = clientHeaderSummaryResp!!.data.clientInfo.phone
                            }
                        }
                    }

                    override fun onFailure(call: Call<ClientHeaderSummaryResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Toast.makeText(requireContext(), ex.toString(), Toast.LENGTH_LONG).show()
        }

    }
}