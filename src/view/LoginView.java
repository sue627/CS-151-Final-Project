package view;

import control.LoginMessage;
import control.Message;
import control.SignupMessage;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingDeque;

public class LoginView extends JFrame {
    private JPanel loginPanel;
    private BlockingDeque<Message> queue;
    private int loginStatus;

    public LoginView(BlockingDeque<Message> queue) {
        this.loginStatus = 3;
        this.queue = queue;
        loginPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if(loginStatus == 2) {
                    g2.setColor(Color.red);
                    g2.setFont(new Font("", Font.PLAIN, 15));
                    g2.drawString("Invalid username or password",570,430);
                }
            }
        };

        //"welcome" label
        JLabel welcome = new JLabel("Welcome!");
        welcome.setFont(new Font("", Font.PLAIN, 35));
        welcome.setBounds(600,120,250,30);
        loginPanel.add(welcome);

        //"Username" label
        JLabel userName = new JLabel("Username:");
        userName.setFont(new Font("", Font.PLAIN, 20));
        userName.setBounds(480, 300, 150,30);
        loginPanel.add(userName);

        //"Password" label
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("", Font.PLAIN, 20));
        password.setBounds(480, 350, 100,30);
        loginPanel.add(password);

        //username textfield
        JTextField userText = new JTextField();
        userText.setBounds(600,300,250,30);
        loginPanel.add(userText);

        //password textfield
        JTextField passText = new JTextField();
        passText.setBounds(600,350,250,30);
        loginPanel.add(passText);

        //login button
        JButton loginB = new JButton("Login");
        loginB.setFont(new Font("",Font.PLAIN,15));
        loginB.setBounds(500,500,135,50);
        loginB.addActionListener((e)->{
            String inputUserName = userText.getText();
            String inputPassword = passText.getText();
            try {
                this.queue.put(new LoginMessage(inputUserName, inputPassword));
            } catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        loginPanel.add(loginB);

        //signup button
        JButton signupB = new JButton("Sign up");
        signupB.setFont(new Font("", Font.PLAIN,15));
        signupB.setBounds(700,500,135,50);
        signupB.addActionListener((e)->{
            try {
                queue.put(new SignupMessage());
            }catch (InterruptedException p) {
                p.printStackTrace();
            }

        });
        loginPanel.add(signupB);


        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.blue);
        loginPanel.setVisible(true);

        add(loginPanel);
        this.setVisible(true);
        this.setBounds(20,30,1400,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void updateLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
        repaint();
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
