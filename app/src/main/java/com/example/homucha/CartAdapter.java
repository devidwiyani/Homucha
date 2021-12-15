package com.example.homucha;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList productImageList;
    private ArrayList productNameList;
    private ArrayList productAmountList;
    private Cursor listBarang;

    public CartAdapter(ArrayList productImageList, ArrayList productNameList, ArrayList productAmountList, Cursor cursor){
        this.productImageList = productImageList;
        this.productNameList = productNameList;
        this.productAmountList = productAmountList;
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
    }

    @Override
    public int getItemCount() {
        return listBarang.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productName, productAmount;

        ViewHolder(View itemView){
            super(itemView);
            productImage = itemView.findViewById(R.id.image_product);
            productName = itemView.findViewById(R.id.nama_product);
            productAmount =itemView.findViewById(R.id.amount_product);

        }
    }
}
