import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;


public class ChatServer {
    // base on our chat room protocol
    public static final int PORT = 1337;
    // construct a thread pool for concurrency
    private static final Executor executor = Executors.newCachedThreadPool();
    //  maintain a dictionary of all socket connections in the chatroom
    
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        HashMap<String, String> username_socket_HashMap = new HashMap<String, String>();
        ArrayList<String> usernameList = new ArrayList<String>();
        
        try {
            // establish the socket
            serverSocket = new ServerSocket(PORT);
            
            
            while (true){
                // now listen for connections and service the connection in a separate thread.
                Runnable chatConnectRunnable = new ChatServerConnection(serverSocket.accept(), username_socket_HashMap, usernameList);
                executor.execute(chatConnectRunnable);
            }
        }
        catch(IOException ioe){
            System.err.println(ioe);
            ioe.printStackTrace();
        }
        finally{
            if (serverSocket != null){
                try {
                    serverSocket.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }


        }

    }





}