package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Fragments.Dashboard.ClientsF
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants


class BottomSheetFilterAdapter(
    var context: Context,
    onBoardingStatusList: ArrayList<Datum>
) :
    RecyclerView.Adapter<BottomSheetFilterAdapter.MyViewHolder>() {

    lateinit var onclickedFilterItemList: ArrayList<Int>

    companion object {
        const val WIDE_VIEW_TYPE = 1 // Define your custom view type constant
        const val NORMAL_VIEW_TYPE = 2 // Define another custom view type constant
    }


    var statusList = onBoardingStatusList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.filterlist_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        // Determine the view type based on your criteria
        return if (shouldDisplayWideView(position)) {
            WIDE_VIEW_TYPE
        } else {
            NORMAL_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val statusname = statusList.get(position).onboardingStatusName.toString()

        onclickedFilterItemList = ArrayList()
        holder.tv_status_name.setText(statusname)
        if (Constants.filterList.contains(statusList[position].companyOnboardingStatusId)) {

            holder.tv_status_name.setBackgroundResource(R.drawable.filterfilledbg)
            holder.tv_status_name.setTextColor(context.resources.getColor(R.color.white))

        }
        /*    try {
                val maxLength = 9
                val filterArray = arrayOf(InputFilter.LengthFilter(maxLength))
                holder.tv_status_name.filters = filterArray

                if (statusname.length > maxLength) {
                    val trimmedText = statusname.substring(0, maxLength) + ".."
                    holder.tv_status_name.text = trimmedText
                } else {
                    holder.tv_status_name.setText(statusname)
                }

            } catch (e: Exception) {
            }
    */

        holder.tv_status_name.setOnClickListener {
            ClientsF.binding.clearFilter.visibility = View.VISIBLE
            holder.tv_status_name.setBackgroundResource(R.drawable.filterfilledbg)
            holder.tv_status_name.setTextColor(context.resources.getColor(R.color.white))

            if (!Constants.filterList.contains(statusList[position].companyOnboardingStatusId)) {
                onclickedFilterItemList.add(statusList[position].companyOnboardingStatusId)
                Constants.filterList = onclickedFilterItemList
                ClientsF.filterListAdapter()
            } else {

                val toast = Toast.makeText(
                    context,
                    "Already added",
                    Toast.LENGTH_LONG)
                
                toast.show()

            }
        }


    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_status_name: TextView = itemView.findViewById(R.id.tv_name)


    }

    private fun shouldDisplayWideView(position: Int): Boolean {

        val user = statusList[position]
        return user.onboardingStatusName.length > 18
        // Define your custom logic here to determine if an item should be wide
        // In this example, every item at an even position is considered wide:
        return position % 2 == 0
    }

}