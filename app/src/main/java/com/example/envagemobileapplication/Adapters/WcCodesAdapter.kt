package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R


class WcCodesAdapter(
    var context: Context,

    datalist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse.Datum>
) :
    RecyclerView.Adapter<WcCodesAdapter.MyViewHolder>() {


    var wcDataList = datalist


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WcCodesAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wc_codes_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if (!wcDataList.get(position).jobWcClassificationCode.toString().isNullOrEmpty()) {
            holder.wc_code.setText(wcDataList.get(position).jobWcClassificationCode).toString()
        } else {
            holder.wc_code.visibility = View.GONE
        }

        if (!wcDataList.get(position).areaCovered.toString().isNullOrEmpty()) {
            holder.wc_country.setText(wcDataList.get(position).areaCovered).toString()
        } else {
            holder.wc_country.visibility = View.GONE
        }

        if (!wcDataList.get(position).billingRate.toString().isNullOrEmpty()) {
            holder.wc_price.setText("$" + wcDataList.get(position).billingRate.toString())
        } else {
            holder.wc_price.visibility = View.GONE
        }

    }


    override fun getItemCount(): Int {
        return wcDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wc_country: TextView = itemView.findViewById(R.id.wc_countryt)
        var wc_code: TextView = itemView.findViewById(R.id.wc_code)
        var wc_price: TextView = itemView.findViewById(R.id.wc_price)


    }

}
