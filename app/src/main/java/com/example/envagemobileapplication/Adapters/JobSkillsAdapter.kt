package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.JobSkillsSummary
import com.example.envagemobileapplication.R


class JobSkillsAdapter(
    var context: Context,
    skillList: ArrayList<JobSkillsSummary>

) :
    RecyclerView.Adapter<JobSkillsAdapter.MyViewHolder>() {

    var dataList: ArrayList<JobSkillsSummary> = skillList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.billing_detail_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvValue.visibility = View.GONE
        holder.tvKey.setText(dataList.get(position).name)


    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKey: TextView = itemView.findViewById(R.id.tvKey)
        var tvValue: TextView = itemView.findViewById(R.id.tvValue)


    }
}