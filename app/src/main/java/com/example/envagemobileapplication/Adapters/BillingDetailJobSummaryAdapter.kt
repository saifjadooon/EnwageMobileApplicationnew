package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobsSummaryFragment
import com.example.envagemobileapplication.R


class BillingDetailJobSummaryAdapter(
    var context: Context,
    billingdatalist: ArrayList<JobsSummaryFragment.KeyValueData>

) :
    RecyclerView.Adapter<BillingDetailJobSummaryAdapter.MyViewHolder>() {

    var dataList: ArrayList<JobsSummaryFragment.KeyValueData> = billingdatalist


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.billing_detail_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val data = dataList[position]

        holder.tvKey.text = data.key
        if (data.value != "null") {
            holder.tvValue.text = data.value.toString()
        } else {
            holder.tvValue.text = "-"
        }


    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKey: TextView = itemView.findViewById(R.id.tvKey)
        var tvValue: TextView = itemView.findViewById(R.id.tvValue)


    }
}