import java.util.*;

public class SyntaxTreeGenerator extends Generator {

   /**
   * This is an ArrayList of all of the nouns this Generator uses to make sentences.
   */
   private ArrayList<String> Nouns;
  
   /**
   * This is an ArrayList of all of the (intransitive) verbs this Generator uses to make sentences.
   */
   private ArrayList<String> Verbs;
   
   /**
   * This is an ArrayList of all of the adjectives this Generator uses to make sentences.
   */
   private ArrayList<String> Adjectives;
   
   /**
   * This is an ArrayList of all of the adverbs this Generator uses to make sentences.
   */
   private ArrayList<String> Adverbs;
   
   /**
   * This is an ArrayList of all of the conjunctions this Generator uses to make sentences.
   */
   private ArrayList<String> Conjunctions;
   
   /**
   * This is an ArrayList of all of the subordinate conjunctions this Generator uses to make sentences.
   */
   private ArrayList<String> SubCons;
   
   /**
   * This is an ArrayList of all of the prepositions this Generator uses to make sentences.
   */
   private ArrayList<String> Prepositions;
   
   /**
   * This is an ArrayList of all of the transitive verbs this Generator uses to make sentences.
   */
   private ArrayList<String> Transitives;



   /**
   * This is the constructor for a SyntaxTreeGenerator.
   * It initializes the ArrayLists, and adds the sets of words into the ArrayLists.
   * @param data a Map of sets of words to parts of speech for easy loading into ArrayLists.
   */
   public SyntaxTreeGenerator (Map<String, Set<String>> data) {
      Nouns = new ArrayList<String>();
      Nouns.addAll(data.get("NOUN"));
      Verbs = new ArrayList<String>();
      Verbs.addAll(data.get("VERB"));
      Adjectives = new ArrayList<String>();
      Adjectives.addAll(data.get("ADJE"));
      Adverbs = new ArrayList<String>();
      Adverbs.addAll(data.get("ADVE"));
      Conjunctions = new ArrayList<String>();
      Conjunctions.addAll(data.get("CONJ"));
      SubCons = new ArrayList<String>();
      SubCons.addAll(data.get("SUBC"));
      Prepositions = new ArrayList<String>(); 
      Prepositions.addAll(data.get("PREP"));
      Transitives = new ArrayList<String>();
      Transitives.addAll(data.get("TRAN"));
      if (Nouns.size()<1 || Verbs.size()<1 || Adjectives.size()<1 || Adverbs.size()<1 || Conjunctions.size()<1 || SubCons.size()<1 || Prepositions.size()<1 || Transitives.size()<1) {
         throw new IllegalArgumentException ();
      }
           
   }
   
   
   
   
      
      
      
   /**
   * Gets a randomly generated sentence from the dictionary. 
   * @return sentence
   */               
   public String getSentence () {
      
      //Generate random clauses, break them down into phrases, then break phrases down into words.
      ArrayList<Clause> clauses = getClauses();
      ArrayList<Phrase> phrases = getPhrases(clauses);
      return getWords (phrases);
   }
   
   
   
   
   /**
   * This enum is used for breaking sentences down into Clauses.
   */   
   public enum Clause {
      CLAUSE, SUBORDINATE, CONJUNCTION, COMMA
   }
   /**
   * This enum is used for breaking clauses down into phrases.
   * Noun phrase
   * Verb phrase (intransitive)
   * Transitive verb prhase)
   * Prepositional phrase (kinda)
   * Indirect noun phrase
   * Comma
   * Conjunction
   * Subordinate Conjunction
   */
   public enum Phrase {
      NOUN, VERB, TRANSITIVE, PREPOSITIONAL, INDIRECTNOUN, COMMA, CONJUNCTION, SUBCON
   }
   
   /**
   * This method gets a random String from an ArrayList of Strings.
   * I use it to get words randomly from the ArrayLists that make up my dictionary.
   * @param strings ArrayList to choose from
   * @return word randomly chosen
   */
   public String getRandom (ArrayList<String> strings) {
      return strings.get((int)(Math.random()*(strings.size())));
   }
   
   /**
   * This method gets a random noun from the noun dictionary
   * @return random noun.
   */
   public String randomNoun () {
      return getRandom(Nouns);
   }
   
   /**
   * This method gets a random intransitive verb from the verb dictionary
   * @return random verb.
   */   
   public String randomVerb () {
      return getRandom(Verbs);
   }

   
   /**
   * This method gets a random adjective from the adjective dictionary
   * @return random adjective.
   */   
   public String randomAdjective () {
      return getRandom(Adjectives);
   }   
   
   /**
   * This method gets a random adverb from the adverb dictionary
   * @return random adverb.
   */  
   public String randomAdverb () {
      return getRandom(Adverbs);
   }
   
   /**
   * This method gets a random subordinate conjunction from the subcon dictionary
   * @return random subordinate conjunction.
   */   
   public String randomSubcon () {
      return getRandom(SubCons);
   }
   
   /**
   * This method gets a random conjunction from the conjunction dictionary
   * @return random conjunction.
   */   
   public String randomConjunction() {
      return getRandom(Conjunctions);
   }
   
   /**
   * This method gets a random preposition from the preposition dictionary
   * @return random preposition.
   */   
   public String randomPreposition () {
      return getRandom(Prepositions);    
   }      
   
   /**
   * This method gets a random transitive verb from the transitive dictionary
   * @return random t-verb.
   */   
   public String randomTransitive() {
      return getRandom(Transitives);
   }
   
   
   /**
   * This method takes an ArrayList of phrases, and turns it into a real sentence.
   * It goes through the ArrayList, and replaces each phrase with a series of randomly chosen words
   * of the parts of speech that make sense for these phrases. 
   * @param phrases ArrayList of phrases to turn into a real sentence.
   * @return words sentence (String) to return.
   */
   public String getWords (ArrayList<Phrase> phrases) {
      String sentence = "";
      
      //Go through each phrase, and add words to represent the  
      for (Phrase p : phrases) {
         
         //Add a comma
         if (p.equals(Phrase.COMMA)) {
            //First, get rid of the space after the last word (no spaces before commas!)
            sentence = sentence.substring(0, sentence.length()-1);
            //Then add a comma and a space.
            sentence += ", ";
         }
        
        
         //Noun phrases are nouns with an article ("the ball"), and sometimes an adjective ("the green ball");
         if (p.equals(Phrase.NOUN)) {
            String tempWord;
            String secondTempWord;
            if (Math.random()<.5) {
               tempWord = randomNoun();
               secondTempWord = "";
            }
            else {
               tempWord = randomAdjective();
               secondTempWord = randomNoun();
            }
            char firstChar = tempWord.charAt(0);
           
            if (Math.random()<.5) {   
              if (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o') {
                  sentence+="an ";
               }
               else {
                  sentence += "a ";
               }
            }
            else {
               sentence +="the ";
            }
            
            sentence += tempWord + " ";
            if (secondTempWord != "") {
               sentence += secondTempWord + " ";
            }
         
         
         }
         
         
         //An indirect noun phrase is just a special preposition used for indirect nouns.
         //For example, "he kicked a ball TO the roof" or "he drove a car FOR the boss".
         else if(p.equals(Phrase.INDIRECTNOUN) ) {
            if (Math.random()<.5) {
               sentence+="to ";
            }
            else {
               sentence += "for ";
            }
         }
         
         //A verb phrase is an intransitive verb, maybe with an adverb.
         //For example "run" or "quickly run".
         else if(p.equals(Phrase.VERB)) {
            if (Math.random() <.5) {
               sentence += randomAdverb();
               sentence += " ";
            }
            sentence += randomVerb();
            sentence += " ";
         }
         
         
         //A transitive verb phrase is a transitive verb, maybe with an adjective.
         else if (p.equals(Phrase.TRANSITIVE)) {
            if (Math.random() <.5) {
               sentence += randomAdverb();
               sentence+= " ";
            }
            sentence += randomTransitive();
            sentence += " ";
         }
         
         //A prepositional phrase is just a preposition (not in English class, but here it is).      
         else if(p.equals(Phrase.PREPOSITIONAL)) {
            sentence += randomPreposition();
            sentence += " ";
         
         }
         
         //A Subordinate conjunction phrase is just a subordinate conjunction.
         else if(p.equals(Phrase.SUBCON)) {
            sentence += randomSubcon();
            sentence += " ";
         }
         
         //A conjunction phrase is just a conjunction.
         else if(p.equals(Phrase.CONJUNCTION)) {
            sentence += randomConjunction();
            sentence += " ";
         
         }


                                                            
      }
      
      //Capitalize the first letter of each sentence.
      char firstLetter = Character.toUpperCase(sentence.charAt(0));
      sentence = firstLetter + sentence.substring(1, sentence.length()-1);

      //Sentences end in periods.
      sentence += ".";
      
      //All done!
      return sentence;
   }
   
   
   
   public ArrayList<Phrase> getPhrases (ArrayList<Clause> clauses) {
      ArrayList<Phrase> phrases = new ArrayList<Phrase>();
      
      //For each clause, replace it in the Phrases list with...
      for (Clause c : clauses) {
         //Clauses need a subject and a verb. If the verb is transitive, it needs an indirect noun.
         if (c.equals(Clause.CLAUSE)) {
            phrases.add(Phrase.NOUN);
            if (Math.random() <.5) {
                phrases.add(Phrase.TRANSITIVE);
                phrases.add(Phrase.NOUN);
                if (Math.random()<.3) {
                    phrases.add(Phrase.INDIRECTNOUN);
                }
                else {
                    phrases.add(Phrase.PREPOSITIONAL);
                }
                phrases.add(Phrase.NOUN);
             }
             else {
                phrases.add(Phrase.VERB);
             }
         }
         
         //Commas are just commas.
         else if (c.equals(Clause.COMMA) ) { 
            phrases.add(Phrase.COMMA);
         }
         
         //Subordinate conjunctions are just subordinate conjunctions.
         else if (c.equals(Clause.SUBORDINATE)) {
            
            phrases.add(Phrase.SUBCON);
         
         }
         
         //Conjunctions are just conjunctions.
         else if (c.equals(Clause.CONJUNCTION)) {
            
            phrases.add(Phrase.CONJUNCTION);
            
         }
      
      }
      return phrases;
   }
      
      
   /**
   * Gets an ArrayList of Clauses that represents a sentence.
   * @return clauses
   */
   public ArrayList<Clause> getClauses () {
      ArrayList<Clause> sentence = new ArrayList<Clause>();
      //Every sentence needs at least one clause.
      sentence.add(Clause.CLAUSE);
      
      
      //Half of all sentences are just one clause.
      if (Math.random()<.5) {
         return sentence;
      }
      
      //A quarter of sentences have two clauses joined by a conjunction and a comma.
      if (Math.random()<.5) {
         sentence.add(Clause.COMMA);
         sentence.add(Clause.CONJUNCTION);
         sentence.add(Clause.CLAUSE);
      }
      //The rest have two a subordinate conjunction, then two clauses with a comma between.
      else {
         sentence.add(0, Clause.COMMA);
         sentence.add(0, Clause.CLAUSE);
         sentence.add(0, Clause.SUBORDINATE);
      }
      return sentence;
   }
      

   
     
      


}
   