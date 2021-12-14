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
        String queryProduk = "CREATE TABLE " + table_produk + "(" + id_produk + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + kategori_id + " INTEGER," + row_nproduk + " TEXT," + row_harga + " INTEGER,"
                + row_deskripsi + " TEXT," + row_gambar + " INTEGER, "
                + " FOREIGN KEY ("+kategori_id+") REFERENCES "+table_kategori+"("+id_kategori+"));";
        database.execSQL(queryProduk);
        String queryKategori = "CREATE TABLE " + table_kategori + "(" + id_kategori + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_nkategori + " TEXT)";
        database.execSQL(queryKategori);
        database.execSQL("CREATE TABLE tb_carting (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUser INTEGER," +
                "idProduk INTEGER," +
                "jumlahBeli INTEGER" +
                ")");
        database.execSQL("CREATE TABLE tb_pembelian(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUser INTEGER," +
                "alamatPengiriman VARCHAR(100)," +
                "hargaTotal FLOAT(20,2)," +
                "jenisPembayaran VARCHAR(100)," +
                "statusPembayaran VARCHAR(50)," +
                "statusPengiriman VARCHAR(50))");
        database.execSQL("CREATE TABLE det_pembelian(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idPembelian INTEGER," +
                "idBarang INTEGER," +
                "hargaSatuan FLOAT(20,2)," +
                "jumlahBeli INTEGER)");
        setKategoriBarang();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + table_name);
        database.execSQL("DROP TABLE IF EXISTS " + table_produk);
        database.execSQL("DROP TABLE IF EXISTS " + table_kategori);
        database.execSQL("DROP TABLE IF EXISTS tb_carting");
        database.execSQL("DROP TABLE IF EXISTS tb_pembelian");
        database.execSQL("DROP TABLE IF EXISTS det_pembelian");
        onCreate(database);
    }

    public Cursor readSofa(){
        String sql = "select * from "+table_produk+" WHERE kategoriId = 1";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, null);
        }
        return cursor;
    }

    public Cursor readSpecCategory(String category){
        String sql = "select * from "+table_produk+"" +
                "INNER JOIN tb_kategori ON tb_produk.kategoriId = tb_kategori._id " +
                "WHERE tb_kategori.nama = "+category;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, null);
        }
        return cursor;
    }

    public Cursor readProductId(int id)
    {
        String sql = "select * from "+table_produk+" " +
                "WHERE _id = "+id;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, null);
        }
        return cursor;
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

        if(count > 0)
            return true;
        else
            return false;
    }

    public void editProfile(String usernameOld, String username, String password,  String name, String address,
                            String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(row_username, username);
        values.put(row_password, password);
        values.put(row_name, name);
        values.put(row_address, address);
        values.put(row_phone, phone);
        values.put(row_email, email);
        db.update(table_name, values, "username=?", new String[]{usernameOld});
        db.close();
    }

    public int checkUserId(String username, String password)
    {
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor check = dbRead.rawQuery("SELECT*FROM tb_user WHERE username = '"+username+"' AND password = '"+password+"'",null);
        check.moveToFirst();
        return check.getInt(check.getColumnIndex("_id"));
    }

    public void setKategoriBarang()
    {
        SQLiteDatabase dbWrite = getWritableDatabase();
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(1,'sofa')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(2,'meja')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(3,'kursi')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(4,'dekorasi')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(5,'lemari')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(6,'furniture')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(7,'kasur')");
        dbWrite.execSQL("INSERT INTO tb_kategori VALUES(8,'elektronik')");
    }

    public void insertCart(int id_user, int id_barang)
    {
        SQLiteDatabase dbWrite = getWritableDatabase();
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor checkId = dbRead.rawQuery("SELECT*FROM tb_carting",null);
        int idLast;
        if(checkId.getCount() == 0)
        {
            idLast = 1;
        }
        else
        {
            checkId.moveToLast();
            idLast = checkId.getInt(checkId.getColumnIndex("_id"))+1;
        }
        Cursor checkSameInCart = dbRead.rawQuery("SELECT*FROM tb_carting WHERE idProduk = "+id_barang,null);
        if(checkSameInCart.getCount() == 0)
        {
            dbWrite.execSQL("INSERT INTO tb_carting VALUES ("+idLast+","+id_user+","+id_barang+",1)");
        }
        else
        {
            checkSameInCart.moveToLast();
            dbWrite.execSQL("UPDATE tb_carting SET jumlahBeli = jumlahBeli + 1" +
                    "WHERE _id = "+checkSameInCart.getInt(checkSameInCart.getColumnIndex("_id")));
        }
    }
}
