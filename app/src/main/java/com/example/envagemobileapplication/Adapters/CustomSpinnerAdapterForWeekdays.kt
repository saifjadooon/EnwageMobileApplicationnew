package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.envagemobileapplication.Models.RequestModels.weekdaysModel
import com.example.envagemobileapplication.R


class CustomSpinnerAdapterForWeekdays(
    context: Context,
    private val weekdays: List<weekdaysModel>,
    private val onItemSelectedListener: (List<weekdaysModel>) -> Unit
) : ArrayAdapter<weekdaysModel>(context, 0, weekdays) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item_weekday, parent, false)

        val checkbox: CheckBox = view.findViewById(R.id.checkbox)
        val weekdayText: TextView = view.findViewById(R.id.weekdayText)

        val weekday = getItem(position)!!

        // Remove the previous listener to avoid triggering it unintentionally
        checkbox.setOnCheckedChangeListener(null)

        // Set the initial state without triggering the listener
        checkbox.isChecked = weekday.isSelected

        // Set the listener after setting the initial state
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            weekday.isSelected = isChecked
            // Notify the listener when an item is selected
            onItemSelectedListener(getSelectedWeekdays())
        }

        weekdayText.text = weekday.name

        return view
    }

    // Helper method to get the list of selected weekdays
    private fun getSelectedWeekdays(): List<weekdaysModel> {
        return weekdays.filter { it.isSelected }
    }
}
