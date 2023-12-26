package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.envagemobileapplication.R

class JobSummaryCandidateDropCandidateF : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_job_summary_candidate_drop_candidate,
            container,
            false
        )
    }

}