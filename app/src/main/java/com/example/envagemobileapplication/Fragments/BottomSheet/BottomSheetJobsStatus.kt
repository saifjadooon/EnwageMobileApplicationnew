package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.JobsStatusAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.BottomSheetJobsStatusBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetJobsStatus : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetJobsStatusBinding
    val sharedViewModel: MainActivityViewModel by viewModels()
    var statusList: ArrayList<Datum> = ArrayList()
    private lateinit var adapter: JobsStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetJobsStatusBinding.inflate(inflater, container, false)
        setUpDatAdapter()
        return binding.root


    }


    private fun setUpDatAdapter() {


        binding!!.rvStatusList.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            context
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvStatusList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = JobsStatusAdapter(
            requireContext(),
            Constants.jobStatusList, sharedViewModel
        )
        binding!!.rvStatusList.adapter = adapter
    }
}