package noviaasriromadhoni.pmo.aplikasi_wisata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText email, fullname, password, telepon;
    Button btnRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView tv_to_login;
    Switch sw_accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullname = findViewById(R.id.et_rname);
        email = findViewById(R.id.et_remail);
        password = findViewById(R.id.et_rpassword);
        telepon = findViewById(R.id.et_rtelepon);
        btnRegister = findViewById(R.id.btn_register);
        tv_to_login = findViewById(R.id.tv_to_login);
        sw_accept = findViewById(R.id.sw_accept);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullname);
                checkField(email);
                checkField(password);
                checkField(telepon);


                if (valid) {
                    //start the user registration proses by user
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", fullname.getText().toString());
                            userInfo.put("UserEmail", email.getText().toString());
                            userInfo.put("PhoneNumber", telepon.getText().toString());
                            userInfo.put("Password", password.getText().toString());


                            //specify if the user is admin

                            userInfo.put("isUser", "4");

                            df.set(userInfo);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                    tv_to_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Register.this, Login.class));
                            Register.this.finish();
                        }
                    });
                }
            }


    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }
});
    }
}