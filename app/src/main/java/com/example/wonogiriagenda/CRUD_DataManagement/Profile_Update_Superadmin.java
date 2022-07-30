package com.example.wonogiriagenda.CRUD_DataManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataRegister;
import com.example.wonogiriagenda.superadmin.SuperAdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile_Update_Superadmin extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update_superadmin);

        //Menginisialisasi FirebaseDatabase, References (tabel database), dan authentication
        //Agar dapat digunakan di activity berikut
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("register");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Bundle exstras = getIntent().getExtras();
        String KEY_USERNAME = "username";
        username = exstras.getString(KEY_USERNAME);

        TextView getusername = findViewById(R.id.getusername);
        TextView getemail = findViewById(R.id.getemail);
        TextView getrole = findViewById(R.id.getrole);
        TextInputEditText getnama = findViewById(R.id.nama_update);
        TextInputEditText getpassword = findViewById(R.id.password_update);
        TextInputEditText getnip = findViewById(R.id.nip_update);
        Button submit = findViewById(R.id.button_submit_update);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (Objects.equals(ds.child("username").getValue(), username)){
                        getemail.setText(Objects.requireNonNull(ds.child("email").getValue(String.class)).trim());
                        getnama.setText(Objects.requireNonNull(ds.child("nama").getValue(String.class)).trim());
                        getusername.setText(Objects.requireNonNull(ds.child("username").getValue(String.class)).trim());
                        getpassword.setText(Objects.requireNonNull(ds.child("password").getValue(String.class)).trim());
                        getnip.setText(Objects.requireNonNull(ds.child("nip").getValue(String.class)).trim());
                        getrole.setText(Objects.requireNonNull(ds.child("as").getValue(String.class)).trim());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile_Update_Superadmin.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile_Update_Superadmin.this);
                // set title dialog
                alertDialogBuilder.setTitle("UPDATE DATA");
                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Anda yakin untuk update data?")
                        .setIcon(R.mipmap.logo_wonnda_foreground)
                        .setCancelable(false)
                        .setPositiveButton("Ya", (dialog, id) -> {
                            String cekNama = getnama.getText().toString().trim();
                            String cekUsername = getusername.getText().toString().trim();
                            String cekEmail = getemail.getText().toString().trim();
                            String cekPassword = getpassword.getText().toString().trim();
                            String cekNip = getnip.getText().toString().trim();
                            String cekRole = getrole.getText().toString().trim();
                            DataRegister dataRegister = new DataRegister(cekNama,cekUsername,cekEmail,cekPassword,cekNip,cekRole);
                            mDatabase.child(cekUsername).setValue(dataRegister).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Profile_Update_Superadmin.this, "Update Berhasil.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                            String send_username = username;
                            Intent menu = new Intent(Profile_Update_Superadmin.this, SuperAdminActivity.class);
                            menu.putExtra(KEY_USERNAME, send_username);
                            startActivity(menu);
                        })
                        .setNegativeButton("Tidak", (dialog, id) -> {
                            // jika tombol ini diklik, akan menutup dialog
                            // dan tidak terjadi apa2
                            dialog.cancel();
                        });
                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();
                // menampilkan alert dialog
                alertDialog.show();
            }//end of onclick
        });//end of setOnClick
    }
}