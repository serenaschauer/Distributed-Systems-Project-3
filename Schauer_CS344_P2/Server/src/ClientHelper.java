import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHelper extends Thread {
	
	private static final String STUDENT = "Student";
	private static final String TEACHER = "Teacher";
	public static Student [] students = new Student [100];
	private static Object lock = new Object();
	public static Object goHome = new Object();
	public static Teacher teacher;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private Student student;
	private String threadtype;

    // variables from Project 1 
	public static long time = System.currentTimeMillis();
	public static int numSeats;
	public static int tableNumber = 1;
	public static int seatNumber = 1;
	public static int grades_received[] = new int[Main.numStudents];
	public static int studentsWithExams = 0;
	public static int studentsInRoom = 0;
	public static boolean roomIsOpen = false;
	
	public static ArrayList studentsWaitingForGrade = new ArrayList();
	public static ArrayList waitingStudents_OnLine = new ArrayList();
	public static ArrayList waiting_TestToEnd = new ArrayList();
	public static ArrayList waitingStudents_ToStartTest = new ArrayList();
	public static ArrayList waitingTeacher_ToGetBackTests = new ArrayList();
	
	String ID; 
	
    private static AtomicInteger ID_number = new AtomicInteger();
	
	// Constructor
	public ClientHelper(Socket socket){
		this.socket = socket;
	}
	
	// Create Helper Thread IDs
    public static String createID(){
        return Integer.toString(ID_number.getAndIncrement() + 1 ) ;
    }
	

	public void run (){

		threadtype = "Unknown";
		String line;
		
		try{
			
			// Create A Helper Thread ID
			ID = createID();
			System.out.println("ClientHelper " + ID + ": is running");
			
			// Create Input And Output Streams
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(os, true);
			
			// Tell Client There Is A Connection
			pw.println(" There is a connection to the server." );

			// Client Tells Server The Thread Type (Student Or Teacher)
			line = br.readLine();
			threadtype = line;
			System.out.println("ClientHelper " + ID + ": This thread is a " + threadtype);

			// Create Student And Teacher Threads
			if(threadtype.equals(STUDENT)) { 
				student = new Student(Integer.parseInt(ID));
				System.out.println("Created Student " + ID);
			}
			if (threadtype.equals(TEACHER)){
				teacher = new Teacher(Integer.parseInt(ID));
				System.out.println("Created Teacher " + ID);
			}

			// Client Tells Server To Run Methods
			String MethodLine0 = br.readLine();
			runMethod(Integer.parseInt(MethodLine0));

			String MethodLine1 = br.readLine();
			runMethod(Integer.parseInt(MethodLine1));

			String MethodLine2 = br.readLine();
			runMethod(Integer.parseInt(MethodLine2));

			String MethodLine3 = br.readLine();
			runMethod(Integer.parseInt(MethodLine3));

			String MethodLine4 = br.readLine();
			runMethod(Integer.parseInt(MethodLine4));
		
		} catch (Exception e){   }

		
	}

	// Run Methods According to Method Number And Thread Type
	// Print Information To Console
	public void runMethod (int methodNumber){
		if (threadtype.equals(STUDENT)){
			switch (methodNumber){
				case 0:
							System.out.println("ClientHelper " + ID + ": Student about to start method 0 -- Waiting in ClassroomLine");
							student.waitInClassRoomLine();
							System.out.println("ClientHelper " + ID + ": Student completed method 0 -- Waiting in ClassRoomLine");
							break;
				case 1:
							System.out.println("ClientHelper " + ID + ": Student about to start method 1 -- takes seats");
							student.takeSeats();
							System.out.println("ClientHelper " + ID + ": Student completed method 1 -- takes seats");
							break;
				case 2:
							System.out.println("ClientHelper " + ID + ": Student about to start method 2 -- takes test");
							student.takesTest();
							System.out.println("ClientHelper " + ID + ": Student completed method 2 -- takes test");
							break;
				case 3:
							System.out.println("ClientHelper " + ID + ": Student about to start method 3 -- return exam");
							student.returnExam();
							System.out.println("ClientHelper " + ID + ": Student completed method 3 -- return exam");
							break;
				case 4:
							System.out.println("ClientHelper " + ID + ": Student about to start method 4 -- leave class room");
							student.leaveClassRoom();
							System.out.println("ClientHelper " + ID + ": did case 4 -- leave class room");
							break;
			}
		}

		else {
			switch (methodNumber){
				case 0:
							System.out.println("ClientHelper " + ID + ": Teacher about to start method 0 -- Let students in class room");
							teacher.letStudentsEnterClassRoom();
							System.out.println("ClientHelper " + ID + ": Teacher completed method 0 -- Let students in class room");

							break;
				case 1:
							System.out.println("ClientHelper " + ID + ": Teacher about to start method 1 -- Hand Out Exams");
							teacher.handOutExams();
							System.out.println("ClientHelper " + ID + ": Teacher completed method 1 -- Hand Out Exams");
							break;
				case 2:
							System.out.println("ClientHelper " + ID + ": Teacher about to start method 2 -- Wait For Exam to End");
							teacher.waitForExamToEnd();
							System.out.println("ClientHelper " + ID + ": Teacher completed method 2 -- Wait For Exam to End");
							break;
				case 3:
							System.out.println("ClientHelper " + ID + ": Teacher about to start method 3 -- Check Exams");
							teacher.checkExams();
							System.out.println("ClientHelper " + ID + ": Teacher completed method 3 -- Check Exams");
							break;
				case 4:
							System.out.println("ClientHelper " + ID + ": Teacher about to start method 4 -- prepair for next exam");
							teacher.prepairForNextExam();
							System.out.println("ClientHelper " + ID + ": Teacher completed method 4 -- prepair for next exam");
							break;
			}
		}

	}

}