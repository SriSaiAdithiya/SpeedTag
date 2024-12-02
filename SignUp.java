package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    JLabel sname;
    JFrame signFrame;
    JLabel img1label;
    JPanel spanel;
    JLabel spassword;
    JTextField susername;
    JPasswordField spass;
    JPasswordField srepass;
    JButton sreset;
    JButton ssignup;
    JLabel reenter;

    SignUp(){
        signFrame = new JFrame("SignUp Page");

        img1label = new JLabel();
        img1label.setIcon(new ImageIcon(new ImageIcon("D:\\TCS\\Backimg.jpg").getImage().getScaledInstance(1300,650, Image.SCALE_SMOOTH)
        ));
        signFrame.setContentPane(img1label);


        signFrame.setLayout(null);
        signFrame.setSize(1300,650);



        spanel = new JPanel();
        spanel.setLayout(null);
        signFrame.add(spanel);
        spanel.setBounds(400,180,500,300);
        spanel.setBackground(new Color(166,165,153,125));




        sname = new JLabel("User Name :");
        sname.setBounds(60,60,130,25);
        sname.setFont(new Font("Gadugi",Font.BOLD,18));

        spassword = new JLabel("Password :");
        spassword.setBounds(60,100,130,25);
        spassword.setFont(new Font("Gadugi",Font.BOLD,18));

        susername = new JTextField();
        susername.setBounds(230,60,200,25);
        susername.setFont(new Font("HP Simplified",Font.BOLD,16));


        spass = new JPasswordField();
        spass.setBounds(230,100,200,25);
        spass.setFont(new Font("HP Simplified",Font.BOLD,16));

        reenter = new JLabel("Re-enter Password:");
        reenter.setBounds(60,140,170,25);
        reenter.setFont(new Font("Gadugi",Font.BOLD,18));

        srepass = new JPasswordField();
        srepass.setBounds(230,140,200,25);
        srepass.setFont(new Font("HP Simplified",Font.BOLD,16));

        sreset = new JButton("Reset");
        sreset.setBounds(350,200,80,20);
        sreset.setFont(new Font("HP Simplified",Font.BOLD,12));
        sreset.setBackground(new Color(250,250,250));

        sreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                susername.setText("");
                spass.setText("");
                srepass.setText("");

            }
        });

        ssignup = new JButton("Confirm");
        ssignup.setBounds(210,260,80,20);
        ssignup.setFont(new Font("HP Simplified",Font.BOLD,12));

        signFrame.setLocationRelativeTo(null);
        spanel.add(sname);
        spanel.add(spassword);
        spanel.add(susername);
        spanel.add(spass);
        spanel.add(sreset);
        spanel.add(ssignup);
        spanel.add(reenter);
        spanel.add(srepass);

        signFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        signFrame.setLayout(null);

        ssignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x=0;
                int y=0;
                int m=0;
                if(susername.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"User Name cannot be empty");
                    x++;
                    m++;
                }
                if(spass.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Password cannot be empty");
                    x++;
                    m++;
                }
                if(spass.getText().equals(srepass.getText()))
                {
                    if(m==0)
                    {
                        JOptionPane.showMessageDialog(null,"Remember your password");
                        y++;
                    }

                }
                if(y==0)
                {
                    if(x==0)
                    {
                        JOptionPane.showMessageDialog(null,"Invalid re-entered password");
                        x++;
                    }

                }



                if(x==0)
                {
                    signFrame.setVisible(false);
                    new User_Login();
                }

            }
        });



//        MainFrame.addWindowListener(new WindowAdapter()
//        {
//            public void windowClosing(WindowEvent e)
//            {
//                System.exit(0);
//            }
//        });

        signFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
