package com.example.wonogiriagenda.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.loginregister.MainActivity;
import com.example.wonogiriagenda.loginregister.preferences;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private long exitTime = 0;
    private final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle exstras = getIntent().getExtras();
        String username = exstras.getString(KEY_USERNAME);

        Button bt_agenda = findViewById(R.id.bt_agenda);
        Button bt_profile = findViewById(R.id.bt_profile);
        Button bt_logout = findViewById(R.id.bt_logout);

        bt_agenda.setOnClickListener(v -> {
            Intent iPindah = new Intent(getApplicationContext(), Admin_Agenda.class);
            startActivity(iPindah);
        });

        bt_profile.setOnClickListener(v ->{
            Intent iPindah = new Intent(getApplicationContext(), Admin_Profile.class);
            iPindah.putExtra(KEY_USERNAME, username);
            startActivity(iPindah);
        });

        bt_logout.setOnClickListener(v -> {
            //Menampilkan alert dialog logout
            showLogoutDialog();
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title dialog
        alertDialogBuilder.setTitle("LOGOUT");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin untuk logout akun?")
                .setIcon(R.mipmap.logo_wonnda_foreground)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    preferences.clearData(AdminActivity.this);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminActivity.this,MainActivity.class));
                    finish();
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
    }

    //Saat menekan tombol kembali, akan dikonfirmasi lagi apakah akan keluar aplikasi.
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 3000) {
            Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }

    }
}