package models;

import java.math.BigDecimal;
import java.sql.Date;

public class Pinjaman {
    private int id;
    private int idAnggota;
    private BigDecimal jumlah;
    private Date tanggalPinjam;
    private Date tanggalJatuhTempo;
    private String status;

    public Pinjaman(int id, int idAnggota, BigDecimal jumlah, Date tanggalPinjam, Date tanggalJatuhTempo, String status) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.jumlah = jumlah;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.status = status;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public BigDecimal getJumlah() {
        return jumlah;
    }

    public void setJumlah(BigDecimal jumlah) {
        this.jumlah = jumlah;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
