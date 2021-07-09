package noviaasriromadhoni.pmo.aplikasi_wisata.ui.user;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import noviaasriromadhoni.pmo.aplikasi_wisata.R;

public class BacaKonten extends AppCompatActivity {

    //Deklarasi Variable
    private TextView Judul, Artikel, Tanggal, Kategori, Bagikan;
    private ImageView Gambar;

    //Get
    private String getJudul;
    private String getArtikel;
    private String getTanggal;
    private String getGambar;

    //private String getFoto;
    private String getKategori;



    //tanggal
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikelmore);

        //TEXT Biasa
        Judul = findViewById(R.id.judul);
        Artikel =findViewById(R.id.artikel);
        Gambar = findViewById(R.id.imgView);

        //tgl lahir
        Tanggal = findViewById(R.id.tanggal);

        //Spiner
        Kategori = findViewById(R.id.kategori);


        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


        getData();

    }


    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData() {
        final String getJudul = getIntent().getExtras().getString("dataJudul");
        final String getArtikel = getIntent().getExtras().getString("dataArtikel");
        final String getKategori = getIntent().getExtras().getString("dataKategori");
        final String getTanggal = getIntent().getExtras().getString("dataTanggal");
        final String getGambar = getIntent().getExtras().getString("dataGambar");

        //FUNGSI REPLACE PADA URL GAMBAR
        String sumber = getGambar;
        String a1 = sumber.replace("o?name=", "o/");
        String a2 = a1.replace("&uploadType=resumable", "?alt=media&uploadType=resumable");
        String a3 = a2.replace("&upload_id", "&upload_ids");
        String a4 = a3.replace("&upload_protocol=resumable", "");
        String imgsumber = a4;

        //img
        Glide.with(this)
                .load(imgsumber)
                .placeholder(R.drawable.nothumb)
                .centerCrop()
                .into(Gambar);

        //SET TEXT
        Judul.setText(getJudul);
        Artikel.setText(getArtikel);
        Tanggal.setText(getTanggal);
        Kategori.setText(getKategori);

    }

}
