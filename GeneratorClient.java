import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class GeneratorClient {
   
   /**
   * Get user input on what kind of sentences they want, and use generators to 
   * generate their sentences. 
   * @param args input
   */
   public static void main(String [] args) {
      Scanner scan = new Scanner(System.in);
      
      //Keep going until the user asks you to stop, which is represented by a break command.
      while (true) {
         
         //Get ready to create a generator
         Generator g = null;
         String generatorType;
         
         //Get user input about what generator to make
         while (true) {
            System.out.println("How would you like to generate sentences? (Complete Sentences: CS     Random Words: RW       Markov Chain: MC    Syntax Tree: ST)");
            String response = scan.nextLine();         
            if (response == null) {
            }
            else {
               if (response.equals("CS")) {
                  generatorType = "CS";
                  break;
               }
               else if (response.equals("MC")) {
                  generatorType = "MC";
                  break;
               }
               else if (response.equals("RW")) {
                  generatorType = "RW";
                  break;
               }
               
               else if (response.equals("ST")) {
                  generatorType = "ST";
                  break;
               }

            }
            System.out.print("Please try again. ");
         }
  
         //Get ready to read a file
         File f = null;
         FileReader fr = null;
         BufferedReader br = null;
         Scanner sc = null;
         
         
         
         //get user input on what file to read        
         while (true) {
            System.out.println("What text file would you like to base your sentences on?");
            String response = scan.nextLine();
            if (response == null) {
            }
            else {
               try {
                  f = new File (response);
                  fr = new FileReader(f);
                  br = new BufferedReader(fr);
                  sc = new Scanner(f);  
         
         
         
                  //Make a markov generator
                  if (generatorType.equals("MC")) {
                     String [] [] data = {getSentences(br), {}}; 
                     
                     g = new MarkovGenerator(data);
                     
                     //Check to make sure the input file works for this MarkovGenerator.
                     //If there isn't enough in the file, an exception gets thrown, so I need to check here
                     //So it can reprompt the user immediately for a new input.
                     g.getSentence();
                  }
                  
                  //Make a random-word generator
                  else if (generatorType.equals("RW")) {
                     String [] [] data = {getWords(br), {} };
                     g = new WordGenerator(data);
                  }
                  
                  //Make a syntax tree generator
                  else if (generatorType.equals("ST")) {
                     g = new SyntaxTreeGenerator(getDictionary(sc));
                  }
                  
                  //Make a complete-sentence generator
                  else if (generatorType.equals("CS")) {
                     String [] [] data = {getSentences(br), {}};
                     
                     g = new SentenceGenerator(data);
                  }
                  System.out.println("Generator ready to generate sentences! Say something that starts with a letter y to get a sentence.");
                  break;
                                  
               }
               catch (NullPointerException e) {
                  System.out.println("That file isn't formatted like a dictionary. Please try again.");
               }
               catch (FileNotFoundException e) {
                  System.out.println("I can't find that file. Please try again.");
               }
               catch (IllegalArgumentException e) {
                  System.out.println("That file doesn't have enough in it to base sentences on. Please try again.");
               }
               catch (Exception e) {
                  System.out.println("There was a problem. Maybe the file was too short? Please try again.");
               }
            }
         }
         
         
         //Keep giving the user sentences until they stop asking for them. Then break out.
         while (true) {
            try {
               //As soon as the user stops saying yes, break out.
               if (scan.nextLine().toLowerCase().charAt(0) != 'y') {
                  break;
               }
            }
            catch (Exception e) {
               break;
            }   
            
            //Get the sentence!      
            for (int axe = 0; axe < 50; axe ++) {
            System.out.println(g.getSentence());
            }
            
         }
         System.out.println("Would you like to load another generator?");
         
         //If they want to load another generator, keep going. Otherwise, stop the program.
         while (true) {
            try {
               if (scan.nextLine().toLowerCase().charAt(0) != 'y') {
                  System.exit(0);
               }
               else {
                  break;
               }
            }
            catch (Exception e) {
               System.out.println("Please enter a response. Would you like to make another sentence?");
              
            }
         }
            
            
            
            
      }
    
   }
   
   
   /**
   * Gets a String array of sentences from a file, via a bufferedreader
   * @param br BufferedReader to read from
   * @return Array of sentences
   */
   public static String [] getSentences (BufferedReader br) {
      ArrayList<String> sentences = new ArrayList<String>();
      String fullText = "";
      String str = "";
      try {
         //As long as there's more text, add the next line to a String.
         while (str!= null) {
            fullText += str;
            str = br.readLine();  
         }
         
         //Then go through the String and move all sentences into an ArrayList of sentences.
         Scanner sc = new Scanner(fullText);
         sc.useDelimiter(Pattern.compile("\\.|\\?|\\!"));
         while (sc.hasNext()) {
            sentences.add(sc.next().trim());
         }
      }
      catch (IOException e) {
      }
      
      
      scrubData(sentences);
      return (sentences.toArray(new String [sentences.size()]));
   }                
   
   
   /**
   * Removes any excess Strings, such as null ones, spaces, or empty strings.
   * These show up when you have "..." or ". . ." in the text you read from.
   * @param data data to scrub
   * @return data scrubbed data
   */
   public static ArrayList<String> scrubData (ArrayList<String> data) {
      for (int a = 0; a < data.size(); a++) {
         String str = data.get(a);
         if (str.equals("") || str.equals(null) || str.equals(" ")) {
            data.remove(a);
            a--;
         }
      }
      return data;
   }
   
   
   /**
   * Gets a dictionary from a dictionary-formatted text file, and sorts it by
   * parts of speech. Some of these, like protagonist, may not be used in the generators submitted,
   * but were being experimented with, and may be developed in the future. They don't affect the program.
   * @param sc scanner to read from
   * @return maps set of maps of words (organized by part of speech)
   */
   public static Map<String, Set<String>> getDictionary (Scanner sc) {
      try {
         Map<String, Set<String>> maps = new TreeMap<String, Set<String>>();
      
         Set<String> Nouns = new TreeSet<String>();
         Set<String> Transitives = new TreeSet<String>();
         Set<String> Verbs = new TreeSet<String>();
         Set<String> Adjectives = new TreeSet<String>();
         Set<String> Adverbs = new TreeSet<String>();
         Set<String> Conjunctions = new TreeSet<String>();
         Set<String> Subcons = new TreeSet<String>();
         Set<String> Prepositions = new TreeSet<String>();
         Set<String> Protagonist = new TreeSet<String>();
      
         maps.put("NOUN", Nouns);
         maps.put("VERB", Verbs);
         maps.put("ADJE", Adjectives);
         maps.put("CONJ", Conjunctions);
         maps.put("ADVE", Adverbs);
         maps.put("SUBC", Subcons);
         maps.put("PREP", Prepositions);
         maps.put("TRAN", Transitives);
         maps.put("PROT", Protagonist);
            
         
         
         //Add words to the set that corresponds to their part of speech. 
         while (sc.hasNextLine() ) {
            String POS = sc.next();
            String word = sc.nextLine();
            word = word.trim();
            Set<String> thisSet = maps.get(POS);
            thisSet.add(word);
        }
        return maps;        
      }
      catch (NullPointerException e) {
         return null;
      }
   }

   /**
   * Gets a String array of all of the words from a text file.
   * @param br BufferedReader to read from
   * @return Array of words
   */
   public static String [] getWords (BufferedReader br) {
      ArrayList<String> words = new ArrayList<String>();
      String str ="";
      try {
         //While there are more words, put words into the ArrayList.
         while (str != null) {
            str = br.readLine();
            Scanner sc = new Scanner(str);
            while (sc.hasNext()) {
               words.add(sc.next());
            }
         }
         str = br.readLine();         
      }
      catch (IOException e) {
         System.out.println("problem at the bottom of getWords");
      }
      catch (NullPointerException e) {
      }
      return (words.toArray(new String [words.size()]));
   }
         
}