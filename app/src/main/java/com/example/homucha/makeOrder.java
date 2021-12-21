package com.example.homucha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class makeOrder extends AppCompatActivity {
    Spinner listPembayaran;
    DbHelper dbHelper;
    SQLiteDatabase dbRead, dbWrite;
    RecyclerView listInCart;
    TextView hargaTotal;
    EditText alamat;
    RecyclerView.LayoutManager layoutManager;
    sharedPrefManager spm;
    private ArrayList productImageList;
    private ArrayList productNameList;
    private ArrayList productAmountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        listPembayaran = findViewById(R.id.spinner);
        dbHelper = new DbHelper(this);
        int idUser= spm.getSPId(this);

        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.jenis_pembayaran, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listPembayaran.setAdapter(adapter);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alamat = findViewById(R.id.alamatPengiriman);
        listInCart = findViewById(R.id.listInCart1);
        Cursor getInCart = dbHelper.getInCart(idUser);
        layoutManager = new LinearLayoutManager(this);
        listInCart.setLayoutManager(layoutManager);
        listInCart.setHasFixedSize(true);
        productImageList = new ArrayList();
        productNameList = new ArrayList();
        productAmountList = new ArrayList();
        CartAdapter adapter1 = new CartAdapter(productImageList, productNameList, productAmountList,getInCart);
        listInCart.setAdapter(adapter1);
        hargaTotal = findViewById(R.id.totalHarga);
        Locale indonesia = new Locale("in","ID");
        NumberFormat toIndonesia = NumberFormat.getCurrencyInstance(indonesia);
        hargaTotal.setText(toIndonesia.format(dbHelper.getSumHarga(idUser)));
        Button purchase = findViewById(R.id.button_purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_pembelian;
                Cursor checkPembelian = dbRead.rawQuery("SELECT*FROM tb_pembelian",null);
                if(checkPembelian.getCount() == 0)
                {
                    id_pembelian = 1;
                }
                else
                {
                    id_pembelian = getInCart.getCount() + 1;
                }
                dbWrite.execSQL("INSERT INTO tb_pembelian " +
                        "VALUES("+id_pembelian+","+idUser+",'"+alamat.getText()+"',"+dbHelper.getSumHarga(idUser)+"" +
                        ",'"+listPembayaran.getSelectedItem().toString()+"','Lunas','Sedang Diantar')");
                getInCart.moveToFirst();
                int id_detail_pembelian;
                Cursor getLastDetPembelian = dbRead.rawQuery("SELECT*FROM det_pembelian",null);
                if(getLastDetPembelian.getCount() == 0)
                {
                    id_detail_pembelian = 1;
                }
                else
                {
                    id_detail_pembelian = getLastDetPembelian.getCount()+1;
                }
                for(int i = 0; i < getInCart.getCount(); i++)
                {
                    dbWrite.execSQL("INSERT INTO det_pembelian VALUES("+id_detail_pembelian+","+id_pembelian+"" +
                            ","+getInCart.getInt(getInCart.getColumnIndex("idProduk"))+"" +
                            ","+getInCart.getInt(getInCart.getColumnIndex("harga"))+"" +
                            ","+getInCart.getInt(getInCart.getColumnIndex("jumlahBeli"))+")");
                    id_detail_pembelian++;
                    getInCart.moveToNext();
                }
                dbWrite.execSQL("DELETE FROM tb_carting WHERE idUser = "+idUser);
                Toast.makeText(v.getContext(), "Data pembelian sudah dimasukan. Silahkan cek di riwayat pembelian. Selamat berbelanja kembali!", Toast.LENGTH_SHORT).show();
                Intent toMainMenu = new Intent(v.getContext(),DashboardActivity.class);
                finish();
                startActivity(toMainMenu);
            }
        });
    }
}