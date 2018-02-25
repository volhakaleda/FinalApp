package com.volha.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class BreedsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breeds);

    TextView greeting = findViewById(R.id.greeting);

    final SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS_KEY, MODE_PRIVATE);
    String savedUsername = sharedPreferences.getString(LoginActivity.USERNAME_KEY, "");

    if(TextUtils.isEmpty(savedUsername)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }

    StringBuilder builder = new StringBuilder(getString(R.string.greeting));
    builder.append(savedUsername);
    builder.append(getString(R.string.question_mark));

    greeting.setText(builder.toString());
  }
}
