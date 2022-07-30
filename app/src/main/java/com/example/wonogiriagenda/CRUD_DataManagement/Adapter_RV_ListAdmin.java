package com.example.wonogiriagenda.CRUD_DataManagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataRegister;
import com.example.wonogiriagenda.loginregister.RegisterActivity;
import com.example.wonogiriagenda.superadmin.SuperAdmin_ListAdmin;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Adapter_RV_ListAdmin extends RecyclerView.ViewHolder{
    public TextView tvNama;
    public TextView tvEmail;
    public TextView tvUsername;
    public TextView tvPassword;
    public TextView tvNip;
    public TextView tvRole;
    public Button btnDelete;

    public Adapter_RV_ListAdmin(View itemView) {
        super(itemView);
        tvNama = itemView.findViewById(R.id.et_item_nama);
        tvEmail = itemView.findViewById(R.id.et_item_email);
        tvUsername = itemView.findViewById(R.id.et_item_username);
        tvPassword = itemView.findViewById(R.id.et_item_password);
        tvNip = itemView.findViewById(R.id.et_item_nip);
        tvRole = itemView.findViewById(R.id.et_item_role);
        btnDelete = itemView.findViewById(R.id.btn_delete);
    }

    @SuppressLint("SetTextI18n")
    public void bindToListAdmin(DataRegister register, View.OnClickListener onClickListener){
        tvNama.setText("Nama: "+register.getNama());
        tvEmail.setText("Email: "+register.getEmail());
        tvUsername.setText("Username: "+register.getUsername());
        tvPassword.setText("Password: "+register.getPassword());
        tvNip.setText("NIP: "+register.getNip());
        tvRole.setText("Role: "+register.getAs());
        btnDelete.setOnClickListener(onClickListener);
    }
}
