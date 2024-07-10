package models;

import java.sql.Date;

public class Anggota {
    private int id;
    private String nama;
    private String alamat;
    private String telepon;
    private String email;
    private String password;
    private Date tanggalBergabung;

    public Anggota(int id, String nama, String alamat, String telepon, String email, String password, Date tanggalBergabung) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.email = email;
        this.password = password;
        this.tanggalBergabung = tanggalBergabung;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
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

    public Date getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(Date tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }
    
    @Override
    public String toString() {
        return this.nama; // This will display the name in the JComboBox
    }
}
