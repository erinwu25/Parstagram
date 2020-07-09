package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private EditText etSignupUsername;
    private EditText etSignupPassword;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // find elements in view
        btnCreate = findViewById(R.id.btnCreateAccount);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupUsername = findViewById(R.id.etSignupUsername);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "OnClick Create Account button");
                String username = etSignupUsername.getText().toString();
                String password = etSignupPassword.getText().toString();
                createAccount(username, password);
            }
        });

    }

    private void createAccount(String username, String password) {
        ParseUser user = new ParseUser();  // create new user

        // set core properties
        user.setUsername(username);
        user.setPassword(password);

        // invoke signup in background
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // yay no errors!

                }
                else {
                    // there was an error and we need to handle it

                }
            }
        });
        finish();
    }
}