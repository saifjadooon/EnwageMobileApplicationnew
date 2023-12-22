package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomsheetContactDetail
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class ClientContactsAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.Record>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ClientContactsAdapter.MyViewHolder>() {

    private val bottomSheetDialog: BottomSheetDialog? = null
    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.Record> =
        onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.client_contacts_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        initdata(holder, position)
        clicklistenrs(holder, position)


    }

    private fun clicklistenrs(holder: ClientContactsAdapter.MyViewHolder, position: Int) {
        var bottomSheetFragment = BottomsheetContactDetail()
        holder.itemLayout.setOnClickListener {
            if (bottomSheetFragment.isAdded()) {
                return@setOnClickListener
            } else {
                Constants.clientPocId = dataList.get(position).clientPocId
                bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
            }
        }

    }

    private fun initdata(holder: MyViewHolder, position: Int) {


        if (!dataList.get(position).primaryFirstName.isNullOrEmpty() && !dataList.get(position).primaryLastName.isNullOrEmpty()) {
            holder.tv_Name.setText(
                dataList.get(position).primaryFirstName + " " + dataList.get(
                    position
                ).primaryLastName
            )
        } else if (!dataList.get(position).primaryFirstName.isNullOrEmpty() && dataList.get(position).primaryLastName.isNullOrEmpty()) {
            holder.tv_Name.setText(
                dataList.get(position).primaryFirstName
            )
        } else if (dataList.get(position).primaryFirstName.isNullOrEmpty() && !dataList.get(position).primaryLastName.isNullOrEmpty()) {
            holder.tv_Name.setText(
                dataList.get(position).primaryLastName
            )
        }

        if (!dataList.get(position).contactDepartment.isNullOrEmpty() && !dataList.get(position).contactDepartment.equals(
                "null"
            )
        ) {
            holder.tv_contact_phone_department.setText(dataList.get(position).contactDepartment)
        } else {
            holder.tv_contact_phone_department.setText("-")
        }
        if (!dataList.get(position).primaryEmail.isNullOrEmpty() && !dataList.get(position).primaryEmail.equals(
                "null"
            )
        ) {
            holder.tv_contact_mail.setText(dataList.get(position).primaryEmail)
        } else {
            holder.tv_contact_mail.setText("Not provided")
        }

        if (!dataList.get(position).primaryPhoneNumber.isNullOrEmpty() && !dataList.get(position).primaryPhoneNumber.equals(
                "null"
            )
        ) {
            val inputPhoneNumber = dataList.get(position).primaryPhoneNumber.toString()
            val formattedPhoneNumber = formatToUSAPhoneNumber(inputPhoneNumber)
            holder.tv_contact_phone.setText(formattedPhoneNumber)
        } else {
            holder.tv_contact_phone.setText("Not provided")
        }

        if (!dataList.get(position).primaryLocation.isNullOrEmpty() && !dataList.get(position).primaryLocation.equals(
                "null"
            )
        ) {
            holder.tv_location.setText(dataList.get(position).primaryLocation)
        } else {
            holder.tv_location.setText("Not provided")
        }
        if (!dataList.get(position).primaryAddress1.isNullOrEmpty() && !dataList.get(position).primaryAddress2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).primaryAddress1 + ", " + dataList.get(
                    position
                ).primaryAddress2
            )
        } else if (!dataList.get(position).primaryAddress1.isNullOrEmpty() && dataList.get(position).primaryAddress2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).primaryAddress1
            )
        } else if (dataList.get(position).primaryAddress1.isNullOrEmpty() && !dataList.get(position).primaryAddress2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).primaryAddress2
            )
        } else {
            holder.tv_home_adresss.setText(
                "Not provided"
            )
        }

        if (!dataList.get(position).createdDate.isNullOrEmpty() && !dataList.get(position).createdDate.equals(
                "null"
            )
        ) {
            val inputDate = dataList.get(position).createdDate
            val formattedDate = formatDate(inputDate)
            println(formattedDate) // Output: 04/17/2023
            holder.tv_createdat.setText("Created on: " + formattedDate)
        } else {
            holder.tv_createdat.setText("Created on: " + "-")
        }
        if (dataList.get(position).isPrimaryContact) {
            holder.tv_type.setText("Primary")
        } else {
            holder.tv_type.setText("Billing")
        }

        if (dataList.get(position).primaryGender != null
        ) {

            if (dataList.get(position).primaryGender.equals("Male")) {
                holder.iv_gender.setImageDrawable(context.resources.getDrawable(R.drawable.ic_female))
            } else if (dataList.get(position).primaryGender.equals("Female")) {
                holder.iv_gender.setImageDrawable(context.resources.getDrawable(R.drawable.ic_male))
            } else if (dataList.get(position).primaryGender.equals("Other")) {
                holder.iv_gender.setImageDrawable(context.resources.getDrawable(R.drawable.ic_othergender))
            } else {
                holder.iv_gender.visibility = View.INVISIBLE
            }
        } else {
            holder.iv_gender.visibility = View.INVISIBLE
        }

        if (!dataList.get(position).primaryProfileImage.isNullOrEmpty()) {
            Picasso.get().load(dataList.get(position).primaryProfileImage)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)
        } else {
            Picasso.get().load(R.drawable.upload_pic_bg)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation())
                .into(holder.iv_profile_pic)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tv_type: TextView = itemView.findViewById(R.id.tv_type)
        var tv_contact_phone_department: TextView =
            itemView.findViewById(R.id.tv_contact_phone_department)
        var tv_contact_mail: TextView = itemView.findViewById(R.id.tv_contact_phone_email)
        var tv_contact_phone: TextView = itemView.findViewById(R.id.tv_contact_phone)
        var tv_location: TextView = itemView.findViewById(R.id.tv_location)
        var tv_home_adresss: TextView = itemView.findViewById(R.id.tv_home_adresss)
        var tv_createdat: TextView = itemView.findViewById(R.id.tv_createdat)
        var iv_gender: ImageView = itemView.findViewById(R.id.iv_gender)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)


    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val outputFormat = SimpleDateFormat("MM-dd-yyyy")

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle parsing error here
        }
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