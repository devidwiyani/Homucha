package com.example.homucha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.ce1, R.drawable.ce2, R.drawable.ce3};
    CardView sofa, meja, kursi, dekorasi, penyimpanan, furnitur, kasur, elektronik;
    ImageButton next;
    RecyclerView bestseller;
    DbHelper database;
    ArrayList<String> produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar;
    ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sofa = v.findViewById(R.id.sofa);
        meja = v.findViewById(R.id.meja);
        kursi = v.findViewById(R.id.kursi);
        dekorasi = v.findViewById(R.id.dekorasi);
        penyimpanan = v.findViewById(R.id.penyimpanan);
        furnitur = v.findViewById(R.id.furnitur);
        kasur = v.findViewById(R.id.kasur);
        elektronik = v.findViewById(R.id.elektronik);
        next = v.findViewById(R.id.next);
        bestseller = v.findViewById(R.id.bestSeller);
        database = new DbHelper(v.getContext());
        SQLiteDatabase dbRead = database.getReadableDatabase();
        Cursor cursor1 = dbRead.rawQuery("SELECT*FROM tb_produk WHERE kategoriId = 9 limit 4",null);
        productAdapter = new ProductAdapter(getActivity(),v.getContext(), produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar, cursor1);
        bestseller.setAdapter(productAdapter);
        bestseller.setLayoutManager(new LinearLayoutManager(v.getContext()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(), 2, GridLayoutManager.VERTICAL, false);
        bestseller.setLayoutManager(gridLayoutManager);
        bestseller.setHasFixedSize(true);

        sofa.setOnClickListener(this);
        meja.setOnClickListener(this);
        kursi.setOnClickListener(this);
        dekorasi.setOnClickListener(this);
        penyimpanan.setOnClickListener(this);
        furnitur.setOnClickListener(this);
        kasur.setOnClickListener(this);
        elektronik.setOnClickListener(this);
        next.setOnClickListener(this);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        carouselView = v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        return v;


//        productAdapter = new ProductAdapter(BestSellerActivity.this,this, produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar, cursor1);
//        bestseller.setAdapter(productAdapter);
//        bestseller.setLayoutManager(new LinearLayoutManager(this));
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        bestseller.setLayoutManager(gridLayoutManager);
//        bestseller.setHasFixedSize(true);

    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(getActivity(), ListSofaActivity.class);
        switch (v.getId()){
            case R.id.sofa:
                intent1.putExtra("id_jenis",1);
                startActivity(intent1);
                break;
            case R.id.meja:
                intent1.putExtra("id_jenis",2);
                startActivity(intent1);
                break;
            case R.id.kursi:
                intent1.putExtra("id_jenis",3);
                startActivity(intent1);
                break;
            case R.id.dekorasi:
                intent1.putExtra("id_jenis",4);
                startActivity(intent1);
                break;
            case R.id.penyimpanan:
                intent1.putExtra("id_jenis",5);
                startActivity(intent1);
                break;
            case R.id.furnitur:
                intent1.putExtra("id_jenis",6);
                startActivity(intent1);
                break;
            case R.id.kasur:
                intent1.putExtra("id_jenis",7);
                startActivity(intent1);
                break;
            case R.id.elektronik:
                intent1.putExtra("id_jenis",8);
                startActivity(intent1);
                break;
            case R.id.next:
                intent1.putExtra("id_jenis",9);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
