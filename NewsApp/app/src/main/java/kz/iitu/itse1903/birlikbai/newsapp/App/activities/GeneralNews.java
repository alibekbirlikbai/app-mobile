package kz.iitu.itse1903.birlikbai.newsapp.App.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.CustomAdapter;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Listeners.OnFetchDataListener;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Listeners.SelectListener;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Models.NewsApiResponse;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.Models.NewsHeadline;
import kz.iitu.itse1903.birlikbai.newsapp.App.A_CATEGORIES.RequestManager;
import kz.iitu.itse1903.birlikbai.newsapp.R;


public class GeneralNews extends AppCompatActivity implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_news);




        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching News...");
        progressDialog.show();

        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);


        RequestManager manager = new RequestManager(GeneralNews.this);
        manager.getNewsHeadlines(listener, "general", null);



        //Bottom menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.general);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.general:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorite:
                        startActivity(new Intent(getApplicationContext()
                                , Favorites.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private final OnFetchDataListener<NewsApiResponse> listener =new OnFetchDataListener<NewsApiResponse>() {

        @Override
        public void onFetchData(List<NewsHeadline> data, String message) {
            showNews(data);
            progressDialog.dismiss();
            if (data.isEmpty()){
                Toast.makeText(GeneralNews.this, "No Data Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(GeneralNews.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadline> headlines) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customAdapter = new CustomAdapter(this, headlines, this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadline headline) {
        startActivity(new Intent(GeneralNews.this, NewsDetail.class)
                .putExtra("data", headline));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        progressDialog.setTitle("Fetching News Of " + category);
        progressDialog.show();
        RequestManager manager = new RequestManager(GeneralNews.this);
        manager.getNewsHeadlines(listener, category, null);

        recyclerView.setVisibility(View.VISIBLE);
    }


}