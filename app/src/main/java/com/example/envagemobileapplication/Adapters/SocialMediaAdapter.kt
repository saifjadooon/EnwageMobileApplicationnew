package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayloadforSocialMediacheckbox
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientSocialMedium
import com.example.envagemobileapplication.Models.SocialMediaResponse.SocialMediaResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialMediaAdapter(
    var context: Context,
    onlineList: ArrayList<ClientSocialMedium>

) :
    RecyclerView.Adapter<SocialMediaAdapter.MyViewHolder>() {

    private var invalidurl: Boolean = false
    lateinit var loader: Loader
    var dataList: ArrayList<ClientSocialMedium> = onlineList
    lateinit var payloadList: MutableList<UpdateStatusPayload>
    lateinit var payloadListforsocialmediaCheckbox: MutableList<UpdateStatusPayloadforSocialMediacheckbox>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_social_media, parent, false)
        loader = Loader(context)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.checkBox.isChecked = dataList.get(position).isActive

        holder.iv_edit.setOnClickListener {
            holder.et_enter_url.visibility = View.VISIBLE
            holder.iv_edit.visibility = View.INVISIBLE
            holder.checkBox.visibility = View.GONE
            holder.cc_tic_cross.visibility = View.VISIBLE
        }

        holder.et_enter_url.setOnTouchListener(View.OnTouchListener { v, event ->
            holder.et_enter_url.visibility = View.VISIBLE
            holder.iv_edit.visibility = View.INVISIBLE
            holder.checkBox.visibility = View.GONE
            holder.cc_tic_cross.visibility = View.VISIBLE
            false
        })


        holder.iv_cross.setOnClickListener {

            holder.iv_edit.visibility = View.VISIBLE
            holder.checkBox.visibility = View.VISIBLE
            holder.cc_tic_cross.visibility = View.INVISIBLE
        }

        holder.iv_tic.setOnClickListener {

            if (invalidurl == false) {
                val onboardingstatus = UpdateStatusPayload(
                    "add",
                    "/Url",
                    holder.et_enter_url.text.toString()
                )


                var socialmediaid = dataList.get(position).clientSocialMediaId
                callUpdateClientApi(
                    onboardingstatus,
                    socialmediaid,
                    holder.checkBox,
                    holder.cc_tic_cross
                )
            }

        }

        holder.checkBox.setOnClickListener {
            if (!dataList.get(position).isActive) {
                holder.checkBox.isChecked = false
                val onboardingstatus = UpdateStatusPayloadforSocialMediacheckbox(
                    "add",
                    "/isActive",
                    true
                )
                var socialmediaid = dataList.get(position).clientSocialMediaId

                callUpdateClientApisocialMediaCheckbox(
                    onboardingstatus,
                    socialmediaid,
                    holder.checkBox,
                    holder.cc_tic_cross
                )


                dataList.get(position).isActive = true
                notifyDataSetChanged()
            }
            else {
                holder.checkBox.isChecked = false
                val onboardingstatus = UpdateStatusPayloadforSocialMediacheckbox(
                    "add",
                    "/isActive",
                    false
                )
                var socialmediaid = dataList.get(position).clientSocialMediaId

                callUpdateClientApisocialMediaCheckbox(
                    onboardingstatus,
                    socialmediaid,
                    holder.checkBox,
                    holder.cc_tic_cross
                )
                dataList.get(position).isActive = false
                notifyDataSetChanged()
            }
        }

        holder.et_enter_url.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    holder.et_enter_url.error = null
                } else if (isValidUrl(enteredText)) {
                    // URL is valid, you can update UI or perform other actions
                    holder.et_enter_url.error = null // Clear any previous error
                } else {

                    holder.et_enter_url.setError("Enter valid url");
                    /*         holder.et_enter_url.setErrorIconDrawable(null)// Set the error message
                             binding.clientWebsite.setErrorTextAppearance(R.style.ErrorText);*/
                    // URL is not valid, display an error message
                    /*binding.etClientWebsite.error = "Invalid URL"*/
                }

            }
        })

        if (dataList.get(position).name.equals("Facebook")) {
            holder.iv_social_media_image.setImageDrawable(context.resources.getDrawable(R.drawable.ic_facebook_colored))

        } else if (dataList.get(position).name.equals("Twitter")) {
            holder.iv_social_media_image.setImageDrawable(context.resources.getDrawable(R.drawable.ic_twitter_colored))
        } else if (dataList.get(position).name.equals("LinkedIn")) {
            holder.iv_social_media_image.setImageDrawable(context.resources.getDrawable(R.drawable.ic_linkedin_coloredd))

        } else if (dataList.get(position).name.equals("Instagram")) {
            holder.iv_social_media_image.setImageDrawable(context.resources.getDrawable(R.drawable.ic_instagram_colored))
        }
        if (!dataList.get(position).url.isNullOrEmpty()) {
            holder.et_enter_url.text =
                Editable.Factory.getInstance().newEditable(dataList.get(position).url)
        }
        holder.et_enter_url.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    invalidurl = false
                    holder.et_enter_url.error = null
                } else if (isValidUrl(enteredText)) {
                    // URL is valid, you can update UI or perform other actions
                    invalidurl = false
                    holder.et_enter_url.error = null // Clear any previous error
                } else {
                    invalidurl = true
                    holder.et_enter_url.setError("Enter valid url");
                }

            }
        })

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_social_media_image: ImageView = itemView.findViewById(R.id.iv_social_media_image)

        //   var tv_url: TextView = itemView.findViewById(R.id.tv_url)
        var iv_edit: ImageView = itemView.findViewById(R.id.iv_edit)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var et_enter_url: EditText = itemView.findViewById(R.id.et_enter_url)
        var cc_tic_cross: ConstraintLayout = itemView.findViewById(R.id.cc_tic_cross)
        var iv_cross: ImageView = itemView.findViewById(R.id.iv_cross)
        var iv_tic: ImageView = itemView.findViewById(R.id.iv_tic)


        init {

        }
    }

    private fun callUpdateClientApi(
        patch: UpdateStatusPayload,
        socialmediaid: Int,
        position: CheckBox,
        ccTicCross: ConstraintLayout
    ) {
        try {
            loader.show()
            payloadList = ArrayList()
            payloadList.add(patch)
            var tokenmanager: TokenManager = TokenManager(context)
            var token = tokenmanager.getAccessToken()
            var socialmediaid = socialmediaid

            ApiUtils.getAPIService(context).UpdateClientSocialMedia(
                token.toString(), payloadList, socialmediaid
            )
                .enqueue(object : Callback<SocialMediaResponse> {
                    override fun onResponse(
                        call: Call<SocialMediaResponse>,
                        response: Response<SocialMediaResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            loader.hide()
                            ccTicCross.visibility = View.GONE
                            position.visibility = View.VISIBLE
                            val toast = Toast.makeText(
                                context,
                                "Client information has been updated successfully",
                                Toast.LENGTH_LONG
                            )

                            toast.show()
                        }
                    }

                    override fun onFailure(
                        call: Call<SocialMediaResponse>,
                        t: Throwable
                    ) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun callUpdateClientApisocialMediaCheckbox(
        patch: UpdateStatusPayloadforSocialMediacheckbox,
        socialmediaid: Int,
        position: CheckBox,
        ccTicCross: ConstraintLayout
    ) {
        try {

            loader.show()
            // binding.loader.visibility = View.VISIBLE
            payloadListforsocialmediaCheckbox = ArrayList()
            payloadListforsocialmediaCheckbox.add(patch)


            var tokenmanager: TokenManager = TokenManager(context)
            var token = tokenmanager.getAccessToken()
            var socialmediaid = socialmediaid

            ApiUtils.getAPIService(context).UpdateClientSocialMediaCheckbox(
                token.toString(), payloadListforsocialmediaCheckbox, socialmediaid
            )
                .enqueue(object : Callback<SocialMediaResponse> {
                    override fun onResponse(
                        call: Call<SocialMediaResponse>,
                        response: Response<SocialMediaResponse>
                    ) {


                        if (response.body() != null) {
                            loader.hide()

                            invalidurl = false
                            ccTicCross.visibility = View.GONE
                            position.visibility = View.VISIBLE
                            val toast = Toast.makeText(
                                context,
                                "Client information has been updated successfully",
                                Toast.LENGTH_LONG
                            )

                            toast.show()
                        }
                    }

                    override fun onFailure(
                        call: Call<SocialMediaResponse>,
                        t: Throwable
                    ) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    /*
        private fun isValidUrl(url: String): Boolean {
            val pattern: Pattern = Patterns.WEB_URL
            val matcher = pattern.matcher(url)
            return matcher.matches()
        }*/
    private fun isValidUrl(url: String): Boolean {
        val customPattern =
            "^(https?://(www\\.)?|www\\.)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/.*)?$"
        return url.matches(customPattern.toRegex())
    }

}