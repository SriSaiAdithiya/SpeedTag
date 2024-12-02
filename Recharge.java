package org.example;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Recharge extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JPasswordField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    protected Window frame;
    private static int cid;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Recharge frame = new Recharge(cid);
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
    public Recharge(int cid) {
        //System.out.println(1);
        this.cid = cid;
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
        panel_1.setBounds(0, 603, 1370, 146);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(198, 250, 253), new Color(14, 132, 248), new Color(14, 132, 248), new Color(14, 132, 248)));
        panel_2.setBackground(new Color(255, 255, 255));
        panel_2.setBounds(470, 63, 430, 529);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel("Recharge Tag");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(131, 11, 143, 37);
        panel_2.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Customer Name");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(63, 70, 168, 29);
        panel_2.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Vehicle Id");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(63, 150, 111, 29);
        panel_2.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Balance Amount");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(63, 230, 128, 29);
        panel_2.add(lblNewLabel_1_2);

        textField = new JTextField();
        textField.setBounds(63, 110, 273, 29);
        panel_2.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(63, 190, 273, 29);
        panel_2.add(textField_1);

        JButton btnNewButton = new JButton("Recharge");
        btnNewButton.setBounds(132, 447, 162, 36);
        panel_2.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try(Connection con = DbConnection.getConnection()){

                    //System.out.println(2);
                    String Query = "UPDATE tag SET tag.wallet_bal = tag.wallet_bal + ? "
                            + "WHERE (select vehicle.tag_id from vehicle where cid = ? and veh_no = ? ) = tag.tag_id;";

                    PreparedStatement pst = con.prepareStatement(Query);
                    pst.setString(1,textField_5.getText());
                    pst.setInt(2, cid);
                    pst.setString(3, textField_1.getText());
                    //pst.setString(2,pass1);
                    int rowsUpdated = pst.executeUpdate();


                    String Query1 = "update bank set closing_bal = closing_bal - ? where "
                            + "acc_no = (select customer.acc_no from customer where customer.cid = ?); ";

                    PreparedStatement pst1 = con.prepareStatement(Query1);
                    pst1.setString(1,textField_5.getText());
                    pst1.setInt(2, cid);

                    int rowsUpdated1 = pst1.executeUpdate();
                    JOptionPane.showMessageDialog(btnNewButton, " Payment Completed successfully");
                    btnNewButton.setEnabled(false);
                    pst.close();
                    pst1.close();
                    con.close();

                }catch(SQLException E){
                    E.printStackTrace();
                    System.out.println(E.getMessage());
                }
            }
        });
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));

        JLabel lblNewLabel_1_2_1 = new JLabel("Pay Amount");
        lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_2_1.setBounds(63, 322, 128, 29);
        panel_2.add(lblNewLabel_1_2_1);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(63, 362, 273, 29);
        panel_2.add(textField_5);

        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(63, 278, 273, 33);
        panel_2.add(lblNewLabel_2);


    }
}