package com.example.homucha;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList productImageList;
    private ArrayList productNameList;
    private ArrayList productAmountList;
    private ArrayList productPriceList;
    private Cursor listBarang;
    private ImageButton btnDelete;
    DbHelper dbHelper;

    public CartAdapter(ArrayList productImageList, ArrayList productNameList, ArrayList productAmountList, ArrayList productPriceList, Cursor cursor){
        this.productImageList = productImageList;
        this.productNameList = productNameList;
        this.productAmountList = productAmountList;
        this.productPriceList = productPriceList;
        this.listBarang = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final String productImage = (String) productImageList.get(position);
        //final String productName = (String) productNameList.get(position);
        //final String productAmount = (String) productAmountList.get(position);
        listBarang.moveToPosition(position);
        holder.productImage.setImageResource(listBarang.getInt(listBarang.getColumnIndex("gambar")));
        holder.productName.setText(listBarang.getString(listBarang.getColumnIndex("nama")));
        holder.productAmount.setText(listBarang.getString(listBarang.getColumnIndex("jumlahBeli")));
        holder.productPrice.setText(listBarang.getString(listBarang.getColumnIndex("harga")));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), UpdateCartActivity.class);

                i.putExtra("id", listBarang.getString(listBarang.getInt(0)));
                i.putExtra("produkName", listBarang.getString(listBarang.getColumnIndex("nama")));
                i.putExtra("produkJumlah", listBarang.getString(listBarang.getColumnIndex("jumlahBeli")));
                i.putExtra("produkHarga", listBarang.getString(listBarang.getColumnIndex("harga")));

                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBarang.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productName, productAmount, productPrice;
        DbHelper dbHelper;

        ViewHolder(View itemView){
            super(itemView);
            productImage = itemView.findViewById(R.id.image_product);
            productName = itemView.findViewById(R.id.nama_riwayat);
            productAmount =itemView.findViewById(R.id.jumlah_beli);
            productPrice = itemView.findViewById(R.id.harga_bayar);
            dbHelper = new DbHelper(itemView.getContext());
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = listBarang.getInt(0);
                    dbHelper.deleteCart(id);
                    Toast.makeText(v.getContext(), "ID Cart deleted :" +id, Toast.LENGTH_SHORT).show();
                    Intent refreshCart = new Intent(v.getContext(), CartActivity.class);
                    v.getContext().startActivity(refreshCart);
                    ((Activity)v.getContext()).finish();
                }
            });

        }
    }

}


