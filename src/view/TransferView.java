package view;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TransferView extends JFrame {
    private JPanel payPanel;
    private String targetAccount;
    private String payAmount;
    private int signal = 0;

    public TransferView() {
        payPanel = new JPanel();

        JLabel payTo = new JLabel("Transfer to:");
        payTo.setBounds(70,40,150,30);
        payPanel.add(payTo);

        JLabel amount = new JLabel("Amount:");
        amount.setBounds(70,100,150,30);
        payPanel.add(amount);

        JTextField usernameText = new JTextField();
        usernameText.setBounds(200,40, 150,30);
        payPanel.add(usernameText);

        JTextField passwordText = new JTextField();
        passwordText.setBounds(200,100, 150,30);
        payPanel.add(passwordText);

        JButton createB = new JButton("Pay");
        createB.setBounds(160, 160, 70, 30);
        createB.addActionListener((e)->{
            targetAccount = usernameText.getText();
            payAmount = passwordText.getText();
            signal = 1;
        });
        payPanel.add(createB);

        payPanel.setLayout(null);
        payPanel.setVisible(true);
        this.add(payPanel);
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

    public String getPayAmount() {
        return payAmount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public int getSignal() {
        return signal;
    }
}
