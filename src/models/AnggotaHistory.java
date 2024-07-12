package models;

import java.math.BigDecimal;
import java.sql.Date;

public class AnggotaHistory {
    private int anggotaId;
    private String nama;
    private String alamat;
    private String telepon;
    private String email;
    private Date tanggalBergabung;
    private int simpananId;
    private BigDecimal jumlahSimpanan;
    private Date tanggalSimpan;
    private int pinjamanId;
    private BigDecimal jumlahPinjaman;
    private Date tanggalPinjam;
    private Date tanggalJatuhTempo;
    private String statusPinjaman;

    // Constructors, getters, and setters

    public AnggotaHistory(int anggotaId, String nama, String alamat, String telepon, String email, Date tanggalBergabung, int simpananId, BigDecimal jumlahSimpanan, Date tanggalSimpan, int pinjamanId, BigDecimal jumlahPinjaman, Date tanggalPinjam, Date tanggalJatuhTempo, String statusPinjaman) {
        this.anggotaId = anggotaId;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.email = email;
        this.tanggalBergabung = tanggalBergabung;
        this.simpananId = simpananId;
        this.jumlahSimpanan = jumlahSimpanan;
        this.tanggalSimpan = tanggalSimpan;
        this.pinjamanId = pinjamanId;
        this.jumlahPinjaman = jumlahPinjaman;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.statusPinjaman = statusPinjaman;
    }
    
    

    // Getters and setters...

    public int getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(int anggotaId) {
        this.anggotaId = anggotaId;
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

    public Date getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(Date tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    public int getSimpananId() {
        return simpananId;
    }

    public void setSimpananId(int simpananId) {
        this.simpananId = simpananId;
    }

    public BigDecimal getJumlahSimpanan() {
        return jumlahSimpanan;
    }

    public void setJumlahSimpanan(BigDecimal jumlahSimpanan) {
        this.jumlahSimpanan = jumlahSimpanan;
    }

    public Date getTanggalSimpan() {
        return tanggalSimpan;
    }

    public void setTanggalSimpan(Date tanggalSimpan) {
        this.tanggalSimpan = tanggalSimpan;
    }

    public int getPinjamanId() {
        return pinjamanId;
    }

    public void setPinjamanId(int pinjamanId) {
        this.pinjamanId = pinjamanId;
    }

    public BigDecimal getJumlahPinjaman() {
        return jumlahPinjaman;
    }

    public void setJumlahPinjaman(BigDecimal jumlahPinjaman) {
        this.jumlahPinjaman = jumlahPinjaman;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public Date getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public void setTanggalJatuhTempo(Date tanggalJatuhTempo) {
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }

    public String getStatusPinjaman() {
        return statusPinjaman;
    }

    public void setStatusPinjaman(String statusPinjaman) {
        this.statusPinjaman = statusPinjaman;
    }
}
