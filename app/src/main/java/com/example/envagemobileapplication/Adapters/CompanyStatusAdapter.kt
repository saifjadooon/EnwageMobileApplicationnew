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
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateStatusResponse.UpdateStatusResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompanyStatusAdapter(
    var context: Context,

    onBoardingStatusList: ArrayList<Datum>,

    sharedViewModel: MainActivityViewModel
) :
    RecyclerView.Adapter<CompanyStatusAdapter.MyViewHolder>() {


    var statusList = onBoardingStatusList
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
            holder.tv_status_name.setText(statusList[position].onboardingStatusName).toString()

        } catch (e: Exception) {
        }

        if (position == selectedPosition || Constants.StatusClickedName == statusList[position].onboardingStatusName) {
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
                Constants.StatusClickedName = statusList[position].onboardingStatusName
                val previousSelectedPosition = selectedPosition
                selectedPosition = holder.adapterPosition
                callUpdateStatusApi(adapterPosition)

                //  notifyDataSetChanged()
            } else {
                Constants.StatusClickedName = "" // Clear the selected status name
            }
            holder.status_checkbox.post { notifyDataSetChanged() }
        }

    }

    private fun callUpdateStatusApi(position: Int) {

        val clientId =
            Constants.StatusClickedClientId // Replace with the actual client ID
        var statuid = statusList.get(position).companyOnboardingStatusId.toString()

        val payload = UpdateStatusPayload("add", "/onboardingStatusId", statuid)

        val payloadList: MutableList<UpdateStatusPayload> = ArrayList()
        payloadList.add(payload)

        var tokenmanager: TokenManager = TokenManager(context)
        var token = tokenmanager.getAccessToken()
        try {
            ApiUtils.getAPIService(context).updateStatus(
                token.toString(), payloadList, clientId!!
            )
                .enqueue(object : Callback<UpdateStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateStatusResponse>,
                        response: Response<UpdateStatusResponse>
                    ) {
                        if (response.body() != null) {


                            var bundle: Bundle = Bundle()
                            try {
                                ClientSummaryF.onboardingStatus.text = Constants.StatusClickedName

                                val hexColorCode = Constants.statusclickedColor
                                val colore = Color.parseColor(hexColorCode)
                                ClientSummaryF.onboardingStatus.setTextColor(colore)

                                ClientSummaryF.tvDropdown.setColorFilter(colore)
                                val intent = Intent(context, ClientSummaryActivity::class.java)
                                context.startActivity(intent)
                                (context as Activity).finish()

                                /*   sharedViewModel.replaceFragmentclientsSummary(
                                       ClientSummaryF(),
                                       bundle
                                   )*/
                            } catch (e: Exception) {

                            }

                            /*var bundle: Bundle = Bundle()
                            sharedViewModel.replaceFragmentclientsSummary(
                                ClientSummaryF(),
                                bundle
                            )
*/
                            var isfromCompnyStatus = true
                            var tokenManager: TokenManager = TokenManager(context)

                            var Clientsbundle = Bundle()
                            //  sharedViewModel.replaceFragment(ClientsF(), Clientsbundle)
                            /*          val model = sortDirection(
                                          pageIndex = 1,
                                           pageSize = 25,
                                          sortBy = "CreatedDate",
                                          sortDirection = 1,
                                          searchText = "",
                                          tileStatusId = -1
                                      )
                                      sharedViewModel.getClients(
                                          context as Activity,
                                          tokenManager.getAccessToken(),
                                          model, isfromCompnyStatus
                                      )*/

                            val toast = Toast.makeText(
                                context,
                                "Client Onboarding status has been updated successfully.",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }

                    override fun onFailure(call: Call<UpdateStatusResponse>, t: Throwable) {
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