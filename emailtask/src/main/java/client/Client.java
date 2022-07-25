package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.Scanner;

import model.Email;

public class Client {
public static void main (String args[])throws IOException{
		
		@SuppressWarnings("resource")
		
		
		Email email=new Email("wafagha3@gmail.com","wafagha3@gmail.com","hello,there,wafa");
		String msg2=email.toString();
		
		
		BufferedOutputStream os;
		DataOutputStream oos = null;
		Scanner in=new Scanner(System.in);
		String temp=null;
		Socket s = new Socket("localhost",5000);
		// ------------------ send emails until the client enteres exit-----------------
		while(true) {
			
			os = new BufferedOutputStream(s.getOutputStream());
			 oos =new DataOutputStream(os);
	    System.out.println("enter recipent email, your email,email body:");
	    System.out.println("enter 'exit' to exit");
	   //----------------------read user input------------------------------
	    temp=in.nextLine();
	    email.setMailto(temp);  // Read user input
	    if(email.getMailto().equals("exit")) {
	    	System.out.println("exit");
	    	oos.writeUTF("exit");
	    	break;
	    }
	    email.setMailfrom(in.nextLine());
	    email.setMailbody(in.nextLine());
	    msg2=email.toString();

		//-----------------------------------------------------------------------------
		System.out.println("From Client  " +msg2);
		//------ send the messeage to the server, close the connection and open new one---------
		 
		oos.writeUTF(msg2);
		oos.flush();
		
		s.close();
		s = new Socket("localhost",5000);
		//----------------------------------------------------------------------------------
		}
		//--------------------client exited,clear and close the buffers and socket-----------------------------------------
		oos.flush();
		in.close();
		s.close();
		
		
		
	}
}
