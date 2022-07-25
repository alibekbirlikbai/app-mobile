package kz.iitu.itse1903.birlikbai.newsapp.Auth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kz.iitu.itse1903.birlikbai.newsapp.App.activities.Favorites;
import kz.iitu.itse1903.birlikbai.newsapp.App.activities.MainActivity;
import kz.iitu.itse1903.birlikbai.newsapp.Auth.Entity.User;
import kz.iitu.itse1903.birlikbai.newsapp.R;

public class LoginActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase db = openOrCreateDatabase("app.db",MODE_PRIVATE,null);

        Cursor query = db.rawQuery("Select * from users", null);
        while (query.moveToNext()){
            User user = new User();
            String Username = query.getString(0);
            String Email = query.getString(1);
            String password = query.getString(2);
            user.setUsername(Username);
            user.setEmail(Email);
            user.setPassword(password);
            users.add(user);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);


        EditText edUsername = findViewById(R.id.UsernameLogin);
        EditText edPassword = findViewById(R.id.UserPassword);


        Button btnLogin = findViewById(R.id.btnSignIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Usname = edUsername.getText().toString();
                String USpassword = edPassword.getText().toString();
                for(User user : users){
                    if(Usname.equals(user.getUsername())){
                        if(USpassword.equals(user.getPassword())){
                            Toast.makeText(LoginActivity.this, "User Found", Toast.LENGTH_LONG).show();
                            //setContentView(R.layout.layout_welcome);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        ImageButton btnBack = findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, kz.iitu.itse1903.birlikbai.newsapp.Auth.MainAuthActivity.class));

            }
        });
    }
}
