import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends Thread {

	 String serverName = "localhost"; 
	 int serverport = 1234;
	 Socket Csocket;
	 BufferedReader in;
	 static PrintWriter out;
	 static BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
	 String ChatName = "";
	
	 JFrame frame = new JFrame("Chatter...");
	    JTextField textField = new JTextField(40);
	    static JTextArea messageArea = new JTextArea(8, 40);
	
	     
	public ChatClient (String serverName, int serverport){
		
		this.serverName = serverName;
		this.serverport = serverport;
	
		
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
            	String msg = textField.getText();
            	messageArea.append(msg + "\n");
                out.println(msg);
                textField.setText("");
            }
        });
	}
	// method to perform registration 
	public void register() throws IOException {
		
		String line = "";
		// dialog window to get user entry
		ChatName = JOptionPane.showInputDialog(frame, "Please enter name:","Screen name selection", JOptionPane.PLAIN_MESSAGE);
		out.println(ChatName);
        // get output from server
		line = in.readLine();
		// text area will append respond from server
		messageArea.append(line + "\n");
	}
	
	// method to perform connection to the server
   	public boolean connect() {
        try {
        	 Csocket = new Socket(serverName, 1234);
             System.out.println("connected to server...");
             in = new BufferedReader(new InputStreamReader(Csocket.getInputStream()));
             out = new PrintWriter(Csocket.getOutputStream(), true);
             //userEntry = new BufferedReader(new InputStreamReader(System.in));
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	   
   	// msg listener to listen for any coming messages from other chat users
	public void msgListener() throws IOException {
		try {
            String line;
            	while ((line = in.readLine()) != null) {
            		String msg = line;
            		messageArea.append(msg + "\n");
            	}     
			}
		catch (Exception ex) 
			{
         ex.printStackTrace();
         	try {
         		Csocket.close();
         		} 
         catch (IOException e)
         	{
             e.printStackTrace();
         	}
		}
	}
		
	// run method it connects to the server, performs registration and handles any messages 
	// coming from a user itself or other chat users
	@Override
	public void run() {
		
		try {
			// connect to server
			System.out.println("Trying to connect to localhost on port 1234...");
			connect();
			System.out.println("Registering...");
			// register chat name with server
			register();
			textField.setEditable(true);
			message_Handler message = new message_Handler(this, Csocket);
			// start thread to listen for user messages
			message.start();
			// while loop to listen for any coming messages from other users
			while(true) {
				msgListener();
			}
			
			        } 
            catch (IOException e) {
			e.printStackTrace();
		
		}
	}
}

