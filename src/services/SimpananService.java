package services;

import models.Simpanan;
import repositories.SimpananRepository;

import java.sql.SQLException;
import java.util.List;

public class SimpananService {
    private SimpananRepository simpananRepository;

    public SimpananService() {
        this.simpananRepository = new SimpananRepository();
    }

    public void createSimpanan(Simpanan simpanan) throws SQLException {
        simpananRepository.createSimpanan(simpanan);
    }

    public Simpanan getSimpananById(int id) throws SQLException {
        return simpananRepository.getSimpananById(id);
    }

    public List<Simpanan> getAllSimpanan() throws SQLException {
        return simpananRepository.getAllSimpanan();
    }

    public void updateSimpanan(Simpanan simpanan) throws SQLException {
        simpananRepository.updateSimpanan(simpanan);
    }

    public void deleteSimpanan(int id) throws SQLException {
        simpananRepository.deleteSimpanan(id);
    }
}
