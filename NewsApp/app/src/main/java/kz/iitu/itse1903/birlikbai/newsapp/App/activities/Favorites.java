package kz.iitu.itse1903.birlikbai.newsapp.App.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kz.iitu.itse1903.birlikbai.newsapp.App.A_FAVORITES.FavoriteListFragment;

public class Favorites extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new FavoriteListFragment()).commit();


    }


}
