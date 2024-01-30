package src.algorithms;

import src.Algorithm;
import src.Process;

public class ShortestJobFirst extends Algorithm {
  public ShortestJobFirst(Boolean preEmptive) {
    super(preEmptive);
  }

  @Override
  public Process compareProcesses(Process a, Process b) {
    switch (a.compareShortest(b)) {
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
