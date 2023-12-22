package com.example.envagemobileapplication.Activities.Jobs.JobSummary.SendOfferLetter

import BaseActivity
import android.os.Bundle
import com.example.envagemobileapplication.databinding.ActivitySendOfferLetterBinding

class SendOfferLetterActivity : BaseActivity() {
    lateinit var binding: ActivitySendOfferLetterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySendOfferLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}