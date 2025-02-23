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
import controllers.AdminController;
import models.Admin;

public class UserView extends JFrame {

    private AdminController userController;
    private JTable table;
    private DefaultTableModel model;

    public UserView() throws SQLException {
        userController = new AdminController();

        setTitle("User Management");
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
                    showUserForm(null);
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int userId = (int) model.getValueAt(selectedRow, 0);
                        showUserForm(userId);
                    } else {
                        JOptionPane.showMessageDialog(UserView.this, "Pilih pengguna yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
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
        List<Admin> users = userController.getAllUsers();
        model.setRowCount(0); // Clear table
        for (Admin user : users) {
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail()});
        }
    }

    // Method untuk menambahkan atau mengupdate pengguna
    private void showUserForm(Integer userId) throws SQLException {
        JDialog formDialog = new JDialog(this, userId == null ? "Tambah Pengguna" : "Update Pengguna", true);
        formDialog.setSize(400, 300);
        formDialog.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton(userId == null ? "Tambah" : "Update");
        saveButton.setBackground(Color.GREEN);
        JButton cancelButton = new JButton("Batal");
        cancelButton.setBackground(Color.RED);

        formDialog.add(nameLabel);
        formDialog.add(nameField);
        formDialog.add(emailLabel);
        formDialog.add(emailField);
        formDialog.add(passwordLabel);
        formDialog.add(passwordField);
        formDialog.add(saveButton);
        formDialog.add(cancelButton);

        if (userId != null) {
            Admin user = userController.getUserById(userId);
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nama = nameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());

                    if (nama != null && email != null && !nama.isEmpty() && !email.isEmpty()) {
                        if (userId == null) {
                            userController.createUser(nama, email, password);
                        } else {
                            userController.updateUser(userId, nama, email, password);
                        }
                        refreshTable();
                        formDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(formDialog, "Masukan semua data dengan benar", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
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
