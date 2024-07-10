package controllers;

import java.math.BigDecimal;
import models.Simpanan;
import services.SimpananService;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

public class SimpananController {
    private SimpananService simpananService;

    public SimpananController() {
        this.simpananService = new SimpananService();
    }

    public String createSimpanan(int idAnggota, BigDecimal jumlah, Date tanggalSimpan) {
        try {
            simpananService.createSimpanan(new Simpanan(0, idAnggota, jumlah, tanggalSimpan));   
            return "Simpanan berhasil ditambahkan.";
        } catch (SQLException e) {
            return "Gagal membuat simpanan: " + e.getMessage();
        }
    }

    public Simpanan getSimpananById(int id) throws SQLException {
        return simpananService.getSimpananById(id);
    }

    public List<Simpanan> getAllSimpanan() throws SQLException {
        return simpananService.getAllSimpanan();
    }

    public String updateSimpanan(int id, int idAnggota, BigDecimal jumlah, Date tanggalSimpan) {
        try {
            simpananService.updateSimpanan(new Simpanan(id, idAnggota, jumlah, tanggalSimpan));   
        } catch (SQLException e) {
            return "Gagal menghapus simpanan: " + e.getMessage();
        }
        return "Simpanan berhasil diupdate.";
    }

    public String deleteSimpanan(int id) {
        try {
            simpananService.deleteSimpanan(id);
            return "Simpanan berhasil dihapus.";
        } catch (SQLException e) {
            return "Gagal menghapus simpanan: " + e.getMessage();
        }
    }
}
