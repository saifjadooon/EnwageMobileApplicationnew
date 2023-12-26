package com.example.envagemobileapplication.Fragments.Dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.CandidateAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionCandidates
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.GetCandidatesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentCandidateBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateF : Fragment() {
    lateinit var adapter: CandidateAdapter
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentCandidateBinding
    private var isLoading = false
    private var currentPage = 1

    val viewModel: MainActivityViewModel by activityViewModels()
    var candidateList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record> =
        ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCandidateBinding.inflate(inflater, container, false);
        initViews()
        clickListeners()
        observers()
        return binding.getRoot();

    }

    private fun observers() {
        viewModel.LDgetCandidates.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {

                var candidateList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record> =
                    ArrayList()

                for (i in 0 until it.data.records.size) {


                    candidateList.add(it.data.records.get(i))
                }
                Constants.Candidatelist = candidateList

                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun clickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val model = SortDirectionCandidates(
                candidateFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusId = -1
            )

            loader.show()
            viewModel.getCandidates(
                requireActivity(),
                tokenManager.getAccessToken(),
                model
            )
        }
        binding.rvCandidates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        candidateList = Constants.Candidatelist
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
        setUpCandidatesAdapter(
            candidateList,
            requireContext()
        )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setUpCandidatesAdapter(
        candidatelist: ArrayList<Record>,
        requireContext: Context
    ) {

        binding!!.rvCandidates.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvCandidates.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = CandidateAdapter(
            requireContext,
            candidatelist,
            childFragmentManager
        )
        binding!!.rvCandidates.adapter = adapter
    }

    private fun loadMoreData(page: Int) {
        loader.show()
        isLoading = true
        val model = SortDirectionCandidates(
            candidateFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )

        try {
            ApiUtils.getAPIService(requireContext()).getCandidates(
                model,
                tokenManager.getAccessToken().toString(),
            )
                .enqueue(object : Callback<GetCandidatesResponse> {
                    override fun onResponse(
                        call: Call<GetCandidatesResponse>,
                        response: Response<GetCandidatesResponse>
                    ) {
                        if (response.body() != null) {
                            loader.hide()
                            if (response.body()!!.data != null) {


                                for (i in 0 until response.body()!!.data.records.size) {


                                    candidateList.add(response.body()!!.data.records.get(i))
                                }
                                Constants.Candidatelist = candidateList
                                currentPage++
                                isLoading = false
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetCandidatesResponse>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }

    }
}