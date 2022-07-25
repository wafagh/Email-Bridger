package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import model.Email;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@SuppressWarnings("unused")
public class ServerThread extends Thread {

	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {

		BufferedInputStream in;
		DataInputStream ois;
		
		try {
			
			Session session = null;
			  Properties properties = System.getProperties();
			//------------get the input sent by the client------------------
			in = new BufferedInputStream(socket.getInputStream());
			ois = new DataInputStream(in);
			// System.out.println(in.readObject());
			String str = ois.readUTF();
			//-------------------if input equals exit close the streams and return
			if(str.equals("exit")) {
				System.out.println("client aborted");
				in.close();
				ois.close();
				return;
			}
			//----------------------------------------------------------------
			System.out.println("message recived : "+ str);
			in.close();
			ois.close();
			//--------------------- email preperation----------------
			String[] email2 = str.split(",");
			String mailto = email2[0];
			String mailfrom = email2[1];
			String body = email2[2];
			
			for(int i =3;i<email2.length-1;i++) {
				 body +=" "+ email2[i];
			}
			
			Email email = new Email(mailto,mailfrom,body);
			String host = null;
			//--------------------------------------------------------------------------
			//check which email vendor should be used and set the information accordingly
			if(email.getPostfix().equals("@gmail.com")) {
				
		         host = "smtp.gmail.com";
		         // Get the Session object.// and pass username and password
			        session = Session.getInstance(properties, new javax.mail.Authenticator() {

			            protected PasswordAuthentication getPasswordAuthentication() {

			                return new PasswordAuthentication("wafagha3@gmail.com", "cimmnafmvlbssovo");

			            }

			        });

			        System.out.println("Sending email through gmail "+email.toString());
	
			}else if(email.getPostfix().equals("@walla.co.il")){
				
		         host = "smtp.walla.co.il";
		         // Get the Session object.// and pass username and password
			         session = Session.getInstance(properties, new javax.mail.Authenticator() {

			            protected PasswordAuthentication getPasswordAuthentication() {

			                return new PasswordAuthentication("admin@walla.co.il", "admin");

			            }

			        });

			         System.out.println("Sending email through walla "+email.toString());
		    				
			}else if(email.getPostfix().equals("@yahoo.com")) {
				
		         host = "smtp.yahoo.com";
		         // Get the Session object.// and pass username and password
			         session = Session.getInstance(properties, new javax.mail.Authenticator() {

			            protected PasswordAuthentication getPasswordAuthentication() {

			                return new PasswordAuthentication("admin@yahoo.com", "admin");

			            }

			        });

			         System.out.println("Sending email through yahoo "+email.toString());
			}
			else {
				
				System.out.println("No such email");
				in.close();
				ois.close();
				return;
				
			}
		    // Get system properties
	      

	        // Setup mail server
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");

	      
	        // Used to debug SMTP issues
	        session.setDebug(true);

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(email.getMailfrom()));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getMailto()));

	            // Set Subject: header field
	            message.setSubject("Test");

	            // Now set the actual message
	            message.setText(email.getMailbody());

	            System.out.println("sending...");
	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	        } catch (MessagingException mex) {
	        	 System.out.println("Error sending message : "+socket);
	            mex.printStackTrace();
	        }

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}


}
