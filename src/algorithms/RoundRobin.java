package src.algorithms;

import java.util.Collection;

import src.Algorithm;
import src.Process;

public class RoundRobin extends Algorithm {
  public RoundRobin(int quantum) {
    super(quantum);
  }

  @Override
  public Process chooseProcess(Collection<Process> processes) {
    return processes.stream().findFirst().orElse(null);
  }
}
