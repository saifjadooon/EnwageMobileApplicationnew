package com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter.SendOfferLetterFragments

import android.R
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.GetCustomJobTemplateReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.Data
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GnrateOFerLeterRsp.GenerateOFferLetterResponse
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
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat


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


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentOfferLetterBinding.inflate(inflater, container, false)
        initviews()
        clicklisteners()
        observers()
        networkCalls()


        val hintselectTemplate = "Select  Template *"
        val formattedhintselectTemplate = formatHintWithRedAsterisk(hintselectTemplate)
        binding.TISelectTemplate.hint = formattedhintselectTemplate


        val hintExpiryDate = "Expiry Date *"
        val formattedhintExpiryDate = formatHintWithRedAsterisk(hintExpiryDate)
        binding.ccStartdate.hint = formattedhintExpiryDate

        binding.ccStartdate.setOnTouchListener(View.OnTouchListener { v, event ->
            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvExpiryDate)
            false
        })
        binding.tvExpiryDate.setOnTouchListener(View.OnTouchListener { v, event ->
            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvExpiryDate)
            false
        })

        binding.ccStartdate.setOnClickListener {
            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvExpiryDate)
        }
        binding.ccexpdate.setOnClickListener {

            datePickerHelper.attachDatePickertoConstraintlayout(
                binding.ccexpdate,
                binding.tvExpiryDate
            )
        }

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
                val inputDate = jobdeetails!!.startDate.toString()
                val formattedDate = formatDate(inputDate)
                joiningdate = formattedDate
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
        setUpRtf()



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


            firstname = global.fn.toString() + ""
            lastname = global.ln.toString() + ""
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
                        "OfferLetterLink",
                        "OfferLetterLink.html",
                        descriptionBody
                    )
                description = descriptionPart
            } else {

                val htmlContent = "<p></p>"
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = RequestBody.create(mediaType, htmlContent)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "OfferLetterLink",
                        "OfferLetterLink.html",
                        descriptionBody
                    )
                description = descriptionPart

            }

            global.offerlettlink = description


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
            var validTill =
                MultipartBody.Part.createFormData("validTill", binding.tvExpiryDate.text.toString())

            var spinersleectTemplatetext = binding.spinnerSelectTemplate.text.toString()

            if (!spinersleectTemplatetext.isNullOrBlank() && !binding.tvExpiryDate.text.isNullOrEmpty()) {
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


                                        val formattedDate =
                                            formatDate(response.body()!!.data.get(0).validTill)
                                        global.validtill = formattedDate
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
            } else {
                if (spinersleectTemplatetext.isNullOrBlank()) {
                    binding.TISelectTemplate.error = "Template is Required."
                    binding.TISelectTemplate.errorIconDrawable = null// Set the error message
                    binding.TISelectTemplate.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                } else if (binding.tvExpiryDate.text.isNullOrEmpty()) {
                    binding.ccStartdate.error = "Link Expiry Date is Required."
                    binding.ccStartdate.errorIconDrawable = null// Set the error message
                    binding.ccStartdate.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
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
            binding.TISelectTemplate.error = null
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

        binding.etDescription!!.setEditorHeight(110)
        binding.etDescription!!.setEditorFontSize(14)
        binding.etDescription!!.setEditorFontColor(Color.BLACK)
        /*  binding.etDescription!!.insertLink(
              "https://github.com/wasabeef",
              "Offer Letter Link"
          )*/

        //   binding.etDescription.setEditorBackgroundColor(Color.BLUE);
        //   binding.etDescription.setBackgroundColor(Color.BLUE);
        //   binding.etDescription.setBackgroundResource(R.drawable.bg);
        binding.etDescription!!.setPadding(0, 10, 10, 10)
        //   binding.etDescription.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //    binding.etDescription!!.setPlaceholder("Offer Letter Link")


        binding.etDescription.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val enteredText = binding.etDescription.html  // Get the HTML content

                // Check if the entered text looks like a URL (you might want to improve this validation)
                if (Patterns.WEB_URL.matcher(enteredText).matches()) {
                    // Manually insert the link
                    val url = enteredText
                    val linkText = "Offer Letter Link"
                    binding.etDescription.insertLink(url, linkText)

                    // Clear the editor to avoid duplicate entries
                    binding.etDescription.html = ""

                    // Return true to consume the key event
                    return@OnKeyListener true
                }
            }

            // Return false to allow the default action to be performed
            false
        })



        binding.actionUndo.setOnClickListener { binding.etDescription!!.undo() }

        binding.actionRedo.setOnClickListener { binding.etDescription!!.redo() }

        binding.actionBold.setOnClickListener { binding.etDescription!!.setBold() }

        binding.actionItalic.setOnClickListener { binding.etDescription!!.setItalic() }

        binding.actionStrikethrough.setOnClickListener { binding.etDescription!!.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener { binding.etDescription!!.setUnderline() }


        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                binding.etDescription!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etDescription!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                    isChanged = !isChanged
                }
            })


        binding.actionAlignLeft.setOnClickListener { binding.etDescription!!.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { binding.etDescription!!.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { binding.etDescription!!.setAlignRight() }


        binding.actionInsertBullets.setOnClickListener { binding.etDescription!!.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { binding.etDescription!!.setNumbers() }

        binding.actionInsertLink.setOnClickListener {
            binding.etDescription!!.insertLink(
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
        var replacedContent = htmlContent
            .replace("[First Name]", firstname)
            .replace("[Last Name]", lastname)
            .replace("[Joining Date]", joiningdate)
            .replace("[Salary]", salary)
            .replace("[Offer Letter Link]", offerletterlink)
            .replace("[Client Name]", clientname)
            .replace("[Client Industry]", clientindustry)
            .replace("[Client Website]", clientwebsite)
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
        replacedContent = replacedContent.replace(
            "[Client Facebook]",
            getDrawableTag(com.example.envagemobileapplication.R.drawable.ic_facebook_colored)
        )
        replacedContent = replacedContent.replace(
            "[Client Instagram]",
            getDrawableTag(com.example.envagemobileapplication.R.drawable.ic_instagram_colored)
        )
        replacedContent = replacedContent.replace(
            "[Client Linkedin]",
            getDrawableTag(com.example.envagemobileapplication.R.drawable.ic_linkedin_coloredd)
        )

        // Add more replacements as needed
        return replacedContent
    }

    private fun getDrawableTag(drawableResourceId: Int): String {
        val drawable = resources.getDrawable(drawableResourceId)
        val vectorDrawableCompat =
            VectorDrawableCompat.create(resources, drawableResourceId, null)

        val bitmap: Bitmap = when (drawable) {
            is BitmapDrawable -> Bitmap.createScaledBitmap(
                (drawable as BitmapDrawable).bitmap,
                dpToPx(10),
                dpToPx(10),
                false
            )
            is VectorDrawable -> {
                vectorDrawableCompat?.let {
                    val scaledBitmap = Bitmap.createBitmap(
                        dpToPx(10),
                        dpToPx(10),
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(scaledBitmap)
                    it.setBounds(0, 0, canvas.width, canvas.height)
                    it.draw(canvas)
                    scaledBitmap
                } ?: throw IllegalStateException("Error converting VectorDrawable to Bitmap")
            }
            else -> throw IllegalArgumentException("Unknown drawable type")
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return "<img src=\"data:image/png;base64," + Base64.encodeToString(
            byteArray,
            Base64.DEFAULT
        ) + "\" alt=\"Image\">"
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density + 0.2f).toInt()
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
                                try {
                                    clientname = clientHeaderSummaryResp!!.data.clientInfo.name
                                    clientindustry =
                                        clientHeaderSummaryResp!!.data.clientInfo.industryName
                                    clientwebsite =
                                        clientHeaderSummaryResp!!.data.clientInfo.websiteUrl
                                    clientAddress =
                                        clientHeaderSummaryResp!!.data.clientInfo.primaryAddress1 + " " + clientHeaderSummaryResp!!.data.clientInfo.primaryAddress2
                                    clientLocation =
                                        clientHeaderSummaryResp!!.data.clientInfo.location
                                    clientCountry =
                                        clientHeaderSummaryResp!!.data.clientInfo.country
                                    clientCity =
                                        clientHeaderSummaryResp!!.data.clientInfo.primaryAddressCity
                                    clientState =
                                        clientHeaderSummaryResp!!.data.clientInfo.primaryAddressState
                                    clientZipCode =
                                        clientHeaderSummaryResp!!.data.clientInfo.primaryAddressZipcode
                                    clientPhoneNumber =
                                        clientHeaderSummaryResp!!.data.clientInfo.phone
                                } catch (e: Exception) {
                                }

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

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("MM/dd/yyyy")

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle parsing error here
        }
    }
}
