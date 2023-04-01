/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package define;

import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class ServerDefine {
    ServerSocket serverSocket;
    ArrayList<ClientDefine> clientList = new ArrayList<>();

    public ServerDefine(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ArrayList<ClientDefine> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<ClientDefine> clientList) {
        this.clientList = clientList;
    }
    
}
