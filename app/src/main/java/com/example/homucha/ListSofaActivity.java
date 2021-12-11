package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ListSofaActivity extends AppCompatActivity {

    DbHelper database;
    RecyclerView daftarSofa;
    ArrayList<String> produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sofa);

        database = new DbHelper(this);
        daftarSofa = findViewById(R.id.daftarSofa);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        produk_id = new ArrayList<>();
        kategori_id = new ArrayList<>();
        produk_nama = new ArrayList<>();
        produk_harga = new ArrayList<>();
        produk_deskripsi = new ArrayList<>();
        produk_gambar = new ArrayList<>();

        storeDataInArrays();

        productAdapter = new ProductAdapter(ListSofaActivity.this,this, produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar);
        daftarSofa.setAdapter(productAdapter);
        daftarSofa.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        daftarSofa.setLayoutManager(gridLayoutManager);
        daftarSofa.setHasFixedSize(true);
    }

    void storeDataInArrays(){
        Cursor cursor = database.readAllData();
        while (cursor.moveToNext()){
            produk_id.add(cursor.getString(0));
            kategori_id.add(cursor.getString(1));
            produk_nama.add(cursor.getString(2));
            produk_harga.add(cursor.getString(3));
            produk_deskripsi.add(cursor.getString(4));
            produk_gambar.add(cursor.getString(5));
        }
    }

    private void prosesRecyclerViewAdapter() {
//        RecyclerView recyclerView = findViewById(R.id.daftarSofa);
//        MyAdapterRecipe adapter = new MyAdapterRecipe(dataholder, this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setHasFixedSize(true);
    }
}