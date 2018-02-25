package com.volha.finalapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

  List<String> images = new ArrayList<>();

  @Override
  public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new ViewHolder(inflater.inflate(R.layout.view_holder_layout, parent, false));
  }

  @Override
  public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
    String url = images.get(position);
    Picasso.with(holder.imageView.getContext()).load(url).into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return images.size();
  }

  public void setData(List<String> images) {
    this.images = images;
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    ViewHolder(View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.image);
    }
  }
}
