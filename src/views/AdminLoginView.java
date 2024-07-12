package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLoginView extends JFrame implements ActionListener {

    private JLabel titleLabel, emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLoginView() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Create a panel with padding
        JPanel paddedPanel = new JPanel();
        paddedPanel.setLayout(new GridLayout(6, 1, 10, 10));
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding: top, left, bottom, right

        // Title
        titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        paddedPanel.add(titleLabel);

        // Email field
        emailLabel = new JLabel("Email or phone number");
        paddedPanel.add(emailLabel);
        emailField = new JTextField();
        paddedPanel.add(emailField);

        // Password field
        passwordLabel = new JLabel("Password");
        paddedPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        paddedPanel.add(passwordField);

        // Login button
        loginButton = new JButton("Sign in");
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        paddedPanel.add(loginButton);

        // Add padded panel to the frame
        add(paddedPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login logic here
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Simple login validation (replace with actual validation logic)
            if (email.equals("didi") && password.equals("123")) {
                // Proceed to the next view, for example, AdminDashboardView
                 AdminDashboardView dashboard = new AdminDashboardView();
                 dashboard.setVisible(true);
                 dispose(); // Close login window after successful login
                // Placeholder for successful login action
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show error message on failed login
                JOptionPane.showMessageDialog(this, "Email or password incorrect", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new AdminLoginView();
    }
}
