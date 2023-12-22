package com.example.envagemobileapplication.Activities.Employees

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.EmployeeAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionEmployees
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentEmployeeListBinding


class EmployeeF : Fragment() {

    private var isfrompagginmation: Boolean = false
    lateinit var adapter: EmployeeAdapter
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentEmployeeListBinding
    private var isLoading = false
    private var currentPage = 1

    val viewModel: MainActivityViewModel by activityViewModels()
    var employeeList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.Record> =
        ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeListBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {
            initViews()
            observers()
            clickListeners()
        }

    }


    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
        getEmployees()

    }


    private fun getEmployees() {
        loader.show()
        isLoading = true
        val model = SortDirectionEmployees(
            employeeFilters = emptyList(),
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )

        viewModel.getEmployees(requireContext(), token, model)

    }


    private fun loadMoreData(page: Int) {
        isfrompagginmation = true
        loader.show()
        isLoading = true
        val model = SortDirectionEmployees(
            employeeFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )


        viewModel.getEmployees(requireContext(), token, model)

        /*   try {
               ApiUtils.getAPIService(requireContext()).getEmployees(
                   model,
                   tokenManager.getAccessToken().toString(),
               )
                   .enqueue(object : Callback<GetEmployeesRes> {
                       override fun onResponse(
                           call: Call<GetEmployeesRes>,
                           response: Response<GetEmployeesRes>
                       ) {
                           if (response.body() != null) {

                               if (response.body()!!.data != null) {


                                   for (i in 0 until response.body()!!.data.records.size) {

                                       employeeList.add(response.body()!!.data.records.get(i))
                                   }
   //                                Constants.Candidatelist = candidateList

                                   isLoading = false
                                   adapter.notifyDataSetChanged()
                               }
                           }
                       }

                       override fun onFailure(call: Call<GetEmployeesRes>, t: Throwable) {
                           Log.i("exceptionddsfdsfds", t.toString())

                       }
                   })
           } catch (ex: java.lang.Exception) {
               Log.i("exceptionddsfdsfds", ex.toString())
           }*/

    }

    private fun clickListeners() {


        binding.swipeRefreshLayoutforemp.setOnRefreshListener {
            val model = SortDirectionEmployees(
                employeeFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusId = -1
            )

            loader.show()
            viewModel.getEmployees(
                requireActivity(),
                tokenManager.getAccessToken(),
                model
            )
        }


        binding.rvEmployees.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })
    }


    fun setUpEmployeesAdapter(
        employeesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.Record>,
        requireContext: Context
    ) {

        try {
            binding!!.rvEmployees.setHasFixedSize(true)
            val mLayoutManager = LinearLayoutManager(
                requireContext
            )
            mLayoutManager.reverseLayout = true
            mLayoutManager.stackFromEnd = true
            binding!!.rvEmployees.layoutManager = LinearLayoutManager(
                requireContext,
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = EmployeeAdapter(
                requireContext,
                employeesList,
                childFragmentManager
            )
            binding!!.rvEmployees.adapter = adapter
        } catch (e: Exception) {
            Log.d("yaseeeeen", e.toString())
        }


    }


    private fun observers() {

        viewModel.LDgetEmployees.observe(requireActivity()) {
            binding.swipeRefreshLayoutforemp.isRefreshing = false
            loader.hide()

            if (it.data != null) {

                if (isfrompagginmation == true && currentPage > 1) {
                    Log.d("paginationcase", "yess")
                    isfrompagginmation = false
                    loader.hide()
                    for (i in 0 until it.data.records.size) {
                        employeeList.add(it.data.records.get(i))
                    }

                    isLoading = false
                    adapter.notifyDataSetChanged()
                }


                if (isfrompagginmation == false && currentPage == 1) {
                    for (i in 0 until it.data.records.size) {
                        employeeList.add(it.data.records.get(i))
                    }

                    Log.d("paginationcase", "1st")

                    try {
                        if (employeeList.size > 0) {
                            setUpEmployeesAdapter(
                                employeeList,
                                requireContext()
                            )


                        } else {
                            Toast.makeText(context, "employees not found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: Exception) {

                    }
                }

            }
        }
    }
}