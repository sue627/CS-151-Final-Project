package view;

import control.*;
import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.BlockingDeque;

public class DetailView extends JFrame{
    private Account account;
    private JPanel detailPanel;
    private BlockingDeque<Message> queue;

    public DetailView(Account account, BlockingDeque<Message> queue) {
        this.queue = queue;
        this.account = account;
        detailPanel = new JPanel() {
            public void paintComponent( Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                //rectangle frame for balance
                g2.setColor(Color.darkGray);
                g2.setStroke(new BasicStroke(3));
                RoundRectangle2D.Double rect = new RoundRectangle2D.Double(70,100,400,280,30,30);
                g2.draw(rect);

                //Account type string
                g2.setColor(Color.black);
                g2.setFont(new Font("", Font.PLAIN, 25));
                g2.drawString(account.getType() + " Total:",90,140);

                //Balance String
                g2.setFont(new Font("", Font.BOLD, 50));
                g2.drawString("$" + String.valueOf(account.getBalance()), 130, 270);

                //"account details" string
                g2.setFont(new Font("", Font.PLAIN, 25));
                g2.drawString(account.getType() + " account details", 650, 140);

                //account number
                g2.drawString(account.accountNumber(),1000,207);

                //money
                g2.drawString(String.valueOf(account.getBalance()), 1000, 257);

                //last statement date
                g2.drawString("11/6/2020",1000,307);

                //rectangle frame for transaction
                g2.setColor(Color.darkGray);
                g2.setStroke(new BasicStroke(3));
                RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(70,470,1260,50,30,30);
                g2.draw(rect1);

                //rectangle for transcation history
                g2.setColor(Color.white);
                RoundRectangle2D.Double rect3 = new RoundRectangle2D.Double(70,521,1260,180,30,30);
                g2.fill(rect3);
                g2.setColor(Color.darkGray);
                RoundRectangle2D.Double rect2 = new RoundRectangle2D.Double(70,521,1260,180,30,30);
                g2.draw(rect2);

                //first transaction history
                g2.setColor(Color.black);
                g2.setFont(new Font("", Font.PLAIN,25));
                if(account.getTransactions().size() == 0) {
                    g2.drawString("No transactions yet", 595, 596);
                } else {
                    g2.drawString(account.getTransactions().get(0).getTransactionInfo(), 90, 570);
                    g2.drawString(account.getTransactions().get(0).getTime(), 90, 630);
                    g2.drawString(String.valueOf(account.getTransactions().get(0).getCurrentBalance()), 90, 670);
                    g2.setFont(new Font("", Font.PLAIN,40));
                    if(account.getTransactions().get(0).getType().equals("Deposit") || account.getTransactions().get(0).getType().equals("TransferIn")) {
                        g2.setColor(Color.green);
                        g2.drawString("+$" + String.valueOf(account.getTransactions().get(0).getAmount()),980, 608);
                    } else if(account.getTransactions().get(0).getType().equals("Withdraw") || account.getTransactions().get(0).getType().equals("TransferOut")) {
                        g2.setColor(Color.red);
                        g2.drawString("-$" + String.valueOf(account.getTransactions().get(0).getAmount()),980, 608);
                    }
                }


            }
        };

        //MyAccount button
        JButton myAccountB = new JButton("MyAccount");
        myAccountB.setFont(new Font("", Font.PLAIN, 15));
        myAccountB.setBounds(70, 10, 150, 35);
        myAccountB.setBorderPainted(false);
        myAccountB.setContentAreaFilled(false);
        myAccountB.addActionListener((e)->{
            try{
                String phoneNumber = account.accountNumber().substring(0, account.accountNumber().length() - 1);
                System.out.println(phoneNumber);
                queue.put(new MyAccountMessage(phoneNumber));
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(myAccountB);

        //logout button
        JButton logoutB = new JButton("Logout");
        logoutB.setFont(new Font("", Font.PLAIN, 15));
        logoutB.setBounds(1230,10,130,35);
        logoutB.setContentAreaFilled(false);
        logoutB.setBorderPainted(false);
        logoutB.addActionListener((e)->{
            try{
                queue.put(new LogoutMessage("DetailView"));
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(logoutB);

        //Account number JLabel
        JLabel acNumber = new JLabel("Account number:");
        acNumber.setFont(new Font("", Font.PLAIN, 25));
        acNumber.setBounds(650, 180, 230, 30);
        detailPanel.add(acNumber);

        //current balance JLabel
        JLabel currentBalanceL = new JLabel("Current balance:");
        currentBalanceL.setFont(new Font("", Font.PLAIN, 25));
        currentBalanceL.setBounds(650, 230, 230,30);
        detailPanel.add(currentBalanceL);

        //last statement date JLabel
        JLabel lastStDateL = new JLabel("Last statement date:");
        lastStDateL.setFont(new Font("", Font.PLAIN, 25));
        lastStDateL.setBounds(650, 280, 330, 30);
        detailPanel.add(lastStDateL);

        //pay button
        JButton payB = new JButton("Pay");
        payB.setFont(new Font("", Font.PLAIN, 25));
        payB.setBounds(85, 400, 330, 50);
        payB.addActionListener((e)->{
            try{
                queue.put(new PayMessage());
            } catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(payB);

        //transfer button
        JButton transferB = new JButton("Transfer");
        transferB.setFont(new Font("", Font.PLAIN, 25));
        transferB.setBounds(540, 400, 330, 50);
        transferB.addActionListener((e)->{
            try{
                queue.put(new TransferMessage(account));
            }catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(transferB);

        //Deposit checks button
        JButton depositCheckB = new JButton("Deposit checks");
        depositCheckB.setFont(new Font("", Font.PLAIN, 25));
        depositCheckB.setBounds(995,400,330,50);
        depositCheckB.addActionListener((e)->{
            try{
                queue.put(new DepositMessage());
            } catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(depositCheckB);

        //transactions history label
        JLabel tranHistL = new JLabel("Transaction history");
        tranHistL.setFont(new Font("", Font.PLAIN, 25));
        tranHistL.setBounds(595, 470, 300, 50);
        detailPanel.add(tranHistL);

        //show all button
        JButton showAllB = new JButton("Show all");
        showAllB.setFont(new Font("", Font.PLAIN, 15));
        showAllB.setBounds(650,698,100,30);
        showAllB.setContentAreaFilled(false);
        showAllB.addActionListener((e)->{
            try{
                queue.put(new ShowAllMessage(account));
            } catch(InterruptedException p) {
                p.printStackTrace();
            }
        });
        detailPanel.add(showAllB);

        detailPanel.setLayout(null);
        detailPanel.setVisible(true);
        detailPanel.setBackground(Color.blue);
        add(detailPanel);
        this.setVisible(true);
        this.setBounds(20,30,1400,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
