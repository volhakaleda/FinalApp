package com.volha.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dogs);

    TextView breedTV = findViewById(R.id.breed);
    final RecyclerView recyclerView = findViewById(R.id.images);
    final ImageAdapter adapter = new ImageAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    String breed = getIntent().getStringExtra(BreedsActivity.BREED_KEY);
    breedTV.setText(breed);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dog.ceo")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    DogService service = retrofit.create(DogService.class);

    Call<ThumbnailResponse> call = service.getDogThumbnails(breed);

    call.enqueue(new Callback<ThumbnailResponse>() {
      @Override
      public void onResponse(Call<ThumbnailResponse> call, Response<ThumbnailResponse> response) {
        List<String> images = response.body().getMessage();
        adapter.setData(images);
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Call<ThumbnailResponse> call, Throwable t) {
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS_KEY, MODE_PRIVATE);

    switch (item.getItemId()) {
      case R.id.logout:
        sharedPreferences.edit().remove(LoginActivity.USERNAME_KEY).apply();
        finish();
        Intent intent = new Intent(DogsActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
