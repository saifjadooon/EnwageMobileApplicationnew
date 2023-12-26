package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments.JobSummaryCandidateCandiateFragment
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments.JobSummaryCandidateDropCandidateF
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments.JobSummaryCandidateOfferSentF
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments.JobsSummaryCandidateOnlineApplcantsF
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentJobsSummaryCandidatesBinding

class JobsSummaryCandidatesF : Fragment() {

    lateinit var loader: Loader
    lateinit var binding: FragmentJobsSummaryCandidatesBinding
    val viewModel: JobSummaryViewModel by activityViewModels()
    private var selectedItem: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobsSummaryCandidatesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initviews()
        clicklisteners()
        networkCalls()
        setupHorizontalScrollView()
    }

    private fun networkCalls() {

    }

    private fun clicklisteners() {

    }

    private fun initviews() {

    }

    private fun setupHorizontalScrollView() {


        var itemList: ArrayList<String> = ArrayList()
        itemList.add("Candidates")
        itemList.add("Dropped Candidates")
        itemList.add("Offer sent")
        itemList.add("Online Applicants")



        for (itemText in itemList) {
            val itemView = layoutInflater.inflate(R.layout.hsv_candidates_item, null)

            val textView = itemView.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Candidates") {
                // Set the background for "Profile summary" by default
                itemView.setBackgroundResource(R.drawable.gray_bg)
                selectedItem = itemView
            }

            textView.text = itemText

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    // Reset the background of the previously selected item
                    selectedItem?.setBackgroundResource(R.drawable.btn_white_radius)

                    itemView.setBackgroundResource(R.drawable.gray_bg)
                    selectedItem = itemView

                    val clickedText = itemText

                    val left = itemView.left

                    binding.horizontalScrollView.scrollTo(left, 0)

                    itemView.requestFocus()

                    if (clickedText == "Dropped Candidates") {
                        replaceFragment(JobSummaryCandidateDropCandidateF())
                    } else if (clickedText.equals("Offer sent")) {
                        replaceFragment(JobSummaryCandidateOfferSentF())
                    } else if (clickedText.equals("Online Applicants")) {
                        replaceFragment(JobsSummaryCandidateOnlineApplcantsF())
                    } else if (clickedText.equals("Candidates")) {
                        replaceFragment(JobSummaryCandidateCandiateFragment())
                    }
                }
            })

            binding.linearlist.addView(itemView)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager: FragmentManager = childFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_candiates, fragment)
            transaction.commit()
        } catch (e: Exception) {
        }

    }
}