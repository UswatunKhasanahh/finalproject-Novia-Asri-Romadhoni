package noviaasriromadhoni.pmo.aplikasi_wisata;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginBtn, gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView tv_to_forgetpassword,tv_to_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.et_lemail);
        password = findViewById(R.id.et_lpassword);
        loginBtn = findViewById(R.id.btn_login);
        tv_to_register = findViewById(R.id.tv_to_register);
        tv_to_forgetpassword = findViewById(R.id.tv_to_forgotpassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(email);
                checkField(password);
                Log.d("TAG", "onClick: "+ email.getText().toString());

                if (valid) {
                    fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Leggedin Succesfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });



        tv_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                Login.this.finish();
            }
        });

        tv_to_forgetpassword.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to received Email reset link!");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract the email and send reset link

                        String email = resetMail.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog!

                    }
                });

                passwordResetDialog.create().show();
            }
        }));

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " +documentSnapshot.getData());
                //identify the user access level

                if(documentSnapshot.getString("isAdmin") !=null) {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    finish();

                }

                if (documentSnapshot.getString("isUser") !=null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                if (documentSnapshot.getString("isOwner") !=null) {
                    startActivity(new Intent(getApplicationContext(), Owner.class));
                    finish();
                }



            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }
}