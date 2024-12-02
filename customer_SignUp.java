package org.example;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class customer_SignUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    customer_SignUp frame = new customer_SignUp();
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
    public customer_SignUp() {
        setTitle("Customer SignUp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 100, 1200, 650);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 177, 100));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAccountNo = new JLabel("Account No :");
        lblAccountNo.setFont(new Font("Arial", Font.BOLD, 18));
        lblAccountNo.setBounds(65, 322, 161, 39);
        contentPane.add(lblAccountNo);

        JLabel lblNewLabel_4_1 = new JLabel("Name:");
        lblNewLabel_4_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_1.setBounds(65, 127, 161, 39);
        contentPane.add(lblNewLabel_4_1);

        JLabel lblNewLabel_4_2 = new JLabel("Adhaar No:");
        lblNewLabel_4_2.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_2.setBounds(65, 189, 161, 39);
        contentPane.add(lblNewLabel_4_2);

        JLabel lblNewLabel_4_3 = new JLabel("E-mail :");
        lblNewLabel_4_3.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_3.setBounds(65, 254, 161, 39);
        contentPane.add(lblNewLabel_4_3);

        JLabel lblNewLabel_4_4 = new JLabel("Password :");
        lblNewLabel_4_4.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_4.setBounds(65, 388, 161, 39);
        contentPane.add(lblNewLabel_4_4);

        JLabel lblNewLabel_4_5 = new JLabel("Phone No:");
        lblNewLabel_4_5.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_5.setBounds(65, 453, 161, 39);
        contentPane.add(lblNewLabel_4_5);

        JLabel lblNewLabel_4_6 = new JLabel("Bank Name:");
        lblNewLabel_4_6.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_6.setBounds(65, 511, 161, 39);
        contentPane.add(lblNewLabel_4_6);

        JLabel lblNewLabel_4_7 = new JLabel("Closing Balance:");
        lblNewLabel_4_7.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_4_7.setBounds(65, 569, 161, 39);
        contentPane.add(lblNewLabel_4_7);

        textField = new JTextField(); //name
        textField.setBounds(525, 134, 170, 28);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField(); //aadhar
        textField_1.setColumns(10);
        textField_1.setBounds(525, 196, 170, 28);
        contentPane.add(textField_1);

        textField_2 = new JTextField(); //email
        textField_2.setColumns(10);
        textField_2.setBounds(525, 261, 170, 28);
        contentPane.add(textField_2);

        textField_3 = new JTextField(); //accno
        textField_3.setColumns(10);
        textField_3.setBounds(525, 329, 170, 28);
        contentPane.add(textField_3);

        textField_4 = new JPasswordField(); //pass
        textField_4.setColumns(10);
        textField_4.setBounds(525, 395, 170, 28);
        contentPane.add(textField_4);

        textField_5 = new JTextField(); //phno
        textField_5.setColumns(10);
        textField_5.setBounds(525, 453, 170, 28);
        contentPane.add(textField_5);

        textField_6 = new JTextField(); // bankname
        textField_6.setColumns(10);
        textField_6.setBounds(525, 511, 170, 28);
        contentPane.add(textField_6);

        textField_7 = new JTextField(); // bal
        textField_7.setColumns(10);
        textField_7.setBounds(525, 569, 170, 28);
        contentPane.add(textField_7);


        JButton btnNewButton = new JButton("Register");
        btnNewButton.setBackground(new Color(255, 128, 0));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               /* String url="jdbc:mysql://localhost:3306/sample1";
                String userName="root";
                String passWord="sri123sai123";*/
                try {
                    Connection con= DbConnection.getConnection();

                    String bankQuery = "INSERT INTO BANK (Bank_Name, Acc_No,Closing_Bal) VALUES (?, ?,?);";
                    PreparedStatement bankPst = con.prepareStatement(bankQuery, Statement.RETURN_GENERATED_KEYS);
                    bankPst.setString(1, textField_6.getText()); // Bank_Name
                    bankPst.setString(2, textField_3.getText()); // Acc_No
                    bankPst.setDouble(3, Double.parseDouble(textField_7.getText())); // Closing_Bal//bal
                    int bankRowsAffected = bankPst.executeUpdate();

                    String query="insert into CUSTOMER (Acc_No,Aadhar_No,Email,Cust_Name,Ph_no,Cust_Password)values(?,?,?,?,?,?);";
                    PreparedStatement pst=con.prepareStatement(query);
                    pst.setString(1, textField_3.getText());
                    pst.setString(2, textField_1.getText()); //aadhar
                    pst.setString(3, textField_2.getText());
                    pst.setString(4, textField.getText());
                    pst.setString(5, textField_5.getText());
                    pst.setString(6, textField_4.getText());

                    int Rows=pst.executeUpdate();
                    JOptionPane.showMessageDialog(btnNewButton, Rows+"Record added");

                    pst.close();
                    con.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.out.println("Error: " + e1.getMessage());
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton.setBounds(154, 610, 130, 40);
        contentPane.add(btnNewButton);

        JLabel lblNewLabel = new JLabel("Customer SignUp");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setBounds(140, 38, 251, 52);
        contentPane.add(lblNewLabel);
    }
}