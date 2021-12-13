package com.example.homucha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String database_name = "db_homucha";
    public static final String table_name = "tb_user";
    public static final String id_user = "_id";
    public static final String row_username = "username";
    public static final String row_password = "password";
    public static final String row_name = "name";
    public static final String row_address = "address";
    public static final String row_phone = "phone";
    public static final String row_email = "email";

    public static final String table_produk = "tb_produk";
    public static final String id_produk = "_id";
    public static final String kategori_id = "kategoriId";
    public static final String row_nproduk = "nama";
    public static final String row_harga = "harga";
    public static final String row_deskripsi = "deskripsi";
    public static final String row_gambar = "gambar";

    public static final String table_kategori = "tb_katgeori";
    public static final String id_kategori = "_id";
    public static final String row_nkategori = "nama";

    public static final String table_cart = "tb_user";
    public static final String id_cart = "id_cart";
    public static final String id_product = "id_product";
    public static final String row_product_name = "product_name";
    public static final String row_product_amount = "product_amount";


    private SQLiteDatabase database;

    public DbHelper(Context context) {
        super(context, database_name, null, 2);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String queryUser = "CREATE TABLE " + table_name + "(" + id_user + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_username + " TEXT," + row_password + " TEXT," + row_name + " TEXT,"
                + row_address + " TEXT," + row_phone + " TEXT,"
                + row_email + " TEXT)";
        database.execSQL(queryUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(database);
    }

    public void insertDataUser(ContentValues values) {

        database.insert(table_name, null, values);
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {id_user};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        if (count > 0)
            return true;
        else
            return false;
    }
}
