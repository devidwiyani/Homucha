package com.example.homucha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    sharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(spm.getSPId(this) > 0)
        {
            Intent toDashboard = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(toDashboard);
        }
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
                spm.saveSPInt(getBaseContext(), spm.SP_ID, dbHelper.checkUserId(ambilUsername, ambilPassword));
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
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}