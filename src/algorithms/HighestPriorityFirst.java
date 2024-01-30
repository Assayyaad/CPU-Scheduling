package src.algorithms;

import src.Algorithm;
import src.Process;

public class HighestPriorityFirst extends Algorithm {
  public HighestPriorityFirst(Boolean preEmptive) {
    super(preEmptive);
  }

  @Override
  public Process compareProcesses(Process a, Process b) {
    switch (a.comparePriority(b)) {
      case 0:
        return a;

      case 1:
        return a;
      case -1:
        return b;
    }

    return null;
  }
}
