package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.CleintListAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList.GetClientsAddJobList
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel
import com.example.envagemobileapplication.databinding.BottomsheetClientlistBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BSheetAddjobClientList : BottomSheetDialogFragment() {
    val viewModel: AddJobsSharedViewModel by activityViewModels()
    lateinit var adapter: CleintListAdapter
    lateinit var binding: BottomsheetClientlistBinding
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    private var searchviewtext: String = "a"
    private var isfirsttimesearched: Boolean = false
    lateinit var token: String

    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetClientlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        loader = Loader(requireContext())
        clicklisteners()
        getclientlist(token!!, searchviewtext)
    }

    private fun clicklisteners() {

        binding.searchView2.setOnCloseListener(SearchView.OnCloseListener { // Handle the clear button click here
            // This method will be called when the user clicks the clear button

            handler.postDelayed({
                searchviewtext = "a"
                // Call your API with newText as the search query
                fetchData(searchviewtext)
            }, 500)


            // Return true to indicate that you have handled the event
            true
        })

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    val trimmedText = newText.trimStart()
                    searchviewtext = trimmedText

                    if (trimmedText.length >= 3) {
                        handler.removeCallbacksAndMessages(null)

                        handler.postDelayed({
                            isfirsttimesearched = true

                            fetchData(trimmedText)
                        }, 500)
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
                            searchviewtext = "a"
                            // Call your API with newText as the search query
                            fetchData(newText)
                        }, 500)

                    }
                }
                return true
            }
        })
    }

    private fun getclientlist(token: String, searchviewtext: String) {
        try {

            loader.show()
            ApiUtils.getAPIService(requireContext()).getClientNames(
                token, searchviewtext
            )
                .enqueue(object : Callback<GetClientsAddJobList> {
                    override fun onResponse(
                        call: Call<GetClientsAddJobList>,
                        response: Response<GetClientsAddJobList>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            if (response.body()!!.data.size >= 1) {
                                binding.rvclientlist.visibility = View.VISIBLE
                                binding.tvNoDAta.visibility = View.GONE
                                var clientdata: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList.Datum> =
                                    ArrayList()
                                for (i in 0 until response.body()!!.data.size) {
                                    clientdata.add(response.body()!!.data.get(i))
                                }
                                loader.hide()
                                setupClientListAdapter(clientdata)
                            } else {
                                binding.rvclientlist.visibility = View.INVISIBLE
                                binding.tvNoDAta.visibility = View.VISIBLE
                            }


                        }
                    }

                    override fun onFailure(call: Call<GetClientsAddJobList>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun setupClientListAdapter(clientdata: ArrayList<Datum>) {
        binding!!.rvclientlist.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvclientlist.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = CleintListAdapter(
            requireContext(), clientdata, viewModel
        )
        binding!!.rvclientlist.setHasFixedSize(true);
        binding!!.rvclientlist.adapter = adapter
    }

    private fun fetchData(query: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    getclientlist(token!!, searchviewtext)
                }
            } catch (e: Exception) {
                Log.i("searcherrorrrr", e.toString())
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                e.printStackTrace()
                // Handle errors, e.g., show an error message to the user
            }
        }
    }

}