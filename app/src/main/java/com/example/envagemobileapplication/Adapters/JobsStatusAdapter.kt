package com.example.envagemobileapplication.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateJobsStatusResponse.UpdateJobsStatusResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JobsStatusAdapter(
    var context: Context,

    onBoardingStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum>,

    sharedViewModel: MainActivityViewModel
) :
    RecyclerView.Adapter<JobsStatusAdapter.MyViewHolder>() {


    var statusList = onBoardingStatusList
    private var selectedPosition = -1

    private var sharedViewModel = sharedViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.on_boarding_status_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.status_checkbox.setOnCheckedChangeListener(null);
        if (Constants.StatusClickedName == statusList.get(position).statusName) {
            holder.status_checkbox.isChecked = true;
            holder.cc_mainn.setBackgroundResource(R.drawable.onboarding_status_bg) // Set the background color to red (or any color you prefer)


        } else {
            holder.status_checkbox.isChecked = false
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        try {
            holder.tv_status_name.setText(statusList[position].statusName).toString()

        } catch (e: Exception) {
        }

        holder.status_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                val adapterPosition = holder.adapterPosition

                Constants.StatusClickedName = statusList[position].statusName
                val previousSelectedPosition = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedPosition)

                holder.tv_status_name.setText(Constants.StatusClickedName).toString()
                callUpdateStatusApi(adapterPosition)

            } else {

                Constants.StatusClickedName = "" // Clear the selected status name

            }
            // Notify the adapter to update the UI without causing the IllegalStateException
            holder.status_checkbox.post { notifyDataSetChanged() }
        }


        holder.tv_status_name.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                statusList[position].statusName
                    .toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

    }

    private fun callUpdateStatusApi(position: Int) {

        val clientId =
            Constants.StatusClickedJobId // Replace with the actual client ID
        var statuid = statusList.get(position).jobStatusId.toString()


        val payload = UpdateStatusPayload("add", "/jobStatusId", statuid)

        val payloadList: MutableList<UpdateStatusPayload> = ArrayList()
        payloadList.add(payload)


        var tokenmanager: TokenManager = TokenManager(context)
        var token = tokenmanager.getAccessToken()
        try {
            ApiUtils.getAPIService(context).updateJobStatus(
                token.toString(), payloadList, clientId!!


            )
                .enqueue(object : Callback<UpdateJobsStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateJobsStatusResponse>,
                        response: Response<UpdateJobsStatusResponse>
                    ) {
                        if (response.body() != null) {
                            var tokenManager: TokenManager = TokenManager(context)
                            val model = SortDirectionJobs(
                                clientId = null,
                                jobFilters = emptyList(),
                                pageIndex = 1,
                                 pageSize = 25,
                                searchText = "",
                                sortBy = "CreatedDate",
                                sortDirection = 1,
                                tileStatusId = -1
                            )
                            var isfromJobBottomSheet = true
                            sharedViewModel.getJobs(
                                context as Activity,
                                tokenManager.getAccessToken(),
                                model, isfromJobBottomSheet
                            )


                            val toast = Toast.makeText(
                                context,
                                "Job status updated successfully.",
                                Toast.LENGTH_LONG
                            )

                            toast.show()


                        }
                    }

                    override fun onFailure(call: Call<UpdateJobsStatusResponse>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }

    }


    override fun getItemCount(): Int {
        return statusList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_status_name: TextView = itemView.findViewById(R.id.tv_status_name)
        var status_checkbox: RadioButton = itemView.findViewById(R.id.status_checkbox)
        var cc_mainn: ConstraintLayout = itemView.findViewById(R.id.cc_mainn)


    }

}