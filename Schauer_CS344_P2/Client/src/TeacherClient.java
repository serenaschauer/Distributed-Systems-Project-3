import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TeacherClient extends Thread {

	// similar to student client
//	private static final int portNumber = 81;
//	private static final String host = "localhost";
	int id;

	public TeacherClient(int id){
		this.id = id;
		setName("Teacher " + id); 
	}
	
	public void run (){
		
		Socket socket;
		try{

			// Connect To Server
			System.out.println("Creating socket to '" + runClients.hostname + "' on port " + runClients.portNumber);
			socket = new Socket(runClients.hostname, runClients.portNumber);  
			
			// Create Input And Output Streams
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			
			// Server Tells Client There Is A Connection
			System.out.println("Teacher: server says " + br.readLine());
			
			// Tell Server That This Is A Teacher Thread
			pw.println("Teacher");
			
			// Tell Server Which Method to Execute
			pw.println("0");
			System.out.println("TeacherClient: Sent Method 0 to Server");
			pw.println("1");
			System.out.println("TeacherClient: Sent Method 1 to Server");
			pw.println("2");
			System.out.println("TeacherClient: Sent Method 2 to Server");			
			pw.println("3");
			System.out.println("TeacherClient: Sent Method 3 to Server");			
			pw.println("4");
			System.out.println("TeacherClient: Sent Method 4 to Server");			

	
			// Close PrintWriter And Socket
			pw.close();
     		socket.close();
		}
		catch (Exception e){e.printStackTrace();}
	}
	
}
