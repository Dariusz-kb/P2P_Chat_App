import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	
	int port;
	ServerSocket serverSocket;
	Socket clientSocket;
	// list to store connected clients 
	// list stores socket objects
	public static ArrayList<Socket> clients = new ArrayList<Socket>();

	// constructor of a class server takes one parameter serverport
	public Server(int serverport) {
		this.port = serverport;
		}
	// return list of connected sockets
	public List<Socket> getClients(){
		return clients;
	}

	
	 @Override
	public void run() {
        try {
        	// create server listening socket on specified port and start listening
        	serverSocket = new ServerSocket(port);
		    while(true) {
		    	
		    	// accept connection from client
		       clientSocket = serverSocket.accept();
		       System.out.println("Accepted connection from " + clientSocket);
		       // add client socket to the list of sockets
		       clients.add(clientSocket);
		       // print out list of connected sockets
		       System.out.println(clients);
		       // instantiate client handler to deal with individual clients
		         ClientHandler client = new ClientHandler(this, clientSocket);
		          // start client thread
		           client.start();
		      
		    }
		    // catch exceptions
		        } catch (IOException e) {
		        	System.out.println(e);
		        	System.out.println(clientSocket);
		        }
        		// if something goes wrong try to close socket
        			try {
        				clientSocket.close();
        			
					} catch (IOException e) {
						e.printStackTrace();
					} 
        
        }}

        
		    
	
	


