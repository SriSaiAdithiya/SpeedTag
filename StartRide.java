package org.example;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StartRide {
    JFrame Rideframe;
    JPanel dummy1;
    JPanel dummy2;
    JPanel mainpanel;
    JButton lane1,lane2,lane3,lane4,status;
    JButton kane1,kane2,kane3,kane4,status1;
    JButton jane1,jane2,jane3,jane4,status2;
    JLabel toll1,toll2,toll3;
    JComboBox<String> sourceBox;
    JComboBox<String> destiBox;

    int x,y,z;


    StartRide(){
        Rideframe = new JFrame("Ride Page");
        Rideframe.setLayout(null);
        Rideframe.setSize(1300,650);

        mainpanel = new JPanel();
        mainpanel.setLayout(null);
        Rideframe.add(mainpanel);
        mainpanel.setBounds(50,20,1180,565);
        mainpanel.setBackground(new Color(110, 194, 246));

        lane1 = new JButton("LANE 1");
        lane2 = new JButton("LANE 2");
        lane3 = new JButton("LANE 3");
        lane4 = new JButton("LANE 4");

        kane1 = new JButton("LANE 1");
        kane2 = new JButton("LANE 2");
        kane3 = new JButton("LANE 3");
        kane4 = new JButton("LANE 4");

        jane1 = new JButton("LANE 1");
        jane2 = new JButton("LANE 2");
        jane3 = new JButton("LANE 3");
        jane4 = new JButton("LANE 4");

        lane1 = new JButton("LANE 1");
        lane1.setBounds(270,125,80,30);
        lane1.setFont(new Font("HP Simplified",Font.BOLD,12));
        lane1.setBackground(new Color(250,250,250));


        lane2 = new JButton("LANE 2");
        lane2.setBounds(420,125,80,30);
        lane2.setFont(new Font("HP Simplified",Font.BOLD,12));
        lane2.setBackground(new Color(250,250,250));

        lane3 = new JButton("LANE 3");
        lane3.setBounds(570,125,80,30);
        lane3.setFont(new Font("HP Simplified",Font.BOLD,12));
        lane3.setBackground(new Color(250,250,250));

        lane4 = new JButton("LANE 4");
        lane4.setBounds(720,125,80,30);
        lane4.setFont(new Font("HP Simplified",Font.BOLD,12));
        lane4.setBackground(new Color(250,250,250));

        toll1 = new JLabel("KANIYUR");
        toll1.setFont(new Font("HP Simplified",Font.BOLD,16));
        toll1.setBounds(25,125,120,30);


        kane1 = new JButton("LANE 1");
        kane1.setBounds(270,225,80,30);
        kane1.setFont(new Font("HP Simplified",Font.BOLD,12));
        kane1.setBackground(new Color(250,250,250));


        kane2 = new JButton("LANE 2");
        kane2.setBounds(420,225,80,30);
        kane2.setFont(new Font("HP Simplified",Font.BOLD,12));
        kane2.setBackground(new Color(250,250,250));

        kane3 = new JButton("LANE 3");
        kane3.setBounds(570,225,80,30);
        kane3.setFont(new Font("HP Simplified",Font.BOLD,12));
        kane3.setBackground(new Color(250,250,250));

        kane4 = new JButton("LANE 4");
        kane4.setBounds(720,225,80,30);
        kane4.setFont(new Font("HP Simplified",Font.BOLD,12));
        kane4.setBackground(new Color(250,250,250));

        toll2 = new JLabel("VAIGUNTHAM");
        toll2.setFont(new Font("HP Simplified",Font.BOLD,16));
        toll2.setBounds(25,225,120,30);

        jane1 = new JButton("LANE 1");
        jane1.setBounds(270,325,80,30);
        jane1.setFont(new Font("HP Simplified",Font.BOLD,12));
        jane1.setBackground(new Color(250,250,250));


        jane2 = new JButton("LANE 2");
        jane2.setBounds(420,325,80,30);
        jane2.setFont(new Font("HP Simplified",Font.BOLD,12));
        jane2.setBackground(new Color(250,250,250));

        jane3 = new JButton("LANE 3");
        jane3.setBounds(570,325,80,30);
        jane3.setFont(new Font("HP Simplified",Font.BOLD,12));
        jane3.setBackground(new Color(250,250,250));

        jane4 = new JButton("LANE 4");
        jane4.setBounds(720,325,80,30);
        jane4.setFont(new Font("HP Simplified",Font.BOLD,12));
        jane4.setBackground(new Color(250,250,250));



        toll3 = new JLabel("VIJAYAMANGALAM");
        toll3.setFont(new Font("HP Simplified",Font.BOLD,16));
        toll3.setBounds(25,325,140,30);


        status = new JButton("ARRIVED");
        status.setBounds(940,125,80,30);
        status.setFont(new Font("HP Simplified",Font.BOLD,12));
        status.setBackground(new Color(250,250,250));

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x==1)
                {
                    JOptionPane.showMessageDialog(null,"Toll Crossed");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Toll not arrived yet");
                }
            }

        });

        status1 = new JButton("ARRIVED");
        status1.setBounds(940,225,80,30);
        status1.setFont(new Font("HP Simplified",Font.BOLD,12));
        status1.setBackground(new Color(250,250,250));

        status1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(y==1)
                {
                    JOptionPane.showMessageDialog(null,"Toll Crossed");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Toll not arrived yet");
                }
            }

        });

        status2 = new JButton("ARRIVED");
        status2.setBounds(940,325,80,30);
        status2.setFont(new Font("HP Simplified",Font.BOLD,12));
        status2.setBackground(new Color(250,250,250));

        status2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(z==1)
                {
                    JOptionPane.showMessageDialog(null,"Toll Crossed");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Toll not arrived yet");
                }
            }

        });



        lane1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x=1;
                lane1.setBackground(Color.lightGray);
                mainpanel.add(status);
                status.setBackground(Color.green);
            }
        });

        lane2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x=1;
                lane2.setBackground(Color.lightGray);
                mainpanel.add(status);
                status.setBackground(Color.green);
            }
        });

        lane3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x=1;
                lane3.setBackground(Color.lightGray);
                mainpanel.add(status);
                status.setBackground(Color.green);
            }
        });

        lane4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x=1;
                lane4.setBackground(Color.lightGray);
                mainpanel.add(status);
                status.setBackground(Color.green);
            }
        });

        kane1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    y=1;
                    kane1.setBackground(Color.lightGray);
                    mainpanel.add(status1);
                    status1.setBackground(Color.green);
                }

            }
        });

        kane2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    y=1;
                    kane2.setBackground(Color.lightGray);
                    mainpanel.add(status1);
                    status1.setBackground(Color.green);
                }

            }
        });

        kane3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    y=1;
                    kane3.setBackground(Color.lightGray);
                    mainpanel.add(status1);
                    status1.setBackground(Color.green);
                }

            }
        });

        kane4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    y=1;
                    kane4.setBackground(Color.lightGray);
                    mainpanel.add(status1);
                    status1.setBackground(Color.green);
                }

            }
        });

        jane1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1 || y!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    z=1;
                    jane1.setBackground(Color.lightGray);
                    mainpanel.add(status2);
                    status2.setBackground(Color.green);
                }

            }
        });

        jane2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1 || y!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    z=1;
                    jane2.setBackground(Color.lightGray);
                    mainpanel.add(status2);
                    status2.setBackground(Color.green);
                }

            }
        });

        jane3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1 || y!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    z=1;
                    jane3.setBackground(Color.lightGray);
                    mainpanel.add(status2);
                    status2.setBackground(Color.green);
                }

            }
        });

        jane4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x!=1 || y!=1)
                {
                    JOptionPane.showMessageDialog(null,"You can't skip tolls");
                }
                else {
                    z=1;
                    jane4.setBackground(Color.lightGray);
                    mainpanel.add(status2);
                    status2.setBackground(Color.green);
                }

            }
        });

        mainpanel.add(lane1);
        mainpanel.add(lane2);
        mainpanel.add(lane3);
        mainpanel.add(lane4);
        mainpanel.add(kane1);
        mainpanel.add(kane2);
        mainpanel.add(kane3);
        mainpanel.add(kane4);
        mainpanel.add(jane1);
        mainpanel.add(jane2);
        mainpanel.add(jane3);
        mainpanel.add(jane4);
        //mainpanel.add(status);
        //mainpanel.add(status1);
        //mainpanel.add(status2);
        mainpanel.add(toll1);
        mainpanel.add(toll2);
        mainpanel.add(toll3);


        sourceBox = new JComboBox<>();
        sourceBox.setBounds(350, 15, 200, 30);
        mainpanel.add(sourceBox);

        destiBox = new JComboBox<>();
        destiBox.setBounds(580, 15, 200, 30);
        mainpanel.add(destiBox);


        try {
            List<String> items1 = fetchItemsFromDatabase("Route","Src");
            for (String item : items1) {
                sourceBox.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<String> items2 = fetchItemsFromDatabase("Route","Dest");
            for (String item : items2) {
                destiBox.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Rideframe.setVisible(true);
        Rideframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private List<String> fetchItemsFromDatabase(String TableName,String ColName) throws SQLException {
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


    public static void main(String[] args) {
        new StartRide();
    }

}
