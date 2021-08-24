package view;

import javax.swing.*;

public class AddAccountView extends JFrame {
    private JPanel addAccountPanel;
    private int accountype = 0;
    private String phoneNumber;

    public AddAccountView(String phoneNumber) {
        addAccountPanel = new JPanel();
        this.phoneNumber = phoneNumber;


        JButton checkButton = new JButton("Checking");
        checkButton.setBounds(40, 95, 90,40);
        checkButton.addActionListener((e)->{
            accountype = 1;
        });
        addAccountPanel.add(checkButton);

        JButton savingButton = new JButton("Saving");
        savingButton.setBounds(150, 95, 90,40);
        savingButton.addActionListener((e)->{
            accountype = 2;
        });
        addAccountPanel.add(savingButton);

        JButton creditButton = new JButton("Credit");
        creditButton.setBounds(260, 95, 90,40);
        creditButton.addActionListener((e)->{
            accountype = 3;
        });
        addAccountPanel.add(creditButton);


        addAccountPanel.setVisible(true);
        addAccountPanel.setLayout(null);

        this.add(addAccountPanel);
        this.setBounds(500, 320, 400, 250);
        this.setVisible(true);
    }

    public int getAccountype() {
        return accountype;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
