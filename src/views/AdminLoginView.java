package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLoginView extends JFrame implements ActionListener {

    private JLabel titleLabel, emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgotPasswordLabel;

    public AdminLoginView() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 1, 10, 10));
        setLocationRelativeTo(null);

        // Title
        titleLabel = new JLabel("Nice to see you again", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        // Email field
        emailLabel = new JLabel("Email or phone number");
        add(emailLabel);
        emailField = new JTextField();
        add(emailField);

        // Password field
        passwordLabel = new JLabel("Password");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        // Remember me checkbox
        rememberMeCheckBox = new JCheckBox("Remember me");
        add(rememberMeCheckBox);

        // Forgot password link
        forgotPasswordLabel = new JLabel("<html><a href='#'>Forgot password?</a></html>");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(forgotPasswordLabel);

        // Login button
        loginButton = new JButton("Sign in");
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login logic here
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Misalnya, validasi login sederhana (diganti dengan yang sesuai dengan kebutuhan Anda)
            if (email.equals("didi") && password.equals("123")) {
                AdminDashboardView dashboard = new AdminDashboardView();
                dashboard.setVisible(true);
                dispose(); // Tutup halaman login setelah berhasil login
            } else {
                // Jika login gagal, tampilkan pesan kesalahan
                JOptionPane.showMessageDialog(this, "Email atau password salah", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new AdminLoginView();
    }
}
