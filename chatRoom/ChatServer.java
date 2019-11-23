import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.concurrent.*;


public class ChatServer {
    // base on our chat room protocol
    public static final int PORT = 1337;
    // construct a thread pool for concurrency
    private static final Executor executor = Executors.newCachedThreadPool();
    
    
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        //  maintain a dictionary of all socket connections in the chatroom
        HashMap<String, String> username_socket_HashMap = new HashMap<String, String>();
        try {
            // establish the socket
            serverSocket = new ServerSocket(PORT);
            
            while (true){
                // now listen for connections and service the connection in a separate thread.
                Runnable chatConnectRunnable = new ChatServerConnection(serverSocket.accept());
                executor.execute(chatConnectRunnable);
                username_socket_HashMap.put(serverSocket.getLocalSocketAddress(), );
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