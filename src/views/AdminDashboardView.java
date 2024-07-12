package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDashboardView extends JFrame {

    public AdminDashboardView() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Create buttons for each menu item
        JButton anggotaButton = new JButton("Anggota");
        JButton simpananButton = new JButton("Simpanan");
        JButton pinjamanButton = new JButton("Pinjaman");
        JButton logoutButton = new JButton("Logout");

        // Customize buttons
        anggotaButton.setBackground(Color.LIGHT_GRAY);
        simpananButton.setBackground(Color.LIGHT_GRAY);
        pinjamanButton.setBackground(Color.LIGHT_GRAY);
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);

        // Add buttons to panel
        buttonPanel.add(anggotaButton);
        buttonPanel.add(simpananButton);
        buttonPanel.add(pinjamanButton);

        // Add panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);

        // Action listeners for each button
        anggotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showAnggotaScreen();
                    dispose(); // Close current window

                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        simpananButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showSimpananScreen();
                    dispose(); // Close current window

                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        pinjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showPinjamanScreen();
                    dispose(); // Close current window

                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(AdminDashboardView.this, "Apakah Anda yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Perform logout actions here (e.g., close current window, show login screen)
                    dispose(); // Close current window
                    showLoginScreen(); // Show login screen
                }
            }
        });

        // Center the window
        setLocationRelativeTo(null);
    }

    private void showLoginScreen() {
        AdminLoginView loginView = new AdminLoginView();
        loginView.setVisible(true);
    }

    private void showAnggotaScreen() throws SQLException {
        AnggotaView anggotaView = new AnggotaView();
        anggotaView.setVisible(true);
    }

    private void showSimpananScreen() throws SQLException {
        SimpananView simpananView = new SimpananView();
        simpananView.setVisible(true);
    }

    private void showPinjamanScreen() throws SQLException {
        PinjamanView pinjamanView = new PinjamanView();
        pinjamanView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AdminDashboardView dashboard = new AdminDashboardView();
                dashboard.setVisible(true);
            }
        });
    }
}
