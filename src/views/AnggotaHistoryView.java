package views;

import controllers.AnggotaController;
import models.Anggota;
import models.Pinjaman;
import models.Simpanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AnggotaHistoryView extends JFrame {
    private AnggotaController anggotaController;
    private int anggotaId;
    private JTabbedPane tabbedPane;
    private JPanel simpananPanel;
    private JPanel pinjamanPanel;
    private JLabel infoLabel;
    private JButton backButton;

    public AnggotaHistoryView(int anggotaId) throws SQLException {
        this.anggotaId = anggotaId;
        this.anggotaController = new AnggotaController();
        initUI();
    }

    private void initUI() throws SQLException {
        setTitle("Anggota History");
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        simpananPanel = new JPanel(new BorderLayout());
        simpananPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        loadSimpananTableData();
        tabbedPane.addTab("Riwayat Simpanan", simpananPanel);

        pinjamanPanel = new JPanel(new BorderLayout());
        pinjamanPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        loadPinjamanTableData();
        tabbedPane.addTab("Riwayat Pinjaman", pinjamanPanel);

        add(tabbedPane, BorderLayout.CENTER);

        infoLabel = new JLabel();
        loadAnggotaInfo();
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.NORTH);

        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new AdminDashboardView().setVisible(true); // Show AdminDashboardView
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadAnggotaInfo() {
        try {
            Anggota anggota = anggotaController.getAnggotaById(anggotaId);
            if (anggota != null) {
                infoLabel.setText("<html>"
                        + "<b>ID:</b> " + anggota.getId() + "<br>"
                        + "<b>Nama:</b> " + anggota.getNama() + "<br>"
                        + "<b>Alamat:</b> " + anggota.getAlamat() + "<br>"
                        + "<b>Telepon:</b> " + anggota.getTelepon() + "<br>"
                        + "<b>Email:</b> " + anggota.getEmail() + "<br>"
                        + "<b>Tanggal Bergabung:</b> " + anggota.getTanggalBergabung() + "</html>");
            } else {
                infoLabel.setText("Anggota tidak ditemukan.");
            }
        } catch (SQLException e) {
            infoLabel.setText("Gagal memuat informasi anggota: " + e.getMessage());
        }
    }

    private void loadSimpananTableData() throws SQLException {
        List<Simpanan> simpananList = anggotaController.getSimpananHistory(anggotaId);
        String[] columnNames = {"Simpanan ID", "Jumlah Simpanan", "Tanggal Simpan"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Simpanan history : simpananList) {
            Object[] row = {
                history.getId(),
                history.getJumlah(),
                history.getTanggalSimpan()
            };
            model.addRow(row);
        }
        JTable simpananTable = new JTable(model);
        simpananPanel.add(new JScrollPane(simpananTable), BorderLayout.CENTER);
    }

    private void loadPinjamanTableData() throws SQLException {
        List<Pinjaman> pinjamanList = anggotaController.getPinjamanHistory(anggotaId);
        String[] columnNames = {"Pinjaman ID", "Jumlah Pinjaman", "Tanggal Pinjam", "Tanggal Jatuh Tempo", "Status Pinjaman"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Pinjaman history : pinjamanList) {
            Object[] row = {
                history.getId(),
                history.getJumlah(),
                history.getTanggalPinjam(),
                history.getTanggalJatuhTempo(),
                history.getStatus()
            };
            model.addRow(row);
        }
        JTable pinjamanTable = new JTable(model);
        pinjamanPanel.add(new JScrollPane(pinjamanTable), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AnggotaHistoryView view = new AnggotaHistoryView(1);
                view.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
