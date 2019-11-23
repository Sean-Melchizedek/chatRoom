import java.net.*;
import java.io.*;

public class ChatTestClient{
    private static final int PORT = 1337;
    public static void main(String[] args) {
        BufferedReader streamServerReader = null;	
        PrintWriter streamServerWriter = null;
        Socket socketClient = null;
        String hostName = args[0];
        try {
            socketClient = new Socket(hostName,PORT);
            streamServerWriter = new PrintWriter(socketClient.getOutputStream(),true);
            String requset = "JOIN|haiming|all|current UTC time\r\nhaiming\r\n";
            streamServerWriter.write(requset);
            streamServerWriter.flush();
            streamServerWriter.close();
        } catch (IOException e) {
        
        }
        
    }


}