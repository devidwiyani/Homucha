package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {
    TextView namaProduk, harga, deskripsi;
    int productId;
    DbHelper dbHelper;
    sharedPrefManager spm;
    ImageView imageProduct;
    Intent intent;
    Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        intent = getIntent();
        productId = intent.getIntExtra("id",0);
        namaProduk = findViewById(R.id.t2);
        harga = findViewById(R.id.harga);
        deskripsi = findViewById(R.id.desc);
        imageProduct = findViewById(R.id.img1);
        ImageView back = findViewById(R.id.back);
        addToCart = findViewById(R.id.addToCart);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbHelper = new DbHelper(this);
        Cursor checkProduct = dbHelper.readProductId(productId);
        if(checkProduct.getCount() == 0)
        {
            Toast.makeText(this, "I Cannot Read Data. Please Reset This First", Toast.LENGTH_SHORT).show();
        }
        else
        {
            checkProduct.moveToFirst();
            imageProduct.setImageResource(checkProduct.getInt(checkProduct.getColumnIndex("gambar")));
            namaProduk.setText(checkProduct.getString(checkProduct.getColumnIndex("nama")));
            harga.setText("Rp."+String.valueOf(checkProduct.getInt(checkProduct.getColumnIndex("harga")))+",00");
            deskripsi.setText(checkProduct.getString(checkProduct.getColumnIndex("deskripsi")));
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idUser = spm.getSPId(v.getContext());
                    dbHelper.insertCart(idUser,productId);
                    Toast.makeText(v.getContext(), "Barang sudah masuk di carting", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}