package Server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;

//Server side

public class Server implements Runnable {
    
    private BufferedReader clientRequest;
    private PrintWriter reply;
    private ServerSocket serverSocket;

    private Socket socket;
    private int fibonacciNumber = 1;
    
    public Server(Socket socket) {
        
        this.socket = socket;
     
    }
    
    //compute fibonacci number
    public static int fib(int n) {
        if (n < 2) {
           return n;
        }
        else {
           return fib(n-1)+fib(n-2);
        }
    }
    
    //Thread to handle client requests
    public void run() {
        
        for (int i=0; i<= fibonacciNumber; i++) {
            System.out.printf("fib de %d egal %d\n",i, fib(i));
        }

        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public InetAddress getInetClient() {
        return socket.getInetAddress();
    }
    
    public String getIPClient() {
        return socket.getInetAddress().getHostAddress();
    }
}
