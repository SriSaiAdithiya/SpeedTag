package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleRoute {
    Connection con;

    {
        try {
            con = DbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    JFrame Frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel vehicle;
    JLabel source;
    JLabel desti;
    JButton back;
    JButton confirm;
    JComboBox vehicombo;
    JComboBox srcombo;
    JComboBox descombo;
    JLabel dpps;
    private static String selectedVehicle;
    private static String selectedSource;
    private static String selectedDestination;
    private static List<Integer> myTolls;
    JLabel namelabel;
    private static int cid;
    private static int RID;
    private static List<Integer> tolls;
    private static String name;
    private static String Veh_Type;

    VehicleRoute(int cid) {

        this.cid = cid;
        tolls = new ArrayList<>();
        Frame = new JFrame("Vehicle-Route-Selection-Page");
        Frame.setSize(1300, 650);
        Frame.setLocationRelativeTo(null);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1300, 50);
        panel1.setBackground(new Color(11, 135, 241));

        try (Connection con = DbConnection.getConnection()) {
            String Query = "Select Cust_Name from Customer where CID = ? ;";

            PreparedStatement pst = con.prepareStatement(Query);
            pst.setInt(1, cid);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                //MainFrame.setVisible(false);
                name = rs.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Error");

            }

        } catch (SQLException E) {
            System.out.println(E.getMessage());
        }

        dpps = new JLabel("DPPS");
        dpps.setBounds(250, 130, 130, 25);
        dpps.setFont(new Font("HP Simplified", Font.BOLD, 40));
        dpps.setForeground(Color.WHITE);

        panel1.add(dpps);
        panel3 = new JPanel();
        panel3.setBounds(0, 600, 1300, 50);
        panel3.setBackground(new Color(11, 135, 241));

        panel2 = new JPanel();
        panel2.setBounds(300, 120, 700, 410);
        panel2.setBackground(new Color(255, 255, 255, 184));
        panel2.setLayout(null);
        panel3.setLayout(null);
        Frame.add(panel1);
        Frame.add(panel2);
        Frame.add(panel3);

        vehicle = new JLabel("VEHICLE ");
        vehicle.setBounds(250, 40, 130, 25);
        vehicle.setFont(new Font("HP Simplified", Font.BOLD, 18));
        vehicle.setForeground(Color.BLACK);

        vehicombo = new JComboBox();
        vehicombo.setBounds(250, 80, 180, 25);
        vehicombo.setFont(new Font("HP Simplified", Font.BOLD, 18));
        vehicombo.setForeground(Color.BLACK);

        panel2.add(vehicle);
        panel2.add(vehicombo);

        source = new JLabel("SOURCE ");
        source.setBounds(250, 130, 130, 25);
        source.setFont(new Font("HP Simplified", Font.BOLD, 18));
        source.setForeground(Color.BLACK);

        srcombo = new JComboBox();
        srcombo.setBounds(250, 170, 180, 25);
        srcombo.setFont(new Font("HP Simplified", Font.BOLD, 16));
        srcombo.setForeground(Color.BLACK);

        desti = new JLabel("DESTINATION ");
        desti.setBounds(250, 220, 130, 25);
        desti.setFont(new Font("HP Simplified", Font.BOLD, 18));
        desti.setForeground(Color.BLACK);

        descombo = new JComboBox();
        descombo.setBounds(250, 260, 180, 25);
        descombo.setFont(new Font("HP Simplified", Font.BOLD, 16));
        descombo.setForeground(Color.BLACK);

        confirm = new JButton("CONFIRM");
        confirm.setBounds(295, 340, 90, 30);
        confirm.setFont(new Font("HP Simplified", Font.BOLD, 12));
        confirm.setBackground(new Color(231, 231, 231));

        back = new JButton("BACK ");
        back.setBounds(12, 10, 70, 30);
        back.setFont(new Font("HP Simplified", Font.BOLD, 12));
        back.setBackground(new Color(231, 231, 231, 231));

        namelabel = new JLabel();
        namelabel.setFont(new Font("HP Simplified", Font.BOLD, 28));
        namelabel.setBounds(10, 5, 250, 40);
        namelabel.setText(name);
        namelabel.setForeground(Color.WHITE);
        panel3.add(namelabel);

        panel2.add(vehicle);
        panel2.add(vehicombo);
        panel2.add(source);
        panel2.add(srcombo);
        panel2.add(desti);
        panel2.add(descombo);
        panel2.add(confirm);
        panel2.add(back);

        Frame.setLayout(null);
        Frame.setVisible(true);

        try {
            List<String> items1 = fetchItemsFromDatabase("Route", "Src");
            for (String item : items1) {
                srcombo.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<String> items1 = fetchItemsFromDatabase("Route", "Dest");
            for (String item : items1) {
                descombo.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<String> items3 = fetchVehiclesByCid(cid);
            for (String item : items3) {
                vehicombo.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.setVisible(false);
                new customer_page(cid).setVisible(true);
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int M=0;
                selectedVehicle = (String) vehicombo.getSelectedItem();
                selectedSource = (String) srcombo.getSelectedItem();
                selectedDestination = (String) descombo.getSelectedItem();

                if (Objects.equals(selectedSource, selectedDestination)) {
                    JOptionPane.showMessageDialog(null, "Source and destination must be different");
                    Frame.setVisible(false);
                    M++;
                }


                System.out.println("Selected Vehicle: " + selectedVehicle);
                System.out.println("Selected Source: " + selectedSource);
                System.out.println("Selected Destination: " + selectedDestination);
                try {
                    fetchRouteId(selectedSource, selectedDestination);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    fetchVehicleType(selectedVehicle);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Route id: " + RID);
                System.out.println("Vehicle type: " + Veh_Type);
                try {
                    myTolls=fetchTollsForRID(RID);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (myTolls == null) {
                    JOptionPane.showMessageDialog(null, "No tolls found for the selected route");
                    return; // Exit if no tolls are found
                }

                // Iterate over the tolls list and print each element
                System.out.println("Tolls for RID " + RID + ":");
                for (int i = 0; i < myTolls.size(); i++) {
                    System.out.println(myTolls.get(i));
                }
                if(M==0)
                {
                    new Driving(selectedVehicle,selectedSource,selectedDestination,RID,Veh_Type,myTolls,cid);
                }


            }
        });


    }

    private String fetchVehicleType(String Veh_No) throws SQLException {
        String query = "SELECT Veh_Type FROM Vehicle WHERE Veh_No = ? ;";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, Veh_No);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Veh_Type = rs.getString("Veh_Type");
                }
            }
        }
        return Veh_Type;
    }


    private java.util.List<String> fetchItemsFromDatabase(String TableName, String ColName) throws SQLException {
        List<String> items = new ArrayList<>();
        String query1 = "SELECT distinct " + ColName + " FROM " + TableName + ";";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt1 = conn.createStatement();
             ResultSet rs1 = stmt1.executeQuery(query1)) {
            while (rs1.next()) {
                items.add(rs1.getString(ColName));
            }

        }
        return items;
    }

    private List<String> fetchVehiclesByCid(int cid) throws SQLException {
        List<String> items = new ArrayList<>();
        String query = "SELECT Veh_No FROM Vehicle WHERE cid = " + cid + ";";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(rs.getString("Veh_No"));
            }
        }
        return items;
    }

    private int fetchRouteId(String source, String destination) throws SQLException {
        String query = "SELECT RID FROM Route WHERE Src = ? AND Dest = ? ; ";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RID = rs.getInt("RID");
                }
            }
            return RID;
        }
    }

    private List<Integer> fetchTollsForRID(int RID) throws SQLException {
        List<Integer> tolls = new ArrayList<>();
        String query = "SELECT Toll_No FROM Toll_Contractor WHERE RID = ?; ";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, RID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int toll = rs.getInt("Toll_No");
                    tolls.add(toll);
                }
            }
        }
        return tolls;
    }

    public static void main(String[] args) {
        new VehicleRoute(cid);
    }
}