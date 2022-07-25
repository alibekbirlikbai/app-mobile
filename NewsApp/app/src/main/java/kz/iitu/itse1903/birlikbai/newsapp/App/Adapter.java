package kz.iitu.itse1903.birlikbai.newsapp.App;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;

import kz.iitu.itse1903.birlikbai.newsapp.App.A_FAVORITES.SharedPreference;
import kz.iitu.itse1903.birlikbai.newsapp.App.models.Article;
import kz.iitu.itse1903.birlikbai.newsapp.R;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Article> articles;
    private Context context;
    private OnItemListener onItemListener;

    SharedPreference sharedPreference;


    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;

        sharedPreference = new SharedPreference();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
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

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.published_ad.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());

//        holder.favoriteImg.setImageResource(R.drawable.favorite_foreground);
//        holder.favoriteImg.setTag("grey");

        /*If a article exists in shared preferences then set heart_red drawable
         * and set a tag*/
        if (checkFavoriteItem(model)) {
            holder.favoriteImg.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_foreground_red));
            holder.favoriteImg.setTag("red");
        } else {
            holder.favoriteImg.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_foreground));
            holder.favoriteImg.setTag("grey");
        }
    }


    /*Checks whether a particular article exists in SharedPreferences*/
    public boolean checkFavoriteItem(Article checkArticle) {
        boolean check = false;
        List<Article> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (Article article : favorites) {
                if (article.getTitle().equals(checkArticle.getTitle())) {
                    check = true;
                    break;
                }
            }

        }

        System.out.println(check);

        return check;
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemListener(OnItemListener onItemListener){
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {

        TextView title, desc, author, published_ad, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemListener onItemListener;
        ImageView favoriteImg;


        public MyViewHolder(View itemView, OnItemListener onItemListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_ad = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);

            favoriteImg = itemView.findViewById(R.id.imgbtn_favorite);

            this.onItemListener = onItemListener;

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onItemListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }



    public void remove(Article article) {
        articles.remove(article);
        notifyDataSetChanged();
    }


}
