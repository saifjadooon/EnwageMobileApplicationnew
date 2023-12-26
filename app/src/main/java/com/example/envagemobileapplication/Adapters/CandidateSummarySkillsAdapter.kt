package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R


class CandidateSummarySkillsAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.Datum>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<CandidateSummarySkillsAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.Datum> =
        onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_candidatesummary_skills, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_skillName.setOnLongClickListener{
            if(!dataList?.get(position)?.name.isNullOrBlank()){
                Toast.makeText(context, dataList?.get(position)?.name, Toast.LENGTH_SHORT).show()
            }

            true
        }

        try {
            holder.tv_skillName.text = dataList.get(position).name
        }
        catch (e:Exception){
            Log.d("CandidateSkillsExcep",e.toString())
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_skillName: TextView = itemView.findViewById(R.id.tv_skillName)
    }
}


