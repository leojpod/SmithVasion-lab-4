/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ServerMain {
    
    private static ServerSocket serverSocket;
    private static int port = 11111;
    public static void main(String[] args) {
        
        try {
            serverSocket = new ServerSocket(port);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            Socket socket = null;
            try {
                //Wait for client connection
                socket = serverSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Create a new server thread which handle client connection (--> Fibonacci computation)
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }
    
    public int getPort() {
        return port;
    }
    
}
