package com.example.wonogiriagenda.superadmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonogiriagenda.CRUD_DataManagement.Adapter_RV_ListAdmin;
import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataRegister;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions.Builder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SuperAdmin_ListAdmin extends AppCompatActivity {
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private FirebaseRecyclerAdapter<DataRegister, Adapter_RV_ListAdmin> mAdapter;
    private int posisidata;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_list_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        RecyclerView mRecycler = findViewById(R.id.rv_register);
        mRecycler.setHasFixedSize(true);

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new Builder<DataRegister>()
                .setQuery(query, DataRegister.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<DataRegister, Adapter_RV_ListAdmin>(options) {
            @NonNull
            @Override
            public Adapter_RV_ListAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new Adapter_RV_ListAdmin(inflater.inflate(R.layout.itemview_listadmin, parent, false));
            }
            @Override
            protected void onBindViewHolder(@NonNull Adapter_RV_ListAdmin holder, int position, @NonNull final DataRegister model) {
                holder.bindToListAdmin (model, v -> {
                    //deleteAccount();
                    deleteAccountData(model, posisidata);
                });
            }
        };

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }//end of oncreate

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, SuperAdmin_ListAdmin.class);
    }

    public void deleteAccountData(DataRegister register, final int position) {
        /*
          Kode ini akan memanggil method ShowDeleteDialog
          Yang kemudian akan mendelete data di Firebase Realtime DB berdasarkan username.
          Jika sukses akan memunculkan Toast
         */
        ShowDeleteDialog(register);
    }

    private void ShowDeleteDialog(DataRegister register) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title dialog
        alertDialogBuilder.setTitle("DELETE ACCOUNT");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin akan menghapus akun " + register.getUsername()+" ?")
                .setIcon(R.mipmap.logo_wonnda_foreground)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    /*
                    Jika memilih ya maka akan melakukan proses hapus data akun pada database
                     */
                    if(mDatabase!=null){
                        mDatabase.child("register").child(register.getUsername())
                                .removeValue()
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(SuperAdmin_ListAdmin.this,
                                        "Data akun berhasil dihapus",
                                        Toast.LENGTH_LONG).show());
                    }
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

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private Query getQuery(DatabaseReference mDatabase){
        return mDatabase.child("register");
    }
}//end of public class