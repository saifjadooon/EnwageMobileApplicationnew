package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments

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
import com.example.envagemobileapplication.Adapters.OnlineApplicantsAdapter
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetJobOnlineAplicants
import com.example.envagemobileapplication.Fragments.BottomSheet.OnlineApplicantBottomSheet
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionGetOnlineCandidate
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.OnlineApplicantsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.FragmentJobsSummaryCandidateOnlineApplcantsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsOnlineApplicantsF : Fragment() {
    var global = com.example.envagemobileapplication.Utils.Global
    private val isfirstTimeFragmentLoaded: Boolean = true
    private var totalonlineapplicants: Int? = 0
    private var isfromswipeRefresh: Boolean = false
    lateinit var onlineApplicantsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Record>
    lateinit var adapter: OnlineApplicantsAdapter
    lateinit var token: String
    private var currentPage = 1
    lateinit var tokenManager: TokenManager
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    lateinit var loader: Loader
    private var isLoading = false
    lateinit var binding: FragmentJobsSummaryCandidateOnlineApplcantsBinding
    val bottomSheetFragment = BottomSheetJobOnlineAplicants()
    val bottomSheetCandidateDetailFragment = OnlineApplicantBottomSheet()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentJobsSummaryCandidateOnlineApplcantsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {
            initViews()
            clicklisteners()
            observers()
            networkCalls()
        }

    }

    private fun networkCalls() {
        val model = SortDirectionGetOnlineCandidate(
            applicantFilter = emptyList(),
            jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )
        loader.show()
        viewModel.getOnlineApplicants(requireActivity(), token, model)
        var jobGuid = com.example.envagemobileapplication.Utils.Global.jobGuid
        //  var jobGuid = "c37b0c1b-8515-4e32-b026-a2db13343b20"
        viewModel.getJobHeaderSummary(requireActivity(), token, jobGuid)
    }

    private fun observers() {
        viewModel.LDgetOnlineCandidates.observe(requireActivity()) {
            loader.hide()
            if (it != null) {
                if (isfromswipeRefresh) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                if (it.data.records.size >= 1) {

                    totalonlineapplicants = it.data.totalRecords
                    binding.tvNoApplicant.visibility = View.GONE
                    binding.rvOnlineApplicants.visibility = View.VISIBLE
                    onlineApplicantsList = ArrayList()
                    for (i in 0 until it.data.records.size) {
                        onlineApplicantsList.add(it.data.records.get(i))
                    }
                    setupOnlineApplicantAdapter(onlineApplicantsList)

                    com.example.envagemobileapplication.Utils.Global.onlineApplicantDetails =
                        it.data
                    var applicantID = it.data.records.get(0).applicantId
                    try {
                        viewModel.getAllSkills(requireActivity(), token, applicantID)
                    } catch (e: Exception) {
                    }


                } else {
                    binding.tvNoApplicant.visibility = View.VISIBLE
                    binding.rvOnlineApplicants.visibility = View.GONE
                }


            }
        }

        viewModel.LDviewBottomSheet.observe(requireActivity()) {

            if (global.isfirstTimeFragmentLoaded) {

            }
            else {
                if (bottomSheetFragment.isAdded) {
                    return@observe
                } else {
                    try {
                        var applicantid = it.toString()

                        com.example.envagemobileapplication.Utils.Global.applicantid = applicantid
                        bottomSheetFragment.show(
                            requireActivity().supportFragmentManager,
                            bottomSheetFragment.tag
                        )
                    } catch (e: Exception) {
                    }
                }
            }


        }

        viewModel.LDhideBottomSheet.observe(requireActivity()) {

            if (it.equals("true")) {
                bottomSheetFragment.dismiss()
                val model = SortDirectionGetOnlineCandidate(
                    applicantFilter = emptyList(),
                    jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
                    pageIndex = 1,
                    pageSize = 25,
                    searchText = "",
                    sortBy = "CreatedDate",
                    sortDirection = 1
                )
                loader.show()
                viewModel.getOnlineApplicants(requireActivity(), token, model)
            }
        }

        viewModel.LDhideSummaryDetailBottomSheet.observe(requireActivity()) {

            if (isAdded) {
                if (it.equals("true")) {
                    try {
                        bottomSheetCandidateDetailFragment.dismiss()
                        val model = SortDirectionGetOnlineCandidate(
                            applicantFilter = emptyList(),
                            jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
                            pageIndex = 1,
                            pageSize = 25,
                            searchText = "",
                            sortBy = "CreatedDate",
                            sortDirection = 1
                        )
                        loader.show()
                        viewModel.getOnlineApplicants(requireActivity(), token, model)
                    } catch (e: Exception) {
                    }


                }

            }
        }

        viewModel.LDgetJobHeaderSummary.observe(requireActivity()) {
            com.example.envagemobileapplication.Utils.Global.jobHeaderSummary = it

        }

        viewModel.LDviewCandidateDetailBottomSheet.observe(requireActivity()) {
            if (global.isfirstTimeFragmentLoaded) {
            }
            else {
                if (bottomSheetCandidateDetailFragment.isAdded) {
                    return@observe
                } else {
                    try {
                        var jobId = it.toString()

                        com.example.envagemobileapplication.Utils.Global.GlobalJobID = jobId.toInt()
                        bottomSheetCandidateDetailFragment.show(
                            requireActivity().supportFragmentManager,
                            bottomSheetFragment.tag
                        )
                    } catch (e: Exception) {
                    }
                }
            }


        }

    }

    private fun setupOnlineApplicantAdapter(onlineApplicantsList: ArrayList<Record>) {

        try {
            binding.rvOnlineApplicants.setHasFixedSize(true)

            adapter = OnlineApplicantsAdapter(
                requireContext(),
                onlineApplicantsList, viewModel
            )


            binding.rvOnlineApplicants.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.rvOnlineApplicants.adapter = adapter
        } catch (e: Exception) {

        }

    }

    private fun clicklisteners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            isfromswipeRefresh = true
            /*1802*/
            val model = SortDirectionGetOnlineCandidate(
                applicantFilter = emptyList(),
                jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1
            )
            viewModel.getOnlineApplicants(requireActivity(), token, model)
        }


        binding.rvOnlineApplicants.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // Scrolling downward
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here
                    currentPage++
                    if (totalItemCount < totalonlineapplicants!!) {
                        loadMoreData(currentPage)
                    }

                }

            }
        })

    }

    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        onlineApplicantsList = ArrayList()

    }

    private fun loadMoreData(page: Int) {

        loader.show()
        isLoading = true


        val model = SortDirectionGetOnlineCandidate(
            applicantFilter = emptyList(),
            jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )
        try {
            lifecycleScope.launch {
                try {
                    ApiUtils.getAPIService(requireContext()).getOnlineApplicants(
                        model,
                        token.toString(),
                    )
                        .enqueue(object : Callback<OnlineApplicantsResponse> {
                            override fun onResponse(
                                call: Call<OnlineApplicantsResponse>,
                                response: Response<OnlineApplicantsResponse>
                            ) {
                                if (response.body() != null) {

                                    loader.hide()
                                    for (i in 0 until response.body()!!.data.records.size) {
                                        onlineApplicantsList.add(
                                            response.body()!!.data.records.get(
                                                i
                                            )
                                        )
                                    }
                                    //  currentPage++
                                    isLoading = false
                                    adapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(
                                call: Call<OnlineApplicantsResponse>,
                                t: Throwable
                            ) {
                                loader.hide()
                                Log.i("exceptionddsfdsfds", t.toString())

                            }
                        })
                } catch (ex: java.lang.Exception) {
                    loader.hide()
                    Log.i("exceptionddsfdsfds", ex.toString())
                }
            }


        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }
}