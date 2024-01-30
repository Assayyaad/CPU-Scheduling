package src;

import java.util.*;

public abstract class Algorithm {
  private Boolean preEmptive;
  private int quantum;

  public Algorithm(Boolean preEmptive) {
    this(preEmptive, 0);
  }

  public Algorithm(int quantum) {
    this(false, quantum);
  }

  private Algorithm(Boolean preEmptive, int quantum) {
    this.preEmptive = preEmptive;
    this.quantum = quantum;
  }

  public Boolean isPreEmptive() {
    return this.preEmptive;
  }

  public int getQuantum() {
    return this.quantum;
  }

  public Process chooseProcess(Collection<Process> processes) {
    Process chosen = null;

    for (Process p : processes) {
      if (chosen == null) {
        chosen = p;
        continue;
      }

      chosen = this.compareProcesses(chosen, p);
    }

    return chosen;
  }

  public Process compareProcesses(Process a, Process b) {
    return null;
  }

  @Override
  public String toString() {
    String value = this.quantum > 0 ? String.valueOf(this.quantum)
        : this.preEmptive ? "pre-emptive" : "non-pre-emptive";
    return String.format("%s(%s)", this.getClass().getSimpleName(), value);
  }

  public void log() {
    System.out.println("Algorithm: " + this.getClass().getSimpleName());
    if (this.quantum > 0) {
      System.out.println("Quantum: " + this.quantum);
    } else {
      System.out.println("Pre-emptive: " + this.preEmptive);
    }
  }
}
