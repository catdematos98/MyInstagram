package com.cat.myinstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("myAppId")
                .clientKey("mySecretMasterKey")
                .server("http://instagram4fbu.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}

