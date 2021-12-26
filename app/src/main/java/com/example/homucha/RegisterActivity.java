package com.example.homucha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText regUsername, regPassword, regName, regAddress, regPhone, regEmail;
    public Button btnRegister;
    DbHelper dbHelper;
    sharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regUsername = findViewById(R.id.rgUsername);
        regPassword = findViewById(R.id.rgPassword);
        regName = findViewById(R.id.rgName);
        regAddress = findViewById(R.id.rgAddress);
        regPhone = findViewById(R.id.rgPhone);
        regEmail = findViewById(R.id.rgEmail);
        btnRegister = findViewById(R.id.btn_register);

        dbHelper = new DbHelper(this);
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ambilUsername = regUsername.getText().toString();
                String ambilPassword = regPassword.getText().toString();
                String ambilName = regName.getText().toString();
                String ambilAddress = regAddress.getText().toString();
                String ambilPhone = regPhone.getText().toString();
                String ambilEmail = regEmail.getText().toString();

                boolean check = validasiForm(ambilUsername, ambilPassword, ambilName,
                        ambilAddress, ambilPhone, ambilEmail);
                if (check == true) {
                    Cursor checkAccountAvailable = dbRead.rawQuery("SELECT*FROM tb_user WHERE username = '"+ambilUsername+"'",null);
                    if(checkAccountAvailable.getCount() == 0)
                    {
                        ContentValues values = new ContentValues();
                        values.put(dbHelper.row_username, ambilUsername);
                        values.put(dbHelper.row_password, ambilPassword);
                        values.put(dbHelper.row_name, ambilName);
                        values.put(dbHelper.row_address, ambilAddress);
                        values.put(dbHelper.row_phone, ambilPhone);
                        values.put(dbHelper.row_email, ambilEmail);
                        dbHelper.insertDataUser(values);

                        Intent intent = new Intent(RegisterActivity.this,
                                DashboardActivity.class);
                        Toast.makeText(getApplicationContext(), "Register Berhasil!", Toast.LENGTH_SHORT).show();
                        intent.putExtra("username", ambilUsername);
                        intent.putExtra("password", ambilPassword);
                        intent.putExtra("name", ambilName);
                        intent.putExtra("address", ambilAddress);
                        intent.putExtra("phone", ambilPhone);
                        intent.putExtra("email", ambilEmail);
                        spm.saveSPInt(getBaseContext(), spm.SP_ID, dbHelper.checkUserId(ambilUsername, ambilPassword));
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "Username Akun Ini Sudah Terdaftar. Silahkan Gunakan Username Lain", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    public boolean validasiForm(String ambilUsername, String ambilPassword, String ambilName,
                                String ambilAddress, String ambilPhone, String ambilEmail) {
        if (ambilUsername.length() == 0) {
            regUsername.requestFocus();
            regUsername.setError("Username tidak boleh kosong!");
            return false;
        } else if (ambilPassword.length() <= 5) {
            regPassword.requestFocus();
            regPassword.setError("Password minimal 6 karakter!");
            return false;
        } else if (ambilName.length() == 0) {
            regName.requestFocus();
            regName.setError("Nama tidak boleh kosong!");
            return false;
        } else if (ambilAddress.length() == 0) {
            regAddress.requestFocus();
            regAddress.setError("Alamat tidak boleh kosong!");
            return false;
        } else if (ambilPhone.length() <= 11) {
            regPhone.requestFocus();
            regPhone.setError("Masukkan Nomor Telepon yang Benar!");
            return false;
        } else if (!ambilEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            regEmail.requestFocus();
            regEmail.setError("Email tidak valid!");
            return false;
        }else{
            return true;
        }
    }

    public void backRegister(View view) {
        finish();
    }
}