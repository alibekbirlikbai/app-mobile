package kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import kz.iitu.itse1903.birlikbai.newsapp.R;


public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView title, desc, author, published_ad, source, time;
    public ImageView imageView;
    public ProgressBar progressBar;
    public CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        desc = itemView.findViewById(R.id.desc);
        author = itemView.findViewById(R.id.author);
        published_ad = itemView.findViewById(R.id.publishedAt);
        source = itemView.findViewById(R.id.source);
        time = itemView.findViewById(R.id.time);
        imageView = itemView.findViewById(R.id.img);
        progressBar = itemView.findViewById(R.id.prograss_load_photo);

        cardView = itemView.findViewById(R.id.main_container);
    }
}
