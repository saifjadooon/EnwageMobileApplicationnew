package com.example.envagemobileapplication.Fragments.BottomSheet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.CandidateJobStatusAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BSCandidateJobStatusF : BottomSheetDialogFragment() {

    val viewModel: CandidatesProfileSumViewModel by viewModels()
    lateinit var binding: FragmentBottomSheetBinding
    var statusList: ArrayList<Datum> = ArrayList()
    private lateinit var adapter: CandidateJobStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        /*    statusList =
                requireArguments().getSerializable("statusListData") as ArrayList<Datum>*/

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

        adapter = CandidateJobStatusAdapter(
            requireContext(),
            Constants.candidateJobStatusList, viewModel
        )
        binding!!.rvStatusList.adapter = adapter

    }
}