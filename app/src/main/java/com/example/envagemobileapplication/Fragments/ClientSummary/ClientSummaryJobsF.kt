package com.example.envagemobileapplication.Fragments.ClientSummary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.ClientJobsAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentClientSummaryJobsBinding

class ClientSummaryJobsF : Fragment() {

    lateinit var binding: FragmentClientSummaryJobsBinding
    val viewModel: ClientSummaryViewModel by activityViewModels()
    lateinit var tokenmanager: TokenManager
    var statusesidsList: ArrayList<String> =
        ArrayList() // list in which all statuses ids are populated
    var jobsStatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
        ArrayList()
    lateinit var adapter: ClientJobsAdapter
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment#
        binding = FragmentClientSummaryJobsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // getClientJobs()
        initViews()
        observers()
        clickListeners()
    }

    private fun clickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            getClientJobs()
        }
    }

    private fun initViews() {
        tokenmanager = TokenManager(requireContext())
        loader = Loader(requireContext())
        viewModel.getallJobStatuses(
            requireActivity(),
            tokenmanager.getAccessToken().toString()
        )
    }

    private fun observers() {
        viewModel.LDgetJobStatusResponse.observe(requireActivity())
        {
            jobsStatusesList.clear()
            for (i in 0 until it.data.size) {
                jobsStatusesList.add(it.data.get(i))

                statusesidsList.add(it.data.get(i).jobStatusId.toString())
            }
            try {


                com.example.envagemobileapplication.Utils.Global.JobStatusList =
                    jobsStatusesList

                getClientJobs()

            } catch (e: Exception) {

            }

        }
        viewModel.LDgetJobs.observe(requireActivity()) {

            if (it.data != null) {
                loader.hide()
                if (it.data.records.size != 0) {
                    binding.rvClientJobs.visibility = View.VISIBLE
                    binding.tvNoJobs.visibility = View.GONE
                    var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
                        ArrayList()

                    for (i in 0 until it.data.records.size) {
                        jobsList.add(it.data.records.get(i))
                    }

                    try {
                        setUpJobsAdapter(jobsList, requireContext(), jobsStatusesList)

                    } catch (e: Exception) {
                       // Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                    }

                } else {
                    binding.rvClientJobs.visibility = View.INVISIBLE
                    binding.tvNoJobs.visibility = View.VISIBLE
                }
            }


        }
    }


    private fun getClientJobs() {

        loader.show()
        var isfromJobBottomSheet = false
        val model = SortDirectionJobs(
            clientId = Constants.clientid.toString(),
            jobFilters = emptyList(),
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )
        viewModel.getJobs(
            requireActivity(),
            tokenmanager.getAccessToken(),
            model,
            isfromJobBottomSheet
        )

    }

    fun setUpJobsAdapter(
        jobslist: ArrayList<Record>,
        requireContext: Context,
        onBoardingStatusList: ArrayList<Datum>
    ) {

        binding!!.rvClientJobs.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvClientJobs.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = ClientJobsAdapter(
            requireContext,
            jobslist,
            childFragmentManager,
            onBoardingStatusList
        )
        binding!!.rvClientJobs.adapter = adapter

        try {
            binding.swipeRefreshLayout.isRefreshing = false
        } catch (e: Exception) {
        }
    }


    override fun onResume() {
        super.onResume()

        //  getClientJobs()
    }
}