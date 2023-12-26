package com.example.envagemobileapplication.Utils

import androidx.recyclerview.widget.GridLayoutManager
import com.example.envagemobileapplication.Adapters.BottomSheetFilterAdapter

class CustomSpanSizeLookup(private val adapter: BottomSheetFilterAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        // Adjust the logic here based on your item's position or data
        return if (adapter.getItemViewType(position) == BottomSheetFilterAdapter.WIDE_VIEW_TYPE) {
            // Return a span count of 2 for wide items
            2
        } else {
            // Return a span count of 1 for other items (wrap content)
            1
        }
    }
}