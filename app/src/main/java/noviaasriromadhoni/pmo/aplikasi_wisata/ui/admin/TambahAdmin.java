package noviaasriromadhoni.pmo.aplikasi_wisata.ui.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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
import noviaasriromadhoni.pmo.aplikasi_wisata.ui.user.CariKonten;

public class TambahAdmin extends AppCompatActivity {
    //Deklarasi Variabel
    //private ProgressBar progressBar;

    private EditText Judul, Artikel;
    private TextView Tanggal;
    private Spinner Kategori;


    //Get
    private String getJudul;
    private String getArtikel;
    private String getTanggal;
    //private String getFoto;
    private String getKategori;


    //gambar
    private Button btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    //tanggal
    Calendar calendar;
    DatePickerDialog datePickerDialog;


    DatabaseReference getReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikelcreate);


        //TEXT Biasa
        Judul = findViewById(R.id.judul);
        Artikel = findViewById(R.id.artikel);

        //Date Picker
        Tanggal = findViewById(R.id.tanggal);

        //Spiner
        Kategori = findViewById(R.id.kategori);

        // Image
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.imgView);


        //mendapatkan Intstance dari database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getReference= database.getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Date Picker
        Tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hari = calendar.get(Calendar.DAY_OF_MONTH);
                int bulan = calendar.get(Calendar.MONTH);
                int tahun = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(TambahAdmin.this, new DatePickerDialog.OnDateSetListener() {
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
            //progressDialog.setTitle("Mengupload...");
            //progressDialog.show();
            StorageReference ref = storageReference.child("gambar/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressDialog.dismiss();
                            //Menyimpan Data yang diinputkan user kedala variabel
                            getJudul = Judul.getText().toString();
                            getArtikel = Artikel.getText().toString();


                            //Date Picker
                            getTanggal = Tanggal.getText().toString();

                            //Spinner
                            getKategori = Kategori.getSelectedItem().toString();

                            getReference.child("Konten").push().setValue(new DataKonten(getJudul, getArtikel, getTanggal, getKategori, taskSnapshot.getUploadSessionUri().toString()));
                            //Toast.makeText(CreateProfile.this, "Berhasil Upload dan Simpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahAdmin.this, CariKonten.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TambahAdmin.this, "Gagal " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //progressDialog.setMessage("Proses " + (int) progress + "%");
                        }
                    });
        }
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
