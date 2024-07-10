package services;

import models.Pinjaman;
import repositories.PinjamanRepository;

import java.sql.SQLException;
import java.util.List;

public class PinjamanService {
    private PinjamanRepository pinjamanRepository;

    public PinjamanService() {
        this.pinjamanRepository = new PinjamanRepository();
    }

    public void createPinjaman(Pinjaman pinjaman) throws SQLException {
        pinjamanRepository.createPinjaman(pinjaman);
    }

    public Pinjaman getPinjamanById(int id) throws SQLException {
        return pinjamanRepository.getPinjamanById(id);
    }

    public List<Pinjaman> getAllPinjaman() throws SQLException {
        return pinjamanRepository.getAllPinjaman();
    }

    public void updatePinjaman(Pinjaman pinjaman) throws SQLException {
        pinjamanRepository.updatePinjaman(pinjaman);
    }

    public void deletePinjaman(int id) throws SQLException {
        pinjamanRepository.deletePinjaman(id);
    }
}
