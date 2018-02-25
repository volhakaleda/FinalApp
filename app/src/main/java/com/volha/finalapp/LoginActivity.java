package com.volha.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

  private EditText username;
  private EditText password;
  private Button submit;

  private final static String SHARED_PREFS_KEY = "dogSharedPRefs";
  private final static String USERNAME_KEY = "usernameKey";
  private final static String PASSWORD_KEY = "passwordKey";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    username = findViewById(R.id.username);
    password = findViewById(R.id.password);
    submit = findViewById(R.id.submit);

    final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
    String savedUsername = sharedPreferences.getString(USERNAME_KEY, "");
    String savedPassword = sharedPreferences.getString(PASSWORD_KEY, "");

    if(!TextUtils.isEmpty(savedUsername) && !TextUtils.isEmpty(savedPassword)) {
      Intent intent = new Intent(LoginActivity.this, BreedsActivity.class);
      startActivity(intent);
    }


    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String usernameInput = username.getText().toString();
        String passwordInput = password.getText().toString();
        boolean isValid = true;

        if(TextUtils.isEmpty(usernameInput)) {
          username.setError("Please enter a username");
          isValid = false;
        }

        if(TextUtils.isEmpty(passwordInput)) {
          password.setError("Please enter a password");
          isValid = false;
        } else if (passwordInput.contains(usernameInput)) {
          password.setError("password cannot contain username");
          isValid = false;
        }

        if(isValid) {
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString(USERNAME_KEY, usernameInput);
          editor.putString(PASSWORD_KEY, passwordInput);
          Intent intent = new Intent(LoginActivity.this, BreedsActivity.class);
          startActivity(intent);
        }


      }
    });
  }
}
