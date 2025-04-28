public class ProcessThread extends Thread {
  private final int pid, burstTime;

  public ProcessThread(int pid, int burstTime) {
    this.pid = pid;
    this.burstTime = burstTime;
  }

  @Override 
  public void run() {
    System.out.println("Process " + pid + " started.");
    try {
      Thread.sleep(burstTime * 1000);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println("Process " + pid + " finished.");
  }
}
