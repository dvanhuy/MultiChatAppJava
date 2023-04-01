/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package define;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author ADMIN
 */
public class ClientDefine {
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    String clientName;
    public ClientDefine(){
    }
    public ClientDefine(Socket socketServer){
        try {
            this.socket = socketServer;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            clientName = input.readUTF();
            // Đọc tên được gửi từng socket client
        } catch (IOException ex) {
            System.out.println("Lỗi khởi tạo ClientHandler");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    
}
