package org.example;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class signIn extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    signIn frame = new signIn();
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
    public signIn() {
        setBackground(new Color(240, 240, 240));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        panel.setBounds(38, 28, 360, 193);
        contentPane.add(panel);
        panel.setLayout(null);

        JButton btnNewButton = new JButton("Customer");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton.setBounds(10, 82, 127, 39);
        panel.add(btnNewButton);

        JLabel lblNewLabel = new JLabel("         Register As ");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(74, 22, 205, 27);
        panel.add(lblNewLabel);

        JButton btnContractor = new JButton("Contractor");
        btnContractor.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnContractor.setBounds(208, 82, 127, 39);
        panel.add(btnContractor);

        btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new Customer().setVisible(true);

            }
        });

        btnContractor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new Contractor().setVisible(true);

            }
        });
    }
}
