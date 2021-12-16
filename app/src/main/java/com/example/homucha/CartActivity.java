package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private DbHelper database;
    sharedPrefManager spm;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList productImageList;
    private ArrayList productNameList;
    private ArrayList productAmountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        int idUser= spm.getSPId(this);
        productImageList = new ArrayList();
        productNameList = new ArrayList();
        productAmountList = new ArrayList();
        database = new DbHelper(getBaseContext());
        recyclerView = findViewById(R.id.daftarCart);
        getData();
        Cursor getInCart = database.getInCart(idUser);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CartAdapter(productImageList, productNameList, productAmountList,getInCart);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, String.valueOf(getInCart.getCount()), Toast.LENGTH_SHORT).show();
        Button makeOrder = findViewById(R.id.button_purchase);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMakeOrder = new Intent(v.getContext(), com.example.homucha.makeOrder.class);
                startActivity(toMakeOrder);
            }
        });
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