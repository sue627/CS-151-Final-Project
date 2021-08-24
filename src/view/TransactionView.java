package view;

import control.BackToDetailMessage;
import control.LogoutMessage;
import control.Message;
import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.BlockingDeque;

public class TransactionView extends JFrame {
    private JPanel transactionPanel;
    private JScrollPane scrollPane;
    private Account account;
    private BlockingDeque<Message> queue;
    private int panelHeight = 700;

    public TransactionView(Account account, BlockingDeque<Message> queue) {
        transactionPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                //rectangle frame for transaction
                g2.setColor(Color.darkGray);
                g2.setStroke(new BasicStroke(3));
                RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(70,100,1260,50,30,30);
                g2.draw(rect1);

                if(account.getTransactions().size() == 0) {
                    g2.setColor(Color.black);
                    g2.setFont(new Font("", Font.PLAIN,25));
                    g2.drawString("No transactions yet", 595, 230);
                } else {
                    for(int i = 0; i < account.getTransactions().size(); i++) {
                        //draw frame
                        g2.setColor(Color.darkGray);
                        RoundRectangle2D.Double grayFrame = new RoundRectangle2D.Double(70, 151+181*i,1260,180,30,30);
                        g2.draw(grayFrame);
                        g2.setColor(Color.white);
                        RoundRectangle2D.Double whiteRect = new RoundRectangle2D.Double(70, 151+181*i,1260,180,30,30);
                        g2.fill(whiteRect);

                        //draw information
                        g2.setColor(Color.black);
                        g2.setFont(new Font("", Font.PLAIN,25));
                        g2.drawString(account.getTransactions().get(i).getTransactionInfo(), 90, 200 + 181*i);
                        g2.drawString(account.getTransactions().get(i).getTime(), 90, 260 + 181*i);
                        g2.drawString(String.valueOf(account.getTransactions().get(i).getCurrentBalance()), 90, 300 + 181*i);
                        g2.setFont(new Font("", Font.PLAIN,40));
                        if(account.getTransactions().get(i).getType().equals("Deposit") || account.getTransactions().get(i).getType().equals("TransferIn")) {
                            g2.setColor(Color.green);
                            g2.drawString("+$" + String.valueOf(account.getTransactions().get(i).getAmount()),980, 238 + 181*i);
                        } else if(account.getTransactions().get(i).getType().equals("Withdraw") || account.getTransactions().get(i).getType().equals("TransferOut")) {
                            g2.setColor(Color.red);
                            g2.drawString("-$" + String.valueOf(account.getTransactions().get(i).getAmount()),980, 238 + 181*i);
                        }
                    }
                }

            }
        };

        if(account.getTransactions().size() > 3) {
            panelHeight = panelHeight + (account.getTransactions().size() - 3) * 200;
        }
        transactionPanel.setPreferredSize(new Dimension(1350,panelHeight));

        this.account = account;
        this.queue = queue;

        //back button
        JButton backB = new JButton("Back");
        backB.setFont(new Font("", Font.PLAIN,15));
        backB.setBounds(55, 10, 150, 35);
        backB.setBorderPainted(false);
        backB.addActionListener((e)->{
            try{
                queue.put(new BackToDetailMessage(account));
            }catch (InterruptedException p) {
                p.printStackTrace();
            }
        });
        transactionPanel.add(backB);

        //logout button
        JButton logoutB = new JButton("Logout");
        logoutB.setFont(new Font("", Font.PLAIN, 15));
        logoutB.setBounds(1230,10,130,35);
        logoutB.setContentAreaFilled(false);
        logoutB.setBorderPainted(false);
        logoutB.addActionListener((e)->{
            try{
                queue.put(new LogoutMessage("TransactionView"));
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        });
        transactionPanel.add(logoutB);

        //transactions history label
        JLabel tranHistL = new JLabel("Transaction history");
        tranHistL.setFont(new Font("", Font.PLAIN, 25));
        tranHistL.setBounds(595, 105, 300, 50);
        transactionPanel.add(tranHistL);


        transactionPanel.setVisible(true);
        transactionPanel.setLayout(null);
        transactionPanel.setBackground(Color.blue);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(transactionPanel);
        scrollPane.setBounds(300,300,300,300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVisible(true);

//        transactionPanel.add(scrollPane);
        this.setContentPane(scrollPane);
        this.setBounds(20,30,1400,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
