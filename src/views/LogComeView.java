package views;

import controllers.LogComeController;
import models.LogCome;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogComeView extends JFrame {

    private LogComeController logComeController;
    private JTable table;
    private DefaultTableModel model;

    public LogComeView() throws SQLException {
        logComeController = new LogComeController();

        setTitle("LogCome Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Panel untuk tombol tambah, update, dan hapus
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE); // Set background color to white
        JButton addButton = createStyledButton("Tambah");
        JButton updateButton = createStyledButton("Update");
        JButton deleteButton = createStyledButton("Hapus");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Tambah event listener untuk tombol tambah, update, dan hapus
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tambahLogCome();
                } catch (SQLException ex) {
                    Logger.getLogger(LogComeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSelectedLogCome();
                } catch (SQLException ex) {
                    Logger.getLogger(LogComeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedLogCome();
                } catch (SQLException ex) {
                    Logger.getLogger(LogComeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        // Table untuk menampilkan data log come
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Amount");
        model.addColumn("Username");
        model.addColumn("Description");
        model.addColumn("Type");
        model.addColumn("Date");

        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12)); // Set header font
        table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12)); // Set table content font
        table.setRowHeight(25); // Set row height
        table.setGridColor(Color.LIGHT_GRAY); // Set grid color

        // Set table header background and foreground color
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.DARK_GRAY);
        headerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Set alternate row color
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tampilkan data log come saat aplikasi dimulai
        try {
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching LogCome data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk membuat tombol dengan gaya yang lebih baik
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        button.setBackground(Color.decode("#4CAF50")); // Green background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // No focus border
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20)); // Padding
        return button;
    }

    // Method untuk menampilkan data log come dalam tabel
    private void refreshTable() throws SQLException {
        List<LogCome> logComes = logComeController.getAllLogComes();
        model.setRowCount(0); // Clear table
        for (LogCome logCome : logComes) {
            model.addRow(new Object[]{logCome.getId(), logCome.getAmount(), logCome.getUsername(), logCome.getDescription(), logCome.getType(), logCome.getDate()});
        }
    }

    // Method untuk menambahkan log come baru
    private void tambahLogCome() throws SQLException {
        JTextField amountField = new JTextField(10);
        JTextField usernameField = new JTextField(20);
        JTextField descriptionField = new JTextField(30);
        JTextField typeField = new JTextField(20);
        JTextField dateField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(5, 2));
        myPanel.add(new JLabel("Amount:"));
        myPanel.add(amountField);
        myPanel.add(new JLabel("Username:"));
        myPanel.add(usernameField);
        myPanel.add(new JLabel("Description:"));
        myPanel.add(descriptionField);
        myPanel.add(new JLabel("Type:"));
        myPanel.add(typeField);
        myPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        myPanel.add(dateField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter LogCome Information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int amount = Integer.parseInt(amountField.getText());
                String username = usernameField.getText();
                String description = descriptionField.getText();
                String type = typeField.getText();
                String date = dateField.getText();

                // Validate date format
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                sdf.parse(date); // This will throw ParseException if not valid

                logComeController.createLogCome(amount, username, description, type, date);
                refreshTable();
            } catch (NumberFormatException | ParseException e) {
                JOptionPane.showMessageDialog(this, "Masukkan data yang benar, coba lagi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method untuk mengupdate log come yang dipilih
    private void updateSelectedLogCome() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int logComeId = (int) model.getValueAt(selectedRow, 0);

            JTextField amountField = new JTextField(10);
            JTextField usernameField = new JTextField(20);
            JTextField descriptionField = new JTextField(30);
            JTextField typeField = new JTextField(20);
            JTextField dateField = new JTextField(10);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(5, 2));
            myPanel.add(new JLabel("Amount:"));
            myPanel.add(amountField);
            myPanel.add(new JLabel("Username:"));
            myPanel.add(usernameField);
            myPanel.add(new JLabel("Description:"));
            myPanel.add(descriptionField);
            myPanel.add(new JLabel("Type:"));
            myPanel.add(typeField);
            myPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            myPanel.add(dateField);

            // Populate fields with current values
            amountField.setText(String.valueOf(model.getValueAt(selectedRow, 1)));
            usernameField.setText((String) model.getValueAt(selectedRow, 2));
            descriptionField.setText((String) model.getValueAt(selectedRow, 3));
            typeField.setText((String) model.getValueAt(selectedRow, 4));
            dateField.setText((String) model.getValueAt(selectedRow, 5));

            int result = JOptionPane.showConfirmDialog(null, myPanel, "Update LogCome Information", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int amount = Integer.parseInt(amountField.getText());
                    String username = usernameField.getText();
                    String description = descriptionField.getText();
                    String type = typeField.getText();
                    String date = dateField.getText();

                    // Validate date format
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    sdf.parse(date); // This will throw ParseException if not valid

                    logComeController.updateLogCome(logComeId, amount, username, description, type, date);
                    refreshTable();
                } catch (NumberFormatException | ParseException e) {
                    JOptionPane.showMessageDialog(this, "Masukkan data yang benar, coba lagi!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih log come yang ingin diupdate.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method untuk menghapus log come yang dipilih
    private void deleteSelectedLogCome() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int logComeId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus log come ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                logComeController.deleteLogCome(logComeId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih log come yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LogComeView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(LogComeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
