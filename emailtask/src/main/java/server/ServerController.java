package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
	public static void main(String args[]) throws IOException {

		int i = 0;
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(5000);
			System.out.println("Server is Running");
			//--------server is up and reciving clients---
			while (true) {
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s);
				st.start();
				System.out.println("Client: "+i++);
			}
			

		} catch (Exception e) {
			System.out.println("error in server socket startup " + e.getStackTrace());
			
		}

	}
}
