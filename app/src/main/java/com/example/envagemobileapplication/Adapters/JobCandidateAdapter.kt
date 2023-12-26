package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.squareup.picasso.Picasso

class JobCandidateAdapter(
    var context: Context,
    datalist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.Record>,
    viewModel: JobSummaryCandidateViewModel
) :
    RecyclerView.Adapter<JobCandidateAdapter.MyViewHolder>() {

    var global = com.example.envagemobileapplication.Utils.Global
    var loader = Loader(context)
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var viewmodel = viewModel
    var onlineApplicantsDataList = datalist


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JobCandidateAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.job_candidate_iten, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        tokenManager = TokenManager(context)
        token = tokenManager.getAccessToken().toString()

        holder.tv_Name.setOnLongClickListener {

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

        if (onlineApplicantsDataList.get(position).profileImage != "") {


            Picasso.get().load(onlineApplicantsDataList.get(position).profileImage)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)
        } else {
            Picasso.get().load(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)
        }

        if (!onlineApplicantsDataList.get(position).firstName.toString().isNullOrEmpty()) {
            holder.tv_Name.setText(
                onlineApplicantsDataList.get(position).firstName + " " + onlineApplicantsDataList.get(
                    position
                ).lastName
            )
        } else {
            holder.tv_Name.text = "Not Provided"
        }

        if (!onlineApplicantsDataList.get(position).primaryEmail.toString().isNullOrEmpty()) {
            holder.tvEmail.setText(onlineApplicantsDataList.get(position).primaryEmail)
        } else {
            holder.tvEmail.text = "Not Provided"
        }

        holder.tvEmail.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                onlineApplicantsDataList.get(position).primaryEmail,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        if (!onlineApplicantsDataList.get(position).phoneNumber.toString().isNullOrEmpty()) {
            val inputPhoneNumber = onlineApplicantsDataList.get(position).phoneNumber.toString()
            val formattedPhoneNumber = formatToUSAPhoneNumber(inputPhoneNumber)
            holder.tv_contact_phone.setText(formattedPhoneNumber)

        } else {
            holder.tv_contact_phone.text = "Not Provided"
        }

        if (!onlineApplicantsDataList.get(position).source.toString().isNullOrEmpty()) {
            for (i in 0 until onlineApplicantsDataList.get(position).candidateJobs.size) {
                holder.tv_type.setText(onlineApplicantsDataList.get(position).candidateJobs.get(i).status)
            }

        } else {
            holder.tv_type.text = "Not Provided"
        }

        holder.iv_menu.setOnClickListener {
            var candidateid = onlineApplicantsDataList.get(position).candidateId
            var jobid = ""
            for (i in 0 until onlineApplicantsDataList.get(position).candidateJobs.size) {

                jobid = onlineApplicantsDataList.get(position).candidateJobs.get(i).jobId.toString()
                var jobGuid = onlineApplicantsDataList.get(position).guid
                global.candidateIdForOfferLetter = candidateid
                global.jobidForOfferLetter = jobid
                global.jobGuidforOfferLetter = jobGuid.toString()
                global.firstnameforofferletter =
                    onlineApplicantsDataList.get(position).firstName

                var firstnameeeee = onlineApplicantsDataList.get(position).firstName
                global.fn = firstnameeeee
                var lastnameee = onlineApplicantsDataList.get(position).lastName
                global.ln = lastnameee

                global.lastnameforofferletter =
                    onlineApplicantsDataList.get(position).lastName
                global.candidateEmailAdress = onlineApplicantsDataList.get(position).primaryEmail

                var statusid = onlineApplicantsDataList.get(position).candidateJobs.get(i).statusId

                try {
                    var phonenumber = onlineApplicantsDataList.get(position).phoneNumber
                    global.composeMessagePhoneNumber = phonenumber
                } catch (e: Exception) {
                }

                if (statusid == Constants.candidateInterviewdId) {
                    viewmodel.showJobCandidateKebabmenuBottomSheet(true)
                } else {
                    viewmodel.showJobCandidateKebabmenuBottomSheet(false)
                }
            }
        }

        holder.itemLayout.setOnClickListener {


            var candidateid = onlineApplicantsDataList.get(position).candidateId
            var jobid = ""
            for (i in 0 until onlineApplicantsDataList.get(position).candidateJobs.size) {

                jobid = onlineApplicantsDataList.get(position).candidateJobs.get(i).jobId.toString()
            }

            var jobGuid = onlineApplicantsDataList.get(position).guid
            global.candidateIdForOfferLetter = candidateid
            global.jobidForOfferLetter = jobid
            global.jobGuidforOfferLetter = jobGuid.toString()
            global.firstnameforofferletter = onlineApplicantsDataList.get(position).firstName
            global.lastnameforofferletter = onlineApplicantsDataList.get(position).lastName
            global.candidateEmailAdress = onlineApplicantsDataList.get(position).primaryEmail
        }

    }

    override fun getItemCount(): Int {
        return onlineApplicantsDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tv_type: TextView = itemView.findViewById(R.id.tv_type)
        var tvEmail: TextView = itemView.findViewById(R.id.tv_contact_phone_email)
        var tv_contact_phone: TextView = itemView.findViewById(R.id.tv_contact_phone)
        var iv_menu: ImageView = itemView.findViewById(R.id.iv_menu)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)
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
