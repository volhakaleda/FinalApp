package com.volha.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedsActivity extends AppCompatActivity {

  private static String[] BREEDS = {"terrier", "spaniel", "retriever", "poodle"};
  private ImageView terrierIV;
  private ImageView spanielIV;
  private ImageView retrieverIV;
  private ImageView poodleIV;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breeds);

    TextView greeting = findViewById(R.id.greeting);
    terrierIV = findViewById(R.id.terrier);
    spanielIV = findViewById(R.id.spaniel);
    retrieverIV = findViewById(R.id.retriever);
    poodleIV = findViewById(R.id.poodle);

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

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dog.ceo")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    DogService service = retrofit.create(DogService.class);

    for(int i = 0; i < BREEDS.length; i++) {
      final String currentBreed = BREEDS[i];

      Call<DogImage> call = service.getDogImage(currentBreed);

      call.enqueue(new Callback<DogImage>() {
        @Override
        public void onResponse(Call<DogImage> call, Response<DogImage> response) {
          String image = response.body().getMessage();
          ImageView currentImageView = terrierIV;
          if(currentBreed.equals("spaniel")) {
            currentImageView = spanielIV;
          } else if (currentBreed.equals("retriever")) {
            currentImageView = retrieverIV;
          } else if (currentBreed.equals("poodle")) {
            currentImageView = poodleIV;
          }

          Picasso.with(getApplicationContext()).load(image).into(currentImageView);
        }

        @Override
        public void onFailure(Call<DogImage> call, Throwable t) {
          t.getMessage();
        }
      });
    }
  }
}
