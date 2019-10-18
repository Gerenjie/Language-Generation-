import java.util.*;

public class RandomGenerator extends Generator {
   
   private Random r;
   
   
   public RandomGenerator (String [] [] input) {
      this.setData(input);
      r = new Random ();
   }
   
   public String getSentence () {
      String sentence = "";
      int wordsInThisSentence = Math.abs(r.nextInt()%7)+4;
      
      int a = 0;
      for (a = 0; a < wordsInThisSentence; a++) {
         sentence += getData(0, r.nextInt(this.getDataZeroLength()));
         sentence += " ";
      }
      System.out.println(a);
      return sentence;
   }
}
      
      
   