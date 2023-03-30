/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multichatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author ADMIN
 */
public class ClientServer {
    Socket clientSocket;
    DataInputStream input;
    DataOutputStream output;
    String clientName;
    public ClientServer(){
    }
    public ClientServer(Socket socketServer){
        try {
            this.clientSocket = socketServer;
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
            clientName = input.readUTF();
            // Đọc tên được gửi từng socket client
        } catch (IOException ex) {
            System.out.println("Lỗi khởi tạo ClientHandler");
        }
    }
}
