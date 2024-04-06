
import java.io.IOException;
import java.net.Socket;

// this class handles the messages from user

public class message_Handler extends Thread{
	 private Socket           socket   = null;
	 private ChatClient       client   = null;


public message_Handler(ChatClient client, Socket clientSocket) {
	this.client = client;
	this.socket = clientSocket;
}

public Socket getsocket() {
	return socket;
}
public ChatClient getclient() {
	return client;
}

// run method to get entry from a user and display it on chat screen
// and broadcast messages to other users
public void run() {
	
	try {
		
        String line;
          while ((line = ChatClient.userEntry.readLine()) !=null) {
        	String msg = line;
        	ChatClient.messageArea.append(msg + "\n");
    		ChatClient.out.println(msg);
    		ChatClient.out.flush();
        }     
 }
	catch (Exception ex) 
	{
 ex.printStackTrace();
 	try {
 	  socket.close();
 		} 
 	catch (IOException e)
 	{
     e.printStackTrace();
 	}
	}
}
}
