import java.io.*;
import java.util.*;

public class MarkovTree {
   
   /**
   * contains the word that this MarkovTree node represents.
   */
   private String word;
   


   /**
   * contains this node's branches, all the words that can follow it. 
   */
   private ArrayList<MarkovTree> branches;
   
   /**
   * contains the weight of each branch (how often each word follows each other word).
   */
   private ArrayList<Integer> branchWeight;
   
   
   /**
   * contains the weight of all of its branches. 
   */
   private int branchSum;
   
   
   /**
   * Constructor sets the value of the word, and initializes all of its class variables.
   * @param str value of this node
   */
   public MarkovTree (String str) {
      word = str;
      branches = new ArrayList<MarkovTree>();
      branchWeight = new ArrayList<Integer>();
      branchSum = 0;
   }
   
   
   /*
   * This method finds all of the branches that stem from a markov tree,  or any of its branches, or any of theirs... which
   * have a certain value (word).
   * @param str Value to find the branches of
   * @return branches ArrayList of branches that have that value
   */
   public ArrayList<MarkovTree> findAllBranches (String str) {
      ArrayList<MarkovTree> allBranches = new ArrayList<MarkovTree>();
      if (branches.size()>0) {
         for (MarkovTree mt : branches) {
            mt.findAllBranches(str);
         }
      }
      if (this.value().equals(str)) {
         allBranches.add(this);
      }
      return branches;
   }
      
 
         
   /**
   * Adds a new branch to a tree
   * @param str value of the branch to add
   */
   public void addWord (String str) {
      branchSum++;
      for (int a = 0; a < branches.size(); a++) {
         if (branches.get(a).value().equals(str) ) {
            branchWeight.set(a, new Integer(branchWeight.get(a).intValue()+1));
            return;
         }
         
      }
      
      branches.add(new MarkovTree(str));
      branchWeight.add(new Integer(1));
   }
   
   
   /**
   * Finds the branch of a certain value that stems directly from one node.
   * @param check value of the branch to find
   * @return mt branch with the value check
   */
   public MarkovTree findBranch (String check) {
      for (MarkovTree mt : branches) {
         if (mt.value().equals(check)) {
            return mt;
         }
      }
      return null;
   }
   
   
   /**
   * Returns a random branch from a tree
   * @return branch random branch
   */
   public MarkovTree getRandomBranch () {
      if (branchSum == 0) {
         return null;
      }
      Random r = new Random();
      int num = r.nextInt(branchSum+1);
      MarkovTree branch = branches.get(0);
      int a = -1;
      while (num>0) {
         a++;
         num -= branch.branches();
            try {
               branch = branches.get(a);         
            }
            catch (IndexOutOfBoundsException e) {
               return branch;
            }
      }
      return branch;
   
   }
      
         
   /**
   * Accessor method for branchsum
   * @return branchSum branchSum
   */   
   public int branches() {
      return branchSum;
   }   
   
   /**
   * Accessor method for value
   * @return word value of this node
   */
   public String value () {
      return word;
   }
   
   /**
   * Accessor method for the branches of a tree
   * @return branches
   */
   public ArrayList<MarkovTree> getBranches () {
      return this.branches;
   }
   
   /**
   * toString Method. Not a great representation, but it was very useful for debugging.
   * @return string represtation of a tree
   */
   public String toString () {
      String str = word + "("+branchSum+") ";
      for (MarkovTree mt : branches) {
         str+=mt.toString();
      }
      return str;
   }
   
   
   
   /**
   * Adds a branch with value addMe to every node of value addTo that stems from a tree
   * @param addTo branch value to add addMe to
   * @param addMe value of branch to add to everything
   */   
   public void addAll (String addTo, String addMe) {
      if (this.value().equals(addTo)) {
         this.addWord(addMe);
      }
      
      
      if (this.branches.size()>0) {
         for (MarkovTree mt : branches) {
            mt.addAll(addTo, addMe);
         }
      }
      else {
         return;
      }
   }
      
   
}