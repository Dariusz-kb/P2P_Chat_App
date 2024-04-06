# P2P_Chat_App
Peer to peer chat application using java

To run the chat app you must run the server code first.
You can start the Server by typing 
	java ServerMain
at the console prompt. That will execute it in console mode and the server will wait for connection on port 1234.
After the server is up and running on port 1234 you can start the client.
To do so you can type 
	Java ClientMain
This will start the client as an application with a closeable frame. It connects to local host server on port 1234. After connecting to the server chat user must choose a chat name to register with server. When name chosen by user is accepted by server user server will update user with the list of clients currently available on chat. When user is leaving the chat server informs other connected users and removes name and socket from the user list.
