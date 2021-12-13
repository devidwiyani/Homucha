package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {
    Cursor cursor;
    EditText txUsername, txPassword, txName, txAddress, txPhone, txEmail;
    String oldUsername, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txUsername = findViewById(R.id.editUsername);
        txPassword = findViewById(R.id.editPassword);
        txName = findViewById(R.id.editName);
        txAddress = findViewById(R.id.editAddress);
        txPhone = findViewById(R.id.editPhone);
        txEmail = findViewById(R.id.editEmail);

        Bundle account = getIntent().getExtras();
        profile = account.getString("editProfile");

        DbHelper dbHelper = new DbHelper(EditProfileActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_user WHERE name = '" +
                getIntent().getStringExtra("editProfile") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            txUsername.setText(cursor.getString(1).toString());
            txPassword.setText(cursor.getString(2).toString());
            txName.setText(cursor.getString(3).toString());
            txAddress.setText(cursor.getString(4).toString());
            txPhone.setText(cursor.getString(5).toString());
            txEmail.setText(cursor.getString(6).toString());
            oldUsername = cursor.getString(1);
        }
    }

    public void btnEditProfile(View view){

        String username = txUsername.getText().toString().trim();
        String password = txPassword.getText().toString().trim();
        String name = txName.getText().toString().trim();
        String address = txAddress.getText().toString().trim();
        String phone = txPhone.getText().toString().trim();
        String email = txEmail.getText().toString().trim();

        Boolean res = validasi(username, password, name, address, phone, email);
        if(res){
            DashboardActivity.dbHelper.editProfile(
                    oldUsername, username, password, name, address,
                    phone,email
            );
            Toast.makeText(EditProfileActivity.this, "Edit Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfileActivity.this,DashboardActivity.class);
            intent.putExtra("UserNameLogin", username);
            startActivity(intent);
            finish();
        }
    }

    public boolean validasi(String username, String password,  String name, String address,
                            String phone, String email){
        if (username.length() == 0) {
            txUsername.requestFocus();
            txUsername.setError("Username tidak boleh kosong!");
            return false;
        } else if (password.length() <= 5) {
            txPassword.requestFocus();
            txPassword.setError("Password minimal 6 karakter!");
            return false;
        } else if (name.length() == 0) {
            txName.requestFocus();
            txName.setError("Nama tidak boleh kosong!");
            return false;
        } else if (address.length() == 0) {
            txAddress.requestFocus();
            txAddress.setError("Alamat tidak boleh kosong!");
            return false;
        } else if (phone.length() <= 11) {
            txPhone.requestFocus();
            txPhone.setError("Masukkan Nomor Telepon yang Benar!");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            txEmail.requestFocus();
            txEmail.setError("Email tidak valid!");
            return false;
        }else{
            return true;
        }
    }
}