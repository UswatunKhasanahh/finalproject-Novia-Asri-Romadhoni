package noviaasriromadhoni.pmo.aplikasi_wisata.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import noviaasriromadhoni.pmo.aplikasi_wisata.R;
import noviaasriromadhoni.pmo.aplikasi_wisata.adapter.Adapter;
import noviaasriromadhoni.pmo.aplikasi_wisata.model.DataKonten;

import static android.view.View.VISIBLE;

public class CariAdmin extends AppCompatActivity implements Adapter.dataListener {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button Booking, Tiket, Maps, Tambah;
    private DatabaseReference reference;
    private ArrayList<DataKonten> dataMahasiswa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikeldatalist);

        recyclerView = findViewById(R.id.datalist);
        MyRecyclerView();
        GetData();
        Booking = findViewById(R.id.booking);
        Tiket = findViewById(R.id.HargaTiket);
        Maps = findViewById(R.id.maps);
        Tambah = findViewById(R.id.tambah);

        Tambah.setVisibility(VISIBLE);
        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CariAdmin.this, TambahAdmin.class));
            }
        });

        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CariAdmin.this, CariAdmin.class));
            }
        });
        Tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CariAdmin.this, noviaasriromadhoni.pmo.aplikasi_wisata.Tiket.class));
            }
        });
       Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CariAdmin.this, noviaasriromadhoni.pmo.aplikasi_wisata.Maps.class));
            }
        });

    }

    private void GetData() {
        reference = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        String kategoriku = bundle.getString("kategoriku");
        reference.child("Konten").orderByChild("kategori").equalTo(kategoriku).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataMahasiswa = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataKonten mahasiswa = snapshot.getValue(DataKonten.class);
                    mahasiswa.setKey(snapshot.getKey());
                    dataMahasiswa.add(mahasiswa);
                }
                adapter = new Adapter(dataMahasiswa, CariAdmin.this);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
    }


    private void MyRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public void onDeleteData(DataKonten data, int position) {
        if (reference != null) {
            reference.child("Konten")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(CariAdmin.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_data_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
