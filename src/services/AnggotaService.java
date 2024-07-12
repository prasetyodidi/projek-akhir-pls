package services;

import models.Anggota;
import repositories.AnggotaRepository;

import java.sql.SQLException;
import java.util.List;
import models.Simpanan;
import models.Pinjaman;

public class AnggotaService {

    private AnggotaRepository anggotaRepository;

    public AnggotaService() {
        this.anggotaRepository = new AnggotaRepository();
    }

    public void createAnggota(Anggota anggota) throws SQLException {
        anggotaRepository.createAnggota(anggota);
    }

    public Anggota getAnggotaById(int id) throws SQLException {
        return anggotaRepository.getAnggotaById(id);
    }

    public List<Anggota> getAllAnggota() throws SQLException {
        return anggotaRepository.getAllAnggota();
    }

    public List<Simpanan> getSimpananHistory(int id) throws SQLException {
        return anggotaRepository.getSimpananHistory(id);
    }

    public List<Pinjaman> getPinjamanHistory(int id) throws SQLException {
        return anggotaRepository.getPinjamanHistory(id);
    }

    public void updateAnggota(Anggota anggota) throws SQLException {
        anggotaRepository.updateAnggota(anggota);
    }

    public void deleteAnggota(int id) throws SQLException {
        anggotaRepository.deleteAnggota(id);
    }
}
