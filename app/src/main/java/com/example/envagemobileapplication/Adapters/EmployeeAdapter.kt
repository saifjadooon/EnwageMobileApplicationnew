package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.squareup.picasso.Picasso

class EmployeeAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.Record>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.Record> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {

            holder.tv_emp_name.setOnLongClickListener {
                Toast.makeText(context, dataList.get(position).firstName +" "+dataList.get(position).lastName , Toast.LENGTH_SHORT).show()
                true
            }

            holder.tv_emp_email.setOnLongClickListener {
                if(dataList.get(position).email.length >0){
                    Toast.makeText(context, dataList.get(position).email , Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Not Provided", Toast.LENGTH_SHORT).show()
                }

                true
            }

            holder.tv_emp_address1.setOnLongClickListener {
                if(dataList.get(position).addressLine1.length >0){
                    Toast.makeText(context, dataList.get(position).addressLine1+" "+dataList.get(position).addressLine2 , Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Not Provided", Toast.LENGTH_SHORT).show()
                }

                true
            }

            holder.tv_emp_address.setOnLongClickListener {
                if(dataList.get(position).country.toString().length >0){
                    Toast.makeText(context, dataList.get(position).country.toString() , Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Not Provided", Toast.LENGTH_SHORT).show()
                }

                true
            }

            holder.tv_emp_name.text = dataList.get(position).firstName +" "+ dataList.get(position).lastName

            if(dataList.get(position).email!= null){
                holder.tv_emp_email.text = dataList.get(position).email
            }

            if(dataList.get(position).phoneNumber!= null){

                val formatedPhoneNo = formatPhoneNumber(dataList.get(position).phoneNumber)
                holder.tv_emp_contact.text = formatedPhoneNo
            }

            if(dataList.get(position).country!= null){
                holder.tv_emp_address.text = dataList.get(position).country.toString()
            }

            if(dataList.get(position).addressLine1!= ""  && dataList.get(position).addressLine2!= ""  ){
                holder.tv_emp_address1.text = dataList.get(position).addressLine1 +" "+ dataList.get(position).addressLine2
            } else if(dataList.get(position).addressLine1 != "" && dataList.get(position).addressLine2 == "" ){
                holder.tv_emp_address1.text = dataList.get(position).addressLine1
            }else if(dataList.get(position).addressLine1 == "" && dataList.get(position).addressLine2 != "" ) {
                holder.tv_emp_address1.text = dataList.get(position).addressLine2
            }else{
                holder.tv_emp_address1.text = "Not Provided"
            }


            if (
                dataList.get(position).gender !=null
            ) {

                if(dataList.get(position).gender.equals("Male")){
                    val maleIcon = R.drawable.ic_male
                    holder.iv_gender.setImageResource(maleIcon)
                }else{

                    val femaleIcon = R.drawable.ic_female
                    holder.iv_gender.setImageResource(femaleIcon)
                }

            }

            if (!
                dataList.get(position).profileImage.isNullOrEmpty()
            ) {

                Picasso.get().load(dataList.get(position).profileImage)
                    .placeholder(R.drawable.upload_pic_bg)
                    .transform(CircleTransformation()).into(holder.emp_profile_pic)

            }


        }catch (e:Exception){
            Log.d("employeeException",e.toString())
        }



    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_emp_name: TextView = itemView.findViewById(R.id.tv_emp_name)
        var tv_emp_contact: TextView = itemView.findViewById(R.id.tv_emp_contact)
        var tv_emp_email: TextView = itemView.findViewById(R.id.tv_emp_email)
        var tv_emp_address: TextView = itemView.findViewById(R.id.tv_emp_address)
        var tv_emp_address1: TextView = itemView.findViewById(R.id.tv_emp_address1)
        var emp_profile_pic: ImageView = itemView.findViewById(R.id.emp_profile_pic)
          var iv_gender: ImageView = itemView.findViewById(R.id.iv_gender)


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