package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private DbHelper database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList productImageList;
    private ArrayList productNameList;
    private ArrayList productAmountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        productImageList = new ArrayList();
        productNameList = new ArrayList();
        productAmountList = new ArrayList();
        database = new DbHelper(getBaseContext());
        recyclerView = findViewById(R.id.daftarCart);
        getData();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CartAdapter(productImageList, productNameList, productAmountList);
        recyclerView.setAdapter(adapter);

    }

    protected void getData(){
//        SQLiteDatabase ReadData = database.getReadableDatabase();
//        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DbHelper.table_cart, null);
//
//        cursor.moveToFirst();
//
//        for (int count=0; count < cursor.getCount(); count++){
//
//        }
    }
}