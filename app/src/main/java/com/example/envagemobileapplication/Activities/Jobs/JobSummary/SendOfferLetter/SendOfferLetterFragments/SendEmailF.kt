package com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter.SendOfferLetterFragments

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.text.*
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
import androidx.fragment.app.activityViewModels
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddJobResponse.AddJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getEmailTemplateResponse.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.SendOfferLetterViewModel
import com.example.envagemobileapplication.databinding.FragmentSendEmailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
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

class SendEmailF : Fragment() {
    lateinit var emailtemplate: MultipartBody.Part
    var descriptiontext: String = ""
    lateinit var globalresp: MutableList<Datum>
    private var clientHeaderSummaryResp: ClientHeaderSummaryResponse? = null
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var templateId: String
    private var jobdeetails: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Data? =
        null
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: SendOfferLetterViewModel by activityViewModels()
    lateinit var binding: FragmentSendEmailBinding

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

    var candidateid = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentSendEmailBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())
        token = tokenManager.getAccessToken().toString()
        clicklisteners()
        observers()
        networkCalls()
        setUpRtf()
        initviews()
        return binding.root
    }

    private fun networkCalls() {
        viewModel.getEmailTemplates(requireContext(), tokenManager.getAccessToken())
        loader.show()
        var jobguid = global.jobHeaderSummary!!.data.jobInfo.guid!!
        viewModel.getjobbyJobid(requireContext(), tokenManager.getAccessToken(), jobguid)
    }

    private fun observers() {

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
                getClientHeaderSummary()
            }
        }
        viewModel.LDEmailTemplate.observe(requireActivity()) {
            loader.hide()

            if (it.data != null) {

                globalresp = it.data
                var items: ArrayList<String> = ArrayList()
                items.add("Select Template")
                for (i in 0 until it.data.size) {

                    items.add(it.data.get(i).templateName)
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

    private fun initviews() {


        val hintSubject = "Subject: *"
        val formattedhintSubject = formatHintWithRedAsterisk(hintSubject)
        binding.textView55.text = formattedhintSubject

        try {

            binding.tvEmailTo.setText(global.candidateEmailAdress)

        } catch (e: Exception) {
        }
        try {

            senderName =
                global.loggedinuserDetails!!.firstName + " " + global.loggedinuserDetails!!.lastName

        } catch (e: Exception) {
        }
        try {


            senderDesignation = global.loggedinuserDetails!!.designation.designationName.toString()

        } catch (e: Exception) {
        }
        try {
            positionname = global.jobHeaderSummary!!.data.jobInfo.positionName


        } catch (e: Exception) {
        }
        try {

            jobIndustry = global.jobHeaderSummary!!.data.jobInfo.industryName.toString()

        } catch (e: Exception) {
        }
        try {


            jobType = global.jobHeaderSummary!!.data.jobInfo.jobType

        } catch (e: Exception) {
        }
        try {


            jobNature = global.jobHeaderSummary!!.data.jobInfo.jobNature


        } catch (e: Exception) {
        }
        try {

            jobFrequency = global.jobHeaderSummary!!.data.jobInfo.jobFrequency

        } catch (e: Exception) {
        }
        try {


            jobaddress =
                global.jobHeaderSummary!!.data.jobInfo.address1 + " " + global.jobHeaderSummary!!.data.jobInfo.address2

        } catch (e: Exception) {


            try {

                jobcity = global.jobHeaderSummary!!.data.jobInfo.city.toString()

            } catch (e: Exception) {
            }
            try {

                jobstate = global.jobHeaderSummary!!.data.jobInfo.state.toString()

            } catch (e: Exception) {
            }
            try {

                joblocation = global.jobHeaderSummary!!.data.jobInfo.location.toString()

            } catch (e: Exception) {
            }
            try {

                jobCountry = global.jobHeaderSummary!!.data.jobInfo.country.toString()

            } catch (e: Exception) {
            }
            try {

                jobzipcode = global.jobHeaderSummary!!.data.jobInfo.zipcode.toString()

            } catch (e: Exception) {
            }
            try {
                firstname = global.firstnameforofferletter.toString()
            } catch (e: Exception) {
            }
            try {
                lastname = global.lastnameforofferletter.toString()

            } catch (e: Exception) {
            }

            offerletterlink = "offer letter link"
            clientFacebook = "www.facebook.com"
            clientInstagram = "www.instagram.com"
            clientLinkedin = "www.facebook.com"

        }
    }

    private fun clicklisteners() {
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

            for (i in 0 until globalresp.size) {

                if (selectedText == globalresp.get(i).templateName.toString()) {
                    templateId = globalresp.get(i).emailTemplateId.toString()
                    var filename =
                        globalresp.get(i).templatePath.toString()
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

        binding.etDescriptionSendEEmail!!.setOnTextChangeListener { text ->

            if (text.contains("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }


        }

        binding.btnSendEmail.setOnClickListener {

            sendEmail(requireContext(), tokenManager.getAccessToken())
        }

    }

    private fun sendEmail(context: Context, accessToken: String?) {
        var subject = binding.etSubjecht.text
        if (!binding.etDescriptionSendEEmail.html
                .isNullOrEmpty() && !subject.isNullOrEmpty()
        ) {
            try {
                descriptiontext = binding.etDescriptionSendEEmail.html.toString()
                if (!descriptiontext.isNullOrBlank()) {
                    val htmlContent = descriptiontext
                    val mediaType = "text/html".toMediaTypeOrNull()
                    val descriptionBody = RequestBody.create(mediaType, htmlContent)
                    val descriptionPart =
                        MultipartBody.Part.createFormData(
                            "body",
                            "description.html",
                            descriptionBody
                        )
                    emailtemplate = descriptionPart
                } else {

                    val htmlContent = "<p></p>"
                    val mediaType = "text/html".toMediaTypeOrNull()
                    val descriptionBody = RequestBody.create(mediaType, htmlContent)
                    val descriptionPart =
                        MultipartBody.Part.createFormData(
                            "body",
                            "description.html",
                            descriptionBody
                        )
                    emailtemplate = descriptionPart
                }


                var body = emailtemplate
                var html = MultipartBody.Part.createFormData("html", descriptiontext)
                var from =
                    MultipartBody.Part.createFormData("from", binding.tvEmailTo.text.toString())
                var to =
                    MultipartBody.Part.createFormData("to", binding.tvEmailTo.text.toString())
                var cc =
                    MultipartBody.Part.createFormData("cc", binding.etCC.text.toString() + "")
                var bcc =
                    MultipartBody.Part.createFormData("bcc", binding.etBCC.text.toString() + "")
                var subject =
                    MultipartBody.Part.createFormData(
                        "subject",
                        binding.etSubjecht.text.toString() + ""
                    )
                var candidateid = MultipartBody.Part.createFormData(
                    "candidateId",
                    global.candidateIdForOfferLetter.toString()
                )
                var jobid =
                    MultipartBody.Part.createFormData(
                        "jobId",
                        global.jobidForOfferLetter.toString()
                    )
                var templateid = MultipartBody.Part.createFormData("TemplateId", "0")
                var offerletterlinkid =
                    MultipartBody.Part.createFormData(
                        "OfferLetterLinkId",
                        global.offerlettertemplateid
                    )
                var offerletterlink = global.offerlettlink!!
                var validtill = MultipartBody.Part.createFormData("validTill", global.validtill)
                var guid =
                    MultipartBody.Part.createFormData(
                        "guid",
                        global.jobHeaderSummary!!.data.jobInfo.guid!!
                    )
                var clientlogo = MultipartBody.Part.createFormData("ClientLogo", "false")
                var clientname = MultipartBody.Part.createFormData("ClientName", "false")
                var clientaddress = MultipartBody.Part.createFormData("ClientAddress", "false")
                var clientwebsite = MultipartBody.Part.createFormData("ClientWebsite", "false")
                var clientfacebook =
                    MultipartBody.Part.createFormData("ClientFacebook", "false")
                var clientinstagram =
                    MultipartBody.Part.createFormData("ClientInstagram", "false")
                var clientlinkedin =
                    MultipartBody.Part.createFormData("ClientLinkedin", "false")
                var clientwitter = MultipartBody.Part.createFormData("ClientTwitter", "false")
                var poweredby = MultipartBody.Part.createFormData("PoweredBy", "false")
                var clientpoc = MultipartBody.Part.createFormData("ClientPoc", "false")

                loader.show()

                ApiUtils.getAPIService(requireContext()).SendEmailOfferLetter(
                    token,
                    body,
                    html,
                    from,
                    to,
                    cc,
                    bcc,
                    subject,
                    candidateid,
                    jobid,
                    templateid,
                    offerletterlinkid,
                    offerletterlink,
                    validtill,
                    guid,
                    clientlogo,
                    clientname,
                    clientaddress,
                    clientwebsite,
                    clientfacebook,
                    clientinstagram,
                    clientlinkedin,
                    clientwitter,
                    poweredby,
                    clientpoc
                )
                    .enqueue(object : Callback<AddJobResponse> {
                        override fun onResponse(
                            call: Call<AddJobResponse>,
                            response: Response<AddJobResponse>
                        ) {
                            loader.hide()
                            if (response.body() != null) {


                                Toast.makeText(
                                    requireContext(),
                                    "Email sent succesfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                requireActivity().finish()

                            } else {
                                Log.i("errormsg", response.message())
                            }
                        }

                        override fun onFailure(
                            call: Call<AddJobResponse>,
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
            if (subject.isNullOrEmpty()) {

                binding.etSubject.error = "Subject is Required."
                binding.etSubject.errorIconDrawable = null// Set the error message
                binding.etSubject.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)

            }


        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        binding.etDescriptionSendEEmail.html = replacedHtmlContent
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

        binding.etDescriptionSendEEmail!!.setEditorHeight(110)
        binding.etDescriptionSendEEmail!!.setEditorFontSize(14)
        binding.etDescriptionSendEEmail!!.setEditorFontColor(Color.BLACK)
        /*  binding.etDescriptionSendEEmail!!.insertLink(
              "https://github.com/wasabeef",
              "Offer Letter Link"
          )*/

        //   binding.etDescriptionSendEEmail.setEditorBackgroundColor(Color.BLUE);
        //   binding.etDescriptionSendEEmail.setBackgroundColor(Color.BLUE);
        //   binding.etDescriptionSendEEmail.setBackgroundResource(R.drawable.bg);
        binding.etDescriptionSendEEmail!!.setPadding(0, 10, 10, 10)
        //   binding.etDescriptionSendEEmail.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //    binding.etDescriptionSendEEmail!!.setPlaceholder("Offer Letter Link")


        binding.etDescriptionSendEEmail.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val enteredText = binding.etDescriptionSendEEmail.html  // Get the HTML content

                // Check if the entered text looks like a URL (you might want to improve this validation)
                if (Patterns.WEB_URL.matcher(enteredText).matches()) {
                    // Manually insert the link
                    val url = enteredText
                    val linkText = "Offer Letter Link"
                    binding.etDescriptionSendEEmail.insertLink(url, linkText)

                    // Clear the editor to avoid duplicate entries
                    binding.etDescriptionSendEEmail.html = ""

                    // Return true to consume the key event
                    return@OnKeyListener true
                }
            }

            // Return false to allow the default action to be performed
            false
        })


        binding.etSubjecht.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //   binding.TIminPayrate.error = null
                // This method is not used in this example.
                binding.etSubject.error = null

            }

            override fun afterTextChanged(editable: Editable?) {
                try {

                } catch (e: Exception) {

                }


            }
        })
        binding.actionUndo.setOnClickListener { binding.etDescriptionSendEEmail!!.undo() }

        binding.actionRedo.setOnClickListener { binding.etDescriptionSendEEmail!!.redo() }

        binding.actionBold.setOnClickListener { binding.etDescriptionSendEEmail!!.setBold() }

        binding.actionItalic.setOnClickListener { binding.etDescriptionSendEEmail!!.setItalic() }

        binding.actionStrikethrough.setOnClickListener { binding.etDescriptionSendEEmail!!.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener { binding.etDescriptionSendEEmail!!.setUnderline() }


        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                binding.etDescriptionSendEEmail!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etDescriptionSendEEmail!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                    isChanged = !isChanged
                }
            })


        binding.actionAlignLeft.setOnClickListener { binding.etDescriptionSendEEmail!!.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { binding.etDescriptionSendEEmail!!.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { binding.etDescriptionSendEEmail!!.setAlignRight() }


        binding.actionInsertBullets.setOnClickListener { binding.etDescriptionSendEEmail!!.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { binding.etDescriptionSendEEmail!!.setNumbers() }

        binding.actionInsertLink.setOnClickListener {
            binding.etDescriptionSendEEmail!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }


    fun replacePlaceholders(htmlContent: String): String {
        // Replace placeholders with your desired values

        var replacedContent = htmlContent
            .replace("[First Name]", global.fn!!)
            .replace("[Last Name]", global.ln!!)
            .replace("[Joining Date]", joiningdate)
            .replace("[Salary]", salary)
            .replace("[Offer Letter Link]", offerletterlink)
            .replace("[Client Name]", clientname)
            .replace("[Client Industry]", clientindustry)
            .replace("[Client Website]", clientwebsite)
            //  .replace("[Client Facebook]", clientFacebook)
            // .replace("[Client Instagram]", clientInstagram)
            //   .replace("[Client Linkedin]", clientLinkedin)
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

/*    private fun getBase64EncodedImage(
        drawablee: Drawable,
        vectordrawablefacebook: VectorDrawableCompat?
    ): String {


        val bitmap: Bitmap = when (drawablee) {
            is BitmapDrawable -> {
                val originalBitmap = (drawablee as BitmapDrawable).bitmap
                // Resize the bitmap to the desired width and height
                Bitmap.createScaledBitmap(originalBitmap, dpToPx(10), dpToPx(10), false)
            }
            is VectorDrawable -> {


                vectordrawablefacebook?.let {
                    val scaledBitmap = Bitmap.createBitmap(
                        dpToPx(10),
                        dpToPx(10),
                        Bitmap.Config.ARGB_8888
                    ).apply {
                        val canvas = Canvas(this)
                        it.setBounds(0, 0, canvas.width, canvas.height)
                        it.draw(canvas)
                    }
                    scaledBitmap
                } ?: throw IllegalStateException("Error converting VectorDrawable to Bitmap")
            }
            else -> throw IllegalArgumentException("Unknown drawable type")
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density + 0.2f).toInt()
    }*/

    private fun getClientHeaderSummary() {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        var clientid = jobdeetails!!.clientId
        try {
            ApiUtils.getAPIService(requireContext()).GetClientHeaderSummary(
                token.toString(), clientid!!
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
                                    clientname = clientHeaderSummaryResp!!.data.clientInfo.name

                                    clientindustry =
                                        clientHeaderSummaryResp!!.data.clientInfo.industryName
                                    clientwebsite =
                                        clientHeaderSummaryResp!!.data.clientInfo.websiteUrl
                                } catch (e: Exception) {
                                }

/*
                                for (i in 0 until clientHeaderSummaryResp!!.data.clientSocialMedia.size){

                                    if (clientHeaderSummaryResp!!.data.clientSocialMedia.get(i).url!=null ||clientHeaderSummaryResp!!.data.clientSocialMedia.get(i).url!=""){
                                        clientFacebook = clientHeaderSummaryResp!!.data.clientSocialMedia.get(i).urlb
                                    }

                                }*/
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ClientHeaderSummaryResponse>,
                        t: Throwable
                    ) {
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

}