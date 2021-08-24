package view;

import control.AddAccountMessage;
import control.DetailMessage;
import control.LogoutMessage;
import control.Message;
import model.Account;
import util.AccountUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;


public class AccountsView extends JFrame {
    private String phoneNumber;
    private ArrayList<Account> relatedAccounts;
    private JPanel accountPanel;
    private BlockingDeque<Message> queue;


    public AccountsView(String phoneNumber, BlockingDeque<Message> queue, ArrayList<Account> relatedAccounts) {
        this.phoneNumber = phoneNumber;
        this.queue = queue;
        this.relatedAccounts = relatedAccounts;
        accountPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if(relatedAccounts.size() == 0) {
                    g2.setColor(Color.black);
                    g2.setFont(new Font("", Font.PLAIN, 20));
                    g2.drawString("Please add an account!", 580, 380);
                } else if(relatedAccounts.size() == 1) {
                    String type = AccountUtil.checkAccountType(relatedAccounts.get(0));
                    System.out.println(type);
                    String showType = null;
                    if(type.equals("Checking")) {
                        showType = "Checking Account:";
                    }
                    if(type.equals("Saving")) {
                        showType = "Saving Account:";
                    }
                    if(type.equals("Credit")) {
                        showType = "Credit Account:";
                    }
                    //draw rectangle
                    g2.setColor(Color.white);
                    RoundRectangle2D.Double rect = new RoundRectangle2D.Double(515,300,350,230,30,30);
                    g2.fill(rect);

                    //"account type" string
                    g2.setColor(Color.black);
                    g2.setFont(new Font("", Font.PLAIN, 25));
                    g2.drawString(showType, 535, 340);

                    //"balance" string
                    g2.setFont(new Font("", Font.PLAIN, 15));
                    g2.drawString("Balance:", 545, 450);

                    //Show money
                    String money = String.valueOf(relatedAccounts.get(0).getBalance());
                    g2.setFont(new Font("", Font.PLAIN, 40));
                    g2.drawString(money,620, 480);


                } else if(relatedAccounts.size() == 2) {
                    String checkingMoney = null;
                    String savingMoney = null;
                    String creditMoney = null;

                    for(Account a : relatedAccounts) {
                        if(AccountUtil.checkAccountType(a).equals("Checking")) {
                            checkingMoney = String.valueOf(a.getBalance());
                        }
                        if(AccountUtil.checkAccountType(a).equals("Saving")) {
                            savingMoney = String.valueOf(a.getBalance());
                        }
                        if(AccountUtil.checkAccountType(a).equals("Credit")) {
                            creditMoney = String.valueOf(a.getBalance());
                        }
                    }

                    g2.setColor(Color.white);

                    //rectangles for two accounts
                    RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(275,300,350,230,30,30);
                    RoundRectangle2D.Double rect2 = new RoundRectangle2D.Double(725,300,350,230,30,30);
                    g2.fill(rect1);
                    g2.fill(rect2);


                    g2.setColor(Color.black);
                    if(checkingMoney == null) {
                        g2.setFont(new Font("", Font.PLAIN, 25));
                        g2.drawString("Saving account:", 295,340);
                        g2.drawString("Credit account:", 745, 340);

                        g2.setFont(new Font("", Font.PLAIN, 15));
                        g2.drawString("Balance:",305,450);
                        g2.drawString("Balance:",755,450);

                        g2.setFont(new Font("", Font.PLAIN, 40));
                        g2.drawString(savingMoney,370,480);
                        g2.drawString(creditMoney, 820, 480);
                    } else if(savingMoney == null) {
                        g2.setFont(new Font("", Font.PLAIN, 25));
                        g2.drawString("Checking account:", 295,340);
                        g2.drawString("Credit account:", 745, 340);

                        g2.setFont(new Font("", Font.PLAIN, 15));
                        g2.drawString("Balance:",305,450);
                        g2.drawString("Balance:",755,450);

                        g2.setFont(new Font("", Font.PLAIN, 40));
                        g2.drawString(checkingMoney,370,480);
                        g2.drawString(creditMoney, 820, 480);
                    } else if(creditMoney == null) {
                        g2.setFont(new Font("", Font.PLAIN, 25));
                        g2.drawString("Checking account:", 295,340);
                        g2.drawString("Saving account:", 745, 340);

                        g2.setFont(new Font("", Font.PLAIN, 15));
                        g2.drawString("Balance:",305,450);
                        g2.drawString("Balance:",755,450);

                        g2.setFont(new Font("", Font.PLAIN, 40));
                        g2.drawString(checkingMoney,370,480);
                        g2.drawString(savingMoney, 820, 480);
                    }


                } else if(relatedAccounts.size() == 3) {
                    String checkingMoney = null;
                    String savingMoney = null;
                    String creditMoney = null;

                    for(Account a : relatedAccounts) {
                        if(AccountUtil.checkAccountType(a).equals("Checking")) {
                            checkingMoney = String.valueOf(a.getBalance());
                        }
                        if(AccountUtil.checkAccountType(a).equals("Saving")) {
                            savingMoney = String.valueOf(a.getBalance());
                        }
                        if(AccountUtil.checkAccountType(a).equals("Credit")) {
                            creditMoney = String.valueOf(a.getBalance());
                        }
                    }


                    g2.setColor(Color.white);

                    //3 rectangles for each account
                    RoundRectangle2D.Double checkingRect = new RoundRectangle2D.Double(100,300,350,230,30,30);
                    RoundRectangle2D.Double savingRect = new RoundRectangle2D.Double(515,300,350,230,30,30);
                    RoundRectangle2D.Double creditRect = new RoundRectangle2D.Double(930,300,350,230,30,30);
                    g2.fill(checkingRect);
                    g2.fill(savingRect);
                    g2.fill(creditRect);

                    //"Checking account" string
                    g2.setColor(Color.black);
                    g2.setFont(new Font("", Font.PLAIN, 25));
                    g2.drawString("Checking account:", 120, 340);

                    //"Saving account" string
                    g2.drawString("Saving account:",535, 340);

                    //"Credit account" string
                    g2.drawString("Credit account", 950, 340);

                    //"Balance" string for 3 accounts
                    g2.setFont(new Font("", Font.PLAIN, 15));
                    g2.drawString("Balance:",130, 450);
                    g2.drawString("Balance:",545, 450);
                    g2.drawString("Balance:", 960, 450);

                    //Show money
                    g2.setFont(new Font("", Font.PLAIN, 40));
                    g2.drawString(checkingMoney,195, 480);
                    g2.drawString(savingMoney, 610, 480);
                    g2.drawString(creditMoney, 1025, 480);

                }



            }
        };

        //logout button
        JButton logoutB = new JButton("Logout");
        logoutB.setFont(new Font("", Font.PLAIN, 15));
        logoutB.setBounds(1230,10,130,35);
        logoutB.setContentAreaFilled(false);
        logoutB.setBorderPainted(false);
        logoutB.addActionListener((e)->{
            try{
                queue.put(new LogoutMessage("AccountsView"));
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        });
        accountPanel.add(logoutB);

        //welcome statement
        JLabel welcomeL = new JLabel("Welcome, user " + phoneNumber);
        welcomeL.setFont(new Font("", Font.PLAIN, 20));
        welcomeL.setBounds(560,120,500,30);
        accountPanel.add(welcomeL);

        //"accounts:" label
        JLabel accountsL = new JLabel("Accounts:");
        accountsL.setFont(new Font("", Font.PLAIN, 20));
        accountsL.setBounds(640, 160,100,30);
        accountPanel.add(accountsL);

        //"add account button"
        JButton addAccountB = new JButton("+ add an account");
        addAccountB.setFont(new Font("", Font.PLAIN, 20));
        addAccountB.setBounds(590, 620,200,40);
        addAccountB.addActionListener((e)->{
            try{
                queue.put(new AddAccountMessage());
            } catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        accountPanel.add(addAccountB);

        //button to DetailView
        if(relatedAccounts.size() == 1) {
            JButton detailB = new JButton();
            detailB.setBounds(515,300,350,230);
            detailB.setContentAreaFilled(false);
            detailB.setBorderPainted(false);
            detailB.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(0)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB);
        } else if(relatedAccounts.size() == 2) {
            //button 1
            JButton detailB1 = new JButton();
            detailB1.setBorderPainted(false);
            detailB1.setContentAreaFilled(false);
            detailB1.setBounds(275,300,350,230);
            detailB1.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(0)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB1);

            //button 2
            JButton detailB2 = new JButton();
            detailB2.setBorderPainted(false);
            detailB2.setContentAreaFilled(false);
            detailB2.setBounds(725,300,350,230);
            detailB2.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(1)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB2);
        } else if(relatedAccounts.size() == 3) {
            //button 1
            JButton detailB1 = new JButton();
            detailB1.setBorderPainted(false);
            detailB1.setContentAreaFilled(false);
            detailB1.setBounds(100,300,350,230);
            detailB1.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(0)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB1);

            //button 2
            JButton detailB2 = new JButton();
            detailB2.setBorderPainted(false);
            detailB2.setContentAreaFilled(false);
            detailB2.setBounds(515,300,350,230);
            detailB2.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(1)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB2);

            //button 3
            JButton detailB3 = new JButton();
            detailB3.setBorderPainted(false);
            detailB3.setContentAreaFilled(false);
            detailB3.setBounds(930,300,350,230);
            detailB3.addActionListener((e)->{
                try {
                    queue.put(new DetailMessage(relatedAccounts.get(2)));
                } catch (InterruptedException p) {
                    p.printStackTrace();
                }
            });
            accountPanel.add(detailB3);
        }

        accountPanel.setBackground(Color.blue);
        accountPanel.setLayout(null);
        accountPanel.setVisible(true);
        this.add(accountPanel);
        this.setVisible(true);
        this.setBounds(20,30,1400,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
