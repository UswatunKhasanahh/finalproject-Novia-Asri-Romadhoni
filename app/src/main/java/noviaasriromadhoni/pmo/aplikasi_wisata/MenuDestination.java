package noviaasriromadhoni.pmo.aplikasi_wisata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import noviaasriromadhoni.pmo.aplikasi_wisata.ui.user.CariKonten;

public class MenuDestination extends AppCompatActivity {
    private ImageView keluarga, sejarah, edukasi, religi, alam, kuliner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_destination);

        keluarga = findViewById(R.id.keluarga);
        sejarah = findViewById(R.id.sejarah);
        edukasi = findViewById(R.id.edukasi);
        religi = findViewById(R.id.religi);
        alam = findViewById(R.id.alam);
        kuliner = findViewById(R.id.kuliner);

        keluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Keluarga";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
        sejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Sejarah";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
        edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Edukasi";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
        religi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Religi";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
        alam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Alam";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
       kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = "Wisata Kuliner";
                Intent i = new Intent(MenuDestination.this, CariKonten.class);
                i.putExtra("kategoriku", kategori);
                startActivity(i);
            }
        });
    }
}