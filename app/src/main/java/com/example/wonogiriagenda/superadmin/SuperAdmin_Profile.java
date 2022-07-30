package com.example.wonogiriagenda.superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.CRUD_DataManagement.Profile_Update_Superadmin;
import com.example.wonogiriagenda.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SuperAdmin_Profile extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String username;
    private TextInputEditText tie_nama, tie_username, tie_email, tie_password, tie_nip, tie_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_profile);

        Bundle exstras = getIntent().getExtras();
        String KEY_USERNAME = "username";
        username = exstras.getString(KEY_USERNAME);

        // inisialisasi fields EditText dan Button
        tie_email = findViewById(R.id.email_profil);
        tie_nama = findViewById(R.id.nama_profil);
        tie_username = findViewById(R.id.username_profil);
        tie_password = findViewById(R.id.password_profil);
        tie_nip = findViewById(R.id.nip_profil);
        tie_role = findViewById(R.id.role_profil);
        Button bt_update = findViewById(R.id.button_update);

        tie_email.setEnabled(false);
        tie_email.setKeyListener(null);
        tie_email.setFocusable(false);

        tie_nama.setEnabled(false);
        tie_nama.setKeyListener(null);
        tie_nama.setFocusable(false);

        tie_username.setEnabled(false);
        tie_username.setKeyListener(null);
        tie_username.setFocusable(false);

        tie_password.setEnabled(false);
        tie_password.setKeyListener(null);
        tie_password.setFocusable(false);

        tie_nip.setEnabled(false);
        tie_nip.setKeyListener(null);
        tie_nip.setFocusable(false);

        tie_role.setEnabled(false);
        tie_role.setKeyListener(null);
        tie_role.setFocusable(false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("register");
        getdata();

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile_Update_Superadmin.class);
                intent.putExtra(KEY_USERNAME, username);
                startActivity(intent);
            }
        });//end of bt_update
    }//end of oncreate

    private void getdata() {
        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (Objects.equals(ds.child("username").getValue(), username)){
                        tie_email.setText(Objects.requireNonNull(ds.child("email").getValue(String.class)).trim());
                        tie_nama.setText(Objects.requireNonNull(ds.child("nama").getValue(String.class)).trim());
                        tie_username.setText(Objects.requireNonNull(ds.child("username").getValue(String.class)).trim());
                        tie_password.setText(Objects.requireNonNull(ds.child("password").getValue(String.class)).trim());
                        tie_nip.setText(Objects.requireNonNull(ds.child("nip").getValue(String.class)).trim());
                        tie_role.setText(Objects.requireNonNull(ds.child("as").getValue(String.class)).trim());
                    }
                }
            }//end of onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SuperAdmin_Profile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}