package com.example.wonogiriagenda.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wonogiriagenda.CRUD_DataManagement.Agenda_Create;
import com.example.wonogiriagenda.CRUD_DataManagement.Agenda_Read;
import com.example.wonogiriagenda.R;

public class Admin_Agenda extends AppCompatActivity {
    private Button btTambahData;
    private Button btLihatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agenda);


        btTambahData = findViewById(R.id.bt_tambahdata);
        btLihatData = findViewById(R.id.bt_lihatdata);

        btTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kelas yang akan dijalankan ketika tombol Create/Insert Data diklik
                startActivity(Agenda_Create.getActIntent(Admin_Agenda.this));
            }
        });

        btLihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Agenda_Read.getActIntent(Admin_Agenda.this));
            }
        });

    }
}