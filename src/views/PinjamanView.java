package views;

import components.DatePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import controllers.PinjamanController;
import controllers.AnggotaController;
import models.Anggota;
import models.Pinjaman;
import java.math.BigDecimal;
import java.sql.Date;
import javax.swing.table.DefaultTableCellRenderer;

public class PinjamanView extends JFrame {

    private PinjamanController pinjamanController;
    private AnggotaController anggotaController;
    private JTable table;
    private DefaultTableModel model;

    public PinjamanView() throws SQLException {
        pinjamanController = new PinjamanController();
        anggotaController = new AnggotaController();

        setTitle("Pinjaman Management");
        setSize(800, 600); // Perbesar ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color to white for the content pane
        getContentPane().setBackground(Color.WHITE);

        // Panel untuk tombol tambah, update, dan hapus
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Set alignment to left
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
                    showPinjamanForm(null);
                } catch (SQLException ex) {
                    Logger.getLogger(PinjamanView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int pinjamanId = (int) model.getValueAt(selectedRow, 0);
                        showPinjamanForm(pinjamanId);
                    } else {
                        JOptionPane.showMessageDialog(PinjamanView.this, "Pilih pinjaman yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PinjamanView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedPinjaman();
                } catch (SQLException ex) {
                    Logger.getLogger(PinjamanView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        // Panel untuk menampilkan data pinjaman dengan padding
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Set padding/margin

        // Table untuk menampilkan data pinjaman
        model = new DefaultTableModel();
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (row % 2 == 0) {
                    component.setBackground(new Color(240, 240, 240)); // Baris genap berwarna abu-abu muda
                } else {
                    component.setBackground(Color.WHITE); // Baris ganjil berwarna putih
                }
                return component;
            }
        };
        
        // Set header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(100, 149, 237)); // Cornflower blue
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Set table cell alignment and padding
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
                    label.setHorizontalAlignment(SwingConstants.CENTER); // Center align
                    return label;
                }
            });
        }

        model.addColumn("ID");
        model.addColumn("Nama Anggota");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal Pinjam");
        model.addColumn("Tanggal Jatuh Tempo");
        model.addColumn("Status");

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Tampilkan data pinjaman saat aplikasi dimulai
        refreshTable();
    }

    // Method untuk menampilkan data pinjaman dalam tabel
    private void refreshTable() throws SQLException {
        List<Pinjaman> pinjamanList = pinjamanController.getAllPinjaman();
        model.setRowCount(0); // Clear table
        for (Pinjaman pinjaman : pinjamanList) {
            String namaAnggota = getNamaAnggotaById(pinjaman.getIdAnggota());
            model.addRow(new Object[]{pinjaman.getId(), namaAnggota, pinjaman.getJumlah(), pinjaman.getTanggalPinjam(), pinjaman.getTanggalJatuhTempo(), pinjaman.getStatus()});
        }
    }

    // Method untuk mendapatkan nama anggota dari ID anggota
    private String getNamaAnggotaById(int idAnggota) throws SQLException {
        Anggota anggota = anggotaController.getAnggotaById(idAnggota);
        return anggota != null ? anggota.getNama() : "Unknown";
    }

    // Method untuk menambahkan atau mengupdate pinjaman
    private void showPinjamanForm(Integer pinjamanId) throws SQLException {
        JDialog formDialog = new JDialog(this, pinjamanId == null ? "Tambah Pinjaman" : "Update Pinjaman", true);
        formDialog.setSize(400, 300);
        formDialog.setLayout(new GridLayout(6, 2));

        JLabel idAnggotaLabel = new JLabel("ID Anggota:");
        JComboBox<Anggota> idAnggotaComboBox = new JComboBox<>();
        List<Anggota> anggotaList = anggotaController.getAllAnggota();
        for (Anggota anggota : anggotaList) {
            idAnggotaComboBox.addItem(anggota);
        }

        JLabel jumlahLabel = new JLabel("Jumlah:");
        JTextField jumlahField = new JTextField();
        JLabel tanggalPinjamLabel = new JLabel("Tanggal Pinjam:");
        DatePicker tanggalPinjamPicker = new DatePicker();
        JLabel tanggalJatuhTempoLabel = new JLabel("Tanggal Jatuh Tempo:");
        DatePicker tanggalJatuhTempoPicker = new DatePicker();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"belum_lunas", "lunas"});

        JButton saveButton = new JButton(pinjamanId == null ? "Tambah" : "Update");
        saveButton.setBackground(Color.GREEN);
        JButton cancelButton = new JButton("Batal");
        cancelButton.setBackground(Color.RED);

        formDialog.add(idAnggotaLabel);
        formDialog.add(idAnggotaComboBox);
        formDialog.add(jumlahLabel);
        formDialog.add(jumlahField);
        formDialog.add(tanggalPinjamLabel);
        formDialog.add(tanggalPinjamPicker);
        formDialog.add(tanggalJatuhTempoLabel);
        formDialog.add(tanggalJatuhTempoPicker);
        formDialog.add(statusLabel);
        formDialog.add(statusComboBox);
        formDialog.add(saveButton);
        formDialog.add(cancelButton);

        if (pinjamanId != null) {
            Pinjaman pinjaman = pinjamanController.getPinjamanById(pinjamanId);
            for (int i = 0; i < idAnggotaComboBox.getItemCount(); i++) {
                if (idAnggotaComboBox.getItemAt(i).getId() == pinjaman.getIdAnggota()) {
                    idAnggotaComboBox.setSelectedIndex(i);
                    break;
                }
            }
            jumlahField.setText(String.valueOf(pinjaman.getJumlah()));
            tanggalPinjamPicker.setSelectedDate(pinjaman.getTanggalPinjam().toLocalDate());
            tanggalJatuhTempoPicker.setSelectedDate(pinjaman.getTanggalJatuhTempo().toLocalDate());
            statusComboBox.setSelectedItem(pinjaman.getStatus());
            idAnggotaComboBox.setEnabled(false); // Disable Anggota dropdown for update
        }

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idAnggota = ((Anggota) idAnggotaComboBox.getSelectedItem()).getId();
                    Double jumlah = Double.parseDouble(jumlahField.getText());
                    LocalDate tanggalPinjam = tanggalPinjamPicker.getSelectedDate();
                    LocalDate tanggalJatuhTempo = tanggalJatuhTempoPicker.getSelectedDate();
                    String status = (String) statusComboBox.getSelectedItem();

                    if (pinjamanId == null) {
                        pinjamanController.createPinjaman(idAnggota, BigDecimal.valueOf(jumlah), Date.valueOf(tanggalPinjam), Date.valueOf(tanggalJatuhTempo), status);
                    } else {
                        pinjamanController.updatePinjaman(pinjamanId, idAnggota, BigDecimal.valueOf(jumlah), Date.valueOf(tanggalPinjam), Date.valueOf(tanggalJatuhTempo), status);
                    }
                    refreshTable();
                    formDialog.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(PinjamanView.class.getName()).log(Level.SEVERE, null, ex);
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

    // Method untuk menghapus pinjaman yang dipilih
    private void deleteSelectedPinjaman() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int pinjamanId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus pinjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pinjamanController.deletePinjaman(pinjamanId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pinjaman yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PinjamanView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PinjamanView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
