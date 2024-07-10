package controllers;

import java.math.BigDecimal;
import models.Pinjaman;
import services.PinjamanService;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

public class PinjamanController {
    private PinjamanService pinjamanService;

    public PinjamanController() {
        this.pinjamanService = new PinjamanService();
    }

    public String createPinjaman(int idAnggota, BigDecimal jumlah, Date tanggalPinjam, Date tanggalJatuhTempo, String status) throws SQLException {
        pinjamanService.createPinjaman(new Pinjaman(0, idAnggota, jumlah, tanggalPinjam, tanggalJatuhTempo, status));
        return "Pinjaman berhasil ditambahkan.";
    }

    public Pinjaman getPinjamanById(int id) throws SQLException {
        return pinjamanService.getPinjamanById(id);
    }

    public List<Pinjaman> getAllPinjaman() throws SQLException {
        return pinjamanService.getAllPinjaman();
    }

    public String updatePinjaman(int id, int idAnggota, BigDecimal jumlah, Date tanggalPinjam, Date tanggalJatuhTempo, String status) {
        try {
            pinjamanService.updatePinjaman(new Pinjaman(id, idAnggota, jumlah, tanggalPinjam, tanggalJatuhTempo, status));
            return "Pinjaman berhasil diupdate.";
        } catch (SQLException e) {
            return "Gagal mengupdate pinjaman: " + e.getMessage();
        }
    }

    public String deletePinjaman(int id) {
        try {
            pinjamanService.deletePinjaman(id);
            return "Pinjaman berhasil dihapus.";
        } catch (SQLException e) {
            return "Gagal menghapus pinjaman: " + e.getMessage();
        }
    }
}
