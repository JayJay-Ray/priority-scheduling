#Priority Scheduling Simulator (Console-based)

A simple console-based simulator to demonstrate priority non-preemptive and preemptive CPU scheduling.

##Compilation
Ensure you have the Java Development Kit (JDK) installed. Run the following command in your terminal:
javac Main.java

##Usage
Run the program using:
java Main

Follow this step-by-step to run the program smoothly:
1. It will ask what type of CPU scheduling you want to use. Number "1" corresponds to "Non-preepmtive", while, number "2" corresponds to "Preemptive".
2. Enter the number of processes
3. Enter the process ID, arrival time, burst time, and priority number on each process.

##Output
The program will output the following:
 • CPU scheduling results: A summary of all process metrics.
 • Gantt chart: A text-based representation of the CPU execution timeline.
 • Averages: Average turnaroud time and average waiting time.

##Continuation
The program will ask the user whether the user wants to continue using the program or not. "Yes" corresponds to continue using the program and make another CPU scheduling calculation, while, "No" corresponds to end the program. 
