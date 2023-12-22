package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.BottomSheetEditJobBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetEditJob : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetEditJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetEditJobBinding.inflate(inflater, container, false)
        return binding.root

    }

}
