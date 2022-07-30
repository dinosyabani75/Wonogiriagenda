package com.example.wonogiriagenda.loginregister;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.admin.AdminActivity;
import com.example.wonogiriagenda.superadmin.SuperAdminActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String input1;
    String input2;
    private final String KEY_USERNAME = "username";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch active;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView daftar = findViewById(R.id.daftar_akun);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        active = findViewById(R.id.active);
        mAuth = FirebaseAuth.getInstance();

        daftar.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent1);
        });

        login.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("register").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    input1 = username.getText().toString();
                    input2 = password.getText().toString();

                    if (dataSnapshot.child(input1).exists()) {
                        if (dataSnapshot.child(input1).child("username").getValue(String.class).equals(input1) && dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                            if (active.isChecked()) {
                                if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("superadmin")) {
                                    mAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    preferences.setDataLogin(MainActivity.this, true);
                                    preferences.setDataAs(MainActivity.this, "superadmin");
                                    preferences.setDataUsername(MainActivity.this, input1);
                                    Intent menu = new Intent(MainActivity.this, SuperAdminActivity.class);
                                    menu.putExtra(KEY_USERNAME, input1);
                                    startActivity(menu);
                                } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")){
                                    mAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    preferences.setDataLogin(MainActivity.this, true);
                                    preferences.setDataAs(MainActivity.this, "admin");
                                    preferences.setDataUsername(MainActivity.this, input1);
                                    Intent menu = new Intent(MainActivity.this, AdminActivity.class);
                                    menu.putExtra(KEY_USERNAME, input1);
                                    startActivity(menu);
                                }
                            } else {
                                if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("superadmin")) {
                                    preferences.setDataLogin(MainActivity.this, false);
                                    preferences.setDataUsername(MainActivity.this, input1);
                                    Intent menu = new Intent(MainActivity.this, SuperAdminActivity.class);
                                    menu.putExtra(KEY_USERNAME, input1);
                                    startActivity(menu);

                                } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")){
                                    preferences.setDataLogin(MainActivity.this, false);
                                    preferences.setDataUsername(MainActivity.this, input1);
                                    Intent menu = new Intent(MainActivity.this, AdminActivity.class);
                                    menu.putExtra(KEY_USERNAME, input1);
                                    startActivity(menu);
                                }
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Kata sandi salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Data belum terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("superadmin")) {
                Intent menu = new Intent(MainActivity.this, SuperAdminActivity.class);
                menu.putExtra(KEY_USERNAME, preferences.getDataUsername(this));
                startActivity(menu);
                finish();
            } else if (preferences.getDataAs(this).equals("admin")){
                Intent menu = new Intent(MainActivity.this, AdminActivity.class);
                menu.putExtra(KEY_USERNAME, preferences.getDataUsername(this));
                startActivity(menu);
                finish();
            }
        }
    }
}