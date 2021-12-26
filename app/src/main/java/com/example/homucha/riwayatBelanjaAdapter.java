package com.example.homucha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class riwayatBelanjaAdapter extends RecyclerView.Adapter<riwayatBelanjaAdapter.ViewHolder> {
    Cursor data;
    DbHelper dataHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;

    public riwayatBelanjaAdapter(Cursor data)
    {
        this.data = data;
    }
    @NonNull
    @Override
    public riwayatBelanjaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new riwayatBelanjaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull riwayatBelanjaAdapter.ViewHolder holder, int position) {
        DateFormat input = new SimpleDateFormat("yyyyMMddHHmmss");
        DateFormat output_date = new SimpleDateFormat("dd-MM-yyy");
        DateFormat output_time = new SimpleDateFormat("HH:mm:ss");
        int positionNow = position;
        data.moveToPosition(positionNow);
        int id_pembelian = data.getInt(data.getColumnIndex("_id"));
        dataHelper = new DbHelper(holder.itemView.getContext());
        dbRead = dataHelper.getReadableDatabase();
        Cursor detailBarang = dbRead.rawQuery("SELECT*FROM det_pembelian WHERE idPembelian = "+id_pembelian,null);
        Cursor detailBarangImage = dbRead.rawQuery("SELECT gambar FROM det_pembelian" +
                " INNER JOIN tb_produk ON det_pembelian.idBarang = tb_produk._id" +
                " WHERE idPembelian = "+id_pembelian,null);
        if(detailBarang.getCount() == 0)
        {
            Toast.makeText(holder.itemView.getContext(), "Gk Ada Data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            detailBarangImage.moveToLast();
            holder.imageProduct.setImageResource(detailBarangImage.getInt(detailBarangImage.getColumnIndex("gambar")));
            String tanggalBeli = data.getString(data.getColumnIndex("tanggalPembelian"));
            Date dateTanggalBeli = null;
            try {
                dateTanggalBeli = input.parse(tanggalBeli);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.namaProduct.setText("Pembelian Tanggal "+output_date.format(dateTanggalBeli)+"Jam "+output_time.format(dateTanggalBeli));
            int jumlahBarang = 0;
            detailBarang.moveToFirst();
            for(int i = 0; i < detailBarang.getCount(); i++)
            {
                jumlahBarang = jumlahBarang + (detailBarang.getInt(detailBarang.getColumnIndex("jumlahBeli")));
            }
            Locale indonesia = new Locale("in","ID");
            NumberFormat toIndonesia = NumberFormat.getCurrencyInstance(indonesia);
            holder.jumlahBeli.setText(String.valueOf(jumlahBarang));
            holder.hargaTotal.setText(toIndonesia.format(data.getFloat(data.getColumnIndex("hargaTotal"))));
            holder.cardBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(v.getContext(), detailRiwayat.class);
                    toDetail.putExtra("id_pembelian",id_pembelian);
                    v.getContext().startActivity(toDetail);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView namaProduct, jumlahBeli, hargaTotal;
        CardView cardBarang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.image_product);
            hargaTotal = itemView.findViewById(R.id.harga_bayar);
            namaProduct = itemView.findViewById(R.id.nama_riwayat);
            jumlahBeli = itemView.findViewById(R.id.jumlah_beli);
            cardBarang = itemView.findViewById(R.id.card_barang);

        }
    }

}
