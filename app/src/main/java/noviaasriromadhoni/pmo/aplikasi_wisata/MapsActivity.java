package noviaasriromadhoni.pmo.aplikasi_wisata;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import noviaasriromadhoni.pmo.aplikasi_wisata.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng LokawisataBaturraden = new LatLng(-7.3131652, 109.2279242);
        mMap.addMarker(new MarkerOptions().position(LokawisataBaturraden ).title("Marker in Lokawisata Baturraden "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LokawisataBaturraden ));

        LatLng LimpaKuwus = new LatLng(-7.3037855, 109.2172432);
        mMap.addMarker(new MarkerOptions().position(LimpaKuwus).title("Marker in Limpa Kuwus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LimpaKuwus));

        LatLng BaturradenAdventureForest = new LatLng(-7.3069134, 109.2407516);
        mMap.addMarker(new MarkerOptions().position(BaturradenAdventureForest).title("Marker in Baturraden Adventure Forest"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(BaturradenAdventureForest));

        LatLng CurugTelu = new LatLng(-7.3197898, 109.2408421);
        mMap.addMarker(new MarkerOptions().position(CurugTelu).title("Marker in Curug Telu"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurugTelu));

        LatLng TelagaSunyi = new LatLng(-7.3060009, 109.2422322);
        mMap.addMarker(new MarkerOptions().position(TelagaSunyi).title("Marker in Telaga Sunyi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TelagaSunyi));

        LatLng CurugGomblang = new LatLng(-7.3237089, 109.1791999);
        mMap.addMarker(new MarkerOptions().position(CurugGomblang).title("Marker in Curug Gomblang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurugGomblang));
    }
}