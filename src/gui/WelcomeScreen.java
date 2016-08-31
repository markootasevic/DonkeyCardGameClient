package gui;


import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.DGame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marko
 */
public class WelcomeScreen extends javax.swing.JFrame {
    Socket soketZaKontrolu = null;
    PrintStream izlazniTokKaServeru = null;
    BufferedReader ulazniTokOdServera = null;
    ObjectInputStream objectInput = null;
    LinkedList<DGame> roomList = null;

    /**
     * Creates new form WelcomeScreen
     */
    public WelcomeScreen(Socket soketParam) {
        try {
            initComponents();
            soketZaKontrolu = soketParam;
            izlazniTokKaServeru = new PrintStream(soketZaKontrolu.getOutputStream());
            ulazniTokOdServera = new BufferedReader(new InputStreamReader(soketZaKontrolu.getInputStream()));

            try {

                objectInput = new ObjectInputStream(soketZaKontrolu.getInputStream());
                Object list = objectInput.readObject();

                if (list instanceof LinkedList<?>) {
                    roomList = (LinkedList<DGame>) list;
//                    System.out.println(roomList.get(0).getName());
                    for (int i = 0; i < roomList.size(); i++) {
                        roomCBox.addItem(roomList.get(i).getName());

                    }

                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    objectInput.close();
                } catch (IOException ex) {
                    Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ooops something went wrong,try connecting again", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            new ServerConnect().setVisible(true);
            this.dispose();
//            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public WelcomeScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        playerNameTf = new javax.swing.JTextField();
        nameNotEntered = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        roomCBox = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        customRoomName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        customRoomPassword = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        customRoomRobots = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/index.jpg")));
        setResizable(false);

        jLabel2.setText("Enter your name");

        playerNameTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerNameTfActionPerformed(evt);
            }
        });

        nameNotEntered.setForeground(new java.awt.Color(255, 0, 0));
        nameNotEntered.setText("You have not entered a name");
        nameNotEntered.setEnabled(false);

        jButton1.setText("Quick play");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Play selected room");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Room name");

        jLabel4.setText("Room password ");

        jLabel5.setText("(leave blank for no password)");

        jLabel6.setText("Number of robots");

        customRoomRobots.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3" }));

        jButton3.setText("Create room and play");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Refresh room list");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(customRoomPassword))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(29, 29, 29)
                                        .addComponent(customRoomName, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(216, 216, 216)
                                        .addComponent(jLabel6)
                                        .addGap(41, 41, 41)
                                        .addComponent(customRoomRobots, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(207, 207, 207)
                                        .addComponent(jButton3)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerNameTf)
                    .addComponent(nameNotEntered, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(285, 285, 285)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(80, 80, 80)
                        .addComponent(jButton2))
                    .addComponent(roomCBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerNameTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameNotEntered)
                    .addComponent(roomCBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(customRoomName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(customRoomPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(customRoomRobots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playerNameTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerNameTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playerNameTfActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nameNotEntered.setEnabled(false);
        String playerName = playerNameTf.getText();
        if (playerName.equals("") || playerName == null) {
            nameNotEntered.setEnabled(true);
            return;
        }
        izlazniTokKaServeru.println(playerName);
        izlazniTokKaServeru.println("quickgame");
        GameWindow gw = new GameWindow(soketZaKontrolu, playerName);
        gw.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        izlazniTokKaServeru.println("refresh");
        Object list = null;
        try {
            list = objectInput.readObject();
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (list instanceof LinkedList<?>) {
            LinkedList<DGame> roomList = (LinkedList<DGame>) list;
//                    System.out.println(roomList.get(0).getName());
            for (int i = 0; i < roomList.size(); i++) {
                roomCBox.addItem(roomList.get(i).getName());

            }

        } else {
            JOptionPane.showMessageDialog(this, "Ooops something went wrong,please try again", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        nameNotEntered.setEnabled(false);
        String playerName = playerNameTf.getText();
        if (playerName.equals("") || playerName == null) {
            nameNotEntered.setEnabled(true);
            return;
        }
        izlazniTokKaServeru.println(playerName);
        izlazniTokKaServeru.println("newGameRoom");
        izlazniTokKaServeru.println(customRoomName.getText());
        izlazniTokKaServeru.println(customRoomPassword.getPassword());
        izlazniTokKaServeru.println(customRoomRobots.getSelectedItem().toString());
        try {
            String available = (String) objectInput.readObject();
            if (available.equals("serverNameUsed")) {
                JOptionPane.showMessageDialog(this, "There is another room with that name,try another name", "ERROR",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            GameWindow gw = new GameWindow(soketZaKontrolu, playerName);
            gw.setVisible(true);
            this.dispose();

        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        nameNotEntered.setEnabled(false);
        String playerName = playerNameTf.getText();
        if (playerName.equals("") || playerName == null) {
            nameNotEntered.setEnabled(true);
            return;
        }
        String roomName = (String) roomCBox.getSelectedItem();
        if (roomList == null) {
            JOptionPane.showMessageDialog(this, "There are no room available,try quick play", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        DGame room = null;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getName().equals(roomName)) {
                room = roomList.get(i);
                break;
            }
        }
        if (!room.getPassword().equals("")) {
            String password = (String) JOptionPane.showInputDialog(this, "Enter room password");
            if (password.equals(room.getPassword())) {
                izlazniTokKaServeru.println(playerName);
                izlazniTokKaServeru.println("connectToGameRoom");
                izlazniTokKaServeru.println(room.getName());
                izlazniTokKaServeru.println(password);
                GameWindow gw = new GameWindow(soketZaKontrolu, playerName);
                gw.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "The password is incorect,please try again", "ERROR",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        izlazniTokKaServeru.println(playerName);
        izlazniTokKaServeru.println("connectToGameRoom");
        izlazniTokKaServeru.println(room.getName());
        izlazniTokKaServeru.println("");
        GameWindow gw = new GameWindow(soketZaKontrolu, playerName);
        gw.setVisible(true);
        this.dispose();


    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(WelcomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WelcomeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField customRoomName;
    private javax.swing.JPasswordField customRoomPassword;
    private javax.swing.JComboBox<String> customRoomRobots;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel nameNotEntered;
    private javax.swing.JTextField playerNameTf;
    private javax.swing.JComboBox<String> roomCBox;
    // End of variables declaration//GEN-END:variables
}
