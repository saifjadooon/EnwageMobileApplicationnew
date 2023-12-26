package com.example.envagemobileapplication.Activities.Candidates

import BaseActivity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Candidates.CandidateFragments.CandidateSummaryF
import com.example.envagemobileapplication.Activities.Candidates.CandidateFragments.ResumeF
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.ActivityCandidateProfileSummaryBinding

class CandidateProfileSummary : BaseActivity() {
    private val MULTIPLE_PERMISSIONS = 10
    lateinit var binding: ActivityCandidateProfileSummaryBinding
    val viewModel: CandidatesProfileSumViewModel by viewModels()
    private var selectedItem: View? = null

    lateinit var loader: Loader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCandidateProfileSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observers()
        clickListeners()

    }

    private fun clickListeners() {
        binding.ivBack.setOnClickListener{
          finish()
        }
    }

    private fun observers() {

    }

    private fun initViews() {

        loader = Loader(this)
        if (!Constants.candidateName.isNullOrEmpty()) {
            binding.tvName.text = Constants.candidateName
        }


        binding.tvName.setOnLongClickListener {
            val toast = Toast.makeText(
                this,
                Constants.candidateName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }



        val itemList = listOf(
            Pair("Candidate summary", R.drawable.ic_profile_summary),
            Pair("Resumes", R.drawable.ic_resume),
            Pair("Interviews", R.drawable.iconinterviews),
            Pair("Attachments", R.drawable.ic_profile_attachments),
            Pair("Notes", R.drawable.ic_profile_notes),
            Pair("Tasks", R.drawable.ic_profile_tasks),
            Pair("Assessments", R.drawable.ic_assessments),
            Pair("Inbox", R.drawable.ic_messages),

        )


        for ((itemText, drawableResId) in itemList) {
            val itemView = layoutInflater.inflate(R.layout.horzontal_scrolview_item, null)
            val imageView = itemView.findViewById<ImageView>(R.id.iv_drawable)
            val textView = itemView.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Candidate summary") {
                // Set the background for "Profile summary" by default
                itemView.setBackgroundResource(R.drawable.gray_bg)
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

                    // Highlight the selected item
                    itemView.setBackgroundResource(R.drawable.gray_bg)
                    selectedItem = itemView

                    val clickedText = itemText
                    // Get the left position of the clicked item relative to its parent
                    val left = itemView.left

                    // Scroll the ScrollView to bring the clicked item into view
                    binding.horizontalScrollView.scrollTo(left, 0)

                    // Give focus to the clicked item's view
                    itemView.requestFocus()

                    /* val navController: NavController = Navigation.findNavController(
                         this@ClientSummaryActivity,
                         R.id.nav_client_summary
                     )*/
                    //navController.navigateUp()
                    if (clickedText == "Candidate summary") {
                        replaceFragment(CandidateSummaryF())
                    } else if (clickedText.equals("Resumes")) {
                        replaceFragment(ResumeF())
                    }
                }
            })

            binding.linearlist.addView(itemView)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.candidate_host_fragment, fragment)
        transaction.commit()
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
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    showNoPermissionDialog()
                    //    checkPermissions()

                }
                return
            }
        }
    }

    private fun showNoPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
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

}