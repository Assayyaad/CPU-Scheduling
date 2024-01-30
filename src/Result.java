package src;

import java.util.*;

public class Result {
  private HashMap<Integer, ArrayList<Process.Schedule>> scheduleMap;
  private ArrayList<Process> processes;

  public Result() {
    this.scheduleMap = new HashMap<Integer, ArrayList<Process.Schedule>>();
  }

  public void setProcesses(ArrayList<Process> processes) {
    this.processes = processes;
  }

  public void addSchedule(Process.Schedule schedule) {
    int id = schedule.id();

    ArrayList<Process.Schedule> list = this.scheduleMap.get(id);
    if (list == null) {
      list = new ArrayList<Process.Schedule>();
    }

    list.add(schedule);
    this.scheduleMap.put(id, list);
  }

  public int calculateTT(Process process) {
    return this.getLastFinishTime(process) - process.getArrivalTime();
  }

  public int calculateWT(Process process) {
    return this.calculateTT(process) - process.getBurstTime();
  }

  public int calculateAverageTT() {
    int sum = 0;
    for (Process p : this.processes) {
      sum += this.calculateTT(p);
    }

    return sum / this.scheduleMap.size();
  }

  public int calculateAverageWT() {
    int sum = 0;
    for (Process p : this.processes) {
      sum += this.calculateWT(p);
    }

    return sum / this.scheduleMap.size();
  }

  public void log(Algorithm algorithm) {
    System.out.println("======================");
    System.out.println("======================");
    algorithm.log();
    System.out.println("----------------------");
    System.out.print("[");
    for (Process.Schedule r : this.getSchedules()) {
      System.out.print(r.toString());
    }
    System.out.println("]");
    System.out.println("----------------------");
    this.logCalculations();
    System.out.println("======================");
    System.out.println("======================");
  }

  private ArrayList<Process.Schedule> getSchedules() {
    ArrayList<Process.Schedule> schedules = new ArrayList<Process.Schedule>();

    for (Map.Entry<Integer, ArrayList<Process.Schedule>> entry : this.scheduleMap.entrySet()) {
      schedules.addAll(entry.getValue());
    }

    schedules.sort((a, b) -> a.startTime() - b.startTime());

    return schedules;
  }

  private void logCalculations() {
    this.processes.sort((a, b) -> a.getId() - b.getId());

    for (Process p : this.processes) {
      int tt = this.calculateTT(p);
      int wt = this.calculateWT(p);
      System.out.println(String.format("P%d: TT = %d, WT = %d", p.getId(), tt, wt));
    }

    int averageTT = this.calculateAverageTT();
    int averageWT = this.calculateAverageWT();
    System.out.println(String.format("Average: TT = %d, WT = %d", averageTT, averageWT));
  }

  private int getLastFinishTime(Process process) {
    ArrayList<Process.Schedule> list = this.scheduleMap.get(process.getId());
    Process.Schedule last = list.get(list.size() - 1);
    return last.finishTime();
  }
}
