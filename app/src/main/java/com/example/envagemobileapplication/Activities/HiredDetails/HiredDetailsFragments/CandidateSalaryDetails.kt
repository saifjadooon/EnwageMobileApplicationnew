package com.example.envagemobileapplication.Activities.HiredDetails.HiredDetailsFragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Models.RequestModels.UpdateCandidateStatusRequestModel
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.HireCandidateViewModel
import com.example.envagemobileapplication.databinding.FragmentCandidateSalaryDetailsBinding
import kotlin.math.round


class CandidateSalaryDetails : Fragment() {
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    val viewModel: HireCandidateViewModel by activityViewModels()
    var payrate = 0.0
    var otPayrate = 0.0
    var dtPayrate = 0.0
    var otmarkupPercentage = 0.0
    var dtmarkupPercentage = 0.0
    var otBillRate = 0.0
    var dtBillRate = 0.0
    lateinit var binding: FragmentCandidateSalaryDetailsBinding
    var overtimeMultiplier: Float = 1.0f
    var doubletimeMultiplier: Float = 1.0f


    var global = com.example.envagemobileapplication.Utils.Global

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentCandidateSalaryDetailsBinding.inflate(inflater, container, false)
        clicklisteners()
        initviews()
        observers()
        return binding.root
    }

    private fun observers() {
        viewModel.LDupdateCandidate.observe(requireActivity())
        {
            loader.hide()
            if (it.data != null) {

                try {
                    Toast.makeText(
                        requireContext(),
                        "Candidate status updated Successfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val delayMillis = 3000L // Delay between transitions in milliseconds
                    val handler = Handler()
                    handler.postDelayed({
                        // requireActivity().finish()
                    }, delayMillis)

                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Candidate status updated Successfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val delayMillis = 3000L // Delay between transitions in milliseconds
                    val handler = Handler()
                    handler.postDelayed({
                        //   requireActivity().finish()
                    }, delayMillis)
                }
            }
        }
    }

    private fun initviews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())

        val hintpayrate = "Pay Rate *"
        val formattedhintpayrate = formatHintWithRedAsterisk(hintpayrate)
        binding.TIPayRate.hint = formattedhintpayrate
        try {
            if (global.getjobbyjoid.data.billingDetails.overtimeType.equals("None")) {
                binding.tvHeadingOTrule.setText("Overtime Rule (None)")
                binding.TIovertimeMarkup.isEnabled = false
                binding.TIOvertimePayrate.isEnabled = false
                binding.TIovertimeBillrate.isEnabled = false
                binding.ivPlusOvertime.isClickable = false
                binding.ivMinusOvertime.isClickable = false

            } else if (global.getjobbyjoid.data.billingDetails.overtimeType.equals("Paid Not Billed")) {
                binding.tvHeadingOTrule.setText("Overtime Rule (Paid Not Billed)")
                binding.TIovertimeMarkup.isEnabled = false
                binding.TIOvertimePayrate.isEnabled = true
                binding.TIovertimeBillrate.isEnabled = false
                binding.ivPlusOvertime.isClickable = true
                binding.ivMinusOvertime.isClickable = true

            } else {
                binding.tvHeadingOTrule.setText("Overtime Rule (Paid and Billed)")
                binding.TIovertimeMarkup.isEnabled = true
                binding.TIOvertimePayrate.isEnabled = false
                binding.TIovertimeBillrate.isEnabled = false
                binding.ivPlusOvertime.isClickable = true
                binding.ivMinusOvertime.isClickable = true

            }
            if (global.getjobbyjoid.data.billingDetails.doubletimeType.equals("None")) {
                binding.tvHeadingDTrule.setText("DoubleTime Rule (None)")
                binding.TIdoubletimeMarkupPercentage.isEnabled = false
                binding.TIDoubletimePayrate.isEnabled = false
                binding.TIdoubletimeBillrate.isEnabled = false
                binding.ivPlusdbltime.isClickable = false
                binding.ivMinusdbltime.isClickable = false
            } else if (global.getjobbyjoid.data.billingDetails.doubletimeType.equals("Paid Not Billed")) {
                binding.tvHeadingDTrule.setText("DoubleTime Rule (Paid Not Billed)")
                binding.TIdoubletimeMarkupPercentage.isEnabled = false
                binding.TIDoubletimePayrate.isEnabled = true
                binding.TIdoubletimeBillrate.isEnabled = false
                binding.ivPlusdbltime.isClickable = true
                binding.ivMinusdbltime.isClickable = true
            } else {
                binding.tvHeadingDTrule.setText("DoubleTime Rule (Paid and Billed)")
                binding.TIdoubletimeMarkupPercentage.isEnabled = true
                binding.TIDoubletimePayrate.isEnabled = false
                binding.TIdoubletimeBillrate.isEnabled = false
                binding.ivPlusdbltime.isClickable = true
                binding.ivMinusdbltime.isClickable = true
            }
        } catch (e: Exception) {
        }

    }

    private fun clicklisteners() {
        binding.etPayRate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //   binding.TIminPayrate.error = null
                // This method is not used in this example.
                binding.TIPayRate.error = null

            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    val enteredText = editable.toString()
                    var minPayRate = enteredText
                    if (!enteredText.isEmpty()) {

                        payrate = enteredText.toDouble()
                        var markup = global.getjobbyjoid.data.billingDetails.markup
                        var billrate = calculateBillRate(markup.toDouble(), payrate.toDouble())
                        binding.etbillrate.setText(billrate.toString())
                        var otPayrate = payrate.toInt() * overtimeMultiplier
                        binding.etovertimePayrate.setText(otPayrate.toString())
                        var dtPayrate = payrate.toInt() * doubletimeMultiplier
                        binding.etdoubletimePayrate.setText(dtPayrate.toString())

                    } else {
                        binding.etbillrate.setText("")
                        binding.etovertimePayrate.setText("")
                        binding.etdoubletimePayrate.setText("")
                    }
                } catch (e: Exception) {

                }


            }
        })
        binding.etotMarkupPercentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //   binding.TIminPayrate.error = null
                // This method is not used in this example.
                binding.TIovertimeMarkup.error = null

            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    val enteredText = editable.toString()

                    if (!enteredText.isEmpty()) {

                        otmarkupPercentage = enteredText.toDouble()
                        otPayrate = binding.etovertimePayrate.text.toString().toDouble()
                        otBillRate = calculateOTBillRate(otmarkupPercentage, otPayrate.toDouble())
                        binding.etovertimeBillRate.setText(otBillRate.toString())
                    }
                } catch (e: Exception) {

                }


            }
        })
        binding.etDoubletimeMarkupPercentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TIdoubletimeMarkupPercentage.error = null
                //   binding.TIminPayrate.error = null
                // This method is not used in this example.

            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    val enteredText = editable.toString()

                    if (!enteredText.isEmpty()) {

                        dtmarkupPercentage = enteredText.toDouble()
                        dtPayrate = binding.etdoubletimePayrate.text.toString().toDouble()
                        dtBillRate = calculateDTBillRate(dtmarkupPercentage, dtPayrate.toDouble())
                        binding.etdoubletimeBillRate.setText(dtBillRate.toString())
                    }
                } catch (e: Exception) {

                }


            }
        })
        binding.ivMinusOvertime.setOnClickListener {

            overtimeMultiplier -= 0.1f

            // Ensure the value is not less than 0
            overtimeMultiplier = maxOf(0f, overtimeMultiplier)

            // Round to one digit after the decimal point
            overtimeMultiplier = round(overtimeMultiplier * 10) / 10

            binding.tvHeadCountOvertime.text = overtimeMultiplier.toString()

            var otPayrate = payrate.toInt() * overtimeMultiplier

            binding.etovertimePayrate.setText(otPayrate.toString())

            try {
                otBillRate = calculateOTBillRate(otmarkupPercentage, otPayrate.toDouble())

                binding.etovertimeBillRate.setText(otBillRate.toString())
            } catch (e: Exception) {

            }


        }
        binding.ivPlusOvertime.setOnClickListener {

            overtimeMultiplier += 0.1f

            // Ensure the value is not less than 0
            overtimeMultiplier = maxOf(0f, overtimeMultiplier)

            // Round to one digit after the decimal point
            overtimeMultiplier = round(overtimeMultiplier * 10) / 10

            binding.tvHeadCountOvertime.text = overtimeMultiplier.toString()

            var otPayrate = payrate.toInt() * overtimeMultiplier

            binding.etovertimePayrate.setText(otPayrate.toString())

            try {
                otBillRate = calculateOTBillRate(otmarkupPercentage, otPayrate.toDouble())

                binding.etovertimeBillRate.setText(otBillRate.toString())
            } catch (e: Exception) {

            }


        }
        binding.ivMinusdbltime.setOnClickListener {

            doubletimeMultiplier -= 0.1f

            // Ensure the value is not less than 0
            doubletimeMultiplier = maxOf(0f, doubletimeMultiplier)

            // Round to one digit after the decimal point
            doubletimeMultiplier = round(doubletimeMultiplier * 10) / 10

            binding.tvHeadCountdbltime.text = doubletimeMultiplier.toString()

            var dtPayrate = payrate.toInt() * doubletimeMultiplier

            binding.etdoubletimePayrate.setText(dtPayrate.toString())

            try {
                dtBillRate = calculateDTBillRate(dtmarkupPercentage, dtPayrate.toDouble())
                binding.etdoubletimeBillRate.setText(dtBillRate.toString())
            } catch (e: Exception) {
            }

        }
        binding.ivPlusdbltime.setOnClickListener {

            doubletimeMultiplier += 0.1f

            // Ensure the value is not less than 0
            doubletimeMultiplier = maxOf(0f, doubletimeMultiplier)

            // Round to one digit after the decimal point
            doubletimeMultiplier = round(doubletimeMultiplier * 10) / 10

            binding.tvHeadCountdbltime.text = doubletimeMultiplier.toString()

            var dtPayrate = payrate.toInt() * doubletimeMultiplier

            binding.etdoubletimePayrate.setText(dtPayrate.toString())

            try {
                dtBillRate = calculateDTBillRate(dtmarkupPercentage, dtPayrate.toDouble())
                binding.etdoubletimeBillRate.setText(dtBillRate.toString())
            } catch (e: Exception) {

            }

        }
        binding.ivSave.setOnClickListener {
            if (payrate <= 0.0) {
                binding.TIPayRate.error = "Pay Rate is Required."
                binding.TIPayRate.errorIconDrawable = null// Set the error message
                binding.TIPayRate.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
            }
            /*   if (otmarkupPercentage <= 0.0) {
                   binding.TIovertimeMarkup.error = "Overtime Markup Percentage is Required."
                   binding.TIovertimeMarkup.errorIconDrawable = null// Set the error message
                   binding.TIovertimeMarkup.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
               }

               if (dtmarkupPercentage <= 0.0) {
                   binding.TIdoubletimeMarkupPercentage.error =
                       "Doubletime Markup Percentage is Required."
                   binding.TIdoubletimeMarkupPercentage.errorIconDrawable =
                       null// Set the error message
                   binding.TIdoubletimeMarkupPercentage.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)

               }*/
            else {
                loader.show()
                var token = tokenManager.getAccessToken()
                val offeredSalary = ""
                val billRate = binding.etbillrate.text.toString()
                val candidateGUID = "054ce4cb-71b1-4fb0-9211-77d03227ab1e"
                val doubleBillRate = binding.etdoubletimeBillRate.text.toString()
                val doublePayRate = binding.etdoubletimePayrate.text.toString()
                val jobId = 1489
                val joiningDate = ""
                val overtimeBillRate = binding.etovertimeBillRate.text.toString()
                val overtimePayRate = binding.etovertimePayrate.text.toString()
                val payRate = binding.etPayRate.text.toString()
                val statusId = "1004"


                var reqModel = UpdateCandidateStatusRequestModel(
                    offeredSalary,
                    billRate,
                    candidateGUID,
                    doubleBillRate,
                    doublePayRate,
                    jobId,
                    joiningDate,
                    overtimeBillRate,
                    overtimePayRate,
                    payRate,
                    statusId
                )
                viewModel.updateCandidateStatusApi(requireActivity(), reqModel, token)
            }

        }
        binding.ivCancel.setOnClickListener {
            showDialog()
        }
    }

    fun calculateBillRate(markupPercentage: Double, minPayRate: Double): Double {
        val markupMinimumPayRate = minPayRate * (markupPercentage / 100)
        val minBillRate = markupMinimumPayRate + minPayRate
        return minBillRate
    }

    fun calculateOTBillRate(otMarkupPercentage: Double, otpayRate: Double): Double {
        val markupOTPayRate = otpayRate * (otMarkupPercentage / 100)
        val otBillRate = markupOTPayRate + otpayRate
        return otBillRate
    }

    fun calculateDTBillRate(dtMarkupPercentage: Double, dtpayRate: Double): Double {
        val markupDTPayRate = dtpayRate * (dtMarkupPercentage / 100)
        val dtBillRate = markupDTPayRate + dtpayRate
        return dtBillRate
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
            requireActivity().finish()// Dismiss the dialog
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

}