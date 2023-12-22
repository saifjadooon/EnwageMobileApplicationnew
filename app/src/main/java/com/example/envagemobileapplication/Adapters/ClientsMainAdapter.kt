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
import com.bumptech.glide.Glide
import com.example.envagemobileapplication.Activities.AddClient.ClientSummaryActivity
import com.example.envagemobileapplication.Fragments.BottomSheet.BottomSheetStatusFragment
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.squareup.picasso.Picasso

class ClientsMainAdapter(
    var context: Context,
    onlineList: java.util.ArrayList<Record>,
    childFragmentManager: FragmentManager,
    onBoardingStatusList: ArrayList<Datum>
) :
    RecyclerView.Adapter<ClientsMainAdapter.MyViewHolder>() {

    lateinit var loader: Loader
    var cfm = childFragmentManager
    var dataList: ArrayList<Record> = onlineList
    var statusList = onBoardingStatusList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.clients_item, parent, false)
        loader = Loader(context)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        initViews(holder, position)
        clickListeners(holder, position)
    }

    private fun initViews(holder: MyViewHolder, position: Int) {
        Constants.onBoardingStatusList = statusList
        try {
            val name = dataList.get(position).name.replace("\"", "")
            holder.tv_Name.text = name
        } catch (e: java.lang.Exception) {
        }
        try {dataList.get(position).industryName.equals("<null>")
            if (dataList[position].industryName != null && !dataList[position].industryName.equals("")) {
                holder.tv_company_category.text = dataList.get(position).industryName.toString()
            } else {
                holder.tv_company_category.text = "-"
                //   holder.tv_company_category.visibility = View.GONE
            }
        } catch (e: java.lang.Exception) {
        }
        try {

            if (dataList[position].ownerName != null) {
                holder.tv_company_owner_name.text = "Owner: " + dataList[position].ownerName
            } else {
                holder.tv_company_owner_name.text = "Not provided"
                //    holder.tv_company_owner_name.visibility = View.GONE
            }


        } catch (e: java.lang.Exception) {
        }
        try {
            if (dataList.get(position).location != null && !dataList.get(position).location.equals("")) {
                holder.tv_adress.text = dataList.get(position).location.toString()
            } else {
                holder.tv_adress.text = "Not provided"
                //      holder.tv_adress.visibility = View.GONE
            }

        } catch (e: java.lang.Exception) {
        }
        try {
            if (dataList.get(position).phone != null && !dataList.get(position).phone.equals("")) {
                val inputPhoneNumber =
                    dataList.get(position).phone.toString()
                val formattedPhoneNumber = formatToUSAPhoneNumber(inputPhoneNumber)

                holder.tv_contact.text = formattedPhoneNumber
            } else {
                holder.tv_contact.text = "Not provided"
                //  holder.tv_contact.visibility = View.GONE
            }

        } catch (e: Exception) {
        }
        try {
            if (!dataList.get(position).primaryAddress1.toString().isNullOrBlank() && !dataList.get(
                    position
                ).primaryAddress2.toString().isNullOrBlank()
            ) {

                holder.tv_home_adresss.text =
                    dataList.get(position).primaryAddress1.toString() + ", " + dataList.get(
                        position
                    ).primaryAddress2.toString()
            } else if (dataList.get(position).primaryAddress2.toString()
                    .isNullOrEmpty() && !dataList.get(position).primaryAddress1.isNullOrEmpty()
            ) {
                holder.tv_home_adresss.text =
                    dataList.get(position).primaryAddress1.toString()
            } else if (dataList.get(position).primaryAddress1.toString()
                    .isNullOrEmpty() && !dataList.get(position).primaryAddress2.isNullOrEmpty()
            ) {
                holder.tv_home_adresss.text =
                    dataList.get(position).primaryAddress2.toString()
            } else {
                holder.tv_home_adresss.text = "Not provided"
                //   holder.tv_home_adresss.visibility = View.GONE
            }


        } catch (e: Exception) {
        }
        try {
            Picasso.get().load(dataList.get(position).profilePicture)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)

        } catch (e: Exception) {
            Picasso.get().load(R.drawable.upload_pic_bg)
                .placeholder(R.drawable.upload_pic_bg).into(holder.iv_profile_pic)
        }

        try {

            if (dataList.get(position).visibilityStatus.equals("public")) {
                Glide.with(context)
                    .load(com.example.envagemobileapplication.R.drawable.ic_publicsvg) // Replace "your_drawable_resource" with the name of your drawable
                    // You can set width and height here
                    .into(holder.iv_status)
                /*  Picasso.get().load(R.drawable.pub)
                      .into(holder.iv_status)*/
            } else if (dataList.get(position).visibilityStatus.equals("private")) {
                Glide.with(context)
                    .load(com.example.envagemobileapplication.R.drawable.ic_privatesvg) // Replace "your_drawable_resource" with the name of your drawable

                    .into(holder.iv_status)
            } else {
                Glide.with(context)
                    .load(com.example.envagemobileapplication.R.drawable.ic_confidentailsvg) // Replace "your_drawable_resource" with the name of your drawable
                    // You can set width and height here
                    .into(holder.iv_status)
            }
        } catch (e: Exception) {
        }

        try {
            holder.iv_deal_status.text = dataList.get(position).onboardingStatus.toString()
            val hexColorCode = dataList.get(position).colorCode
            val colore = Color.parseColor(hexColorCode)
            holder.iv_deal_status.setTextColor(colore)
            // holder.tv_dropdown.setTextColor(colore)
            holder.tv_dropdown.setColorFilter(colore)

        } catch (ex: Exception) {
        }

    }

    private fun clickListeners(holder: MyViewHolder, position: Int) {
        holder.iv_deal_status.setOnClickListener {
            Constants.isOnboardingStatusUpdatedfromClientSummaryList = false
            val bottomSheetFragment = BottomSheetStatusFragment()
            Constants.onBoardingStatusList = statusList
            Constants.StatusClickedName = dataList.get(position).onboardingStatus.toString()
            Constants.StatusClickedClientId = dataList.get(position).clientId
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }
        holder.itemLayout.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.iv_profile_pic.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_Name.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_company_category.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_company_owner_name.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_contact.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_adress.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.tv_home_adresss.setOnClickListener {
            Constants.ownerName = dataList.get(position).owner
            Constants.clientid = dataList.get(position).clientId
            Constants.clientName = dataList.get(position).name
            context.startActivity(Intent(context, ClientSummaryActivity::class.java))
        }
        holder.iv_deal_status.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                dataList.get(position).onboardingStatus.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        holder.tv_Name.setOnLongClickListener {


            val toast = Toast.makeText(
                context,
                dataList.get(position).name.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        holder.tv_company_owner_name.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                dataList[position].ownerName,
                Toast.LENGTH_LONG
            )

            toast.show()

            true
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tv_company_category: TextView = itemView.findViewById(R.id.tv_company_category)
        var tv_company_owner_name: TextView = itemView.findViewById(R.id.tv_company_owner_name)
        var tv_contact: TextView = itemView.findViewById(R.id.tv_contact)
        var tv_adress: TextView = itemView.findViewById(R.id.tv_adress)
        var tv_home_adresss: TextView = itemView.findViewById(R.id.tv_home_adresss)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)
        var iv_status: ImageView = itemView.findViewById(R.id.iv_status)
        var iv_deal_status: TextView = itemView.findViewById(R.id.iv_deal_status)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
        var tv_dropdown: ImageView = itemView.findViewById(R.id.tv_dropdown)

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