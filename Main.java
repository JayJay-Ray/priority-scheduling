import java.util.Scanner;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Process> processes = new ArrayList<>(); 
    PriorityScheduler algorithm;
    int choice;
    int numOfProcess;
    boolean isContinue = false;
    String option = "";
    
    do {
      System.out.print("***********************\n");
      System.out.print("Priority CPU Scheduling\n");
      System.out.print("***********************\n");
    
    // Accepting the prefer CPU scheduling with error handling
      while(true) {
        System.out.print("1. Non-preemptive\n");
        System.out.print("2. Preemptive\n");
        System.out.print("3. Exit\n");
      
        try{
          System.out.print("Enter your choice: ");
          choice = Integer.parseInt(scanner.nextLine());
        } catch(NumberFormatException e) {
          System.out.print("Invalid input. Please try again.\n");
          continue;
        }
        
        if(choice < 1) {
          System.out.print("Invalid input. Only 1 and 2 are the choices. Please try again.\n");
          continue;
        }
        clearScreen();
        break;
      }
      
      //checks if the user want to exit the program.
      if(choice == 3) {
        break;
      }
    
    //Ask for the number of process with error handling
      while(true) {
        try {
          System.out.print("Enter the number of process: ");
          numOfProcess = Integer.parseInt(scanner.nextLine());
        } catch(NumberFormatException e) {
          System.out.print("Invalid input. Please try again.\n");
          continue;
        }
      
        if(numOfProcess <= 0) {
          System.out.println("Number of process must exceeds 0. Please try again.\n");
          continue;
        }
        break;
      }
    
    clearScreen();
    //Accept the process based on the number of process
      addProcesses(processes, numOfProcess, scanner);
      
    
    //Checks what type of cpu scheduling does the user wants
      if(choice == 1) {
        clearScreen();
        algorithm = new NonPreemptive();
        algorithm.schedule(processes);
      } else if(choice == 2) {
        clearScreen();
        algorithm = new Preemptive();
        algorithm.schedule(processes);
      }
    
    //Ask the user whether he/she wants to continue using the program. It also includes error handling.
      do{
        System.out.print("\nDo you want to continue(yes/no)? ");
        option = scanner.nextLine();
      
        if(option.equalsIgnoreCase("yes")) {
          isContinue = true;
          processes.clear();
          clearScreen();
        } else if(option.equalsIgnoreCase("no")) {
          isContinue = false;
          clearScreen();
        } else {
          System.out.print("Invalid input. Please try again.\n");
        }
      } while(!option.equalsIgnoreCase("yes") && !option.equalsIgnoreCase("no"));
    //The entire program will depend on this ccondition whether it will continue to run or stop.
    }while(isContinue || choice == 3);
    
    System.out.println("Thank you for using our program.");
    
    scanner.close();
  }
  //method for adding process on process ArrayList based on the given number of process.
  public static void addProcesses(ArrayList<Process> processes, int numOfProcess, Scanner scanner) {
    int arrivalTime = 0;
    int burstTime = 0;
    int priority = 0;
    
    //It will loop based on the given number of process
    for(int i = 0; i < numOfProcess; i++) {
      int processNumber = i + 1;
      
      System.out.printf("Process No. %d:\n", processNumber);
      //Get the necessary values on user. 
      arrivalTime = getArrivalTime(arrivalTime, scanner);
      burstTime = getBurstTime(burstTime, scanner);
      priority = getPriority(priority, scanner);
      
      processes.add(new Process(processNumber, arrivalTime, burstTime, priority));
      clearScreen();
    }
  }
  
  //method for getting arrival time with error handling.
  public static int getArrivalTime(int arrivalTime, Scanner scanner) {
    while(true) {
      try {
        System.out.print("Enter Arrival Time: ");
        arrivalTime = Integer.parseInt(scanner.nextLine());
      } catch(NumberFormatException e) {
        System.out.print("Invalid input. Please try again.\n");
        continue;
      }
      
      if(arrivalTime < 0) {
        System.out.print("Arrival time must not less than to 0. Please try again.\n");
        continue;
      }
      break;
    }
    
    return arrivalTime;
  }
  //method for getting burst time with error handling.
  public static int getBurstTime(int burstTime, Scanner scanner) {
    while(true) {
      try {
        System.out.print("Enter Burst Time: ");
        burstTime = Integer.parseInt(scanner.nextLine());
      } catch(NumberFormatException e) {
        System.out.print("Invalid input. Please try again.\n");
        continue;
      }
      
      if(burstTime <= 0) {
        System.out.print("Burst time must not less than to 0. Please try again.\n");
        continue;
      }
      break;
    }
    
    return burstTime;
  }
  //method for getting priority number with error handling.
  public static int getPriority(int priority, Scanner scanner) {
    while(true) {
      try {
        System.out.print("Enter Priority number: ");
        priority = Integer.parseInt(scanner.nextLine());
      } catch(NumberFormatException e) {
        System.out.print("Invalid input. Please try again.\n");
        continue;
      }
      
      if(priority < 0) {
        System.out.print("Priority must not less than to 0. Please try again.\n");
        continue;
      }
      break;
    }
    
    return priority;
  }
  //method for clearing the terminal once the user decided to make another cpu scheduling.
  public static void clearScreen() {
        // Use the ANSI escape code (best for the terminal in your screenshot)
        //System.out.print("\033[H\033[2J");
        System.out.print("\033c");
        System.out.flush();
    }
}