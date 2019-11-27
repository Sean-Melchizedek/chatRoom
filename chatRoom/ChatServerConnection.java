import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ChatServerConnection implements Runnable
{
	private Socket serverSocket;
	private ChatServerHandler chatHandler = null;
	public ChatServerConnection(Socket serverSocket, HashMap<String,String> username_socket_HashMap, ArrayList<String> usernameList) {
		this.serverSocket = serverSocket;
		chatHandler = new ChatServerHandler(username_socket_HashMap, usernameList);
	}

    /**
     * This method runs in a separate thread.
     */
	public void run() {
		try {
			chatHandler.process(this.serverSocket);
		}
		finally{

		}
	}
}