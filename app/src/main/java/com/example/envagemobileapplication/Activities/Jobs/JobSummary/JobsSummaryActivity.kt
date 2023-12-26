package com.example.envagemobileapplication.Activities.Jobs.JobSummary

import BaseActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.*
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.ActivityJobsSummaryBinding

class JobsSummaryActivity : BaseActivity() {
    val viewModel: JobSummaryViewModel by viewModels()
    lateinit var binding: ActivityJobsSummaryBinding
    private var selectedItem: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobsSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        clicklisteners()
        networkCalls()
        setupHorizontalScrollView()
    }

    private fun clicklisteners() {

        binding.ivcCross.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {

        try {
            binding.tvClientnameHeading.setText(com.example.envagemobileapplication.Utils.Global.jobtitle)

        } catch (e: Exception) {

        }
    }

    private fun setupHorizontalScrollView() {
        val itemList = listOf(
            Pair("Job Summary", R.drawable.ic_profile_summary),
            Pair("Candidates", R.drawable.ic_candidatesvg),
            Pair("Attachments", R.drawable.ic_profile_attachments),
            Pair("Notes", R.drawable.ic_profile_notes),
            Pair("Tasks", R.drawable.ic_profile_tasks),
        )
        for ((itemText, drawableResId) in itemList) {
            val itemView = layoutInflater.inflate(R.layout.horzontal_scrolview_item, null)
            val imageView = itemView.findViewById<ImageView>(R.id.iv_drawable)
            val textView = itemView.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Job Summary") {
                // Set the background for "Profile summary" by default
                itemView.setBackgroundResource(R.drawable.ic_bg_underline)
                selectedItem = itemView
            }
            // Set the drawable resource for the ImageView
            imageView.setImageResource(drawableResId)
            // Set the text for the TextView
            textView.text = itemText
            // Set a click listener for each item
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    // Reset the background of the previously selected item
                    selectedItem?.setBackgroundResource(R.drawable.btn_white_radius)

                    itemView.setBackgroundResource(R.drawable.ic_bg_underline)
                    selectedItem = itemView

                    val clickedText = itemText

                    val left = itemView.left

                    binding.horizontalScrollView.scrollTo(left, 0)

                    itemView.requestFocus()

                    if (clickedText == "Candidates") {
                        replaceFragment(JobsSummaryCandidatesF())
                    } else if (clickedText.equals("Attachments")) {
                        replaceFragment(JobsSummaryAttachmentsF())
                    } else if (clickedText.equals("Notes")) {
                        replaceFragment(JobsSummaryNotesF())
                    } else if (clickedText.equals("Tasks")) {
                        replaceFragment(JobsSummaryTasksF())
                    } else if (clickedText.equals("Job Summary")) {
                        replaceFragment(JobsSummaryFragment())
                    }
                }
            })

            binding.linearlist.addView(itemView)
        }
    }

    private fun networkCalls() {

    }

    fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_job_summary, fragment)
            transaction.commit()
        } catch (e: Exception) {

        }

    }
}