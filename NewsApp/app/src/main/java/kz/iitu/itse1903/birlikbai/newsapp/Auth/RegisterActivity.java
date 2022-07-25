package kz.iitu.itse1903.birlikbai.newsapp.Auth;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kz.iitu.itse1903.birlikbai.newsapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);


        ImageButton btnBack = findViewById(R.id.imageButtonBack);
        Button btnReg = findViewById(R.id.btnRegisterActivate);
        EditText edtUsername = findViewById(R.id.editTextUsername);
        EditText edtLogin = findViewById(R.id.editTextLogin);
        EditText edtPassword1 = findViewById(R.id.editTextPassword1);
        EditText edtPassword2 = findViewById(R.id.editTextPassword2);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pas1 = edtPassword1.getText().toString();
                String pas2 = edtPassword2.getText().toString();
                if(pas1.equals(pas2)){
                    String Username = edtUsername.getText().toString();
                    String Login = edtLogin.getText().toString();

                    try {
                        SQLiteDatabase db = openOrCreateDatabase("app.db",MODE_PRIVATE,null);
                        String sql = "INSERT INTO users (Username, Email, password) Values(?,?,?)";
                        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
                        sqLiteStatement.bindString(1, Username);
                        sqLiteStatement.bindString(2, Login);
                        sqLiteStatement.bindString(3, pas1);
                        sqLiteStatement.executeInsert();
                        Toast.makeText(RegisterActivity.this, "New Account add", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "Username is already exist", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }

            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, kz.iitu.itse1903.birlikbai.newsapp.Auth.MainAuthActivity.class));
            }
        });


    }
}