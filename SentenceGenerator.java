import java.util.*;

public class SentenceGenerator extends Generator {
   
   /**
   * Constructor for a SentenceGenerator
   * Sets the data
   * @param data data to set
   */
   public SentenceGenerator (String [] [] data) {
      
      this.setData(data);
   }
   
   /**
   * Gets a random sentence from data, which is a list of sentences
   * @return sentence
   */
   public String getSentence() {
      return getData(0, (int)(Math.random()*getDataZeroLength()));
   }
}