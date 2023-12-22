package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.envagemobileapplication.Adapters.BottomSheetFilterAdapter
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.CustomSpanSizeLookup
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentBottomSheetFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFilterFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: BottomSheetFilterAdapter

    lateinit var binding: FragmentBottomSheetFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheetFilterBinding.inflate(inflater, container, false)
        setUpDatAdapter(binding)



        binding.tvPublic.setOnClickListener { Constants.filterList.add(1) }
        binding.tvPrivate.setOnClickListener { Constants.filterList.add(2) }
        binding.tvConfidential.setOnClickListener { Constants.filterList.add(2) }


        return binding.root

    }


    private fun setUpDatAdapter(binding: FragmentBottomSheetFilterBinding) {


        binding.rvOnboardingStatusList.setHasFixedSize(true)

/*
        binding.rvOnboardingStatusList.layoutManager = GridLayoutManager(
            context, 3,
            RecyclerView.VERTICAL,
            false
        )*/
        adapter = BottomSheetFilterAdapter(
            requireContext(),
            com.example.envagemobileapplication.Utils.Global.onBoardingStatusList
        )

        val layoutManager =
            GridLayoutManager(requireContext(), 2) // Set your desired span count (e.g., 2 columns)

        layoutManager.spanSizeLookup = CustomSpanSizeLookup(adapter)
        binding.rvOnboardingStatusList.layoutManager = layoutManager




        binding.rvOnboardingStatusList.adapter = adapter
    }
}