package com.example.envagemobileapplication.Utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
/*
class DatePickerHelper(private val context: Context) {

    private var isDatePickerOpen = false

    fun attachDatePicker(button: TextInputLayout, textviewSelectedDate: TextView) {
        button.setOnClickListener {
            if (!isDatePickerOpen) {
                showDatePicker(textviewSelectedDate)
            }
        }
    }

    private fun showDatePicker(textviewSelectedDate: TextView) {
        isDatePickerOpen = true

        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Format the selected date as "MM/dd/yyyy"
                val formattedDate = formatDate(year, month, dayOfMonth)
                textviewSelectedDate.text = formattedDate
                isDatePickerOpen = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the minimum date to today
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.setOnDismissListener {
            isDatePickerOpen = false
        }

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}*/
class DatePickerHelper(private val context: Context) {

    private var isDatePickerOpen = false
    private var selectedDate: String? = null // Property to store the selected date

    fun attachDatePicker(button: TextInputLayout, textviewSelectedDate: TextView) {
        button.setOnClickListener {
            if (!isDatePickerOpen) {
                showDatePicker(textviewSelectedDate)
            }
        }
    }

    private fun showDatePicker(textviewSelectedDate: TextView) {
        isDatePickerOpen = true

        val calendar = Calendar.getInstance()

        // Set the initial date of the date picker to the selected date, if available
        val selectedDateArray = selectedDate?.split("/")?.map { it.toInt() }
        val initialYear = selectedDateArray?.get(2) ?: calendar.get(Calendar.YEAR)
        val initialMonth = selectedDateArray?.get(0)?.minus(1) ?: calendar.get(Calendar.MONTH)
        val initialDay = selectedDateArray?.get(1) ?: calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Format the selected date as "MM/dd/yyyy"
                val formattedDate = formatDate(year, month, dayOfMonth)
                textviewSelectedDate.text = formattedDate
                selectedDate = formattedDate // Update the selected date
                isDatePickerOpen = false
            },
            initialYear,
            initialMonth,
            initialDay
        )

        // Set the minimum date to today
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.setOnDismissListener {
            isDatePickerOpen = false
        }

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}