package com.example.wonogiriagenda.superadmin;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.CRUD_DataManagement.Agenda_Create;
import com.example.wonogiriagenda.CRUD_DataManagement.Agenda_Read;
import com.example.wonogiriagenda.R;

public class SuperAdmin_Agenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_agenda);
        Button btTambahData = findViewById(R.id.bt_tambahdata);
        Button btLihatData = findViewById(R.id.bt_lihatdata);

        btTambahData.setOnClickListener(view -> {
            // kelas yang akan dijalankan ketika tombol Create/Insert Data diklik
            startActivity(Agenda_Create.getActIntent(SuperAdmin_Agenda.this));
        });

        btLihatData.setOnClickListener(view ->
                startActivity(Agenda_Read.getActIntent(SuperAdmin_Agenda.this)));
    }//end of oncreate
}//end of public class