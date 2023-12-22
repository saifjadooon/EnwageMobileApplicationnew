package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobsSummaryActivity
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetEditJob
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetJobsStatus
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Global
import com.squareup.picasso.Picasso


class JobsMainAdapter(
    var context: Context,
    onlineList: java.util.ArrayList<Record>,
    childFragmentManager: FragmentManager,
    jobsStatusList: ArrayList<Datum>
) :
    RecyclerView.Adapter<JobsMainAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<Record> = onlineList
    var statusList = jobsStatusList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.jobs_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            if (!dataList.get(position).positionName.isNullOrEmpty()) {
                holder.tv_position.text = dataList.get(position).positionName
            } else {
                holder.tv_position.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }
        try {
            if (!dataList.get(position).jobStatus.isNullOrEmpty()) {
                holder.iv_job_status.text = dataList.get(position).jobStatus
            } else {
                holder.iv_job_status.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }
        try {
            if (!dataList.get(position).jobNature.isNullOrEmpty()) {
                holder.tv_jobs_type.text = dataList.get(position).jobNature
            } else {
                holder.tv_jobs_type.text = "Not provided"
            }

        } catch (e: java.lang.Exception) {
        }
        try {
            if (!dataList.get(position).jobType.isNullOrEmpty()) {
                holder.tv_jobs_time.text = " - " + dataList.get(position).jobType
            } else {
                holder.tv_jobs_time.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }

        try {
            if (!dataList.get(position).clientName.isNullOrEmpty()) {

                holder.tv_client_name.text = dataList.get(position).clientName
            } else {
                holder.tv_client_name.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }


        holder.tv_client_name.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).clientName,
                Toast.LENGTH_LONG
            )


            toast.show()
            true
        }




        try {
            if (dataList.get(position).minPayRate != null) {
                holder.tv_salary.text = "$" +
                        dataList.get(position).minPayRate.toString() + " - " + "$" + dataList.get(
                    position
                ).maxPayRate.toString()
            } else {
                holder.tv_salary.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }
        try {
            if (dataList.get(position).clientProfilePicture != null) {
                Picasso.get().load(dataList.get(position).clientProfilePicture)
                    .placeholder(R.drawable.upload_pic_bg)
                    .into(holder.iv_profile_pic)
            }

        } catch (e: java.lang.Exception) {
        }

        try {
            val hexColorCode = dataList.get(position).colorCode
            val colore = Color.parseColor(hexColorCode)
            holder.iv_job_status.setTextColor(colore)
            holder.tv_dropdown.setColorFilter(colore)

        } catch (ex: Exception) {
        }

        holder.jobs_menu.setOnClickListener {

            val bottomSheetFragment = BottomSheetEditJob()
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }
        holder.iv_job_status.setOnClickListener {
            Constants.isfromClientsummaryJobsList = false
            val bottomSheetFragment = BottomSheetJobsStatus()
            Constants.jobStatusList = statusList
            Constants.StatusClickedName = dataList.get(position).jobStatus.toString()
            Constants.StatusClickedJobId = dataList.get(position).jobId
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }



        holder.tv_position.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                dataList.get(position).positionName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        holder.iv_job_status.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                dataList.get(position).jobStatus,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        holder.itemLayout.setOnClickListener {
            com.example.envagemobileapplication.Utils.Global.GlobalJobID =
                dataList.get(position).jobId
            Global.jobGuid = dataList.get(position).guid
            Global.jobtitle =
                dataList.get(position).positionName
            context.startActivity(Intent(context, JobsSummaryActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_position: TextView = itemView.findViewById(R.id.tv_position)
        var iv_job_status: TextView = itemView.findViewById(R.id.iv_job_status)
        var tv_client_name: TextView = itemView.findViewById(R.id.tv_client_name)
        var tv_salary: TextView = itemView.findViewById(R.id.tv_salary)
        var tv_jobs_time: TextView = itemView.findViewById(R.id.tv_jobs_time)
        var tv_jobs_type: TextView = itemView.findViewById(R.id.tv_jobs_type)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)
        var jobs_menu: ImageView = itemView.findViewById(R.id.jobs_menu)
        var tv_dropdown: ImageView = itemView.findViewById(R.id.tv_dropdown)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
    }
}