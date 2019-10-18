public class WordGenerator extends Generator {
   
   /**
   * Constructor for a WordGenerator
   * Sets data
   * @param data new data array
   */
   public WordGenerator (String [] [] data) {
      this.setData(data);
   }
   
   /**
   * Gets a random word from the data array
   * @return random word
   */
   public String getWord () {
      return getData(0, (int)(Math.random()*getDataZeroLength()));
   }   
   
   
   
   /**
   * Creates a full sentence by putting random words after other random words.
   * @return sentence random sentence.
   */
   public String getSentence () {
      boolean haveASentence = false;
      String sentence = "";
      
      while (!haveASentence) {
         try {
            sentence = "";
            int a = (int)(Math.random()*20);
            for (int b = 0; b < a; b++) {
               sentence += getWord();
               sentence += " ";
            }
            //Capitalize the first letter of each sentence. 
            
            String c = "";
            c += sentence.charAt(0);
            c=c.toUpperCase();
            sentence = sentence.substring(1);
            sentence = c + sentence;
            sentence = sentence.substring(0, sentence.length()-1);
            sentence += ".";
            haveASentence = true;
         }
         catch (StringIndexOutOfBoundsException e) {
         
         }
      }
      
      return sentence;
   }
}