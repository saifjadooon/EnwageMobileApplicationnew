package com.example.envagemobileapplication.Activities


import BaseActivity
import NetworkChangeReceiver
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.envagemobileapplication.Activities.Login.LoginActivty
import com.example.envagemobileapplication.Fragments.Dashboard.*
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionCandidates
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.RequestModels.sortDirection
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.Utils.SharedPrefs
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso


class MainActivity : BaseActivity() {

    companion object {
        lateinit var ctx: Context
    }

    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var networkChangeReceiver: NetworkChangeReceiver
    val viewModel: MainActivityViewModel by viewModels()
    lateinit var tokenManager: TokenManager
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var outer: ConstraintLayout

    lateinit var bundle: Bundle
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102

    lateinit var loader: Loader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ctx = this
        bundle = Bundle()
        loader = Loader(this)
        initViews()
        clickListeners()
        observers()
    }

    private fun clickListeners() {
        binding.fab.setOnClickListener {
            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvHome.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

            replaceFragment(HomeF(), bundle)
        }
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {

                R.id.candidateFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundResource(R.drawable.btn_black_radius)

                    val model = SortDirectionCandidates(
                        candidateFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = "",
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )

                    loader.show()
                    viewModel.getCandidates(
                        this,
                        tokenManager.getAccessToken(),
                        model
                    )
                    true
                }
                R.id.clientFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))


                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvClient.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.white))
                    var isfromCompnyStatus = false
                    loader.show()
                    val model = sortDirection(
                        pageIndex = 1,
                        pageSize = 25,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        searchText = "",
                        tileStatusId = -1
                    )
                    viewModel.getClients(
                        this,
                        tokenManager.getAccessToken(),
                        model,
                        isfromCompnyStatus
                    )
                    // replaceFragment(ClientsF())

                    true
                }
                R.id.jobsFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))



                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvJobs.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.white))
                    loader.show()
                    var isfromJobBottomSheet = false
                    val model = SortDirectionJobs(
                        clientId = null,
                        jobFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = "",
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )
                    viewModel.getJobs(
                        this,
                        tokenManager.getAccessToken(),
                        model,
                        isfromJobBottomSheet
                    )
                    //   replaceFragment(JobsF(), bundle)

                    true
                }
                R.id.messagesFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))


                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvMessages.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.white))
                    replaceFragment(MessagesF(), bundle)

                    true
                }
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.candidateFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))
                    val model = SortDirectionCandidates(
                        candidateFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = "",
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )
                    loader.show()
                    viewModel.getCandidates(
                        this,
                        tokenManager.getAccessToken(),
                        model
                    )
                    true
                }
                R.id.clientFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))


                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvClient.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.white))

                    var isfromCompnyStatus = false
                    loader.show()
                    val model = sortDirection(
                        pageIndex = 1,
                        pageSize = 25,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        searchText = "",
                        tileStatusId = -1
                    )
                    viewModel.getClients(
                        this,
                        tokenManager.getAccessToken(),
                        model,
                        isfromCompnyStatus
                    )
                    // replaceFragment(ClientsF())

                    true
                }
                R.id.jobsFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))



                    binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvJobs.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.white))
                    loader.show()
                    var isfromJobBottomSheet = false

                    val model = SortDirectionJobs(
                        clientId = null,
                        jobFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = "",
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )
                    viewModel.getJobs(
                        this,
                        tokenManager.getAccessToken(),
                        model,
                        isfromJobBottomSheet
                    )

                    true
                }
                R.id.messagesFragment -> {
                    binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivMessages.setColorFilter(Color.WHITE)
                    binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
                    binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))

                    // val svgDrawable: PictureDrawable = loadSVGFromResource(R.drawable.ic_messages)

// Set the initial color
                    //  binding.leftDrawerMenu.ivMessages.setImageDrawable(svgDrawable)

// Change the color programmatically
                    binding.leftDrawerMenu.ivMessages.setColorFilter(Color.WHITE)

                    binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
                    binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

                    binding.leftDrawerMenu.cvMessages.setBackgroundResource(R.drawable.btn_black_radius)
                    binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.white))

                    replaceFragment(MessagesF(), bundle)

                    true
                }

            }
            true
        }
        binding.ivMenu.setOnClickListener {

            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(Gravity.LEFT)

            } else {
                binding.drawerLayout.openDrawer(Gravity.LEFT)

            }

        }
        binding.leftDrawerMenu.ivCloseDrawer.setOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)

        }
        binding.leftDrawerMenu.cvClient.setOnClickListener {
            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))


            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvClient.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.white))
            var isfromCompnyStatus = false
            loader.show()
            val model = sortDirection(
                pageIndex = 1,
                pageSize = 25,
                sortBy = "CreatedDate",
                sortDirection = 1,
                searchText = "",
                tileStatusId = -1
            )

            viewModel.getClients(
                this,
                tokenManager.getAccessToken(),
                model,
                isfromCompnyStatus
            )
            binding.drawerLayout.closeDrawer(Gravity.LEFT)

        }
        binding.leftDrawerMenu.cvCandidate.setOnClickListener {
            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundResource(R.drawable.btn_black_radius)
            val model = SortDirectionCandidates(
                candidateFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusId = -1
            )
            loader.show()
            viewModel.getCandidates(
                this,
                tokenManager.getAccessToken(),
                model
            )

            binding.bottomNavigationView.selectedItemId = R.id.candidateFragment
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }
        binding.leftDrawerMenu.cvJobs.setOnClickListener {
            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))



            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvJobs.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.white))
            replaceFragment(JobsF(), bundle)
            binding.bottomNavigationView.selectedItemId = R.id.jobsFragment
            binding.drawerLayout.closeDrawer(Gravity.LEFT)


        }
        binding.leftDrawerMenu.cvMessages.setOnClickListener {
            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvHome.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.black))


            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))

            binding.leftDrawerMenu.cvMessages.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.white))
            replaceFragment(MessagesF(), bundle)
            binding.bottomNavigationView.selectedItemId = R.id.messagesFragment

            binding.drawerLayout.closeDrawer(Gravity.LEFT)


        }
        binding.leftDrawerMenu.cvHome.setOnClickListener {

            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvHome.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))


            replaceFragment(HomeF(), bundle)
            binding.bottomNavigationView.selectedItemId = 0
            binding.drawerLayout.closeDrawer(Gravity.LEFT)

        }
        binding.leftDrawerMenu.ivLogout.setOnClickListener {
            logout()

        }
        binding.leftDrawerMenu.tvUsername.setOnLongClickListener {

            val toast = Toast.makeText(
                this@MainActivity,
                tokenManager.getuserFirstName() + " " + tokenManager.getuserLastName(),
                Toast.LENGTH_LONG
            )

            toast.show()

            true
        }
        binding.leftDrawerMenu.tvUserEmail.setOnLongClickListener {


            val toast = Toast.makeText(
                this@MainActivity,
                tokenManager.getUserEmail(),
                Toast.LENGTH_LONG
            )

            toast.show()

            true
        }
    }

    fun logout() {
        val sharedPreferences = getSharedPreferences("SSOSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val sharedPreferencestoken = getSharedPreferences("TokenManager", Context.MODE_PRIVATE)
        val editortoken = sharedPreferencestoken.edit()
        editortoken.clear()
        editortoken.apply()
        SharedPrefs.setUserLogin(this@MainActivity, "isUserLogin", false)
        // Perform logout actions like navigating to the login screen
        // For example, if you're using an Intent to navigate:
        val intent = Intent(this, LoginActivty::class.java)
        finishAffinity()
        startActivity(intent)
        // Close the current activity
    }

    private fun observers() {
        viewModel.LDgetClients.observe(this)
        {
            loader.hide()


            val clientDataList: ArrayList<Record> = ArrayList()
            for (i in 0 until it.data.records.size) {
                clientDataList.add(it.data.records.get(i))
            }

            Constants.ClientList = clientDataList

            replaceFragment(ClientsF(), bundle)

        }
        viewModel.LDgetLoggedinClientDetail.observe(this)
        {
           global.loggedinuserDetails = it.data


            if (it.data != null) {

                try {
                    tokenManager.saveFirstName(it.data.firstName)
                    tokenManager.saveLastName(it.data.lastName)
                    tokenManager.saveProfiepic(it.data.imagePath)
                    tokenManager.saveUserEmail(it.data.email)
                    tokenManager.saveLoggedInUserName(it.data.userName)

                    binding.leftDrawerMenu.tvUsername.text =
                        tokenManager.getuserFirstName() + " " + tokenManager.getuserLastName()
                } catch (ex: Exception) {

                }

                try {
                    Picasso.get().load(tokenManager.getProfilePic())
                        .into(binding.leftDrawerMenu.ivProfile)
                } catch (e: Exception) {
                }

                try {
                    binding.leftDrawerMenu.tvUserEmail.text = tokenManager.getUserEmail()
                } catch (ex: Exception) {
                }

                try {
                    //  binding.leftDrawerMenu.tvUsername.text = tokenManager.getUserName()
                } catch (ex: Exception) {
                }
            }
        }
        viewModel.LDgetJobs.observe(this) {
            loader.hide()
            if (it.data != null) {

                var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record> =
                    ArrayList()

                for (i in 0 until it.data.records.size) {


                    jobsList.add(it.data.records.get(i))
                }
                Constants.JobsList = jobsList

                replaceFragment(JobsF(), bundle)
            }


        }

        viewModel.LDgetCandidates.observe(this) {
            loader.hide()
            if (it.data != null) {

                var candidateList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.Record> =
                    ArrayList()

                for (i in 0 until it.data.records.size) {


                    candidateList.add(it.data.records.get(i))
                }
                Constants.Candidatelist = candidateList

                replaceFragment(CandidateF(), bundle)
            }
        }
    }

    private fun initViews() {

        Constants.context = this@MainActivity
        networkChangeReceiver = NetworkChangeReceiver()
        tokenManager = TokenManager(this@MainActivity)
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.background = null
        outer = findViewById(R.id.leftDrawerMenu)
        val fragmentManager: FragmentManager = supportFragmentManager
        Constants.cfm = fragmentManager
        var token = tokenManager.getAccessToken().toString()
        viewModel.getLoggedInUserDetails(this, token)


        try {

            binding.leftDrawerMenu.ivHome.setColorFilter(resources.getColor(R.color.white))
            binding.leftDrawerMenu.ivMessages.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivClient.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivCandidate.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.ivJobs.setColorFilter(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvCandidate.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvCandidate.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvHome.setBackgroundResource(R.drawable.btn_black_radius)
            binding.leftDrawerMenu.tvHome.setTextColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.cvClient.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvClients.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvJobs.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvJobs.setTextColor(resources.getColor(R.color.black))
            binding.leftDrawerMenu.cvMessages.setBackgroundColor(resources.getColor(R.color.white))
            binding.leftDrawerMenu.tvMessages.setTextColor(resources.getColor(R.color.black))
        } catch (e: Exception) {
        }


    }

    override fun onResume() {
        super.onResume()
        /* initViews()
         clickListeners()
         observers()*/
        val fragmentManager: FragmentManager = supportFragmentManager
        Constants.cfm = fragmentManager
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)

/*

        var token = tokenManager.getAccessToken().toString()

        var isfromCompnyStatus = false

        if (com.example.envagemobileapplication.Utils.Constants.isfromBackpress == true) {
            loader.show()
            val model = sortDirection(
                pageIndex = 1,
                pageSize = 25,
                sortBy = "CreatedDate",
                sortDirection = 1,
                searchText = "",
                tileStatusId = -1
            )
            viewModel.getClients(
                this,
                tokenManager.getAccessToken(),
                model,
                isfromCompnyStatus
            )
        }
*/


    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {

        val fragmentManager: FragmentManager = supportFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, fragment)

        fragment.arguments = bundle

        transaction.commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                clickListeners()
            } else {


                // Handle the case where permissions are not granted
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    // Handle the selected image from the gallery
                    val selectedImageUri: Uri? = data?.data
                    // Use the selectedImageUri as needed
                }
                CAMERA_REQUEST_CODE -> {
                    // Handle the captured image from the camera
                    // The image is usually available in the data parameter of the intent.
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    // Use the imageBitmap as needed
                }
            }
        }
    }

    private fun loadSVGFromResource(resourceId: Int): PictureDrawable {
        try {
            val svg = SVG.getFromResource(resources, resourceId)
            val picture = svg.renderToPicture()
            return PictureDrawable(picture)
        } catch (e: SVGParseException) {
            e.printStackTrace()
        }
        // Return a default drawable if there was an error
        return PictureDrawable(null)
    }


}



