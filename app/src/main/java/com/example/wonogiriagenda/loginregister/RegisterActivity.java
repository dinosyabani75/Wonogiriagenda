package com.example.wonogiriagenda.loginregister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataRegister;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Menginisialisasi FirebaseDatabase, References (tabel database), dan authentication
        //Agar dapat digunakan di activity berikut
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("register");

        TextView login_act = findViewById(R.id.masuk_akun);
        TextInputEditText nama = findViewById(R.id.nama_register);
        TextInputEditText username = findViewById(R.id.username_register);
        TextInputEditText email = findViewById(R.id.email_register);
        TextInputEditText password = findViewById(R.id.password_register);
        TextInputEditText nip = findViewById(R.id.nip_register);
        Button button_registrasi = findViewById(R.id.button_register);

        login_act.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent1);
        });

        button_registrasi.setOnClickListener(v -> {
            String getnama = Objects.requireNonNull(nama.getText()).toString().trim();
            String getusername = Objects.requireNonNull(username.getText()).toString().trim();
            String getemail = Objects.requireNonNull(email.getText()).toString().trim();
            String getpassword = Objects.requireNonNull(password.getText()).toString().trim();
            String getnip = Objects.requireNonNull(nip.getText()).toString().trim();
            String getas = "admin";
            //String getas = "superadmin";
            //Validasi inputan
            if (getnama.isEmpty()){
                nama.setError("Nama harap diisi!");
            } else if (getusername.isEmpty()){
                username.setError("Username harap diisi!");
            } else if (getusername.length()<5){
                username.setError("Username terlalu singkat, minimal 5 karakter!");
            } else if (getemail.isEmpty()){
                email.setError("Email harap diisi!");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(getemail).matches()){
                email.setError("Email tidak valid");
            } else if (getpassword.isEmpty()){
                password.setError("Password harap diisi!");
            } else if (getpassword.length()<6){
                password.setError("Password terlalu singkat minimal 6 karakter!");
            } else if (getnip.isEmpty()){
                nip.setError("NIP harap diisi!");
            }
            //Menyiapkan wadah untuk menyimpan data yang telah diinput, ke dataregister.java, jumlah data harus sesuai.
            DataRegister dataRegister = new DataRegister(getnama,getusername,getemail,getpassword,getnip,getas);

            /*
            Jika username or nip tidak sama dengan nilai yang ada
            di database, maka lanjutkan perintah buat akun
            */
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                            //1 email untuk 1x registrasi, jika email sama maka tidak dapat mendaftar
                            mAuth.createUserWithEmailAndPassword(getemail, getpassword).addOnCompleteListener(RegisterActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    mDatabase.child(getusername).setValue(dataRegister).addOnSuccessListener(aVoid ->
                                            Toast.makeText(RegisterActivity.this, "Daftar Berhasil.",Toast.LENGTH_SHORT).show());
                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent1);
                                } else {
                                    email.setError("Email yang sama telah terdaftar");
                                }
                            });

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RegisterActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }//end of oncreate
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, RegisterActivity.class);
    }
}//end of publiclass