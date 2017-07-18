# Distributed-Systems-Project-3
Remote Procedure Calls Project 

Run Server and Client on two separate windows on the local machine.
First run Server, then Client

-----------------------------------------------------
-- Server
-----------------------------------------------------
run the Main method with arguements
	<hostname>
	<portNumber>
	<numStudents>
	<capacity>

example: java -jar Server.jar localhost 81 5 3


-----------------------------------------------------
-- Client
-----------------------------------------------------
run the runClients Method with arguements
	<hostname>
	<portNumber>
	<numStudents>
	<capacity>

example: java -jar Client.jar localhost 81 5 3
