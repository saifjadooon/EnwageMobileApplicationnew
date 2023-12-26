package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.RequestModels.ClientInfo
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList.Datum
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.ViewModels.AddJobsSharedViewModel


class CleintListAdapter(

    var context: Context,
    onlineList: ArrayList<Datum>,
    viewModel: AddJobsSharedViewModel
) :
    RecyclerView.Adapter<CleintListAdapter.MyViewHolder>() {

    var viewmodel = viewModel
    var dataList: ArrayList<Datum> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.clientlist_item_add_job, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (dataList.get(position).name != null) {
            holder.tv_Name.setText(dataList.get(position).name)
        }

        holder.tv_Name.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).name,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }

        holder.itemlayout.setOnClickListener {
            var selectedClientname = holder.tv_Name.text.toString()
            var clientid = dataList.get(position).clientId
            val clientInfo = ClientInfo(selectedClientname, clientid)
            viewmodel.dismissBottomsheet(clientInfo)


        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tvClientName)
        var itemlayout: ConstraintLayout = itemView.findViewById(R.id.itemlayout)
    }


}