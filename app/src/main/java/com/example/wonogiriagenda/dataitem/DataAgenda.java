package com.example.wonogiriagenda.dataitem;

import java.io.Serializable;

public class DataAgenda implements Serializable {
    private String penanggungjawab;
    private String tempat;
    private String acara;
    private String tanggal;
    private String waktu_mulai;
    private String waktu_berakhir;
    private String key;

    public DataAgenda(){

    }

    public DataAgenda(String penanggungjawab, String tempat, String acara, String tanggal, String waktu_mulai, String waktu_berakhir, String key) {
        this.penanggungjawab = penanggungjawab;
        this.tempat = tempat;
        this.acara = acara;
        this.tanggal = tanggal;
        this.waktu_mulai = waktu_mulai;
        this.waktu_berakhir = waktu_berakhir;
        this.key = key;
    }

    @Override
    public String toString() {
        return "DataAgenda{" +
                "penanggungjawab='" + penanggungjawab + '\'' +
                ", tempat='" + tempat + '\'' +
                ", acara='" + acara + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", waktu_mulai='" + waktu_mulai + '\'' +
                ", waktu_berakhir='" + waktu_berakhir + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getPenanggungjawab() {
        return penanggungjawab;
    }

    public void setPenanggungjawab(String penanggungjawab) {
        this.penanggungjawab = penanggungjawab;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getAcara() {
        return acara;
    }

    public void setAcara(String acara) {
        this.acara = acara;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getWaktu_berakhir() {
        return waktu_berakhir;
    }

    public void setWaktu_berakhir(String waktu_berakhir) {
        this.waktu_berakhir = waktu_berakhir;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
