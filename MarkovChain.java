import java.io.*;
import java.util.*;

public class MarkovChain {

   private static ArrayList<String> words;
   private static ArrayList<String> sentences;
   
   public static void main(String [] args) {
      
      File f = new File("Dracula.txt");
      String str = "";
      FileReader fr = null;
      BufferedReader br = null;
      
      try {
         fr = new FileReader(f);
         br = new BufferedReader(fr);
      }
      catch (IOException e) {
         System.out.println(e);
      }         
      try {
         for (int a = 0; a  < 10000; a++) {
            str += br.readLine() + " ";
         }
      }
      catch (IOException e) {
      
      }
      
      Scanner sc = new Scanner (str);
      
     words = new ArrayList<String>();
      
      while (sc.hasNext()) {
         words.add(sc.next());
      }
      while (true) {
         printRandomWord();
         try {
            Thread.sleep(100);
         }
         catch (InterruptedException e) {
            System.exit(0);
         }
      }
   }
   
   public static void printRandomWord () {
      Random r = new Random();
      System.out.println(words.get(r.nextInt(words.size()+1)));
   }
   

   
}