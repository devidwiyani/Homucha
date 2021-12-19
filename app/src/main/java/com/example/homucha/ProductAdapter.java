package com.example.homucha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList produk_id, kategori_id, produk_nama, produk_harga, produk_deskripsi, produk_gambar;
    private Cursor listBarang;

    public ProductAdapter(Activity activity, Context context, ArrayList<String> produk_id, ArrayList<String> kategori_id, ArrayList<String> produk_nama, ArrayList<String> produk_harga, ArrayList<String> produk_deskripsi, ArrayList<String> produk_gambar, Cursor cursor) {
        this.activity = activity;
        this.context = context;
        this.produk_id = produk_id;
        this.kategori_id = kategori_id;
        this.produk_nama = produk_nama;
        this.produk_harga = produk_harga;
        this.produk_deskripsi = produk_deskripsi;
        this.produk_gambar = produk_gambar;
        this.listBarang = cursor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.product_image.setText(String.valueOf(produk_gambar.get(holder.getAdapterPosition())));
        listBarang.moveToPosition(position);
        int id_barang = listBarang.getInt(listBarang.getColumnIndex("_id"));
        holder.product_name.setText(String.valueOf(produk_nama.get(position)));
        holder.product_price.setText(String.valueOf(produk_harga.get(position)));
        holder.product_image.setImageResource(listBarang.getInt(listBarang.getColumnIndex("gambar")));
        int positionReal = position;
        //Recyclerview onClickListener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("id", id_barang);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produk_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_price;
        CardView cardView;
        ImageView product_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.img);
            product_name = itemView.findViewById(R.id.nama);
            product_price = itemView.findViewById(R.id.harga);
            cardView = itemView.findViewById(R.id.cardView);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            cardView.setAnimation(translate_anim);
        }
    }
}
