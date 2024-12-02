package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class TollAvailableToBuy extends JFrame {
    private static int contractorId;
    private JTable tollTable;
    private JButton backButton;

    public TollAvailableToBuy(int contractorId, String tollNos) {
        this.contractorId = contractorId;
        initializeUI();
        loadTollData(tollNos);
    }

    private void initializeUI() {
        setTitle("Tolls Available to Buy");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(14, 132, 248)); // Set background color

        // Top panel with DPPS and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(14, 132, 248));
        topPanel.setPreferredSize(new Dimension(800, 50));

        backButton = new JButton();
        try {
            Image img = ImageIO.read(new File("D:\\OS NOTES\\javaprojects\\icons8-back-30.png"));
            backButton.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Contractor_page(contractorId).setVisible(true); // Open Contractor_page with the same con_id
            }
        });

        JLabel dppsLabel = new JLabel("DPPS");
        dppsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dppsLabel.setForeground(Color.BLACK);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(new Color(14, 132, 248));
        backPanel.add(backButton);
        backPanel.add(dppsLabel);

        topPanel.add(backPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(14, 132, 248));
        bottomPanel.setPreferredSize(new Dimension(800, 50));
        add(bottomPanel, BorderLayout.SOUTH);

        // Center panel with table and "TOLLS AVAILABLE TO BUY" label
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new LineBorder(Color.BLACK, 2));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headingLabel = new JLabel("TOLLS AVAILABLE TO BUY");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(headingLabel, BorderLayout.NORTH);

        String[] columnNames = {"Toll No", "Toll Name", "Lease Amount", "Buy"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames);
        tollTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tollTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        tollTable.getColumn("Buy").setCellRenderer(new ButtonRenderer());
        tollTable.getColumn("Buy").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadTollData(String tollNos) {
        try (Connection connection = DbConnection.getConnection()) {
            String query = "SELECT t.Toll_No, t.Toll_Name, l.Toll_Amt " +
                    "FROM TOLL t " +
                    "JOIN TOLL_LEASE l ON t.Toll_No = l.Toll_No " +
                    "WHERE t.Toll_No IN (" + tollNos + ") AND l.Toll_Amt IS NOT NULL";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tollTable.getModel();
            while (resultSet.next()) {
                int tollNo = resultSet.getInt("Toll_No");
                String tollName = resultSet.getString("Toll_Name");
                double leaseAmount = resultSet.getDouble("Toll_Amt");
                model.addRow(new Object[]{tollNo, tollName, leaseAmount, "Buy"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Custom renderer for the Buy button
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom editor for the Buy button
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        private int tollNo;
        private String tollName;
        private double leaseAmount;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            isPushed = true;
            tollNo = (int) table.getValueAt(row, 0);
            tollName = (String) table.getValueAt(row, 1);
            leaseAmount = (double) table.getValueAt(row, 2);
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Show confirmation dialog
                int option = JOptionPane.showConfirmDialog(TollAvailableToBuy.this,
                        "Do you want to buy Toll No: " + tollNo + " (" + tollName + ") for Lease Amount: " + leaseAmount + "?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Perform payment logic
                    performPayment();
                }
            }
            isPushed = false;
            return button.getText();
        }

        private void performPayment() {
            // Check contractor balance
            double contractorBalance = getContractorBalance();
            if (contractorBalance >= leaseAmount) {
                // Update database
                updateDatabase();
                JOptionPane.showMessageDialog(TollAvailableToBuy.this, "Payment successful! Toll bought.");
            } else {
                JOptionPane.showMessageDialog(TollAvailableToBuy.this, "Insufficient balance! Cannot buy toll.");
            }
        }

        private double getContractorBalance() {
            double balance = 0;
            try (Connection connection = DbConnection.getConnection()) {
                String query = "SELECT Closing_Bal FROM Bank WHERE Acc_No = (SELECT Acc_No FROM Contractor WHERE Con_id = ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, contractorId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getDouble("Closing_Bal");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return balance;
        }

        private void updateDatabase() {
            try (Connection connection = DbConnection.getConnection()) {
                // Fetch old contractor's details
                String oldContractorQuery = "SELECT Con_id FROM toll_contractor WHERE Toll_No = ?";
                PreparedStatement oldContractorStatement = connection.prepareStatement(oldContractorQuery);
                oldContractorStatement.setInt(1, tollNo);
                ResultSet oldContractorResultSet = oldContractorStatement.executeQuery();
                int oldContractorId = 0;
                if (oldContractorResultSet.next()) {
                    oldContractorId = oldContractorResultSet.getInt("Con_id");
                }

                // Update toll_contractor table
                String updateQuery = "UPDATE toll_contractor SET Con_id = ?, Available = 'no' WHERE Toll_No = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, contractorId);
                updateStatement.setInt(2, tollNo);
                updateStatement.executeUpdate();

                // Update old contractor's bank balance
                if (oldContractorId != 0) {
                    double oldContractorBalance = getContractorBalance(oldContractorId);
                    double newOldContractorBalance = oldContractorBalance + leaseAmount;
                    String updateOldContractorBalanceQuery = "UPDATE Bank SET Closing_Bal = ? WHERE Acc_No = (SELECT Acc_No FROM Contractor WHERE Con_id = ?)";
                    PreparedStatement updateOldContractorBalanceStatement = connection.prepareStatement(updateOldContractorBalanceQuery);
                    updateOldContractorBalanceStatement.setDouble(1, newOldContractorBalance);
                    updateOldContractorBalanceStatement.setInt(2, oldContractorId);
                    updateOldContractorBalanceStatement.executeUpdate();
                }

                // Deduct amount from contractor's balance
                double newBalance = getContractorBalance(contractorId) - leaseAmount;
                String deductQuery = "UPDATE Bank SET Closing_Bal = ? WHERE Acc_No = (SELECT Acc_No FROM Contractor WHERE Con_id = ?)";
                PreparedStatement deductStatement = connection.prepareStatement(deductQuery);
                deductStatement.setDouble(1, newBalance);
                deductStatement.setInt(2, contractorId);
                deductStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private double getContractorBalance(int contractorId) {
            double balance = 0;
            try (Connection connection = DbConnection.getConnection()) {
                String query = "SELECT Closing_Bal FROM Bank WHERE Acc_No = (SELECT Acc_No FROM Contractor WHERE Con_id = ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, contractorId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getDouble("Closing_Bal");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return balance;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        // Example usage
        int contractorId = 123;
        String tollNos = "1, 2, 3";
        SwingUtilities.invokeLater(() -> new TollAvailableToBuy(contractorId, tollNos).setVisible(true));
    }
}
