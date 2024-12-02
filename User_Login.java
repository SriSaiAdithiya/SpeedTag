package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_Login {
    JLabel name, userTypeLabel;
    JFrame MainFrame;
    JLabel imglabel;
    JPanel mypanel;
    JLabel password;
    JTextField username;
    JPasswordField pass;
    JButton reset;
    JCheckBox showpass;
    JLabel signup;
    JButton signbut;
    JButton login;
    JComboBox<String> userType;

    User_Login() {
        MainFrame = new JFrame("Login Page");

        imglabel = new JLabel();
        imglabel.setIcon(new ImageIcon(new ImageIcon("\\D:\\OS NOTES\\javaprojects\\dpps2.jpg\\").getImage().getScaledInstance(1300, 650, Image.SCALE_SMOOTH)));
        MainFrame.setContentPane(imglabel);

        MainFrame.getContentPane().setLayout(null);
        MainFrame.setSize(1300, 650);

        mypanel = new JPanel();
        mypanel.setLayout(null);
        MainFrame.getContentPane().add(mypanel);
        mypanel.setBounds(400, 180, 500, 300);
        mypanel.setBackground(new Color(166, 165, 153, 25));

        name = new JLabel("User Name ");
        name.setBounds(80, 60, 130, 25);
        name.setFont(new Font("Gadugi", Font.BOLD, 18));
        name.setForeground(Color.white);

        password = new JLabel("Password ");
        password.setBounds(80, 100, 130, 25);
        password.setFont(new Font("Gadugi", Font.BOLD, 18));
        password.setForeground(Color.white);

        username = new JTextField();
        username.setBounds(230, 60, 200, 25);
        username.setFont(new Font("HP Simplified", Font.BOLD, 16));

        pass = new JPasswordField();
        pass.setBounds(230, 100, 200, 25);
        pass.setFont(new Font("HP Simplified", Font.BOLD, 16));

        userTypeLabel = new JLabel("User Type ");
        userTypeLabel.setBounds(80, 140, 130, 25);
        userTypeLabel.setFont(new Font("Gadugi", Font.BOLD, 18));
        userTypeLabel.setForeground(Color.white);

        userType = new JComboBox<>(new String[]{"Customer", "Contractor"});
        userType.setBounds(230, 140, 200, 25);
        userType.setFont(new Font("HP Simplified", Font.BOLD, 16));

        reset = new JButton("Reset");
        reset.setBounds(350, 215, 80, 20);
        reset.setFont(new Font("HP Simplified", Font.BOLD, 12));
        reset.setBackground(new Color(250, 250, 250));

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                pass.setText("");
                userType.setSelectedIndex(0);
            }
        });

        showpass = new JCheckBox("Show password");
        showpass.setBounds(320, 260, 122, 20);
        showpass.setFont(new Font("HP Simplified", Font.BOLD, 12));

        showpass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showpass.isSelected()) {
                    pass.setEchoChar((char) 0);
                } else {
                    pass.setEchoChar('*');
                }
            }
        });

        signup = new JLabel("Don't have an account ->");
        signup.setBounds(80, 260, 200, 25);
        signup.setFont(new Font("HP Simplified", Font.BOLD, 14));
        signup.setForeground(Color.white);

        signbut = new JButton("Sign up");
        signbut.setBounds(230, 260, 80, 20);
        signbut.setFont(new Font("HP Simplified", Font.BOLD, 12));

        login = new JButton("Log in");
        login.setBounds(230, 215, 80, 20);
        login.setFont(new Font("HP Simplified", Font.BOLD, 12));
        login.setBackground(new Color(250, 250, 250));

        MainFrame.setLocationRelativeTo(null);
        mypanel.add(name);
        mypanel.add(password);
        mypanel.add(username);
        mypanel.add(pass);
        mypanel.add(userTypeLabel);
        mypanel.add(userType);
        mypanel.add(reset);
        mypanel.add(login);
        mypanel.add(showpass);
        mypanel.add(signup);
        mypanel.add(signbut);
        MainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainFrame.getContentPane().setLayout(null);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = 0;
                char[] s = pass.getPassword();
                String pass1 = new String(s);
                if (username.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User Name cannot be empty");
                    x++;
                }
                if (pass.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty");
                    x++;
                }

                if (x == 0) {
                    try (Connection con = DbConnection.getConnection()) {
                        String userTypeSelected = (String) userType.getSelectedItem();
                        if (userTypeSelected.equals("Customer")) {
                            String customerQuery = "SELECT CID FROM Customer WHERE Cust_Name = ? AND Cust_Password = ?";
                            PreparedStatement customerPst = con.prepareStatement(customerQuery);
                            customerPst.setString(1, username.getText());
                            customerPst.setString(2, pass1);
                            ResultSet customerRs = customerPst.executeQuery();

                            if (customerRs.next()) {
                                MainFrame.setVisible(false);
                                int cid = customerRs.getInt("CID");
                                new customer_page(cid).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid username or password for Customer");
                            }
                        } else if (userTypeSelected.equals("Contractor")) {
                            String contractorQuery = "SELECT Con_id FROM Contractor WHERE cname = ? AND Con_Password = ?";
                            PreparedStatement contractorPst = con.prepareStatement(contractorQuery);
                            contractorPst.setString(1, username.getText());
                            contractorPst.setString(2, pass1);
                            ResultSet contractorRs = contractorPst.executeQuery();

                            if (contractorRs.next()) {
                                MainFrame.setVisible(false);
                                int conid = contractorRs.getInt("Con_id");
                                new Contractor_page(conid).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid username or password for Contractor");
                            }
                        }
                    } catch (SQLException E) {
                        System.out.println(E.getMessage());
                    }
                }
            }
        });

        signbut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new signIn().setVisible(true);
            }
        });

        MainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new User_Login();
    }
}