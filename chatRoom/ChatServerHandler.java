import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServerHandler{
    // Message limit 512 characters
    // Username limit 15 characters
    public String username;

    private ArrayList<String> userText = new ArrayList<String>();

    // process is the main function for Handler class and contains other functionality of the server
    public void process(Socket clientSocket){
        try{
            String[] requested = this.streamParser(clientSocket);
            for (int i = 0; i < requested.length; i++){ 
                System.out.println(requested[i]);
            }
            this.functionReader(requested);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{

        }
    }

    // Command: Just the command
	// Source: The source username 
	// Destination: a single username on the server, or "all"
	// Time Sent: yyyy-mm-dd-hh-mm-ss UTC 
    // request first line syntax from the client side:
    // CommandSource|Destination|Time Sent + \r\n
    private String[] streamParser(Socket clientSocket) throws IOException {
        BufferedReader  streamClientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String requestLineFromClient = streamClientReader.readLine();
        String massegeFromClient = streamClientReader.readLine();

        String tokenStrings[] = requestLineFromClient.split("\\|");
        this.userText.add(massegeFromClient);
        return tokenStrings;
    }

    private void functionReader(String[] clientStrings){
        String clientFunction = clientStrings[0];

        if (clientFunction == "JOIN"){
            this.join();
        }
        else if (clientFunction == "LEAV"){
            this.leave();
        }
        else if (clientFunction == "PVMG"){
            this.privateMessage();
        }
        else if (clientFunction == "BDMG"){
            this.broadcastMessage();
        }
        else if (clientFunction == "STAT"){
            this.status();
        }
        else{}


    }

    private void join(){}
    private void leave(){}
    private void privateMessage(){}
    private void broadcastMessage(){}
    private void status(){}







}