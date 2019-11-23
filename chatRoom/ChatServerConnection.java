import java.net.*;


public class ChatServerConnection implements Runnable
{
	private Socket clientSocket;
	private static ChatServerHandler chatHandler = new ChatServerHandler();
	public ChatServerConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

    /**
     * This method runs in a separate thread.
     */
	public void run() {
		try {
			chatHandler.process(this.clientSocket);
		}
		finally{

		}
	}
}