package com.example.envagemobileapplication.Fragments.Dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.AddClient.AddClientActivity
import com.example.envagemobileapplication.Activities.MainActivity
import com.example.envagemobileapplication.Adapters.ClientsMainAdapter
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetFilterFragment
import com.example.envagemobileapplication.Models.RequestModels.sortDirection
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.GetClients
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentClientsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientsF : Fragment() {

    lateinit var loader: Loader
    val viewModel: MainActivityViewModel by activityViewModels()
    lateinit var tokenManager: TokenManager
    var token = ""
    private var currentPage = 1
    private val pageSize = 25 // Number of items to load per page
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientsBinding.inflate(inflater, container, false)

        loader = Loader(requireContext())
        initViews()
        clickListeners()
        observers()
        return binding.root
    }

    private fun observers() {
        viewModel.LDgetClients.observe(requireActivity())
        {
            loader.hide()
            if (it.data != null) {
                val clientDataList: ArrayList<Record> = ArrayList()
                for (i in 0 until it.data.records.size) {
                    clientDataList.add(it.data.records.get(i))
                }

                Constants.ClientList = clientDataList
                cLientList = clientDataList
                com.example.envagemobileapplication.Utils.Global.onBoardingStatusList =
                    onBoardingStatusList
                try {
                    adapter.notifyDataSetChanged()

                    loader.hide()
                } catch (e: Exception) {
                    loader.hide()
                }

            }


        }

        viewModel.LDgetCompanyOnBoardingStatus.observe(context as MainActivity)
        {

            onBoardingStatusList.clear()
            for (i in 0 until it.data.size) {
                onBoardingStatusList.add(it.data.get(i))
                statusesidsList.add(it.data.get(i).companyOnboardingStatusId.toString())
            }
            try {
                com.example.envagemobileapplication.Utils.Global.onBoardingStatusList =
                    onBoardingStatusList
                setUpNotificationAdapter(
                    cLientList,

                    requireContext(), onBoardingStatusList
                )
            } catch (e: Exception) {

            }

        }
    }

    private fun clickListeners() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            loader.show()
            val model = sortDirection(
                pageIndex = 1,
                pageSize = 25,
                sortBy = "CreatedDate",
                sortDirection = 1,
                searchText = "",
                tileStatusId = -1
            )


            getClients(
                requireActivity(),
                tokenManager.getAccessToken(),
                model
            )
        }
        binding.addCliens.setOnClickListener {


            requireContext().startActivity(Intent(requireContext(), AddClientActivity::class.java))


        }
        binding.ivFilter.setOnClickListener {

            val bottomSheetFragment = BottomSheetFilterFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        }
        binding.clearFilter.setOnClickListener {

            binding.clearFilter.visibility = View.INVISIBLE
            Constants.filterList.clear()

            setUpNotificationAdapter(
                cLientList,
                requireContext(), onBoardingStatusList
            )
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here
                    //     currentPage++
                    loadMoreData(currentPage)
                }
            }
        })
        binding.addCliens.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), AddClientActivity::class.java))

        }
    }

    private fun initViews() {
        cLientList = Constants.ClientList
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
        onBoardingStatusList = ArrayList()
        cfm = childFragmentManager
        ctx = requireContext()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getallOnBoardingStatuses(requireContext() as MainActivity, token)
    }

    companion object {
        lateinit var onBoardingStatusList: ArrayList<Datum> // list in which all data is populated
        var filteredList: ArrayList<Datum> = ArrayList() // list in which all data is populated
        var filteredListClients: ArrayList<Record> =
            ArrayList()// list in which all data is populated


        // list in which all data is populated
        var statusesidsList: ArrayList<String> =
            ArrayList() // list in which all statuses ids are populated

        var cLientList: ArrayList<Record> = ArrayList()

        lateinit var binding: FragmentClientsBinding


        public lateinit var adapter: ClientsMainAdapter

        lateinit var cfm: FragmentManager
        lateinit var ctx: Context

        fun filterListAdapter() {


            for (i in 0 until onBoardingStatusList.size) {
                for (j in 0 until Constants.filterList.size) {


                    if (onBoardingStatusList.get(i).companyOnboardingStatusId.equals(
                            Constants.filterList.get(
                                j
                            )
                        )
                    ) {

                        filteredList.add(onBoardingStatusList.get(i))
                    }

                }


            }

            for (i in 0 until cLientList.size) {

                for (j in 0 until filteredList.size) {
                    if (cLientList[i].onboardingStatusId.equals(filteredList[j].companyOnboardingStatusId)) {

                        filteredListClients.add(cLientList.get(i))
                    }
                }

            }
            Log.i("updatedList", filteredListClients.size.toString() + "wi")


            adapter = ClientsMainAdapter(
                ctx,
                filteredListClients,
                cfm,
                onBoardingStatusList
            )
            binding!!.recyclerView.adapter = adapter
            binding!!.recyclerView.adapter?.notifyDataSetChanged()
        }

        fun setUpNotificationAdapter(
            cLientList: ArrayList<Record>,
            requireContext: Context,
            onBoardingStatusList: ArrayList<Datum>
        ) {

            binding!!.recyclerView.setHasFixedSize(true)
            val mLayoutManager = LinearLayoutManager(
                requireContext
            )
            mLayoutManager.reverseLayout = true
            mLayoutManager.stackFromEnd = true
            binding!!.recyclerView.layoutManager = LinearLayoutManager(
                requireContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ClientsMainAdapter(
                requireContext,
                cLientList,
                cfm,
                onBoardingStatusList
            )
            binding!!.recyclerView.setHasFixedSize(true);
            binding!!.recyclerView.adapter = adapter
        }

    }

    private fun loadMoreData(page: Int) {
        isLoading = true
        var isfromCompnyStatus = false
        loader.show()
        val model = sortDirection(
            page,
            pageSize = 25,
            sortBy = "CreatedDate",
            sortDirection = 1,
            searchText = "",
            tileStatusId = -1
        )
        try {
            var tokenManager: TokenManager = TokenManager(requireContext())

            tokenManager.getAccessToken()

            lifecycleScope.launch {
                ApiUtils.getAPIService(requireContext()).getClients(
                    model,
                    tokenManager.getAccessToken().toString(),
                )
                    .enqueue(object : Callback<GetClients> {
                        override fun onResponse(
                            call: Call<GetClients>,
                            response: Response<GetClients>
                        ) {
                            if (response.body() != null) {
                                loader.hide()
                                for (i in 0 until response.body()!!.data.records.size) {
                                    cLientList.add(response.body()!!.data.records.get(i))
                                }
                                currentPage++
                                isLoading = false
                                adapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<GetClients>, t: Throwable) {
                            Log.i("exception", t.toString())

                        }
                    })
            }

        } catch (ex: java.lang.Exception) {
            Log.i("exception", ex.toString())
        }
    }

    override fun onResume() {

        super.onResume()
        loader.show()
        val model = sortDirection(
            pageIndex = 1,
            pageSize = 25,
            sortBy = "CreatedDate",
            sortDirection = 1,
            searchText = "",
            tileStatusId = -1
        )

        getClients(
            requireActivity(),
            tokenManager.getAccessToken(),
            model
        )
        cfm = childFragmentManager
    }

    fun getClients(context: Activity, accessToken: String?, sortDirection: sortDirection) {

        try {
            ApiUtils.getAPIService(context).getClients(
                sortDirection, accessToken.toString(),
            )
                .enqueue(object : Callback<GetClients> {
                    override fun onResponse(
                        call: Call<GetClients>,
                        response: Response<GetClients>
                    ) {
                        if (response.body() != null) {

                            if (response.body()!!.data != null) {
                                val clientDataList: ArrayList<Record> = ArrayList()
                                for (i in 0 until response.body()!!.data.records.size) {
                                    clientDataList.add(response.body()!!.data.records.get(i))
                                }

                                Constants.ClientList = clientDataList
                                cLientList = clientDataList
                                com.example.envagemobileapplication.Utils.Global.onBoardingStatusList =
                                    onBoardingStatusList
                                try {
                                    setUpNotificationAdapter(
                                        cLientList,
                                        requireContext(), onBoardingStatusList
                                    )
                                    adapter.notifyDataSetChanged()

                                    val delayMillis =
                                        1500L // Delay between transitions in milliseconds
                                    val handler = Handler()
                                    handler.postDelayed({
                                        loader.hide()
                                        binding.swipeRefreshLayout.isRefreshing = false
                                    }, delayMillis)

                                } catch (e: Exception) {
                                    loader.hide()
                                }

                            }

                        }
                    }

                    override fun onFailure(call: Call<GetClients>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }
}

