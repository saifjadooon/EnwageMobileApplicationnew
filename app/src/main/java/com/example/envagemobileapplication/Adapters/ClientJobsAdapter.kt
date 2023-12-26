package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetEditJob
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetJobsStatus
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants

class ClientJobsAdapter(
    var context: Context,
    onlineList: ArrayList<Record>,
    childFragmentManager: FragmentManager,
    jobsStatusList: ArrayList<Datum>
) :
    RecyclerView.Adapter<ClientJobsAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<Record> = onlineList
    var statusList = jobsStatusList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.client_jobs_item, parent, false)
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
                //    holder.tv_jobs_type.visibility = View.INVISIBLE
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
            if (dataList.get(position).minPayRate != null && dataList.get(position).maxPayRate != null) {
                holder.tv_salary.text = "$" +
                        dataList.get(position).minPayRate.toString() + " - " + "$" + dataList.get(
                    position
                ).maxPayRate.toString()
            } else {
                holder.tv_salary.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }

        val hexColorCode = dataList.get(position).colorCode
        val colore = Color.parseColor(hexColorCode)
        holder.iv_job_status.setTextColor(colore)

        holder.tv_dropdown.setColorFilter(colore)


        // Get the hex color code from your API (replace with your actual code)
        // Get the hex color code from your API (replace with your actual code)
        val hexxxColorCode = dataList.get(position).colorCode // Example hex color code

        val textColor = Color.parseColor(hexColorCode)

        // Calculate a lighter color (you can adjust the factor as needed)

        // Calculate a lighter color (you can adjust the factor as needed)
        val factor = 8.0f // 80% lighter, you can adjust this value

        val lighterColor: Int = adjustColorBrightness(textColor, factor)

        // Set the text color and background color of the TextView

        // Set the text color and background color of the TextView
        //   textView.setTextColor(textColor)
        //  holder.iv_job_status.setBackgroundColor(lighterColor)

        holder.jobs_menu.setOnClickListener {
        }
        holder.iv_job_status.setOnClickListener {
            Constants.isfromClientsummaryJobsList = true
            val bottomSheetFragment = BottomSheetJobsStatus()
            Constants.jobStatusList = statusList
            Constants.StatusClickedName = dataList.get(position).jobStatus.toString()
            Constants.StatusClickedJobId = dataList.get(position).jobId
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }

        holder.tv_position.setOnLongClickListener {


            val toast = Toast.makeText(
                context,
                dataList.get(position).positionName.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

        holder.jobs_menu.setOnClickListener {

            val bottomSheetFragment = BottomSheetEditJob()
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_position: TextView = itemView.findViewById(R.id.tv_position)
        var iv_job_status: TextView = itemView.findViewById(R.id.iv_job_status)
        var tv_salary: TextView = itemView.findViewById(R.id.tv_salary)
        var tv_jobs_time: TextView = itemView.findViewById(R.id.tv_jobs_time)
        var tv_jobs_type: TextView = itemView.findViewById(R.id.tv_jobs_type)
        var jobs_menu: ImageView = itemView.findViewById(R.id.jobs_menu)
        var tv_dropdown: ImageView = itemView.findViewById(R.id.tv_dropdown)


    }


    // Function to adjust color brightness
    private fun adjustColorBrightness(color: Int, factor: Float): Int {
        val alpha = Color.alpha(color)
        val red = Math.min(Math.round(Color.red(color) * factor), 255)
        val green = Math.min(Math.round(Color.green(color) * factor), 255)
        val blue = Math.min(Math.round(Color.blue(color) * factor), 255)
        return Color.argb(alpha, red, green, blue)
    }

}