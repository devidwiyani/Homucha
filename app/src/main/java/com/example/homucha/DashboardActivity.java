package com.example.homucha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    static DbHelper dbHelper;
    String mainUser;
    int idUser;
    protected Cursor cursor;
    String name, email, address, phone;
    public Bundle bundleFragment;
    SQLiteDatabase dbRead, dbWrite;
    sharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle login = getIntent().getExtras();
        idUser = spm.getSPId(this);
        if(idUser == 0)
        {
            Intent toLogin = new Intent(this, LoginActivity.class);
            startActivity(toLogin);
        }
        else {
            dbHelper = new DbHelper(this);

            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnItemSelectedListener(navListener);

            DbHelper dbHelper = new DbHelper(DashboardActivity.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM tb_user WHERE _id = " +
                    idUser, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                name = cursor.getString(3).toString();
                email = cursor.getString(6).toString();
                address = cursor.getString(4).toString();
                phone = cursor.getString(5).toString();
            }
            Cursor checkItem = db.rawQuery("SELECT*FROM tb_produk",null);
            if(checkItem.getCount() == 0)
            {
                //SOFA
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(1,1," +
                        "'CASABEL Estrella',19400000," +
                        "'ÄPPLARYD sofa will be your home’s comfy oasis. An eye-catcher that reflects your personality and style. Great to sit, lie down and hang out on. And with lots of space for the whole family, year after year.',"+R.drawable.sofa_hitam+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(2,1," +
                        "'Ebonia',2825000," +
                        "'Teman terbaik Anda untuk menonton film dan juga pusat perhatian dari ruangan. Kami mencoba memikirkan segalanya. Dari dudukan yang luas dan nyaman hingga sandaran lengan yang membulat dan sarung penutup yang lembut.',"+R.drawable.sofa2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(3,1," +
                        "'Ivaro',3350000," +
                        "'Bantal dudukan dan sandaran memberi penyangga yang nyaman untuk tubuh anda dan dengan mudah mendapatkan kembali bentuknya karena berisi busa berketahanan tinggi dan serat polyester.',"+R.drawable.sofa3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(4,1," +
                        "'Zyo Hererra',2899000," +
                        "'We launched KLIPPAN sofa in the 1980s and it’s still a favourite. Its comfortable, fits almost everywhere and has many covers to choose from. A modern and timeless classic!',"+R.drawable.sofa4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(5,1," +
                        "'Olc Sofabed Wellington',1999000," +
                        "'Jika suka dengan tampilannya, maka Anda harus mencobanya! Dudukan yang dalam, bantak punggung yang dapat dipindahkan dan suspensi kain membuat tempat duduk sangat nyaman. Ciptakan kombinasi Anda sendiri lalu duduk dan bersantai.',"+R.drawable.sofa5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(6,1," +
                        "'Morres Queen',1950000," +
                        "'Jika suka dengan tampilannya, maka Anda harus mencobanya! Dudukan yang dalam, bantak punggung yang dapat dipindahkan dan suspensi kain membuat tempat duduk sangat nyaman. Ciptakan kombinasi Anda sendiri lalu duduk dan bersantai.',"+R.drawable.sofa6+")");

                //MEJA
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(7,2," +
                        "'ARKELSTORP',2999000," +
                        "'Dapat diletakkan di tengah ruangan karena bagian belakang dilapisi. Kayu solid adalah bahan alami tahan lama. Penyetop laci untuk menghindar laci ditarik terlalu jauh.',"+R.drawable.meja1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(8,2," +
                        "'HEMNES',2499000," +
                        "'Meja tamu, warna putih/cokelat muda, 90x90 cm. Keindahan alami kayu pinus solid. Bahan yang tahan lama dan terbarukan yang menjaga keaslian karakternya setiap tahunnya. Terlihat cocok dikombinasikan dengan perabotan lainnya dari seri HEMNES.',"+R.drawable.meja2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(9,2," +
                        "'LOMMARP',2499000," +
                        "'Meja, biru gelap-hijau, 90x54 cm. Meja untuk menggambar, belajar, membaca dan melakukan hobi yang muat di ruang kecil. Permukaannya tahan lama dan mudah dibersihkan. Pengaturan kabel built-in menjaga kabel tidak terlihat tetapi mudah dijangkau. Ruang penyimpan tambahan di bawah laci tarik.',"+R.drawable.meja3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(10,2," +
                        "'KRAGSTA',1499000," +
                        "'Coffee table, light beige, 90 cm. Round, soft shapes and crafted details are characteristic of KRAGSTA coffee and nesting tables. Sturdy construction and timeless style make them easy to match with other furniture and love for many years!',"+R.drawable.meja4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(11,2," +
                        "'NORDVIKEN',5999000," +
                        "'Meja yang dapat dipanjangkan, hitam, 152/223x95 cm. Meja makan kayu yang luas ini memiliki kesan tradisional dan akan segera menjadi tempat berkumpul alami di rumah Anda. Konstruksi yang kokoh dan mekanisme perpanjangan yang halus menjadikannya favorit yang tahan lama.',"+R.drawable.meja5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(12,2," +
                        "'VEBJÖRN',3799000," +
                        "'Meja, krem, 140x72 cm. Penyetop laci untuk menghindar laci ditarik terlalu jauh. Dapat diletakkan di tengah ruangan karena bagian belakang dilapisi. Tempat untuk label di setiap laci sehingga mudah untuk menyimpan barang-barang dengan teratur dan mudah untuk mencarinya.',"+R.drawable.meja6+")");

                //KURSI
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(13,3," +
                        "'LÅNGFJÄLL',3199000," +
                        "'Office chair with armrests, gunnared light brown-pink/white. The gently curved lines accentuated by sewn details are kind to your body and pleasant to look at. Also, there’s a tilt and height-adjusting mechanism that’s built to outlast years of ups and downs.',"+R.drawable.kursi1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(14,3," +
                        "'HATTEFJÄLL',3000000," +
                        "'Kursi kantor, gunnared abu-abu medium. Garis melengkung mulus yang ditonjolkan dengan detail jahit cocok untuk tubuh Anda dan indah dilihat. Juga, terdapat mekanisme pengatur kemiringan dan ketinggian yang tahan lama selama bertahun-tahun.',"+R.drawable.kursi2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(15,3," +
                        "'ALEFJÄLL',4999000," +
                        "'Kursi kantor, grann krem. Bahan kulit grain asli sangat memanjakan dalam hal kelembutan dan kenyamanan - tempat duduk dan sandaran punggungnya dapat disesuaikan kemiringan dan ketinggiannya sehingga memberi daya topang maksimal. Terjamin tahan lama hingga bertahun-tahun.',"+R.drawable.kursi3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(16,3," +
                        "'LOBERGET',498900," +
                        "'Kursi putar dengan alas , putih/krem. Anda dapat duduk dengan nyaman karena ketinggian kursi dapat disesuaikan.\n" +
                        "\n" +
                        "Roda pengaman memiliki mekanisme rem sensitif tekanan yang menjaga kursi tetap di tempat saat Anda berdiri dan bergerak lagi saat duduk.\n" +
                        "\n" +
                        "Mudah dibersihkan hanya dengan menggunakan lap basah.\n" +
                        "\n" +
                        "Untuk menyesuaikan ketinggian kursi, putar kursi ke atas atau ke bawah.',"+R.drawable.kursi4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(17,3," +
                        "'FJÄLLBERGET',2999000," +
                        "'Kursi rapat, veneer kayu oak diwarnai putih/gunnared krem. Anda dapat duduk dengan nyaman karena ketinggian kursi dapat disesuaikan.\n" +
                        "\n" +
                        "Garansi 10 tahun. Baca tentang istilah dalam brosur jaminan.\n" +
                        "\n" +
                        "Busa berketahanan tinggi yang dapat membentuk tubuh memberi kenyamanan selama bertahun-tahun.',"+R.drawable.kursi5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(18,3," +
                        "'SKRUVSTA',1799000," +
                        "'Kursi putar, vissle abu-abu. Roda pengaman memiliki mekanisme rem sensitif tekanan yang menjaga kursi tetap di tempat saat Anda berdiri dan bergerak lagi saat duduk.\n" +
                        "\n" +
                        "Roda dilapisi karet agar dapat berjalan dengan lancar di jenis lantai apa saja.\n" +
                        "\n" +
                        "Anda dapat duduk dengan nyaman karena ketinggian kursi dapat disesuaikan.',"+R.drawable.kursi6+")");

                //DEKORASI
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(19,4," +
                        "'FEJKA',49900," +
                        "'Tanaman tiruan dalam pot, dalam/luar ruang digantung/peperomia, 9 cm. FEJKA tanaman pot tiruan yang tidak membutuhkan keahlian berkebun. Sangat cocok bagi Anda tidak punya waktu untuk menyiram tanaman dan membersihkan daun-daun kering. Semua orang akan tertipu karena tanaman ini terlihat serupa.',"+R.drawable.dekorasi1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(20,4," +
                        "'VÄXBO',129000," +
                        "'Bingkai kolase untuk 8 gambar, putih, 13x18 cm. Bingkai kolase untuk 8 gambar, putih, 13x18 cm',"+R.drawable.dekorasi2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(21,4," +
                        "'SKÄRIG',199000," +
                        "'Jam dinding, biru, 26 cm. Jam tanpa suara. Mekanisme ini memiliki gerakan tanpa suara sehingga Anda dapat bersantai dan tidur tanpa terganggu oleh suara.',"+R.drawable.dekorasi3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(22,4," +
                        "'FEJRA',199000," +
                        "'Tanaman tiruan dalam pot, anggrek putih, 12 cm. FEJRA tanaman pot tiruan yang tidak membutuhkan keahlian berkebun. Sangat cocok bagi Anda tidak punya waktu untuk menyiram tanaman dan membersihkan daun-daun kering. Semua orang akan tertipu karena tanaman ini terlihat serupa.',"+R.drawable.dekorasi4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(23,4," +
                        "'GRADVIS',199000," +
                        "'Vas, merah muda, 21 cm. Gunakan vas dengan atau tanpa bunga, terlihat indah sebagai obyek tersendiri.',"+R.drawable.dekorasi5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(24,4," +
                        "'HOVSTA',3799000," +
                        "'Bingkai, kesan kayu birch, 23x23 cm. Hiasi dengan gambar yang Anda sukai. Bingkai dengan kedalaman dengan memasangnya dekat atau jauh, dan tersedia dalam berbagai ukuran. Pelindung bagian depan plastik aman digunakan - dan sesuai dengan motif.',"+R.drawable.dekorasi6+")");

                //PENYIMPANAN
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(25,5," +
                        "'BILLY',3299000," +
                        "'Bookcase with glass-doors, grey-turquoise/white stained oak veneer, 80x30x202 cm. Beloved classics are always in style. BILLY bookcase has been a favourite since the 1970s and now we’ve given it nice glass doors and new modern colours and materials – a real eye-catcher in the home.',"+R.drawable.penyimpanan1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(26,5," +
                        "'HEMNES',2999000," +
                        "'Bookcase, white stain/light brown, 90x198 cm. Sustainable beauty from sustainably-sourced solid pine, a natural and renewable material that gets more beautiful with each passing year. Like it? Combine with other products in the HEMNES series.',"+R.drawable.penyimpanan2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(27,5," +
                        "'LOMMARP',4999000," +
                        "'Cabinet, dark blue-green, 102x101 cm. This storage series is inspired by traditional carpentry, combining style and functions for today`s urban lifestyles. Use it wherever you need storage ― and mix with other furniture for a personal look.',"+R.drawable.penyimpanan3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(28,5," +
                        "'ERSNÄS',4999000," +
                        "'Sideboard, birch effect, 180x79 cm. Thin clean lines, a natural birch look and solid wood details. With ERSNÄS sideboard you get the best of modern Scandinavian design – simple elegance, timeless quality and well-thought-out functionality.',"+R.drawable.penyimpanan4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(29,5," +
                        "'HEMNESY',5999000," +
                        "'Glass-door cabinet with 3 drawers, white stain/light brown, 90x198 cm. Sustainable beauty from sustainably-sourced solid pine, a natural and renewable material that gets more beautiful with each passing year. Like it? Combine with other products in the HEMNES series.',"+R.drawable.penyimpanan5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(30,5," +
                        "'BOLLY',3198000," +
                        "'Bookcase, black-brown, 160x28x202 cm. It is estimated that every five seconds, one BILLY bookcase is sold somewhere in the world. Pretty impressive considering we launched BILLY in 1979. It’s the booklovers choice that never goes out of style.',"+R.drawable.penyimpanan6+")");


                //FURNITUR
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(31,6," +
                        "'VARDAGEN',499000," +
                        "'Panci dengan penutup, baja dienamel, 3 l. Anda dapat menggunakan panci ini dengan tutup di kompor dan oven, dan membawanya ke meja dan menyajikan makanan dari itu. Cara mudah untuk menyimpan sisa makanan Anda karena wadah makanan dapat disimpan di lemari es. Terbuat dari baja dilapis enamel, tahan lama, mudah dibersihkan dan menyebar panas dengan cepat dan rata, sehingga anda dapat mengatur suhu dengan mudah. Dengan penutup, masakan menjadi lebih cepat matang sehingga hemat waktu, energi dan pengeluaran serta mengurangi dampak terhadap lingkungan. Dapat digunakan untuk semua jenis kompor termasuk kompor induksi.',"+R.drawable.furnitur1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(32,6," +
                        "'FÖRSLAG',199000," +
                        "'3-piece knife set.',"+R.drawable.furnitur2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(33,6," +
                        "'FRUKTKAKA',149000," +
                        "'Wajan kadai, abu-abu, 24 cm. Panas paling tinggi di bagian bawah, dan sisi yang miring membuat makanan turun. Aduk terus hingga makanan renyah.\n" +
                        "\n" +
                        "Dua pegangan memudahkan untuk mengangkat panci.\n" +
                        "\n" +
                        "Dengan Teflon® Select, lapisan tahan lama anti lengket memungkinkan masak menggunakan sedikit lemak dan memudahkan pembersihan.\n" +
                        "\n" +
                        "Lapisan tidak melekat mengurangi resiko makanan hangus dan melekat.\n" +
                        "\n" +
                        "Terbuat dari aluminium, sehingga panas dapat disebarkan dengan rata dan hemat energi, memudahkan mengatur panas sehingga makanan tidak gosong dan melekat.',"+R.drawable.furnitur3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(34,6," +
                        "'HEMLAGAD',249000," +
                        "'Panci saus dengan penutup, hitam, 2 l. Panci memiliki dinding dan dasar yang ekstra tebal, dapat menyalurkan panas dengan rata dan memberi hasil masakan yang baik.\n" +
                        "\n" +
                        "Gagang panci membuatnya mudah diangkat.\n" +
                        "\n" +
                        "Terbuat dari aluminium, sehingga panas dapat disebarkan dengan rata dan hemat energi, memudahkan mengatur panas sehingga makanan tidak gosong dan melekat.\n" +
                        "\n" +
                        "Dengan penutup, masakan menjadi lebih cepat matang sehingga hemat waktu, energi dan pengeluaran serta mengurangi dampak terhadap lingkungan.\n" +
                        "\n" +
                        "Dengan Teflon® Select, lapisan tahan lama anti lengket memungkinkan masak menggunakan sedikit lemak dan memudahkan pembersihan.\n" +
                        "\n" +
                        "Wajan yang ringan mudah ditangani saat terisi penuh dengan makanan.\n" +
                        "\n" +
                        "Saluran uap mengurangi tekanan sehingga makanan tidak mudah meluap.',"+R.drawable.furnitur4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(35,6," +
                        "'ANNONS',99900," +
                        "'Panci dengan penutup, kaca/baja tahan karat, 2.8 l. Terbuat dari baja tahan karat, membuat panci tahan lama dan mudah dibersihkan.\n" +
                        "\n" +
                        "Penutup kaca memungkinkan anda melihat isi panci selama proses memasak.\n" +
                        "\n" +
                        "Dapat digunakan untuk semua jenis kompor termasuk kompor induksi.\n" +
                        "\n" +
                        "Bagian dasar memiliki satu lapisan aluminium diantara dua lapis baja tahan karat, sehingga panas merata dan mengurangi resiko makanan terbakar dan melekat.\n" +
                        "\n" +
                        "Dengan penutup, masakan menjadi lebih cepat matang sehingga hemat waktu, energi dan pengeluaran serta mengurangi dampak terhadap lingkungan.\n" +
                        "\n" +
                        "Lubang uap mengurangi tekanan sehingga makanan tidak mudah meluap.',"+R.drawable.furnitur5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(36,6," +
                        "'KAVALKAD',129000," +
                        "'Wajan penggorengan, set isi 2, hitam. Wajan yang ringan mudah ditangani saat terisi penuh dengan makanan.\n" +
                        "\n" +
                        "Gagang panci membuatnya mudah diangkat.\n" +
                        "\n" +
                        "Dengan Teflon® Classic lapisan non-stick yang memudahkan memasak dan pembersihan.\n" +
                        "\n" +
                        "Terbuat dari aluminium, sehingga panas dapat disebarkan dengan rata dan hemat energi, memudahkan mengatur panas sehingga makanan tidak gosong dan melekat.',"+R.drawable.furnitur6+")");


                //TEMPAT TIDUR
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(37,7," +
                        "'TUFJORD',9999000," +
                        "'Rangka tempat tidur berpelapis, djuparp hijau tua, 180x200 cm. Kepala ranjang melengkung sangat bagus untuk waktu tenang yang nyaman, membantu Anda bersantai dan rileks di tempat tidur.\n" +
                        "\n" +
                        "Anda dapat bersandar nyaman di kepala ranjang ini sambil minum kopi dan baca buku di pagi hari.\n" +
                        "\n" +
                        "Pelapisnya sangat pas di sekitar tempat tidur, membuatnya nyaman untuk dilihat dari segala sudut dan bagus untuk diletakkan di tengah ruangan.\n" +
                        "\n" +
                        "Pelapis beludru terasa lembut di kulit dan memiliki kilau yang indah.\n" +
                        "\n" +
                        "Velvet ditenun dari bahan rayon dan poliester sehingga membuatnya tahan lama.\n" +
                        "\n" +
                        "Kaki logam yang dibentuk sangat kuat dan stabil serta menambah karakter pada tempat tidur.',"+R.drawable.bed1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(38,7," +
                        "'SAGSTUA',4399000," +
                        "'Bed frame, white/luröy, 180x200 cm. A classic bed frame with a brass twist. The curved headboard and brass-coloured details soften the sturdy steel. Dressed with your favourite linens, it becomes a statement piece and your own personal haven.',"+R.drawable.bed2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(39,7," +
                        "'IDANÄS',10199000," +
                        "'Tempat tidur ottoman, gunnared pink pucat, 180x200 cm. Ruang penyimpanan di bawah tempat tidur menyimpan segalanya mulai dari quilt dan bantal hingga pakaian musiman. Atau kenapa tidak papan setrika?\n" +
                        "\n" +
                        "Detail tombol klasik membuat kepala tempat tidur menarik di kamar tidur mana pun.',"+R.drawable.bed3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(40,7," +
                        "'MALM',4899000," +
                        "'Rangka tempat tidur, tinggi, veneer kayu oak diwarnai putih/luröy, 180x200 cm. Desain rapi dengan kayu solid veneer. Letakkan tempat tidur berdiri sendiri atau dengan kepala tempat tidur merapat ke dinding. Jika memerlukan tempat untuk seprai tambahan, tambahkan kotak tempat penyimpanan tempat tidur beroda MALM.',"+R.drawable.bed4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(41,7," +
                        "'LEIRVIK',3999000," +
                        "'Rangka tempat tidur, putih/lönset, 180x200 cm. Jika Anda seorang yang romantis, Anda akan sulit menolak lekukan dekoratif dari baja putih ini. Pasangkan tempat tidur dengan tekstil dan bantal yang cantik serta nikmati mimpi Anda seperti akhir cerita dongeng.',"+R.drawable.bed5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(42,7," +
                        "'HAUGA',3499000," +
                        "'Rangka tempat tidur berpelapis, vissle abu-abu, 160x200 cm. Rangka tempat tidur berlapis ini menghadirkan kehangatan dan kelembutan ke kamar Anda. Kain dua warna tahan lama, sandaran kepala melengkung dengan lembut dan ujung pipanya menciptakan tampilan klasik yang akan Anda nikmati selama bertahun-tahun.',"+R.drawable.bed6+")");


                //ELEKTRONIK
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(43,8," +
                        "'Coffee Maker',1039200," +
                        "'Kels menghadirkan Coffee Maker Milano untuk Anda penikmat kopi. Alat ini memiliki fitur penyaring untuk mencegah bubuk kopi masuk ke dalam teko kaca, dengan katup anti tetes untuk mencegah tetesan kopi yang berlebih. Tangki untuk tempat air mudah dilepas agar lebih mudah dibersihkan. Tombol power ditempatkan di bagian yang mudah dijangkau dan dilengkapi lampu indikator. Coffee maker ini cocok untuk melengkapi dapur rumah atau pantry kantor.',"+R.drawable.elektronik1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(44,8," +
                        "'LG Lemari Es',18889000," +
                        "'Hadirkan lemari es LG di hunian ataupun usaha komersil Anda untuk membuat makanan dan minuman tetap terjaga kesegarannya. Lemari es 2 pintu side by side ini memilki kapasitas hingga 687 liter sehingga dapat memuat semua kebutuhan konsumsi Anda. Dilengkapi fitur Inverter Linear Compressor, lampu LED, wifi, serta tombol pada tampilan displaynya untuk menunjang kinerja lemari es agar semakin maksimal.',"+R.drawable.elektronik2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(45,8," +
                        "'Sahara Dispenser',2499000," +
                        "'Permudah akses mengambil air minum di rumah dengan menggunakan dispenser dari Kels. Dispenser ini memiliki 3 mode suhu air, yaitu dingin, nromal dan panass. Dengan kompartemen galon pada bagian bawah, dapat menghemat tenaga dan juga waktu saat mengganti galon. Dispenser ini hadir dengan nuansa warna rosegold yang elegan.',"+R.drawable.elektronik3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(46,8," +
                        "'LG AC',5019000," +
                        "'Hadirkan kesejukan dan kenyamanan di hunian Anda dengan pendingin udara dari LG. Memiliki fitur Dual Inverter, AC ini menghemat lebih banyak listrik dibanding kompresor biasa. Dilengkapi dengan teknologi Watt Control dan Low Noise, jadikan AC dari LG ini sebagai pilihan terbaik untuk kebutuhan Anda.',"+R.drawable.elektronik4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(47,8," +
                        "'Harbey Toaster',359000," +
                        "'Membuat roti panggang jadi lebih mudah dengan toaster dari Kels. Anda bisa mengatur tingkat kematangan sesuai selera dengan hanya menggunakan satu tombol. Selain untuk memanggang, alat ini bisa juga untuk menghangatkan roti. Di bagian bawah alat, terdapat nampan untuk menampung remahan roti agar meja tetap bersih.',"+R.drawable.elektronik5+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(48,8," +
                        "'Harbey Blender',579000," +
                        "'Blender dari Appetite akan membantu Anda menyiapkan berbagai racikan dengan tekstur yang sesuai kebutuhan. Terdapat 3 level kekuatan blender yang bisa disesuaikan dengan bahan makanan atau buah dan sayur yang sedang diolah. Tutup blender rapat sehingga mencegah cairan keluar dari jar. Blender didesain dengan dominasi warna hitam dan silver yang akan menambah kesan modern. Kegiatan memasak semakin mudah dan menyenangkan dengan kehadiran Harbey Blender dari Appetite Electrical.',"+R.drawable.elektronik6+")");

                //BEST SELLER
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(49,9," +
                        "'Ivaro',3350000," +
                        "'Bantal dudukan dan sandaran memberi penyangga yang nyaman untuk tubuh anda dan dengan mudah mendapatkan kembali bentuknya karena berisi busa berketahanan tinggi dan serat polyester.',"+R.drawable.sofa3+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(50,9," +
                        "'TUFJORD',9999000," +
                        "'Rangka tempat tidur berpelapis, djuparp hijau tua, 180x200 cm. Kepala ranjang melengkung sangat bagus untuk waktu tenang yang nyaman, membantu Anda bersantai dan rileks di tempat tidur.\n" +
                        "\n" +
                        "Anda dapat bersandar nyaman di kepala ranjang ini sambil minum kopi dan baca buku di pagi hari.\n" +
                        "\n" +
                        "Pelapisnya sangat pas di sekitar tempat tidur, membuatnya nyaman untuk dilihat dari segala sudut dan bagus untuk diletakkan di tengah ruangan.\n" +
                        "\n" +
                        "Pelapis beludru terasa lembut di kulit dan memiliki kilau yang indah.\n" +
                        "\n" +
                        "Velvet ditenun dari bahan rayon dan poliester sehingga membuatnya tahan lama.\n" +
                        "\n" +
                        "Kaki logam yang dibentuk sangat kuat dan stabil serta menambah karakter pada tempat tidur.',"+R.drawable.bed1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(51,9," +
                        "'FEJKA',49900," +
                        "'Tanaman tiruan dalam pot, dalam/luar ruang digantung/peperomia, 9 cm. FEJKA tanaman pot tiruan yang tidak membutuhkan keahlian berkebun. Sangat cocok bagi Anda tidak punya waktu untuk menyiram tanaman dan membersihkan daun-daun kering. Semua orang akan tertipu karena tanaman ini terlihat serupa.',"+R.drawable.dekorasi1+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(52,9," +
                        "'KAVALKAD',129000," +
                        "'Wajan penggorengan, set isi 2, hitam. Wajan yang ringan mudah ditangani saat terisi penuh dengan makanan.\n" +
                        "\n" +
                        "Gagang panci membuatnya mudah diangkat.\n" +
                        "\n" +
                        "Dengan Teflon® Classic lapisan non-stick yang memudahkan memasak dan pembersihan.\n" +
                        "\n" +
                        "Terbuat dari aluminium, sehingga panas dapat disebarkan dengan rata dan hemat energi, memudahkan mengatur panas sehingga makanan tidak gosong dan melekat.',"+R.drawable.furnitur6+")");

                dbWrite.execSQL("INSERT INTO tb_produk VALUES(53,9," +
                        "'LOBERGET',498900," +
                        "'Kursi putar dengan alas , putih/krem. Anda dapat duduk dengan nyaman karena ketinggian kursi dapat disesuaikan.\n" +
                        "\n" +
                        "Roda pengaman memiliki mekanisme rem sensitif tekanan yang menjaga kursi tetap di tempat saat Anda berdiri dan bergerak lagi saat duduk.\n" +
                        "\n" +
                        "Mudah dibersihkan hanya dengan menggunakan lap basah.\n" +
                        "\n" +
                        "Untuk menyesuaikan ketinggian kursi, putar kursi ke atas atau ke bawah.',"+R.drawable.kursi4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(54,9," +
                        "'HEMNES',2499000," +
                        "'Meja tamu, warna putih/cokelat muda, 90x90 cm. Keindahan alami kayu pinus solid. Bahan yang tahan lama dan terbarukan yang menjaga keaslian karakternya setiap tahunnya. Terlihat cocok dikombinasikan dengan perabotan lainnya dari seri HEMNES.',"+R.drawable.meja2+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(55,9," +
                        "'ERSNÄS',4999000," +
                        "'Sideboard, birch effect, 180x79 cm. Thin clean lines, a natural birch look and solid wood details. With ERSNÄS sideboard you get the best of modern Scandinavian design – simple elegance, timeless quality and well-thought-out functionality.',"+R.drawable.penyimpanan4+")");
                dbWrite.execSQL("INSERT INTO tb_produk VALUES(56,9," +
                        "'Coffee Maker',1039200," +
                        "'Kels menghadirkan Coffee Maker Milano untuk Anda penikmat kopi. Alat ini memiliki fitur penyaring untuk mencegah bubuk kopi masuk ke dalam teko kaca, dengan katup anti tetes untuk mencegah tetesan kopi yang berlebih. Tangki untuk tempat air mudah dilepas agar lebih mudah dibersihkan. Tombol power ditempatkan di bagian yang mudah dijangkau dan dilengkapi lampu indikator. Coffee maker ini cocok untuk melengkapi dapur rumah atau pantry kantor.',"+R.drawable.elektronik1+")");
            }
            bundleFragment = new Bundle();
            bundleFragment.putString("sendName", "name");
            bundleFragment.putString("sendEmail", "email");
            bundleFragment.putString("sendAddress", "address");
            bundleFragment.putString("sendPhone", "phone");

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(bundleFragment);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public String getNameFragment(){
        return name;
    }

    public String getMailFragment(){
        return email;
    }

    public String getAdressFragment(){
        return address;
    }

    public String getPhoneFragment(){
        return phone;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
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

}

