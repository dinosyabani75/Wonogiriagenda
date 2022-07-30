package com.example.wonogiriagenda.CRUD_DataManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataAgenda;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Agenda_Read extends AppCompatActivity implements Adapter_RV_Agenda.FirebaseDataListener {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataAgenda> daftarAgenda;
    private ImageButton mSearchBtn,mSortPJBtn;
    private Button mSortAcara, mSortTanggal, mSortTempat, mSortWaktuMulai,mSortWaktuBerakhir;
    private EditText mSearchfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_read);

/**
 * Inisialisasi RecyclerView & komponennya
 */
        rvView = findViewById(R.id.rv_agenda);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        mSortAcara = findViewById(R.id.bt_acara);
        mSortTanggal = findViewById(R.id.bt_tanggal);
        mSortTempat = findViewById(R.id.bt_tempat);
        mSortWaktuMulai = findViewById(R.id.bt_waktu_mulai);
        mSortWaktuBerakhir = findViewById(R.id.bt_waktu_berakhir);
        mSearchBtn = findViewById(R.id.ImageButton);
        mSearchfield = findViewById(R.id.search);
        mSortPJBtn  = findViewById(R.id.ImageButton1);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data agenda dari Firebase Realtime DB
         */
        database.child("agenda").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarAgenda = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                    agenda.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarAgenda.add(agenda);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

        /**
         * Mengambil kata atau kalimat yang akan di cari
         */
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mSearchfield.getText().toString();
                FirebaseSearch(searchText);
            }
        });

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet Acara
         */
        mSortPJBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda").orderByChild("penanggungjawab").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet Acara
         */
        mSortAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda").orderByChild("acara").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet Penanggungjawab
         */

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet TANGGAL
         */
        mSortTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda")
                        .orderByChild("tanggal")
                        .limitToLast(20)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet TEMPAT
         */
        mSortTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda").orderByChild("tempat")
                        .limitToLast(15)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet WAKTU MULAI
         */
        mSortWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda").orderByChild("waktu_mulai")
                        .limitToLast(15)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

        /**
         * Melalukan Sorting pada Database berdasarkan alphabet WAKTU BERAKHIR
         */
        mSortWaktuBerakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("agenda").orderByChild("waktu_berakhir")
                        .limitToLast(15)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daftarAgenda = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                            /**
                             * Mapping data pada DataSnapshot ke dalam object Barang
                             * Dan juga menyimpan primary key pada object Barang
                             * untuk keperluan Edit dan Delete data
                             */
                            DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                            agenda.setKey(noteDataSnapshot.getKey());

                            /**
                             * Menambahkan object Barang yang sudah dimapping
                             * ke dalam ArrayList
                             */
                            daftarAgenda.add(agenda);
                        }
                        adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getDetails()+" "+error.getMessage());
                    }
                });
            }
        });//end of sort function

    }//end of oncreate

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, Agenda_Read.class);
    }

    @Override
    public void onDeleteData(DataAgenda agenda, final int position) {
        /**
         * Kode ini akan dipanggil ketika method onDeleteData
         * dipanggil dari adapter lewat interface.
         * Yang kemudian akan mendelete data di Firebase Realtime DB
         * berdasarkan key barang.
         * Jika sukses akan memunculkan Toast
         */
        if(database!=null){
            database.child("agenda").child(agenda.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Agenda_Read.this,"Berhasil hapus data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Function Mencari data agenda berdasarkan penanggungjawab dari Firebase Realtime DB
     */
    private void FirebaseSearch(String searchText) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("agenda").orderByChild("penanggungjawab")
                .startAt(searchText).endAt(searchText + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarAgenda = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    DataAgenda agenda = noteDataSnapshot.getValue(DataAgenda.class);
                    agenda.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarAgenda.add(agenda);
                }
                adapter = new Adapter_RV_Agenda(daftarAgenda, Agenda_Read.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+" "+error.getMessage());
            }
        });
    }//end of search acara agenda function

}//end of public class