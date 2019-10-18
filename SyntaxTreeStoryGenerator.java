import java.util.*;

public class SyntaxTreeStoryGenerator extends Generator {

   private ArrayList<String> Nouns;
   private ArrayList<String> Verbs;
   private ArrayList<String> Adjectives;
   private ArrayList<String> Adverbs;
   private ArrayList<String> Conjunctions;
   private ArrayList<String> SubCons;
   private ArrayList<String> Prepositions;
   private ArrayList<String> Transitives;
   private ArrayList<String> Protagonists;//SUPER INEFFICIENT. FIX.
   private String Protagonist;

   public String getProtagonist() {
      return Protagonist;
   }
   
   public void analyze (String [] [] data) {
      for (int a = 0; a < data.length; a++) {
         if (data[a][0].equals("NOUN")) {
            Nouns.add(data[a][1]);
         }
         else if(data[a][0].equals("ADVE")) {
            Adverbs.add(data[a][1]);
         }
         else if(data[a][0].equals("VERB")) {
            Verbs.add(data[a][1]);
         }
         else if(data[a][0].equals("ADJE")) {
            Adjectives.add(data[a][1]);
         }            
         else if(data[a][0].equals("CONJ")) {
            Conjunctions.add(data[a][1]);
         }
         else if(data[a][0].equals("SUBC")) {
            SubCons.add(data[a][1]);
         }
         else if(data[a][0].equals("PREP")) {
            Prepositions.add(data[a][1]);
         }
      }
   }
   
   public SyntaxTreeStoryGenerator (Map<String, Set<String>> data) {
      //this.setData(data);
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
      Iterator i = data.get("PROT").iterator();
      Protagonist = (String)i.next();
      //this.analyze(data);
           
   }
   
      
   public enum Clause {
      CLAUSE, SUBORDINATE, CONJUNCTION, COMMA, PROTCLAUSE
   }
   
   public enum Phrase {
      NOUN, VERB, TRANSITIVE, PREPOSITIONAL, PARTICIPAL, INDIRECTNOUN, COMMA, CONJUNCTION, SUBCON, PROT
   }
   
   
   public String getRandom (ArrayList<String> strings) {
      return strings.get((int)(Math.random()*(strings.size())));
   }
   
   
   public String randomNoun () {
      return getRandom(Nouns);
   }
   public String randomVerb () {
      return getRandom(Verbs);
   }

   public String randomAdjective () {
      return getRandom(Adjectives);
   }   
   public String randomAdverb () {
      return getRandom(Adverbs);
   }
   public String randomSubcon () {
      return getRandom(SubCons);
   }
   public String randomConjunction() {
      return getRandom(Conjunctions);
   }
   public String randomPreposition () {
      return getRandom(Prepositions);    
   }      
   public String randomTransitive() {
      return getRandom(Transitives);
   }
   public String getWords (ArrayList<Phrase> phrases) {
      String [] [] data = this.getData();
      String words = "";
      for (Phrase p : phrases) {
        
         if (p.equals(Phrase.COMMA)) {
            //First, get rid of the space after the last word (no spaces before commas!)
            words = words.substring(0, words.length()-1);
            //Then add a comma.
            words += ", ";
         }
         if (p.equals(Phrase.PROT)) {
            words += getProtagonist();
            words += " ";
         }
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
                  words+="an ";
               }
               else {
                  words += "a ";
               }
            }
            else {
               words +="the ";
            }
            
            words += tempWord + " ";
            if (secondTempWord != "") {
               words += secondTempWord + " ";
            }
         
         
         }


         else if(p.equals(Phrase.INDIRECTNOUN) ) {
            if (Math.random()<.5) {
               words+="to ";
            }
            else {
               words += "for ";
            }
         }
         else if(p.equals(Phrase.VERB)) {
            if (Math.random() <.5) {
               words += randomAdverb();
               words += " ";
            }
            words += randomVerb();
            words += " ";
         }
         else if (p.equals(Phrase.TRANSITIVE)) {
            if (Math.random() <.5) {
               words += randomAdverb();
               words+= " ";
            }
            words += randomTransitive();
            words += " ";
         }
               
         else if(p.equals(Phrase.PREPOSITIONAL)) {
            words += randomPreposition();
            words += " ";
         
         }
         else if(p.equals(Phrase.SUBCON)) {
            words += randomSubcon();
            words += " ";
         }
         else if(p.equals(Phrase.CONJUNCTION)) {
            words += randomConjunction();
            words += " ";
         
         }


                                                            
      }
      
      //Capitalize the sentence!
      char firstLetter = Character.toUpperCase(words.charAt(0));
      words = firstLetter + words.substring(1, words.length()-1);
      words += ".";
      return words;
   }
   
   public ArrayList<Phrase> getPhrases (ArrayList<Clause> clauses) {
      ArrayList<Phrase> phrases = new ArrayList<Phrase>();
      for (Clause c : clauses) {
         if (c.equals(Clause.PROTCLAUSE)) {
            phrases.add(Phrase.PROT);
            if (Math.random() <.5) {
                phrases.add(Phrase.TRANSITIVE);
                phrases.add(Phrase.NOUN);
                phrases.add(Phrase.INDIRECTNOUN);
                phrases.add(Phrase.NOUN);
             }
             else {
                phrases.add(Phrase.VERB);
             }
          }

         if (c.equals(Clause.CLAUSE)) {
            phrases.add(Phrase.NOUN);
            if (Math.random() <.5) {
                phrases.add(Phrase.TRANSITIVE);
                phrases.add(Phrase.NOUN);
                phrases.add(Phrase.INDIRECTNOUN);
                phrases.add(Phrase.NOUN);
             }
             else {
                phrases.add(Phrase.VERB);
             }
         }
         else if (c.equals(Clause.COMMA) ) { 
            phrases.add(Phrase.COMMA);
         }
         
         else if (c.equals(Clause.SUBORDINATE)) {
            
            phrases.add(Phrase.SUBCON);
         
         }
         
         else if (c.equals(Clause.CONJUNCTION)) {
            
            phrases.add(Phrase.CONJUNCTION);
            
         }
      
      }
      return phrases;
   }
      
      
            
   
   public Clause randomClause() {
      double r = Math.random();
      if (r<.33) {
         return Clause.CLAUSE;
      }
      if (r<.66) {
         return Clause.SUBORDINATE;
      }
      else {
         return Clause.CONJUNCTION;         
      }
   }
   
      
   public String getSentence () {
      
      ArrayList<Clause> clauses = getClauses();
      ArrayList<Phrase> phrases = getPhrases(clauses);
      String sentence = getWords (phrases);
      return sentence;
   }
   
   
   
   public ArrayList<Clause> getClauses () {
      ArrayList<Clause> sentence = new ArrayList<Clause>();
      sentence.add(Clause.PROTCLAUSE);
      if (Math.random()<.5) {
         return sentence;
      }
      if (Math.random()<.5) {
         sentence.add(Clause.COMMA);
         sentence.add(Clause.CONJUNCTION);
         sentence.add(Clause.CLAUSE);
      }
      else {
         sentence.add(0, Clause.COMMA);
         sentence.add(0, Clause.CLAUSE);
         sentence.add(0, Clause.SUBORDINATE);
      }
      return sentence;
   }
      
  
      


}
   