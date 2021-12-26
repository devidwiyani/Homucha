package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateCartActivity extends AppCompatActivity {

    String id, produkName, produkHarga, produkJumlah;
    TextView namaProduk, hargaProduk;
    EditText jumlahProduk;
    Button btnUpdate;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cart);

        namaProduk = findViewById(R.id.nama_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        jumlahProduk = findViewById(R.id.jumlah_produk);
        btnUpdate = findViewById(R.id.btn_update);

        id = getIntent().getStringExtra("id");
        produkName = getIntent().getStringExtra("produkName");
        produkHarga = getIntent().getStringExtra("produkHarga");
        produkJumlah = getIntent().getStringExtra("produkJumlah");

        namaProduk.setText(produkName);
        hargaProduk.setText(produkHarga);
        jumlahProduk.setText(produkJumlah);

        dbHelper=new DbHelper(getApplicationContext());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getId = id;
                String getJumlah = jumlahProduk.getText().toString();
                String getNama = namaProduk.getText().toString();
                String getHarga = hargaProduk.getText().toString();

                dbHelper.updateJumlah(getId, getJumlah);

                Toast.makeText(UpdateCartActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}