package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import controllers.AnggotaController;
import models.Anggota;
import java.sql.Date;
import java.time.LocalDate;

public class AnggotaView extends JFrame {

    private AnggotaController anggotaController;
    private JTable table;
    private DefaultTableModel model;

    public AnggotaView() throws SQLException {
        anggotaController = new AnggotaController();

        setTitle("Anggota Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Set background color to white
        getContentPane().setBackground(Color.WHITE);

        // Panel untuk tombol tambah, update, dan hapus
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE); // Set button panel background to white
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Hapus");
        JButton backButton = new JButton("Kembali");
        JButton historyButton = new JButton("History");

        // Set button colors to green
        addButton.setBackground(Color.GREEN);
        updateButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.RED);
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        historyButton.setBackground(Color.YELLOW);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        buttonPanel.add(historyButton);

        // Tambah event listener untuk tombol tambah, update, dan hapus
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showAnggotaForm(null);
                } catch (SQLException ex) {
                    Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int anggotaId = (int) model.getValueAt(selectedRow, 0);
                        showAnggotaForm(anggotaId);
                    } else {
                        JOptionPane.showMessageDialog(AnggotaView.this, "Pilih anggota yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int anggotaId = (int) model.getValueAt(selectedRow, 0);
                    try {
                        AnggotaHistoryView historyView = new AnggotaHistoryView(anggotaId);
                        historyView.setVisible(true);
                        dispose();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(AnggotaView.this, "Pilih anggota yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedAnggota();
                } catch (SQLException ex) {
                    Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new AdminDashboardView().setVisible(true); // Show AdminDashboardView
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        // Table untuk menampilkan data anggota
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat sel dalam tabel tidak bisa diedit secara langsung
            }
        };
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Email");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        model.addColumn("Tanggal Bergabung");

        // Mengatur style dan padding untuk tabel
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5)); // Padding antar sel

        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);

        // Tampilkan data anggota saat aplikasi dimulai
        refreshTable();
    }

    // Method untuk menampilkan data anggota dalam tabel
    private void refreshTable() throws SQLException {
        List<Anggota> anggotaList = anggotaController.getAllAnggota();
        model.setRowCount(0); // Clear table
        for (Anggota anggota : anggotaList) {
            model.addRow(new Object[]{anggota.getId(), anggota.getNama(), anggota.getEmail(), anggota.getAlamat(), anggota.getTelepon(), anggota.getTanggalBergabung()});
        }
    }

    // Method untuk menambahkan atau mengupdate anggota
    private void showAnggotaForm(Integer anggotaId) throws SQLException {
        JDialog formDialog = new JDialog(this, anggotaId == null ? "Tambah Anggota" : "Update Anggota", true);
        formDialog.setSize(400, 300);
        formDialog.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel alamatLabel = new JLabel("Alamat:");
        JTextField alamatField = new JTextField();
        JLabel teleponLabel = new JLabel("Telepon:");
        JTextField teleponField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton(anggotaId == null ? "Tambah" : "Update");
        saveButton.setBackground(Color.GREEN);
        JButton cancelButton = new JButton("Batal");
        cancelButton.setBackground(Color.RED);

        formDialog.add(nameLabel);
        formDialog.add(nameField);
        formDialog.add(emailLabel);
        formDialog.add(emailField);
        formDialog.add(alamatLabel);
        formDialog.add(alamatField);
        formDialog.add(teleponLabel);
        formDialog.add(teleponField);
        formDialog.add(passwordLabel);
        formDialog.add(passwordField);
        formDialog.add(saveButton);
        formDialog.add(cancelButton);

        if (anggotaId != null) {
            Anggota anggota = anggotaController.getAnggotaById(anggotaId);
            nameField.setText(anggota.getNama());
            emailField.setText(anggota.getEmail());
            alamatField.setText(anggota.getAlamat());
            teleponField.setText(anggota.getTelepon());
        }

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nama = nameField.getText();
                    String email = emailField.getText();
                    String alamat = alamatField.getText();
                    String telepon = teleponField.getText();
                    String password = new String(passwordField.getPassword());

                    if (nama != null && email != null && !nama.isEmpty() && !email.isEmpty()) {
                        LocalDate now = LocalDate.now();
                        if (anggotaId == null) {
                            anggotaController.createAnggota(nama, email, alamat, telepon, password, Date.valueOf(now));
                        } else {
                            anggotaController.updateAnggota(anggotaId, nama, email, alamat, telepon, password, Date.valueOf(now));
                        }
                        refreshTable();
                        formDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(formDialog, "Masukan semua data dengan benar", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formDialog.dispose();
            }
        });

        formDialog.setVisible(true);
    }

    // Method untuk menghapus anggota yang dipilih
    private void deleteSelectedAnggota() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int anggotaId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus anggota ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                anggotaController.deleteAnggota(anggotaId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AnggotaView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AnggotaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
