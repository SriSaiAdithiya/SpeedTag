package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountCollected extends JFrame {

    private int contractorId;
    private JTable table;
    private DefaultTableModel tableModel;

    public AmountCollected(int contractorId) {
        this.contractorId = contractorId;
        initializeUI();
        loadAmountCollectedData();
    }

    private void initializeUI() {
        setTitle("Amount Collected");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel with back button and DPPS label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(14, 132, 248));
        topPanel.setPreferredSize(new Dimension(800, 50));

        JButton backButton = new JButton();
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
                dispose();
                new Contractor_page(contractorId).setVisible(true);
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

        // Center panel with table and "AMOUNT COLLECTED" label
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new LineBorder(Color.BLACK, 2));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel amountCollectedLabel = new JLabel("AMOUNT COLLECTED");
        amountCollectedLabel.setFont(new Font("Arial", Font.BOLD, 18));
        amountCollectedLabel.setForeground(Color.BLACK);
        amountCollectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(amountCollectedLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Toll No", "Toll Name", "Route ID", "Vehicle No", "Timestamp", "Amount Collected"});

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void loadAmountCollectedData() {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT Toll_No, toll_Name, RID, Veh_No, AmtTimestamp, Fair_collected FROM Toll_AmountCollected " +
                    "WHERE Toll_No IN (SELECT Toll_No FROM toll_contractor WHERE Con_id = ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, contractorId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("Toll_No");
                row[1] = rs.getString("toll_Name");
                row[2] = rs.getInt("RID");
                row[3] = rs.getString("Veh_No");
                row[4] = rs.getTimestamp("AmtTimestamp");
                row[5] = rs.getInt("Fair_collected");
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AmountCollected(121).setVisible(true); // Example contractor ID
    }
}