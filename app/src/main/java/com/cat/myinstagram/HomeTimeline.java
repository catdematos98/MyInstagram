package com.cat.myinstagram;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cat.myinstagram.model.fragments.ComposeFragment;
import com.cat.myinstagram.model.fragments.PostsFragment;
import com.cat.myinstagram.model.fragments.ProfileFragment;
import com.parse.ParseUser;

public class HomeTimeline extends AppCompatActivity {

    private final String TAG = "HomeTimeline";
    BottomNavigationView bottomNavView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.nav_logo_whiteout);


        bottomNavView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.activity_profile:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new ComposeFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavView.setSelectedItemId(R.id.action_home);
    }

    public void logoutBT(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be nul
        Intent i = new Intent(HomeTimeline.this, LoginActivity.class);
        Toast.makeText(this, "Log out successful.", Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }

}
