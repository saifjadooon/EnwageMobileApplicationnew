package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.JobCandidateAdapter
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomsheetJobCandidatesKebabMenu
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionCandidateCandidate
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.GetJobCandidates
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.FragmentJobSummaryCandidateCandiateBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsCandidateF : Fragment() {
    lateinit var model: SortDirectionCandidateCandidate
    private var isfirsttimesearched: Boolean = false
    private val handler = Handler(Looper.getMainLooper())
    private var isfromswipeRefresh: Boolean = false
    private var searchviewtext: String = ""
    val bottomSheetFragment = BottomsheetJobCandidatesKebabMenu()
    private var currentPage = 1
    private var isLoading = false
    lateinit var adapter: JobCandidateAdapter
    lateinit var onlineApplicantsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.Record>
    private var totalcandidates: Int? = 0
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    lateinit var binding: FragmentJobSummaryCandidateCandiateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentJobSummaryCandidateCandiateBinding.inflate(inflater, container, false)
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
        val model = SortDirectionCandidateCandidate(
            candidateFilters = emptyList(),
            jobGUID = Global.jobGuid!!,
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusID = -1
        )
        loader.show()
        viewModel.getJobCandidates(requireActivity(), token, model)
    }

    private fun observers() {

        viewModel.LDshowJobCandidateKebabmenuBottomSheet.observe(requireActivity()) {

            if (bottomSheetFragment.isAdded) {
                return@observe
            } else {
                try {
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                } catch (e: Exception) {
                }
            }


        }
        viewModel.LDgetJObCandidates.observe(requireActivity()) {
            loader.hide()
            if (it != null) {
                if (isfromswipeRefresh) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                if (it.data.records.size >= 1) {

                    totalcandidates = it.data.totalRecords
                    binding.tvNoCandidate.visibility = View.GONE
                    binding.rvCandidates.visibility = View.VISIBLE
                    onlineApplicantsList = ArrayList()
                    for (i in 0 until it.data.records.size) {
                        onlineApplicantsList.add(it.data.records.get(i))
                    }
                    setupCandidateAdapter(onlineApplicantsList)
                } else {
                    binding.tvNoCandidate.visibility = View.VISIBLE
                    binding.rvCandidates.visibility = View.GONE
                }
            }
        }
    }

    private fun clicklisteners() {
        binding.rvCandidates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // Scrolling downward
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here
                    currentPage++
                    if (totalItemCount < totalcandidates!!) {
                        loadMoreData(currentPage)
                    }

                }

            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            isfromswipeRefresh = true
            val model = SortDirectionCandidateCandidate(
                candidateFilters = emptyList(),
                jobGUID = Global.jobGuid!!,
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusID = -1
            )
            loader.show()
            viewModel.getJobCandidates(requireActivity(), token, model)
        }

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search here when the user submits the query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // Implement the debounce mechanism here
                newText?.let {
                    // Remove leading spaces from the input
                    val trimmedText = newText.trimStart()
                    searchviewtext = trimmedText

                    // Check if the length of the trimmed input is at least 3 characters
                    if (trimmedText.length >= 3) {
                        // Cancel previous requests if any
                        handler.removeCallbacksAndMessages(null)

                        // Delay the API call to ensure efficient network usage
                        handler.postDelayed({
                            isfirsttimesearched = true
                            // Call your API with trimmedText as the search query
                            fetchData(trimmedText)
                        }, 500) // Adjust the delay time as needed
                    }

                    if (newText.length < 3) {

                        if (isfirsttimesearched) {
                            isfirsttimesearched = false
                            // Call your API with newText as the search query (even if length is less than 3)
                            handler.postDelayed({
                                // Call your API with newText as the search query
                                fetchData(newText)
                            }, 500)
                        }

                    }

                    if (newText.equals("")) {
                        handler.postDelayed({
                            searchviewtext = ""
                            // Call your API with newText as the search query
                            fetchData(newText)
                        }, 500)

                    }
                }
                return true
            }
        })

        val hint = "Search"
        binding.searchView2.queryHint = hint

        binding.searchView2.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Clear the hint when the SearchView gains focus
                binding.searchView2.queryHint = null
            } else {
                // Set the hint when the SearchView loses focus
                binding.searchView2.queryHint = hint
            }
        }
    }

    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
    }


    private fun setupCandidateAdapter(jobCandidateList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.Record>) {

        try {
            binding.rvCandidates.setHasFixedSize(true)

            adapter = JobCandidateAdapter(
                requireContext(),
                jobCandidateList, viewModel
            )

            binding.rvCandidates.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.rvCandidates.adapter = adapter
        } catch (e: Exception) {

        }

    }

    private fun loadMoreData(page: Int) {

        loader.show()
        isLoading = true


        val model = SortDirectionCandidateCandidate(
            candidateFilters = emptyList(),
            jobGUID = Global.jobGuid!!,
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusID = -1
        )


        try {

            lifecycleScope.launch {

                try {
                    ApiUtils.getAPIService(requireContext()).getjobcandidatess(
                        model,
                        token,
                    )
                        .enqueue(object : Callback<GetJobCandidates> {
                            override fun onResponse(
                                call: Call<GetJobCandidates>,
                                response: Response<GetJobCandidates>
                            ) {
                                if (response.body() != null) {
                                    for (i in 0 until response.body()!!.data.records.size) {
                                        onlineApplicantsList.add(
                                            response.body()!!.data.records.get(
                                                i
                                            )
                                        )
                                    }
                                    loader.hide()
                                    isLoading = false
                                    adapter.notifyDataSetChanged()

                                }
                            }

                            override fun onFailure(call: Call<GetJobCandidates>, t: Throwable) {
                                loader.hide()
                                Log.i("exceptionddsfdsfds", t.toString())

                            }
                        })
                } catch (e: Exception) {
                    loader.hide()
                }
            }

        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun fetchData(query: String) {


        loader.show()
        // Use Coroutines to perform the API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make your API call here and handle the response
                if (query.length < 3) {

                    model = SortDirectionCandidateCandidate(
                        candidateFilters = emptyList(),
                        jobGUID = Global.jobGuid!!,
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusID = -1
                    )
                } else {
                    model = SortDirectionCandidateCandidate(
                        candidateFilters = emptyList(),
                        jobGUID = Global.jobGuid!!,
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusID = -1
                    )
                }
                viewModel.getJobCandidates(requireActivity(), token, model)

            } catch (e: Exception) {
                loader.hide()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                e.printStackTrace()

            }
        }
    }


}