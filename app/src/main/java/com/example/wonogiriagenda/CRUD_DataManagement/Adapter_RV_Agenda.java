package com.example.wonogiriagenda.CRUD_DataManagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wonogiriagenda.R;
import com.example.wonogiriagenda.dataitem.DataAgenda;

import java.util.ArrayList;

public class Adapter_RV_Agenda extends RecyclerView.Adapter<Adapter_RV_Agenda.ViewHolder> {
    private final ArrayList<DataAgenda> daftarAgenda;
    private final Context context;
    FirebaseDataListener listener;

    public Adapter_RV_Agenda(ArrayList<DataAgenda> agenda, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarAgenda = agenda;
        context = ctx;
        listener = (Agenda_Read)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPj, tvTanggal;

        ViewHolder(View v) {
            super(v);
            tvPj = v.findViewById(R.id.et_item_namapj);
            tvTitle = v.findViewById(R.id.et_item_namakegiatan);
            tvTanggal = v.findViewById(R.id.et_item_tanggal);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_agenda, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }//end of oncreateviewholder

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        /**
         *  Menampilkan data dari database pada view
         */
        final String acara = daftarAgenda.get(position).getAcara();
        final String namapj = daftarAgenda.get(position).getPenanggungjawab();
        final String tanggal = daftarAgenda.get(position).getTanggal();

        System.out.println("Data Agenda: "+position+daftarAgenda.size());

        //START - function untuk menampilkan data dengan klik 1x pada nama,judul acara, tanggal
        holder.tvPj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(Agenda_See.getActIntent((Activity) context).putExtra("data", daftarAgenda.get(position)));
            }
        });
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(Agenda_See.getActIntent((Activity) context).putExtra("data", daftarAgenda.get(position)));
            }
        });
        holder.tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(Agenda_See.getActIntent((Activity) context).putExtra("data", daftarAgenda.get(position)));
            }
        });
        //END - function untuk menampilkan data dengan klik 1x pada nama,judul acara, tanggal

        //START - function untuk menampilkan dialog button
        // edit dan delete data dengan klik dan tahan pada nama,judul acara, tanggal
        holder.tvTanggal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogview_agenda);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = dialog.findViewById(R.id.bt_edit_data);
                Button delButton = dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(Agenda_Create.getActIntent((Activity) context).
                                        putExtra("data", daftarAgenda.get(position)));
                            }
                        }
                );

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Kodingan untuk Delete data (memanggil interface delete data)
                                 */
                                dialog.dismiss();
                                listener.onDeleteData(daftarAgenda.get(position), position);
                            }
                        }
                );
                return true;
            }
        });

        holder.tvPj.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogview_agenda);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = dialog.findViewById(R.id.bt_edit_data);
                Button delButton = dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(Agenda_Create.getActIntent((Activity) context).
                                        putExtra("data", daftarAgenda.get(position)));
                            }
                        }
                );
                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Kodingan untuk Delete data (memanggil interface delete data)
                                 */
                                dialog.dismiss();
                                listener.onDeleteData(daftarAgenda.get(position), position);
                            }
                        }
                );
                return true;
            }
        });

        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogview_agenda);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = dialog.findViewById(R.id.bt_edit_data);
                Button delButton = dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(Agenda_Create.getActIntent((Activity) context).
                                        putExtra("data", daftarAgenda.get(position)));
                            }
                        }
                );

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Kodingan untuk Delete data (memanggil interface delete data)
                                 */
                                dialog.dismiss();
                                listener.onDeleteData(daftarAgenda.get(position), position);
                            }
                        }
                );
                return true;
            }
        });//END - function untuk menampilkan dialog button edit dan delete

        //Set data ke textview dari database yang telah diinisialisasi di bindviewholder
        holder.tvPj.setText(namapj);
        holder.tvTitle.setText(acara);
        holder.tvTanggal.setText(tanggal);
    } //end of bindviewholder


    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarAgenda.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(DataAgenda agenda, int position);
    }
} //end of public class
