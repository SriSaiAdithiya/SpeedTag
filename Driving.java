package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Driving {
    private static String selectedVehicle, selectedSource, selectedDestination, Veh_Type;
    private static int RID, cid, tagId;
    private static List<Integer> myTolls;
    private static Map<Integer, String> tollNamesMap;
    private static Map<Integer, Integer> fairAmountsMap;
    private static int walletBalance;
    private static String name;
    private boolean isPaused = false;

    JFrame newframe;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel tollimage;
    JLabel dpps;
    JLabel sourceLabel;
    JLabel destinationLabel;
    JLabel hyphenLabel;
    JLabel namelabel;
    JLabel tollNameLabel;
    JLabel walletBalanceLabel;
    JButton pauseResumeButton;
    Timer openTimer;
    Timer closeTimer;

    Driving(String selectedVehicle, String selectedSource, String selectedDestination, int RID, String Veh_Type, List<Integer> myTolls, int cid) {
        this.selectedVehicle = selectedVehicle;
        this.selectedSource = selectedSource;
        this.selectedDestination = selectedDestination;
        this.RID = RID;
        this.cid = cid;
        this.Veh_Type = Veh_Type;
        this.myTolls = myTolls;

        try {
            tollNamesMap = fetchTollNames(myTolls);
            fairAmountsMap = fetchFairAmounts(myTolls, Veh_Type);
            tagId = fetchTagId(selectedVehicle);
            walletBalance = fetchWalletBalance(tagId);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT Cust_Name FROM Customer WHERE CID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, cid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        newframe = new JFrame("Simulation Page");
        newframe.setSize(1300, 650);
        newframe.setLocationRelativeTo(null);
        newframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1300, 50);
        panel1.setBackground(new Color(11, 135, 241));
        dpps = new JLabel("DPPS");
        dpps.setBounds(250, 130, 130, 25);
        dpps.setFont(new Font("HP Simplified", Font.BOLD, 40));
        dpps.setForeground(Color.WHITE);

        panel1.add(dpps);
        panel3 = new JPanel();
        panel3.setBounds(0, 600, 1300, 50);
        panel3.setBackground(new Color(11, 135, 241));
        panel2 = new JPanel();
        panel2.setBounds(100, 120, 1100, 410);
        panel2.setBackground(new Color(255, 255, 255, 184));
        panel2.setLayout(null);
        panel3.setLayout(null);
        newframe.add(panel1);
        newframe.add(panel2);
        newframe.add(panel3);

        sourceLabel = new JLabel(selectedSource);
        sourceLabel.setBounds(300, 60, 280, 80);
        sourceLabel.setFont(new Font("HP Simplified", Font.BOLD, 20));

        hyphenLabel = new JLabel("-");
        hyphenLabel.setBounds(660, 60, 20, 80);
        hyphenLabel.setFont(new Font("HP Simplified", Font.BOLD, 20));

        destinationLabel = new JLabel(selectedDestination);
        destinationLabel.setBounds(1000, 60, 280, 80);
        destinationLabel.setFont(new Font("HP Simplified", Font.BOLD, 20));

        tollNameLabel = new JLabel("");
        tollNameLabel.setBounds(400, 350, 300, 30);
        tollNameLabel.setFont(new Font("HP Simplified", Font.BOLD, 20));
        tollNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tollimage = new JLabel();
        tollimage.setBounds(400, 50, 300, 300);
        ImageIcon closedTollIcon = new ImageIcon("D:\\OS NOTES\\javaprojects\\closeimg.jpg");
        Image scaledImage = closedTollIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        tollimage.setIcon(new ImageIcon(scaledImage));

        walletBalanceLabel = new JLabel("Wallet Balance: " + walletBalance);
        walletBalanceLabel.setBounds(10, 40, 250, 40);
        walletBalanceLabel.setFont(new Font("HP Simplified", Font.BOLD, 20));
        walletBalanceLabel.setForeground(Color.WHITE);
        panel3.add(walletBalanceLabel);

        pauseResumeButton = new JButton("Pause");
        pauseResumeButton.setBounds(1150, 10, 100, 30);
        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePauseResume();
            }
        });
        panel3.add(pauseResumeButton);

        panel2.add(tollNameLabel);
        panel2.add(tollimage);
        namelabel = new JLabel();
        namelabel.setFont(new Font("HP Simplified", Font.BOLD, 28));
        namelabel.setBounds(10, 5, 250, 40);
        namelabel.setText(name);
        namelabel.setForeground(Color.WHITE);
        panel3.add(namelabel);
        newframe.setLayout(null);
        newframe.add(sourceLabel);
        newframe.add(hyphenLabel);
        newframe.add(destinationLabel);
        newframe.setVisible(true);

        startTollSequence(tollimage, myTolls, 0);
    }

    private void startTollSequence(JLabel tollimage, List<Integer> tolls, int index) {
        if (index >= myTolls.size()) {
            JOptionPane.showMessageDialog(null, "Successfully reached " + selectedDestination);
            newframe.setVisible(false);
            return;
        }

        int tollNo = myTolls.get(index);
        String tollName = tollNamesMap.get(tollNo);
        int fairAmount = fairAmountsMap.get(tollNo);

        if (walletBalance < fairAmount) {
            JOptionPane.showMessageDialog(newframe, "Insufficient balance for toll: " + tollName, "Insufficient Balance", JOptionPane.ERROR_MESSAGE);
            openRechargePage(index);
            return;
        }

        walletBalance -= fairAmount;
        walletBalanceLabel.setText("Wallet Balance: " + walletBalance);
        try {
            updateWalletBalance(tagId, walletBalance);
            processTransaction(tollNo, tollName, fairAmount);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(newframe, "Error updating wallet balance in database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tollNameLabel.setText(tollName);

        Random random = new Random();
        int delay = 1000 + random.nextInt(13000);

        openTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon openTollIcon = new ImageIcon("D:\\OS NOTES\\javaprojects\\openimg.jpg");
                Image openTollScaledImage = openTollIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                tollimage.setIcon(new ImageIcon(openTollScaledImage));
                tollNameLabel.setForeground(Color.GREEN);

                JOptionPane.showMessageDialog(null, tollName + ": passed\nAmount: " + fairAmount + " Deducted\n"+ "Wallet Balance :"+walletBalance);

                closeTimer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tollNameLabel.setForeground(Color.BLACK);
                        ImageIcon closedTollIcon = new ImageIcon("D:\\OS NOTES\\javaprojects\\closeimg.jpg");
                        Image closedTollScaledImage = closedTollIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                        tollimage.setIcon(new ImageIcon(closedTollScaledImage));

                        startTollSequence(tollimage, tolls, index + 1);
                    }
                });
                closeTimer.setRepeats(false);
                closeTimer.start();
            }
        });
        openTimer.setRepeats(false);
        openTimer.start();
    }

    private void openRechargePage(int index) {

        Recharge_Design rechargePage = new Recharge_Design(cid);
        rechargePage.setVisible(true);


        rechargePage.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                try {
                    walletBalance = fetchWalletBalance(tagId);
                    walletBalanceLabel.setText("Wallet Balance: " + walletBalance);
                    if (walletBalance >= fairAmountsMap.get(myTolls.get(index))) {
                        startTollSequence(tollimage, myTolls, index);
                    } else {
                        JOptionPane.showMessageDialog(newframe, "Recharge failed or insufficient balance. Please recharge again.", "Insufficient Balance", JOptionPane.ERROR_MESSAGE);
                        openRechargePage(index);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(newframe, "Error fetching wallet balance after recharge.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void togglePauseResume() {
        if (isPaused) {
            pauseResumeButton.setText("Pause");
            isPaused = false;
            if (openTimer != null) {
                openTimer.start();
            }
            if (closeTimer != null) {
                closeTimer.start();
            }
        } else {
            pauseResumeButton.setText("Resume");
            isPaused = true;
            if (openTimer != null) {
                openTimer.stop();
            }
            if (closeTimer != null) {
                closeTimer.stop();
            }
        }
    }

    private Map<Integer, String> fetchTollNames(List<Integer> tollNumbers) throws SQLException {
        Map<Integer, String> tollNamesMap = new HashMap<>();
        String query = "SELECT toll_no, toll_Name FROM toll WHERE toll_no IN (" +
                tollNumbers.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                tollNamesMap.put(rs.getInt("toll_no"), rs.getString("toll_Name"));
            }
        }
        return tollNamesMap;
    }

    private Map<Integer, Integer> fetchFairAmounts(List<Integer> tollNumbers, String vehType) throws SQLException {
        Map<Integer, Integer> fairAmountsMap = new HashMap<>();
        String query = "SELECT Toll_No, Fair_Amt FROM Toll_Fair WHERE Toll_No IN (" +
                tollNumbers.stream().map(String::valueOf).collect(Collectors.joining(",")) + ") AND Veh_Type = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehType);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    fairAmountsMap.put(rs.getInt("Toll_No"), rs.getInt("Fair_Amt"));
                }
            }
        }
        return fairAmountsMap;
    }

    private int fetchTagId(String vehicleNumber) throws SQLException {
        int tagId = 0;
        String query = "SELECT Tag_Id FROM Vehicle WHERE Veh_No = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehicleNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tagId = rs.getInt("Tag_Id");
                } else {
                    System.err.println("No tag found for vehicle: " + vehicleNumber);
                }
            }
        }
        return tagId;
    }

    private int fetchWalletBalance(int tagId) throws SQLException {
        int Wallet_Bal = 0;
        String query = "SELECT Wallet_Bal FROM Tag WHERE Tag_Id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tagId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Wallet_Bal = rs.getInt("Wallet_Bal");
                } else {
                    System.err.println("No wallet balance found for tag: " + tagId);
                }
            }
        }
        return Wallet_Bal;
    }

    private void updateWalletBalance(int tagId, int newBalance) throws SQLException {
        String query = "UPDATE Tag SET Wallet_Bal = ? WHERE Tag_Id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newBalance);
            pstmt.setInt(2, tagId);
            pstmt.executeUpdate();
        }
    }

    private void processTransaction(int tollNo, String tollName, int fairAmount) throws SQLException {
        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false);


            String contractorQuery = "SELECT c.Acc_No FROM Contractor c JOIN TOLL_CONTRACTOR tc ON c.Con_id = tc.Con_id WHERE tc.Toll_No = ?";
            String contractorAccNo = null;
            try (PreparedStatement pstmt = conn.prepareStatement(contractorQuery)) {
                pstmt.setInt(1, tollNo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        contractorAccNo = rs.getString("Acc_No");
                    }
                }
            }

            if (contractorAccNo == null) {
                throw new SQLException("Contractor account not found for toll number: " + tollNo);
            }


            String customerQuery = "SELECT b.Acc_No, b.Closing_Bal FROM Bank b JOIN Customer c ON b.Acc_No = c.Acc_No WHERE c.CID = ?";
            String customerAccNo = null;
            double customerBalance = 0;
            try (PreparedStatement pstmt = conn.prepareStatement(customerQuery)) {
                pstmt.setInt(1, cid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customerAccNo = rs.getString("Acc_No");
                        customerBalance = rs.getDouble("Closing_Bal");
                    }
                }
            }

            if (customerAccNo == null) {
                throw new SQLException("Customer account not found for CID: " + cid);
            }

            if (customerBalance < fairAmount) {
                throw new SQLException("Insufficient balance in customer's account.");
            }


            String deductQuery = "UPDATE Bank SET Closing_Bal = Closing_Bal - ? WHERE Acc_No = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deductQuery)) {
                pstmt.setDouble(1, fairAmount);
                pstmt.setString(2, customerAccNo);
                pstmt.executeUpdate();
            }


            String creditQuery = "UPDATE Bank SET Closing_Bal = Closing_Bal + ? WHERE Acc_No = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(creditQuery)) {
                pstmt.setDouble(1, fairAmount);
                pstmt.setString(2, contractorAccNo);
                pstmt.executeUpdate();
            }


            String insertQuery = "INSERT INTO Toll_AmountCollected (Toll_No, toll_Name, RID, Veh_No, Fair_collected) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, tollNo);
                pstmt.setString(2, tollName);
                pstmt.setInt(3, RID);
                pstmt.setString(4, selectedVehicle);
                pstmt.setInt(5, fairAmount);
                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Transaction failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Driving(selectedVehicle, selectedSource, selectedDestination, RID, Veh_Type, myTolls, cid);
    }
}
