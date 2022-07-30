package com.example.wonogiriagenda.dataitem;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataRegister implements Serializable {
    String nama, username, email, password, nip, as;
    String key;
    public DataRegister(){

    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama", nama);
        result.put("username", username);
        result.put("email", email);
        result.put("password",password);
        result.put("nip", nip);
        result.put("as", as);
        return result;
    }
    public DataRegister(String nama, String username, String email, String password, String nip, String as) {
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nip = nip;
        this.as = as;
    }

    @Override
    public String toString() {
        return "DataRegister{" +
                "nama='" + nama + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nip='" + nip + '\'' +
                ", as='" + as + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }
}
