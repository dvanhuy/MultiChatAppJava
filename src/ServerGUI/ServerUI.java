/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerGUI;

import define.ClientDefine;
import define.ServerDefine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class ServerUI extends javax.swing.JFrame {

    ServerSocket serverRoot;
    ServerSocket serverRootCreateRoom;
    ArrayList<ServerDefine> listServer = new ArrayList<>();
    
    public ServerUI() {
        initComponents();
        try {
            serverRoot = new ServerSocket(10000);
        } catch (Exception e) {
            System.out.println("Không thể tạo server gốc");
        }
        
        // gửi danh sách server tới các client input
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    Socket socket;
                    try {
                        socket = serverRoot.accept();
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        String listRoom ="";
                        for (ServerDefine serverDefine : listServer) {
                            listRoom+=serverDefine.getServerSocket().getLocalPort()+",";
                        }
                        output.writeUTF(listRoom);
                        socket.close();
                    } catch (IOException ex) {
                        System.out.println("Lỗi tại Thread  accept");
                    }
                }
            }
        }).start();
        
        //nhận yêu cầu tạo server
        try {
            serverRootCreateRoom = new ServerSocket(10001);
        } catch (Exception e) {
            System.out.println("Không thể tạo serverRootCreateRoom");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    Socket socket;
                    try {
                        socket = serverRootCreateRoom.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        String mess = input.readUTF();
                        if (mess.contains("Server###")){
                            openNewRoom(Integer.parseInt(mess.substring(mess.indexOf("Server###")+9, mess.length())));
                            updateListRoom();
                        }
                        socket.close();
                    } catch (IOException ex) {
                        System.out.println("Lỗi tại Thread  accept");
                    }
                }
            }
        }).start();
    }
    
    // mở phòng mới
    public void openNewRoom(int port){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ServerDefine define = new ServerDefine(serverSocket);
            listServer.add(define);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initServer(define);
                }
            }).start();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Phòng đã tồn tại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //cập nhật danh sách phòng trên giao diện
    public void updateListRoom(){
        DefaultListModel<String> modelList = new DefaultListModel<>(); 
        for (ServerDefine serverDefine : listServer) {
            modelList.addElement(String.valueOf(serverDefine.getServerSocket().getLocalPort()));
        }
        listRoom.setModel(modelList);
    }
    
    // khởi tạo danh sách các server
    public void initServer(ServerDefine server){
        try {
            while (!server.getServerSocket().isClosed()){
                //Client nào accept sẽ được lấy theo từng phòng
                Socket socket = server.getServerSocket().accept();
                ClientDefine client = new ClientDefine(socket);
                // Gửi cho toàn bộ client khác trang room với cú pháp ten###mess
                broadcastMessage(client.getClientName()+"###Chào mọi người, tôi mới gia nhập phòng chat","",server);
                //thêm client vào server được tạo
                server.getClientList().add(client);
                
                // in tại console của server
                System.out.println(client.getClientName()+" đã tham gia nhóm chat");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String mess;
                        System.out.println("Đang chạy");
                        while (client.getSocket().isConnected()) {
                            try {
                                mess = client.getInput().readUTF();
                                System.out.println("Nhận tin từ client "+client.getClientName());
                                broadcastMessage(mess,client.getClientName(),server);
                            } catch (Exception e) {
                                System.out.println(client.getClientName()+" đã ròi khỏi nhóm chat");
                                server.getClientList().remove(client);
                                broadcastMessage(client.getClientName()+"###Đã rời khỏi phòng chat","",server);
                                break;
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            System.out.println("Lỗi initServer "+e);
        }
    }
    // phát tin nhắn nhận được sang các client khác cùng server
    public void broadcastMessage(String message,String usernamesend,ServerDefine server){
        for (ClientDefine client: server.getClientList())
        {
            try{
                // không gửi cho người đã nhắn tin
                if(!client.getClientName().equals(usernamesend)){
                    client.getOutput().writeUTF(message);
                    client.getOutput().flush();
                }
            }
            catch(Exception e){
                System.out.println("Lỗi tại broadcastMessage");
            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        listRoom = new javax.swing.JList<>();
        btRemoveRoom = new javax.swing.JButton();
        btAddRoom = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnumberclient = new javax.swing.JLabel();
        txtnumberid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listRoom.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listRoom.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRoomValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listRoom);

        btRemoveRoom.setText("Đóng phòng");
        btRemoveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveRoomActionPerformed(evt);
            }
        });

        btAddRoom.setText("Mở phòng");
        btAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btRemoveRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRemoveRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Thông tin chi tiết");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Số người trong phòng: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Mã phòng: ");

        txtnumberclient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnumberclient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtnumberclient.setText(" ");

        txtnumberid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnumberid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtnumberid.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnumberclient, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnumberid, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtnumberclient))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnumberid))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddRoomActionPerformed
        // TODO add your handling code here:
        String ma = JOptionPane.showInputDialog("Nhập mã phòng cần tạo");
        try {
            openNewRoom(Integer.parseInt(ma));
            updateListRoom();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập lại mã phòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btAddRoomActionPerformed

    private void btRemoveRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveRoomActionPerformed
        // TODO add your handling code here:
        // kiểm tra đã chọn số phòng hay chưa
        if (listRoom.getSelectedValue()==null){
            JOptionPane.showMessageDialog(this, "Chưa chọn phòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            // xóa phòng trong danh sách đã lưu
            ServerDefine serverDefinetemp = null;
            for (ServerDefine serverDefine : listServer) {
                if (serverDefine.getServerSocket().getLocalPort()==Integer.parseInt(listRoom.getSelectedValue())){
                    serverDefinetemp=serverDefine;
                }
            }
            try {
                serverDefinetemp.getServerSocket().close();
                for (ClientDefine cd :serverDefinetemp.getClientList()){
                    cd.getSocket().close();
                }
            } catch (IOException ex) {
                System.out.println("Lỗi tại serverDefinetemp.getServerSocket().close();");
            }
            listServer.remove(serverDefinetemp);
            updateListRoom();
        }
        
    }//GEN-LAST:event_btRemoveRoomActionPerformed

    private void listRoomValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRoomValueChanged
        // TODO add your handling code here:
        for (ServerDefine serverDefine : listServer) {
            if (serverDefine.getServerSocket().getLocalPort()==Integer.parseInt(listRoom.getSelectedValue())){
                txtnumberid.setText(String.valueOf(serverDefine.getServerSocket().getLocalPort()));
                txtnumberclient.setText(String.valueOf(serverDefine.getClientList().size()));
            }
        }
    }//GEN-LAST:event_listRoomValueChanged

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
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddRoom;
    private javax.swing.JButton btRemoveRoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listRoom;
    private javax.swing.JLabel txtnumberclient;
    private javax.swing.JLabel txtnumberid;
    // End of variables declaration//GEN-END:variables
}
