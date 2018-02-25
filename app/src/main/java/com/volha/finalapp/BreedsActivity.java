package com.volha.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedsActivity extends AppCompatActivity implements View.OnClickListener{

  static final String BREED_KEY = "breedKey";
  private static String[] BREEDS = {"terrier", "spaniel", "retriever", "poodle"};
  private ImageView terrierIV;
  private ImageView spanielIV;
  private ImageView retrieverIV;
  private ImageView poodleIV;

  private SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breeds);

    TextView greeting = findViewById(R.id.greeting);
    terrierIV = findViewById(R.id.terrier);
    spanielIV = findViewById(R.id.spaniel);
    retrieverIV = findViewById(R.id.retriever);
    poodleIV = findViewById(R.id.poodle);

    CardView terrierCard = findViewById(R.id.terrier_card);
    CardView spanielCard = findViewById(R.id.spaniel_card);
    CardView retrieverCard = findViewById(R.id.retriever_card);
    CardView poodleCard = findViewById(R.id.poodle_card);
    terrierCard.setOnClickListener(this);
    spanielCard.setOnClickListener(this);
    retrieverCard.setOnClickListener(this);
    poodleCard.setOnClickListener(this);

    sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS_KEY, MODE_PRIVATE);
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

  @Override
  public void onClick(View v) {
    String breed = "";
    switch (v.getId()) {
      case R.id.terrier_card:
        breed = "terrier";
        break;
      case R.id.spaniel_card:
        breed = "spaniel";
        break;
      case R.id.retriever_card:
        breed = "retriever";
        break;
      case R.id.poodle_card:
        breed = "poodle";
        break;
    }

    Intent intent = new Intent(this, DogsActivity.class);
    intent.putExtra(BREED_KEY, breed);
    startActivity(intent);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.logout:
        sharedPreferences.edit().remove(LoginActivity.USERNAME_KEY).apply();
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
