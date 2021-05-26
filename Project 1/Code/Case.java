package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.util.ArrayList;
/**
 * Datasets are represented in a case.
 * Each Case has attributes, contains a class, and is in a subset.
 */
public class Case {
    private final int classID;    // variable to enter case into class type
    private final int subsetID;     // variable to identify subsets.
    private ArrayList<Integer> attributeID;     // array to identify attributes within a class
    public int retrieveClassID(){  // variable returners 
        return this.classID;
    } 
    public int retrieveSubsetID(){ 
        return this.subsetID; 
    }
    public ArrayList<Integer> retrieveAttributesID(){ 
        return this.attributeID; 
    }

    Case(String line, int totalAttributes){
        this.attributeID = new ArrayList<Integer>(totalAttributes);         // size
        String[] csv = line.split(",");         // split the input line into a String array
        this.classID = Integer.parseInt(csv[0]);         // populate class type and subsetID;
        this.subsetID = Integer.parseInt(csv[1]);       
        for (int i=0; i<totalAttributes; i++){  // put attribute values into the array.
            this.attributeID.add(Integer.parseInt(csv[i+2])); //add csv data
        } 
    }
}
