package com.example.homucha;

import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

public class detailRiwayatAdapter extends RecyclerView.Adapter<detailRiwayatAdapter.ViewHolder>{
    Cursor inDetail;
    public detailRiwayatAdapter(Cursor cursor)
    {
        this.inDetail = cursor;
    }
    @NonNull
    @Override
    public detailRiwayatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new detailRiwayatAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull detailRiwayatAdapter.ViewHolder holder, int position) {
        inDetail.moveToPosition(position);
        holder.image.setImageResource(inDetail.getInt(inDetail.getColumnIndex("gambar")));
        holder.namaBarang.setText(inDetail.getString(inDetail.getColumnIndex("nama")));
        Locale indonesia = new Locale("in","ID");
        NumberFormat toIndonesia = NumberFormat.getCurrencyInstance(indonesia);
        holder.hargaBarang.setText(toIndonesia.format(inDetail.getInt(inDetail.getColumnIndex("hargaSatuan"))));
        holder.jumlahBeli.setText(inDetail.getString(inDetail.getColumnIndex("jumlahBeli")));
    }

    @Override
    public int getItemCount() {
        return inDetail.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView namaBarang, hargaBarang, jumlahBeli;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_product);
            namaBarang = itemView.findViewById(R.id.nama_riwayat);
            jumlahBeli = itemView.findViewById(R.id.jumlah_beli);
            hargaBarang = itemView.findViewById(R.id.harga_bayar);
        }
    }
}
