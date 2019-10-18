import java.util.*;
public abstract class Generator {
   
   /**
   * This String array represents all of the data this Generator has to work with.
   * For some Generators, this will be a 1d array of sentences. For others it might be
   * An array of words, along with part of speech, or something else.
   */
   private String [][] data;
   
   
 
   
   
   /**
   * This method will be the main variation between Generators. Each Generator will
   * generate its sentences differently very differently.
   * @return sentence
   */
   public abstract String getSentence();
   
   /**
   * Mutator method for the Data array
   * @param newDat new data
   */
   public void setData (String [] [] newDat) {
      data = newDat;
   }
   
   /**
   * This method returns the String in the data array at a given index.
   * @param x index to grab (part one)
   * @param y index to grab (part two)
   * @return grabbed thing
   */
   public String getData (int x, int y) {
      try {
         return data[x][y];
      }
      catch (ArrayIndexOutOfBoundsException e) {
         return null;
      }
   }
   
   /**
   * Accessor method for the Data object.
   * @return data object
   */
   public String [] [] getData() {
      return this.data;
   }
   
   /**
   * Accessor method for the data object's length
   * @return data's length;
   */
   public int getDataLength() {
      return data.length;
   }
   
   /**
   * Accessor method for the data object's first array's length
   * @return length of the first array
   */
   public int getDataZeroLength () {
      return data[0].length;
   }
   

}  