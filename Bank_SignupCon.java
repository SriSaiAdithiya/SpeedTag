package org.example;


import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Bank_SignupCon extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private String email;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String email = "";
                    Bank_SignupCon frame = new Bank_SignupCon(email);
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
    public Bank_SignupCon(String email) {
        this.email = email;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1500, 1000);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(14,132,248));
        panel.setBounds(0, 0, 1370, 40);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("DPPS");
        lblNewLabel_3.setFont(new Font("Gadugi", Font.BOLD, 17));
        lblNewLabel_3.setBounds(10, 5, 104, 35);
        panel.add(lblNewLabel_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(14,132,248));
        panel_1.setBounds(0, 575, 1370, 174);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(198, 250, 253), new Color(14, 132, 248), new Color(14, 132, 248), new Color(14, 132, 248)));
        panel_2.setBackground(new Color(255, 255, 255));
        panel_2.setBounds(470, 79, 430, 439);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel(" Bank Details");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(144, 11, 143, 37);
        panel_2.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Name ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(79, 68, 101, 29);
        panel_2.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Account No. ");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(79, 148, 111, 29);
        panel_2.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Balance");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(79, 230, 101, 29);
        panel_2.add(lblNewLabel_1_2);

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

        JButton btnNewButton = new JButton("Submit");
        btnNewButton.setBounds(152, 350, 120, 36);
        panel_2.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                /*String url = "jdbc:mysql://localhost:3306/Sample2";
                String userName = "root";
                String passWord = "sri123sai123";*/
                try {
                    Connection con = DbConnection.getConnection();

                    String bankQuery = "INSERT INTO BANK (Bank_Name, Acc_No, Closing_Bal) VALUES (?, ?, ?);";
                    PreparedStatement bankPst = con.prepareStatement(bankQuery, Statement.RETURN_GENERATED_KEYS);
                    bankPst.setString(1, textField.getText()); // Bank_Name
                    bankPst.setString(2, textField_1.getText()); // Acc_No
                    bankPst.setDouble(3, Double.parseDouble(textField_2.getText())); // Closing_Bal
                    int Rows = bankPst.executeUpdate();
                    JOptionPane.showMessageDialog(btnNewButton, Rows + " Record added");
                    bankPst.close();

                    String addAccQuery = "UPDATE Contractor SET Acc_No = ? WHERE email = ?";
                    PreparedStatement addAccPst = con.prepareStatement(addAccQuery);
                    addAccPst.setString(1, textField_1.getText()); // Bank account number
                    addAccPst.setString(2, email); // Email obtained from the Contractor class
                    int rowsUpdated = addAccPst.executeUpdate();
                    JOptionPane.showMessageDialog(btnNewButton, rowsUpdated + " Record added");
                    addAccPst.close();

                    con.close();

                    //new customer_page(cid).setVisible(true);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println("Error: " + e1.getMessage());
                }
            }
        });
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
    }
}
