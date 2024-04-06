/**
 * When a client connects, the server requests to enter chat name.
 * After a client submits a unique name, the server adds the socket of client
 * to the list of connected sockets and also adds a chat name to the list of names.
 * All messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name.
 * when client disconnects the server removes its socket from a list of sockets
 * and chat name from list of names and acknowledges other chat users that client has left chat.
 */

public class ServerMain {
 
	// main class of server side of chat it starts server running on port 1234
	public static void main(String[] args) {
        int port = 1234;
        
        System.out.println(" Server running on port 1234 ");
        Server server = new Server(port);
        server.start();
    
    }
	
}
