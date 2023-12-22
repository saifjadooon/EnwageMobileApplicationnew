package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants.Companion.formatDate
import com.example.envagemobileapplication.Utils.Constants.Companion.formatDateForOlderVersions

class CandidateSummaryEducationAdapter(
    var context: Context,
    onlineList : ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.Datum>,
    childFragmentManager: FragmentManager
) : RecyclerView.Adapter<CandidateSummaryEducationAdapter.MyViewHolder>() {


    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.Datum> = onlineList



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_candidatesummary_education, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_university.setOnLongClickListener{
            if(!dataList?.get(position)?.schoolName.isNullOrBlank()){
                Toast.makeText(context,dataList.get(position).schoolName , Toast.LENGTH_SHORT).show()
            }
            true
        }

        holder.tv_degreeType.setOnLongClickListener{
            if(!dataList?.get(position)?.degreeName.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).degreeName , Toast.LENGTH_SHORT).show()
            }
            true
        }


        try {
            if(!dataList.get(position).schoolName.isNullOrBlank()){
                holder.tv_university.text = dataList.get(position).schoolName
            }else{
                holder.tv_university.text = "No data Provided"
            }


            if(!dataList.get(position).degreeName.isNullOrBlank()){
                holder.tv_degreeType.text = dataList.get(position).degreeName
            }else{
                holder.tv_degreeType.text = "Not Provided"
            }


//            val startDate =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    formatDate(dataList.get(position).startDate)
//                } else {
//                    formatDateForOlderVersions(dataList.get(position).startDate)
//                }
//
//            val endDate =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    formatDate(dataList.get(position).endDate)
//                } else {
//                    formatDateForOlderVersions(dataList.get(position).endDate)
//                }

            if(dataList.get(position).startDate !=null && dataList.get(position).endDate!=null){

                val startDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatDate(dataList.get(position).startDate)
                    } else {
                        formatDateForOlderVersions(dataList.get(position).startDate)
                    }

                val endDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatDate(dataList.get(position).endDate)
                    } else {
                        formatDateForOlderVersions(dataList.get(position).endDate)
                    }

                holder.tv_duration.text = startDate +" - "+endDate
            }else if (dataList.get(position).startDate !=null && dataList.get(position).endDate ==null){
                val startDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatDate(dataList.get(position).startDate)
                    } else {
                        formatDateForOlderVersions(dataList.get(position).startDate)
                    }
                holder.tv_duration.text = startDate

            } else if(dataList.get(position).startDate == null && dataList.get(position).endDate!=null){

                val endDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatDate(dataList.get(position).endDate)
                    } else {
                        formatDateForOlderVersions(dataList.get(position).endDate)
                    }

                holder.tv_duration.text = endDate
            }else{
                holder.tv_duration.text = "Not Provided"
            }
        }catch (e:Exception){
            Log.d("candidateSummary",e.toString())
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tv_university: TextView = itemView.findViewById(R.id.tv_schoolName)
        var tv_degreeType: TextView = itemView.findViewById(R.id.tv_degreeType)
        var tv_duration: TextView = itemView.findViewById(R.id.tv_duration_edu)
    }


}