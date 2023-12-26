package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnlineApplicantsAdapter(
    var context: Context,

    datalist: ArrayList<Record>,
    viewModel: JobSummaryCandidateViewModel
) :
    RecyclerView.Adapter<OnlineApplicantsAdapter.MyViewHolder>() {

    var global = com.example.envagemobileapplication.Utils.Global
    var loader = Loader(context)
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var viewmodel = viewModel
    var onlineApplicantsDataList = datalist


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnlineApplicantsAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.online_applicants_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        tokenManager = TokenManager(context)
        token = tokenManager.getAccessToken().toString()

        holder.tvCandidateName.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                onlineApplicantsDataList.get(position).firstName + " " + onlineApplicantsDataList.get(
                    position
                ).lastName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

        if (!onlineApplicantsDataList.get(position).firstName.toString().isNullOrEmpty()) {
            holder.tvCandidateName.setText(
                onlineApplicantsDataList.get(position).firstName + " " + onlineApplicantsDataList.get(
                    position
                ).lastName
            )
        } else {
            holder.tvCandidateName.text = "Not Provided"
        }

        if (!onlineApplicantsDataList.get(position).email.toString().isNullOrEmpty()) {
            holder.tvEmail.setText(onlineApplicantsDataList.get(position).email)
        } else {
            holder.tvEmail.text = "Not Provided"
        }

        holder.tvEmail.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                onlineApplicantsDataList.get(position).email,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        if (!onlineApplicantsDataList.get(position).phoneNumber.toString().isNullOrEmpty()) {
            val inputPhoneNumber = onlineApplicantsDataList.get(position).phoneNumber.toString()
            val formattedPhoneNumber = formatToUSAPhoneNumber(inputPhoneNumber)
            holder.tvPhoneNumber.setText(formattedPhoneNumber)

        } else {
            holder.tvPhoneNumber.text = "Not Provided"
        }

        if (!onlineApplicantsDataList.get(position).source.toString().isNullOrEmpty()) {
            holder.tvSource.setText("Source: " + onlineApplicantsDataList.get(position).source)
        } else {
            holder.tvSource.text = "Not Provided"
        }

        holder.kebabMenu.setOnClickListener {
            global.isfirstTimeFragmentLoaded = false
            var applicantId = onlineApplicantsDataList.get(position).applicantId
            viewmodel.showBottomSheet(applicantId)
        }

        holder.itemLayout.setOnClickListener {
            global.isfirstTimeFragmentLoaded = false
            loader.show()

            var applicantID = onlineApplicantsDataList.get(position).applicantId
            getAllSkills(context, token, applicantID, position)
        }

    }

    private fun getAllSkills(context: Context, token: String, applicantID: Int, position: Int) {
        try {

            ApiUtils.getAPIService(context).getAllSkills(
                applicantID!!,
                token,
            )
                .enqueue(object : Callback<GetAllSkillsResponse> {
                    override fun onResponse(
                        call: Call<GetAllSkillsResponse>,
                        response: Response<GetAllSkillsResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            com.example.envagemobileapplication.Utils.Global.AllOnlineSkills =
                                response.body()
                            com.example.envagemobileapplication.Utils.Global.AllOnlineSkillsPosition =
                                position
                            var jobID = onlineApplicantsDataList.get(position).jobId
                            com.example.envagemobileapplication.Utils.Global.position = position
                            viewmodel.showClientDetailBottomSheet(jobID)
                        }
                    }

                    override fun onFailure(call: Call<GetAllSkillsResponse>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())
                        loader.hide()

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
            loader.hide()
        }
    }


    override fun getItemCount(): Int {
        return onlineApplicantsDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCandidateName: TextView = itemView.findViewById(R.id.tvCandidateName)
        var tvSource: TextView = itemView.findViewById(R.id.tvSource)
        var tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        var tvPhoneNumber: TextView = itemView.findViewById(R.id.tvPhoneNumber)
        var kebabMenu: ImageView = itemView.findViewById(R.id.KebabMenu)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
    }

    fun formatToUSAPhoneNumber(inputPhoneNumber: String): String {
        val digitsOnly = inputPhoneNumber.replace(Regex("\\D"), "")

        if (digitsOnly.length == 10) {
            return digitsOnly.substring(0, 3) + "-" +
                    digitsOnly.substring(3, 6) + "-" +
                    digitsOnly.substring(6)
        } else {
            // Return the input as is if it doesn't match the expected format
            return digitsOnly
        }
    }

}
