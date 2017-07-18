
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main extends Thread{

	public static int portNumber;
	public static int numStudents;
	public static int capacity;
	public static int numSeats;
	public static String hostname;
			
	public static void main (String [] args) throws IOException {

		// Initialize Variables
		hostname = args[0];									// localhost
		portNumber = Integer.parseInt(args[1]);				// 81
		numStudents = Integer.parseInt(args[2]);   			// 5
		capacity = Integer.parseInt(args[3]);     		    //3
		
	    InetAddress addr;
	    addr = InetAddress.getLocalHost();
	   
	   // hostname = addr.getHostName();
		
		new Main().start();
	}

	public void run(){	
		try{
			
			System.out.println("------------------------------------------------");			
			System.out.println("-- Server");
			System.out.println("------------------------------------------------");			
			
			System.out.println("Creating server socket on port " + portNumber + " on Host " + hostname);
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (true){
				Socket socket = serverSocket.accept();
				
				// Create Helpers
				new ClientHelper(socket).start();
				System.out.println("ClientHelper " + "was created." );	
				
			}
		} catch (Exception e){
			System.out.println("Unable to listen to port.");
			e.printStackTrace();
		}

		
	}
	
}
