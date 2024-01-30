package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CPU {
  private ArrayList<Process> startingProcesses;
  private ArrayList<Process> finishedProcesses;
  private Result result = new Result();

  private Queue<Process> readyQueue = new LinkedList<Process>();
  private Algorithm algorithm;
  private Process worker;

  private int workerStartTime = 0;
  private int counter = 0;

  public CPU(ArrayList<Process> processes, Algorithm algorithm) {
    this.startingProcesses = processes;
    this.algorithm = algorithm;
    this.finishedProcesses = new ArrayList<Process>(processes.size());
  }

  public Result start() {
    while (true) {
      this.checkArrivals();

      if (this.worker == null) {
        Process p = this.algorithm.chooseProcess(this.readyQueue);
        if (p == null) {
          break;
        }

        this.newWorker(p);
      }

      int quantum = this.algorithm.getQuantum();
      int worked = this.counter - this.workerStartTime;
      if (quantum > 0 && worked == quantum) {
        Process p = this.algorithm.chooseProcess(this.readyQueue);
        this.newWorker(p);
      }

      this.counter++;
      this.work();
    }

    this.result.setProcesses(this.finishedProcesses);
    return this.result;
  }

  private void checkArrivals() {
    ArrayList<Process> arrivedProcesses = new ArrayList<Process>();

    Iterator<Process> iterator = this.startingProcesses.iterator();
    while (iterator.hasNext()) {
      Process p = iterator.next();

      if (p.justArrived(this.counter)) {
        arrivedProcesses.add(p);
        this.readyQueue.add(p);
        iterator.remove();
      }
    }

    if (!this.algorithm.isPreEmptive() || arrivedProcesses.size() == 0 || this.worker == null) {
      return;
    }

    Process p = this.algorithm.chooseProcess(arrivedProcesses);
    p = this.algorithm.compareProcesses(this.worker, p);
    this.newWorker(p);
  }

  private void work() {
    this.worker.work();

    if (this.worker.remaining() > 0) {
      return;
    }

    this.newWorker(null);
  }

  private void newWorker(Process newWorker) {
    if (this.worker == newWorker) {
      return;
    }

    Process oldWorker = this.worker;
    if (oldWorker != null) {
      Process.Schedule schedule = new Process.Schedule(oldWorker.getId(), this.workerStartTime, this.counter);
      this.result.addSchedule(schedule);

      if (newWorker == null) {
        this.finishedProcesses.add(oldWorker);
        this.worker = null;
        return;
      }

      this.readyQueue.add(oldWorker);
    }

    this.workerStartTime = this.counter;
    this.readyQueue.remove(newWorker);
    this.worker = newWorker;
  }
}
