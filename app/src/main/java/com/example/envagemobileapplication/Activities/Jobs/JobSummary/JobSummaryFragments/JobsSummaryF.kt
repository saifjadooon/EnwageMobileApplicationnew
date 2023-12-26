package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.BillingDetailJobSummaryAdapter
import com.example.envagemobileapplication.Adapters.JobSkillsAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.JobSkillsSummary
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentJobsSummaryBinding
import com.squareup.picasso.Picasso
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

class JobsSummaryFragment : Fragment() {
    data class KeyValueData(val key: String, val value: String)

    private var weekdaysList: List<String> = ArrayList()
    private var isExpanded = false
    val viewModel: JobSummaryViewModel by activityViewModels()
    lateinit var binding: FragmentJobsSummaryBinding
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var adapter: BillingDetailJobSummaryAdapter
    lateinit var jobSkillsAdapter: JobSkillsAdapter
    val billingDataList: ArrayList<KeyValueData> = ArrayList()
    val skillsList: ArrayList<JobSkillsSummary> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentJobsSummaryBinding.inflate(inflater, container, false)
        clicklisteners()
        initviews()
        observers()
        networkCalls()
        binding.ivShowmore.setOnClickListener {
            isExpanded = !isExpanded
            updateRecyclerViewHeight()
        }
        return binding.root

    }

    private fun networkCalls() {
        var jobGuid = com.example.envagemobileapplication.Utils.Global.jobGuid
        //  var jobGuid = "c37b0c1b-8515-4e32-b026-a2db13343b20"
        viewModel.getJobHeaderSummary(requireActivity(), token, jobGuid)
    }

    private fun observers() {
        viewModel.LDgetJobHeaderSummary.observe(requireActivity()) {
            global.jobHeaderSummary = it
            try {
                billingDataList.add(
                    KeyValueData(
                        "Markup Percentage",
                        it.data.billingDetails.markup.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Min. Pay Rate",
                        it.data.billingDetails.minPayRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Min. Bill Rate",
                        it.data.billingDetails.minBillRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Max. Pay Rate",
                        it.data.billingDetails.maxPayRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Max. Bill Rate",
                        it.data.billingDetails.maxBillRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Target Pay Rate",
                        it.data.billingDetails.targetPayRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Target Bill Rate",
                        it.data.billingDetails.targetBillRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Overtime Type",
                        it.data.billingDetails.overtimeType.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Overtime Multiplier",
                        it.data.billingDetails.overtimeMultiplier.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Overtime Markup Percentage",
                        it.data.billingDetails.overtimeMarkup.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Overtime Pay Rate",
                        it.data.billingDetails.overtimePayRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Overtime Bill Rate",
                        it.data.billingDetails.overtimeBillRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Double-time Type",
                        it.data.billingDetails.doubletimeType.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Double-Time Multiplier",
                        it.data.billingDetails.doubletimeMultiplier.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Double-Time Markup Percentage",
                        it.data.billingDetails.doubletimeMarkup.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Double-Time Pay Rate",
                        it.data.billingDetails.doubletimePayRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Double-Time Bill Rate",
                        it.data.billingDetails.doubletimeBillRate.toString()
                    )
                )
                billingDataList.add(
                    KeyValueData(
                        "Frequency",
                        it.data.billingDetails.frequency.toString()
                    )
                )
            } catch (e: Exception) {
            }
            try {
                val weekdaysString = it.data.jobInfo.workingDays

                if (weekdaysList.isEmpty()) {
                    weekdaysList = weekdaysString.split(",")
                    setupHorizontalScrollView(weekdaysList)
                }


            } catch (e: Exception) {
            }
            try {
                for (i in 0 until it.data.jobSkillsSummary.size) {
                    skillsList.add(it.data.jobSkillsSummary.get(i))
                }
                if (skillsList.size >= 1) {
                    binding.tvNoskills.visibility = View.INVISIBLE
                    setupjobsSkillsAdapter(skillsList)
                } else {
                    binding.tvNoskills.visibility = View.VISIBLE
                }

                setupBillingDetailAdapter(billingDataList)
            } catch (e: Exception) {
            }
            try {
                val hexColorCode = it.data.jobStatus.colorCode
                binding.tvJobstatus.setTextColor(Color.parseColor(hexColorCode))
                parseBackgroundColor(binding.tvJobstatus, hexColorCode)

                val jobtypehexcolor = "#AF6606"
                binding.tvjobtype.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(binding.tvjobtype, jobtypehexcolor)

                val blackcolor = "#000000"
                parseBackgroundColor(binding.tvjobNature, blackcolor)
            } catch (e: Exception) {
            }

            try {
                if (it.data.jobInfo.positionName != null) {
                    binding.tvJobName.setText(it.data.jobInfo.positionName)
                } else {
                    binding.tvJobName.setText("Not Provided")
                }
                if (it.data.jobInfo.clientName != null) {
                    binding.clientname.setText(it.data.jobInfo.clientName)
                } else {
                    binding.clientname.setText("Not Provided")
                }
                if (it.data.jobInfo.industryName != null) {
                    binding.tvIndustry.setText(it.data.jobInfo.industryName.toString())
                } else {
                    binding.tvIndustry.setText("Not Provided")
                }

                if (it.data.jobStatus.statusName != null) {
                    binding.tvJobstatus.setText(it.data.jobStatus.statusName)
                } else {
                    binding.tvJobstatus.setText("Not Provided")
                }


                if (it.data.jobInfo.jobType != null) {
                    binding.tvjobtype.setText(it.data.jobInfo.jobType)
                } else {
                    binding.tvjobtype.setText("Not Provided")
                }

                if (it.data.jobInfo.jobNature != null) {
                    binding.tvjobNature.setText(it.data.jobInfo.jobNature)
                } else {
                    binding.tvjobNature.setText("Not Provided")
                }

                if (it.data.jobInfo.payGroupName != null) {
                    binding.payGroup.setText("Pay Group:" + it.data.jobInfo.payGroupName.toString())
                } else {
                    binding.payGroup.setText("Pay Group:Not Provided")
                }

                if (it.data.jobInfo.headcount != null) {
                    binding.tvHeadCount.setText("Headcount:" + it.data.jobInfo.headcount.toString())
                } else {
                    binding.tvHeadCount.setText("Headcount:Not Provided")
                }

                if (it.data.jobInfo.startDate != null) {
                    val formattedDate = formatDate(it.data.jobInfo.startDate)
                    binding.tvStartDate.setText("Start Date:" + formattedDate)
                } else {
                    binding.tvStartDate.setText("Start Date:Not Provided")
                }

                if (it.data.jobInfo.workingDaysNo != null) {
                    if (it.data.jobInfo.workingDaysNo.equals(1)) {
                        binding.tvTotalDays.setText(it.data.jobInfo.workingDaysNo.toString() + " Day")
                    } else {
                        binding.tvTotalDays.setText(it.data.jobInfo.workingDaysNo.toString() + " Days")

                    }
                } else {
                    binding.tvTotalDays.setText("Days:Not Provided")
                }




                if (it.data.jobInfo.address1 != null && it.data.jobInfo.address2 != null) {
                    binding.tvHomeAdress.setText(it.data.jobInfo.address1 + " " + it.data.jobInfo.address2)
                } else if (it.data.jobInfo.address1 != null && it.data.jobInfo.address2 == null) {
                    binding.tvHomeAdress.setText(it.data.jobInfo.address1)
                } else if (it.data.jobInfo.address1 == null && it.data.jobInfo.address2 != null) {
                    binding.tvHomeAdress.setText(it.data.jobInfo.address2)
                } else {
                    binding.tvHomeAdress.setText("Not Provided")
                }

                if (it.data.jobInfo.zipcode != null) {
                    binding.postalcode.setText(it.data.jobInfo.zipcode.toString())
                } else {
                    binding.postalcode.setText("Not Provided")
                }
                if ((it.data.jobInfo.city != null)) {
                    binding.country.setText(it.data.jobInfo.city.toString())
                } else {
                    binding.country.setText("Not Provided")
                }

                if ((it.data.jobInfo.state != null)) {
                    binding.city.setText(it.data.jobInfo.state.toString())
                } else {
                    binding.city.setText("Not Provided")
                }

                if ((it.data.jobInfo.location != null)) {

                    binding.location.setText(it.data.jobInfo.location.toString())
                } else {
                    binding.location.setText("Not Provided")
                }



                if (it.data.jobInfo.clientProfilePicture != null) {
                    Picasso.get().load(it.data.jobInfo.clientProfilePicture)
                        .placeholder(R.drawable.upload_pic_bg).into(binding.ivProfilePic)

                } else {
                    Picasso.get().load(
                        R.drawable.upload_pic_bg
                    ).into(binding.ivProfilePic)
                }

                if (!it.data?.jobInfo?.jopbDescription.isNullOrEmpty()) {
                    var filename = it.data?.jobInfo?.jopbDescription
                    var baseurlnew =
                        "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                    try {
                        loadJobDescriptionContent(baseurlnew)
                    } catch (e: Exception) {
                    }

                } else {
                    binding.tvDescription.text = "No Description to Show"
                }


            } catch (e: Exception) {
            }
        }
    }


    private fun parseBackgroundColor(tvJobstatus: TextView, hexColorCode: String) {
        val currentTextColor = Color.parseColor(hexColorCode)

        // Adjust the alpha component
        val adjustedAlpha = (Color.alpha(currentTextColor) * 0.1).toInt()

        // Create the new color with adjusted alpha
        val adjustedColor = Color.argb(
            adjustedAlpha,
            Color.red(currentTextColor),
            Color.green(currentTextColor),
            Color.blue(currentTextColor)
        )

        val cornerRadius = 20f // You can adjust this value based on your preference

        // Create a GradientDrawable
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = cornerRadius
        gradientDrawable.setColor(adjustedColor)

        // Set the background drawable with corner radius to the TextView
        tvJobstatus.background = gradientDrawable
    }

    private fun setupjobsSkillsAdapter(skillsList: ArrayList<JobSkillsSummary>) {
        try {
            binding.rvSkills.setHasFixedSize(true)
            jobSkillsAdapter = JobSkillsAdapter(
                requireContext(),
                skillsList
            )
            binding.rvSkills.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rvSkills.adapter = jobSkillsAdapter
        } catch (e: Exception) {
        }
    }

    private fun initviews() {
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        loader = Loader(requireContext())
    }

    private fun clicklisteners() {
        binding.clientname.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                global.jobHeaderSummary!!.data.jobInfo.clientName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        binding.tvJobName.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                global.jobHeaderSummary!!.data.jobInfo.positionName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        binding.tvHomeAdress.setOnLongClickListener {
            if (global.jobHeaderSummary!!.data.jobInfo.address1 != null && global.jobHeaderSummary!!.data.jobInfo.address2 != null) {
                val toast = Toast.makeText(
                    context,
                    global.jobHeaderSummary!!.data.jobInfo.address1 + " " + global.jobHeaderSummary!!.data.jobInfo.address2,
                    Toast.LENGTH_LONG
                )

                toast.show()

            } else if (global.jobHeaderSummary!!.data.jobInfo.address1 != null && global.jobHeaderSummary!!.data.jobInfo.address2 == null) {
                val toast = Toast.makeText(
                    context,
                    global.jobHeaderSummary!!.data.jobInfo.address1,
                    Toast.LENGTH_LONG
                )

                toast.show()

            } else if (global.jobHeaderSummary!!.data.jobInfo.address1 == null && global.jobHeaderSummary!!.data.jobInfo.address2 != null) {
                val toast = Toast.makeText(
                    context,
                    global.jobHeaderSummary!!.data.jobInfo.address2,
                    Toast.LENGTH_LONG
                )
                toast.show()

            }

            true
        }

        binding.location.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                global.jobHeaderSummary!!.data.jobInfo.location.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

    }

    private fun setupBillingDetailAdapter(billdata: ArrayList<KeyValueData>) {

        try {
            binding.rvBillingInformation.setHasFixedSize(true)

            adapter = BillingDetailJobSummaryAdapter(
                requireContext(),
                billdata
            )


            binding.rvBillingInformation.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.rvBillingInformation.adapter = adapter
        } catch (e: Exception) {
        }

    }


    private fun setupHorizontalScrollView(weekdaysList: List<String>) {
        for (itemText in weekdaysList) {
            val itemView = layoutInflater.inflate(R.layout.weekdays_item, null)
            val textView = itemView.findViewById<TextView>(R.id.tvWeekday)
            textView.text = itemText
            binding.linearlist.addView(itemView)
        }
    }

    private fun updateRecyclerViewHeight() {
        val newHeight = if (isExpanded) {

            binding.ivShowmore.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_showless))
            // Set a larger height when "show more" is clicked
            resources.getDimensionPixelSize(R.dimen.expanded_height)
        } else {
            binding.ivShowmore.setImageDrawable(requireContext().getDrawable(com.example.envagemobileapplication.R.drawable.ic_showmore))
            // Set the original height when "show less" is clicked
            resources.getDimensionPixelSize(R.dimen.original_height)
        }

        binding.rvBillingInformation.layoutParams.height = newHeight
        binding.rvBillingInformation.requestLayout()
    }
/*
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
    }*/

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

}
