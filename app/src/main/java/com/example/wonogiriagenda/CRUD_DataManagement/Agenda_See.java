package com.example.wonogiriagenda.CRUD_DataManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataAgenda;
import com.google.android.material.textfield.TextInputEditText;

public class Agenda_See extends AppCompatActivity {
    // variable fields EditText dan Button
    private TextInputEditText etTanggal;
    private TextInputEditText etWaktuMulai;
    private TextInputEditText etWaktuBerakhir;
    private TextInputEditText etNamaPJ;
    private TextInputEditText etTempat;
    private TextInputEditText etNamaKegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_see);
        etNamaPJ = findViewById(R.id.et_nama_pj);
        etTempat = findViewById(R.id.et_tempat_acara);
        etNamaKegiatan = findViewById(R.id.et_nama_kegiatan);
        etTanggal = findViewById(R.id.et_tanggal);
        etWaktuMulai = findViewById(R.id.et_waktu_mulai);
        etWaktuBerakhir = findViewById(R.id.et_waktu_berakhir);

        etNamaPJ.setEnabled(false);
        etTempat.setEnabled(false);
        etNamaKegiatan.setEnabled(false);
        etTanggal.setEnabled(false);
        etWaktuMulai.setEnabled(false);
        etWaktuBerakhir.setEnabled(false);

        DataAgenda agenda = (DataAgenda) getIntent().getSerializableExtra("data");
        if(agenda!=null){
            etNamaPJ.setText(agenda.getPenanggungjawab());
            etTempat.setText(agenda.getTempat());
            etNamaKegiatan.setText(agenda.getAcara());
            etTanggal.setText(agenda.getTanggal());
            etWaktuMulai.setText(agenda.getWaktu_mulai());
            etWaktuBerakhir.setText(agenda.getWaktu_berakhir());
        }
    }//end of oncreate
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, Agenda_See.class);
    }
}//end of public class