import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class NonPreemptive implements PriorityScheduler {
  @Override
  public void schedule(ArrayList<Process> processes) {
    ArrayList<SchedulingStatistics> schedulingStatistics = new ArrayList<>();
    int length = processes.size();
    int finishedProcessCount = 0;
    int currentTime = 0;
    int listIdx = 0;
    // Ensure processes are reset and sorted by arrival
    processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        
    PriorityQueue<Process> pq = new PriorityQueue<>(
      Comparator.comparingInt((Process p) -> p.priority).thenComparingInt(p -> p.pid));
    
    while(finishedProcessCount < length){
      // 1. Add all processes that have arrived by the current time into the PQ
      while (listIdx < length && processes.get(listIdx).arrivalTime <= currentTime) {
        pq.add(processes.get(listIdx));
        listIdx++;
      }
      
      if (!pq.isEmpty()) {
        // 2. Extract the process with the highest priority
        Process p = pq.poll();
        
        p.completionTime = currentTime + p.burstTime;
        p.turnAroundTime = p.completionTime - p.arrivalTime;
        p.waitingTime = p.turnAroundTime - p.burstTime;
                    
        currentTime = p.completionTime;
        finishedProcessCount++;
        
        schedulingStatistics.add(new SchedulingStatistics("P" + p.pid, p.completionTime));
        // 3. AGING LOGIC (Non-Preemptive style)
        // Now that a process has finished and time has jumped forward,
        // we update the priorities of everyone still waiting in the queue.
        if (!pq.isEmpty()) {
          ArrayList<Process> waiting = new ArrayList<>();
          
          while(!pq.isEmpty()) {
            waiting.add(pq.poll());
          }
          
          for(Process wp : waiting) {
            // If they've been waiting while the previous process was running
            if(wp.priority > 1) {
              wp.priority--; 
            }
          }
          // Put them back so the PQ re-sorts for the next selection
          pq.addAll(waiting);
        }
        
      } else {
        // 4. Handle IDLE time
        int nextArrivalTime = processes.get(listIdx).arrivalTime;
        schedulingStatistics.add(new SchedulingStatistics("IDLE", nextArrivalTime));
        currentTime = nextArrivalTime;
      }
    }
    // Display results
    System.out.println("Priority Scheduling Results (Non-preemptive)");
    displayPrioritySchedulingResults(processes);
    ganttChart(schedulingStatistics);
    displayAverages(processes);
  }
}
