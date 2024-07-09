package views;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import controllers.UserController;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class UserView extends JFrame {

    private UserController userController;
    private JTable table;
    private DefaultTableModel model;

    public UserView() throws SQLException {
        userController = new UserController();

        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel untuk tombol tambah, update, dan hapus
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Hapus");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Tambah event listener untuk tombol tambah, update, dan hapus
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tambahUser();
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSelectedUser();
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedUser();
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        // Table untuk menampilkan data pengguna
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Email");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tampilkan data pengguna saat aplikasi dimulai
        refreshTable();
    }

    // Method untuk menampilkan data pengguna dalam tabel
    private void refreshTable() throws SQLException {
        List<User> users = userController.getAllUsers();
        model.setRowCount(0); // Clear table
        for (User user : users) {
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail()});
        }
    }

    // Method untuk menambahkan pengguna baru
    private void tambahUser() throws SQLException {
        String nama = JOptionPane.showInputDialog(this, "Masukkan nama pengguna:");
        String email = JOptionPane.showInputDialog(this, "Masukkan email pengguna:");
        if (nama != null && email != null && !nama.isEmpty() && !email.isEmpty()) {
            userController.createUser(nama, email);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Masukan Semua data yang benar", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method untuk mengupdate pengguna yang dipilih
    private void updateSelectedUser() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) model.getValueAt(selectedRow, 0);
            String newName = JOptionPane.showInputDialog(this, "Masukkan nama baru:");
            String newEmail = JOptionPane.showInputDialog(this, "Masukkan email baru:");
            if (newName != null && newEmail != null && !newName.isEmpty() && !newEmail.isEmpty()) {
                userController.updateUser(userId, newName, newEmail);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Masukan Semua data yang benar", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pengguna yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method untuk menghapus pengguna yang dipilih
    private void deleteSelectedUser() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus pengguna ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userController.deleteUser(userId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pengguna yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UserView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
