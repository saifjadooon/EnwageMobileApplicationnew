package com.example.envagemobileapplication.Fragments.ClientSummary

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Activities.AddClient.EditClientActivity
import com.example.envagemobileapplication.Adapters.WcCodesAdapter
import com.example.envagemobileapplication.BuildConfig
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetStatusFragment
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse.WcCodeResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateProfileResultResponse.UpdateProfileResultResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentClientSummaryBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class ClientSummaryF : Fragment() {
    companion object {
        lateinit var onboardingStatus: TextView
        lateinit var tvDropdown: ImageView
        lateinit var bottomSheetFragment: BottomSheetStatusFragment


    }

    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO

    )
    private val MULTIPLE_PERMISSIONS = 10
    val usaPhoneNumberRegex =
        """^(?:(?:\+?1\s*(?:[.-]\s*)?)?(?:\(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\s*\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})$""".toRegex()
    lateinit var publicResponse: ClientHeaderSummaryResponse
    lateinit var binding: FragmentClientSummaryBinding
    lateinit var adapter: WcCodesAdapter
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    lateinit var currentPhotoPath: String
    lateinit var imagefilforapi: File
    lateinit var bodyofpf: MultipartBody.Part
    var facebookurl = ""
    var twitterurl = ""
    var linkedinurl = ""
    var instagramurl = ""
    lateinit var loader: Loader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientSummaryBinding.inflate(inflater, container, false)
        bottomSheetFragment = BottomSheetStatusFragment()
        onboardingStatus = binding.onboardingStatus
        tvDropdown = binding.tvDropdown
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = Loader(requireContext())
        getClientHeaderSummary()
        clicklisteners()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireActivity(), "Permission granted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    //    checkPermissions()
                    showNoPermissionDialog()
                    //  Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    private fun clicklisteners() {
        binding.ccall.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Check if the touch event is inside the target view
                val location = IntArray(2)
                binding.cvCameragallery.getLocationOnScreen(location)
                val targetRect = Rect(
                    location[0],
                    location[1],
                    location[0] + binding.cvCameragallery.width,
                    location[1] + binding.cvCameragallery.height
                )

                if (!targetRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    // Touch event is outside the target view, so hide it
                    binding.cvCameragallery.visibility = View.GONE
                    return@setOnTouchListener true // Consume the touch event
                }
            }
            false // Don't consume the touch event
        }
        binding.ivEditClient.setOnClickListener {

            startActivity(Intent(context, EditClientActivity::class.java))
            requireActivity().finish()

        }
        binding.facebook.setOnClickListener {

            for (i in 0 until publicResponse.data.clientSocialMedia.size) {
                if (publicResponse.data.clientSocialMedia.get(i).name.equals("Facebook")) {

                    if (!publicResponse.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                        if (isFacebookAppInstalled()) {
                            // Open the Facebook app
                            openFacebookApp()
                        } else {
                            // Open the web browser
                            openFacebookInBrowser()
                        }
                    }
                }
            }


        }
        binding.instagram.setOnClickListener {

            for (i in 0 until publicResponse.data.clientSocialMedia.size) {
                if (publicResponse.data.clientSocialMedia.get(i).name.equals("Instagram")) {

                    if (!publicResponse.data.clientSocialMedia.get(i).url.isNullOrBlank()) {

                        if (isInstagramAppInstalled()) {
                            // Open the Instagram app
                            openInstagramApp()
                        } else {
                            // Open Instagram in the web browser
                            openInstagramInBrowser()
                        }
                    }
                }
            }

        }
        binding.twitter.setOnClickListener {
            for (i in 0 until publicResponse.data.clientSocialMedia.size) {
                if (publicResponse.data.clientSocialMedia.get(i).name.equals("Twitter")) {

                    if (!publicResponse.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                        if (isTwitterAppInstalled()) {
                            // Open the Twitter app
                            openTwitterApp()
                        } else {
                            // Open Twitter in the web browser
                            openTwitterInBrowser()
                        }
                    }
                }
            }


        }
        binding.linkedin.setOnClickListener {


            for (i in 0 until publicResponse.data.clientSocialMedia.size) {
                if (publicResponse.data.clientSocialMedia.get(i).name.equals("LinkedIn")) {

                    if (!publicResponse.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                        if (isLinkedInAppInstalled()) {
                            // Open the LinkedIn app
                            openLinkedInApp()
                        } else {
                            // Open LinkedIn in the web browser
                            openLinkedInInBrowser()
                        }
                    }
                }
            }


        }
        binding.ivCamera.setOnClickListener {

            if (checkPermissions()) {
                binding.cvCameragallery.visibility = View.VISIBLE
            }
        }


        binding.cvTakePhoto.setOnClickListener {
            openCamera()
            binding.cvGallery.setBackgroundResource(R.drawable.searchbg)
            binding.cvGallery.setBackgroundColor(resources.getColor(R.color.white))
            binding.tvGallery.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
            binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                null,
                null
            )
            binding.cvTakePhoto.setBackgroundResource(R.drawable.btn_black_radius)
            binding.tvCamera.setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }


        }

        binding.cvGallery.setOnClickListener {

            openGallery()
            binding.cvTakePhoto.setBackgroundResource(R.drawable.searchbg)
            binding.cvTakePhoto.setBackgroundColor(resources.getColor(R.color.white))
            binding.tvCamera.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)
            binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)

            binding.cvGallery.setBackgroundResource(R.drawable.btn_black_radius)

            binding.tvGallery.setTextColor(resources.getColor(R.color.white))
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }
        }
        val bottomSheetFragment = BottomSheetStatusFragment()
        binding.onboardingStatus.setOnClickListener {


            if (bottomSheetFragment.isAdded()) {
                return@setOnClickListener
            } else {
                Constants.isOnboardingStatusUpdatedfromClientSummaryList = true
                //     Constants.StatusClickedName = binding.onboardingStatus.text.toString()
                Constants.StatusClickedClientId = publicResponse.data.clientInfo.clientId
                bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            }

        }
        binding.tvDropdown.setOnClickListener {


            if (bottomSheetFragment.isAdded()) {
                return@setOnClickListener
            } else {
                Constants.isOnboardingStatusUpdatedfromClientSummaryList = true
                // Constants.StatusClickedName = binding.onboardingStatus.text.toString()
                Constants.StatusClickedClientId = publicResponse.data.clientInfo.clientId
                bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            }

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


                requireActivity().runOnUiThread {
                    val spannedText: Spanned =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.webView.settings.javaScriptEnabled =
                                true // Enable JavaScript if needed
                            binding.webView.webViewClient = WebViewClient()

                            binding.webView.loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                            Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            binding.webView.settings.javaScriptEnabled =
                                true // Enable JavaScript if needed
                            binding.webView.webViewClient = WebViewClient()

                            binding.webView.loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                            Html.fromHtml(htmlContent)
                        }
                    val description: String = spannedText.toString()

                    if (description.isNullOrEmpty()) {

                        binding.tvNodescription.visibility = View.VISIBLE
                        binding.webView.visibility = View.INVISIBLE
                    } else {

                        binding.tvDescription.text = spannedText
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

    private fun getApprovedWCCodes() {
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId =
            Constants.clientid // Replace with the actual client ID
        try {
            ApiUtils.getAPIService(requireContext()).GetApprovedWcCodes(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<WcCodeResponse> {
                    override fun onResponse(
                        call: Call<WcCodeResponse>,
                        response: Response<WcCodeResponse>
                    ) {
                        if (isAdded) {
                            if (response.body() != null) {
/*
                            if (response.body()?.message!!.equals("No record found")) {
                                binding.rvWcCodes.visibility = View.INVISIBLE
                                binding.noApprovedWcCodes.visibility = View.VISIBLE
                            }
                            */


                                binding.noApprovedWcCodes.visibility = View.GONE
                                binding.rvWcCodes.visibility = View.VISIBLE

                                var wcDataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse.Datum> =
                                    ArrayList()

                                for (i in 0 until response.body()?.data?.size!!) {
                                    wcDataList.add(response.body()?.data?.get(i)!!)

                                }


                                setupWcAdapter(wcDataList)


                            }
                        }
                    }

                    override fun onFailure(call: Call<WcCodeResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exception", ex.toString())
        }
    }

    private fun getClientHeaderSummary() {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId =
            Constants.clientid // Replace with the actual client ID
        try {
            ApiUtils.getAPIService(requireContext()).GetClientHeaderSummary(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {
                        if (isAdded) {
                            if (response.body() != null) {
                                loader.hide()
                                publicResponse = response.body()!!
                                Constants.clientSummaryResp = response.body()!!
                                try {
                                    getApprovedWCCodes()
                                } catch (e: Exception) {

                                    Toast.makeText(
                                        requireContext(),
                                        e.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                                try {

                                    for (i in 0 until response.body()!!.data.clientSocialMedia.size) {

                                        if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                "Facebook"
                                            )
                                        ) {

                                            if (response.body()!!.data.clientSocialMedia.get(i).isActive == true
                                            ) {
                                                if (!response.body()!!.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                                                    facebookurl =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url
                                                }
                                                binding.facebook.visibility = View.VISIBLE
                                            } else {
                                                binding.facebook.visibility = View.GONE
                                            }


                                        } else if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                "Twitter"
                                            )
                                        ) {
                                            if (response.body()!!.data.clientSocialMedia.get(i).isActive.equals(
                                                    true
                                                )
                                            ) {
                                                if (!response.body()!!.data.clientSocialMedia.get(i).url.isNullOrBlank()
                                                ) {
                                                    twitterurl =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url
                                                }
                                                binding.twitter.visibility = View.VISIBLE
                                            } else {
                                                binding.twitter.visibility = View.GONE
                                            }

                                        } else if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                "LinkedIn"
                                            )
                                        ) {

                                            if (response.body()!!.data.clientSocialMedia.get(i).isActive.equals(
                                                    true
                                                )
                                            ) {
                                                if (!response.body()!!.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                                                    linkedinurl =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url


                                                }
                                                binding.linkedin.visibility = View.VISIBLE
                                            } else {
                                                binding.linkedin.visibility = View.GONE
                                            }
                                        } else if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                "Instagram"
                                            )

                                        ) {

                                            if (response.body()!!.data.clientSocialMedia.get(i).isActive.equals(
                                                    true
                                                )
                                            ) {
                                                if (!response.body()!!.data.clientSocialMedia.get(i).url.isNullOrBlank()) {
                                                    instagramurl =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url
                                                }
                                                binding.instagram.visibility = View.VISIBLE
                                            } else {
                                                binding.instagram.visibility = View.GONE
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        requireContext(),
                                        e.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                                if (!response.body()?.data?.clientInfo?.description.isNullOrEmpty()) {
                                    var filename =
                                        response.body()?.data?.clientInfo?.description.toString()
                                    var baseurlnew =
                                        "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                                    try {

                                        loadJobDescriptionContent(baseurlnew)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            requireContext(),
                                            e.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                                else {
                                    binding.tvDescription.text = "No Description to Show"
                                }


                                if (!response.body()?.data?.clientInfo?.name.toString()
                                        .isNullOrEmpty()
                                ) {
                                    val name = response.body()?.data?.clientInfo?.name.toString()
                                        .replace("\"", "")

                                    binding.clientname.text = name

                                } else {
                                    binding.clientname.text = "Not provided"
                                    //binding.clientname.visibility = View.GONE
                                }



                                binding.clientname.setOnLongClickListener {


                                    val toast = Toast.makeText(
                                        requireContext(),
                                        response.body()?.data?.clientInfo?.name.toString(),
                                        Toast.LENGTH_LONG
                                    )

                                    toast.show()
                                    true
                                }



                                binding.clientiIndustry.setOnLongClickListener {


                                    val toast = Toast.makeText(
                                        requireContext(),
                                        response.body()?.data?.clientInfo?.industryName.toString(),
                                        Toast.LENGTH_LONG
                                    )

                                    toast.show()

                                    true
                                }


                                if (!response.body()?.data?.clientInfo?.visibilityStatus.isNullOrEmpty()) {

                                    binding.tvvisibilitystatus.text =
                                        response.body()?.data?.clientInfo?.visibilityStatus.toString()
                                    if (response.body()?.data?.clientInfo?.visibilityStatus.toString()
                                            .equals("public")
                                    ) {

                                        binding.tvvisibilitystatus.setCompoundDrawablesWithIntrinsicBounds(
                                            R.drawable.ic_publicsvg,
                                            0,
                                            0,
                                            0
                                        )
                                    } else if (response.body()?.data?.clientInfo?.visibilityStatus.toString()
                                            .equals("private")
                                    ) {
                                        binding.tvvisibilitystatus.setCompoundDrawablesWithIntrinsicBounds(
                                            R.drawable.ic_privatesvg,
                                            0,
                                            0,
                                            0
                                        )
                                    } else {
                                        binding.tvvisibilitystatus.setCompoundDrawablesWithIntrinsicBounds(
                                            R.drawable.ic_confidentailsvg,
                                            0,
                                            0,
                                            0
                                        )
                                    }
                                } else {
                                    binding.tvvisibilitystatus.visibility = View.GONE
                                }

                                if (!response.body()?.data?.clientInfo?.industryName.isNullOrEmpty()) {
                                    binding.clientiIndustry.visibility = View.VISIBLE
                                    binding.clientiIndustry.text =
                                        response.body()?.data?.clientInfo?.industryName.toString() + ""
                                } else {
                                    binding.clientiIndustry.text = "Not provided"
                                    //    binding.clientiIndustry.visibility = View.GONE
                                }
                                if (!response.body()?.data?.clientInfo?.onboardingStatus.isNullOrEmpty()) {

                                    Constants.StatusClickedName =
                                        response.body()?.data?.clientInfo?.onboardingStatus.toString()
                                    binding.onboardingStatus.text =
                                        response.body()?.data?.clientInfo?.onboardingStatus.toString()

                                    // val hexColorCode = dataList.get(position).colorCode
                                    try {
                                        val hexColorCode =
                                            response.body()!!.data.clientInfo.colorCode
                                        val colore = Color.parseColor(hexColorCode)


                                        binding.tvDropdown.setColorFilter(colore)

                                        binding.onboardingStatus.setTextColor(colore)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            requireContext(),
                                            e.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }


                                } else {
                                    binding.onboardingStatus.visibility = View.GONE
                                }
                                if (!response.body()?.data?.clientInfo?.ownerName.isNullOrEmpty()) {
                                    binding.ownername.text =
                                        response.body()?.data?.clientInfo?.ownerName.toString()
                                } else {
                                    binding.ownername.text = "Not provided"
                                    //  binding.ownername.visibility = View.GONE
                                }

                                if (!response.body()?.data?.clientInfo?.phone.isNullOrEmpty()) {

                                    binding.contactnumber.visibility = View.VISIBLE
                                    val inputPhoneNumber =
                                        response.body()?.data?.clientInfo?.phone.toString()
                                    val formattedPhoneNumber =
                                        formatToUSAPhoneNumber(inputPhoneNumber)
                                    binding.contactnumber.text = formattedPhoneNumber

                                } else {
                                    binding.contactnumber.text = "Not provided"
                                    // binding.contactnumber.visibility = View.GONE
                                }



                                try {
                                    if (!response.body()?.data?.clientInfo?.primaryAddress1.isNullOrBlank() && !response.body()?.data?.clientInfo?.primaryAddress2.toString()
                                            .isNullOrBlank() && !response.body()?.data?.clientInfo?.country.toString()
                                            .isNullOrBlank()
                                    ) {
                                        if (!response.body()?.data?.clientInfo?.country.toString()
                                                .equals("null")
                                        ) {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress1 + ", " + response.body()?.data?.clientInfo?.primaryAddress2 + ", " + response.body()?.data?.clientInfo?.country.toString()
                                        } else {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress1 + ", " + response.body()?.data?.clientInfo?.primaryAddress2
                                        }

                                    } else if (response.body()?.data?.clientInfo?.primaryAddress2
                                            .isNullOrEmpty() && !response.body()?.data?.clientInfo?.primaryAddress1.isNullOrEmpty()
                                    ) {
                                        if (response.body()?.data?.clientInfo?.country == null || response.body()?.data?.clientInfo?.country.equals(
                                                "null"
                                            )
                                        ) {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress1.toString()
                                        } else {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress1.toString() + ", " + response.body()?.data?.clientInfo?.country.toString()
                                        }

                                    } else if (response.body()?.data?.clientInfo?.primaryAddress1
                                            .isNullOrEmpty() && !response.body()?.data?.clientInfo?.primaryAddress2.isNullOrEmpty()
                                    ) {

                                        if (response.body()?.data?.clientInfo?.country.toString()
                                                .isNullOrEmpty()
                                        ) {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress2
                                        } else {
                                            binding.adress.visibility = View.VISIBLE
                                            binding.adress.text =
                                                response.body()?.data?.clientInfo?.primaryAddress2 + ", " + response.body()?.data?.clientInfo?.country.toString()
                                        }


                                    } else {

                                        binding.adress.text = "Not provided"
                                        //  binding.adress.visibility = View.GONE
                                    }


                                } catch (e: Exception) {
                                    Toast.makeText(
                                        requireContext(),
                                        e.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                binding.adress.setOnLongClickListener {
                                    val toast = Toast.makeText(
                                        requireContext(),
                                        response.body()?.data?.clientInfo?.primaryAddress1.toString() + ", " + response.body()?.data?.clientInfo?.primaryAddress2.toString() + ", " +
                                                response.body()?.data?.clientInfo?.country.toString(),
                                        Toast.LENGTH_LONG
                                    )


                                    toast.show()
                                    true
                                }
                                if (!response.body()?.data?.clientInfo?.city.isNullOrEmpty()

                                ) {
                                    binding.country.visibility = View.VISIBLE
                                    binding.country.text =
                                        response.body()?.data?.clientInfo?.city.toString()
                                } else {
                                    binding.country.text = "Not provided"
                                    //      binding.country.visibility = View.INVISIBLE
                                }

                                binding.country.setOnLongClickListener {

                                    val toast = Toast.makeText(
                                        context,
                                        response.body()?.data?.clientInfo?.city.toString(),
                                        Toast.LENGTH_LONG
                                    )

                                    toast.show()
                                    true
                                }
                                if (!response.body()?.data?.clientInfo?.state.isNullOrEmpty()) {
                                    binding.city.visibility = View.VISIBLE
                                    binding.city.text =
                                        response.body()?.data?.clientInfo?.state.toString()
                                } else {
                                    binding.city.text = "Not provided"

                                }

                                binding.city.setOnLongClickListener {

                                    val toast = Toast.makeText(
                                        context,
                                        response.body()?.data?.clientInfo?.state.toString(),
                                        Toast.LENGTH_LONG
                                    )

                                    toast.show()
                                    true
                                }

                                if (!response.body()?.data?.clientInfo?.zipcode.isNullOrEmpty()) {
                                    binding.postalcode.visibility = View.VISIBLE
                                    binding.postalcode.text =
                                        response.body()?.data?.clientInfo?.zipcode.toString()
                                } else {
                                    binding.postalcode.text = "Not provided"

                                }
                                if (!response.body()?.data?.clientInfo?.location.isNullOrEmpty()) {
                                    binding.location.visibility = View.VISIBLE
                                    binding.location.text =
                                        response.body()?.data?.clientInfo?.location.toString()
                                } else {
                                    binding.location.text = "Not provided"

                                }

                                binding.location.setOnLongClickListener {

                                    val toast = Toast.makeText(
                                        context,
                                        response.body()?.data?.clientInfo?.location.toString(),
                                        Toast.LENGTH_LONG
                                    )

                                    toast.show()
                                    true
                                }

                                if (!response.body()?.data?.clientInfo?.profilePicture.isNullOrEmpty()) {
                                    Picasso.get()
                                        .load(response.body()?.data?.clientInfo?.profilePicture)
                                        .into(binding.ivProfilePic)

                                    Log.i(
                                        "pathhhhhh",
                                        response.body()?.data?.clientInfo?.profilePicture.toString()
                                    )
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

    private fun setupWcAdapter(wcDataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse.Datum>) {

        var arraylist: ArrayList<String> = ArrayList()
        binding.rvWcCodes.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding.rvWcCodes.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter = WcCodesAdapter(
            requireContext(),
            wcDataList
        )
        binding.rvWcCodes.adapter = adapter

    }

    private fun isFacebookAppInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        return try {
            packageManager.getPackageInfo("com.facebook.katana", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // Function to open the Facebook app
    private fun openFacebookApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl))
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl))
            startActivity(intent)
        }

    }

    // Function to open Facebook in the web browser
    private fun openFacebookInBrowser() {
        try {
            if (facebookurl.contains("https://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl))
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + facebookurl))
                startActivity(intent)
            }

        } catch (e: Exception) {

        }


    }

    // Function to check if the Instagram app is installed
    private fun isInstagramAppInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        return try {
            packageManager.getPackageInfo("com.instagram.android", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // Function to open the Instagram app
    private fun openInstagramApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramurl))
            intent.setPackage("com.instagram.android")
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramurl))
            startActivity(intent)
            // Toast.makeText(context, "Incorrect url", Toast.LENGTH_SHORT).show()
        }

    }

    // Function to open Instagram in the web browser
    private fun openInstagramInBrowser() {

        if (instagramurl.contains("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramurl))
            startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + instagramurl))
            startActivity(intent)
        }

    }

    private fun isLinkedInAppInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        return try {
            packageManager.getPackageInfo("com.linkedin.android", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // Function to open the LinkedIn app
    private fun openLinkedInApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinurl))
            intent.setPackage("com.linkedin.android")
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinurl))
            startActivity(intent)
        }

    }

    // Function to open LinkedIn in the web browser
    private fun openLinkedInInBrowser() {


        if (linkedinurl.contains("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinurl))
            startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + linkedinurl))
            startActivity(intent)
        }

    }

    private fun isTwitterAppInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        return try {
            packageManager.getPackageInfo("com.twitter.android", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // Function to open the Twitter app
    private fun openTwitterApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl))
            intent.setPackage("com.twitter.android")
            startActivity(intent)
        } catch (e: Exception) {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl))
            startActivity(intent)

        }

    }

    // Function to open Twitter in the web browser
    private fun openTwitterInBrowser() {


        if (twitterurl.contains("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl))
            startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + twitterurl))
            startActivity(intent)
        }
    }


    private fun openCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                file
            ) else Uri.fromFile(file)
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            openCameraActivityResultLauncher.launch(pictureIntent)
        } else {
            startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
        }
    }


    private fun getImageFile(): File {
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
        val storageDir: File = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requireContext().filesDir
        } else {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "Camera"
            )
        }
        val file = File.createTempFile(
            imageFileName, ".jpg", storageDir
        )
        currentPhotoPath = "file:" + file.absolutePath
        return file
    }

    var openCameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val uri = Uri.parse(currentPhotoPath)
                val resultUri = uri
                val file = resultUri.path
                // Check if the file size is less than or equal to 3MB (3 * 1024 * 1024 bytes)
                val imagePath = File(file)

                var imagebytearray = getStreamByteFromImage(imagePath)
                val tempImageFile = createTempImageFile(imagebytearray!!)
                // Compress the image using BitmapFactory
                val options = BitmapFactory.Options()
                options.inSampleSize = 2 // Adjust the sample size as needed
                val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath, options)

                val compressedImageFile =
                    File(requireContext().externalCacheDir, "compressed_image.jpg")
                val outputStream = FileOutputStream(compressedImageFile)

                // Compress the Bitmap to a file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)

                if (tempImageFile.length() <= 5 * 1024 * 1024) {


                    // The image is within the size limit
                    Glide.with(this)
                        .load(resultUri)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.ivProfilePic)

                    imagefilforapi = tempImageFile

                    val requestBody =
                        imagefilforapi.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf = MultipartBody.Part.createFormData(
                        "profileImage",
                        imagefilforapi.name,
                        requestBody
                    )

                    UpdateProfilePic(bodyofpf)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please upload an image in defined limit (5 MB).",
                        Toast.LENGTH_SHORT
                    ).show()
                    // The image size exceeds 3MB, handle this as per your requirements (e.g., show an error message)
                    // You can add a Toast or Snackbar to inform the user about the size limit.
                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/jpg", "image/png")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    binding.cvCameragallery.visibility = View.GONE
                    val selectedImageUri: Uri? = data?.data
                    val contentResolver = requireContext().contentResolver
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val fileSize = inputStream?.available() ?: 0

                    if (fileSize > 5 * 1024 * 1024) { // 5 MB in bytes
                        // Show an error message
                        Toast.makeText(
                            requireContext(),
                            "Please upload an image in defined limit (5 MB).",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val uri: Uri? =
                            selectedImageUri

                        val imageFile: File? = uriToFile(contentResolver, uri!!)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!
                        Picasso.get().load(selectedImageUri).into(binding.ivProfilePic)
                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imageFile.name,
                                requestBody
                            )

                        UpdateProfilePic(bodyofpf)
                    }


                }

                CAMERA_REQUEST_CODE -> {

                    val uri = Uri.parse(currentPhotoPath)
                    CropImage.activity(uri)
                        .setRequestedSize(512, 512)
                        .start(requireActivity())

                    val imageFile: File? = File(currentPhotoPath)
                    Log.i("imagefile", imageFile.toString())
                    imagefilforapi = imageFile!!
                    Picasso.get().load(uri).into(binding.ivProfilePic)
                    val requestBody =
                        imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf =
                        MultipartBody.Part.createFormData(
                            "profileImage",
                            imageFile.name,
                            requestBody
                        )
                    binding.cvCameragallery.visibility = View.GONE

                    UpdateProfilePic(bodyofpf)

                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    loader.show()
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == Activity.RESULT_OK) {
                        val resultUri = result.uri
                        val file = resultUri.path
                        // mChatListner.sendImageMessage(file!!)

                        Glide.with(this)
                            .load(resultUri)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(binding.ivProfilePic)

                        val imagePath = File(file)


                        imagefilforapi = imagePath

                        val requestBody =
                            imagefilforapi
                                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imagefilforapi.name,
                                requestBody
                            )


                        binding.cvCameragallery.visibility = View.GONE
                        val delayMillis = 3000L // Delay between transitions in milliseconds
                        val handler = Handler()
                        handler.postDelayed({
                            UpdateProfilePic(bodyofpf)
                        }, delayMillis)


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        result.error.printStackTrace()
                    }

                }


            }
        }
    }

    fun uriToFile(contentResolver: ContentResolver, uri: Uri): File? {
        try {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val inputStream = contentResolver.openInputStream(uri)
                val file = File(Constants.context!!.cacheDir, displayName)

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                    return file
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return null
    }

    private fun UpdateProfilePic(bodyofpf: MultipartBody.Part) {
        loader.show()

        if (imagefilforapi != null) {
            var tokenmanager: TokenManager = TokenManager(requireContext())
            var token = tokenmanager.getAccessToken()
            var clientID = Constants.clientid

            try {


                ApiUtils.getAPIService(requireContext()).UpdateClientProfilepic(
                    token.toString(), clientID!!, bodyofpf
                )
                    .enqueue(object : Callback<UpdateProfileResultResponse> {
                        override fun onResponse(
                            call: Call<UpdateProfileResultResponse>,
                            response: Response<UpdateProfileResultResponse>
                        ) {
                            if (response.body() != null) {

                                loader.hide()
                                getClientHeaderSummary()
                                Toast.makeText(
                                    requireContext(),
                                    "Profile Image updated successfully",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }

                        override fun onFailure(
                            call: Call<UpdateProfileResultResponse>,
                            t: Throwable
                        ) {
                            loader.hide()
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                loader.hide()
                Toast.makeText(requireContext(), ex.toString(), Toast.LENGTH_LONG).show()
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        } else {
            loader.hide()
            Toast.makeText(requireContext(), "imageofpf is null", Toast.LENGTH_LONG).show()
        }
        /*   var name = "testtttxffdsfdsfdsfdsffdst"
           var websiteurl = "www.url.com"
           var description = stringToBinary("testing userrr")*/


    }

    private fun checkPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= 33) {
            permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        }

        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(requireActivity(), p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }

        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
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

    override fun onResume() {
        super.onResume()
        binding.cvCameragallery.visibility = View.GONE
        //  getClientHeaderSummary()
    }

    private fun showNoPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())


        alertDialogBuilder.setMessage("App does not have access to your Camera and Gallery. Please enable Camera and Gallery permissions from the settings and select Always.")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()

        }


        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    fun getStreamByteFromImage(imageFile: File): ByteArray? {
        var photoBitmap = BitmapFactory.decodeFile(imageFile.path)
        val stream = ByteArrayOutputStream()
        val imageRotation = getImageRotation(imageFile)
        if (imageRotation != 0) photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation)
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }


    private fun getImageRotation(imageFile: File): Int {
        var exif: ExifInterface? = null
        var exifRotation = 0
        try {
            exif = ExifInterface(imageFile.path)
            exifRotation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return if (exif == null) 0 else exifToDegrees(exifRotation)
    }

    private fun exifToDegrees(rotation: Int): Int {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90) return 90 else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) return 180 else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) return 270
        return 0
    }

    private fun getBitmapRotatedByDegree(bitmap: Bitmap, rotationDegree: Int): Bitmap {
        val matrix = Matrix()
        matrix.preRotate(rotationDegree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun createTempImageFile(imageByteArray: ByteArray): File {
        val tempFile = File.createTempFile("temp_image", ".jpg", requireActivity().cacheDir)
        try {
            val fileOutputStream = FileOutputStream(tempFile)
            fileOutputStream.write(imageByteArray)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile
    }
}