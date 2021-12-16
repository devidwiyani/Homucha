package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListSofaActivity extends AppCompatActivity {

    DbHelper database;
    RecyclerView daftarSofa;
    ArrayList<String> produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar;
    ProductAdapter productAdapter;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sofa);

        database = new DbHelper(this);
        daftarSofa = findViewById(R.id.daftarSofa);
        ImageView back = findViewById(R.id.back);
        header = findViewById(R.id.header);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent fromHome = getIntent();
        switch (fromHome.getIntExtra("id_jenis",0))
        {
            case 1:
                header.setText("sofa");
                break;
            case 2:
                header.setText("meja");
                break;
            case 3:
                header.setText("kursi");
                break;
            case 4:
                header.setText("dekorasi");
                break;
            case 5:
                header.setText("lemari");
                break;
            case 6:
                header.setText("furniture");
                break;
            case 7:
                header.setText("kasur");
                break;
            case 8:
                header.setText("elektronik");
                break;
            default:
                header.setText("No Data");
                break;
        }
        produk_id = new ArrayList<>();
        kategori_id = new ArrayList<>();
        produk_nama = new ArrayList<>();
        produk_harga = new ArrayList<>();
        produk_deskripsi = new ArrayList<>();
        produk_gambar = new ArrayList<>();

        storeDataInArrays();

        if(fromHome.getIntExtra("id_jenis",0) == 0)
        {
            Toast.makeText(this, "I Cant Read Data. Please Refresh The App First", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Cursor cursor1 = database.readSpecCategory(fromHome.getIntExtra("id_jenis",0));
            if(cursor1.getCount() == 0)
            {
                Toast.makeText(this, "No Data In Here", Toast.LENGTH_SHORT).show();
            }
            else
            {
                productAdapter = new ProductAdapter(ListSofaActivity.this,this, produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar, cursor1);
                daftarSofa.setAdapter(productAdapter);
                daftarSofa.setLayoutManager(new LinearLayoutManager(this));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                daftarSofa.setLayoutManager(gridLayoutManager);
                daftarSofa.setHasFixedSize(true);
            }

        }
    }
    void storeDataInArrays(){
        Cursor cursor = database.readSofa();
        while (cursor.moveToNext()){
            produk_id.add(cursor.getString(0));
            kategori_id.add(cursor.getString(1));
            produk_nama.add(cursor.getString(2));
            produk_harga.add(cursor.getString(3));
            produk_deskripsi.add(cursor.getString(4));
            produk_gambar.add(cursor.getString(5));
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//        loadData();
//    }
//
//    private void initView() {
//        listViewContact = findViewById(R.id.listViewContact);
//    }
//
//    private void loadData() {
//        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//        List<Contact> contacts = databaseHelper.findAll();
//        if (contacts != null) {
//            listViewContact.setAdapter(new ContactListAdapter(getApplicationContext(), contacts));
//        }
//    }

}