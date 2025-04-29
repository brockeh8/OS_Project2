import java.util.concurrent.Semaphore;

public class DiningManager {
  public static void run(int NumPhilosophers, int meals) throws InterruptedException {
    Semaphore[] forks = new Semaphore[numPhilosophers];
    for (int i = 0; i < numPhilosophers; i++) {
      forks[i] = new Semaphore;
    }

    Thread[] threads = new Thread[numPhilosophers];
    for (int i = 0; i < numPhilosophers; i++) {
      threads[i] = new Thread(new Philosopher(i + 1, forks, meals));
      threads[i].start();
    }

    for (Thread t : threads) t.join();

    System.out.println("Dining Philosphers simulation complete");
  }
}
