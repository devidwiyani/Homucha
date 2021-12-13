package com.example.homucha;

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

    CartAdapter(ArrayList productImageList, ArrayList productNameList, ArrayList productAmountList){
        this.productImageList = productImageList;
        this.productNameList = productNameList;
        this.productAmountList = productAmountList;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String productImage = (String) productImageList.get(position);
        final String productName = (String) productNameList.get(position);
        final String productAmount = (String) productAmountList.get(position);

        holder.productImage.setImageDrawable(Drawable.createFromPath(productImage));
        holder.productName.setText(productName);
        holder.productAmount.setText(productAmount);
    }

    @Override
    public int getItemCount() {
        return productNameList.size();
    }
}
