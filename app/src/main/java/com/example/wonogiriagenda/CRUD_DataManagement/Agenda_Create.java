package com.example.wonogiriagenda.CRUD_DataManagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataAgenda;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Agenda_Create extends AppCompatActivity {
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog timePickerDialog;

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    //Variabel Global EditText
    TextInputEditText etNamaPJ;
    TextInputEditText etTempat;
    TextInputEditText etNamaKegiatan;
    EditText etTanggal;
    EditText etWaktuMulai;
    EditText etWaktuBerakhir;
    EditText etKey;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_create);

        /*
         * Kita menggunakan format tanggal dd-MM-yyyy
         * jadi nanti tanggal nya akan diformat menjadi
         * misalnya 01-12-2017
         */
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        final DataAgenda agenda = (DataAgenda) getIntent().getSerializableExtra("data");

        // inisialisasi fields EditText dan Button
        etNamaPJ = findViewById(R.id.et_nama_pj);
        etTempat = findViewById(R.id.et_tempat_acara);
        etNamaKegiatan = findViewById(R.id.et_nama_kegiatan);
        etTanggal = findViewById(R.id.et_tanggal);
        etWaktuMulai = findViewById(R.id.et_waktu_mulai);
        etWaktuBerakhir = findViewById(R.id.et_waktu_berakhir);
        etKey = findViewById(R.id.et_key);
        btSubmit = findViewById(R.id.bt_submit);

        etTanggal.setEnabled(true);
        etTanggal.setClickable(true);
        etTanggal.setFocusable(false);

        etWaktuMulai.setEnabled(true);
        etWaktuMulai.setClickable(true);
        etWaktuMulai.setFocusable(false);

        etWaktuBerakhir.setEnabled(true);
        etWaktuBerakhir.setClickable(true);
        etWaktuBerakhir.setFocusable(false);

        etKey.setEnabled(true);
        etKey.setClickable(true);
        etKey.setFocusable(false);

        //Fungsi saat edittext di klik
        etWaktuMulai.setOnClickListener(v -> showTimeDialog());
        etWaktuBerakhir.setOnClickListener(v -> showTimeDialog2());
        etTanggal.setOnClickListener(v -> showDateDialog());
        etKey.setOnClickListener(v -> {
            long mDateTime = 9999999999999L - System.currentTimeMillis();
            String mKeyTime = String.valueOf(mDateTime);
            etKey.setText(mKeyTime);
        }
        );

        if (agenda != null){
            etNamaPJ.setText(agenda.getPenanggungjawab());
            etTempat.setText(agenda.getTempat());
            etNamaKegiatan.setText(agenda.getAcara());
            etTanggal.setText(agenda.getTanggal());
            etWaktuMulai.setText(agenda.getWaktu_mulai());
            etWaktuBerakhir.setText(agenda.getWaktu_berakhir());
            etKey.setText(agenda.getKey());

            btSubmit.setOnClickListener(v -> {
                agenda.setPenanggungjawab(Objects.requireNonNull(etNamaPJ.getText()).toString());
                agenda.setTempat(Objects.requireNonNull(etTempat.getText()).toString());
                agenda.setAcara(Objects.requireNonNull(etNamaKegiatan.getText()).toString());
                agenda.setTanggal(etTanggal.getText().toString());
                agenda.setWaktu_mulai(etWaktuMulai.getText().toString());
                agenda.setWaktu_berakhir(etWaktuBerakhir.getText().toString());
                agenda.setKey(etKey.getText().toString());
                updateAgenda(agenda);
            });
        } else {
            // kode yang dipanggil ketika tombol Submit diklik
            btSubmit.setOnClickListener(view -> {
                if(!isEmpty(Objects.requireNonNull(etNamaPJ.getText()).toString()) &&
                        !isEmpty(Objects.requireNonNull(etTempat.getText()).toString()) &&
                        !isEmpty(Objects.requireNonNull(etNamaKegiatan.getText()).toString()) &&
                        !isEmpty(etTanggal.getText().toString()) &&
                        !isEmpty(etWaktuMulai.getText().toString()) &&
                        !isEmpty(etKey.getText().toString()) &&
                        !isEmpty(etWaktuBerakhir.getText().toString()))
                    submitAgenda(new DataAgenda(etNamaPJ.getText().toString(),
                            etTempat.getText().toString(),
                            etNamaKegiatan.getText().toString(),
                            etTanggal.getText().toString(),
                            etWaktuMulai.getText().toString(),
                            etWaktuBerakhir.getText().toString(),
                            etKey.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.bt_submit), "Data agenda tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etNamaPJ.getWindowToken(), 0);
            });
        }

    }

    //Mengatur waktu mulai
    @SuppressLint("SetTextI18n")
    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> etWaktuMulai.setText(+hourOfDay+":"+minute  + " WIB"),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    //Mengatur waktu berakhir
    @SuppressLint("SetTextI18n")
    private void showTimeDialog2() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> etWaktuBerakhir.setText(+hourOfDay+":"+minute  + " WIB"),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    //Mengatur tanggal acara
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            etTanggal.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateAgenda(DataAgenda agenda) {
        /*
          Baris kode yang digunakan untuk mengupdate data barang
          yang sudah dimasukkan di Firebase Realtime Database
         */
        database.child("agenda") //akses parent index, ibaratnya seperti nama tabel
                .child(agenda.getKey()) //select barang berdasarkan key
                .setValue(agenda) //set value barang yang baru
                .addOnSuccessListener(this, aVoid -> {

                    /**
                     * Baris kode yang akan dipanggil apabila proses update barang sukses
                     */
                    Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", view -> finish()).show();
                });
    }

    private void submitAgenda(DataAgenda agenda) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        long mDateTime = 9999999999999L - System.currentTimeMillis();
        String mOrderTime = String.valueOf(mDateTime);
        database.child("agenda")
                .child(agenda.getKey())
                .setValue(agenda).addOnSuccessListener(this, aVoid -> {
                    etNamaPJ.setText("");
                    etTempat.setText("");
                    etNamaKegiatan.setText("");
                    etTanggal.setText("");
                    etWaktuMulai.setText("");
                    etWaktuBerakhir.setText("");
                    etKey.setText("");
                    Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambah", Snackbar.LENGTH_LONG).show();
                });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, Agenda_Create.class);
    }
}