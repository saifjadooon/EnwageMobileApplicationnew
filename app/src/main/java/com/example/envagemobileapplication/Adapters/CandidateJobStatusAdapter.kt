package com.example.envagemobileapplication.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.AddClient.ClientSummaryActivity
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryF
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionEmployees
import com.example.envagemobileapplication.Models.RequestModels.UpdateJobStatusPayload
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCJobStatusRes.UpdateCJobStatusRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateStatusResponse.UpdateStatusResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Constants.Companion.jobId
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CandidateJobStatusAdapter(
    var context: Context,

    candidateJobStatusList: ArrayList<Datum>,

    sharedViewModel: CandidatesProfileSumViewModel
) :
    RecyclerView.Adapter<CandidateJobStatusAdapter.MyViewHolder>() {


    var statusList = candidateJobStatusList
    private var selectedPosition = -1

    lateinit var loader: Loader
    private var sharedViewModel = sharedViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.on_boarding_status_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        loader = Loader(context)
        holder.status_checkbox.setOnCheckedChangeListener(null);

        try {
            holder.tv_status_name.setText(statusList[position].statusName).toString()

        } catch (e: Exception) {
        }

        if (position == selectedPosition || Constants.StatusClickedName == statusList[position].statusName) {
            holder.cc_mainn.setBackgroundResource(R.drawable.onboarding_status_bg)
            holder.status_checkbox.isChecked = true
        } else {
            // Set the default background drawable for other items
            holder.cc_mainn.setBackgroundResource(R.drawable.searchbg)
            holder.status_checkbox.isChecked = false
        }
        holder.status_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                /* loader.show()*/
                val adapterPosition = holder.adapterPosition

                Constants.statusclickedColor = statusList[position].colorCode
                Constants.StatusClickedName = statusList[position].statusName
                val previousSelectedPosition = selectedPosition
                selectedPosition = holder.adapterPosition

                if(statusList.get(selectedPosition).candidateStatusId == Constants.candidateJobHiredId ){
                    Toast.makeText(context, "id matched", Toast.LENGTH_SHORT).show()
                }else{
                    callUpdateStatusApi(position,selectedPosition)
                }

//                callUpdateStatusApi(adapterPosition)

                //  notifyDataSetChanged()
            } else {
                Constants.StatusClickedName = "" // Clear the selected status name
            }
            holder.status_checkbox.post { notifyDataSetChanged() }
        }

    }

    private fun callUpdateStatusApi(position: Int,selectedPosition:Int) {

        val payload = UpdateJobStatusPayload(
            jobId = Constants.jobId, //ok
            candidateGUID = Constants.candidateId.toString(),
            statusId = statusList.get(selectedPosition).candidateStatusId.toString(),
            joiningDate = "",
            OfferedSalary = "",
            payRate = "",
            billRate = "",
            overtimePayRate = "",
            overtimeBillRate = "",
            doublePayRate = "0.00",
            doubleBillRate = "0.00",
        )


//        val payloadList: MutableList<UpdateJobStatusPayload> = ArrayList()
//        payloadList.add(payload)

        var tokenmanager: TokenManager = TokenManager(context)
        var token = tokenmanager.getAccessToken()
        try {

            ApiUtils.getAPIService(context).updateCandidateJobStatus(
                token.toString(),
                payload
            )
                .enqueue(object : Callback<UpdateCJobStatusRes> {
                    override fun onResponse(
                        call: Call<UpdateCJobStatusRes>,
                        response: Response<UpdateCJobStatusRes>
                    ) {
                        if (response.body() != null) {

                            try {

                                (context as Activity).finish()

                            } catch (e: Exception) {

                            }

                        }
                    }

                    override fun onFailure(call: Call<UpdateCJobStatusRes>, t: Throwable) {
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