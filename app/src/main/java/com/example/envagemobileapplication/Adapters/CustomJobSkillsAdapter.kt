package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.envagemobileapplication.Models.RequestModels.SkillsRequestModels

import com.example.envagemobileapplication.R

class CustomJobSkillsAdapter(
    context: Context,
    private val weekdays: List<SkillsRequestModels>,
    private val onItemSelectedListener: (List<SkillsRequestModels>) -> Unit
) : ArrayAdapter<SkillsRequestModels>(context, 0, weekdays) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // Flag to check whether the change is due to user interaction
    private var userInteracted = false

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

        // Set the checkbox state without triggering the listener
        checkbox.setOnCheckedChangeListener(null)
        checkbox.isChecked = weekday.isSelected
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (userInteracted) {
                weekday.isSelected = isChecked
                // Notify the listener only if the change is due to user interaction
                onItemSelectedListener(getSelectedWeekdays())
            }
        }

        weekdayText.text = weekday.name

        return view
    }

    // Helper method to get the list of selected weekdays
    private fun getSelectedWeekdays(): List<SkillsRequestModels> {
        return weekdays.filter { it.isSelected }
    }

    // Method to set the user interaction flag
    fun setUserInteracted(isUserInteracted: Boolean) {
        userInteracted = isUserInteracted
    }
}