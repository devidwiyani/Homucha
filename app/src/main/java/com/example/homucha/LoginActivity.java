package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public EditText logUsername, logPassword;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logUsername = findViewById(R.id.inUsername);
        logPassword = findViewById(R.id.inPassword);
        dbHelper = new DbHelper(this);
    }

    public void onLoginClick (View view){
        if(view.getId() == R.id.btnLogin) {
            String ambilUsername = logUsername.getText().toString().trim();
            String ambilPassword = logPassword.getText().toString().trim();

            Boolean res = dbHelper.checkUser(ambilUsername, ambilPassword);
            if (res){
                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                intent.putExtra("UserNameLogin", ambilUsername);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, "Login Failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void klikRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}