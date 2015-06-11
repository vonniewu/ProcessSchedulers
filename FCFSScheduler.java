//import java.io.File;
//import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/**
 * First comes first served scheduler.
 */
public class FCFSScheduler implements Scheduler {

	public static class Process {
		public int processID;
		public int arrivalTime;
		public int burstTime;

		public Process(int id, int time, int duration) {
			processID = id;
			arrivalTime = time;
			burstTime = duration;
		}
	}

	// Sorting the Arraylist with Arrival Time
	public static class ArrivalTimeComparator implements Comparator<Process> {
		@Override
		public int compare(Process first, Process second) {
			if (first.arrivalTime < second.arrivalTime)
				return -1;

			if (first.arrivalTime == second.arrivalTime)
				return 0;
			return 1;
		}
	}

	public int calculateWaitTime() {
		return 0;
	}

	public int calculateTurnAroundTime() {
		return 0;
	}

  @Override
  public void schedule(String inputFile, String outputFile) {
    // implement your scheduler here.
    // write the scheduler output to {@code outputFile}

  		File input = new File(inputFile);
  		File output = new File(outputFile);

  		ArrayList<Process> processArray = new ArrayList<Process>();

  		try {
  			Scanner sc = new Scanner(input);

  			while (sc.hasNextLine()) {
  				String entry = sc.nextLine();
  				String[] split = entry.split(" ");
  				processArray.add(new Process(Integer.parseInt(split[0]), 
  										Integer.parseInt(split[1]), 
  										Integer.parseInt(split[2])));
  			}

  			sc.close();

  			// Sort the arrayList by Arrival Time
  			Collections.sort(processArray, new ArrivalTimeComparator());

  			for (int i=0; i < processArray.size(); i++) {
  				System.out.printf("ID: %d, Arrival Time: %d, Burst Time: %d\n", 
  									processArray.get(i).processID, 
  									processArray.get(i).arrivalTime, 
  									processArray.get(i).burstTime);
  			}

  			try {
  				FileWriter fWriter = new FileWriter(output);
  				PrintWriter pWriter = new PrintWriter(fWriter);

  				pWriter.println("Hello world");

  				int startTime = processArray.get(0).arrivalTime;
	  			int finishTime = startTime;
	  			int totalWaitingTime = 0;
	  			int totalTurnaroundTime = 0;

	  			for (int i=0; i < processArray.size(); i++) {
	  				finishTime += processArray.get(i).burstTime; 
	  				int waitingTime = startTime - processArray.get(i).arrivalTime;
	  				int turnaroundTime = processArray.get(i).burstTime + waitingTime;

	  				totalWaitingTime += waitingTime;
	  				totalTurnaroundTime += turnaroundTime;

	  				String scheduledProcess = String.format("%d %d %d %d", 
	  													processArray.get(i).processID,
	  													finishTime,
	  													waitingTime,
	  													turnaroundTime);
	  				System.out.printf("Schedule: %s \n", scheduledProcess);
	  				pWriter.println(scheduledProcess);
	  			}
  			} catch (IOException e) {
  				e.printStackTrace();
  			} 

  			//pWriter.println("%d %d", totalWaitingTime/processArray.size(),
  									//totalTurnaroundTime/processArray.size());
  		} catch (FileNotFoundException e) {
  			e.printStackTrace();
  		}
  }
}
