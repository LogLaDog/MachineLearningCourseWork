package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.util.ArrayList;

/**
 * Sets of cases are built in this class from the datasets.
 */

public class Set {
    
    private final int classTotal;     // variable storage
    private final int attributesTotal;
    private int[] binsTotal;    
    private String[] classID; //classID storage
    private ArrayList<Case> cases = new ArrayList<Case>(); //Store cases in array
    public void addCase(Case tcase){ 
        this.cases.add(tcase); 
    }
    public ArrayList<Case> retrieveCase(){ 
        return this.cases; 
    }
    public int retrieveNumberOfClasses(){   // variable returners
        return this.classTotal; 
    } 
    public int retrieveNumberOfCases(){ 
        return this.cases.size(); 
    }
    public int retrieveNumberOfAttributes(){ 
        return this.attributesTotal; 
    }
    public int[] retrieveNumberOfBins(){ 
        return this.binsTotal; 
    }
    public String[] retrieveClassID(){ 
        return this.classID; 
    }

    Set(Set[] sets, int ig){ //class to populate finalize global variables
        this.classTotal = sets[0].retrieveNumberOfClasses(); this.attributesTotal = sets[0].retrieveNumberOfAttributes(); this.binsTotal = sets[0].retrieveNumberOfBins(); this.classID = sets[0].retrieveClassID();
        if (ig>=0 && ig<sets.length){      // ensure that the subset to be excluded is a valid subset
            for (int i=0; i<sets.length; i++){             // iterate through sets
                if (ig!=i){                 // add subset to set if subset should not be excluded
                    Set tmp = sets[i];                     // assign variable to current subset
                    ArrayList<Case> tmpf = tmp.retrieveCase();                     // iterate through cases in a subset and add cases to the set
                    int tmpNumberOfCases = tmp.retrieveNumberOfCases();
                    for (int j = 0; j < tmpNumberOfCases; j++){
                        this.addCase(tmpf.get(j));
                    }
                }
            }
        }
    }
    Set(int classTotal, int attributesTotal, int[] binsTotal, String[] classID){ //class to populate global variables
        this.classTotal = classTotal; this.attributesTotal = attributesTotal; this.binsTotal = binsTotal; this.classID = classID;
    }


}

