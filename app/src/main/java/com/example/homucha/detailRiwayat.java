package com.example.homucha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class detailRiwayat extends AppCompatActivity {
    TextView alamat, totalHarga, statusPengiriman, statusPembayaran, tanggalTransaksi;
    RecyclerView listBarangTerbeli;
    Button finish;
    detailRiwayatAdapter detailRiwayatAdapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout expandableView;
    ImageButton arrowBtn;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);
        DbHelper dataHelper = new DbHelper(this);

        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardView);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_expand_more);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_expand_less);
                }
            }
        });

        SQLiteDatabase dbRead = dataHelper.getReadableDatabase();
        SQLiteDatabase dbWrite = dataHelper.getWritableDatabase();
        alamat = findViewById(R.id.alamat);
        totalHarga = findViewById(R.id.total_harga);
        statusPembayaran = findViewById(R.id.status_pembayaran);
        statusPengiriman = findViewById(R.id.status_pengiriman);
        tanggalTransaksi = findViewById(R.id.tanggal_transaksi);
        listBarangTerbeli = findViewById(R.id.listBarangTerbeli);
        DateFormat input = new SimpleDateFormat("yyyyMMddHHmmss");
        DateFormat output_date = new SimpleDateFormat("dd-MM-yyy");
        DateFormat output_time = new SimpleDateFormat("HH:mm:ss");
        finish = findViewById(R.id.button_finish);
        Locale indonesia = new Locale("in","ID");
        NumberFormat toIndonesia = NumberFormat.getCurrencyInstance(indonesia);
        Intent fromRiwayat = getIntent();
        int id = fromRiwayat.getIntExtra("id_pembelian",0);
        if(id == 0)
        {
            Toast.makeText(this, "cannot process data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Cursor getPembelian = dbRead.rawQuery("SELECT*FROM tb_pembelian WHERE _id = "+id,null);
            getPembelian.moveToFirst();
            String tanggalBeli = getPembelian.getString(getPembelian.getColumnIndex("tanggalPembelian"));
            Date dateTanggalBeli = null;
            try {
                dateTanggalBeli = input.parse(tanggalBeli);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tanggalTransaksi.setText(output_date.format(dateTanggalBeli)+" "+output_time.format(dateTanggalBeli));
            alamat.setText(getPembelian.getString(getPembelian.getColumnIndex("alamatPengiriman")));
            totalHarga.setText(toIndonesia.format(getPembelian.getInt(getPembelian.getColumnIndex("hargaTotal")))+" ("+getPembelian.getString(getPembelian.getColumnIndex("jenisPembayaran"))+")");
            statusPengiriman.setText(getPembelian.getString(getPembelian.getColumnIndex("statusPengiriman")));
            statusPembayaran.setText(getPembelian.getString(getPembelian.getColumnIndex("statusPembayaran")));
            Cursor getDetPembelian = dbRead.rawQuery("SELECT*FROM det_pembelian" +
                    " INNER JOIN tb_produk ON det_pembelian.idBarang = tb_produk._id" +
                    " WHERE det_pembelian.idPembelian = "+id,null);
            layoutManager = new LinearLayoutManager(this);
            listBarangTerbeli.setLayoutManager(layoutManager);
            listBarangTerbeli.setHasFixedSize(true);
            detailRiwayatAdapter = new detailRiwayatAdapter(getDetPembelian);
            listBarangTerbeli.setAdapter(detailRiwayatAdapter);
            if(getPembelian.getString(getPembelian.getColumnIndex("statusPengiriman")) == "Sudah Sampai Di Tujuan")
            {
                finish.setVisibility(View.GONE);
            }
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder.setTitle("Apakah Barang Ini Sudah Diterima?");
                    alertDialogBuilder
                            .setMessage("Click yes to confirm!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dbWrite.execSQL("UPDATE tb_pembelian SET statusPengiriman = 'Sudah Sampai Di Tujuan'" +
                                                    " WHERE _id = "+id);
                                            Toast.makeText(v.getContext(), "Barang sudah sampai. terima kasih sudah membeli di toko kami!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        }
    }
}