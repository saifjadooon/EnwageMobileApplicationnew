package com.example.envagemobileapplication.Activities.Jobs.AddJobFragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Jobs.AddJob.AddJobActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentAddjobSalaryDetailBinding
import com.google.gson.Gson
import okhttp3.MultipartBody
import kotlin.math.round

class AddjobSalaryDetailF : Fragment() {
    private var frequeny: String = ""
    val viewModel: AddJobsSharedViewModel by activityViewModels()
    var global = com.example.envagemobileapplication.Utils.Global
    private var doubletimeType: String = "Paid and Billed"
    private var overtimeType: String = "Paid and Billed"
    private var doubleTimeMarkupPErcentage: Editable? = null
    var doubletimBillRateGlobal: String = ""
    var doubleTimePayRateGlobal: String = ""
    var overTimeBillRateGlobal: String = ""
    var overtimePayRateGlobal: String = ""
    var overtimemarkupPercentageGlobal: Editable? = null
    private var targetPayrate: String = ""
    private var maxPAyRatee: String = "0"
    private var minPayRate: String = "0"
    lateinit var binding: FragmentAddjobSalaryDetailBinding
    lateinit var token: String
    lateinit var loader: Loader
    lateinit var tokenmanager: TokenManager
    var overtimeMultiplier: Float = 1.5f
    var currentMultiplier: Float = overtimeMultiplier


    var doubletimeMultiplier: Float = 2.0f
    var doubletimeMultiplierMultiplier: Float = doubletimeMultiplier

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddjobSalaryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AddJobActivity.binding.ivFour.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_active_four))
        initViews()
        clickListeners()
        networkCalls()
        observers()
    }

    override fun onResume() {
        super.onResume()
        try {
            binding.etMarkupPErcentage.setText(viewModel.markuppercentage)
            binding.etPayrate.setText(viewModel.minpayrate)
            binding.etMinBillRate.setText(viewModel.minbillrate)
            binding.etMaxPayrate.setText(viewModel.maxpayrate)
            binding.etMaxBillRate.setText(viewModel.maxbillrate)
            binding.etTargetPayrate.setText(viewModel.targetPayrate)
            binding.etTargetBillRate.setText(viewModel.targetbillrate)
            binding.tvHeadCountOvertime.setText(viewModel.overtimemultiplier)
            binding.etOvertimeMarkupPercentage.setText(viewModel.overtimemarkuppercentage)
            binding.etovertimePayrate.setText(viewModel.ovetimepayrate)
            binding.etovertimeBillRate.setText(viewModel.overtimebillrate)
            binding.tvHeadCountdbltime.setText(viewModel.doubletimeMultiplier)
            binding.etDoubletimeMarkupPercentage.setText(viewModel.doubletimeMarkupPercentage)
            binding.etdoubletimePayrate.setText(viewModel.doubletimepayrate)
            binding.etdoubletimeBillRate.setText(viewModel.doubletimebillrate)
            binding.spinnerFrequency.setText(viewModel.frequency)
            if (viewModel.ovetimetype.equals("Paid and Billed")) {
                binding.rbPaidandBilledOvertime.isChecked = true
            } else if (viewModel.ovetimetype.equals("Paid not Billed")) {
                binding.rbPaidNotBilledOvertime.isChecked = true
            } else if (viewModel.ovetimetype.equals("None")) {
                binding.rbNoneOvertime.isChecked = true
            }

            if (viewModel.doubletimetype.equals("Paid and Billed")) {
                binding.rbPaidandBilledDoubletime.isChecked = true
            } else if (viewModel.doubletimetype.equals("Paid not Billed")) {
                binding.rbPaidNotBilledDoubletime.isChecked = true
            } else if (viewModel.doubletimetype.equals("None")) {
                binding.rbNonerbDoubletime.isChecked = true
            }
        } catch (e: Exception) {
        }

    }

    private fun observers() {
        viewModel.LDAddJob.observe(requireActivity())
        {
            loader.hide()
            if (it.data != null) {

                try {
                    binding.btnnext.isEnabled = true
                    Toast.makeText(requireContext(), "Job created Successfully", Toast.LENGTH_LONG)
                        .show()

                    val delayMillis = 3000L // Delay between transitions in milliseconds
                    val handler = Handler()
                    handler.postDelayed({
                        requireActivity().finish()
                    }, delayMillis)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Job created Successfully", Toast.LENGTH_LONG)
                        .show()
                    val delayMillis = 3000L // Delay between transitions in milliseconds
                    val handler = Handler()
                    handler.postDelayed({
                        requireActivity().finish()
                    }, delayMillis)

                }
            }
        }
    }

    private fun networkCalls() {

    }

    private fun clickListeners() {

        binding.btnback.setOnClickListener {

            viewModel.markuppercentage = binding.etMarkupPErcentage.text.toString()
            viewModel.minpayrate = binding.etPayrate.text.toString()
            viewModel.minbillrate = binding.etMinBillRate.text.toString()
            viewModel.maxpayrate = binding.etMaxPayrate.text.toString()
            viewModel.maxbillrate = binding.etMaxBillRate.text.toString()
            viewModel.targetPayrate = binding.etTargetPayrate.text.toString()
            viewModel.targetbillrate = binding.etTargetBillRate.text.toString()
            viewModel.overtimemultiplier = binding.tvHeadCountOvertime.text.toString()
            viewModel.overtimemarkuppercentage = binding.etOvertimeMarkupPercentage.text.toString()
            viewModel.ovetimepayrate = binding.etovertimePayrate.text.toString()
            viewModel.overtimebillrate = binding.etovertimeBillRate.text.toString()
            viewModel.doubletimeMultiplier = binding.tvHeadCountdbltime.text.toString()
            viewModel.doubletimeMarkupPercentage =
                binding.etDoubletimeMarkupPercentage.text.toString()
            viewModel.doubletimepayrate = binding.etdoubletimePayrate.text.toString()
            viewModel.doubletimebillrate = binding.etdoubletimeBillRate.text.toString()
            viewModel.frequency = binding.spinnerFrequency.text.toString()
            viewModel.ovetimetype = overtimeType
            viewModel.doubletimetype = doubletimeType
            requireActivity().onBackPressed()
        }

        binding.spinnerFrequency.setOnItemClickListener { _, _, position, _ ->

            binding.TIFrequency.error = null
        }
        binding.btnnext.setOnClickListener {
            var frequencyy = binding.spinnerFrequency.text.toString()
            if (!overtimemarkupPercentageGlobal.isNullOrEmpty() && !maxPAyRatee.isNullOrEmpty() && !minPayRate.isNullOrEmpty() && !targetPayrate.isNullOrEmpty()
                && !frequencyy.isNullOrEmpty()
            ) {

                binding.btnnext.isEnabled = false

                var description = global.addJobDetailModel?.description
                var clientid = global.basicDetailReqModel?.clientId
                var payrollPayGroupid = global.basicDetailReqModel?.payrollPayGroupId
                var jobid = global.basicDetailReqModel?.jobId
                var industryid = global.basicDetailReqModel?.industryId
                var jobnature = global.basicDetailReqModel?.jobNature
                var address1 = global.addJobAddressDetailModel?.address1
                var address2 = global.addJobAddressDetailModel?.address2
                var country = global.addJobAddressDetailModel?.country
                var zipcode = global.addJobAddressDetailModel?.zipcode
                var city = global.addJobAddressDetailModel?.city
                var statee = global.addJobAddressDetailModel?.state
                var location = global.addJobAddressDetailModel?.location
                var headcount = global.addJobDetailModel?.headcount
                var jobtype = global.addJobDetailModel?.jobType
                var startDate = global.addJobDetailModel?.startDate
                var endDate = global.addJobDetailModel?.endDate
                var currency = global.addJobDetailModel?.currency
                var workingDaysNo = global.addJobDetailModel?.workingDaysNo
                var estimatedHours = global.addJobDetailModel?.estimatedHours
                var workingdays = global.addJobDetailModel?.workingDays
                var jobstatusID = global.addJobDetailModel?.jobStatusId
                var jobskills = global.addJobDetailModel?.jobSkills
                var markupPercentage = binding.etMarkupPErcentage.text.toString()
                var minPayrate = binding.etPayrate.text.toString()
                var minBillRate = binding.etMinBillRate.text.toString()
                var maxPayRate = binding.etMaxPayrate.text.toString()
                var maxBillRate = binding.etMaxBillRate.text.toString()
                var targetPayRate = binding.etTargetPayrate.text.toString()
                var targetBillrate = binding.etTargetBillRate.text.toString()
                var overtimeMultiplier = binding.tvHeadCountOvertime.text.toString()
                var overTimeTypefinal = overtimeType
                var overtimeMarkupPercentage = binding.etOvertimeMarkupPercentage.text.toString()
                var overTimeBillRate = binding.etovertimeBillRate.text.toString()
                var overtimePayRate = binding.etovertimePayrate.text.toString()
                var doubletimeMultiplier = binding.tvHeadCountdbltime.text.toString()
                var doubletimeTypefinal = doubletimeType
                var doubleTimeMarkUpPercentage =
                    binding.etDoubletimeMarkupPercentage.text.toString()
                var doubletimePayRate = binding.etdoubletimePayrate.text.toString()
                var doubletimeBillRate = binding.etdoubletimeBillRate.text.toString()
                var frequencyy = binding.spinnerFrequency.text.toString()
                var jobskilllist = global.addJobDetailModel?.jobSkills

                var token = tokenmanager.getAccessToken()

// Convert the list to a JSON string
                val gson = Gson()
                //  val json = gson.toJson(jobskilllist)

// Convert jobskilllist to a list of LinkedHashMap to control property order
                val listOfLinkedHashMaps = jobskilllist?.map { jobSkill ->
                    linkedMapOf(
                        "skillId" to jobSkill.skillId,
                        "name" to jobSkill.name,
                        "skillRating" to jobSkill.skillRating,
                        "IsDeleted" to jobSkill.IsDeleted,
                        "jobId" to jobSkill.jobId
                    )
                }

                val json = gson.toJson(listOfLinkedHashMaps)
                Log.i("jsonstringofskills", json)

                var weekdays = global.addJobDetailModel?.workingDays



                viewModel.addJobApi(
                    binding,
                    requireContext(),
                    token.toString(),
                    description!!,
                    MultipartBody.Part.createFormData(
                        "positionName",
                        global.basicDetailReqModel?.positionName.toString()
                    ),
                    MultipartBody.Part.createFormData("clientId", clientid.toString()),
                    MultipartBody.Part.createFormData(
                        "payrollPayGroupId",
                        payrollPayGroupid.toString()
                    ),
                    MultipartBody.Part.createFormData("jobId", jobid.toString()),
                    MultipartBody.Part.createFormData("industryId", industryid.toString()),
                    MultipartBody.Part.createFormData("jobNature", jobnature.toString()),
                    MultipartBody.Part.createFormData("address1", address1.toString()),
                    MultipartBody.Part.createFormData("address2", address2.toString()),
                    MultipartBody.Part.createFormData("country", country.toString()),
                    MultipartBody.Part.createFormData("zipcode", zipcode.toString()),
                    MultipartBody.Part.createFormData("city", city.toString()),
                    MultipartBody.Part.createFormData("state", statee.toString()),
                    MultipartBody.Part.createFormData("location", location.toString()),
                    MultipartBody.Part.createFormData("headcount", headcount.toString()),
                    MultipartBody.Part.createFormData("jobType", jobtype.toString()),
                    MultipartBody.Part.createFormData("startDate", startDate.toString()),
                    MultipartBody.Part.createFormData("endDate", endDate.toString()),
                    MultipartBody.Part.createFormData("currency", currency.toString()),
                    MultipartBody.Part.createFormData("minimumSalary", "0"),
                    MultipartBody.Part.createFormData("maximumSalary", "0"),
                    MultipartBody.Part.createFormData("workingDaysNo", workingDaysNo.toString()),
                    MultipartBody.Part.createFormData("estimatedHours", estimatedHours.toString()),
                    MultipartBody.Part.createFormData("WorkingDays", workingdays.toString()),
                    MultipartBody.Part.createFormData("jobStatusId", jobstatusID.toString()),
                    MultipartBody.Part.createFormData("jobSkills", json.toString()),
                    MultipartBody.Part.createFormData("experienceRequired", "2"),
                    MultipartBody.Part.createFormData(
                        "useTemplate",
                        global.basicDetailReqModel?.useTemplate.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "jobTemplateId",
                        global.basicDetailReqModel?.jobTemplateId.toString()
                    ),
                    MultipartBody.Part.createFormData("markup", markupPercentage.toString()),
                    MultipartBody.Part.createFormData("minPayRate", minPayrate.toString()),
                    MultipartBody.Part.createFormData("minBillRate", minBillRate.toString()),
                    MultipartBody.Part.createFormData("maxPayRate", maxPayRate.toString()),
                    MultipartBody.Part.createFormData("maxBillRate", maxBillRate.toString()),
                    MultipartBody.Part.createFormData("targetPayRate", targetPayRate.toString()),
                    MultipartBody.Part.createFormData("targetBillRate", targetBillrate.toString()),
                    MultipartBody.Part.createFormData(
                        "overTimeMultiplier",
                        overtimeMultiplier.toString()
                    ),
                    MultipartBody.Part.createFormData("overtimeType", overtimeType.toString()),
                    MultipartBody.Part.createFormData(
                        "overtimeMarkup",
                        overtimeMarkupPercentage.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "overtimePayRate",
                        overtimePayRate.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "overtimeBillRate",
                        overTimeBillRate.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "doubletimeMultiplier",
                        doubletimeMultiplier.toString()
                    ),
                    MultipartBody.Part.createFormData("doubletimeType", doubletimeType.toString()),
                    MultipartBody.Part.createFormData(
                        "doubletimeMarkup",
                        doubleTimeMarkUpPercentage.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "doubletimePayRate",
                        doubletimePayRate.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "doubletimeBillRate",
                        doubletimeBillRate.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "frequency",
                        binding.spinnerFrequency.text.toString()
                    ),
                    MultipartBody.Part.createFormData("ApplicationFormId", "1036"),
                    MultipartBody.Part.createFormData("ShowSalary", "false"),
                    MultipartBody.Part.createFormData("ShowNature", "false"),
                    MultipartBody.Part.createFormData("ShowClient", "false"),
                    MultipartBody.Part.createFormData("ShowIndustry", "false"),
                    MultipartBody.Part.createFormData("ShowAddress", "false"),
                    MultipartBody.Part.createFormData("ShowType", "false"),
                    MultipartBody.Part.createFormData("ShowSkills", "false"),
                    MultipartBody.Part.createFormData("ShowShift", "false"),
                    MultipartBody.Part.createFormData("IsPublish", "false")
                )
            } else {

                if (frequencyy.isNullOrEmpty()) {
                    binding.TIFrequency.error = "Frequency is Required."
                }
                if (overtimemarkupPercentageGlobal.isNullOrEmpty()) {
                    binding.TImarkupPercentage.error = "Markup Percentage is Required."
                }
                if (maxPAyRatee.isNullOrEmpty()) {
                    binding.TImaxPayrate.error = "Max. Pay Rate  is Required."
                }
                if (minPayRate.isNullOrEmpty()) {
                    binding.TIminPayrate.error = "Min. Pay Rate  is Required."
                }
                if (targetPayrate.isNullOrEmpty()) {
                    binding.TItargetPayrate.error = "Target Pay Rate is Required."
                }

            }

        }


        binding.radioGroupOvertime.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // Handle the checked RadioButton
            when (checkedId) {
                R.id.rbPaidandBilledOvertime -> {

                    overtimeType = "Paid and Billed"
                    if (binding.rbPaidandBilledOvertime.isChecked) {
                        binding.constraintLayout10.visibility = View.VISIBLE
                        binding.TIovertimeMarkupPercentage.visibility = View.VISIBLE
                        if (!overtimemarkupPercentageGlobal.isNullOrEmpty()) {
                            binding.etOvertimeMarkupPercentage.text = overtimemarkupPercentageGlobal
                        }
                        if (!overtimePayRateGlobal.isNullOrEmpty()) {
                            binding.etovertimePayrate.setText(overtimePayRateGlobal)
                        }

                        if (!overTimeBillRateGlobal.isNullOrEmpty()) {
                            binding.etovertimeBillRate.setText(overTimeBillRateGlobal)
                        }

                    }
                }
                R.id.rbPaidNotBilledOvertime -> {

                    overtimeType = "Paid not Billed"
                    if (binding.rbPaidNotBilledOvertime.isChecked) {

                        binding.etovertimeBillRate.setText(overtimePayRateGlobal.toString())
                        binding.etovertimePayrate.setText(overtimePayRateGlobal.toString())
                        binding.TIovertimeMarkupPercentage.visibility = View.GONE
                        binding.constraintLayout10.visibility = View.VISIBLE
                    } else {

                    }
                }
                R.id.rbNoneOvertime -> {
                    overtimeType = "None"
                    if (binding.rbNoneOvertime.isChecked) {
                        binding.TIovertimeMarkupPercentage.visibility = View.VISIBLE
                        binding.constraintLayout10.visibility = View.GONE
                        binding.TIovertimeMarkupPercentage.isEnabled = false
                        binding.TIOvertimePayrate.isEnabled = false
                        binding.TIovertimeBillrate.isEnabled = false
                        binding.etovertimePayrate.setText("")
                        binding.etovertimeBillRate.setText("")
                        binding.etOvertimeMarkupPercentage.setText("")
                    } else {

                    }
                }
            }
        })
        binding.radioGroupDoubletime.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // Handle the checked RadioButton
            when (checkedId) {
                R.id.rbPaidandBilledDoubletime -> {

                    doubletimeType = "Paid and Billed"
                    if (binding.rbPaidandBilledDoubletime.isChecked) {


                        if (!doubleTimePayRateGlobal.isNullOrEmpty()) {

                            binding.etdoubletimePayrate.setText(doubleTimePayRateGlobal)
                        }

                        if (!doubletimBillRateGlobal.isNullOrEmpty()) {

                            binding.etdoubletimeBillRate.setText(doubletimBillRateGlobal)
                        }

                        if (!doubleTimeMarkupPErcentage.isNullOrEmpty()) {
                            binding.etDoubletimeMarkupPercentage.text = doubleTimeMarkupPErcentage
                        }

                        binding.ccDoubletimeMultiplier.visibility = View.VISIBLE
                        binding.TIdoubletimeMarkupPercentage.visibility = View.VISIBLE
                    } else {

                    }
                }
                R.id.rbPaidNotBilledDoubletime -> {

                    doubletimeType = "Paid not Billed"
                    if (binding.rbPaidNotBilledDoubletime.isChecked) {
                        binding.etdoubletimePayrate.setText(doubleTimePayRateGlobal)
                        binding.etdoubletimeBillRate.setText(doubleTimePayRateGlobal)
                        binding.TIdoubletimeMarkupPercentage.visibility = View.GONE
                        binding.ccDoubletimeMultiplier.visibility = View.VISIBLE
                    } else {

                    }
                }
                R.id.rbNonerbDoubletime -> {
                    doubletimeType = "None"
                    if (binding.rbNonerbDoubletime.isChecked) {
                        binding.TIdoubletimeMarkupPercentage.visibility = View.VISIBLE
                        binding.ccDoubletimeMultiplier.visibility = View.GONE
                        binding.TIdoubletimeMarkupPercentage.isEnabled = false
                        binding.TIDoubletimePayrate.isEnabled = false
                        binding.TIdoubletimeBillrate.isEnabled = false
                        binding.etdoubletimePayrate.setText("")
                        binding.etdoubletimeBillRate.setText("")
                        binding.etDoubletimeMarkupPercentage.setText("")
                    } else {

                    }
                }
            }
        })

        binding.ivMinusdbltime.setOnClickListener {
            doubletimeMultiplier -= 0.1f
            // Ensure the value is not less than 0
            doubletimeMultiplier = maxOf(0f, doubletimeMultiplier)

            // Round to one digit after the decimal point
            doubletimeMultiplier = round(doubletimeMultiplier * 10) / 10


            binding.tvHeadCountdbltime.text = doubletimeMultiplier.toString()


            var doubletimex = doubletimeMultiplier

            if (!targetPayrate.isNullOrEmpty()) {
                var doubleTimePayrate =
                    calculateOvertimePayRate(
                        targetPayrate.toDouble(),
                        doubletimex.toString().toDouble()
                    )

                doubleTimePayRateGlobal = doubleTimePayrate.toString()
                binding.etdoubletimePayrate.setText(doubleTimePayrate.toString())
                var markupPercentage = binding.etMarkupPErcentage.text
                try {
                    val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                    var markupOT = doubleTimePayrate * (markuppercentagedouble / 100)
                    Log.i("markupot", markupOT.toString())
                    var doubletimeBillRate = markupOT + doubleTimePayrate
                    doubletimBillRateGlobal = doubletimeBillRate.toString()
                    binding.etdoubletimeBillRate.setText(doubletimeBillRate.toString())
                } catch (e: Exception) {

                }

            }
        }
        binding.ivPlusdbltime.setOnClickListener {

            doubletimeMultiplier += 0.1f

            // Ensure the value is not less than 0
            doubletimeMultiplier = maxOf(0f, doubletimeMultiplier)

            // Round to one digit after the decimal point
            doubletimeMultiplier = round(doubletimeMultiplier * 10) / 10


            binding.tvHeadCountdbltime.text = doubletimeMultiplier.toString()


            var doubletimex = doubletimeMultiplier

            if (!targetPayrate.isNullOrEmpty()) {
                var doubleTimePayrate =
                    calculateOvertimePayRate(
                        targetPayrate.toDouble(),
                        doubletimex.toString().toDouble()
                    )
                doubleTimePayRateGlobal = doubleTimePayrate.toString()
                binding.etdoubletimePayrate.setText(doubleTimePayrate.toString())
                var markupPercentage = binding.etMarkupPErcentage.text
                try {
                    val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                    var markupOT = doubleTimePayrate * (markuppercentagedouble / 100)
                    Log.i("markupot", markupOT.toString())
                    var doubletimeBillRate = markupOT + doubleTimePayrate
                    doubletimBillRateGlobal = doubletimeBillRate.toString()
                    binding.etdoubletimeBillRate.setText(doubletimeBillRate.toString())
                } catch (e: Exception) {
                }

            }
        }

        binding.ivMinusOvertime.setOnClickListener {

            currentMultiplier -= 0.1f

            // Ensure the value is not less than 0
            currentMultiplier = maxOf(0f, currentMultiplier)

            // Round to one digit after the decimal point
            currentMultiplier = round(currentMultiplier * 10) / 10


            binding.tvHeadCountOvertime.text = currentMultiplier.toString()


            var ovettimeMultiplur = currentMultiplier

            if (!targetPayrate.isNullOrEmpty()) {
                var overTimePayrate =
                    calculateOvertimePayRate(
                        targetPayrate.toDouble(),
                        ovettimeMultiplur.toString().toDouble()
                    )

                overtimePayRateGlobal = overTimePayrate.toString()
                binding.etovertimePayrate.setText(overTimePayrate.toString())
                var markupPercentage = binding.etMarkupPErcentage.text
                try {
                    val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                    var markupOT = overTimePayrate * (markuppercentagedouble / 100)
                    Log.i("markupot", markupOT.toString())
                    var overtimeBillRate = markupOT + overTimePayrate
                    overTimeBillRateGlobal = overtimeBillRate.toString()
                    binding.etovertimeBillRate.setText(overtimeBillRate.toString())
                } catch (e: Exception) {
                }
            }

        }
        binding.ivPlusOvertime.setOnClickListener {

            currentMultiplier += 0.1f

            // Ensure the value is not less than 0
            currentMultiplier = maxOf(0f, currentMultiplier)

            // Round to one digit after the decimal point
            currentMultiplier = round(currentMultiplier * 10) / 10
            binding.tvHeadCountOvertime.text = currentMultiplier.toString()


            var ovettimeMultiplur = currentMultiplier

            if (!targetPayrate.isNullOrEmpty()) {
                var overTimePayrate =
                    calculateOvertimePayRate(
                        targetPayrate.toDouble(),
                        ovettimeMultiplur.toString().toDouble()
                    )
                overtimePayRateGlobal = overTimePayrate.toString()
                binding.etovertimePayrate.setText(overTimePayrate.toString())
                var markupPercentage = binding.etMarkupPErcentage.text
                try {
                    val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                    var markupOT = overTimePayrate * (markuppercentagedouble / 100)
                    Log.i("markupot", markupOT.toString())
                    var overtimeBillRate = markupOT + overTimePayrate
                    overTimeBillRateGlobal = overtimeBillRate.toString()
                    binding.etovertimeBillRate.setText(overtimeBillRate.toString())
                } catch (e: Exception) {
                }


            }


        }
        binding.etPayrate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TIminPayrate.error = null
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                minPayRate = enteredText
                var markupPercentage = binding.etMarkupPErcentage.text


                if (!enteredText.isEmpty() && !markupPercentage!!.isEmpty()) {


                    val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                    val minPayRatedouble: Double = minPayRate.toString().toDouble()
                    var calculatePayRate =
                        calculateMinBillRate(markuppercentagedouble, minPayRatedouble)
                    binding.etMinBillRate.setText(calculatePayRate.toString())
                }

            }
        })
        binding.etMaxPayrate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
                binding.TImaxPayrate.error = null
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()
                maxPAyRatee = enteredText
                var markupPercentage = binding.etMarkupPErcentage.text
                if (!enteredText.isEmpty() && !markupPercentage!!.isEmpty()) {

                    if (minPayRate >= enteredText) {
                        binding.TImaxPayrate.error =
                            "Max. Pay Rate should be greater than Min. Pay Rate"
                    } else {
                        binding.TImaxPayrate.error = null
                        maxPAyRatee = enteredText
                        val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                        val maxPAyRateedouble: Double = maxPAyRatee.toString().toDouble()
                        var calculateMaxPayRate =
                            calculateMaxBillRate(maxPAyRateedouble, markuppercentagedouble)
                        binding.etMaxBillRate.setText(calculateMaxPayRate.toString())
                    }
                } else {
                    binding.TImaxPayrate.error = null
                }
            }
        })
        binding.etTargetPayrate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TItargetPayrate.error = null
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()
                var markupPercentage = binding.etMarkupPErcentage.text
                if (!enteredText.isEmpty() && !markupPercentage!!.isEmpty()) {


                    if (isTargetPayRateValid(
                            minPayRate.toDouble(),
                            maxPAyRatee.toDouble(),
                            enteredText.toDouble()
                        )
                    ) {
                        targetPayrate = enteredText

                        binding.TItargetPayrate.error = null
                        val markuppercentagedouble: Double = markupPercentage.toString().toDouble()
                        val targetPayratedouble: Double = targetPayrate.toString().toDouble()
                        var calculateTargetPayRate =
                            calculateTargetBillRate(targetPayratedouble, markuppercentagedouble)
                        binding.etTargetBillRate.setText(calculateTargetPayRate.toString())


                        var ovettimeMultiplur = 1.5
                        var doubletimeMulltiplier = 2.0

                        var overTimePayrate =
                            calculateOvertimePayRate(targetPayrate.toDouble(), ovettimeMultiplur)
                        overtimePayRateGlobal = overTimePayrate.toString()
                        binding.etovertimePayrate.setText(overTimePayrate.toString())

                        var markupOT = overTimePayrate * (markuppercentagedouble / 100)
                        Log.i("markupot", markupOT.toString())
                        var overtimeBillRate = markupOT + overTimePayrate

                        overTimeBillRateGlobal = overtimeBillRate.toString()
                        binding.etovertimeBillRate.setText(overtimeBillRate.toString())

                        var doubletimePAyrate =
                            calculateDoubletimePayRate(
                                targetPayrate.toDouble(),
                                doubletimeMulltiplier
                            )

                        doubleTimePayRateGlobal = doubletimePAyrate.toString()
                        binding.etdoubletimePayrate.setText(doubletimePAyrate.toString())


                        var markupDt = doubletimePAyrate * (markuppercentagedouble / 100)
                        Log.i("markupot", markupOT.toString())
                        var doubletimeBillRate = markupDt + doubletimePAyrate

                        doubletimBillRateGlobal = doubletimeBillRate.toString()
                        binding.etdoubletimeBillRate.setText(doubletimeBillRate.toString())

                    } else {

                        binding.TItargetPayrate.error =
                            "Target Pay Rate should be between Min. Pay Rate & Max. Pay Rate"
                    }


                } else {
                    binding.TItargetPayrate.error = null
                    targetPayrate = "" // Reset the targetPayrate or take any other action

                    binding.etovertimePayrate.setText("")
                    binding.etovertimeBillRate.setText("")
                    binding.etdoubletimePayrate.setText("")
                    binding.etdoubletimeBillRate.setText("")
                    // You may want to set default values or perform other actions

                }

            }
        })
        binding.etMarkupPErcentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
                binding.TImarkupPercentage.error = null
            }

            override fun afterTextChanged(editable: Editable?) {

                val enteredText = editable.toString()
                overtimemarkupPercentageGlobal = binding.etMarkupPErcentage.text
                doubleTimeMarkupPErcentage = overtimemarkupPercentageGlobal
                binding.etOvertimeMarkupPercentage.setText(enteredText)
                binding.etDoubletimeMarkupPercentage.setText(enteredText)
            }
        })
    }

    private fun calculateOvertimePayRate(targetPayrate: Double, ovettimeMultiplur: Double): Double {

        var overTimeePayRate = targetPayrate * ovettimeMultiplur
        return overTimeePayRate
    }

    private fun calculateDoubletimePayRate(
        targetPayrate: Double,
        ovettimeMultiplur: Double
    ): Double {

        var overTimeePayRate = targetPayrate * ovettimeMultiplur
        return overTimeePayRate
    }

    private fun initViews() {
        loader = Loader(requireContext())
        tokenmanager = TokenManager(requireContext())

        val markupPercentage = "Markup Percentage *"
        val formattedmarkupPercentage = formatHintWithRedAsterisk(markupPercentage)
        binding.TImarkupPercentage.hint = formattedmarkupPercentage

        val minPayRate = "Min. Pay Rate *"
        val formattedminPayRate = formatHintWithRedAsterisk(minPayRate)
        binding.TIminPayrate.hint = formattedminPayRate

        val maxPayRate = "Max. Pay Rate *"
        val formattedmaxPayRate = formatHintWithRedAsterisk(maxPayRate)
        binding.TImaxPayrate.hint = formattedmaxPayRate

        val targetPayRate = "Target Pay Rate *"
        val formattedtargetPayRate = formatHintWithRedAsterisk(targetPayRate)
        binding.TItargetPayrate.hint = formattedtargetPayRate

        val frequencyy = "Frequency *"
        val formattetFrequency = formatHintWithRedAsterisk(frequencyy)
        binding.TIFrequency.hint = formattetFrequency

        binding.spinnerFrequency.setOnClickListener {
            if (binding.spinnerFrequency.isPopupShowing) {
                binding.spinnerFrequency.dismissDropDown()
            } else {
                var frequencylist: ArrayList<String> = ArrayList()
                frequencylist.add("Daily")
                frequencylist.add("Hourly")
                frequencylist.add("Weekly")
                frequencylist.add("Monthly")
                frequencylist.add("Annually")
                val adapter = customadapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    frequencylist
                )
                binding.spinnerFrequency.setAdapter(adapter)
                binding.spinnerFrequency.showDropDown()
            }
        }
        binding.TIFrequency.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerFrequency.isPopupShowing) {
                binding.spinnerFrequency.dismissDropDown()
            } else {
                var frequencylist: ArrayList<String> = ArrayList()
                frequencylist.add("Daily")
                frequencylist.add("Hourly")
                frequencylist.add("Weekly")
                frequencylist.add("Monthly")
                frequencylist.add("Annually")
                val adapter = customadapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    frequencylist
                )
                binding.spinnerFrequency.setAdapter(adapter)
                binding.spinnerFrequency.showDropDown()
            }

            false
        })

        binding.spinnerFrequency.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerFrequency.isPopupShowing) {
                binding.spinnerFrequency.dismissDropDown()
            } else {
                var frequencylist: ArrayList<String> = ArrayList()
                frequencylist.add("Daily")
                frequencylist.add("Hourly")
                frequencylist.add("Weekly")
                frequencylist.add("Monthly")
                frequencylist.add("Annually")
                val adapter = customadapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    frequencylist
                )
                binding.spinnerFrequency.setAdapter(adapter)
                binding.spinnerFrequency.showDropDown()
            }
            false
        })
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

    fun calculateMinBillRate(markupPercentage: Double, minPayRate: Double): Double {
        val markupMinimumPayRate = minPayRate * (markupPercentage / 100)
        val minBillRate = markupMinimumPayRate + minPayRate
        return minBillRate
    }

    fun calculateMaxBillRate(maxPayRate: Double, markupPercentage: Double): Double {

        val markupAmount = maxPayRate * (markupPercentage / 100)
        val maxBillRate = maxPayRate + markupAmount
        return maxBillRate
    }

    fun calculateTargetBillRate(targetPayRate: Double, markupPercentage: Double): Double {
        val markupAmount = targetPayRate * (markupPercentage / 100)
        val targetBillRate = targetPayRate + markupAmount
        return targetBillRate
    }

    fun isTargetPayRateValid(
        minPayRate: Double,
        maxPayRate: Double,
        targetPayRate: Double
    ): Boolean {
        return targetPayRate > minPayRate && targetPayRate < maxPayRate
    }


    override fun onPause() {
        viewModel.markuppercentage = binding.etMarkupPErcentage.text.toString()
        viewModel.minpayrate = binding.etPayrate.text.toString()
        viewModel.minbillrate = binding.etMinBillRate.text.toString()
        viewModel.maxpayrate = binding.etMaxPayrate.text.toString()
        viewModel.maxbillrate = binding.etMaxBillRate.text.toString()
        viewModel.targetPayrate = binding.etTargetPayrate.text.toString()
        viewModel.targetbillrate = binding.etTargetBillRate.text.toString()
        viewModel.overtimemultiplier = binding.tvHeadCountOvertime.text.toString()
        viewModel.overtimemarkuppercentage = binding.etOvertimeMarkupPercentage.text.toString()
        viewModel.ovetimepayrate = binding.etovertimePayrate.text.toString()
        viewModel.overtimebillrate = binding.etovertimeBillRate.text.toString()
        viewModel.doubletimeMultiplier = binding.tvHeadCountdbltime.text.toString()
        viewModel.doubletimeMarkupPercentage =
            binding.etDoubletimeMarkupPercentage.text.toString()
        viewModel.doubletimepayrate = binding.etdoubletimePayrate.text.toString()
        viewModel.doubletimebillrate = binding.etdoubletimeBillRate.text.toString()
        viewModel.frequency = binding.spinnerFrequency.text.toString()
        viewModel.ovetimetype = overtimeType
        viewModel.doubletimetype = doubletimeType
        super.onPause()
    }

}