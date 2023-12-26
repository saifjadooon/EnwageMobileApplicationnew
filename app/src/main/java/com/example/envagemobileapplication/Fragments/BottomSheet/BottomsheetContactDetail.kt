package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientcntctHderRes.GetClientContactHeaderResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.BottomsheetContactdetailsDisplayBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

class BottomsheetContactDetail : BottomSheetDialogFragment() {
    lateinit var responsebody: GetClientContactHeaderResponse
    lateinit var loader: Loader
    lateinit var binding: BottomsheetContactdetailsDisplayBinding
    val viewModel: ClientSummaryViewModel by activityViewModels()
    private val delayMillis = 5000L // Delay between transitions in milliseconds
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomsheetContactdetailsDisplayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader = Loader(requireContext())
        getClientContactSummary()
        clicklisteners()

    }

    private fun clicklisteners() {

        binding.tvContactName.setOnLongClickListener {
            if (!responsebody.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && !responsebody.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {

                val toast = Toast.makeText(
                    context,
                    responsebody.data.clientContactInfo.primaryFirstName + " " + responsebody.data.clientContactInfo.primaryLastName,
                    Toast.LENGTH_LONG
                )
                toast.show()


            } else if (!responsebody.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && responsebody.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {
                val toast = Toast.makeText(
                    context, responsebody.data.clientContactInfo.primaryFirstName,
                    Toast.LENGTH_LONG
                )
                toast.show()
            } else if (responsebody.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && !responsebody.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {


                val toast = Toast.makeText(
                    context, responsebody.data.clientContactInfo.primaryLastName,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
            true
        }
        binding.tvclientname.setOnLongClickListener {

            if (responsebody.data?.client?.name != null) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data?.client?.name.toString(),
                    Toast.LENGTH_LONG
                )


                toast.show()

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )


                toast.show()

            }

            true
        }
        binding.primaryemail.setOnLongClickListener {

            if (!responsebody.data.clientContactInfo.primaryEmail.isNullOrEmpty() && !responsebody.data.clientContactInfo.primaryEmail.equals(
                    "null"
                )
            ) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data.clientContactInfo.primaryEmail,
                    Toast.LENGTH_LONG
                )


                toast.show()

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )


                toast.show()

            }



            true
        }
        binding.alternateemail.setOnLongClickListener {

            if (!responsebody.data.clientContactInfo.alternateEmail.isNullOrEmpty() && !responsebody.data.clientContactInfo.alternateEmail.equals(
                    "null"
                )
            ) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data.clientContactInfo.alternateEmail,
                    Toast.LENGTH_LONG
                )


                toast.show()

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )


                toast.show()

            }

            true
        }
        binding.tvadress.setOnLongClickListener {

            if (!responsebody.data.clientContactInfo?.primaryAddress1.isNullOrBlank() && !responsebody.data.clientContactInfo?.primaryAddress2.toString()
                    .isNullOrBlank() && !responsebody.data.clientContactInfo?.primaryCountry.toString()
                    .isNullOrBlank()
            ) {
                if (!responsebody.data.clientContactInfo?.primaryCountry.toString()
                        .equals("null")
                ) {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress1 + ", " + responsebody.data.clientContactInfo?.primaryAddress2 + ", " + responsebody.data.clientContactInfo?.primaryCountry.toString(),
                        Toast.LENGTH_LONG
                    )
                    toast.show()


                } else {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress1 + ", " + responsebody.data.clientContactInfo?.primaryAddress2,
                        Toast.LENGTH_LONG
                    )
                    toast.show()

                }

            } else if (responsebody.data.clientContactInfo?.primaryAddress2
                    .isNullOrEmpty() && !responsebody.data.clientContactInfo?.primaryAddress1.isNullOrEmpty()
            ) {
                if (responsebody.data.clientContactInfo?.primaryCountry == null || responsebody.data.clientContactInfo?.primaryCountry.equals(
                        "null"
                    )
                ) {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress1.toString(),
                        Toast.LENGTH_LONG
                    )
                    toast.show()


                } else {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress1.toString() + ", " + responsebody.data.clientContactInfo?.primaryCountry.toString(),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }

            } else if (responsebody.data.clientContactInfo?.primaryAddress1
                    .isNullOrEmpty() && !responsebody.data.clientContactInfo?.primaryAddress2.isNullOrEmpty()
            ) {

                if (responsebody.data.clientContactInfo?.primaryCountry.toString()
                        .isNullOrEmpty()
                ) {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress2, Toast.LENGTH_LONG
                    )
                    toast.show()


                } else {
                    val toast = Toast.makeText(
                        context,
                        responsebody.data.clientContactInfo?.primaryAddress2 + ", " + responsebody.data.clientContactInfo?.primaryCountry.toString(),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided", Toast.LENGTH_LONG
                )
                toast.show()

            }

            true
        }
        binding.country2.setOnLongClickListener {
            if (!responsebody.data?.clientContactInfo?.primaryCity.isNullOrEmpty()) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data?.clientContactInfo?.primaryCity.toString(),
                    Toast.LENGTH_LONG
                )


                toast.show()

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )


                toast.show()


            }

            true
        }
        binding.city2.setOnLongClickListener {

            if (!responsebody.data?.clientContactInfo?.primaryState.isNullOrEmpty()) {


                val toast = Toast.makeText(
                    context,
                    responsebody.data?.clientContactInfo?.primaryState.toString(),
                    Toast.LENGTH_LONG
                )

                toast.show()

            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )

                toast.show()

            }

            true
        }
        binding.postalcode2.setOnLongClickListener {

            if (!responsebody.data?.clientContactInfo?.primaryZipcode.isNullOrEmpty()) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data?.clientContactInfo?.primaryZipcode.toString(),
                    Toast.LENGTH_LONG
                )


                toast.show()
            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided", Toast.LENGTH_LONG
                )


                toast.show()
            }

            true
        }
        binding.location2.setOnLongClickListener {
            if (!responsebody.data?.clientContactInfo?.primaryLocation.isNullOrEmpty()) {
                val toast = Toast.makeText(
                    context,
                    responsebody.data?.clientContactInfo?.primaryLocation.toString(),
                    Toast.LENGTH_LONG
                )
                toast.show()
            } else {
                val toast = Toast.makeText(
                    context,
                    "Not provided",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }


    private fun getClientContactSummary() {


        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId =
            Constants.clientPocId  // Replace with the actual client ID
        try {
            ApiUtils.getAPIService(requireContext()).GetContactHeaderSummary(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<GetClientContactHeaderResponse> {
                    override fun onResponse(
                        call: Call<GetClientContactHeaderResponse>,
                        response: Response<GetClientContactHeaderResponse>
                    ) {
                        if (response.body() != null) {
                            responsebody = response.body()!!
                            if (!response.body()?.data?.clientContactInfo?.primaryProfileImage.isNullOrEmpty()) {
                                Picasso.get()
                                    .load(response.body()?.data?.clientContactInfo?.primaryProfileImage)
                                    .placeholder(R.drawable.upload_pic_bg)
                                    .transform(CircleTransformation()).into(binding.ivprofilepic)
                            } else {
                                Picasso.get().load(R.drawable.upload_pic_bg)
                                    .placeholder(R.drawable.upload_pic_bg)
                                    .transform(CircleTransformation())
                                    .into(binding.ivprofilepic)
                            }
                            if (!response.body()!!.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && !response.body()!!.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {
                                binding.tvContactName.setText(
                                    response.body()!!.data.clientContactInfo.primaryFirstName + " " + response.body()!!.data.clientContactInfo.primaryLastName
                                )
                            } else if (!response.body()!!.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && response.body()!!.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {
                                binding.tvContactName.setText(
                                    response.body()!!.data.clientContactInfo.primaryFirstName
                                )
                            } else if (response.body()!!.data.clientContactInfo.primaryFirstName.isNullOrEmpty() && !response.body()!!.data.clientContactInfo.primaryLastName.isNullOrEmpty()) {
                                binding.tvContactName.setText(
                                    response.body()!!.data.clientContactInfo.primaryLastName
                                )
                            }


                            if (!response.body()!!.data.clientContactInfo.primaryEmail.isNullOrEmpty() && !response.body()!!.data.clientContactInfo.primaryEmail.equals(
                                    "null"
                                )
                            ) {
                                binding.primaryemail.setText(response.body()!!.data.clientContactInfo.primaryEmail)
                            } else {
                                binding.primaryemail.setText("Not provided")
                            }

                            if (!response.body()!!.data.clientContactInfo.alternateEmail.isNullOrEmpty() && !response.body()!!.data.clientContactInfo.alternateEmail.equals(
                                    "null"
                                )
                            ) {
                                binding.alternateemail.setText(response.body()!!.data.clientContactInfo.alternateEmail)
                            } else {
                                binding.alternateemail.setText("Not provided")
                            }

                            try {
                                if (!response.body()!!.data.clientContactInfo?.primaryAddress1.isNullOrBlank() && !response.body()!!.data.clientContactInfo?.primaryAddress2.toString()
                                        .isNullOrBlank() && !response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                        .isNullOrBlank()
                                ) {
                                    if (!response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                            .equals("null")
                                    ) {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress1 + ", " + response.body()!!.data.clientContactInfo?.primaryAddress2 + ", " + response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                    } else {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress1 + ", " + response.body()!!.data.clientContactInfo?.primaryAddress2
                                    }

                                } else if (response.body()!!.data.clientContactInfo?.primaryAddress2
                                        .isNullOrEmpty() && !response.body()!!.data.clientContactInfo?.primaryAddress1.isNullOrEmpty()
                                ) {
                                    if (response.body()!!.data.clientContactInfo?.primaryCountry == null || response.body()!!.data.clientContactInfo?.primaryCountry.equals(
                                            "null"
                                        )
                                    ) {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress1.toString()
                                    } else {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress1.toString() + ", " + response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                    }

                                } else if (response.body()!!.data.clientContactInfo?.primaryAddress1
                                        .isNullOrEmpty() && !response.body()!!.data.clientContactInfo?.primaryAddress2.isNullOrEmpty()
                                ) {

                                    if (response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                            .isNullOrEmpty()
                                    ) {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress2
                                    } else {
                                        binding.tvadress.visibility = View.VISIBLE
                                        binding.tvadress.text =
                                            response.body()!!.data.clientContactInfo?.primaryAddress2 + ", " + response.body()!!.data.clientContactInfo?.primaryCountry.toString()
                                    }


                                } else {

                                    binding.tvadress.text = "Not provided"
                                }


                            } catch (e: Exception) {
                            }

                            if (!response.body()?.data?.clientContactInfo?.primaryCity.isNullOrEmpty()) {

                                binding.country2.text =
                                    response.body()?.data?.clientContactInfo?.primaryCity.toString()
                            } else {
                                binding.country2.text = "Not provided"
                            }

                            if (!response.body()?.data?.clientContactInfo?.primaryState.isNullOrEmpty()) {
                                binding.city2.visibility = View.VISIBLE
                                binding.city2.text =
                                    response.body()?.data?.clientContactInfo?.primaryState.toString()
                            } else {
                                binding.city2.text = "Not provided"
                                // binding.city2.visibility = View.INVISIBLE
                            }

                            if (!response.body()?.data?.clientContactInfo?.primaryZipcode.isNullOrEmpty()) {
                                binding.postalcode2.visibility = View.VISIBLE
                                binding.postalcode2.text =
                                    response.body()?.data?.clientContactInfo?.primaryZipcode.toString()
                            } else {
                                binding.postalcode2.text = "Not provided"
                                //binding.postalcode2.visibility = View.INVISIBLE
                            }

                            if (!response.body()?.data?.clientContactInfo?.primaryLocation.isNullOrEmpty()) {
                                binding.location2.visibility = View.VISIBLE
                                binding.location2.text =
                                    response.body()?.data?.clientContactInfo?.primaryLocation.toString()
                            } else {
                                binding.location2.text = "Not provided"
                                // binding.location2.visibility = View.INVISIBLE
                            }

                            if (!response.body()!!.data.clientContactInfo.createdDate.isNullOrEmpty() && !response.body()!!.data.clientContactInfo.createdDate.equals(
                                    "null"
                                )
                            ) {
                                val inputDate = response.body()!!.data.clientContactInfo.createdDate
                                val formattedDate = formatDate(inputDate)
                                println(formattedDate) // Output: 04/17/2023
                                binding.tvCreatedat.setText("Created on: " + formattedDate)
                            } else {
                                binding.tvCreatedat.setText("Created on: " + "-")
                            }

                            if (response.body()!!.data.clientContactInfo.isPrimaryContact) {
                                binding.tvContactprimaryStatus.setText("Primary")
                            } else {
                                binding.tvContactprimaryStatus.setText("Billing")
                            }

                            if (response.body()!!.data.clientContactInfo.primaryGender != null) {
                                if (response.body()!!.data.clientContactInfo.primaryGender.equals("Male")) {
                                    binding.ivGender.setImageDrawable(
                                        context?.resources?.getDrawable(
                                            R.drawable.ic_male
                                        )
                                    )
                                } else if (response.body()!!.data.clientContactInfo.primaryGender.equals(
                                        "Female"
                                    )
                                ) {
                                    binding.ivGender.setImageDrawable(
                                        context?.resources?.getDrawable(
                                            R.drawable.ic_female
                                        )
                                    )
                                } else {
                                    binding.ivGender.visibility = View.INVISIBLE
                                }
                            } else {
                                binding.ivGender.visibility = View.INVISIBLE
                            }

                            if (response.body()?.data?.clientContactInfo?.primaryPhoneNumber != null) {
                                val inputPhoneNumber =
                                    response.body()?.data?.clientContactInfo?.primaryPhoneNumber.toString()
                                val formattedPhoneNumber =
                                    formatToUSAPhoneNumber(inputPhoneNumber)

                                binding.tvphone.setText(formattedPhoneNumber)
                            } else {
                                binding.tvphone.setText("Not provided")
                            }
                            if (response.body()?.data?.client?.name != null) {
                                binding.tvclientname.setText(response.body()?.data?.client?.name.toString())
                            } else {
                                binding.tvclientname.setText("Not provided")
                            }


                            if (response.body()?.data?.clientContactInfo?.contactDescription != null) {
                                var filename =
                                    response.body()?.data?.clientContactInfo?.contactDescription.toString()
                                var baseurlnew =
                                    "https://devgateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename
                                try {
                                    loadJobDescriptionContent(baseurlnew)
                                } catch (e: Exception) {
                                }


                            } else {
                                binding.tvDescription.text = "No Description to Show"
                            }


                        } else {

                        }
                    }

                    override fun onFailure(
                        call: Call<GetClientContactHeaderResponse>,
                        t: Throwable
                    ) {

                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {

            Log.i("exception", ex.toString())
        }

    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val outputFormat = SimpleDateFormat("MM-dd-yyyy")

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle parsing error here
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
                        }
                        else {
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
                        binding.tvDescription.text = "No Description to Show"
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
}