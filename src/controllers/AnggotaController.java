package controllers;

import models.Anggota;
import services.AnggotaService;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class AnggotaController {
    private AnggotaService anggotaService;

    public AnggotaController() {
        this.anggotaService = new AnggotaService();
    }

    public String createAnggota(String nama, String alamat, String telepon, String email, String password, Date tanggalBergabung) throws SQLException {
        anggotaService.createAnggota(new Anggota(0, nama, alamat, telepon, email, password, tanggalBergabung));
        return "Anggota berhasil ditambahkan.";
    }

    public Anggota getAnggotaById(int id) throws SQLException {
        return anggotaService.getAnggotaById(id);
    }

    public List<Anggota> getAllAnggota() throws SQLException {
        return anggotaService.getAllAnggota();
    }

    public String updateAnggota(int id, String nama, String alamat, String telepon, String email, String password, Date tanggalBergabung) throws SQLException {
        anggotaService.updateAnggota(new Anggota(id, nama, alamat, telepon, email, password, tanggalBergabung));
        return "Anggota berhasil diupdate.";
    }

    public String deleteAnggota(int id) {
        try {
            anggotaService.deleteAnggota(id);
            return "Anggota berhasil dihapus.";
        } catch (SQLException e) {
            return "Gagal menghapus anggota: " + e.getMessage();
        }
    }
}
