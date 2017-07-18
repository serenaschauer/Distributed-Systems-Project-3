import java.util.Random;


class runClients {
	
	public static int portNumber;
	public static String hostname;
	public static int numStudents ;
	public static int capacity;
	
	static Random rand = new Random();


	public static void main (String [] args){
	
		hostname = args[0];									// localhost
		portNumber = Integer.parseInt(args[1]);				// 81
		numStudents = Integer.parseInt(args[2]);   			// 5
		capacity = Integer.parseInt(args[3]);    			// 4
		
		System.out.println("------------------------------------------------");
		System.out.println("-- Client");
		System.out.println("------------------------------------------------");
		
		// Start Student Threads
		for (int i=0; i<numStudents; i++){
			new StudentClient(i).start();
		}
		
		// Wait
		try{
			Thread.sleep(rand.nextInt(3000));
		}catch(InterruptedException e){
			e.printStackTrace();
		}	
		
		// Start Teacher Thread
		new TeacherClient(0).start();

	}
	
	
	
	
}
