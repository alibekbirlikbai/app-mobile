package kz.iitu.itse1903.birlikbai.newsapp.Auth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kz.iitu.itse1903.birlikbai.newsapp.Auth.Entity.User;
import kz.iitu.itse1903.birlikbai.newsapp.R;


public class MainAuthActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase db = openOrCreateDatabase("app.db",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (Username TEXT PRIMARY KEY, Email TEXT, password TEXT)");
        db.execSQL("INSERT OR IGNORE INTO users VALUES ('alibek', 'alibekbirlikbai21@gmail.com', '123');");

        Cursor query = db.rawQuery("Select * from users", null);
        while (query.moveToNext()){
            User user = new User();
            String Username = query.getString(0);
            String Email = query.getString(1);
            String password = query.getString(2);
            user.setUsername(Username);
            user.setEmail(Email);
            user.setPassword(password);
            System.out.println(user.getUsername());
            users.add(user);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
             Button btnRegister = findViewById(R.id.btnregister);

             btnRegister.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     startActivity(new Intent(MainAuthActivity.this, RegisterActivity.class));
                 }
             });



             Button btnLogin = findViewById(R.id.btnLoginMain);
             btnLogin.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     startActivity(new Intent(MainAuthActivity.this, LoginActivity.class));
                 }
             });

        query.close();
        db.close();
    }
}