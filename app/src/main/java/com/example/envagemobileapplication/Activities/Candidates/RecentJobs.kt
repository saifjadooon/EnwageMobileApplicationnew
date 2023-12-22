package com.example.envagemobileapplication.Activities.Candidates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
 import com.example.envagemobileapplication.Adapters.RecentJobsAdapter
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.ActivityRecentJobsBinding

class RecentJobs : AppCompatActivity() {

    lateinit var recentJobsAdapter: RecentJobsAdapter
    lateinit var binding: ActivityRecentJobsBinding
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: CandidatesProfileSumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observers()
        clickListeners()

    }

    private fun clickListeners() {

    }


    private fun observers() {
        getRecentJobsObserver()
    }

    private fun getRecentJobsObserver() {
        viewModel.LDgetRecentJobs.observe(this) {
            loader.hide()

            if (it.data != null) {

                var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum> = ArrayList()
                (it.data?.let { arraylist.addAll(it) })

                if(arraylist.size >0){
                    setupJobsAdapter(arraylist)
                }

            } else {
                Toast.makeText(this, "no recent jobs found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setupJobsAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum>
    ) {

        binding!!.rvRecentJobs.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            this
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvRecentJobs.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        recentJobsAdapter = RecentJobsAdapter(
            this,
            candidatelist,
            supportFragmentManager
        )
        binding!!.rvRecentJobs.adapter = recentJobsAdapter

    }

    private fun initViews() {
        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken()!!

        var search = " "

        viewModel.getRecentJobs(
            this,
            token,
            search
        )
    }
}