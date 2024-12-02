package org.example;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Recharge_history extends JFrame {

    static final long serialVersionUID = 1L;
    public JPanel contentPane;
    private JTable table;
    private static int cid;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Recharge_history frame = new Recharge_history(cid);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Recharge_history(int cid) {
        Recharge_history.cid = cid;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1500, 1000);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1370, 40);
        panel.setBackground(new Color(14, 132, 248));
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("DPPS");
        lblNewLabel_3.setFont(new Font("Gadugi", Font.BOLD, 17));
        lblNewLabel_3.setBounds(10, 5, 104, 35);
        panel.add(lblNewLabel_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 658, 1370, 91);
        panel_1.setBackground(new Color(14, 132, 248));
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBackground(new Color(13, 13, 255));
        panel_2.setBounds(0, 36, 1370, 40);
        contentPane.add(panel_2);

        JLabel lblNewLabel = new JLabel("    Recharge History ");
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        lblNewLabel.setBounds(540, 101, 272, 50);
        contentPane.add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(230, 179, 900, 400);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));

        loadRechargeHistory(cid);
    }

    private void loadRechargeHistory(int cid) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT * FROM Recharge WHERE cid = ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, cid);
            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            //String[] colNames = new String[cols];
            String[] colNames = {"CID","Customer Name","Timestamp","Vehicle No.","Amount"};

            //for (int i = 0; i < cols; i++) {
            //   colNames[i] = rsmd.getColumnName(i + 1);
            //}
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

