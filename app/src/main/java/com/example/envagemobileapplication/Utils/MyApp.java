package com.example.envagemobileapplication.Utils;

import android.app.Application;

import com.example.envagemobileapplication.Oauth.TokenManager;

public class MyApp extends Application {

    public static String Token;

    private TokenManager mSharedPrefRep;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPrefRep = new TokenManager(this);


    }


}
