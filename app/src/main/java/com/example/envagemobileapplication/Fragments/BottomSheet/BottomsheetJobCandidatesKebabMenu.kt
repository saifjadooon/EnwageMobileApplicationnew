package com.example.envagemobileapplication.Fragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.ComposeMessage.ComposeMessageActivity
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter.SendOfferLetterActivity
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.BsheetJobCandidateKebabMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomsheetJobCandidatesKebabMenu : BottomSheetDialogFragment() {


    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BsheetJobCandidateKebabMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BsheetJobCandidateKebabMenuBinding.inflate(inflater, container, false)

        if (global.showofferLetter) {
            binding.sendOfferLetter.visibility = View.VISIBLE
        }
        else  {
            binding.sendOfferLetter.visibility = View.GONE
        }
        binding.sendOfferLetter.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    SendOfferLetterActivity::class.java
                )
            )
        }

        binding.composeMessage.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    ComposeMessageActivity::class.java
                )
            )
        }
        return binding.root

    }

}
