package models;

import java.math.BigDecimal;
import java.sql.Date;

public class Simpanan {
    private int id;
    private int idAnggota;
    private BigDecimal jumlah;
    private Date tanggalSimpan;

    public Simpanan(int id, int idAnggota, BigDecimal jumlah, Date tanggalSimpan) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.jumlah = jumlah;
        this.tanggalSimpan = tanggalSimpan;
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

    public Date getTanggalSimpan() {
        return tanggalSimpan;
    }

    public void setTanggalSimpan(Date tanggalSimpan) {
        this.tanggalSimpan = tanggalSimpan;
    }
}
