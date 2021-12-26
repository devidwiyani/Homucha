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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetailActivity extends AppCompatActivity {
    TextView namaProduk, harga, deskripsi;
    int productId;
    DbHelper dbHelper;
    sharedPrefManager spm;
    ImageView imageProduct;
    Intent intent;
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
        Button addToCart = findViewById(R.id.addToCart);
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

                    SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                    SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
                    Cursor checkId = dbRead.rawQuery("SELECT*FROM tb_carting",null);
                    int idLast;
                    if(checkId.getCount() == 0)
                    {
                        idLast = 1;
                    }
                    else
                    {
                        checkId.moveToLast();
                        idLast = checkId.getInt(checkId.getColumnIndex("_id"))+1;
                    }
                    Cursor checkSameInCart = dbRead.rawQuery("SELECT*FROM tb_carting WHERE idProduk = "+productId,null);
                    if(checkSameInCart.getCount() == 0)
                    {
                        dbWrite.execSQL("INSERT INTO tb_carting VALUES ("+idLast+","+idUser+","+productId+",1)");
                    }
                    else
                    {
                        checkSameInCart.moveToLast();
                        dbWrite.execSQL("UPDATE tb_carting SET jumlahBeli = jumlahBeli + 1" +
                                " WHERE _id = "+checkSameInCart.getInt(checkSameInCart.getColumnIndex("_id")));
                    }
                    Toast.makeText(v.getContext(), "Barang sudah masuk di carting", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}