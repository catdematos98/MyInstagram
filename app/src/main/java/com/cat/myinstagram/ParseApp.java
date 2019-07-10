package com.cat.myinstagram;

import android.app.Application;

import com.cat.myinstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

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
        ParseObject.registerSubclass(Post.class);
    }

}

