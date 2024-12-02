package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class BuyToll extends JFrame {
    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destComboBox;
    private JLabel contractorLabel;
    private JLabel accountBalanceLabel;
    private JButton selectButton;
    private JButton backButton;
    private static int Con_id;

    public BuyToll(int Con_id) {
        this.Con_id = Con_id;
        setTitle("Buy Toll");
        setSize(600, 300); // Reduced frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(14, 132, 248)); // Set background color

        // Top panel for the back button and contractor details
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(14, 132, 248));

        // Back button with arrow icon
        try {
            Image img = ImageIO.read(new File("D:\\OS NOTES\\javaprojects\\icons8-back-30.png"));
            backButton = new JButton(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
            backButton = new JButton("Back"); // Fallback text if image fails to load
        }
        backButton.setPreferredSize(new Dimension(50, 50)); // Set preferred size for the button
        backButton.setContentAreaFilled(false); // Remove button background
        backButton.setBorderPainted(false); // Remove button border
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Contractor_page(Con_id).setVisible(true); // Open the Contractor_page window
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        // Contractor details
        JPanel contractorDetailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contractorDetailsPanel.setBackground(new Color(14, 132, 248));
        contractorLabel = new JLabel();
        accountBalanceLabel = new JLabel();
        fetchContractorDetails(Con_id);
        contractorDetailsPanel.add(contractorLabel);
        contractorDetailsPanel.add(accountBalanceLabel);
        topPanel.add(contractorDetailsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Middle panel for source and destination options
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.setBackground(new Color(255, 255, 255));
        sourceComboBox = new JComboBox<>();
        destComboBox = new JComboBox<>();
        fetchSourceAndDestinationOptions();
        middlePanel.add(new JLabel("Source:"));
        middlePanel.add(sourceComboBox);
        middlePanel.add(new JLabel("Destination:"));
        middlePanel.add(destComboBox);
        add(middlePanel, BorderLayout.CENTER);

        // Bottom panel for the select button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(14, 132, 248));
        selectButton = new JButton("Select");
        selectButton.setPreferredSize(new Dimension(150, 40)); // Reduced button size
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAvailableTolls(Con_id);
            }
        });
        bottomPanel.add(selectButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchContractorDetails(int contractorId) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT c.cname, b.Closing_Bal " +
                     "FROM Contractor c " +
                     "JOIN Bank b ON c.Acc_No = b.Acc_No " +
                     "WHERE c.Con_id = ?")) {
            statement.setInt(1, contractorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String contractorName = resultSet.getString("cname");
                double closingBalance = resultSet.getDouble("Closing_Bal");
                contractorLabel.setText("<html><font color='black'><b>Contractor: </b></font>" + contractorName);
                accountBalanceLabel.setText("<html><font color='black'><b>Closing Balance: </b></font>" + closingBalance);
                contractorLabel.setFont(new Font("Arial", Font.BOLD, 14));
                accountBalanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchSourceAndDestinationOptions() {
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT Src FROM ROUTE");
            while (resultSet.next()) {
                sourceComboBox.addItem(resultSet.getString("Src"));
            }
            resultSet = statement.executeQuery("SELECT DISTINCT Dest FROM ROUTE");
            while (resultSet.next()) {
                destComboBox.addItem(resultSet.getString("Dest"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchAvailableTolls(int contractorId) {
        String source = sourceComboBox.getSelectedItem().toString();
        String destination = destComboBox.getSelectedItem().toString();
        String tollNos = "";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT Toll_No FROM TOLL_CONTRACTOR WHERE RID IN (SELECT RID FROM ROUTE WHERE Src = ? AND Dest = ?) AND available = 'yes'")) {
            statement.setString(1, source);
            statement.setString(2, destination);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder tolls = new StringBuilder();
            while (resultSet.next()) {
                int tollNo = resultSet.getInt("Toll_No");
                tolls.append(tollNo).append(", ");
                tollNos += tollNo + ",";
            }
            if (tolls.length() > 0) {
                tolls.delete(tolls.length() - 2, tolls.length());
                TollAvailableToBuy tollAvailableToBuy = new TollAvailableToBuy(Con_id, tolls.toString());
                tollAvailableToBuy.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No available tolls for lease.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int contractorId = Con_id;
        SwingUtilities.invokeLater(() -> new BuyToll(Con_id));
    }
}
