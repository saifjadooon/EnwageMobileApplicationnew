package com.example.envagemobileapplication.Activities.Jobs.AddJobFragments

import android.R
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Jobs.AddJob.AddJobActivity
import com.example.envagemobileapplication.Adapters.CustomJobSkillsAdapter
import com.example.envagemobileapplication.Adapters.CustomSpinnerAdapterForWeekdays
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.SkillsRequestModels
import com.example.envagemobileapplication.Models.RequestModels.weekdaysModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobSkillsResponse.JobSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getJobStatusResponse.GetJobStatusResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.DatePickerHelper
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentAddjobJobDetailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddjobJobDetailF : Fragment() {
    lateinit var jobtypeList: ArrayList<String>
    val viewModel: AddJobsSharedViewModel by activityViewModels()
    var global: Global.Companion =
        com.example.envagemobileapplication.Utils.Global
    private var concatenatedNamesfinal: String = ""
    var arraylistskills: ArrayList<JobSkill> = ArrayList()
    lateinit var description: MultipartBody.Part
    private val jobskilllistGlobal: ArrayList<JobSkill> = ArrayList()
    private val jobStatusIdslistGlobal: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getJobStatusResponse.Datum> =
        ArrayList()
    private var mEditor: RichEditor? = null
    var jobskillsList: ArrayList<JobSkill> = ArrayList()
    private lateinit var datePickerHelper: DatePickerHelper
    val weekdays = listOf(
        weekdaysModel(1, "Sun"),
        weekdaysModel(2, "Mon"),
        weekdaysModel(3, "Tue"),
        weekdaysModel(4, "Wed"),
        weekdaysModel(5, "Thu"),
        weekdaysModel(6, "Fri"),
        weekdaysModel(7, "Sat")
    )
    var descriptiontext: String = ""
    val workingHours = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
    )
    private var counter = 0
    lateinit var binding: FragmentAddjobJobDetailBinding
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var searchviewtext = "a"
    var searchjobSkillText = "a"
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddjobJobDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun updateVisibility() {
        val selectedCount = viewModel.selectedPositions.count { it }
        if (selectedCount >= 1) {
            binding.tvskillllstext.visibility = View.VISIBLE
            binding.tvskillllstext.text = "$selectedCount Skills Selected"
        } else {
            binding.tvskillllstext.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("isStartDateFieldEnabled", viewModel.isStartDateFieldEnabled)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initviews()
        clicklisteners()
        networkCalls()

    }


    override fun onResume() {
        super.onResume()

        if (viewModel.isStartDateFieldEnabled) {
            binding.ccStartdate.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
            binding.ccStartdate.isEnabled = true
            binding.tvStartDate.text = viewModel.startdate
            //   binding.tvHeadCount.text = global.headcount
        }
        if (viewModel.isEndDateFieldEnabled) {
            binding.ccEnddate.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
            binding.ccEnddate.isEnabled = true
            binding.tvEndDate.text = viewModel.endDate

        }
        try {
            if (viewModel.headcount != "") {
                binding.tvHeadCount.setText(viewModel.headcount)
            }

            binding.spinnerCurrency.setText(viewModel.currency)

        } catch (e: Exception) {
        }
        if (viewModel.weekdaysConcatinated != "") {
            binding.TIweekdays.visibility = View.INVISIBLE
            binding.tvWeekdaysconcaatinated.visibility = View.VISIBLE
            binding.tvWeekdaysconcaatinated.setText(viewModel.weekdaysConcatinated)
            binding.tvDaysCount.text = viewModel.noofWorkingDays
        }
        if (global.descriptiontext != "") {

            // binding.etClientDescription.loadData(global.descriptiontext, "", "")
        }

        if (viewModel.skillCountText != "") {

            //  binding.TIRequiredSkills.visibility = View.INVISIBLE
            binding.tvskillllstext.visibility = View.VISIBLE
            binding.tvskillllstext.text = viewModel.skillCountText

        }

        try {
            binding.spinnerJobType.setText(viewModel.jobtype)
            binding.spinnerJobStatus.setText(viewModel.jobstatius)
            binding.spinnerEstimatedHour.setText(viewModel.estimatedhours)
        } catch (e: Exception) {
        }

        //  updateVisibility()
    }

    private fun networkCalls() {


        if (!global.isfrombackpress) {
            getJobSkills(token, searchjobSkillText)
        } else {
            val adapternew =
                CustomJobSkillsAdapter(
                    requireContext(),
                    global.jobskilllslist
                ) { jobskillList ->

                    if (jobskillList.size >= 1) {
                        binding.tvskillllstext.visibility = View.VISIBLE

                        // Remove items that are not selected
                        val iterator = arraylistskills.iterator()
                        while (iterator.hasNext()) {
                            val skill = iterator.next()
                            if (!jobskillList.any { it.skillId == skill.skillId }) {
                                iterator.remove()
                            }
                        }

                        // Add newly selected items
                        for (i in jobskillList) {
                            if (!arraylistskills.any { it.skillId == i.skillId }) {
                                val jobskill =
                                    JobSkill(i.skillId, i.name, "1", false, 0)
                                arraylistskills.add(jobskill)
                                com.example.envagemobileapplication.Utils.Global.jobskills =
                                    jobskill
                            }
                        }

                        binding.tvskillllstext.text =
                            "${arraylistskills.size} Skills Selected"
                        viewModel.skillCountText =
                            "${arraylistskills.size} Skills Selected"
                    } else {
                        binding.tvskillllstext.visibility = View.GONE
                    }

                }

            binding.spinnerRequiredSkills.setAdapter(adapternew)

            binding.spinnerRequiredSkills.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedPositions[position] = true
                    // Update visibility based on the selected items
                    updateVisibility()
                    /*   // Check the size of the selected items and update visibility accordingly
                       if (jobskillList.size >= 1) {

                           binding.tvskillllstext.visibility = View.VISIBLE
                           binding.tvskillllstext.text =
                               "${jobskillList.size} Skills Selected"


                       } else {
                           binding.tvskillllstext.visibility = View.GONE
                       }*/
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Do nothing if nothing is selected
                }
            })

        }
        getJobStatusResponse(token, searchviewtext)


    }

    private fun getJobStatusResponse(token: String, searchviewtext: String) {
        try {

            loader.show()
            ApiUtils.getAPIService(requireContext()).getJobStatus(
                token, searchviewtext
            )
                .enqueue(object : Callback<GetJobStatusResponse> {
                    override fun onResponse(
                        call: Call<GetJobStatusResponse>,
                        response: Response<GetJobStatusResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {


                            var jobStatuslist: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()!!.data.size) {
                                jobStatusIdslistGlobal.add(response.body()!!.data.get(i))
                                jobStatuslist.add(response.body()!!.data.get(i).statusName)
                            }
                            loader.hide()
                            val adapter = customadapter(
                                requireContext(),
                                R.layout.simple_spinner_item,
                                jobStatuslist
                            )
                            binding.spinnerJobStatus.setAdapter(adapter)
                        }
                    }

                    override fun onFailure(call: Call<GetJobStatusResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }

    private fun getJobSkills(token: String, searchjobSkillText: String) {
        try {

            loader.show()
            ApiUtils.getAPIService(requireContext()).getJobSkills(
                token, searchjobSkillText
            )
                .enqueue(object : Callback<JobSkillsResponse> {
                    override fun onResponse(
                        call: Call<JobSkillsResponse>,
                        response: Response<JobSkillsResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            //       var jobsSkillsList: ArrayList<String> = ArrayList()
                            var jobskillList: ArrayList<SkillsRequestModels> = ArrayList()
                            var skillModel: SkillsRequestModels
                            for (i in 0 until response.body()!!.data.size) {
                                skillModel = SkillsRequestModels(
                                    response.body()!!.data.get(i).skillId,
                                    response.body()!!.data.get(i).name, false
                                )
                                jobskillList.add(skillModel)
                            }
                            loader.hide()

                            global.jobskilllslist = jobskillList
                            val adapternew =
                                CustomJobSkillsAdapter(
                                    requireContext(),
                                    jobskillList
                                ) { jobskillList ->

                                    if (jobskillList.size >= 1) {
                                        binding.tvskillllstext.visibility = View.VISIBLE

                                        // Remove items that are not selected
                                        val iterator = arraylistskills.iterator()
                                        while (iterator.hasNext()) {
                                            val skill = iterator.next()
                                            if (!jobskillList.any { it.skillId == skill.skillId }) {
                                                iterator.remove()
                                            }
                                        }

                                        // Add newly selected items
                                        for (i in jobskillList) {
                                            if (!arraylistskills.any { it.skillId == i.skillId }) {
                                                val jobskill =
                                                    JobSkill(i.skillId, i.name, "1", false, 0)
                                                arraylistskills.add(jobskill)
                                                com.example.envagemobileapplication.Utils.Global.jobskills =
                                                    jobskill
                                            }
                                        }

                                        global.arraylistSelectedSkills = arraylistskills
                                        binding.tvskillllstext.text =
                                            "${arraylistskills.size} Skills Selected"
                                        viewModel.skillCountText =
                                            "${arraylistskills.size} Skills Selected"
                                    } else {
                                        binding.tvskillllstext.visibility = View.GONE
                                    }

                                }

                            arraylistskills = ArrayList()
                            binding.spinnerRequiredSkills.setOnItemSelectedListener(object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    viewModel.selectedPositions[position] = true
                                    // Update visibility based on the selected items
                                    updateVisibility()
                                    /*   // Check the size of the selected items and update visibility accordingly
                                       if (jobskillList.size >= 1) {

                                           binding.tvskillllstext.visibility = View.VISIBLE
                                           binding.tvskillllstext.text =
                                               "${jobskillList.size} Skills Selected"


                                       } else {
                                           binding.tvskillllstext.visibility = View.GONE
                                       }*/
                                }

                                override fun onNothingSelected(parentView: AdapterView<*>?) {
                                    // Do nothing if nothing is selected
                                }
                            })

                            binding.spinnerRequiredSkills.setAdapter(adapternew)
                        }
                    }

                    override fun onFailure(call: Call<JobSkillsResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }

    private fun initviews() {

        AddJobActivity.binding.ivTwo.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_active_two))
        val JobtypeHint = "Job Type *"
        val formattedHintJobtype = formatHintWithRedAsterisk(JobtypeHint)
        binding.TIJobtype.hint = formattedHintJobtype

        val jobStatusHint = "Job Status *"
        val formattedHintjobStatus = formatHintWithRedAsterisk(jobStatusHint)
        binding.TIJobStatus.hint = formattedHintjobStatus

        val weekDaysHint = "Weekdays *"
        val formattedweekDaysHint = formatHintWithRedAsterisk(weekDaysHint)
        binding.TIweekdays.hint = formattedweekDaysHint

        /*     val requiredSkillsHint = "Required Skills *"
             val formattedrequiredSkillsHint = formatHintWithRedAsterisk(requiredSkillsHint)
             binding.TIRequiredSkills.hint = formattedrequiredSkillsHint*/

        val workingDaysHint = "No. of Working Days *"
        val formattedworkingDays = formatHintWithRedAsterisk(workingDaysHint)
        binding.tvDaysCount.hint = formattedworkingDays

        val estimatedHours = "Estimated Hours (Day) *"
        val formattedestimatedHours = formatHintWithRedAsterisk(estimatedHours)
        binding.TIestimatedHours.hint = formattedestimatedHours

        val description = "Description *"
        val formatteddescription = formatHintWithRedAsterisk(description)
        binding.TIDescription.hint = formatteddescription

        setUpRtf()
        datePickerHelper = DatePickerHelper(requireContext())
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        jobtypeList = ArrayList()
        jobtypeList.add("Full Time")
        jobtypeList.add("Part Time")
        jobtypeList.add("Temporary")
        jobtypeList.add("Freelance")
        jobtypeList.add("Internship")
        jobtypeList.add("Contractor")
        jobtypeList.add("Consultancy")

        global.jobtypelist = jobtypeList
        val adapter = customadapter(
            requireContext(),
            R.layout.simple_spinner_item,
            jobtypeList
        )
        binding.spinnerJobType.setAdapter(adapter)

        val adapternew =
            CustomSpinnerAdapterForWeekdays(requireContext(), weekdays) { selectedWeekdays ->
                for (selectedWeekday in selectedWeekdays) {
                    if (selectedWeekdays.size >= 1) {
                        binding.tvWeekdaysconcaatinated.visibility = View.VISIBLE
                        val concatenatedNames = selectedWeekdays.joinToString(",") { it.name }
                        concatenatedNamesfinal = concatenatedNames
                        Log.i("concatednatedstring", concatenatedNames.toString())
                        binding.tvWeekdaysconcaatinated.setText(concatenatedNames.toString())
                        global.weekdaysConcatinated =
                            concatenatedNamesfinal
                        viewModel.weekdaysConcatinated = concatenatedNamesfinal
                        binding.TIweekdays.visibility = View.INVISIBLE

                    } else {
                        binding.tvWeekdaysconcaatinated.visibility = View.GONE
                        binding.TIweekdays.visibility = View.VISIBLE

                    }

                }

                binding.spinnerWeekdays.setOnItemSelectedListener(object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                    ) {

                        // Check the size of the selected items and update visibility accordingly
                        if (selectedWeekdays.size >= 1) {

                            binding.tvWeekdaysconcaatinated.visibility = View.VISIBLE

                        } else {
                            binding.tvWeekdaysconcaatinated.visibility = View.GONE
                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // Do nothing if nothing is selected
                    }
                })

                if (selectedWeekdays.size == 0) {
                    binding.TIweekdays.visibility = View.VISIBLE
                    binding.tvWeekdaysconcaatinated.visibility = View.GONE
                } else {
                    binding.TIweekdays.visibility = View.INVISIBLE
                    binding.tvWeekdaysconcaatinated.visibility = View.VISIBLE
                }
                binding.tvDaysCount.setText(selectedWeekdays.size.toString())
            }

        binding.spinnerWeekdays.setAdapter(adapternew)

        val workingHoursAdapter = customadapter(
            requireContext(),
            R.layout.simple_spinner_item,
            workingHours
        )
        binding.spinnerEstimatedHour.setAdapter(workingHoursAdapter)

        binding.ccStartdate.isEnabled = false
        binding.ccEnddate.isEnabled = false


        var currenyList: ArrayList<String> = ArrayList()
        currenyList.add("USD")

        val currencyAdapter = customadapter(
            requireContext(),
            R.layout.simple_spinner_item,
            currenyList
        )
        binding.spinnerCurrency.setAdapter(currencyAdapter)
    }

    private fun clicklisteners() {

        binding.btnback.setOnClickListener {

            requireActivity().onBackPressed()
        }

        binding.etClientDescription!!.setOnTextChangeListener { text ->
            binding.TIDescription.error = null
            if (text.contains("<")) {

                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }


        }

        binding.spinnerJobStatus.setOnItemClickListener { _, _, position, _ ->
            binding.TIJobStatus.error = null
        }
        binding.spinnerWeekdays.setOnItemClickListener { _, _, position, _ ->
            binding.TIworkingdays.error = null
            binding.TIweekdays.error = null
        }


        binding.spinnerEstimatedHour.setOnItemClickListener { _, _, position, _ ->
            binding.TIestimatedHours.error = null
        }


        binding.ccStartdate.setOnClickListener {
            datePickerHelper.attachDatePicker(binding.ccStartdate, binding.tvStartDate)
        }

        binding.ccEnddate.setOnClickListener {
            datePickerHelper.attachDatePicker(
                binding.ccEnddate,
                binding.tvEndDate
            )
        }


        binding.spinnerJobType.setOnItemClickListener { _, _, position, _ ->
            // Update the selected position in the adapter
            binding.TIJobtype.error = null
            global.index = position
            val selectedItemText = binding.spinnerJobType.text.toString()
            if (selectedItemText == "Full Time" || selectedItemText == "Part Time" || selectedItemText == "Freelance") {
                binding.ccStartdate.isEnabled = true
                viewModel.isStartDateFieldEnabled = true
                binding.ccEnddate.isEnabled = false
                viewModel.isEndDateFieldEnabled = false
                binding.ccStartdate.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
            } else {
                binding.ccStartdate.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
                binding.ccEnddate.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
                binding.ccStartdate.isEnabled = true
                viewModel.isStartDateFieldEnabled = true
                binding.ccEnddate.isEnabled = true
                viewModel.isEndDateFieldEnabled = true
            }
        }
        binding.TIcurrency.setOnClickListener {
            if (binding.spinnerCurrency.isPopupShowing) {
                binding.spinnerCurrency.dismissDropDown()
            } else {
                binding.spinnerCurrency.showDropDown()
            }
        }
        binding.spinnerCurrency.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerCurrency.isPopupShowing) {
                binding.spinnerCurrency.dismissDropDown()
            } else {
                binding.spinnerCurrency.showDropDown()
            }
            false
        })

        binding.TIcurrency.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerCurrency.isPopupShowing) {
                binding.spinnerCurrency.dismissDropDown()
            } else {
                binding.spinnerCurrency.showDropDown()
            }
            false
        })

        binding.spinnerJobStatus.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobStatus.isPopupShowing) {
                binding.spinnerJobStatus.dismissDropDown()
            } else {
                binding.spinnerJobStatus.showDropDown()
            }
            false
        })

        binding.TIJobStatus.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobStatus.isPopupShowing) {
                binding.spinnerJobStatus.dismissDropDown()
            } else {
                binding.spinnerJobStatus.showDropDown()
            }
            false
        })

        binding.ivPlus.setOnClickListener {
            counter++
            binding.tvHeadCount.setText(counter.toString());
        }
        binding.ivMinus.setOnClickListener {
            if (counter > 1) {
                counter--
                binding.tvHeadCount.setText(counter.toString())
            }
        }

        binding.spinnerJobType.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobType.isPopupShowing) {
                binding.spinnerJobType.dismissDropDown()
            } else {
                binding.spinnerJobType.showDropDown()
            }
            false
        })

        binding.TIJobtype.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobType.isPopupShowing) {
                binding.spinnerJobType.dismissDropDown()
            } else {
                binding.spinnerJobType.showDropDown()
            }
            false
        })



        binding.spinnerWeekdays.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerWeekdays.isPopupShowing) {
                binding.spinnerWeekdays.dismissDropDown()
            } else {
                binding.spinnerWeekdays.showDropDown()
            }
            false
        })
        binding.spinnerEstimatedHour.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerEstimatedHour.isPopupShowing) {
                binding.spinnerEstimatedHour.dismissDropDown()
            } else {
                binding.spinnerEstimatedHour.showDropDown()
            }
            false
        })

        binding.TIestimatedHours.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerEstimatedHour.isPopupShowing) {
                binding.spinnerEstimatedHour.dismissDropDown()
            } else {
                binding.spinnerEstimatedHour.showDropDown()
            }
            false
        })

        binding.spinnerRequiredSkills.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerRequiredSkills.isPopupShowing) {
                binding.spinnerRequiredSkills.dismissDropDown()
            } else {
                binding.spinnerRequiredSkills.showDropDown()
                (binding.spinnerRequiredSkills.adapter as? CustomJobSkillsAdapter)?.setUserInteracted(
                    true
                )
            }
            false
        })

        binding.tvskillllstext.setOnClickListener {
            if (binding.spinnerRequiredSkills.isPopupShowing) {
                binding.spinnerRequiredSkills.dismissDropDown()
            } else {
                binding.spinnerRequiredSkills.showDropDown()
            }
        }

        binding.tvWeekdaysconcaatinated.setOnClickListener {
            if (binding.spinnerWeekdays.isPopupShowing) {
                binding.spinnerWeekdays.dismissDropDown()
            } else {
                binding.spinnerWeekdays.showDropDown()
            }
        }



        binding.btnnext.setOnClickListener {
            global.isfrombackpress = false


            viewModel.arraylistSelectedSkills = global.arraylistSelectedSkills
            val jobSkillsList = global.arraylistSelectedSkills
            var headCount = binding.tvHeadCount.text.toString()
            if (headCount.equals("")) {
                headCount = "1"
            }
            global.headcount = headCount
            viewModel.headcount = headCount
            var jobtype = binding.spinnerJobType.text.toString()
            var startdate = binding.tvStartDate.text.toString()

            global.jobtype = jobtype
            viewModel.jobtype = jobtype
            global.jobstatius = binding.spinnerJobStatus.text.toString()
            viewModel.estimatedhours = binding.spinnerEstimatedHour.text.toString()
            global.startdate = startdate
            viewModel.startdate = startdate

            var enddate = binding.tvEndDate.text.toString()
            if (enddate == "End Date") {
                enddate = startdate
            }
            global.enddate = enddate
            viewModel.endDate = enddate


            var currency = binding.spinnerCurrency.text.toString()
            viewModel.currency = currency
            var minimumsalary = 0
            var maximumsalary = 0
            var noOfWorkingDays = binding.tvDaysCount.text.toString()

            viewModel.noofWorkingDays = noOfWorkingDays
            var estimatedhours = binding.spinnerEstimatedHour.text.toString()
            var workingdays = binding.spinnerWeekdays.text
            var jobStatus = binding.spinnerJobStatus.text.toString()
            var jobSkills = jobSkillsList
            var jobSkillsID = 0
            var jobstatusID = 0
            for (i in 0 until jobStatusIdslistGlobal.size) {
                if (jobStatusIdslistGlobal.get(i).statusName.equals(jobStatus)) {
                    jobstatusID = jobStatusIdslistGlobal.get(i).jobStatusId
                }

            }

            if (!descriptiontext.isNullOrBlank()) {
                val htmlContent = descriptiontext
                val mediaType = "text/html".toMediaTypeOrNull()
                val descriptionBody = RequestBody.create(mediaType, htmlContent)
                val descriptionPart =
                    MultipartBody.Part.createFormData(
                        "jopbDescription",
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
                        "jopbDescription",
                        "description.html",
                        descriptionBody
                    )
                description = descriptionPart
            }

            global.descriptiontext = descriptiontext

            if (!jobtype.isNullOrEmpty() && !jobStatus.isNullOrEmpty() && !weekdays.isNullOrEmpty()
                && !noOfWorkingDays.isNullOrEmpty()
                && !estimatedhours.isNullOrEmpty() && !descriptiontext.isNullOrEmpty() && !descriptiontext.equals(
                    "<p></p>"
                )
            ) {

                var addjobDetailRM = AddJobDetailsReqModel(
                    description = description,
                    headcount = headCount.toInt(),
                    jobType = jobtype,
                    startDate = startdate,
                    endDate = enddate,
                    currency = currency,
                    maximumSalary = maximumsalary,
                    minimumSalary = minimumsalary,
                    workingDaysNo = noOfWorkingDays.toInt(),
                    estimatedHours = estimatedhours.toInt(),
                    workingDays = viewModel.weekdaysConcatinated,
                    jobStatusId = jobstatusID,
                    jobSkills = jobSkillsList
                )
                com.example.envagemobileapplication.Utils.Global.addJobDetailModel = addjobDetailRM
                AddJobActivity.binding.ivTwo.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_job_detail_done))
                replaceFragment(AddjobAdressDetailF())

            } else {


                if (descriptiontext.isNullOrEmpty() || descriptiontext.equals("<p></p>")) {
                    binding.TIDescription.error = "Description is Required."
                    binding.TIDescription.errorIconDrawable = null// Set the error message
                    binding.TIDescription.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (jobtype.isNullOrEmpty()) {
                    binding.TIJobtype.error = "Job Type is Required."
                    binding.TIJobtype.errorIconDrawable = null// Set the error message
                    binding.TIJobtype.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (jobStatus.isNullOrEmpty()) {
                    binding.TIJobStatus.error = "Job Status is Required."
                    binding.TIJobStatus.errorIconDrawable = null// Set the error message
                    binding.TIJobStatus.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }

                if (weekdays.isNullOrEmpty()) {
                    binding.TIweekdays.error = "Weekdays is Required."
                    binding.TIweekdays.errorIconDrawable = null// Set the error message
                    binding.TIweekdays.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (noOfWorkingDays.isNullOrEmpty() || noOfWorkingDays.equals("")) {
                    binding.TIweekdays.error = "Weekdays is Required."
                    binding.TIweekdays.errorIconDrawable = null// Set the error message
                    binding.TIweekdays.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                    binding.TIworkingdays.error = "No.of Working days is Required."
                    binding.TIworkingdays.errorIconDrawable = null// Set the error message
                    binding.TIworkingdays.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
                if (estimatedhours.isNullOrEmpty()) {
                    binding.TIestimatedHours.error = "Estimated Hours is Required."
                    binding.TIestimatedHours.errorIconDrawable = null// Set the error message
                    binding.TIestimatedHours.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }
        }

        binding.btnback.setOnClickListener {
            global.isfrombackpress = true
            val jobSkillsList = arraylistskills
            var headCount = binding.tvHeadCount.text.toString()
            if (headCount.equals("")) {
                headCount = "1"
            }
            global.headcount = headCount
            viewModel.headcount = headCount
            var startdate = binding.tvStartDate.text.toString()

            viewModel.jobtype = binding.spinnerJobType.text.toString()
            global.jobstatius = binding.spinnerJobStatus.text.toString()
            viewModel.estimatedhours = binding.spinnerEstimatedHour.text.toString()
            global.startdate = startdate
            viewModel.startdate = startdate

            var enddate = binding.tvEndDate.text.toString()
            if (enddate == "End Date") {
                enddate = startdate
            }
            global.enddate = enddate
            viewModel.endDate = enddate


            var currency = binding.spinnerCurrency.text.toString()
            viewModel.currency = currency

            var noOfWorkingDays = binding.tvDaysCount.text.toString()

            viewModel.noofWorkingDays = noOfWorkingDays

            global.descriptiontext = descriptiontext
            requireActivity().onBackPressed()
        }
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
            .setOnClickListener {
                mEditor!!.setAlignRight()
            }
        requireActivity().findViewById<View>(com.example.envagemobileapplication.R.id.action_align_right)
            .setOnClickListener { mEditor!!.setAlignCenter() }

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

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.envagemobileapplication.R.id.cc_addjob_fragments, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onStop() {
        global.isfrombackpress = true
        val jobSkillsList = arraylistskills
        var headCount = binding.tvHeadCount.text.toString()
        if (headCount.equals("")) {
            headCount = "1"
        }
        global.headcount = headCount
        viewModel.headcount = headCount

        var startdate = binding.tvStartDate.text.toString()

        viewModel.jobtype = binding.spinnerJobType.text.toString()
        viewModel.jobstatius = binding.spinnerJobStatus.text.toString()
        viewModel.estimatedhours = binding.spinnerEstimatedHour.text.toString()
        global.startdate = startdate
        viewModel.startdate = startdate

        var enddate = binding.tvEndDate.text.toString()
        if (enddate == "End Date") {
            enddate = startdate
        }
        global.enddate = enddate
        viewModel.endDate = enddate


        var currency = binding.spinnerCurrency.text.toString()
        viewModel.currency = currency

        var noOfWorkingDays = binding.tvDaysCount.text.toString()

        viewModel.noofWorkingDays = noOfWorkingDays

        global.descriptiontext = descriptiontext
        super.onStop()
    }

    override fun onPause() {
        global.isfrombackpress = true
        val jobSkillsList = arraylistskills
        var headCount = binding.tvHeadCount.text.toString()
        if (headCount.equals("")) {
            headCount = "1"
        }
        global.headcount = headCount
        viewModel.headcount = headCount

        var startdate = binding.tvStartDate.text.toString()

        viewModel.jobtype = binding.spinnerJobType.text.toString()

        viewModel.jobstatius = binding.spinnerJobStatus.text.toString()
        viewModel.estimatedhours = binding.spinnerEstimatedHour.text.toString()
        global.startdate = startdate
        viewModel.startdate = startdate
        var enddate = binding.tvEndDate.text.toString()
        if (enddate == "End Date") {
            enddate = startdate
        }
        global.enddate = enddate
        viewModel.endDate = enddate

        var currency = binding.spinnerCurrency.text.toString()
        viewModel.currency = currency

        var noOfWorkingDays = binding.tvDaysCount.text.toString()
        global.noofWorkingDays = noOfWorkingDays
        viewModel.noofWorkingDays = noOfWorkingDays

        global.descriptiontext = descriptiontext
        super.onPause()
    }
}