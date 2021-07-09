package noviaasriromadhoni.pmo.aplikasi_wisata.ui.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import noviaasriromadhoni.pmo.aplikasi_wisata.R;
import noviaasriromadhoni.pmo.aplikasi_wisata.model.DataKonten;

public class UpdateAdmin extends AppCompatActivity {

    //Deklarasi Variable
    private TextView Tanggal;
    private EditText Judul, Artikel;
    private EditText Gambar;
    private Spinner Kategori;

    //Get
    private String getJudul;
    private String getArtikel;
    private String getTanggal;
    private String getGambar;

    //private String getFoto;
    private String getKategori;
    private String[] ListKategori;

    //gambar
    private Button btnUpload, btnSave;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    //tanggal
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikelupdate);

        //TEXT Biasa
        Judul = findViewById(R.id.judul);
        Artikel =findViewById(R.id.artikel);
        Gambar = findViewById(R.id.imgView2);

        //tgl lahir
        Tanggal = findViewById(R.id.tanggal);

        //Spiner
        Kategori = findViewById(R.id.kategori);

        // Image
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnSave = (Button) findViewById(R.id.btnSave);
        imageView = (ImageView) findViewById(R.id.imgView);

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        getData();

        //Date Picker
        Tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hari = calendar.get(Calendar.DAY_OF_MONTH);
                int bulan = calendar.get(Calendar.MONTH);
                int tahun = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(UpdateAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Tanggal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, hari,bulan,tahun);
                //datePickerDialog.getDatePicker().setMinDate();
                datePickerDialog.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }


    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        final String getJudul = getIntent().getExtras().getString("dataJudul");
        final String getArtikel = getIntent().getExtras().getString("dataArtikel");
        final String getKategori = getIntent().getExtras().getString("dataKategori");
        final String getTanggal = getIntent().getExtras().getString("dataTanggal");

        final String getGambar = getIntent().getExtras().getString("dataGambar");


        //spinner
        ListKategori = new String[]{"Jajanan","Makanan","Minuman"};
        ArrayAdapter<String> kategoriadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,ListKategori);
        Kategori.setAdapter(kategoriadapter);
        Kategori.setSelection(kategoriadapter.getPosition(getKategori.trim()));

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
                .into(imageView);

        //SET TEXT
        Judul.setText(getJudul);
        Artikel.setText(getArtikel);
        Tanggal.setText(getTanggal);
        Gambar.setText(getGambar);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Mengupload...");
            progressDialog.show();
            StorageReference ref = storageReference.child("gambar/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            getJudul = Judul.getText().toString();
                            getArtikel = Artikel.getText().toString();
                            getTanggal = Tanggal.getText().toString();

                            //Spinner
                            getKategori = Kategori.getSelectedItem().toString();

                            String getKey = getIntent().getExtras().getString("getPrimaryKey");
                            database.child("Konten")
                                    .child(getKey)
                                    .setValue(new DataKonten(getJudul, getArtikel, getTanggal, getKategori, taskSnapshot.getUploadSessionUri().toString()));
                            //Toast.makeText(UpdateProfile.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateAdmin.this, CariAdmin.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateAdmin.this, "Gagal " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Proses " + (int) progress + "%");
                        }
                    });
        }
    }

    private void simpan() {
        getJudul = Judul.getText().toString();
        getArtikel = Artikel.getText().toString();
        getTanggal = Tanggal.getText().toString();
        getGambar=Gambar.getText().toString();

        //Spinner
        getKategori = Kategori.getSelectedItem().toString();

        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Konten")
                .child(getKey)
                .setValue(new DataKonten(getJudul, getArtikel, getTanggal, getKategori, getGambar))
                //Toast.makeText(UpdateProfile.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(UpdateAdmin.this, CariAdmin.class));
                    };
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}

