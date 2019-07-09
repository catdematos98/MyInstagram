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

    EditText usernameET;
    EditText passwordET;
    Button loginBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameET = (EditText) findViewById(R.id.etUsername);
        passwordET = (EditText) findViewById(R.id.etPassword);
        loginBT = (Button) findViewById(R.id.btLogin);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                login(username, password);
            }
        });


    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Log.d("LoginActivity", "Login Successful");
                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                    final Intent i = new Intent(LoginActivity.this, HomeContent.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Log.e("LoginActivity", "Login failed");
                    e.printStackTrace();
                }
            }
        });
    }
}
