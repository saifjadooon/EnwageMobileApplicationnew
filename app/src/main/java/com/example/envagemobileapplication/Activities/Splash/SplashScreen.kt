package com.example.envagemobileapplication.Activities.Splash

import BaseActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.envagemobileapplication.Activities.Login.LoginActivty
import com.example.envagemobileapplication.Activities.Splash.landingScreens.LandingScreen
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.SharedPrefs
import com.example.envagemobileapplication.ViewModels.SharedLoginViewModel


class SplashScreen : BaseActivity() {

    lateinit var imageView: ImageView

    val viewModel: SharedLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.envagemobileapplication.R.layout.activity_splash_screen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        imageView = findViewById(com.example.envagemobileapplication.R.id.imageView)

        Glide.with(this)
            .asGif()
            .load(com.example.envagemobileapplication.R.raw.splash_screen_animation) //or url
            .into(object : SimpleTarget<GifDrawable?>() {
                override fun onResourceReady(
                    resource: GifDrawable,
                    @Nullable transition: Transition<in GifDrawable?>?
                ) {
                    resource.start()
                    //resource.setLoopCount(1);
                    imageView.setImageDrawable(resource)
                }
            })

        moveToNextActivity()


        /*    viewModel.LDLoginuserForSplashScreen.observe(this) { data ->
                if (data.equals("UserLoggedinSuccesfully")) {


                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }

            }*/

    }

    private fun moveToNextActivity() {
        val isFirstTime = SharedPrefs.getIsFirstTimeLaunch(this@SplashScreen, "isFirstTime")
        val isUserLogin = SharedPrefs.getUserLogin(this@SplashScreen, "isUserLogin")

        if (isFirstTime) {

            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    startActivity(Intent(this@SplashScreen, LandingScreen::class.java))

                    finish()
                }
            }, 4000)

        }
        /*         else if (isUserLogin) {

                     Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                         override fun run() {
                             var tokenManager: TokenManager = TokenManager(this@SplashScreen)

                             val username = tokenManager.getuserName().toString()
                             val password = tokenManager.getPassword().toString()
                             viewModel.loginUserForSplashScreen(username, password, tokenManager)


                         }
                     }, 3500)



                 } */
        else {
            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    Constants.thisss = this@SplashScreen
                    val intent = Intent(this@SplashScreen, LoginActivty::class.java)
                    finish()
                    startActivity(intent)
                }
            }, 4000)

        }
    }
}