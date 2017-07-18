import java.util.Random;
import java.util.Vector;


public class Teacher extends Thread{	

	// Variables
	static Random rand = new Random();
	static 	int gradeBook[] = new int[Main.numStudents];
	static int testNumber = 0;
	
	private int id;
	
	// Constructor
	public Teacher(int id){
		this.id = id;
		setName("Teacher " + id);
	}
	
	// For Printing
	public void msg(String m){
		System.out.println("Teacher: " + "["+(System.currentTimeMillis()-ClientHelper.time)+"] "+getName()+" : " + m);
	}
	
	// Run Method
	public void run(){
		msg("is Running.");
			letStudentsEnterClassRoom();
			handOutExams();
			waitForExamToEnd();
			checkExams();
			prepairForNextExam();
		msg("ended. ------------ ENDED ------------");
	}//Run
		
	public void letStudentsEnterClassRoom(){
		// Initialize Grade Book to 0
		for(int i=0 ; i<Main.numStudents ; i++){
			gradeBook[i]=0;
		}
		msg("This is for Exam Number: " + testNumber);
		ClientHelper.roomIsOpen = true;
		synchronized(ClientHelper.waitingStudents_OnLine){
			ClientHelper.waitingStudents_OnLine.notifyAll();
		}
		msg("Lets students enter the room.");
		
	}
	
	public void handOutExams(){
		// waits for 15 minutes to be up
		try{
			Thread.sleep(rand.nextInt(2000));
			ClientHelper.roomIsOpen = false;
			msg("Closed the doors.");
		}catch(InterruptedException e){
			e.printStackTrace();
		}	
		msg("15 minutes is up.");
		// Hands out exams by signaling students
		// signal students to start test
		synchronized(ClientHelper.waitingStudents_ToStartTest){
			ClientHelper.waitingStudents_ToStartTest.notifyAll();
			msg("Handed out exams.");
		}
	}
	
	public void waitForExamToEnd(){
		// Students and Teacher wait for exam to end
		try{
			Thread.sleep(rand.nextInt(2000));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		msg("Was notified that Exam Ended.");
		synchronized(ClientHelper.waiting_TestToEnd){
			ClientHelper.waiting_TestToEnd.notifyAll();
		}
	}
	
	public void checkExams(){
		// waits for all students to give back the exam
		while(ClientHelper.studentsWithExams != 0 ){
			try{
				synchronized(ClientHelper.waitingTeacher_ToGetBackTests){
					ClientHelper.waitingTeacher_ToGetBackTests.wait();
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}		
		} // When all students have handed in their exam, Teacher can check exams
		// Checking Exams is Simulated By Sleep For A Random Time
		msg("Is in the process of checking and grading exams.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		try{
			Thread.sleep(rand.nextInt(300));
		}catch(InterruptedException e){
			e.printStackTrace();
		}	
		// Teacher Creates Grades
		for(int i=0 ; i<Main.numStudents ; i++){
			gradeBook[i] = rand.nextInt(100);
		}
		synchronized(ClientHelper.studentsWaitingForGrade){
			ClientHelper.studentsWaitingForGrade.notifyAll();
		}

		

	}	
		
	synchronized public void prepairForNextExam(){
		ClientHelper.tableNumber = 1;
		ClientHelper.seatNumber = 1;		
	}
	

}