/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multichatapp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Server extends javax.swing.JFrame {
    ServerSocket serverSocket;
    ArrayList<ClientServer> clientList = new ArrayList<>();
    /**
     * Creates new form Server
     */
    public Server() {
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

        jLabel1 = new javax.swing.JLabel();
        openServer = new javax.swing.JButton();
        closeServer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SERVER");

        openServer.setText("Mở Server");
        openServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openServerActionPerformed(evt);
            }
        });

        closeServer.setText("Đóng server");
        closeServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openServer, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeServer, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(openServer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closeServer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void openServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openServerActionPerformed
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            this.serverSocket = serverSocket;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startServer();
                }
            }).start();
        } catch (Exception e) {
            System.out.println("Lỗi tại startServer");
        }
    }//GEN-LAST:event_openServerActionPerformed

    public void startServer(){
        System.out.println("           Server Start");
        System.out.println("----------------------------------");
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientServer clientServer = new ClientServer(socket);
                // in tại console của client
                broadcastMessage(clientServer.clientName+"###Chào mọi người tôi mới gia nhập phòng chat","");
                clientList.add(clientServer);
                // in tại console của server
                System.out.println(clientServer.clientName+" đã tham gia nhóm chat");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String mess;
                        while (clientServer.clientSocket.isConnected()) {
                            try {
                                mess = clientServer.input.readUTF();
                                System.out.println("Nhận tin từ client "+clientServer.clientName);
                                broadcastMessage(mess,clientServer.clientName);
                            } catch (Exception e) {
//                                closeEveryThing(clientSocket, bufferedReader, bufferedWriter);
                                break;
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            System.out.println("Lỗi cmn "+e);
        }
    }
    
    public void broadcastMessage(String message,String usernamesend){
        for (ClientServer client: clientList)
        {
            try{
                // không gửi cho người đã nhắn tin
                System.out.println(clientList.size());
                System.out.println(message);
                System.out.println(usernamesend);
                if(!client.clientName.equals(usernamesend)){
                    client.output.writeUTF(message);
                    client.output.flush();
                }
            }
            catch(Exception e){
                System.out.println("Lỗi tại broadcastMessage");
//                closeEveryThing(clientSocket,bufferedReader,bufferedWriter);
            }
        }
    }
    
    private void closeServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeServerActionPerformed

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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton openServer;
    // End of variables declaration//GEN-END:variables
}