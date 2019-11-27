import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServerHandler {
    // Message limit 512 characters
    // Username limit 15 characters
    private static final int OK = 200;
    private static final int NOT_OK = 400;
    private static final int BAD_USERNAME = 420;
    private static final int INVALID_USERNAME = 421;

    HashMap<String, String> username_socket_HashMap = null;
    ArrayList<String> usernameList = null;
    private boolean left = false;
    private boolean lefted = false;
    
    public ChatServerHandler(HashMap<String, String> username_socket_HashMap, ArrayList<String> usernameList) {
        this.username_socket_HashMap = username_socket_HashMap;
        this.usernameList = usernameList;
    }

    // process is the main function for Handler class and contains other
    // functionality of the server
    public void process(Socket serverSocket) {
        try {
            while (lefted == false){
                String[] requested = this.streamParser(serverSocket);
                for (int i = 0; i < requested.length; i++) {
                    System.out.println(requested[i]);
                }
                this.functionReader(requested, serverSocket);
                // send status code 

                if (this.left == true)
                    this.lefted = true;
            }
        } 
        catch (IOException ioException) {
                ioException.printStackTrace();
        } 
        finally {
            try {
                serverSocket.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Command: Just the command
	// Source: The source username 
	// Destination: a single username on the server, or "all"
	// Time Sent: yyyy-mm-dd-hh-mm-ss UTC 
    // request first line syntax from the client side:
    // Command|Source|Destination|Time Sent + \r\n
    //    [0]   [1]      [2]        [3]   ->tokenStrings
    private String[] streamParser(Socket serverSocket) throws IOException {
        BufferedReader  streamClientReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        String requestLineFromClient = streamClientReader.readLine();
        String massegeFromClient = streamClientReader.readLine();

        String tokenStrings[] = requestLineFromClient.split("\\|");
        return tokenStrings;
    }

    private String functionReader(String[] clientStrings, Socket serverSocket){
        String clientFunction = clientStrings[0];
        int statusCode;
        if (clientFunction == "JOIN"){
            statusCode = this.join(clientStrings, serverSocket);
        }
        else if (clientFunction == "LEAV"){
            statusCode = this.leave(clientStrings);
            this.left = true;
        }
        else if (clientFunction == "PVMG"){
            this.privateMessage();
        }
        else if (clientFunction == "BDMG"){
            this.broadcastMessage();
        }
        else{}

        return "STAT|" + statusCode;

    }

    // updating the Hashmap from the out side class: ChatServer
    // 
    private int join(String[] clientStrings, Socket serverSocket){
        SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();
        String socketPair = serverSocket.toString() + ":" + localSocketAddress.toString();
        // check if the username already exist
        if (this.username_socket_HashMap.containsKey(clientStrings[1]) == false){
            // if is not then update the hashmap
            this.username_socket_HashMap.put(clientStrings[1], socketPair);
            // broadcast user joined the chat room
            this.broadcastMessage(clientStrings, clientStrings[1]+" has joined the chat.\r\n");
            // send 200 code back to the client
            return ChatServerHandler.OK;
        }
        else {
            // if is then send 420 code back to the client and close the socket connection.
            return ChatServerHandler.BAD_USERNAME;
        }

    }
    private int leave(String[] clientStrings){
        int statusCode; 
        
        this.broadcastMessage(clientStrings, clientStrings[1]+ ":" + clientStrings[1] + "has left the chat\r\n");
        statusCode = ChatServerHandler.OK;
        
        return statusCode;
    }
    private void privateMessage(String[] clientStrings){

    }
    private void broadcastMessage(String[] clientStrings, String message){

    }
    private void sendStatus(String[] clientStrings){

    }







}