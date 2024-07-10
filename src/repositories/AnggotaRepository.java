package repositories;

import models.Anggota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.Database;

public class AnggotaRepository {

    public void createAnggota(Anggota anggota) throws SQLException {
        String query = "INSERT INTO anggota (nama, alamat, telepon, email, password, tanggal_bergabung) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, anggota.getNama());
            statement.setString(2, anggota.getAlamat());
            statement.setString(3, anggota.getTelepon());
            statement.setString(4, anggota.getEmail());
            statement.setString(5, anggota.getPassword());
            statement.setDate(6, anggota.getTanggalBergabung());
            statement.executeUpdate();
        }
    }

    public Anggota getAnggotaById(int id) throws SQLException {
        String query = "SELECT * FROM anggota WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Anggota(
                    resultSet.getInt("id"),
                    resultSet.getString("nama"),
                    resultSet.getString("alamat"),
                    resultSet.getString("telepon"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getDate("tanggal_bergabung")
                );
            }
        }
        return null;
    }

    public List<Anggota> getAllAnggota() throws SQLException {
        List<Anggota> anggotaList = new ArrayList<>();
        String query = "SELECT * FROM anggota";
        try (Statement statement = Database.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                anggotaList.add(new Anggota(
                    resultSet.getInt("id"),
                    resultSet.getString("nama"),
                    resultSet.getString("alamat"),
                    resultSet.getString("telepon"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getDate("tanggal_bergabung")
                ));
            }
        }
        return anggotaList;
    }

    public void updateAnggota(Anggota anggota) throws SQLException {
        String query = "UPDATE anggota SET nama = ?, alamat = ?, telepon = ?, email = ?, password = ?, tanggal_bergabung = ? WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, anggota.getNama());
            statement.setString(2, anggota.getAlamat());
            statement.setString(3, anggota.getTelepon());
            statement.setString(4, anggota.getEmail());
            statement.setString(5, anggota.getPassword());
            statement.setDate(6, anggota.getTanggalBergabung());
            statement.setInt(7, anggota.getId());
            statement.executeUpdate();
        }
    }

    public void deleteAnggota(int id) throws SQLException {
        String query = "DELETE FROM anggota WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
