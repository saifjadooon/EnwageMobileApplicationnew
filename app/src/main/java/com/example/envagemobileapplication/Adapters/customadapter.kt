package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.envagemobileapplication.R

class customadapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var selectedPosition = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        if (position == selectedPosition) {
            // Customize the background of the selected item here
            // Set background color to black
            (view as TextView).setTextColor(Color.WHITE)

            view.setBackgroundResource(R.drawable.bg_spinner_item)
            view.textSize = 18f
        } else {
            (view as TextView).setTextColor(Color.BLACK)
            // Reset the background for other items
            view.setBackgroundColor(Color.TRANSPARENT)
            view.textSize = 18f

        }



        return view
    }


}