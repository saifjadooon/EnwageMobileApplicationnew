package com.example.envagemobileapplication.Activities.Jobs.AddJob

import BaseActivity
import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Jobs.AddJobFragments.AddjobBasicDetailF
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.ActivityAddJobBinding

class AddJobActivity : BaseActivity() {

    companion object {
        lateinit var binding: ActivityAddJobBinding
    }

    val viewModel: AddJobsSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)

        initviews()
        clicklisteners()

        setContentView(binding.root)
    }

    private fun clicklisteners() {
        binding.ivCross2.setOnClickListener {
            showDialog()
        }
    }

    private fun initviews() {
        replaceFragment(AddjobBasicDetailF())
    }


    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cc_addjob_fragments, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            finish()// Dismiss the dialog
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


}