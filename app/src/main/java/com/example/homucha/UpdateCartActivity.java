package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateCartActivity extends AppCompatActivity {

    String produkName, produkHarga, produkJumlah;
    int id;
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

        id = getIntent().getIntExtra("id",0);
        produkName = getIntent().getStringExtra("produkName");
        produkHarga = getIntent().getStringExtra("produkHarga");
        produkJumlah = getIntent().getStringExtra("produkJumlah");
        dbHelper=new DbHelper(getApplicationContext());
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        Cursor dataInCart = dbRead.rawQuery("SELECT" +
                " tb_carting._id AS 'idInCart'," +
                " tb_carting.idUser," +
                " tb_carting.idProduk," +
                " tb_produk.nama," +
                " tb_produk.harga," +
                " tb_produk.gambar," +
                " tb_carting.jumlahBeli" +
                " FROM tb_carting" +
                " INNER JOIN tb_produk ON tb_carting.idProduk = tb_produk._id" +
                " WHERE idInCart ="+id,null);
        dataInCart.moveToFirst();
        namaProduk.setText(dataInCart.getString(dataInCart.getColumnIndex("nama")));
        hargaProduk.setText(dataInCart.getString(dataInCart.getColumnIndex("harga")));
        jumlahProduk.setText(dataInCart.getString(dataInCart.getColumnIndex("jumlahBeli")));
        namaProduk.setFocusable(false);
        hargaProduk.setFocusable(false);
        namaProduk.setClickable(false);
        hargaProduk.setClickable(false);

        dbHelper=new DbHelper(getApplicationContext());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJumlah = jumlahProduk.getText().toString();
                String getNama = namaProduk.getText().toString();
                String getHarga = hargaProduk.getText().toString();

                dbHelper.updateJumlah(String.valueOf(id), getJumlah);

                Toast.makeText(UpdateCartActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                Intent refreshCart = new Intent(v.getContext(), CartActivity.class);
                v.getContext().startActivity(refreshCart);
                finish();
            }
        });

    }
}