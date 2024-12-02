package org.example;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Contractor_page extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField textField_3;
    private JTextField textField_4;
    private static int conid;
    private static String name;
    private static String mail;
    private static String phone;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Contractor_page frame = new Contractor_page(conid);
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
    public Contractor_page(int conid) {
        this.conid = conid;

        try (Connection con = DbConnection.getConnection()) {
            String Query = "Select cname, ph_no, email from contractor where Con_id = ?;";
            PreparedStatement pst = con.prepareStatement(Query);
            pst.setInt(1, conid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
                phone = rs.getString(2);
                mail = rs.getString(3);
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } catch (SQLException E) {
            System.out.println(E.getMessage());
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setResizable(true); // Make the window resizable
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(14, 132, 248));
        panel.setBounds(0, 0, 1370, 40);
        contentPane.add(panel);
        panel.setLayout(null);

        // Back button to navigate to User_login page
        JButton backButton = new JButton(new ImageIcon("D:\\OS NOTES\\javaprojects\\icons8-back-30.png"));
        backButton.setBounds(10, 5, 30, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new User_Login(); // Open the User_login window
            }
        });
        panel.add(backButton);

        JLabel lblNewLabel_3 = new JLabel("DPPS");
        lblNewLabel_3.setFont(new Font("Gadugi", Font.BOLD, 17));
        lblNewLabel_3.setBounds(50, 5, 104, 35);
        panel.add(lblNewLabel_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(14, 132, 248));
        panel_1.setBounds(0, 662, 1381, 87);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("D:\\OS NOTES\\javaprojects\\i8.png"));
        lblNewLabel.setBounds(35, 173, 74, 66);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_4 = new JLabel();
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_4.setBounds(117, 188, 178, 40);
        lblNewLabel_4.setText(name);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_6 = new JLabel("");
        lblNewLabel_6.setBounds(35, 385, 61, 66);
        contentPane.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("     Profile");
        lblNewLabel_7.setFont(new Font("Verdana", Font.BOLD, 25));
        lblNewLabel_7.setBounds(111, 107, 246, 40);
        contentPane.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel();
        lblNewLabel_8.setIcon(new ImageIcon("D:\\OS NOTES\\javaprojects\\email_3178165.png"));
        lblNewLabel_8.setBounds(35, 304, 83, 54);
        contentPane.add(lblNewLabel_8);

        JLabel lblNewLabel_4_2 = new JLabel(mail);
        lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_4_2.setBounds(117, 318, 178, 40);
        contentPane.add(lblNewLabel_4_2);

        JLabel lblNewLabel_2_1 = new JLabel();
        lblNewLabel_2_1.setIcon(new ImageIcon("D:\\OS NOTES\\javaprojects\\phone-call_455705.png"));
        lblNewLabel_2_1.setBounds(35, 413, 61, 66);
        contentPane.add(lblNewLabel_2_1);

        JLabel lblNewLabel_4_1_1 = new JLabel(phone);
        lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_4_1_1.setBounds(119, 427, 178, 40);
        contentPane.add(lblNewLabel_4_1_1);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBackground(new Color(13, 13, 255));
        panel_2.setBounds(0, 36, 1370, 40);
        contentPane.add(panel_2);

        JLabel lblNewLabel_1 = new JLabel("Toll Taken For Lease");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setBackground(new Color(192, 192, 192));
        lblNewLabel_1.setBounds(628, 105, 218, 48);
        contentPane.add(lblNewLabel_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(340, 188, 666, 320);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setBackground(new Color(192, 192, 192));
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));

        JButton btnAmountCollected = new JButton("Amount Collected");
        btnAmountCollected.setBounds(1050, 413, 226, 42);
        contentPane.add(btnAmountCollected);
        btnAmountCollected.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnAmountCollected.setBackground(Color.WHITE);
        btnAmountCollected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose(); // Close the current window
                new AmountCollected(conid).setVisible(true); // Open the AmountCollected window
            }
        });

        JButton btnAddTag = new JButton("Buy Toll");
        btnAddTag.setBounds(1050, 274, 226, 42);
        contentPane.add(btnAddTag);
        btnAddTag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose(); // Close the current window
                new BuyToll(conid).setVisible(true); // Open the new window
            }
        });
        btnAddTag.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnAddTag.setBackground(Color.WHITE);

        JPanel panel_2_1 = new JPanel();
        panel_2_1.setLayout(null);
        panel_2_1.setBackground(new Color(13, 13, 255));
        panel_2_1.setBounds(0, 623, 1370, 40);
        contentPane.add(panel_2_1);
        loadToll(conid);
    }

    private void loadToll(int conid) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT toll_contractor.toll_no, GROUP_CONCAT(DISTINCT toll_contractor.rid), " +
                    "GROUP_CONCAT(DISTINCT route.src), GROUP_CONCAT(DISTINCT route.dest) " +
                    "FROM toll_contractor " +
                    "JOIN route ON toll_contractor.rid = route.rid " +
                    "WHERE toll_contractor.con_id = ? " +
                    "GROUP BY toll_contractor.toll_no";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, conid);
            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] colNames = { "Toll No.", "Route No.", "Source", "Destination" };

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[cols];
                for (int i = 0; i < cols; i++) {
                    if (i == 1) {
                        // Splitting the comma-separated values and joining them with commas
                        String[] tollNumbers = rs.getString(i + 1).split("\n");
                        String uniqueTollNumbers = String.join("\n ", tollNumbers);
                        row[i] = uniqueTollNumbers;
                    } else if (i == 2 || i == 3) {
                        // Replace commas with line breaks
                        row[i] = rs.getString(i + 1).replace(",", "\n");
                    } else {
                        // Setting other values directly
                        row[i] = rs.getString(i + 1);
                    }
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}