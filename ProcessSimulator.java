import java.io.*;
import java.util.*;

public class ProcessSimulator {
  public static void run(String filename) throws Exception {
    List<ProcessThread> threads = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br,readLine()) != null) {
      }
