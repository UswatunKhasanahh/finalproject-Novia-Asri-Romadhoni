package noviaasriromadhoni.pmo.aplikasi_wisata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Pesan (View view){
        Toast.makeText(this, "Ini adalah Tombol Pesan", Toast.LENGTH_SHORT).show();
    }
}