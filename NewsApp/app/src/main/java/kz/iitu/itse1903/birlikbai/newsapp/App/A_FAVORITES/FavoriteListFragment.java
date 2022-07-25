package kz.iitu.itse1903.birlikbai.newsapp.App.A_FAVORITES;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import kz.iitu.itse1903.birlikbai.newsapp.App.Adapter;
import kz.iitu.itse1903.birlikbai.newsapp.R;
import kz.iitu.itse1903.birlikbai.newsapp.App.activities.GeneralNews;
import kz.iitu.itse1903.birlikbai.newsapp.App.activities.MainActivity;
import kz.iitu.itse1903.birlikbai.newsapp.App.activities.NewsDetail;
import kz.iitu.itse1903.birlikbai.newsapp.App.models.Article;


public class FavoriteListFragment extends Fragment {

    RecyclerView favoriteList;

    SharedPreference sharedPreference;
    List<Article> favorites;

    Activity activity;
    Adapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container,
                false);
        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

        favoriteList = (RecyclerView) view.findViewById(R.id.recyclerView_favorite);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        favoriteList.setLayoutManager(manager);

        adapter = new Adapter(favorites, activity);

        favoriteList.setAdapter(adapter);



        adapter.setOnItemListener(new Adapter.OnItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(activity, NewsDetail.class);

                Article article = favorites.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }


                Toast.makeText(activity, "Position "+position+" Click!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

                ImageView button = (ImageView) view
                        .findViewById(R.id.imgbtn_favorite);

                String tag = button.getTag().toString();
                if (tag.equalsIgnoreCase("grey")) {
                    sharedPreference.addFavorite(activity, favorites.get(position));
                    Toast.makeText(activity, "Article is ADDED to Favorite", Toast.LENGTH_SHORT).show();

                    button.setTag("red");
                    button.setImageDrawable(activity.getResources().getDrawable(R.drawable.favorite_foreground_red));
                } else {
                    sharedPreference.removeFavorite(activity, favorites.get(position));
                    button.setTag("grey");
                    button.setImageDrawable(activity.getResources().getDrawable(R.drawable.favorite_foreground));

                    adapter.remove(favorites.get(position));

                    Toast.makeText(activity, "Article is REMOVED from Favorite", Toast.LENGTH_SHORT).show();
                }







//                sharedPreference.removeFavorite(activity, favorites.get(position));
//                button.setTag("grey");
//                button.setImageDrawable(activity.getResources().getDrawable(R.drawable.favorite_foreground));
//
//                Toast.makeText(activity, "Article is REMOVED from Favorite", Toast.LENGTH_SHORT).show();
            }
        });




        //Bottom menu
        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.favorite);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.general:
                        startActivity(new Intent(getContext()
                                , GeneralNews.class));
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getContext()
                                , MainActivity.class));
                        return true;
                    case R.id.favorite:
                        return true;
                }
                return false;
            }
        });



        return view;
    }




}