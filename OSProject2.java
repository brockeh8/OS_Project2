public class OSProject2 {
  public static void main(String[] args) throws Exception {
    ProcessSimulator.run("processes.txt");
    DiningManager.run(5,2);
  }
}
