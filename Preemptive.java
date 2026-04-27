import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Preemptive implements PriorityScheduler {
  @Override
  public void schedule(ArrayList<Process> processes) {
        ArrayList<SchedulingStatistics> schedulingStatistics = new ArrayList<>();

        int n = processes.size();
        int finished = 0;
        int currentTime = 0;
        String lastPid = "";
        
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        //PriorityQueue: Sorts by priority (lower number = higher priority)
        // If priorities are equal, it sorts by Arrival Time (Tie-breaker)
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
            Comparator.comparingInt((Process p) -> p.priority).thenComparingInt(p -> p.arrivalTime)
        );

        // Reset remaining times
        for (Process p : processes) {
            p.remainingTime = p.burstTime;
        }

        while (finished < n) {
          //Add all processes that have arrived by this time to the Ready Queue
          for (Process p : processes) {
            if (p.arrivalTime == currentTime) {
              readyQueue.add(p);
            }
          }
          
          if (!readyQueue.isEmpty()) {
            //Get the process with the highest priority
            Process p = readyQueue.peek();
            String currentPid = "P" + p.pid;
            // Gantt Chart Logic
            if (!currentPid.equals(lastPid)) {
              schedulingStatistics.add(new SchedulingStatistics(currentPid, currentTime + 1));
                    lastPid = currentPid;
            } else {
              schedulingStatistics.get(schedulingStatistics.size() - 1).completionTime = currentTime + 1;
            }
            //Execute for 1 unit
            p.remainingTime--;
            if (p.remainingTime == 0) {
              readyQueue.poll(); // Remove finished process
              finished++;
              p.completionTime = currentTime + 1;
              p.turnAroundTime = p.completionTime - p.arrivalTime;
              p.waitingTime = p.turnAroundTime - p.burstTime;
            }
          } else {
            //Handle Idle Time
            currentTime++;
            if (!lastPid.equals("IDLE")) {
              schedulingStatistics.add(new SchedulingStatistics("IDLE", currentTime));
              lastPid = "IDLE";
            } else {
              schedulingStatistics.get(schedulingStatistics.size() - 1).completionTime = currentTime;
            }
            continue; // Skip the currentTime++ at the bottom to avoid double increment
          }
          currentTime++;
          //Aging Logic
          if (currentTime % 5 == 0 && !readyQueue.isEmpty()) {
            //Identify who is currently running (the one at the top)
            Process runningProcess = readyQueue.poll(); 
    
            ArrayList<Process> waiting = new ArrayList<>();
            //Pull out only the remaining processes (the ones actually waiting)
            while(!readyQueue.isEmpty())
            {
              waiting.add(readyQueue.poll());
            }
            //Age only the waiting processes
            for(Process wp : waiting) {
              if(wp.priority > 1) {
                wp.priority--; 
              }
            }
            //Put everyone back
            readyQueue.add(runningProcess);
            readyQueue.addAll(waiting);

          }
        }
        
        System.out.println("Priority Scheduling Results (Preemptive)");
        displayPrioritySchedulingResults(processes);
        ganttChart(schedulingStatistics);
        displayAverages(processes);
  }
}
