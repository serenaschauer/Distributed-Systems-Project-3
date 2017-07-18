import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class StudentClient extends Thread {
//	private static final int numStudents = 5;
//	private static final int portNumber = 81; //3000
//	private static final String host = "localhost";
	
	private int id;
	
	public StudentClient(int id){  
		this.id = id;
		setName("Student " + id); 
	}
	
	public void run (){
	//	final String host = "localhost";

		Socket socket;
		try{
			// Connect To Server
			System.out.println("Creating socket to '" + runClients.hostname + "' on port " + runClients.portNumber);
			socket = new Socket(runClients.hostname, runClients.portNumber); 
			
			// Create Input And Output Streams
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			System.out.println("StudentClient: server says" + br.readLine());

			// Tell Server That This Is A Teacher Thread
			pw.println("Student");

			// Tell Server Which Method to Execute
			pw.println("0");
			System.out.println("StudentClient " + id + ": Sent 0 to Server");
			pw.println("1");
			System.out.println("StudentClient " + id + ": Sent 1 to Server");
			pw.println("2");
			System.out.println("StudentClient " + id + ": Sent 2 to Server");
			pw.println("3");
			System.out.println("StudentClient " + id + ": Sent 3 to Server");
			pw.println("4");
			System.out.println("StudentClient " + id + ": Sent 4 to Server");

			


 	
			// Close PrintWriter And Socket
			pw.close();
     		socket.close();

		}
		catch (Exception e){e.printStackTrace();}
	}

}
