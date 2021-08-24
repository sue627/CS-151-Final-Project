package view;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignupView extends JFrame {
    private JPanel signUpPanel;
    private String userName;
    private String password;
    private int signal = 0;

    public SignupView() {
        signUpPanel = new JPanel();

        JLabel enterUserName = new JLabel("Please enter username:");
        enterUserName.setBounds(40,40,150,30);
        signUpPanel.add(enterUserName);

        JLabel enterPassword = new JLabel("Please enter password:");
        enterPassword.setBounds(40,100,150,30);
        signUpPanel.add(enterPassword);

        JTextField usernameText = new JTextField();
        usernameText.setBounds(200,40, 150,30);
        signUpPanel.add(usernameText);

        JTextField passwordText = new JTextField();
        passwordText.setBounds(200,100, 150,30);
        signUpPanel.add(passwordText);

        JButton createB = new JButton("Create");
        createB.setBounds(90, 160, 70, 30);
        createB.addActionListener((e)->{
            userName = usernameText.getText();
            password = passwordText.getText();
            signal = 1;

        });
        signUpPanel.add(createB);

        JButton clearB = new JButton("Clear");
        clearB.setBounds(230, 160, 70, 30);
        clearB.addActionListener((e)->{
            usernameText.setText(null);
            passwordText.setText(null);
        });
        signUpPanel.add(clearB);

        signUpPanel.setLayout(null);
        signUpPanel.setVisible(true);

        this.add(signUpPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                signal = 2;
            }
        });
        this.setBounds(500, 320, 400, 250);
        this.setVisible(true);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getSignal() {
        return signal;
    }
}
