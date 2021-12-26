package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class riwayatBelanja extends AppCompatActivity {
    RecyclerView listBarang;
    DbHelper dataHelper;
    SQLiteDatabase dbRead;
    riwayatBelanjaAdapter riwayatBelanjaAdapter;
    sharedPrefManager spm;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_belanja);

        listBarang = findViewById(R.id.daftarRiwayat);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataHelper = new DbHelper(this);
        dbRead = dataHelper.getReadableDatabase();
        int idUser = spm.getSPId(this);
        Cursor getAllPembelian = dbRead.rawQuery("SELECT*FROM tb_pembelian WHERE idUser = "+idUser,null);
        layoutManager = new LinearLayoutManager(this);
        listBarang.setLayoutManager(layoutManager);
        listBarang.setHasFixedSize(true);
        riwayatBelanjaAdapter = new riwayatBelanjaAdapter(getAllPembelian);
        listBarang.setAdapter(riwayatBelanjaAdapter);
    }
}