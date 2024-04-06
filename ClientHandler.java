import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;


public class ClientHandler extends Thread {

	 private final Socket clientSocket;
	 private Server server;
	 private String msg = "";
	 public String chatName;
	 private BufferedReader in;
	 private PrintWriter out;
	 public List<Socket> clients;
	 public Socket current;
	 // list to store chat names of connected clients
	 private static HashSet<String> names = new HashSet<String>();
	
	// constructor of Client handler class takes 2 parameters
	// Server and client socket
	 
	 
	 
	public ClientHandler(Server server, Socket clientSocket) {
		 this.setServer(server);
		 this.clientSocket = clientSocket;
	        
	}
	// method to register client on chat with a unique name	
public void registerClient() {
// while loop to get user entry
	while (true) {
        try {
        	msg = in.readLine();
			chatName = msg;
        }
        catch (IOException e) {
			e.printStackTrace();
		}
        
        if (chatName == null) {
            return;
        }
        
        synchronized (names) {
        	// if list of names does not contain name add name to that list
            if (!names.contains(chatName)) {
                names.add(chatName);
             try {
            	 // acknowledge other connected clients that a new client has joined chat
					joined(chatName);	
             	 }
             // catch exceptions
              	catch (IOException e)
             		{
					e.printStackTrace();
             		} 
             // break from the loop
            break;
            }
            else {
            	out.println("Name already in use... try again...");
          		
          	}
          	 
          }
       }
	}

// method to remove chatter from list of socked and also remove its name from a name list
public void removeChaterName() throws IOException {
    // remove client socket from a socket list on server side
	Server.clients.remove(clientSocket);
	System.out.println("Socket removed from list");
	System.out.println(Server.clients);
	// remove its name from a name list
	names.remove(chatName);
	System.out.println("name removed from list");
	System.out.println(names);
	// send message to all connected chat users to notify them that client left chat
	String msg = chatName + "... has left chat ...\n " + getClientnameList();
	// for loop to iterate over socket list
	for (int i =1; i <= Server.clients.size(); i++) {
		Socket Temp = (Socket) Server.clients.get(i-1);
			if (!(current == Temp)) {
				PrintWriter Temp_out = new PrintWriter(Temp.getOutputStream());
					Temp_out.println(msg);
						Temp_out.flush();
			}
	}
}

// method to use if new client has joined chat
// it notify all connected users about new client has joined in
// and also send to all connected clients actual list of chat users
private void joined(String name) throws IOException {
	this.chatName = name;
	for (int i =1; i <= Server.clients.size(); i++) {
		Socket Temp = (Socket) Server.clients.get(i-1);
			PrintWriter Temp_out = new PrintWriter(Temp.getOutputStream());
				Temp_out.println(chatName + " Has joined chat room ..."  + getClientnameList());
				Temp_out.flush();
	  }
    }

// method to send msg to all clients on chat
private void send(String msg) throws IOException {
	
	for (int i =1; i <= Server.clients.size(); i++) {
		Socket Temp = (Socket) Server.clients.get(i-1);
			if (!(current == Temp)) {
				PrintWriter Temp_out = new PrintWriter(Temp.getOutputStream());
					Temp_out.println(chatName + ": " + msg);
						Temp_out.flush();
							System.out.println("Sent to " + clientSocket);
	   }
    }
}

// method to get list of names on chat 	
public String getClientnameList() { 		
	return "People in chat room : " + names;
}

// run method handles client connection
	@Override
		public void run() {
			
			 try {
				handleClient();
		        } 
			 catch (IOException e)
			 {
		        	System.out.println(e);
		        } catch (InterruptedException e) {
		        	System.out.println(e);
			} 
			 try {
			// if client closes connection close its socked
			// and remove that socket from list and also chat name from name list
			// with method removeChaterName()
			 clientSocket.close();
			 removeChaterName();
				
			 }
			 catch (IOException e) {
				 System.out.println(e);
			} 
		    }
	
	// method to handle clients in/out messages			
public void handleClient() throws IOException, InterruptedException {
		// Create character streams for the socket.	       
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		// register client first		
		registerClient();
		String line;
		// start of a while loop to listen for coming messages
		while((line = in.readLine()) != null) {
			current = clientSocket;
			 msg = line;
			 // broadcast received messages to all the connected clients
		    	send(msg);
		      }
	}
// getter and seter methods for server
public Server getServer() {
	return server;
}
public void setServer(Server server) {
	this.server = server;
}
}
