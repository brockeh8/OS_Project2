import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
  private final int id, meals;
  private final Semaphore[] forks;
  
  public Philosopher(int id, Semaphore[] forks, int meals){
    this.id = id;
    this.forks = forks;
    this.meals;
  }
    @Override
    public void(){
      int left = id - 1;
      int right = id % forks.length;
      int first = Math.min(left, right);
      int second = Math.max(left, right);
      
      for (int m = 1; m <= meals; m++){
        // think
        try{ Thread.sleep(500);} catch (InterruptedException e){ Thread.currentThread().interrupt();}
        
        // wait for forks
        System.out.printf("[Philosopher %d] Waiting for forks...%n",id);
        try{
          forks[first].acquire();
          forks[second].acquire();
        }catch (InterruptedException e){
          Thread.currentThread().interrupt();
          return;
        }
        
        // picked up & eat
        System.out.printf("[Philosopher %d] Picked up fork %d and %d%n", id, first + 1, second + 1);
        System.out.printf("[Philosopher %d] Eating...%n", id);
        try { Thread.sleep(1000);} catch (InterrupedException e){ Thread.currentThread().interrupt();}
        
        // release
        forks[first].release();
        forks[second].release();
        System.out.printf("[Philosopher %d] Released forks%n", id);
      }
    }
}
