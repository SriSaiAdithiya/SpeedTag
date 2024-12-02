package org.example;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Contractor extends JFrame {

    static final long serialVersionUID = 1L;
    public JPanel contentPane;
    public JTextField textField;
    public JTextField textField_1;
    public JTextField textField_2;
    public JPasswordField textField_3;
    public JTextField textField_4;

    String email;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Contractor frame = new Contractor();
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
    public Contractor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1500, 1000);
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

        JLabel lblNewLabel_3 = new JLabel("DPPS");
        lblNewLabel_3.setFont(new Font("Gadugi", Font.BOLD, 17));
        lblNewLabel_3.setBounds(10, 5, 104, 35);
        panel.add(lblNewLabel_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(14, 132, 248));
        panel_1.setBounds(0, 604, 1370, 145);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(198, 250, 253), new Color(14, 132, 248), new Color(14, 132, 248), new Color(14, 132, 248)));
        panel_2.setBackground(new Color(255, 255, 255));
        panel_2.setBounds(474, 51, 438, 553);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel("   SignUp");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(156, 11, 111, 37);
        panel_2.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Name ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(79, 68, 101, 29);
        panel_2.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Aadhaar No. ");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(79, 148, 111, 29);
        panel_2.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("E-mail");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(79, 230, 101, 29);
        panel_2.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Password");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_3.setBounds(79, 310, 101, 29);
        panel_2.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_4 = new JLabel("Phone No.");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_4.setBounds(79, 387, 101, 29);
        panel_2.add(lblNewLabel_1_4);

        textField = new JTextField();
        textField.setBounds(79, 108, 273, 29);
        panel_2.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(79, 190, 273, 29);
        panel_2.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(79, 270, 273, 29);
        panel_2.add(textField_2);

        textField_3 = new JPasswordField();
        textField_3.setColumns(10);
        textField_3.setBounds(79, 347, 273, 29);
        panel_2.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(79, 427, 271, 29);
        panel_2.add(textField_4);

        JButton btnNewButton = new JButton("Register");
        btnNewButton.setBounds(155, 489, 120, 36);
        panel_2.add(btnNewButton);
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*String url = "jdbc:mysql://localhost:3306/Sample2";
                String userName = "root";
                String passWord = "sri123sai123";*/
                try (Connection con = DbConnection.getConnection()) {
                    String query = "INSERT INTO contractor (cname, Aadhar, email, Con_Password, ph_no) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pst = con.prepareStatement(query)) {
                        pst.setString(1, textField.getText());
                        pst.setString(2, textField_1.getText()); // aadhar
                        pst.setString(3, textField_2.getText());
                        pst.setString(4, new String(textField_3.getPassword()));
                        pst.setString(5, textField_4.getText());

                        email = textField_2.getText();

                        int rows = pst.executeUpdate();
                        JOptionPane.showMessageDialog(btnNewButton, rows + " Record(s) added");

                        setVisible(false);
                        new Bank_SignupCon(email).setVisible(true);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(btnNewButton, "Error: " + e1.getMessage());
                }
            }
        });
    }
}