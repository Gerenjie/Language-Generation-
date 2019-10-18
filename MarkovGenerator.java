import java.util.*;

public class MarkovGenerator extends Generator {
   
   /**
   * For analysis of the data. It branches to every word, each of which branch to the words they are followed by.
   */
   private MarkovTree Analysis;
   
   /**
   * Contains each word that can start a sentence.
   */
   private ArrayList<String> StarterWords;
   
   
   /**
   * Initializes variables, sets data, and analyzes data.
   * @param data String array of sentences
   */
   public MarkovGenerator (String [] [] data) {
      StarterWords = new ArrayList<String>();
      this.setData(data);
      this.analyze();
   }
   
   /**
   * gets a random sentence-starting word
   * @return a random word that can start a sentence
   */
   public String getRandomStarterWord () {
      Random r = new Random();
      return StarterWords.get(r.nextInt(StarterWords.size()));
   }
   
   /**
   * Accessor method for StarterWords
   * @return StarterWords
   */
   public ArrayList<String> getStarters () {
      return StarterWords;
   }
   
   
   /**
   * Creates a sentence using Analysis.
   * Gets a random starter word, then loops, finding a word that follows the previous word, until it gets to 
   * 26 words or it hits a word that always ends a sentence. Sentences tend to be 26 words long.
   * @return Sentence generated sentence
   */
   public String getSentence () {
      String sentence = "";
      int ii = 0;
      
      while (sentence.length()<30) {
         //If the file doesn't have enough content, this will get stuck, so I throw an exception instead.
         ii ++;
         if (ii>1000) {
            throw new IllegalArgumentException();
         }
         
         
         //Start out the sentence with a random starter word
         sentence = getRandomStarterWord();
         MarkovTree mt = Analysis.findBranch(sentence);
         sentence+= " ";
         
         
         try {
            int a = 0;
            //Put 25 chained words in a sentence, unless you hit a word with no branches. In that case, end it.
            while (a<25) {
               if (a!=0) {
                  sentence+=" ";
               }
               a++;
               
               //Get a random branch from this word, add that word to the sentence, and make that word the new node.
               MarkovTree mt2 = mt.getRandomBranch();
               mt=Analysis.findBranch(mt2.value());
               sentence+=mt.value();
            }
         }
         //If we run out of words, end the sentence (remove the last space).
         catch (NullPointerException e) {
            sentence = sentence.substring(0, sentence.length()-1);
      
         }
         sentence += ".";
      }
      return sentence;
      
   }
   
   
   /**
   * Accessor method for this Generator's analysis's branches
   * @return branches
   */         
   public int branches () {
      return Analysis.branches();
   }         
   
   /**
   * Gets a random branch from this Generator's Analysis
   * @return random branch
   */
   public MarkovTree getRandomBranch () {
      return Analysis.getRandomBranch();
   }
  
   /**
   * MarkovGenerators' toStrings are just their Analysis's toString
   * @return String representation of this MarkovGenerator's analysis
   */
   public String toString () {
      return Analysis.toString();
   }
   
   /**
   * Gets a branch from Analysis with a certain value
   * @param str value to find
   * @return branch with value str
   */
   public MarkovTree findBranch (String str) {
      return Analysis.findBranch(str);
   }
   
   /**
   * Prints this generator's analysis. This was used for testing.
   */
   public void printAnalysis() {
      System.out.println(Analysis);
   }
   
   /**
   * Accessor method for Analysis
   * @return Analysis
   */
   public MarkovTree getAnalysis () {
      return Analysis;
   }
   
   
   /**
   * Puts every word into a MarkovTree, and branches each word to every word that follows it.
   * Also puts all the sentence-starting words in StarterWords.
   */
   public void analyze () {
      try {
         Analysis = new MarkovTree(null);
         for (String str: this.getData()[0]) {
            try {
                Scanner sc = new Scanner(str);
                String thisWord = sc.next();
                Analysis.addWord(thisWord);
                StarterWords.add(thisWord);
                String nextWord = "";
                while (sc.hasNext()) {
                   try {
                      nextWord = sc.next();
                      Analysis.addWord(thisWord);
                      Analysis.findBranch(thisWord).addWord(nextWord);
                      thisWord = nextWord;   
                   }
                   catch (NullPointerException e) {
                   }
                         
               }
                
            }
            catch (NullPointerException e) {
            }
         }
      }
      catch (NoSuchElementException e) {
       
      }
   }
   
}
   