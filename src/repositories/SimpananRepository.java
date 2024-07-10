package repositories;

import models.Simpanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.Database;

public class SimpananRepository {

    public void createSimpanan(Simpanan simpanan) throws SQLException {
        String query = "INSERT INTO simpanan (id_anggota, jumlah, tanggal_simpan) VALUES (?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, simpanan.getIdAnggota());
            statement.setBigDecimal(2, simpanan.getJumlah());
            statement.setDate(3, simpanan.getTanggalSimpan());
            statement.executeUpdate();
        }
    }

    public Simpanan getSimpananById(int id) throws SQLException {
        String query = "SELECT * FROM simpanan WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Simpanan(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_anggota"),
                    resultSet.getBigDecimal("jumlah"),
                    resultSet.getDate("tanggal_simpan")
                );
            }
        }
        return null;
    }

    public List<Simpanan> getAllSimpanan() throws SQLException {
        List<Simpanan> simpananList = new ArrayList<>();
        String query = "SELECT * FROM simpanan";
        try (Statement statement = Database.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                simpananList.add(new Simpanan(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_anggota"),
                    resultSet.getBigDecimal("jumlah"),
                    resultSet.getDate("tanggal_simpan")
                ));
            }
        }
        return simpananList;
    }

    public void updateSimpanan(Simpanan simpanan) throws SQLException {
        String query = "UPDATE simpanan SET id_anggota = ?, jumlah = ?, tanggal_simpan = ? WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, simpanan.getIdAnggota());
            statement.setBigDecimal(2, simpanan.getJumlah());
            statement.setDate(3, simpanan.getTanggalSimpan());
            statement.setInt(4, simpanan.getId());
            statement.executeUpdate();
        }
    }

    public void deleteSimpanan(int id) throws SQLException {
        String query = "DELETE FROM simpanan WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
