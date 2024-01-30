import java.util.ArrayList;

import src.*;
import src.Process;
import src.algorithms.*;

public class App {
  private static Algorithm[] algorithms = new Algorithm[] {
      new ShortestJobFirst(false),
      new ShortestJobFirst(true),
      new HighestPriorityFirst(false),
      new HighestPriorityFirst(true),
      new RoundRobin(3),
  };

  public static void main(String[] args) throws Exception {
    for (Algorithm algorithm : algorithms) {
      CPU cpu = new CPU(App.getProcesses(), algorithm);
      Result result = cpu.start();
      result.log(algorithm);
    }
  }

  private static ArrayList<Process> getProcesses() {
    ArrayList<Process> processes = new ArrayList<Process>() {
      {
        add(new Process(0, 0, 6, 3));
        add(new Process(1, 1, 4, 3));
        add(new Process(2, 5, 6, 1));
        add(new Process(3, 6, 6, 1));
        add(new Process(4, 7, 6, 5));
        add(new Process(5, 8, 6, 6));
      }
    };

    processes.sort((a, b) -> a.getArrivalTime() - b.getArrivalTime());
    return processes;
  }
}
