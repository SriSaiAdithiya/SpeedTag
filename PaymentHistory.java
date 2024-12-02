package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistory {
    Connection con;

    {
        try {
            con = DbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel dpps;
    JLabel namelabel;
    private static String name;
    private static int cid;
    private static List<String> vehNos;
    private JTable table;

    PaymentHistory(int cid) {
        this.cid = cid;
        this.vehNos = getVehNos(cid);
        frame = new JFrame("Payment-History");
        frame.setSize(1300, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1300, 50);
        panel1.setBackground(new Color(11, 135, 241));

        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT Cust_Name FROM Customer WHERE cid = ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, cid);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Error fetching customer name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dpps = new JLabel("DPPS");
        dpps.setFont(new Font("HP Simplified", Font.BOLD, 40));
        dpps.setForeground(Color.WHITE);
        panel1.add(dpps);

        panel3 = new JPanel();
        panel3.setBounds(0, 600, 1300, 50);
        panel3.setBackground(new Color(11, 135, 241));

        panel2 = new JPanel();
        panel2.setBounds(50, 100, 1200, 450);
        panel2.setBackground(new Color(255, 255, 255, 184));
        panel2.setLayout(new BorderLayout());


        String[] columnNames = getColumnNames();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel2.add(scrollPane, BorderLayout.CENTER);


        fetchAndPopulateData(model);

        namelabel = new JLabel(name);
        namelabel.setFont(new Font("HP Simplified", Font.BOLD, 28));
        namelabel.setForeground(Color.WHITE);
        panel3.add(namelabel);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    private List<String> getVehNos(int cid) {
        List<String> vehNos = new ArrayList<>();
        String query = "SELECT Veh_No FROM Vehicle WHERE cid = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, cid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                vehNos.add(rs.getString("Veh_No"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehNos;
    }


    private String[] getColumnNames() {
        String[] columnNames = {};
        String query = "SELECT Veh_No, Toll_No, toll_Name, AmtTimestamp, Fair_Collected FROM Toll_AmountCollected LIMIT 1;";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    private void fetchAndPopulateData(DefaultTableModel model) {
        String query = "SELECT Veh_No, Toll_No, toll_Name, AmtTimestamp, Fair_Collected FROM Toll_AmountCollected WHERE Veh_No = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            for (String vehNo : vehNos) {
                pst.setString(1, vehNo);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String vehicleNum = rs.getString("Veh_No");
                    int tollNo = rs.getInt("Toll_No");
                    String tollName = rs.getString("toll_Name");
                    String timestamp = rs.getString("AmtTimestamp");
                    int fairAmount = rs.getInt("Fair_Collected");
                    model.addRow(new Object[]{vehicleNum, tollNo, tollName, timestamp, fairAmount});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PaymentHistory(cid);
    }
}
