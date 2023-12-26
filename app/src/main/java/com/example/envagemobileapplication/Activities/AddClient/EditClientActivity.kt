package com.example.envagemobileapplication.Activities.AddClient

import BaseActivity
import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetSocialMedia
import com.example.envagemobileapplication.Models.RequestModels.Client
import com.example.envagemobileapplication.Models.RequestModels.ClientDataRequestModel
import com.example.envagemobileapplication.Models.RequestModels.SearchRequest
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddBulkOwnerRsp.AddBulkOwnerResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientDescRsp.AddClientDescriptionResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.GetCompanyOnboardingStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCountrylistResponse.GetCountrylistResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateJobsStatusResponse.UpdateJobsStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.ZipCodeResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientSocialMedium
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getdbUsersResponse.GetdbUsersResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityEditClientBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class EditClientActivity : BaseActivity() {

    lateinit var description: MultipartBody.Part
    lateinit var loader: Loader
    lateinit var data: ClientHeaderSummaryResponse
    lateinit var binding: ActivityEditClientBinding
    lateinit var token: String
    lateinit var tokenmanager: TokenManager
    lateinit var socialMediaList: ArrayList<ClientSocialMedium>
    lateinit var payloadList: MutableList<UpdateStatusPayload>
    lateinit var dbuserRespList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getdbUsersResponse.Datum>
    private var selectedItem: View? = null
    var descriptiontext: String = ""
    val bottomSheetFragment = BottomSheetSocialMedia()
    var ownerID = 0

    lateinit var onboardingStatuslist: ArrayList<Datum>

    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        // Define a regular expression pattern to match alphabetic characters and spaces
        val regex = "^[a-zA-Z0-9 ]*$"

        if (source.toString().matches(regex.toRegex()) || source.isEmpty()) {
            if (dstart == 0 && end > start && source[start] == ' ') {
                // Block leading space
                return@InputFilter ""
            }

            // Input is alphabetic, numeric, or space; allow it
            null
        } else {
            // Input contains special characters, reject it
            ""
        }
    }

    val zipcodefilterl = InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {
            if (Character.isWhitespace(source[i])) {
                return@InputFilter ""
            }
        }
        null
    }

    val maxLengthFilter = InputFilter.LengthFilter(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)
        tokenmanager = TokenManager(this)
        token = tokenmanager.getAccessToken().toString()
        setUpRtf()
        getOnBoardingStatuses(
            this@EditClientActivity,
            token
        )
        getCountrylist(this, token)

        getDbUserAdmin(this, token)
        data = Constants.clientSummaryResp

        socialMediaList = ArrayList()

        /*try {
            for (i in 0 until data.data.clientSocialMedia.size) {
                socialMediaList.add(data.data.clientSocialMedia.get(i))
            }
        } catch (e: Exception) {
        }*/

        try {
            if (data.data.clientInfo.name != null) {
                binding.etLegalName.setText(data.data.clientInfo.name.toString())
            }
            if (data.data.clientInfo.visibilityStatus != null) {
                binding.etVisibility.setText(data.data.clientInfo.visibilityStatus.toString())

            }
            if (data.data.clientInfo.onboardingStatus != null) {
                //    binding.etOnboardingStatus.setText(data.data.clientInfo.onboardingStatus.toString())

            }
            if (data.data.clientInfo.ownerName != null) {
                //   binding.etOwnerName.setText(data.data.clientInfo.ownerName.toString())

            }
            if (data.data.clientInfo.phone != null) {
                val originalNumber =
                    data.data.clientInfo.phone // Replace with your actual phone number
                val formattedNumber = formatPhoneNumber(originalNumber)
                binding.etPhoneNumber.text =
                    Editable.Factory.getInstance().newEditable(formattedNumber)
                //    binding.etPhoneNumber.text = formattedNumber
                //    binding.etPhoneNumber.setText(data.data.clientInfo.phone.toString())

            }

            if (!data.data?.clientInfo?.description.isNullOrEmpty()) {
                var filename =
                    data.data?.clientInfo?.description.toString()
                var baseurlnew =
                    "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                try {
                    loadJobDescriptionContent(baseurlnew)
                } catch (e: Exception) {
                }

            }
            if (data.data.clientInfo.primaryAddress1 != null) {
                binding.etAdress.setText(data.data.clientInfo.primaryAddress1.toString())
            }
            if (data.data.clientInfo.country != null) {
                //  binding.etCountry.setText(data.data.clientInfo.country.toString())
            }
            if (data.data.clientInfo.zipcode != null) {
                binding.etZipcode.setText(data.data.clientInfo.zipcode.toString())
            }

            if (data.data.clientInfo.city != null) {
                binding.etCity.setText(data.data.clientInfo.city.toString())
            }
            if (data.data.clientInfo.state != null) {
                binding.etState.setText(data.data.clientInfo.state.toString())
            }

            if (data.data.clientInfo.location != null) {
                binding.etLocation.setText(data.data.clientInfo.location.toString())
            }

            if (data.data.clientInfo.websiteUrl != null) {
                binding.etWebsite.setText(data.data.clientInfo.websiteUrl.toString())
            }

            if (data.data.clientInfo.industryName != null) {
                binding.etIndustry.setText(data.data.clientInfo.industryName.toString())

            }
        } catch (e: Exception) {

        }

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed in this case
            }

            override fun afterTextChanged(s: Editable?) {
                val phoneNumber = s.toString()
                val formattedPhoneNumber = formatPhoneNumber(phoneNumber)
                if (phoneNumber != formattedPhoneNumber) {
                    binding.etPhoneNumber.removeTextChangedListener(this)
                    binding.etPhoneNumber.text =
                        Editable.Factory.getInstance().newEditable(formattedPhoneNumber)
                    binding.etPhoneNumber.setSelection(formattedPhoneNumber.length)
                    binding.etPhoneNumber.addTextChangedListener(this)
                }
            }
        })

        binding.socialmediafield.setOnClickListener {
            if (bottomSheetFragment.isAdded) {
                return@setOnClickListener
            } else {
                bottomSheetFragment.show(
                    supportFragmentManager,
                    bottomSheetFragment.tag
                )


            }
        }

        binding.ivCross.setOnClickListener {
            val intent = Intent(this@EditClientActivity, ClientSummaryActivity::class.java)
            finish()
            startActivity(intent)
        }

        val hint = "Client Legal Name *"
        val formattedHint = formatHintWithRedAsterisk(hint)
        binding.clientLegalName.hint = formattedHint

        binding.etLegalName.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccClinetlegalname.visibility = View.VISIBLE
            false
        })

        val hintzipCode = "Zip Code *"
        val formattedHintZipcode = formatHintWithRedAsterisk(hintzipCode)
        binding.textInputLayout8.hint = formattedHintZipcode
        binding.etZipcode.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccZipcode.visibility = View.VISIBLE
            false
        })

        val addressshint = "Address *"
        val addressndupdated = formatHintWithRedAsterisk(addressshint)
        binding.textInputLayout6.hint = addressndupdated
        binding.etAdress.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccAdress.visibility = View.VISIBLE
            false
        })


        binding.etVisibility.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccVisibilityicon.visibility = View.VISIBLE
            false
        })


        binding.spinerOnboardingStatus.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccOnboardingstatus.visibility = View.VISIBLE
            false
        })


        binding.etIndustry.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccClientindustry.visibility = View.VISIBLE
            false
        })

        binding.etOwnerSpinner.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccOwnername.visibility = View.VISIBLE
            false
        })

        binding.etPhoneNumber.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccPhonenumber.visibility = View.VISIBLE
            false
        })


        binding.etCountry.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccCountry.visibility = View.VISIBLE
            false
        })


        binding.etLocation.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccLocation.visibility = View.VISIBLE
            false
        })

        binding.etWebsite.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccWebsite.visibility = View.VISIBLE
            false
        })
        binding.etDescription.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccDescriptionticcross.visibility = View.VISIBLE
            false
        })

        binding.ccDescription.setOnTouchListener(OnTouchListener { v, event ->
            binding.ccDescriptionticcross.visibility = View.VISIBLE
            false
        })



        binding.etWebsite.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.textInputLayout11.error = null
                } else if (isValidUrl(enteredText)) {
                    // URL is valid, you can update UI or perform other actions
                    binding.textInputLayout11.error = null // Clear any previous error
                } else {

                    binding.textInputLayout11.error = "Enter valid url"
                    binding.textInputLayout11.errorIconDrawable = null// Set the error message
                    binding.textInputLayout11.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                    // URL is not valid, display an error message
                    /*binding.etClientWebsite.error = "Invalid URL"*/
                }

            }
        })


        binding.etAdress.filters = arrayOf(filter)
        binding.etLegalName.filters = arrayOf(filter)
        binding.etZipcode.filters = arrayOf(zipcodefilterl, maxLengthFilter)
        binding.ivTicLegalName.setOnClickListener {
            if (binding.etLegalName.text.toString() != "") {

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                val name = UpdateStatusPayload("add", "/Name", binding.etLegalName.text.toString())
                callUpdateClientApi(name, binding.ccClinetlegalname)
            } else {
                binding.clientLegalName.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                binding.clientLegalName.errorIconDrawable = null
                binding.clientLegalName.error = "Client Legal Name can't be empty"
            }


        }
        binding.ivCrossLegalName.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.name != null) {
                binding.etLegalName.setText(data.data.clientInfo.name.toString())
            } else {
                binding.etLegalName.setText("")
            }
            binding.ccClinetlegalname.visibility = View.GONE
        }

        binding.ivCrossdescription.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (!data.data?.clientInfo?.description.isNullOrEmpty()) {
                var filename =
                    data.data?.clientInfo?.description.toString()
                var baseurlnew =
                    "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                try {
                    loadJobDescriptionContent(baseurlnew)
                } catch (e: Exception) {
                }

            } else {
                binding.etDescription.html = ""
            }
            binding.ccDescriptionticcross.visibility = View.GONE

        }

        binding.ivTicVisibilityStatuss.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            val visibilityStatus = UpdateStatusPayload(
                "add",
                "/VisibilityStatus",
                binding.etVisibility.text.toString()
            )

            callUpdateClientApi(visibilityStatus, binding.ccVisibilityicon)
        }
        binding.ivCrossVisibilityStatuss.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.ccVisibilityicon.visibility = View.GONE
            if (data.data.clientInfo.visibilityStatus != null) {
                binding.etVisibility.setText(data.data.clientInfo.visibilityStatus.toString())

            } else {
                binding.etVisibility.setText("")
            }
        }


        binding.ivTicOnboardingStatus.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            var statusID = 0
            for (i in 0 until onboardingStatuslist.size) {
                if (onboardingStatuslist.get(i).onboardingStatusName.equals(binding.spinerOnboardingStatus.text.toString())) {

                    statusID = onboardingStatuslist.get(i).companyOnboardingStatusId

                }
            }
            val onboardingstatus = UpdateStatusPayload(
                "add",
                "/OnboardingStatusId",
                statusID.toString()
            )



            callUpdateClientApi(onboardingstatus, binding.ccOnboardingstatus)
        }
        binding.ivCrossOnboardingStatus.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (!Constants.clientSummaryResp.data?.clientInfo?.onboardingStatus.isNullOrEmpty()) {
                binding.spinerOnboardingStatus.setText(Constants.clientSummaryResp.data?.clientInfo?.onboardingStatus.toString())
            }

            binding.spinerOnboardingStatus.dismissDropDown()
            binding.ccOnboardingstatus.visibility = View.GONE
        }

        binding.ivTicClientDepartment.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            val industry = UpdateStatusPayload(
                "add",
                "/IndustryName",
                binding.etIndustry.text.toString()
            )
            callUpdateClientApi(industry, binding.ccClientindustry)

        }
        binding.ivCrossClientDepartment.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.industryName != null) {
                binding.etIndustry.setText(data.data.clientInfo.industryName.toString())

            } else {
                binding.etIndustry.setText("")
            }
            binding.ccClientindustry.visibility = View.GONE
        }

        binding.ivTicOwnerName.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                val client = Client(
                    clientId = Constants.clientid!!.toInt()
                )


                var ownername = binding.etOwnerSpinner.text.toString()


                ownerID = (dbuserRespList.get(ownerID).userId)


                val clientData = ClientDataRequestModel(
                    clients = listOf(client),
                    owner = ownerID
                )


                callAddBulkOwnerApi(clientData)
            } catch (e: Exception) {
            }


        }
        binding.ivCrossOwnerName.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (!Constants.clientSummaryResp.data?.clientInfo?.ownerName.isNullOrEmpty()) {
                binding.etOwnerSpinner.setText(Constants.clientSummaryResp.data?.clientInfo?.ownerName.toString())

            }
            binding.etOwnerSpinner.dismissDropDown()
            binding.ccOwnername.visibility = View.GONE
        }

        binding.ivTicPhone.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                val phoneNumber = binding.etPhoneNumber.text.toString()
                val formattedPhoneNumber = phoneNumber.replace("-", "").replace(" ", "")
                val phonenumber = UpdateStatusPayload(
                    "add",
                    "/Phone", formattedPhoneNumber

                )

                callUpdateClientApi(phonenumber, binding.ccPhonenumber)
            } catch (e: Exception) {

            }
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val phoneNumber = binding.etPhoneNumber.text.toString()
            val formattedPhoneNumber = phoneNumber.replace("-", "").replace(" ", "")
            val phonenumber = UpdateStatusPayload(
                "add",
                "/Phone", formattedPhoneNumber

            )

            callUpdateClientApi(phonenumber, binding.ccPhonenumber)
        }

        binding.ivTicdescription.setOnClickListener {

            callAddDescriptionApi(binding.ccDescriptionticcross)

        }
        binding.ivCrossPhone.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.phone != null) {
                val originalNumber =
                    data.data.clientInfo.phone // Replace with your actual phone number
                val formattedNumber = formatPhoneNumber(originalNumber)
                binding.etPhoneNumber.text =
                    Editable.Factory.getInstance().newEditable(formattedNumber)
                //    binding.etPhoneNumber.text = formattedNumber
                //    binding.etPhoneNumber.setText(data.data.clientInfo.phone.toString())

            } else {
                binding.etPhoneNumber.setText("")
            }
            binding.ccPhonenumber.visibility = View.GONE
        }

        binding.ivTicAddress.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                if (binding.etAdress.text.toString() != "") {
                    val primaryAdress = UpdateStatusPayload(
                        "add",
                        "/PrimaryAddress1",
                        binding.etAdress.text.toString()
                    )
                    callUpdateClientApi(primaryAdress, binding.ccAdress)
                } else {
                    binding.textInputLayout6.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                    binding.textInputLayout6.errorIconDrawable = null
                    binding.textInputLayout6.error = "Address can't be empty"
                }
            } catch (e: Exception) {

            }


        }
        binding.ivCrossAddress.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.primaryAddress1 != null) {
                binding.etAdress.setText(data.data.clientInfo.primaryAddress1.toString())
            } else {
                binding.etAdress.setText("")
            }
            binding.ccAdress.visibility = View.GONE
        }

        binding.ivTicCountry.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                val country = UpdateStatusPayload(
                    "add",
                    "/Country",
                    binding.etCountry.text.toString()
                )
                callUpdateClientApi(country, binding.ccCountry)
            } catch (e: Exception) {

            }

        }
        binding.ivCrossCountry.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.ccCountry.visibility = View.GONE
            binding.etCountry.dismissDropDown()
        }

        binding.ivTicZipcode.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                if (binding.etZipcode.text.toString() != "" && binding.etZipcode.text.length >= 5) {
                    var zipcodeEnterd = binding.etZipcode.text.toString()
                    getzipcityStatelist(zipcodeEnterd, binding.ccZipcode)
                } else if (binding.etZipcode.text.length < 5) {
                    binding.textInputLayout8.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                    binding.textInputLayout8.errorIconDrawable = null
                    binding.textInputLayout8.error = "Zip Code must be 5 digits"
                } else {
                    binding.textInputLayout8.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                    binding.textInputLayout8.errorIconDrawable = null
                    binding.textInputLayout8.error = "Zip Code can't be empty"
                }
            } catch (e: Exception) {
            }


        }
        binding.ivCrossZipcode.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.zipcode != null) {
                binding.etZipcode.setText(data.data.clientInfo.zipcode.toString())
            } else {
                binding.etZipcode.setText("")
            }
            binding.ccZipcode.visibility = View.GONE
        }

        binding.ivTicLocation.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                val location = UpdateStatusPayload(
                    "add",
                    "/Location",
                    binding.etLocation.text.toString()
                )
                callUpdateClientApi(location, binding.ccLocation)
            } catch (e: Exception) {

            }

        }
        binding.ivCrossLocation.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            if (data.data.clientInfo.location != null) {
                binding.etLocation.setText(data.data.clientInfo.location.toString())
            } else {
                binding.etLocation.setText("")
            }
            binding.ccLocation.visibility = View.GONE
        }

        binding.ivTicWebsite.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                val wesite = UpdateStatusPayload(
                    "add",
                    "/WebsiteUrl",
                    binding.etWebsite.text.toString()
                )
                callUpdateClientApi(wesite, binding.ccWebsite)
            } catch (e: Exception) {

            }

        }
        binding.ivCrossWebsite.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            if (data.data.clientInfo.websiteUrl != null) {
                binding.etWebsite.setText(data.data.clientInfo.websiteUrl.toString())
            } else {
                binding.etWebsite.setText("")
            }
            binding.ccWebsite.visibility = View.GONE
        }

        binding.spinerOnboardingStatus.setOnClickListener {
            if (binding.spinerOnboardingStatus.isPopupShowing) {
                binding.spinerOnboardingStatus.dismissDropDown()
            } else {
                binding.spinerOnboardingStatus.showDropDown()
            }
        }
        binding.etCountry.setOnClickListener {
            binding.etCountry.showDropDown()
        }

        binding.etOwnerSpinner.setOnClickListener {
            binding.etOwnerSpinner.showDropDown()
        }

        selectItem(binding.tvSpecifications)
        binding.tvSpecifications.setOnClickListener {
            binding.ccDescriptionticcross.visibility = View.INVISIBLE
            binding.ccDescription.visibility = View.GONE
            binding.ccSpecifiations.visibility = View.VISIBLE

            selectItem(binding.tvSpecifications)

        }

        binding.tvDescription.setOnClickListener {

            binding.ccDescription.visibility = View.VISIBLE
            binding.ccSpecifiations.visibility = View.GONE
            // binding.tvSpecifications.setBackgroundColor(resources.getColor(R.color.white))
            selectItem(binding.tvDescription)

            Log.i("sadsad", "sfsfdf ")
            // binding.tvSpecifications.background =

        }

        binding.etLegalName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                if (text.length > 100) {
                    // If the text exceeds 100 characters, truncate it to 100 characters
                    val truncatedText = text.substring(0, 100)
                    binding.etLegalName.setText(truncatedText)
                    binding.etLegalName.setSelection(truncatedText.length)
                }
                /*       if (text.contains(" ")) {
                           // Remove any space characters
                           val newText = text.replace(" ", "")
                           binding.etLegalName.setText(newText)
                           binding.etLegalName.setSelection(newText.length)
                       }*/

                binding.clientLegalName.error = null

            }
        })
        binding.etIndustry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                if (text.length > 200) {
                    // If the text exceeds 100 characters, truncate it to 100 characters
                    val truncatedText = text.substring(0, 200)
                    binding.etIndustry.setText(truncatedText)
                    binding.etIndustry.setSelection(truncatedText.length)
                }

            }
        })
        binding.etAdress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                if (text.length > 100) {
                    // If the text exceeds 100 characters, truncate it to 100 characters
                    val truncatedText = text.substring(0, 100)
                    binding.etAdress.setText(truncatedText)
                    binding.etAdress.setSelection(truncatedText.length)
                }
                binding.textInputLayout6.error = null
            }
        })

        binding.etDescription.setOnTouchListener(OnTouchListener { v, event ->
            binding.etDescription.setInputEnabled(true)
            binding.ccDescriptionticcross.visibility = View.VISIBLE
            false
        })
        binding.etDescription.setOnTextChangeListener { text ->
            val leng: Int = text.length
            if (leng >= 500) {
                binding.etDescription.setInputEnabled(false)
            } else {
                binding.etDescription.setInputEnabled(true)
            }
            if (text.startsWith("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }

        }


        binding.textInputLayout12.editText?.isEnabled = false
        binding.textInputLayout9.editText?.isEnabled = false
        binding.textInputLayout.editText?.isEnabled = false


        binding.etZipcode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                /*  if (text.contains(" ")) {
                      // Remove any space characters
                      val newText = text.replace(" ", "")
                      binding.etZipcode.setText(newText)
                      binding.etZipcode.setSelection(newText.length)
                  }*/

                binding.textInputLayout8.error = null
            }
        })


    }

    private fun callAddDescriptionApi(ccDescriptionticcross: ConstraintLayout) {
        try {

            if (!descriptiontext.isNullOrBlank()) {
                val htmlContent = descriptiontext
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = htmlContent.toRequestBody(mediaType)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "description",
                        "description.html",
                        descriptionBody
                    )
                description = descriptionPart
            } else {
                val htmlContent = "<>"
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = htmlContent.toRequestBody(mediaType)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "description",
                        "description.html",
                        descriptionBody
                    )
                description = descriptionPart
            }
            loader.show()

            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            var clientid = Constants.clientid
            ApiUtils.getAPIService(this).addClientDescription(
                token.toString(),
                description, clientid!!
            )
                .enqueue(object : Callback<AddClientDescriptionResponse> {
                    override fun onResponse(
                        call: Call<AddClientDescriptionResponse>,
                        response: Response<AddClientDescriptionResponse>
                    ) {
                        if (response.body() != null) {
                            ccDescriptionticcross.visibility = View.GONE
                            loader.hide()
                            val toast = Toast.makeText(
                                this@EditClientActivity,
                                "Client description updated successfully",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        } else {
                            loader.hide()
                        }
                    }

                    override fun onFailure(
                        call: Call<AddClientDescriptionResponse>,
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


    private fun getzipcityStatelist(zipcodeEnterd: String, ccZipcode: ConstraintLayout) {
        var tokenmanager: TokenManager = TokenManager(this)
        var token = tokenmanager.getAccessToken()

        val zipcode = zipcodeEnterd

        try {
            ApiUtils.getAPIService(this).getzipcityStatelist(
                token.toString(), zipcode
            )
                .enqueue(object : Callback<ZipCodeResponse> {
                    override fun onResponse(
                        call: Call<ZipCodeResponse>,
                        response: Response<ZipCodeResponse>
                    ) {
                        if (response.body()?.data!!.size > 0) {

                            payloadList = ArrayList()
                            var zipcodedata: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.Datum> =
                                ArrayList()
                            for (i in 0 until response.body()!!.data.size) {

                                zipcodedata.add(response.body()!!.data.get(i))
                            }
                            val zipcode = UpdateStatusPayload(
                                "add",
                                "/Zipcode",
                                zipcodedata.get(0).zipcode
                            )
                            val city = UpdateStatusPayload(
                                "add",
                                "/City",
                                zipcodedata.get(0).cityName
                            )
                            val state = UpdateStatusPayload(
                                "add",
                                "/State",
                                zipcodedata.get(0).stateName + "|" + zipcodedata.get(
                                    0
                                ).stateCode
                            )
                            payloadList.add(zipcode)
                            payloadList.add(city)
                            payloadList.add(state)

                            callUpdateClientApizipCode(zipcode, city, state, ccZipcode)

                            //    callUpdateClientApi(wesite)
                        } else {
                            val rootView = binding.root

                            val message = "No Record Found"
                            val duration = Snackbar.LENGTH_SHORT

                            val snackbar = Snackbar.make(rootView, message, duration)
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    this@EditClientActivity,
                                    com.example.envagemobileapplication.R.color.red
                                )
                            )
                            snackbar.show()
                        }
                    }

                    override fun onFailure(call: Call<ZipCodeResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exception", ex.toString())
        }
    }

    private fun callUpdateClientApizipCode(
        zipcode: UpdateStatusPayload,
        city: UpdateStatusPayload,
        state: UpdateStatusPayload,
        ccZipcode: ConstraintLayout
    ) {
        try {

            loader.show()


            payloadList = ArrayList()
            payloadList.add(zipcode)
            payloadList.add(city)
            payloadList.add(state)
            var load = payloadList

            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            var clientid = Constants.clientid

            ApiUtils.getAPIService(this).UpdateClient(
                token.toString(), payloadList, clientid!!
            )
                .enqueue(object : Callback<UpdateJobsStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateJobsStatusResponse>,
                        response: Response<UpdateJobsStatusResponse>
                    ) {
                        if (response.body() != null) {
                            ccZipcode.visibility = View.GONE
                            getClientHeaderSummary()
                        }
                    }

                    override fun onFailure(
                        call: Call<UpdateJobsStatusResponse>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun getClientHeaderSummary() {

        var tokenmanager: TokenManager = TokenManager(this)
        var token = tokenmanager.getAccessToken()

        val clientId =
            Constants.clientid // Replace with the actual client ID
        try {
            ApiUtils.getAPIService(this).GetClientHeaderSummary(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {
                        if (response.body() != null) {


                            loader.hide()


                            val toast = Toast.makeText(
                                this@EditClientActivity,
                                "Client information has been updated successfully",
                                Toast.LENGTH_LONG
                            )

                            toast.show()




                            Constants.clientSummaryResp = response.body()!!

                            try {
                                for (i in 0 until response.body()!!.data.clientSocialMedia.size) {
                                    socialMediaList.add(data.data.clientSocialMedia.get(i))
                                }
                            } catch (e: Exception) {
                            }


                            if (!response.body()?.data?.clientInfo?.name.toString()
                                    .isNullOrEmpty()
                            ) {
                                val name = response.body()?.data?.clientInfo?.name.toString()
                                    .replace("\"", "")

                                binding.etLegalName.setText(name)
                            }
                            if (!response.body()?.data?.clientInfo?.visibilityStatus.isNullOrEmpty()) {

                                binding.etVisibility.setText(response.body()?.data?.clientInfo?.visibilityStatus.toString())
                            }

                            if (!response.body()?.data?.clientInfo?.industryName.isNullOrEmpty()) {
                                binding.etIndustry.setText(response.body()?.data?.clientInfo?.industryName.toString())
                            }
                            if (!response.body()?.data?.clientInfo?.onboardingStatus.isNullOrEmpty()) {
                                binding.spinerOnboardingStatus.setText(response.body()?.data?.clientInfo?.onboardingStatus.toString())
                            }

                            if (!response.body()?.data?.clientInfo?.ownerName.isNullOrEmpty()) {
                                binding.etOwnerSpinner.setText(response.body()?.data?.clientInfo?.ownerName.toString())

                            }

                            if (!response.body()?.data?.clientInfo?.phone.isNullOrEmpty()) {

                                val inputPhoneNumber =
                                    response.body()?.data?.clientInfo?.phone.toString()
                                val formattedPhoneNumber =
                                    formatToUSAPhoneNumber(inputPhoneNumber)
                                binding.etPhoneNumber.setText(formattedPhoneNumber)

                            }

                            if (!response.body()?.data?.clientInfo?.primaryAddress1.isNullOrEmpty()) {
                                binding.etAdress.setText(response.body()?.data?.clientInfo?.primaryAddress1.toString() + response.body()?.data?.clientInfo?.primaryAddress2.toString())

                            }

                            if (!response.body()?.data?.clientInfo?.country.isNullOrEmpty()) {
                                binding.etCountry.setText(response.body()?.data?.clientInfo?.country.toString())

                            }
                            if (!response.body()?.data?.clientInfo?.city.isNullOrEmpty()) {
                                binding.etCity.setText(response.body()?.data?.clientInfo?.city.toString())

                            }
                            if (!response.body()?.data?.clientInfo?.zipcode.isNullOrEmpty()) {
                                binding.etZipcode.setText(response.body()?.data?.clientInfo?.zipcode.toString())

                            }
                            if (!response.body()?.data?.clientInfo?.state.isNullOrEmpty()) {
                                binding.etState.setText(response.body()?.data?.clientInfo?.state.toString())

                            }

                            if (!response.body()?.data?.clientInfo?.location.isNullOrEmpty()) {
                                binding.etLocation.setText(response.body()?.data?.clientInfo?.location.toString())

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
            Log.i("exception", ex.toString())
        }

    }

    fun formatToUSAPhoneNumber(inputPhoneNumber: String): String {
        val digitsOnly = inputPhoneNumber.replace(Regex("\\D"), "")

        if (digitsOnly.length == 10) {
            return digitsOnly.substring(0, 3) + "-" +
                    digitsOnly.substring(3, 6) + "-" +
                    digitsOnly.substring(6)
        } else {
            // Return the input as is if it doesn't match the expected format
            return digitsOnly
        }
    }

    private fun selectItem(item: View) {
        // Unhighlight the previously selected item
        selectedItem?.setBackgroundResource(com.example.envagemobileapplication.R.drawable.btn_white_radius)

        // Highlight the selected item
        item.setBackgroundResource(com.example.envagemobileapplication.R.drawable.gray_bg)

        // Update the currently selected item
        selectedItem = item
    }

    private fun callAddBulkOwnerApi(clientData: ClientDataRequestModel) {
        try {
            loader.show()

            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()

            ApiUtils.getAPIService(this).addBulkOwners(
                clientData,
                token.toString()
            )
                .enqueue(object : Callback<AddBulkOwnerResponse> {
                    override fun onResponse(
                        call: Call<AddBulkOwnerResponse>,
                        response: Response<AddBulkOwnerResponse>
                    ) {
                        if (response.body() != null) {
                            loader.hide()
                            val toast = Toast.makeText(
                                this@EditClientActivity,
                                "Ownership has been updated against the selected clients",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }

                    override fun onFailure(
                        call: Call<AddBulkOwnerResponse>,
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

    private fun getDbUserAdmin(editClientActivity: EditClientActivity, token: String) {
        try {
            loader.show()


            val searchRequest = SearchRequest(
                search = "a",
                isGuest = false
            )

            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()


            ApiUtils.getAPIService(this).getdbusers(
                searchRequest,
                token.toString()
            )
                .enqueue(object : Callback<GetdbUsersResponse> {
                    override fun onResponse(
                        call: Call<GetdbUsersResponse>,
                        response: Response<GetdbUsersResponse>
                    ) {
                        if (response.body() != null) {

                            loader.hide()



                            dbuserRespList =
                                ArrayList()
                            var items: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()!!.data.size) {
                                dbuserRespList.add(response.body()!!.data.get(i))
                                items.add(
                                    response.body()!!.data.get(i).firstName + " " + response.body()!!.data.get(
                                        i
                                    ).lastName
                                )
                            }


                            val adapter = customadapter(
                                this@EditClientActivity,
                                R.layout.simple_spinner_item,
                                items
                            )

                            val ownername = data.data.clientInfo.ownerName
                            val defaultPosition = items.indexOf(ownername)

                            if (defaultPosition >= 0) {
                                binding.etOwnerSpinner.setText(
                                    ownername,
                                    false
                                ) // Set text without triggering selection
                                adapter.setSelectedPosition(defaultPosition) // Highlight the default item
                            }
                            binding.etOwnerSpinner.setOnItemClickListener { _, _, position, _ ->
                                // Update the selected position in the adapter
                                ownerID = position
                                adapter.setSelectedPosition(position)
                            }
                            binding.etOwnerSpinner.setAdapter(adapter)



                            binding.etOwnerSpinner.setOnClickListener {
                                if (binding.etOwnerSpinner.isPopupShowing) {
                                    binding.etOwnerSpinner.dismissDropDown()

                                } else {
                                    dbuserRespList =
                                        ArrayList()
                                    var items: ArrayList<String> = ArrayList()
                                    for (i in 0 until response.body()!!.data.size) {
                                        dbuserRespList.add(response.body()!!.data.get(i))
                                        items.add(
                                            response.body()!!.data.get(i).firstName + " " + response.body()!!.data.get(
                                                i
                                            ).lastName
                                        )
                                    }


                                    val adapter = customadapter(
                                        this@EditClientActivity,
                                        R.layout.simple_spinner_item,
                                        items
                                    )

                                    val ownername = data.data.clientInfo.ownerName
                                    val defaultPosition = items.indexOf(ownername)

                                    if (defaultPosition >= 0) {
                                        binding.etOwnerSpinner.setText(
                                            ownername,
                                            false
                                        ) // Set text without triggering selection
                                        adapter.setSelectedPosition(defaultPosition) // Highlight the default item
                                    }
                                    binding.etOwnerSpinner.setOnItemClickListener { _, _, position, _ ->
                                        // Update the selected position in the adapter
                                        ownerID = position
                                        adapter.setSelectedPosition(position)
                                    }
                                    binding.etOwnerSpinner.setAdapter(adapter)
                                    binding.etOwnerSpinner.showDropDown()
                                }
                            }


                            val compareValue = data.data.clientInfo.ownerName
                            if (compareValue != null) {
                                val adapter =
                                    binding.etOwnerSpinner.adapter as ArrayAdapter<String>
                                val position = adapter.getPosition(compareValue)
                                if (position != -1) {
                                    binding.etOwnerSpinner.setText(
                                        compareValue,
                                        false
                                    ) // Set the text without triggering an item selection event
                                    binding.etOwnerSpinner.setSelection(position) // Set the selection
                                }
                            }

                        }
                    }

                    override fun onFailure(
                        call: Call<GetdbUsersResponse>,
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

    private fun callUpdateClientApi(patch: UpdateStatusPayload, ccticcross: ConstraintLayout) {
        try {

            loader.show()

            payloadList = ArrayList()
            payloadList.add(patch)


            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            var clientid = Constants.clientid

            ApiUtils.getAPIService(this).UpdateClient(
                token.toString(), payloadList, clientid!!
            )
                .enqueue(object : Callback<UpdateJobsStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateJobsStatusResponse>,
                        response: Response<UpdateJobsStatusResponse>
                    ) {
                        if (response.body() != null) {

                            loader.hide()
                            ccticcross.visibility = View.GONE

                            if (response.body()!!.data.alreadyExist.equals(true)) {

                                Snackbar.make(
                                    binding.root,
                                    "Client already exists.",
                                    Snackbar.LENGTH_LONG
                                ).apply {

                                    setTextColor(
                                        ContextCompat.getColor(
                                            this@EditClientActivity,
                                            com.example.envagemobileapplication.R.color.white
                                        )
                                    )
                                    setBackgroundTint(
                                        ContextCompat.getColor(
                                            this@EditClientActivity,
                                            com.example.envagemobileapplication.R.color.orange
                                        )
                                    )
                                    show() // Show the Snackbar
                                }
                            } else {
                                val toast = Toast.makeText(
                                    this@EditClientActivity,
                                    "Client information has been updated successfully",
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<UpdateJobsStatusResponse>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun getCountrylist(context: EditClientActivity, token: String) {
        try {
            ApiUtils.getAPIService(this).GetCountryList(

                token.toString(),
            )
                .enqueue(object : Callback<GetCountrylistResponse> {
                    override fun onResponse(
                        call: Call<GetCountrylistResponse>,
                        response: Response<GetCountrylistResponse>
                    ) {
                        if (response.body() != null) {


                            var countryList: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()?.data!!.size) {
                                countryList.add(
                                    response.body()!!.data.get(i).name
                                )


                            }

                            val adapter = customadapter(
                                this@EditClientActivity,
                                R.layout.simple_spinner_item,
                                countryList
                            )

// Find the position of "Pakistan" in your countryList
                            val defaultCountry = data.data.clientInfo.country
                            val defaultPosition = countryList.indexOf(defaultCountry)

// Set the default selection
                            if (defaultPosition >= 0) {
                                binding.etCountry.setText(
                                    defaultCountry,
                                    false
                                ) // Set text without triggering selection
                                adapter.setSelectedPosition(defaultPosition) // Highlight the default item
                            }

                            // Set the layout style for the drop-down list (optional)
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                            binding.etCountry.setAdapter(adapter)


                            binding.etCountry.setOnItemClickListener { _, _, position, _ ->
                                // Update the selected position in the adapter
                                adapter.setSelectedPosition(position)
                            }


                            // Set the adapter to the Spinner
                            if (data.data.clientInfo.country != null) {
                                var compareValue = data.data.clientInfo.country
                                if (compareValue != null) {
                                    val adapter =
                                        binding.etCountry.adapter as ArrayAdapter<String>
                                    val position = adapter.getPosition(compareValue)
                                    if (position != -1) {
                                        binding.etCountry.setText(
                                            compareValue,
                                            false
                                        ) // Set the text without triggering an item selection event
                                        try {
                                            binding.etCountry.setSelection(position)
                                        } catch (e: Exception) {
                                        }
                                        // Set the selection
                                    }
                                }
                            }


                        }
                    }

                    override fun onFailure(
                        call: Call<GetCountrylistResponse>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun getOnBoardingStatuses(editClientActivity: EditClientActivity, token: String) {


        try {
            ApiUtils.getAPIService(this).GetCompanyOnboardingStatusResponse(

                token.toString(),
            )
                .enqueue(object : Callback<GetCompanyOnboardingStatusResponse> {
                    override fun onResponse(
                        call: Call<GetCompanyOnboardingStatusResponse>,
                        response: Response<GetCompanyOnboardingStatusResponse>
                    ) {
                        if (response.body() != null) {
                            val compareValue = data.data.clientInfo.onboardingStatus


                            onboardingStatuslist = ArrayList()
                            for (i in 0 until response.body()?.data!!.size) {
                                onboardingStatuslist.add(response.body()?.data!!.get(i))
                            }


                            var items: ArrayList<String> = ArrayList()
                            for (i in 0 until onboardingStatuslist.size) {
                                items.add(onboardingStatuslist.get(i).onboardingStatusName)
                            }
                            // Create an ArrayAdapter using a string array or a list of items


                            val adapter = customadapter(
                                this@EditClientActivity,
                                R.layout.simple_spinner_item,
                                items
                            )

                            val defaultonbosrdingstatus = data.data.clientInfo.onboardingStatus
                            val defaultPosition = items.indexOf(defaultonbosrdingstatus)

                            if (defaultPosition >= 0) {
                                binding.spinerOnboardingStatus.setText(
                                    defaultonbosrdingstatus,
                                    false
                                ) // Set text without triggering selection
                                adapter.setSelectedPosition(defaultPosition) // Highlight the default item
                            }


                            binding.spinerOnboardingStatus.setOnClickListener {
                                if (binding.spinerOnboardingStatus.isPopupShowing) {
                                    binding.spinerOnboardingStatus.dismissDropDown()
                                } else {
                                    val compareValue = data.data.clientInfo.onboardingStatus


                                    onboardingStatuslist = ArrayList()
                                    for (i in 0 until response.body()?.data!!.size) {
                                        onboardingStatuslist.add(response.body()?.data!!.get(i))
                                    }


                                    var items: ArrayList<String> = ArrayList()
                                    for (i in 0 until onboardingStatuslist.size) {
                                        items.add(onboardingStatuslist.get(i).onboardingStatusName)
                                    }
                                    // Create an ArrayAdapter using a string array or a list of items


                                    val adapter = customadapter(
                                        this@EditClientActivity,
                                        R.layout.simple_spinner_item,
                                        items
                                    )

                                    val defaultonbosrdingstatus =
                                        data.data.clientInfo.onboardingStatus
                                    val defaultPosition = items.indexOf(defaultonbosrdingstatus)

                                    if (defaultPosition >= 0) {
                                        binding.spinerOnboardingStatus.setText(
                                            defaultonbosrdingstatus,
                                            false
                                        ) // Set text without triggering selection
                                        adapter.setSelectedPosition(defaultPosition) // Highlight the default item
                                    }
                                    binding.spinerOnboardingStatus.setAdapter(adapter)
                                    binding.spinerOnboardingStatus.showDropDown()
                                }
                            }


                            binding.spinerOnboardingStatus.setOnItemClickListener { _, _, position, _ ->
                                // Update the selected position in the adapter
                                adapter.setSelectedPosition(position)
                            }


                            // Set the adapter to the Spinner
                            binding.spinerOnboardingStatus.setAdapter(adapter)
                            if (compareValue != null) {
                                val adapter =
                                    binding.spinerOnboardingStatus.adapter as ArrayAdapter<String>
                                val position = adapter.getPosition(compareValue)

                                try {
                                    if (position != -1) {
                                        binding.spinerOnboardingStatus.setText(
                                            compareValue,
                                            false
                                        ) // Set the text without triggering an item selection event
                                        binding.spinerOnboardingStatus.setSelection(position) // Set the selection
                                    }
                                } catch (e: Exception) {

                                }

                            }

                        }
                    }

                    override fun onFailure(
                        call: Call<GetCompanyOnboardingStatusResponse>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
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

    private fun isValidUrl(url: String): Boolean {
        val customPattern =
            "^(https?://(www\\.)?|www\\.)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/.*)?$"
        return url.matches(customPattern.toRegex())
    }

    /* private fun isValidUrl(url: String): Boolean {
         val pattern: Pattern = Patterns.WEB_URL
         val matcher = pattern.matcher(url)
         return matcher.matches()


         spinerOnboardingStatus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (spinerOnboardingStatus.isPopupShowing()) {
            spinerOnboardingStatus.dismissDropDown();
        } else {
            spinerOnboardingStatus.showDropDown();
        }
    }
});
     }*/

    private fun setUpRtf() {


        binding.etDescription.setEditorHeight(110)
        binding.etDescription.setEditorFontSize(14)
        binding.etDescription.setEditorFontColor(Color.BLACK)
        //binding.etDescription.setEditorBackgroundColor(Color.BLUE);
        //binding.etDescription.setBackgroundColor(Color.BLUE);
        //binding.etDescription.setBackgroundResource(R.drawable.bg);
        binding.etDescription.setPadding(0, 2, 10, 10)
        //binding.etDescription.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        binding.etDescription.setPlaceholder("Description")
        //binding.etDescription.setInputEnabled(false);
        //  mPreview = findViewById<View>(R.id.preview) as TextView
        //   binding.etDescription!!.setOnTextChangeListener { text -> mPreview!!.text = text }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_undo).setOnClickListener { binding.etDescription.undo() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_redo).setOnClickListener { binding.etDescription.redo() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_bold).setOnClickListener { binding.etDescription.setBold() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_italic).setOnClickListener { binding.etDescription.setItalic() }

        findViewById<View>(com.example.envagemobileapplication.R.id.action_strikethrough).setOnClickListener { binding.etDescription.setStrikeThrough() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_underline).setOnClickListener { binding.etDescription.setUnderline() }


        findViewById<View>(com.example.envagemobileapplication.R.id.action_txt_color).setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etDescription.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                    isChanged = !isChanged
                }
            })
        findViewById<View>(com.example.envagemobileapplication.R.id.action_bg_color).setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etDescription.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                    isChanged = !isChanged
                }
            })

        findViewById<View>(com.example.envagemobileapplication.R.id.action_align_left).setOnClickListener { binding.etDescription.setAlignLeft() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_align_center).setOnClickListener { binding.etDescription.setAlignCenter() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_align_right).setOnClickListener { binding.etDescription.setAlignRight() }

        findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_bullets).setOnClickListener { binding.etDescription.setBullets() }
        findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_numbers).setOnClickListener { binding.etDescription.setNumbers() }

        findViewById<View>(com.example.envagemobileapplication.R.id.action_insert_link).setOnClickListener {
            binding.etDescription.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }

    fun loadJobDescriptionContent(url: String) {

        //val htmlTextView = HtmlTextView(requireContext())
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

                this.runOnUiThread {
                    val spannedText: Spanned =
                        Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
                    val description: String = spannedText.toString()

                    if (description.isNullOrEmpty()) {
                        // binding.etDescription.visibility = View.GONE
                    } else {

                        binding.etDescription.html = htmlContent
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

    override fun onBackPressed() {

        val intent = Intent(this@EditClientActivity, ClientSummaryActivity::class.java)
        finish()
        startActivity(intent)
        super.onBackPressed()
    }


}


