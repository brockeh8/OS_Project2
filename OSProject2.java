public class OSProject2 {
  public static void main(String[] args) throws Exception {
    Process.Simulator.run("processes.txt");
    DiningManager.run(5,3);
  }
}
