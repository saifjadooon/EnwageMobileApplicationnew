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
import com.example.envagemobileapplication.Utils.Constants

class CandidateSummaryExperienceAdapter(
    var context: Context,
    onlineList : ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.Datum>,
    childFragmentManager: FragmentManager
) : RecyclerView.Adapter<CandidateSummaryExperienceAdapter.MyViewHolder>() {


    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.Datum> = onlineList



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_candidatesummary_experience, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_positionName.setOnLongClickListener{
            if(!dataList?.get(position)?.positionName.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).positionName, Toast.LENGTH_SHORT).show()
            }
            true
        }


        holder.tv_employerName.setOnLongClickListener{
            if(!dataList?.get(position)?.employerName.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).employerName+" - "+dataList.get(position).employmentType, Toast.LENGTH_SHORT).show()
            }
            true
        }

        holder.tv_location.setOnLongClickListener{
            if(!dataList?.get(position)?.employerLocation.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).employerLocation, Toast.LENGTH_SHORT).show()
            }
            true
        }



        try {
            if(dataList.get(position).positionName!=null){
                holder.tv_positionName.text = dataList.get(position).positionName
            }else{
                holder.tv_positionName.text = "Not Provided"
            }

            if(dataList.get(position).employerName!=null && dataList.get(position).employmentType !=""){
                holder.tv_employerName.text = dataList.get(position).employerName + " - "+dataList.get(position).employmentType
            }
            else{
                holder.tv_employerName.text = dataList.get(position).employerName  + " - Not Provided"
            }


//            val startDate =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    Constants.formatDate(dataList.get(position).startDate)
//                } else {
//                    Constants.formatDateForOlderVersions(dataList.get(position).startDate)
//                }
//
//            val endDate =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    Constants.formatDate(dataList.get(position).endDate)
//                } else {
//                    Constants.formatDateForOlderVersions(dataList.get(position).endDate)
//                }
//
//            if(startDate !=null && endDate!=null){
//                holder.tv_duration.text = startDate +" - "+endDate
//            }else{
//                holder.tv_duration.text = "Not Provided"
//            }

            if(dataList.get(position).startDate !=null && dataList.get(position).endDate!=null){

                val startDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Constants.formatDate(dataList.get(position).startDate)
                    } else {
                        Constants.formatDateForOlderVersions(dataList.get(position).startDate)
                    }

                val endDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Constants.formatDate(dataList.get(position).endDate)
                    } else {
                        Constants.formatDateForOlderVersions(dataList.get(position).endDate)
                    }

                holder.tv_duration.text = startDate +" - "+endDate
            }else if (dataList.get(position).startDate !=null && dataList.get(position).endDate ==null){
                val startDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Constants.formatDate(dataList.get(position).startDate)
                    } else {
                        Constants.formatDateForOlderVersions(dataList.get(position).startDate)
                    }
                holder.tv_duration.text = startDate

            } else if(dataList.get(position).startDate == null && dataList.get(position).endDate!=null){

                val endDate =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Constants.formatDate(dataList.get(position).endDate)
                    } else {
                        Constants.formatDateForOlderVersions(dataList.get(position).endDate)
                    }

                holder.tv_duration.text = endDate
            }else{
                holder.tv_duration.text = "Not Provided"
            }




            if(dataList.get(position).employerLocation != "" || dataList.get(position).employerLocation != null ){
                holder.tv_location.text = dataList.get(position).employerLocation
            }else{
                holder.tv_location.text = "Not Provided"
            }
        }catch (e:Exception){
            Log.d("CandidateExperienceExce",e.toString())
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var tv_positionName: TextView = itemView.findViewById(R.id.tv_positionName)
        var tv_employerName: TextView = itemView.findViewById(R.id.tv_employerName)
        var tv_duration: TextView = itemView.findViewById(R.id.tv_duration)
        var tv_location: TextView = itemView.findViewById(R.id.tv_location)

    }


}