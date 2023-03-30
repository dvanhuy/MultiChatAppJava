/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class ChatBoxClient extends javax.swing.JFrame {

    
    private Socket clientSocket;
    DataInputStream input;
    DataOutputStream output;
    String clientUserName;
    /**
     * Creates new form ChatBoxClient
     */
    public ChatBoxClient() {
        initComponents();
        clientUserName= "bot"+String.valueOf(Math.floor(Math.random()*100));
        
        listchatbox.setVerticalScrollBar(new ScrollBarCustom());
        listchatbox.getVerticalScrollBar().setValue(1);
        listchatbox.getVerticalScrollBar().setUnitIncrement(20);
        BoxLayout boxlayout = new BoxLayout(panelchatchox, BoxLayout.Y_AXIS);
        panelchatchox.setLayout(boxlayout);
        joinServer(6666);
        listenForMessage();
        try {
            output.writeUTF(clientUserName);
            output.flush();
        } catch (Exception e) {
            System.out.println("Lỗi lắng nghe ban đầu");
        }
    }
    
    public ChatBoxClient(String name) {
        initComponents();
        clientUserName= name;
        listchatbox.setVerticalScrollBar(new ScrollBarCustom());
        listchatbox.getVerticalScrollBar().setValue(1);
        listchatbox.getVerticalScrollBar().setUnitIncrement(20);
        BoxLayout boxlayout = new BoxLayout(panelchatchox, BoxLayout.Y_AXIS);
        panelchatchox.setLayout(boxlayout);
        
        joinServer(6666);
        listenForMessage();
        try {
            output.writeUTF(clientUserName);
            output.flush();
        } catch (Exception e) {
            System.out.println("Lỗi lắng nghe ban đầu");
        }
        
    }
    
    public void scrolldown(){
        listchatbox.revalidate(); //Update the scrollbar size
        listchatbox.getVerticalScrollBar().setValue(listchatbox.getVerticalScrollBar().getValue());
    }

    
    public void addChatReceive(String mess,String name){
        Message message = new Message(mess, name);
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(Color.white);
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(message,BorderLayout.WEST);
        panelchatchox.add(tempPanel);
        scrolldown();
    }
    
    public void addChatSend(String mess){
        Message message = new Message(mess, "Bạn");
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(Color.white);
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(message,BorderLayout.EAST);
        panelchatchox.add(tempPanel);
        scrolldown();
    }
    
    public void sendMessage(){
        try {
            String messsend = txtchatbox.getText();
            if (!messsend.isEmpty()){
                addChatSend(messsend);
                output.writeUTF(clientUserName+"###"+messsend);
                output.flush();
            }
        } catch (Exception e) {
            System.out.println("Lỗi tại sendMessage");
        }
    }
    
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String msgfromGroup;
                System.out.println("Đang nghe");
                while (clientSocket.isConnected()) {
                    try {
                        msgfromGroup= input.readUTF();
                        addChatReceive(msgfromGroup.substring( msgfromGroup.indexOf("###")+3, msgfromGroup.length()),msgfromGroup.substring(0, msgfromGroup.indexOf("###")));
                    } catch (Exception e) {
                        System.out.println("Lỗi listenForMessage");
                    }
                }
            }
            
        }).start();
    }
    
    public void joinServer(int host){
        try {
            clientSocket = new Socket("localhost",host);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Lỗi tại join client");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        listchatbox = new javax.swing.JScrollPane();
        panelchatchox = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtchatbox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        listchatbox.setBackground(new java.awt.Color(255, 51, 51));
        listchatbox.setBorder(null);
        listchatbox.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listchatbox.setMaximumSize(new java.awt.Dimension(666, 539));
        listchatbox.setMinimumSize(new java.awt.Dimension(666, 539));

        panelchatchox.setBackground(new java.awt.Color(255, 255, 255));
        panelchatchox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));

        javax.swing.GroupLayout panelchatchoxLayout = new javax.swing.GroupLayout(panelchatchox);
        panelchatchox.setLayout(panelchatchoxLayout);
        panelchatchoxLayout.setHorizontalGroup(
            panelchatchoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        panelchatchoxLayout.setVerticalGroup(
            panelchatchoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
        );

        listchatbox.setViewportView(panelchatchox);

        txtchatbox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtchatbox.setForeground(new java.awt.Color(102, 102, 102));
        txtchatbox.setText("Nhập tin nhắn ...");
        txtchatbox.setMargin(new java.awt.Insets(2, 10, 2, 2));
        txtchatbox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtchatboxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtchatboxFocusLost(evt);
            }
        });
        txtchatbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtchatboxActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plane.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtchatbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtchatbox))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listchatbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(listchatbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtchatboxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtchatboxFocusGained
        // TODO add your handling code here:
        if (txtchatbox.getText().equals("Nhập tin nhắn ...")){
            txtchatbox.setText("");
            txtchatbox.setForeground(Color.BLACK);

        }
    }//GEN-LAST:event_txtchatboxFocusGained

    private void txtchatboxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtchatboxFocusLost
        // TODO add your handling code here:
        if (txtchatbox.getText().equals("")){
            txtchatbox.setText("Nhập tin nhắn ...");
            txtchatbox.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_txtchatboxFocusLost

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        sendMessage();
        txtchatbox.setText("");
        
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txtchatboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtchatboxActionPerformed
        // TODO add your handling code here:
        sendMessage();
        txtchatbox.setText("");
    }//GEN-LAST:event_txtchatboxActionPerformed

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
            java.util.logging.Logger.getLogger(ChatBoxClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatBoxClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatBoxClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatBoxClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatBoxClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane listchatbox;
    private javax.swing.JPanel panelchatchox;
    private javax.swing.JTextField txtchatbox;
    // End of variables declaration//GEN-END:variables
}
