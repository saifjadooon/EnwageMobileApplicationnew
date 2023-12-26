package com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter

import BaseActivity
import android.app.AlertDialog
import android.os.Bundle
import com.example.envagemobileapplication.databinding.ActivitySendOfferLetterBinding

class SendOfferLetterActivity : BaseActivity() {
    lateinit var binding: ActivitySendOfferLetterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySendOfferLetterBinding.inflate(layoutInflater)
        binding.ivcCross.setOnClickListener { showDialog() }
        setContentView(binding.root)

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
            this.finish()// Dismiss the dialog
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