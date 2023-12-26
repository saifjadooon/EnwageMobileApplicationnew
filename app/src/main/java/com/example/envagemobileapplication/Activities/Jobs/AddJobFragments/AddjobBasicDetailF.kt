package com.example.envagemobileapplication.Activities.Jobs.AddJobFragments

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Jobs.AddJob.AddJobActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Fragments.BottomSheet.BSheetAddjobClientList
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobBasicDetailRequestModel
import com.example.envagemobileapplication.Models.RequestModels.PaygroupRequestModel
import com.example.envagemobileapplication.Models.RequestModels.getCustomTemplateRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.PayGroupResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentAddjobBasicDetailBinding

class AddjobBasicDetailF() : Fragment() {
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        if (dstart == 0 && end > start && source[start] == ' ') {
            // Block leading space
            return@InputFilter ""
        }
        null
    }
    private var industryFinal: Int = 0
    lateinit var industryList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.Datum>
    var paygroupIDFinal: Int = 0
    var payGroupListresponse: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.Datum> =
        ArrayList()
    private var clientidFinal: Int = 0
    var customTemplateList:
            ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse.Datum> =
        ArrayList()
    private var isfromselectedclient: Boolean = false
    private var selectedTemplateClientName: String? = ""
    private var clientidForPaygroup: Int? = 0
    lateinit var templateResponse: MutableList<Datum>
    lateinit var loader: Loader
    lateinit var clientid: String
    lateinit var tokenmanager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentAddjobBasicDetailBinding
    val viewModel: AddJobsSharedViewModel by activityViewModels()
    val bottomSheetFragment = BSheetAddjobClientList()
    lateinit var basicDetailsRM: AddJobBasicDetailRequestModel
    var jobTemplateId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddjobBasicDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initviews()
        clicklistners()
        observers()
        networkCalls()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Custom back press logic goes here
            showDialog()
        }

    }


    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            requireActivity().finish()
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun networkCalls() {
        loader.show()
        viewModel.getindustry(requireContext(), token)
    }

    private fun observers() {
        viewModel.LDcustomTemplate.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {
                templateResponse = it.data
                customTemplateList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    customTemplateList.add(it.data.get(i))
                }

                var items: ArrayList<String> = ArrayList()
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
                } catch (e: Exception) {

                }


            }
        }
        viewModel.LDpayGroup.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {
                if (isfromselectedclient) {
                    try {
                        bottomSheetFragment.dismiss()
                    } catch (e: Exception) {

                    }

                    loader.hide()
                    payGroupListresponse = ArrayList()
                    var paygrouplist: ArrayList<String> =
                        ArrayList()
                    for (i in 0 until it.data.size) {
                        payGroupListresponse.add(it.data.get(i))
                        paygrouplist.add(
                            it.data.get(i).payrollPayGroupTitle + " | " + it.data.get(
                                i
                            ).payrollPayGroupId
                        )
                    }

                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        paygrouplist
                    )
                    binding.spinnerPaygroup.setAdapter(adapter)
                    try {
                        binding.TIpaygroup.error = null
                        /*     binding.spinnerPaygroup.setText(
                                 it.data.get(0).payrollPayGroupTitle + " | " + it.data.get(
                                     0
                                 ).payrollPayGroupId
                             )*/
                    } catch (e: Exception) {
                    }
                    isfromselectedclient = false

                } else {
                    try {
                        payGroupListresponse = ArrayList()
                        var paygrouplist: ArrayList<String> =
                            ArrayList()

                        var paygroupresponselist: ArrayList<PayGroupResponse> = ArrayList()
                        for (i in 0 until it.data.size) {
                            paygroupresponselist.add(it)
                            payGroupListresponse.add(it.data.get(i))
                            paygrouplist.add(
                                it.data.get(i).payrollPayGroupTitle + " | " + it.data.get(
                                    i
                                ).payrollPayGroupId
                            )
                        }

                        val adapter = customadapter(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            paygrouplist
                        )
                        binding.spinnerPaygroup.setAdapter(adapter)
                        binding.TIpaygroup.error = null
                        binding.spinnerPaygroup.setText(
                            it.data.get(0).payrollPayGroupTitle + " | " + it.data.get(
                                0
                            ).payrollPayGroupId
                        )
                    } catch (e: Exception) {
                    }
                }

            }
        }
        viewModel.LDgetIndustry.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {
                industryList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    industryList.add(it.data.get(i))
                }

                var industrynames: ArrayList<String> = ArrayList()
                for (i in 0 until industryList.size) {
                    industrynames.add(industryList.get(i).name.toString())
                }
                try {
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        industrynames
                    )
                    binding.spinnerIndustry.setAdapter(adapter)
                } catch (e: Exception) {

                }

                binding.spinnerIndustry.setOnClickListener {
                    if (binding.spinnerIndustry.isPopupShowing) {
                        binding.spinnerIndustry.dismissDropDown()
                    } else {
                        var industrynames: ArrayList<String> = ArrayList()

                        for (i in 0 until industryList.size) {
                            industrynames.add(industryList.get(i).name.toString())
                        }
                        val adapter = customadapter(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            industrynames
                        )
                        binding.spinnerIndustry.setAdapter(adapter)
                        binding.spinnerIndustry.showDropDown()

                    }
                }

                binding.TIindustry.setOnTouchListener(View.OnTouchListener { v, event ->
                    if (binding.spinnerIndustry.isPopupShowing) {
                        binding.spinnerIndustry.dismissDropDown()
                    } else {
                        var industrynames: ArrayList<String> = ArrayList()
                        for (i in 0 until industryList.size) {
                            industrynames.add(industryList.get(i).name.toString())
                        }
                        val adapter = customadapter(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            industrynames
                        )
                        binding.spinnerIndustry.setAdapter(adapter)
                        binding.spinnerIndustry.showDropDown()

                    }
                    false
                })
                binding.spinnerIndustry.setOnTouchListener(View.OnTouchListener { v, event ->
                    if (binding.spinnerIndustry.isPopupShowing) {
                        binding.spinnerIndustry.dismissDropDown()
                    } else {
                        var industrynames: ArrayList<String> = ArrayList()
                        for (i in 0 until industryList.size) {
                            industrynames.add(industryList.get(i).name.toString())
                        }
                        val adapter = customadapter(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            industrynames
                        )
                        binding.spinnerIndustry.setAdapter(adapter)
                        binding.spinnerIndustry.showDropDown()

                    }
                    false
                })

                binding.TIindustry.onFocusChangeListener =
                    View.OnFocusChangeListener { _, hasFocus ->


                        if (binding.spinnerIndustry.isPopupShowing) {
                            binding.spinnerIndustry.dismissDropDown()
                        } else {
                            var industrynames: ArrayList<String> = ArrayList()
                            for (i in 0 until industryList.size) {
                                industrynames.add(industryList.get(i).name.toString())
                            }
                            val adapter = customadapter(
                                requireContext(),
                                R.layout.simple_spinner_item,
                                industrynames
                            )
                            binding.spinnerIndustry.setAdapter(adapter)
                            binding.spinnerIndustry.showDropDown()

                        }
                    }


            }
        }
        viewModel.LDdismissBottomsheet.observe(requireActivity()) {
            if (it != null) {
                binding.Ticlient.error = null
                binding.spinnerClient.setText(it.clientName)
                var model = PaygroupRequestModel(it.clientId!!, "a")
                viewModel.searchPayGroupApi(requireContext(), token, model)
                isfromselectedclient = true

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clicklistners() {

        binding.cbUseTemplate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                binding.spinnerSelectTemplate.setText("")
                binding.spinnerClient.setText("")
                binding.spinnerPaygroup.setText("")
                /*  var token = tokenmanager.getAccessToken()
                  var model = getCustomTemplateRequestModel("0")
                  viewModel.getCustomTemplates(model, token!!, requireContext())*/
                binding.spinnerSelectTemplate.isEnabled = true
                binding.spinnerClient.isEnabled = false
                binding.spinnerPaygroup.isEnabled = false


            } else {

                binding.spinnerSelectTemplate.isEnabled = false
                binding.spinnerClient.isEnabled = true
                binding.spinnerPaygroup.isEnabled = true
                binding.spinnerClient.setText("")
                binding.spinnerPaygroup.setText("")
                binding.spinnerSelectTemplate.setText("")

            }

        }
        /*       binding.spinnerSelectTemplate.setOnClickListener {
                   if (binding.spinnerSelectTemplate.isPopupShowing) {
                       binding.spinnerSelectTemplate.dismissDropDown()
                   } else {
                       binding.spinnerSelectTemplate.showDropDown()
                   }
               }*/
        binding.TISelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })

        binding.TIpaygroup.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerPaygroup.isPopupShowing) {
                binding.spinnerPaygroup.dismissDropDown()
            } else {
                binding.spinnerPaygroup.showDropDown()
            }
            false
        })

        binding.spinnerPaygroup.setOnClickListener {
            if (binding.spinnerPaygroup.isPopupShowing) {
                binding.spinnerPaygroup.dismissDropDown()
            } else {
                binding.spinnerPaygroup.showDropDown()
            }

        }

        binding.spinnerSelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })


        binding.spinnerSelectTemplate.setOnItemClickListener { _, _, position, _ ->
            // Update the selected position in the adapter
            var selectedText = binding.spinnerSelectTemplate.text

            for (i in 0 until templateResponse.size) {

                if (templateResponse.get(i).templateName.toString() == selectedText.toString()) {
                    clientidForPaygroup = templateResponse.get(i).clientId
                    selectedTemplateClientName = templateResponse.get(i).clientName
                    jobTemplateId = templateResponse.get(i).jobTemplateId.toString()
                }
            }
            binding.Ticlient.error = null
            binding.spinnerClient.setText(selectedTemplateClientName)
            var model = PaygroupRequestModel(clientidForPaygroup!!, "a")
            viewModel.searchPayGroupApi(requireContext(), token, model)
        }

        binding.Ticlient.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.cbUseTemplate.isChecked) {
                binding.spinnerClient.isEnabled = false

            } else {
                if (bottomSheetFragment.isAdded) {

                } else {
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }
            }
            false
        })

        binding.spinnerClient.setOnClickListener {
            if (binding.cbUseTemplate.isChecked) {
                binding.spinnerClient.isEnabled = false
            } else {
                if (bottomSheetFragment.isAdded) {
                    return@setOnClickListener
                } else {
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }
            }

        }

        binding.Ticlient.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (binding.cbUseTemplate.isChecked) {

                    binding.spinnerClient.isEnabled = false
                } else {
                    if (bottomSheetFragment.isAdded) {
                        return@OnFocusChangeListener
                    } else {
                        bottomSheetFragment.show(
                            requireActivity().supportFragmentManager,
                            bottomSheetFragment.tag
                        )
                    }
                }
            }
    }

    private fun initviews() {

        val maxLength = 200


        loader = Loader(requireContext())
        clientid = Constants.clientid.toString()
        tokenmanager = TokenManager(requireActivity())
        token = tokenmanager.getAccessToken()!!
        binding.spinnerSelectTemplate.isEnabled = false
        binding.etPosition.filters = arrayOf(filter)
        var token = tokenmanager.getAccessToken()
        var model = getCustomTemplateRequestModel("0")
        viewModel.getCustomTemplates(model, token!!, requireContext())
        val hint = "Position Name *"
        val formattedHint =
            com.example.envagemobileapplication.Utils.Constants.formatHintWithRedAsterisk(hint)
        binding.TIposition.hint = formattedHint

        val clientnamehint = "Client Name *"
        val formatedclientname =
            com.example.envagemobileapplication.Utils.Constants.formatHintWithRedAsterisk(
                clientnamehint
            )
        binding.Ticlient.hint = formatedclientname

        val paygrouphint = "Pay Group *"
        val formatedpaygroup =
            com.example.envagemobileapplication.Utils.Constants.formatHintWithRedAsterisk(
                paygrouphint
            )
        binding.TIpaygroup.hint = formatedpaygroup


        var jobNatureList: ArrayList<String> = ArrayList()
        jobNatureList.add("On-Site")
        jobNatureList.add("Remote")
        jobNatureList.add("Hybrid")

        val adapter = customadapter(
            requireContext(),
            R.layout.simple_spinner_item,
            jobNatureList
        )
        binding.spinnerJobNature.setAdapter(adapter)
        binding.spinnerJobNature.setText(jobNatureList.get(0))

        binding.spinnerJobNature.setOnClickListener {
            if (binding.spinnerJobNature.isPopupShowing) {
                binding.spinnerJobNature.dismissDropDown()
            } else {
                var jobNatureList: ArrayList<String> = ArrayList()
                jobNatureList.add("On-Site")
                jobNatureList.add("Remote")
                jobNatureList.add("Hybrid")
                val adapter = customadapter(
                    requireContext(),
                    R.layout.simple_spinner_item,
                    jobNatureList
                )
                binding.spinnerJobNature.setAdapter(adapter)
                binding.spinnerJobNature.showDropDown()
            }

        }

        binding.TIjobNature.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobNature.isPopupShowing) {
                binding.spinnerJobNature.dismissDropDown()
            } else {
                var jobNatureList: ArrayList<String> = ArrayList()
                jobNatureList.add("On-Site")
                jobNatureList.add("Remote")
                jobNatureList.add("Hybrid")
                val adapter = customadapter(
                    requireContext(),
                    R.layout.simple_spinner_item,
                    jobNatureList
                )
                binding.spinnerJobNature.setAdapter(adapter)
                binding.spinnerJobNature.showDropDown()
            }
            false
        })

        binding.btnNext.setOnClickListener {

            var positioname = binding.etPosition.text.toString()
            var clientname = binding.spinnerClient.text.toString()
            var paygroup = binding.spinnerPaygroup.text.toString()
            var industry = binding.spinnerIndustry.text.toString()

            for (i in 0 until customTemplateList.size) {
                if (customTemplateList.get(i).clientName == clientname) {
                    clientidFinal = customTemplateList.get(i).clientId
                }
            }



            for (i in 0 until payGroupListresponse.size) {
                var paygroupfinal =
                    payGroupListresponse.get(i).payrollPayGroupTitle + " | " + payGroupListresponse.get(
                        i
                    ).payrollPayGroupId
                if (paygroupfinal == paygroup) {
                    paygroupIDFinal = payGroupListresponse.get(i).payrollPayGroupId
                }
            }

            for (i in 0 until industryList.size) {
                if (industryList.get(i).name.equals(industry)) {
                    industryFinal = industryList.get(i).industryId
                }
            }

            var clientid = clientidFinal
            var paygroupid = paygroupIDFinal
            var industryid = industryFinal
            var jobnature = binding.spinnerJobNature.text.toString()
            var jobId = 0
            var usetemplate = false
            if (binding.cbUseTemplate.isChecked) {
                usetemplate = true
            } else {
                usetemplate = false
            }

            basicDetailsRM = AddJobBasicDetailRequestModel(
                positionName = positioname,
                clientId = clientid,
                payrollPayGroupId = paygroupid,
                jobId = jobId,
                industryId = industryid,
                jobNature = jobnature,
                useTemplate = usetemplate.toString(),
                jobTemplateId = jobTemplateId
            )

            com.example.envagemobileapplication.Utils.Global.basicDetailReqModel = basicDetailsRM
            if (!positioname.isNullOrEmpty() && !clientname.isNullOrEmpty() && !paygroup.isNullOrEmpty()) {

                AddJobActivity.binding.ivOne.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_basic_detail_done))
                replaceFragment(AddjobJobDetailF())

            } else {


                if (positioname.isNullOrEmpty()) {
                    binding.TIposition.error = "Position name is Required."
                    binding.TIposition.errorIconDrawable = null// Set the error message
                    binding.TIposition.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (clientname.isNullOrEmpty()) {
                    binding.Ticlient.error = "Client Name is Required."
                    binding.Ticlient.errorIconDrawable = null// Set the error message
                    binding.Ticlient.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }

                if (paygroup.isNullOrEmpty()) {
                    binding.TIpaygroup.error = "Paygroup is Required.."
                    binding.TIpaygroup.errorIconDrawable = null// Set the error message
                    binding.TIpaygroup.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }
        }

        binding.etPosition.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the length exceeds maxLength and trim the text if necessary
                if (s != null && s.length > maxLength) {
                    binding.etPosition.setText(s.subSequence(0, maxLength))
                    binding.etPosition.setSelection(maxLength) // Move the cursor to the end
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.TIposition.error = "Position name is Required."
                    binding.TIposition.errorIconDrawable = null// Set the error message
                    binding.TIposition.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                } else if (enteredText.isNotEmpty()) {
                    // URL is valid, you can update UI or perform other actions
                    binding.TIposition.error = null // Clear any previous error
                }
            }
        })
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.envagemobileapplication.R.id.cc_addjob_fragments, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}