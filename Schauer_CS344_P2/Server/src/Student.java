
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Student extends Thread{
		
	// Variables
	public int id;
	public int grade=0;
	public static int gradeCount = 0;
	private boolean tookTest = false;

	// Constructor
	public Student(int id){  
		this.id = id;
		setName("Student " + id); 
	}
	
	// For Printing
	public void msg(String m){
		System.out.println("Student: " + "["+(System.currentTimeMillis()-ClientHelper.time)+"] "+getName()+" : " + m);
	}
	
	// Run Method
	public void run(){
		msg("is Running.");
			waitInClassRoomLine();   // waits for instructor to Arrive
			takeSeats();       // enter class room 15 mins before test starts
			takesTest();
			returnExam();
			leaveClassRoom();
		msg("ended. Reached Test Limit.. took examCountOfStudent tests. ------------ ENDED ------------");
	}// Run
	
	
	synchronized public void waitInClassRoomLine(){
		try{
			synchronized(ClientHelper.waitingStudents_OnLine){
				ClientHelper.grades_received[id-1] = 0;

				while(ClientHelper.roomIsOpen == false || ClientHelper.studentsInRoom == Main.capacity){  // only wait if door is not open or if capacity is full
					if(ClientHelper.roomIsOpen == false ){  msg("---- waiting in class room line. ----  room door is closed so wait....");  }
					if(ClientHelper.studentsInRoom == Main.capacity){  msg("---- waiting in class room line. ---- capacity over so wait.... studentsInRoom = " + ClientHelper.studentsInRoom);  }
					ClientHelper.waitingStudents_OnLine.wait();
				}
				msg("Now enters the room.");    // if door is open and capacity is not full enter the room
				ClientHelper.studentsInRoom = ClientHelper.studentsInRoom + 1;
			}
		} catch(InterruptedException e) {  e.printStackTrace();  }	
	
	}
	
	public void takeSeats(){	// Students wait for test to start 
		try{
			synchronized(ClientHelper.waitingStudents_ToStartTest){
				msg("is waiting to start test.");
				ClientHelper.waitingStudents_ToStartTest.wait();  // wait until teacher notifies students to take exam
				ClientHelper.studentsWithExams = ClientHelper.studentsWithExams + 1;  // Teacher gives exams
			}
		} catch(InterruptedException e) {  e.printStackTrace();  }
		msg("was notified by teacher to start the test.");
	}
	
	public void takesTest(){  // students and instructor wait until exam ends
		try{
			synchronized(ClientHelper.waiting_TestToEnd){
				ClientHelper.waiting_TestToEnd.wait();  // wait until teacher notifies students the exam has ended
			}
		} catch(InterruptedException e) {   e.printStackTrace();	}		
		msg("Notified that Exam Ended.");
		tookTest = true;
	}
	
	public void returnExam(){ 	// students signal instructor 
		synchronized(ClientHelper.waitingTeacher_ToGetBackTests){
			msg("Handed teacher back exam");
			ClientHelper.studentsWithExams = ClientHelper.studentsWithExams - 1;
			ClientHelper.waitingTeacher_ToGetBackTests.notifyAll();
			System.out.println("Notified techer that test was returned");
		}
		
		// wait to get back grade
		// waits for all students to have test graded
			synchronized(ClientHelper.studentsWaitingForGrade){
				try{
					msg("is waiting for grade.");
					ClientHelper.studentsWaitingForGrade.wait();
				} catch(InterruptedException e) {
				e.printStackTrace();
				}
				msg("has been given a grade.");	
			}
		
	}
	
	synchronized public void leaveClassRoom(){
		msg("leaves the class room.");
		ClientHelper.studentsInRoom = ClientHelper.studentsInRoom - 1;	

		// Student Receives A Grade
		if(tookTest == true){
			msg("Received grade " + Teacher.gradeBook[id-1]);
			gradeCount = gradeCount + 1;
			ClientHelper.grades_received[id-1] = Teacher.gradeBook[id-1];
		}
		
		// Print Out Final Grades
		if(gradeCount == Main.capacity){
			System.out.println("-----------------------------------------------------");
			for(int i=0 ; i<Main.numStudents ; i++){
				if(	ClientHelper.grades_received[i] == 0){
					System.out.println("Student " + (i+1) + " received grade 0 (because of missed test)" );

				} else {
					System.out.println("Student " + (i+1) + " received grade " + ClientHelper.grades_received[i]);
				}
			}
			System.out.println("-----------------------------------------------------");
		}

	}

}