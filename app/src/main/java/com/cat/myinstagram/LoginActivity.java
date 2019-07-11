package com.cat.myinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public final String TAG = "Login Activity";
    EditText usernameET;
    EditText passwordET;
    Button loginBT;
    Button signUpBT;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getSupportActionBar().hide();

        usernameET = (EditText) findViewById(R.id.etUsername);
        passwordET = (EditText) findViewById(R.id.etPassword);
        loginBT = (Button) findViewById(R.id.btCreateAccount);
        signUpBT = (Button) findViewById(R.id.btSignUp);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                login(username, password);
            }
        });

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent toHomeActivity = new Intent(LoginActivity.this, HomeTimeline.class);
            startActivity(toHomeActivity);
            finish();
        }
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Log.d(TAG, "Login Successful");
                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                    final Intent i = new Intent(LoginActivity.this, HomeTimeline.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Log.e(TAG, "Login failed");
                    e.printStackTrace();
                }
            }
        });
    }
}
