package com.example.envagemobileapplication.Fragments.Dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.Jobs.AddJob.AddJobActivity
import com.example.envagemobileapplication.Activities.MainActivity
import com.example.envagemobileapplication.Adapters.JobsMainAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentJobsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobsF : Fragment() {

    lateinit var loader: Loader
    private var isLoading = false
    lateinit var adapter: JobsMainAdapter
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentJobsBinding
    var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
        ArrayList()
    var statusesidsList: ArrayList<String> =
        ArrayList() // list in which all statuses ids are populated
    val viewModel: MainActivityViewModel by activityViewModels()
    var jobsStatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
        ArrayList()
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobsBinding.inflate(inflater, container, false)
        initViews()
        return binding.getRoot();
    }

    private fun observers() {
        viewModel.LDgetJobStatusResponse.observe(context as MainActivity)
        {

            jobsStatusesList.clear()
            for (i in 0 until it.data.size) {
                jobsStatusesList.add(it.data.get(i))

                statusesidsList.add(it.data.get(i).jobStatusId.toString())
            }
            try {


                com.example.envagemobileapplication.Utils.Global.JobStatusList =
                    jobsStatusesList
                setUpJobsAdapter(
                    jobsStatusesList,
                    requireContext(), jobsStatusesList
                )
            } catch (e: Exception) {

            }

        }
        viewModel.LDgetJobs.observe(requireActivity()) {

            loader.hide()
            if (it.data != null) {

                var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
                    ArrayList()

                for (i in 0 until it.data.records.size) {


                    jobsList.add(it.data.records.get(i))
                }
                Constants.JobsList = jobsList

                try {
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                }
            }


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getallJobStatuses(requireContext() as MainActivity, token)
    }

    private fun clickListeners() {


        binding.addJobs.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), AddJobActivity::class.java))
        }
        binding.swipeRefreshLayoutforjobs.setOnRefreshListener {
            var isfromJobBottomSheet = false
            val model = SortDirectionJobs(
                clientId = null,
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
                tokenManager.getAccessToken(),
                model,
                isfromJobBottomSheet
            )
            //
        }
        binding.rvJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here


                    loadMoreData(currentPage)
                }
            }
        })
    }

    private fun initViews() {
        loader = Loader(requireContext())
        jobsList = Constants.JobsList
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!

    }

    fun setUpJobsAdapter(
        jobslist: ArrayList<Datum
                >,
        requireContext: Context,
        onBoardingStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum>
    ) {

        binding!!.rvJobs.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvJobs.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = JobsMainAdapter(
            requireContext,
            jobsList,
            childFragmentManager,
            onBoardingStatusList
        )
        binding!!.rvJobs.adapter = adapter
    }

    private fun loadMoreData(page: Int) {
        loader.show()
        isLoading = true
        val model = SortDirectionJobs(
            clientId = null,
            jobFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )

        try {
            var tokenManager: TokenManager = TokenManager(requireContext())

            tokenManager.getAccessToken()
            lifecycleScope.launch {
                ApiUtils.getAPIService(requireContext()).getJobs(
                    model,
                    tokenManager.getAccessToken().toString(),
                )
                    .enqueue(object : Callback<GetJobsResponse> {
                        override fun onResponse(
                            call: Call<GetJobsResponse>,
                            response: Response<GetJobsResponse>
                        ) {
                            loader.hide()
                            if (response.body() != null) {

                                for (i in 0 until response.body()!!.data.records.size) {


                                    jobsList.add(response.body()!!.data.records.get(i))
                                }
                                Constants.JobsList = jobsList

                                currentPage++
                                isLoading = false
                                adapter.notifyDataSetChanged()
                                //  cLientList = clientDataList
                                /* setUpNotificationAdapter(
                                     cLientList,
                                     requireContext(),
                                     onBoardingStatusList
                                 )*/

                            }
                        }

                        override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                            loader.hide()
                            Log.i("exception", t.toString())

                        }
                    })
            }

        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }

    }

    override fun onResume() {
        initViews()
        clickListeners()
        observers()
        super.onResume()
    }
}