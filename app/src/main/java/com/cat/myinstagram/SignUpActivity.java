package com.cat.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameET;
    EditText passwordET;
    EditText emailET;
    Button signUpBT;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        usernameET = (EditText) findViewById(R.id.etUsername);
        passwordET = (EditText) findViewById(R.id.etPassword);
        emailET = (EditText) findViewById(R.id.etEmailSIGN);
        signUpBT = (Button)findViewById(R.id.btCreateAccount);

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpBT.setEnabled(false);
                if(usernameET == null){
                    Toast.makeText(SignUpActivity.this, "You must enter a username!", Toast.LENGTH_SHORT).show();
                }
                else if(passwordET == null){
                    Toast.makeText(SignUpActivity.this, "You must enter a password!", Toast.LENGTH_SHORT).show();
                }
                else if(emailET == null){
                    Toast.makeText(SignUpActivity.this, "You must enter an email!", Toast.LENGTH_SHORT).show();
                }
                else{
                    signUpBT.setEnabled(true);
                    // Create the ParseUser
                    ParseUser user = new ParseUser();
                    // Set core properties@
                    user.setUsername(usernameET.getText().toString());
                    user.setPassword(passwordET.getText().toString());
                    user.setEmail(emailET.getText().toString());
                    // Set custom properties
                   // user.put("phone", "650-253-0000");
                    // Invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now
                                Toast.makeText(SignUpActivity.this, "Sign up successful, log in!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                e.printStackTrace();
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                            }
                        }
                    });
                }

            }
        });
    }
}
