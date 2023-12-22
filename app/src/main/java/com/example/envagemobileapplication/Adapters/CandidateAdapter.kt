package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.squareup.picasso.Picasso


class CandidateAdapter(
    var context: Context,
    onlineList: ArrayList<Record>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<CandidateAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<Record> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.candidate_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (dataList.get(position).firstName != null && dataList.get(position).lastName != null) {
            holder.tv_Name.setText(dataList.get(position).firstName + " " + dataList.get(position).lastName)
        }

        holder.tv_Name.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).firstName + " " + dataList.get(position).lastName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }


        if (!dataList.get(position).phoneNumber.isNullOrEmpty()) {
            val originalNumber =
                dataList.get(position).phoneNumber
            val formattedNumber = formatPhoneNumber(originalNumber)
            holder.tv_contact.text =
                Editable.Factory.getInstance().newEditable(formattedNumber)

        } else {
            holder.tv_contact.setText("Not provided")
        }
        if (!dataList.get(position).primaryEmail.isNullOrEmpty()) {
            holder.tv_email.setText(dataList.get(position).primaryEmail)
        } else {
            holder.tv_email.setText("Not provided")
        }

        if (!dataList.get(position).addressLine1.isNullOrEmpty() && !dataList.get(position).addressLine2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).addressLine1 + ", " + dataList.get(
                    position
                ).addressLine2
            )
        } else if (!dataList.get(position).addressLine1.isNullOrEmpty() && dataList.get(position).addressLine2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).addressLine1
            )
        } else if (dataList.get(position).addressLine1.isNullOrEmpty() && !dataList.get(position).addressLine2.isNullOrEmpty()) {
            holder.tv_home_adresss.setText(
                dataList.get(position).addressLine2
            )
        } else {
            holder.tv_home_adresss.setText(
                "Not provided"
            )
        }

        if (!
            dataList.get(position).profileImage.isNullOrEmpty()
        ) {

            Picasso.get().load(dataList.get(position).profileImage)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)

        } else {

        }


        if (!dataList.get(position).status.isNullOrEmpty()) {
            holder.iv_deal_status.setText(dataList.get(position).status)
        }

        if (!dataList.get(position).colorCode.isNullOrEmpty()) {
            val hexColorCode = dataList.get(position).colorCode
            val colore = Color.parseColor(hexColorCode)
            holder.iv_deal_status.setTextColor(colore)
            holder.tv_dropdown.setColorFilter(colore)
        }


    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tv_contact: TextView = itemView.findViewById(R.id.tv_contact)
        var tv_email: TextView = itemView.findViewById(R.id.tv_email)
        var tv_home_adresss: TextView = itemView.findViewById(R.id.tv_home_adresss)
        var iv_deal_status: TextView = itemView.findViewById(R.id.iv_deal_status)
        var tv_dropdown: ImageView = itemView.findViewById(R.id.tv_dropdown)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)


    }

    // Function to adjust color brightness
    private fun adjustColorBrightness(color: Int, factor: Float): Int {
        val alpha = Color.alpha(color)
        val red = Math.min(Math.round(Color.red(color) * factor), 255)
        val green = Math.min(Math.round(Color.green(color) * factor), 255)
        val blue = Math.min(Math.round(Color.blue(color) * factor), 255)
        return Color.argb(alpha, red, green, blue)
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val cleanedNumber = phoneNumber.replace("\\D".toRegex(), "") // Remove non-digits
        return if (cleanedNumber.length == 10) {
            "${cleanedNumber.substring(0, 3)}-${
                cleanedNumber.substring(
                    3,
                    6
                )
            }-${cleanedNumber.substring(6)}"
        } else {
            cleanedNumber // Return the cleaned number if it doesn't have 10 digits
        }
    }


}