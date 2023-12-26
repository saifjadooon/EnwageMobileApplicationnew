package com.example.envagemobileapplication.Activities.AddClient

import BaseActivity
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryContactsFragment
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryF
import com.example.envagemobileapplication.Fragments.ClientSummary.ClientSummaryJobsF
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityClientSummaryBinding


class ClientSummaryActivity : BaseActivity() {
    private var selectedItem: View? = null

    lateinit var binding: ActivityClientSummaryBinding
    var clientid = Constants.clientid
    lateinit var loader: Loader
    private val MULTIPLE_PERMISSIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)

        val fragmentManager: FragmentManager = supportFragmentManager
        Constants.cfm = fragmentManager

        //   replaceFragment(ClientSummaryF())
        val itemList = listOf(
            Pair("Profile summary", R.drawable.ic_profile_summary),
            Pair("Jobs", R.drawable.ic_profile_jobs),
            Pair("Contacts", R.drawable.ic_profile_contacts),
            Pair("Guests", R.drawable.ic_profile_guests),
            Pair("Attachments", R.drawable.ic_profile_attachments),
            Pair("Notes", R.drawable.ic_profile_notes),
            Pair("Tasks", R.drawable.ic_profile_tasks),
            Pair("Holiday Calender", R.drawable.ic_profile_calender),
            // Add more items with their respective drawable resource IDs
        )



        for ((itemText, drawableResId) in itemList) {
            val itemView = layoutInflater.inflate(R.layout.horzontal_scrolview_item, null)
            val imageView = itemView.findViewById<ImageView>(R.id.iv_drawable)
            val textView = itemView.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Profile summary") {
                // Set the background for "Profile summary" by default
                itemView.setBackgroundResource(R.drawable.gray_bg)
                selectedItem = itemView
            }
            // Set the drawable resource for the ImageView
            imageView.setImageResource(drawableResId)
            // Set the text for the TextView
            textView.text = itemText

            // Set a click listener for each item
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    // Reset the background of the previously selected item
                    selectedItem?.setBackgroundResource(R.drawable.btn_white_radius)

                    // Highlight the selected item
                    itemView.setBackgroundResource(R.drawable.gray_bg)
                    selectedItem = itemView

                    val clickedText = itemText
                    // Get the left position of the clicked item relative to its parent
                    val left = itemView.left

                    // Scroll the ScrollView to bring the clicked item into view
                    binding.horizontalScrollView.scrollTo(left, 0)

                    // Give focus to the clicked item's view
                    itemView.requestFocus()

                    /* val navController: NavController = Navigation.findNavController(
                         this@ClientSummaryActivity,
                         R.id.nav_client_summary
                     )*/
                    //navController.navigateUp()
                    if (clickedText == "Jobs") {

                        //   navController.navigateUp()
                        // For an activity with a NavHostFragment
                        //  navController.navigate(R.id.clientSummaryJobsF);
                        replaceFragment(ClientSummaryJobsF())
                    } else if (clickedText.equals("Profile summary")) {
                        // navController.navigateUp()
                        //  navController.navigate(R.id.clientSummaryF);
                        replaceFragment(ClientSummaryF())
                    } else if (clickedText.equals("Contacts")) {
                        // navController.navigateUp()
                        //   navController.navigate(R.id.clientSummaryContactsFragment);
                        replaceFragment(ClientSummaryContactsFragment())
                    }
                }
            })

            binding.linearlist.addView(itemView)
        }

        if (!Constants.clientName.isNullOrEmpty()) {
            val name = Constants.clientName
                .replace("\"", "")

            binding.tvClientnameHeading.text = Constants.clientName
        }

        binding.tvClientnameHeading.setOnLongClickListener {
            val toast = Toast.makeText(
                this,
                Constants.clientName.toString(),
                Toast.LENGTH_LONG
            )


            toast.show()
            true
        }
        binding.ivcCross.setOnClickListener { finish() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    showNoPermissionDialog()
                    //    checkPermissions()

                }
                return
            }
        }
    }

    private fun showNoPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("App does not have access to your Camera and Gallery. Please enable Camera and Gallery permissions from the settings and select Always.")
        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
        }


        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.nav_client_summary)
            fragment?.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cc_fragments, fragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        val fragmentManager: FragmentManager = supportFragmentManager
        Constants.cfm = fragmentManager
    }

    override fun onBackPressed() {
        com.example.envagemobileapplication.Utils.Constants.isfromBackpress = true
        super.onBackPressed()
    }
}
