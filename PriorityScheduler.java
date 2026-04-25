import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public interface PriorityScheduler {
  //method for cpu scheduling that can be overwritten based on the user's preferred cpu schedule.
  void schedule(ArrayList<Process> processes);
  
  //method for displaying average of TAT and WT
  default void displayAverages(ArrayList<Process> processes) {
    double avgTAT = processes.stream()
            .mapToInt(p -> p.turnAroundTime)
            .average()
            .orElse(0.0);
    double avgWT = processes.stream()
            .mapToInt(p -> p.waitingTime)
            .average()
            .orElse(0.0);
            
    System.out.printf("\nAverage TAT: %.2f\n", avgTAT);
    System.out.printf("Average WT: %.2f", avgWT);
  }
  
  //method for displaying the gantt chart
  default void ganttChart(ArrayList<SchedulingStatistics> schedulingStatistics) {
    System.out.println("Gantt Chart");
    for(SchedulingStatistics ss : schedulingStatistics) {
      System.out.printf("| %-4s ", ss.pid);
    }
    
    System.out.println("|");
    
    System.out.print(0);
    for(SchedulingStatistics ss : schedulingStatistics)
    {
      System.out.printf("%7d", ss.completionTime);
    }
  }
  //method for displaying priority scheduling results
  default void displayPrioritySchedulingResults(ArrayList<Process> processes) {
    System.out.println("--------------------------------------------------------------");
    System.out.println("| Process |  AT   |  BT   | Priority |  CT   |  TAT  |  WT   |");
    System.out.println("--------------------------------------------------------------");

    for(Process p : processes) {
      System.out.printf("| P%-6s | %5d | %5d | %8d | %5d | %5d | %5d |\n",p.pid, p.arrivalTime, p.burstTime, p.priority, p.completionTime, p.turnAroundTime, p.waitingTime);
    }
    System.out.println("--------------------------------------------------------------");
  }
}