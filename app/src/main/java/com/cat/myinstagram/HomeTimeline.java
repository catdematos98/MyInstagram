package com.cat.myinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeTimeline extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createNewPost(View view) {
        Intent i = new Intent(HomeTimeline.this, NewPostActivity.class);
        startActivity(i);
    }
}
