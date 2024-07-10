package repositories;

import models.Pinjaman;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.Database;

public class PinjamanRepository {

    public void createPinjaman(Pinjaman pinjaman) throws SQLException {
        String query = "INSERT INTO pinjaman (id_anggota, jumlah, tanggal_pinjam, tanggal_jatuh_tempo, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, pinjaman.getIdAnggota());
            statement.setBigDecimal(2, pinjaman.getJumlah());
            statement.setDate(3, pinjaman.getTanggalPinjam());
            statement.setDate(4, pinjaman.getTanggalJatuhTempo());
            statement.setString(5, pinjaman.getStatus());
            statement.executeUpdate();
        }
    }

    public Pinjaman getPinjamanById(int id) throws SQLException {
        String query = "SELECT * FROM pinjaman WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pinjaman(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_anggota"),
                    resultSet.getBigDecimal("jumlah"),
                    resultSet.getDate("tanggal_pinjam"),
                    resultSet.getDate("tanggal_jatuh_tempo"),
                    resultSet.getString("status")
                );
            }
        }
        return null;
    }

    public List<Pinjaman> getAllPinjaman() throws SQLException {
        List<Pinjaman> pinjamanList = new ArrayList<>();
        String query = "SELECT * FROM pinjaman";
        try (Statement statement = Database.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                pinjamanList.add(new Pinjaman(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_anggota"),
                    resultSet.getBigDecimal("jumlah"),
                    resultSet.getDate("tanggal_pinjam"),
                    resultSet.getDate("tanggal_jatuh_tempo"),
                    resultSet.getString("status")
                ));
            }
        }
        return pinjamanList;
    }

    public void updatePinjaman(Pinjaman pinjaman) throws SQLException {
        String query = "UPDATE pinjaman SET id_anggota = ?, jumlah = ?, tanggal_pinjam = ?, tanggal_jatuh_tempo = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, pinjaman.getIdAnggota());
            statement.setBigDecimal(2, pinjaman.getJumlah());
            statement.setDate(3, pinjaman.getTanggalPinjam());
            statement.setDate(4, pinjaman.getTanggalJatuhTempo());
            statement.setString(5, pinjaman.getStatus());
            statement.setInt(6, pinjaman.getId());
            statement.executeUpdate();
        }
    }

    public void deletePinjaman(int id) throws SQLException {
        String query = "DELETE FROM pinjaman WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
