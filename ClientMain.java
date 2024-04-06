import javax.swing.JFrame;

//main class of client side of chat appllication
// Runs the client as an application with a closeable frame.
// It connects to local server on port 1234
// The client follows the Chat Protocol which is as follows.
// the server sends request to submit chat name the client replies with the
// desired screen name.  The server accepts name and adds it to name list.
// after running the ServerMain class run this class to start client
public class ClientMain {
	
	public static void main(String[] args) throws Exception {
        String serverName = "localhost";
		int serverport = 1234;
		
		System.out.println("Waiting for server...");
		ChatClient client = new ChatClient(serverName, serverport);
		 client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 client.frame.setVisible(true);
		 client.run();
        
    }
		
	}


	

