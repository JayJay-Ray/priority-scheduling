//Class that holds the necessary values for priority scheduling
public class Process {
  int pid;
  int arrivalTime;
  int burstTime;
  int priority;
  int completionTime;
  int turnAroundTime;
  int waitingTime;
  int remainingTime;
  int initialPriority;
  
  //Requires the user to input
  public Process(int pid, int arrivalTime, int burstTime, int priority) {
    this.pid = pid;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
  }
}