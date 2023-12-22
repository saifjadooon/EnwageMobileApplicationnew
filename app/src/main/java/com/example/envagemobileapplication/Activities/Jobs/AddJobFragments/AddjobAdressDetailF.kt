package com.example.envagemobileapplication.Activities.Jobs.AddJobFragments

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Jobs.AddJob.AddJobActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobAdressDetailsReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCountrylistResponse.GetCountrylistResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.ZipCodeResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentAddjobAdressDetailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddjobAdressDetailF : Fragment() {
    val viewModel: AddJobsSharedViewModel by activityViewModels()
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        if (dstart == 0 && end > start && source[start] == ' ') {
            // Block leading space
            return@InputFilter ""
        }
        null
    }
    var global: Global.Companion =
        com.example.envagemobileapplication.Utils.Global
    lateinit var token: String
    val maxLengthFilter = InputFilter.LengthFilter(5)
    lateinit var tokenmanager: TokenManager
    lateinit var binding: FragmentAddjobAdressDetailBinding
    lateinit var loader: Loader
    val zipcodefilterl = InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {
            if (Character.isWhitespace(source[i])) {
                return@InputFilter ""
            }
        }
        null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddjobAdressDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {

        try {
            binding.etAdress1.setText(viewModel.addrress1)
            binding.etAdress2.setText(viewModel.address2)
            binding.spinnerCountry.setText(viewModel.country)
            binding.etZipcode.setText(viewModel.zipcode)
            binding.etCity.setText(viewModel.city)
            binding.etState.setText(viewModel.state)
            binding.etLocation.setText(viewModel.location)
        } catch (e: Exception) {

        }
        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        clickListeners()
        networkCalls()
    }

    private fun networkCalls() {
        token = tokenmanager.getAccessToken().toString()
        getCountrylist(requireContext(), token)
    }

    private fun clickListeners() {

        binding.btnback2.setOnClickListener {
            global.isfrombackpress = true
            var address1 = binding.etAdress1.text
            var address2 = binding.etAdress2.text
            var country = binding.spinnerCountry.text.toString()
            var zipcode = binding.etZipcode.text.toString()
            var city = binding.etCity.text.toString()
            var state = binding.etState.text.toString()
            var location = binding.etLocation.text.toString()

            viewModel.addrress1 = address1.toString()
            viewModel.address2 = address2.toString()
            viewModel.country = country
            viewModel.zipcode = zipcode
            viewModel.city = city
            viewModel.state = state
            viewModel.location = location
            requireActivity().onBackPressed()
        }
        binding.btnNext.setOnClickListener {
            var address1 = binding.etAdress1.text
            var address2 = binding.etAdress2.text
            var country = binding.spinnerCountry.text.toString()
            var zipcode = binding.etZipcode.text.toString()
            var city = binding.etCity.text.toString()
            var state = binding.etState.text.toString()
            var location = binding.etLocation.text.toString()

            viewModel.addrress1 = address1.toString()
            viewModel.address2 = address2.toString()
            viewModel.country = country
            viewModel.zipcode = zipcode
            viewModel.city = city
            viewModel.state = state
            viewModel.location = location


            var addjobAddressDetailRequestModel = AddJobAdressDetailsReqModel(
                address1 = address1.toString(),
                address2 = address2.toString(),
                country = country,
                zipcode = zipcode,
                city = city,
                state = state,
                location = location
            )
            if (!address1.isNullOrEmpty() && !address2.isNullOrEmpty()) {

                com.example.envagemobileapplication.Utils.Global.addJobAddressDetailModel =
                    addjobAddressDetailRequestModel
                AddJobActivity.binding.ivThree.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_address_detail_done))
                replaceFragment(AddjobSalaryDetailF())

            } else {
                if (address1.isNullOrEmpty()) {
                    binding.TIadress1.error = "Address 1 is Required."
                    binding.TIadress1.errorIconDrawable = null// Set the error message
                    binding.TIadress1.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (address2.isNullOrEmpty()) {
                    binding.TIadress2.error = "Address 2 is Required."
                    binding.TIadress2.errorIconDrawable = null// Set the error message
                    binding.TIadress2.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }

        }

        binding.spinnerCountry.setOnItemClickListener { _, _, position, _ ->
            // Update the selected position in the adapter

        }

        binding.spinnerCountry.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerCountry.isPopupShowing) {
                binding.spinnerCountry.dismissDropDown()
            } else {
                binding.spinnerCountry.showDropDown()
            }
            false
        })

        binding.etAdress1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TIadress1.error = null
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

            }
        })

        binding.etAdress2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TIadress2.error = null
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

            }
        })


    }

    private fun initViews() {


        binding.etAdress1.filters = arrayOf(filter)
        binding.etAdress2.filters = arrayOf(filter)
        binding.etLocation.filters = arrayOf(filter)
        AddJobActivity.binding.ivThree.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_active_three))
        loader = Loader(requireContext())
        tokenmanager = TokenManager(requireContext())
        binding.etZipcode.filters = arrayOf(zipcodefilterl, maxLengthFilter)
        /*     val hintzipCode = "Zip Code *"
             val formattedHintZipcode = formatHintWithRedAsterisk(hintzipCode)
             binding.TIzipcode.hint = formattedHintZipcode*/
        val hintAdress1 = "Address 1 *"
        val formattedHintAdress1 = formatHintWithRedAsterisk(hintAdress1)
        binding.TIadress1.hint = formattedHintAdress1

        val hintAdress2 = "Address 2 *"
        val formattedHintAdress2 = formatHintWithRedAsterisk(hintAdress2)
        binding.TIadress2.hint = formattedHintAdress2


        binding.etZipcode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is not used in this example.
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()



                if (enteredText.length == 5) {
                    try {

                        val imm =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view!!.windowToken, 0)
                        if (binding.etZipcode.text.toString() != "" && binding.etZipcode.text.length >= 5) {
                            var zipcodeEnterd = binding.etZipcode.text.toString()
                            getzipcityStatelist(zipcodeEnterd)
                        } else if (binding.etZipcode.text.length < 5) {
                            binding.TIzipcode.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                            binding.TIzipcode.errorIconDrawable = null
                            binding.TIzipcode.error = "Zip Code must be 5 digits"
                        } else {
                            binding.TIzipcode.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                            binding.TIzipcode.errorIconDrawable = null
                            binding.TIzipcode.error = "Zip Code is required."
                        }
                    } catch (e: Exception) {
                    }

                } else {
                    binding.etState.setText("")
                    binding.etCity.setText("")
                }
            }
        })

    }

    private fun getCountrylist(context: Context, token: String) {
        try {
            loader.show()
            ApiUtils.getAPIService(context).GetCountryList(

                token.toString(),
            )
                .enqueue(object : Callback<GetCountrylistResponse> {
                    override fun onResponse(
                        call: Call<GetCountrylistResponse>,
                        response: Response<GetCountrylistResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            var countryList: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()?.data!!.size) {
                                countryList.add(
                                    response.body()!!.data.get(i).name
                                )
                            }
                            try {
                                val adapter = customadapter(
                                    requireContext(),
                                    R.layout.simple_spinner_item,
                                    countryList
                                )
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding.spinnerCountry.setAdapter(adapter)
                            } catch (e: Exception) {

                            }

                        }
                    }

                    override fun onFailure(
                        call: Call<GetCountrylistResponse>,
                        t: Throwable
                    ) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.show()
            Log.i("exceptionddsfdsfds", ex.toString())
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

    private fun getzipcityStatelist(zipcodeEnterd: String) {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val zipcode = zipcodeEnterd

        try {
            ApiUtils.getAPIService(requireContext()).getzipcityStatelist(
                token.toString(), zipcode
            )
                .enqueue(object : Callback<ZipCodeResponse> {
                    override fun onResponse(
                        call: Call<ZipCodeResponse>,
                        response: Response<ZipCodeResponse>
                    ) {
                        loader.hide()
                        if (response.body()?.data!!.size > 0) {


                            var zipcodedata: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.Datum> =
                                ArrayList()
                            for (i in 0 until response.body()!!.data.size) {

                                zipcodedata.add(response.body()!!.data.get(i))
                            }

                            binding.etState.setText(zipcodedata.get(0).stateName)
                            binding.etCity.setText(zipcodedata.get(0).cityName)

                            //    callUpdateClientApi(wesite)
                        } else {
                            val rootView = binding.root

                            val message = "No Record Found"
                            val duration = Snackbar.LENGTH_SHORT

                            val snackbar = Snackbar.make(rootView, message, duration)
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    com.example.envagemobileapplication.R.color.red
                                )
                            )
                            snackbar.show()
                        }
                    }

                    override fun onFailure(call: Call<ZipCodeResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())
                        loader.hide()
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exception", ex.toString())
            loader.hide()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_addjob_fragments,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onPause() {
        viewModel.addrress1 = binding.etAdress1.text.toString()
        viewModel.address2 = binding.etAdress2.text.toString()
        viewModel.country = binding.spinnerCountry.text.toString()
        viewModel.zipcode = binding.etZipcode.text.toString()
        viewModel.city = binding.etCity.text.toString()
        viewModel.state = binding.etState.text.toString()
        viewModel.location = binding.etLocation.text.toString()
        super.onPause()
    }
}