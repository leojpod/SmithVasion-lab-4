/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *ax
 * @author leojpod
 */
public class EchoTcpServer {
  private static final Logger logger = Logger.getLogger(EchoTcpServer.class.getName());
  public static final void main(String[] args) throws IOException {
    int port = (args.length > 0)? Integer.parseInt(args[1]): 9876;
    ServerSocket server = new ServerSocket(port);
    Socket socket;
    server.setSoTimeout(20000);
    logger.log(Level.INFO, "Starting echo server on port {0}", port);
    while ((socket = server.accept()) != null) {
      new EchoThread(socket).start();
    }
  }

  private static class EchoThread extends Thread {
    Socket socket;
    public EchoThread(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try {
        logger.log(Level.INFO, "new client handling thread");
        //Set up streams
        InputStream in = this.socket.getInputStream();
        OutputStream out = this.socket.getOutputStream();
        byte[] buff = new byte[1024];
        int bytesread;
        //read/write loop
        while ((bytesread = in.read(buff)) != -1) {
          out.write(buff, 0, bytesread);
        } out.flush();
        System.out.println("Client has left\n");
        socket.close();
        try {
          in.close();
        } catch (IOException ex) {
          Logger.getLogger(EchoTcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
      } catch (IOException ex) {
        Logger.getLogger(EchoTcpServer.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
}
