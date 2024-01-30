package src;

public class Process {
  private int id;
  private int arrivalTime;
  private int burstTime;
  private int priority;

  private int work = 0;

  public Process(int id, int arrivalTime, int burstTime, int priority) {
    this.id = id;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
  }

  public int getId() {
    return this.id;
  }

  public int getArrivalTime() {
    return this.arrivalTime;
  }

  public int getBurstTime() {
    return this.burstTime;
  }

  public String toString() {
    return String.format("P%d(%d)^%d", this.id, this.burstTime, this.priority);
  }

  public Boolean justArrived(int time) {
    return this.arrivalTime == time;
  }

  /**
   * @returns 1 if this has a higher priority
   */
  public int comparePriority(Process other) {
    if (this.priority < other.priority) {
      return 1;
    } else if (this.priority > other.priority) {
      return -1;
    }

    return 0;
  }

  /**
   * @returns 1 if this is has a shorter burst time
   */
  public int compareShortest(Process other) {
    if (this.burstTime < other.burstTime) {
      return 1;
    } else if (this.burstTime > other.burstTime) {
      return -1;
    }

    return 0;
  }

  public void work() {
    this.work++;
  }

  public int remaining() {
    return this.burstTime - this.work;
  }

  public record Schedule(int id, int startTime, int finishTime) {
    public String toString() {
      return String.format("|P%d{%d->%d}|", this.id, this.startTime, this.finishTime);
    }
  }
}
