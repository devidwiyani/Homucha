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
        int id_intent = fromHome.getIntExtra("id_jenis",0);
        switch (id_intent)
        {
            case 1:
                header.setText("Sofa List");
                break;
            case 2:
                header.setText("Table List");
                break;
            case 3:
                header.setText("Chair List");
                break;
            case 4:
                header.setText("Decoration List");
                break;
            case 5:
                header.setText("Storage List");
                break;
            case 6:
                header.setText("Furniture List");
                break;
            case 7:
                header.setText("Bed List");
                break;
            case 8:
                header.setText("Electronic List");
                break;
            case 9:
                header.setText("Best Seller");
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
            int id_jenis = fromHome.getIntExtra("id_jenis",0);
            SQLiteDatabase dbRead = database.getReadableDatabase();
            Cursor cursor1;
            switch (id_jenis){
                case 1:
                    cursor1 = database.readSofa();
                    break;
                case 2:
                    cursor1 = database.readMeja();
                    break;
                case 3:
                    cursor1 = database.readKursi();
                    break;
                case 4:
                    cursor1 = database.readDekorasi();
                    break;
                case 5:
                    cursor1 = database.readPenyimpanan();
                    break;
                case 6:
                    cursor1 = database.readFurnitur();
                    break;
                case 7:
                    cursor1 = database.readKasur();
                    break;
                case 8:
                    cursor1 = database.readElektronik();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + id_jenis);
            }
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
//
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