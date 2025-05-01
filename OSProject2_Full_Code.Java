import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class OSProject2 {
    public static void main(String[] args) throws Exception {
        // 1) simulate processes
        ProcessSimulator.run("processes.txt");

        // 2) dining philosophers: 5 philosophers, 2 meals each
        DiningManager.run(5, 2);
    }
}


class ProcessThread extends Thread {
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
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Process " + pid + " finished.");
    }
}

class ProcessSimulator {
    public static void run(String filename) throws Exception {
        List<ProcessThread> threads = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int pid   = Integer.parseInt(parts[0].trim());
                int burst = Integer.parseInt(parts[1].trim());
                threads.add(new ProcessThread(pid, burst));
            }
        }
        for (ProcessThread t : threads) t.start();
        for (ProcessThread t : threads) t.join();
        System.out.println("All processes complete.\n");
    }
}


class Philosopher implements Runnable {
    private final int id, meals;
    private final Semaphore[] forks;

    public Philosopher(int id, Semaphore[] forks, int meals) {
        this.id    = id;
        this.forks = forks;
        this.meals = meals;
    }

    @Override
    public void run() {
        int left  = id - 1;
        int right = id % forks.length;
        int first  = Math.min(left, right);
        int second = Math.max(left, right);

        for (int m = 1; m <= meals; m++) {
            // think
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // wait for forks
            System.out.printf("[Philosopher %d] Waiting for forks...%n", id);
            try {
                forks[first].acquire();
                forks[second].acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // picked up & eat
            System.out.printf("[Philosopher %d] Picked up fork %d and %d%n", id, first+1, second+1);
            System.out.printf("[Philosopher %d] Eating...%n", id);
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // release
            forks[first].release();
            forks[second].release();
            System.out.printf("[Philosopher %d] Released forks%n", id);
        }
    }
}

class DiningManager {
    public static void run(int numPhilosophers, int meals) throws InterruptedException {
        Semaphore[] forks = new Semaphore[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Semaphore(1);
        }

        Thread[] threads = new Thread[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            threads[i] = new Thread(new Philosopher(i + 1, forks, meals));
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        System.out.println("Dining Philosophers simulation complete.");
    }
}
