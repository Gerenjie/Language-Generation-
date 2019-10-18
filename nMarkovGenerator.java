public class nMarkovGenerator extends MarkovGenerator {
  
   public void addAll (String addTo, String addMe) {
      this.getAnalysis().addAll(addTo, addMe);
   }
  
   public nMarkovGenerator (String [] [] data) {
      this.setData(data);
   }
  
   public void setAnalysis (MarkovTree newAnalysis) {
      this.Analysis = newAnalysis;
   }
   
   public static void main(String [] args) {
      String [] [] words = { {"My"}, {"Name"}};
      nMarkovGenerator nmg = new nMarkovGenerator(words);
   }
   
   public void analyze () {
      try {
         setAnalysis(new MarkovTree(null));
         for (String str: this.getData()[0]) {
            Scanner sc = new Scanner(str);
            String thisWord = sc.next();
            Analysis.addWord(thisWord);
            StarterWords.add(thisWord);
            String nextWord = "";
            while (sc.hasNext()) {
               nextWord = sc.next();
               Analysis.addWord(thisWord);
               Analysis.addAll(thisWord, nextWord);
               thisWord = nextWord;               
                     
            }
         }
      }
      catch (NoSuchElementException e) {
       
      }
   }
}