package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Activities.Candidates.CandidateJobStatusChange
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants


class CandidateSummaryJobsAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummaryJobsRes.Datum>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<CandidateSummaryJobsAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummaryJobsRes.Datum> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_candidatesummary_jobs, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.tvJobName.setOnLongClickListener{
            if(!dataList?.get(position)?.positionName.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).positionName, Toast.LENGTH_SHORT).show()
            }
            true
        }

        holder.tvJobLocation.setOnLongClickListener{
            if(dataList?.get(position)?.location != "" || dataList?.get(position)?.location != null){
                Toast.makeText(context, dataList.get(position).location.toString(), Toast.LENGTH_SHORT).show()
            }

            true
        }

        holder.job_item.setOnClickListener{
            Constants.jobId = dataList.get(position).jobId
            val intent = Intent(context, CandidateJobStatusChange::class.java)
            context.startActivity(intent)
        }

        try{

            if(dataList.get(position).positionName !=null){
                holder.tvJobName.text = dataList.get(position).positionName
            }else{
                holder.tvJobName.text = "Not Provided"
            }


            if(dataList.get(position).clientName != null &&  dataList.get(position).jobType != null){
                holder.tvJobType.text = dataList.get(position).clientName + " - " + dataList.get(position).jobType
            } else if(dataList.get(position).clientName != null &&  dataList.get(position).jobType == null){
                holder.tvJobType.text = dataList.get(position).clientName
            }else if(dataList.get(position).clientName == null &&  dataList.get(position).jobType != null){
                holder.tvJobType.text = dataList.get(position).jobType
            }else{
                holder.tvJobType.text = "Not Provided"
            }


            if(dataList.get(position).status !=null){
                holder.tvJobStatus.text = dataList.get(position).status
            }else{
                holder.tvJobStatus.text = "Not Provided"
            }


//            holder.cr_jobPill.setBackgroundColor(dataList.get(position).colorCode.toInt())


            if(dataList?.get(position)?.location != null && dataList?.get(position)?.country !=null){
                    holder.tvJobLocation.text = dataList.get(position).location.toString() + ", "+ dataList.get(position).country.toString()
            }else if(dataList?.get(position)?.location !=null && dataList?.get(position)?.country ==null ){
                holder.tvJobLocation.text = dataList.get(position).location.toString()
            }else if( dataList?.get(position)?.location == null && dataList?.get(position)?.country!=null ){
                holder.tvJobLocation.text = dataList.get(position).country.toString()
            }else{
                holder.tvJobLocation.text = "Not Provided"
            }


            if(dataList.get(position).clientProfilePicture !=""){
                Glide.with(context)
                    .load(dataList?.get(position)?.clientProfilePicture)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.ivCandidateJobProfile)
            }

        }
        catch (e:Exception){
            Log.d("CandidateJobsExce",e.toString())
        }


//        holder.tvJobType.text = dataList.get(position)

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJobName: TextView = itemView.findViewById(R.id.tvJobName)
        var tvJobType: TextView = itemView.findViewById(R.id.tvJobType)
        var tvJobLocation: TextView = itemView.findViewById(R.id.tvJobLocation)
        var tvJobStatus: TextView = itemView.findViewById(R.id.tvJobStatus)
        var ivCandidateJobProfile :ImageView = itemView.findViewById(R.id.candidateJobProfile)
        var cr_jobPill :CardView = itemView.findViewById(R.id.cr_jobPill)
        var job_item :ConstraintLayout = itemView.findViewById(R.id.job_item)

    }


}