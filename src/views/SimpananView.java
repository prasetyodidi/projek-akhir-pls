package views;

import components.DatePicker;
import controllers.AnggotaController;
import controllers.SimpananController;
import models.Anggota;
import models.Simpanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpananView extends JFrame {

    private SimpananController simpananController;
    private AnggotaController anggotaController;
    private JTable table;
    private DefaultTableModel model;

    public SimpananView() throws SQLException {
        simpananController = new SimpananController();
        anggotaController = new AnggotaController();

        setTitle("Simpanan Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color to white
        getContentPane().setBackground(Color.WHITE);

        // Panel untuk tombol tambah, update, dan hapus
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE); // Set button panel background to white
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Hapus");

        // Set button colors to green
        addButton.setBackground(Color.GREEN);
        updateButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.GREEN);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Tambah event listener untuk tombol tambah, update, dan hapus
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showSimpananForm(null);
                } catch (SQLException ex) {
                    Logger.getLogger(SimpananView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int simpananId = (int) model.getValueAt(selectedRow, 0);
                        showSimpananForm(simpananId);
                    } else {
                        JOptionPane.showMessageDialog(SimpananView.this, "Pilih simpanan yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SimpananView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedSimpanan();
                } catch (SQLException ex) {
                    Logger.getLogger(SimpananView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        // Table untuk menampilkan data simpanan
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("ID Anggota");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal Simpan");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tampilkan data simpanan saat aplikasi dimulai
        refreshTable();
    }

    // Method untuk menampilkan data simpanan dalam tabel
    private void refreshTable() throws SQLException {
        List<Simpanan> simpananList = simpananController.getAllSimpanan();
        model.setRowCount(0); // Clear table
        for (Simpanan simpanan : simpananList) {
            model.addRow(new Object[]{simpanan.getId(), simpanan.getIdAnggota(), simpanan.getJumlah(), simpanan.getTanggalSimpan()});
        }
    }

    // Method untuk menambahkan atau mengupdate simpanan
    private void showSimpananForm(Integer simpananId) throws SQLException {
        JDialog formDialog = new JDialog(this, simpananId == null ? "Tambah Simpanan" : "Update Simpanan", true);
        formDialog.setSize(400, 300);
        formDialog.setLayout(new GridLayout(4, 2));

        JLabel idAnggotaLabel = new JLabel("ID Anggota:");
        JComboBox<Anggota> idAnggotaComboBox = new JComboBox<>();
        List<Anggota> anggotaList = anggotaController.getAllAnggota();
        for (Anggota anggota : anggotaList) {
            idAnggotaComboBox.addItem(anggota);
        }

        JLabel jumlahLabel = new JLabel("Jumlah:");
        JTextField jumlahField = new JTextField();
        JLabel tanggalSimpanLabel = new JLabel("Tanggal Simpan:");
        DatePicker datePickerPanel = new DatePicker();

        JButton saveButton = new JButton(simpananId == null ? "Tambah" : "Update");
        saveButton.setBackground(Color.GREEN);
        JButton cancelButton = new JButton("Batal");
        cancelButton.setBackground(Color.RED);

        formDialog.add(idAnggotaLabel);
        formDialog.add(idAnggotaComboBox);
        formDialog.add(jumlahLabel);
        formDialog.add(jumlahField);
        formDialog.add(tanggalSimpanLabel);
        formDialog.add(datePickerPanel);
        formDialog.add(saveButton);
        formDialog.add(cancelButton);

        if (simpananId != null) {
            Simpanan simpanan = simpananController.getSimpananById(simpananId);
            for (int i = 0; i < idAnggotaComboBox.getItemCount(); i++) {
                if (idAnggotaComboBox.getItemAt(i).getId() == simpanan.getIdAnggota()) {
                    idAnggotaComboBox.setSelectedIndex(i);
                    break;
                }
            }
            jumlahField.setText(String.valueOf(simpanan.getJumlah()));
            datePickerPanel.setSelectedDate(simpanan.getTanggalSimpan().toLocalDate());
            idAnggotaComboBox.setEnabled(false); // Disable Anggota dropdown for update
        }

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idAnggota = ((Anggota) idAnggotaComboBox.getSelectedItem()).getId();
                    Double jumlah = Double.parseDouble(jumlahField.getText());
                    LocalDate tanggalSimpan = datePickerPanel.getSelectedDate();

                    if (simpananId == null) {
                        simpananController.createSimpanan(idAnggota, BigDecimal.valueOf(jumlah), Date.valueOf(tanggalSimpan));
                    } else {
                        simpananController.updateSimpanan(simpananId, idAnggota, BigDecimal.valueOf(jumlah), Date.valueOf(tanggalSimpan));
                    }
                    refreshTable();
                    formDialog.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(SimpananView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formDialog, "Masukan jumlah yang valid", "Peringatan", JOptionPane.WARNING_MESSAGE);
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

    // Method untuk menghapus simpanan yang dipilih
    private void deleteSelectedSimpanan() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int simpananId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus simpanan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                simpananController.deleteSimpanan(simpananId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih simpanan yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SimpananView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(SimpananView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
