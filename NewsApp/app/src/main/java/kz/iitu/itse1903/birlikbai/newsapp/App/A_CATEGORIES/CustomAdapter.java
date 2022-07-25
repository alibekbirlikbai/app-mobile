package kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import kz.iitu.itse1903.birlikbai.newsapp.R;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Listeners.SelectListener;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Models.NewsHeadline;
import kz.iitu.itse1903.birlikbai.newsapp.App.Utils;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadline> headlineList;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadline> headlineList, SelectListener listener) {
        this.context = context;
        this.headlineList = headlineList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.general_headlines_list_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();


        holder.title.setText(headlineList.get(position).getTitle());
        holder.desc.setText(headlineList.get(position).getDescription());
        holder.source.setText(headlineList.get(position).getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(headlineList.get(position).getPublishedAt()));
        holder.published_ad.setText(Utils.DateFormat(headlineList.get(position).getPublishedAt()));
        Glide.with(context)
                .load(headlineList.get(position).getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
//        if (headlineList.get(position).getUrlToImage()!=null){
//            Picasso.get().load(headlineList.get(position).getUrlToImage()).into(holder.imageView);
//        }
        holder.author.setText(headlineList.get(position).getSource().getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(headlineList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlineList.size();
    }
}



