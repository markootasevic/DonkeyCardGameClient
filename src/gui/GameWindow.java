package gui;

import donkeycardgameclient.GamplayThread;
import donkeycardgameclient.Stopwatch;
import donkeycardgameclient.Timer;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import server.Card;
import server.CardSymbol;
import server.DGame;
import server.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marko
 */
public class GameWindow extends javax.swing.JFrame {

    Socket soket = null;
    PrintStream izlazniTokKaServeru = null;
    String playerName = null;
    LinkedList<Player> playerList = new LinkedList<>();
    Player player1 = null;
    Player player2 = null;
    Player player3 = null;
    Card[] myCards = new Card[5];
    int twoOfClubsCounter = 0;
    Stopwatch stopwatch;
    Timer timer;

    /**
     * Creates new form GameWindow
     */
    public GameWindow() {
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeApp();
            }
        });
    }

    public GameWindow(Socket soketParam, String playerName) {
        try {
            initComponents();
            labelWaitForPlayers.setVisible(false);
            this.playerName = playerName;
            namePlayer.setText(playerName);
            soket = soketParam;
            izlazniTokKaServeru = new PrintStream(soket.getOutputStream());
            new GamplayThread(soket, this).start();
            dropPlayer1.setVisible(true);
            dropPlayer2.setVisible(true);
            dropPlayer3.setVisible(true);

            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    windowClosed(e);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeApp() {

        int opcija = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit this game", "Going away? :'(",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (opcija == JOptionPane.YES_OPTION) {
            System.exit(0);   // OVDE TREBA DA SE DORADI ONO STO TREBA DA SE DESI KAD IZADJE IZ APLIKACIJE
        }

    }

    // metode za komunikaciju sa GamplayThread klasom
    public void changePlayerStatus(LinkedList<Player> list) {
        playerList = list;
        if (playerList.size() < 4) {
            labelWaitForPlayers.setVisible(true);
        } else {
            labelWaitForPlayers.setVisible(false);
        }
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i) == null) {
                continue;
            }
            boolean exists = false;
            if (playerList.get(i).getPlayerName().equals(playerName)) {
                exists = true;
                donkeyPlayer.setText(playerList.get(i).getDonkeyLetters());
                continue;
            }
            if (playerList.get(i).getPlayerName().equals(player1.getPlayerName())) {
                player1 = playerList.get(i);
                exists = true;
                continue;
            }
            if (playerList.get(i).getPlayerName().equals(player2.getPlayerName())) {
                player2 = playerList.get(i);
                exists = true;
                continue;
            }
            if (playerList.get(i).getPlayerName().equals(player3.getPlayerName())) {
                player3 = playerList.get(i);
                exists = true;
                continue;
            }
            if (exists == false) {
                if (player1 == null) {
                    player1 = playerList.get(i);
                    continue;
                }
                if (player2 == null) {
                    player2 = playerList.get(i);
                    continue;
                }
                if (player3 == null) {
                    player3 = playerList.get(i);
                    continue;
                }
            }

        }
        if (player1 != null) {
            name1.setText(player1.getPlayerName());
            donkey1.setText(player1.getDonkeyLetters());
        }
        if (player2 != null) {
            name2.setText(player2.getPlayerName());
            donkey3.setText(player2.getDonkeyLetters());
        }
        if (player3 != null) {
            name3.setText(player3.getPlayerName());
            donkey3.setText(player3.getDonkeyLetters());
        }

        changeOponentsCardPictures();

    }

    public void changeOponentsCardPictures() {
        changeOnePlayerCardsPictures(player1, imgPlayer1);
        changeOnePlayerCardsPictures(player2, imgPlayer2);
        changeOnePlayerCardsPictures(player3, imgPlayer3);
    }

    public void changeOnePlayerCardsPictures(Player player, JLabel label) {
        if (player == null) {
            return;
        }
        if (player.isPossesionTwoOfClubs()) {
            if (player.getNumberOfCardsInHand() == 4) {
                String path = "/resources/4 cards 2 of clubs player 1.jpg";
                ImageIcon img = getImageIcon(path);
                label.setIcon(img);

            } else {
                String path = "/resources/5 cards 2 of clubs players 1.jpg";
                ImageIcon img = getImageIcon(path);
                label.setIcon(img);

            }
        } else if (player.getNumberOfCardsInHand() == 4) {
            String path = "/resources/4 cards back.jpg";
            ImageIcon img = getImageIcon(path);
            label.setIcon(img);

        } else {
            String path = "/resources/5 cards back players 1.jpg";
            ImageIcon img = getImageIcon(path);
            label.setIcon(img);
        }
    }

    public ImageIcon getImageIcon(String imagePath) {
        try {
            Image img = ImageIO.read(getClass().getResource(imagePath));
            ImageIcon icon = new ImageIcon(img);
            return icon;
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void getMyCards(Card[] cards) {
        myCards = cards;

    }

    public void receveCard(Card card) {
        enableButtons();
        int[] a = mostFrequentCard();
        int count = a[1];
        if (count == 4) {
            btnDropCards.setEnabled(true);
        }
        timer = new Timer(countdownLabel, this);
        timer.start();
        if (card.isItTwoOfClubs()) {
            twoOfClubsCounter = 0;
        }
        int index = 0;
        for (int i = 0; i < myCards.length; i++) {
            if (myCards[i] == null) {
                myCards[i] = card;
                index = i + 1;
                break;
            }
        }
        if (index == 0) {
            System.out.println("whoops sranje happened");
            return;
        }
        switch (index) {
            case 1:
                setImageToCardButton(btnCard1, card);
                break;
            case 2:
                setImageToCardButton(btnCard2, card);
                break;
            case 3:
                setImageToCardButton(btnCard3, card);
                break;
            case 4:
                setImageToCardButton(btnCard4, card);
                break;
            case 5:
                setImageToCardButton(btnCard5, card);
                break;
        }
    }

    public void setImageToCardButton(JButton btn, Card c) {
        String path = findCardImagePath(c);
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            ImageIcon icon = new ImageIcon(img);
            Image image = icon.getImage();
            // reduce by 50%
            image = image.getScaledInstance(image.getWidth(null) / 2, image.getHeight(null) / 2, Image.SCALE_SMOOTH);
            icon.setImage(image);

            btn.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String findCardImagePath(Card card) {
        String path = "/resources/";
        if (card.isItTwoOfClubs()) {
            path += "2 of clubs.jpg";
            return path;
        }
        switch (card.getCardNumber()) {
            case 1:
                path += "1.";
                path += getCardSymbolNumer(card);
                path += ".jpg";
                return path;
            case 12:
                path += "12.";
                path += getCardSymbolNumer(card);
                path += ".jpg";
                return path;
            case 13:
                path += "13.";
                path += getCardSymbolNumer(card);
                path += ".jpg";
                return path;
            case 14:
                path += "14.";
                path += getCardSymbolNumer(card);
                path += ".jpg";
                return path;
        }
        path += "1.";
        path += getCardSymbolNumer(card);
        path += ".jpg";
        return path;

    }

    public String getCardSymbolNumer(Card card) {
        String path = "";

        switch (card.getSymbolOfCard()) {
            case spades:
                path += "1";
                break;
            case clubs:
                path += "2";
                break;
            case diamonds:
                path += "3";
                break;
            case hearts:
                path += "4";
                break;
        }
        return path;
    }

    public boolean canIPassCard(int cardHandPosition) {
        if (myCards[cardHandPosition - 1].isItTwoOfClubs()) {
            if (twoOfClubsCounter == 3) {
                twoOfClubsCounter = 0;
                return true;
            }
            return false;
        }
        boolean IHaveTwoOfClubs = false;
        for (int i = 0; i < myCards.length; i++) {
            if (myCards[i].isItTwoOfClubs()) {
                IHaveTwoOfClubs = true;
            }
        }
        if (IHaveTwoOfClubs) {
            if (twoOfClubsCounter == 3) {
                twoOfClubsCounter = 0;
            } else {
                twoOfClubsCounter++;
            }
        }
        return true;
    }

    public void sendCard(int cardHandPosition, JButton btn) {
        String cardString = myCards[cardHandPosition - 1].getCardNumber() + "." + getCardSymbolNumer(myCards[cardHandPosition - 1]);
        izlazniTokKaServeru.println(cardString);
        btn.setIcon(null);
        myCards[cardHandPosition - 1] = null;

    }

    public void someoneDroppedCards(String playerName) {
        disableButtons();
        btnDropCards.setEnabled(true);
        if (player1.getPlayerName().equals(playerName)) {
            dropPlayer1.setVisible(true);
        }
        if (player2.getPlayerName().equals(playerName)) {
            dropPlayer2.setVisible(true);
        }
        if (player3.getPlayerName().equals(playerName)) {
            dropPlayer3.setVisible(true);
        }
        btnDropCards.setEnabled(true);
        stopwatch = new Stopwatch();
        stopwatch.start();
    }

    public void disableButtons() {
        btnCard1.setEnabled(false);
        btnCard2.setEnabled(false);
        btnCard3.setEnabled(false);
        btnCard4.setEnabled(false);
        btnCard5.setEnabled(false);

    }

    public void enableButtons() {
        if (myCards[0] != null) {
            btnCard1.setEnabled(true);
        }
        if (myCards[1] != null) {
            btnCard2.setEnabled(true);
        }
        if (myCards[2] != null) {
            btnCard3.setEnabled(true);
        }
        if (myCards[3] != null) {
            btnCard4.setEnabled(true);
        }
        if (myCards[4] != null) {
            btnCard5.setEnabled(true);
        }

    }

    public void sendCardFromTheRightButton(int i) {
        switch (i + 1) {
            case 1:
                sendCard(i + 1, btnCard1);
                break;
            case 2:
                sendCard(i + 1, btnCard2);
                break;
            case 3:
                sendCard(i + 1, btnCard3);
                break;
            case 4:
                sendCard(i + 1, btnCard3);
                break;
            case 5:
                sendCard(i + 1, btnCard3);
                break;
        }
    }

    public void punish() {
        disableButtons();
        for (int i = 0; i < myCards.length; i++) {
            if (myCards[i].isItTwoOfClubs()) {
                sendCardFromTheRightButton(i);
                return;
            }

        }
        int[] a = mostFrequentCard();
        int cardNumber = a[0];
        for (int i = 0; i < myCards.length; i++) {
            if (myCards[i].getCardNumber() == cardNumber) {
                sendCardFromTheRightButton(i);
            }
        }
    }

    public int[] mostFrequentCard() {
        int[] a = new int[2];
        int[] n = new int[]{1, 2, 1, 2};
        int maxKey = 0;
        int maxCounts = 0;

        int[] counts = new int[n.length];

        for (int i = 0; i < n.length; i++) {
            counts[n[i]]++;
            if (maxCounts < counts[n[i]]) {
                maxCounts = counts[n[i]];
                maxKey = n[i];
            }
        }
        a[0] = maxKey;
        a[1] = maxCounts;
        return a;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name1 = new javax.swing.JLabel();
        donkey1 = new javax.swing.JLabel();
        imgPlayer1 = new javax.swing.JLabel();
        donkey3 = new javax.swing.JLabel();
        name3 = new javax.swing.JLabel();
        donkey2 = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        imgPlayer3 = new javax.swing.JLabel();
        imgPlayer2 = new javax.swing.JLabel();
        btnCard1 = new javax.swing.JButton();
        btnCard3 = new javax.swing.JButton();
        btnCard4 = new javax.swing.JButton();
        btnCard5 = new javax.swing.JButton();
        btnCard2 = new javax.swing.JButton();
        namePlayer = new javax.swing.JLabel();
        donkeyPlayer = new javax.swing.JLabel();
        btnDropCards = new javax.swing.JButton();
        dropPlayer2 = new javax.swing.JLabel();
        dropPlayer1 = new javax.swing.JLabel();
        dropPlayer3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        countdownLabel = new javax.swing.JLabel();
        labelWaitForPlayers = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Donkey game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/index.jpg")));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        name1.setText("Name player 1");
        getContentPane().add(name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        donkey1.setText("Donkey 1");
        getContentPane().add(donkey1, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 34, -1, -1));

        imgPlayer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/5 cards 2 of clubs players 1.jpg"))); // NOI18N
        getContentPane().add(imgPlayer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 67, -1, -1));

        donkey3.setText("Donkey 3");
        getContentPane().add(donkey3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 61, -1, -1));

        name3.setText("Name player 3");
        getContentPane().add(name3, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 33, -1, -1));

        donkey2.setText("Donkey 2");
        getContentPane().add(donkey2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 61, -1, -1));

        name2.setText("Name player 2");
        getContentPane().add(name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 33, -1, -1));

        imgPlayer3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/5 cards back players 2,3.jpg"))); // NOI18N
        getContentPane().add(imgPlayer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 95, -1, -1));

        imgPlayer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/5 cards back players 2,3.jpg"))); // NOI18N
        getContentPane().add(imgPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 95, -1, -1));

        btnCard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCard1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 242, 58, 94));

        btnCard3.setText("jButton1");
        btnCard3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCard3ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCard3, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 242, -1, 94));

        btnCard4.setText("jButton1");
        btnCard4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCard4ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCard4, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 242, -1, 94));

        btnCard5.setText("jButton1");
        btnCard5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCard5ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCard5, new org.netbeans.lib.awtextra.AbsoluteConstraints(613, 242, -1, 94));

        btnCard2.setText("jButton1");
        btnCard2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCard2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCard2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 242, -1, 94));

        namePlayer.setText("Username");
        getContentPane().add(namePlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 354, -1, -1));

        donkeyPlayer.setText("Donkey player");
        getContentPane().add(donkeyPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 388, -1, -1));

        btnDropCards.setText("PUT DOWN YOUR CARDS");
        btnDropCards.setEnabled(false);
        btnDropCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropCardsActionPerformed(evt);
            }
        });
        getContentPane().add(btnDropCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 184, 208, -1));

        dropPlayer2.setForeground(new java.awt.Color(204, 0, 0));
        dropPlayer2.setText("Droped cars");
        getContentPane().add(dropPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 94, -1));

        dropPlayer1.setForeground(new java.awt.Color(204, 0, 0));
        dropPlayer1.setText("Droped cars");
        getContentPane().add(dropPlayer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 73, -1));

        dropPlayer3.setForeground(new java.awt.Color(204, 0, 0));
        dropPlayer3.setText("Droped cars");
        getContentPane().add(dropPlayer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 120, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Time to play");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 77, 115, -1));

        countdownLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        countdownLabel.setText("30");
        getContentPane().add(countdownLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(622, 113, -1, -1));

        labelWaitForPlayers.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelWaitForPlayers.setForeground(new java.awt.Color(204, 0, 0));
        labelWaitForPlayers.setText("Waiting for all players");
        getContentPane().add(labelWaitForPlayers, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 95, -1, 46));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDropCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropCardsActionPerformed
        stopwatch.stopTime();
        izlazniTokKaServeru.println(stopwatch.count());
    }//GEN-LAST:event_btnDropCardsActionPerformed

    private void btnCard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCard1ActionPerformed
        if (canIPassCard(1) == false) {
            JOptionPane.showMessageDialog(this, "You can't pass two of clubs yet, you can in" + (3 - twoOfClubsCounter) + "turns", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendCard(1, btnCard1);
        disableButtons();
        timer.kill();
    }//GEN-LAST:event_btnCard1ActionPerformed

    private void btnCard2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCard2ActionPerformed
        if (canIPassCard(2) == false) {
            JOptionPane.showMessageDialog(this, "You can't pass two of clubs yet, you can in" + (3 - twoOfClubsCounter) + "turns", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendCard(2, btnCard2);
        disableButtons();
        timer.kill();
    }//GEN-LAST:event_btnCard2ActionPerformed

    private void btnCard3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCard3ActionPerformed
        if (canIPassCard(3) == false) {
            JOptionPane.showMessageDialog(this, "You can't pass two of clubs yet, you can in" + (3 - twoOfClubsCounter) + "turns", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendCard(3, btnCard3);
        disableButtons();
        timer.kill();
    }//GEN-LAST:event_btnCard3ActionPerformed

    private void btnCard4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCard4ActionPerformed
        if (canIPassCard(4) == false) {
            JOptionPane.showMessageDialog(this, "You can't pass two of clubs yet, you can in" + (3 - twoOfClubsCounter) + "turns", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendCard(4, btnCard4);
        disableButtons();
        timer.kill();
    }//GEN-LAST:event_btnCard4ActionPerformed

    private void btnCard5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCard5ActionPerformed
        if (canIPassCard(5) == false) {
            JOptionPane.showMessageDialog(this, "You can't pass two of clubs yet, you can in" + (3 - twoOfClubsCounter) + "turns", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendCard(5, btnCard5);
        disableButtons();
        timer.kill();
    }//GEN-LAST:event_btnCard5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCard1;
    private javax.swing.JButton btnCard2;
    private javax.swing.JButton btnCard3;
    private javax.swing.JButton btnCard4;
    private javax.swing.JButton btnCard5;
    private javax.swing.JButton btnDropCards;
    private javax.swing.JLabel countdownLabel;
    private javax.swing.JLabel donkey1;
    private javax.swing.JLabel donkey2;
    private javax.swing.JLabel donkey3;
    private javax.swing.JLabel donkeyPlayer;
    private javax.swing.JLabel dropPlayer1;
    private javax.swing.JLabel dropPlayer2;
    private javax.swing.JLabel dropPlayer3;
    private javax.swing.JLabel imgPlayer1;
    private javax.swing.JLabel imgPlayer2;
    private javax.swing.JLabel imgPlayer3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelWaitForPlayers;
    private javax.swing.JLabel name1;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel name3;
    private javax.swing.JLabel namePlayer;
    // End of variables declaration//GEN-END:variables
}
