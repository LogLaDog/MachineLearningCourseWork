package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This Class essentially handles the csv files and processes the data within, setting variables for the Cases, Classes, Attributes, and subsets.
 */

import java.util.logging.Level;
import java.util.logging.Logger;

public class csvHandler {
    private int[] binsCount;     // bin array
    private String[] classes; //array for class names
    private final int[] dataHeaders = new int[3];     // class, attribute, Case array
    private int subsetCount = 10;  // variable for the fixes number of subsets
    private Set[] subset = new Set[subsetCount];  // array for the subsets
    private final String csvName;
    private int retrieveNumberOfClasses(){ //variable returners
        return this.dataHeaders[0]; 
    } 
    private int retrieveNumberOfAttributes(){ 
        return this.dataHeaders[1]; 
    }
    private int retrieveNumberOfCases(){ 
        return this.dataHeaders[2]; 
    }
    public Set[] retrieveSubsets(){ 
        return this.subset; 
    }
    public String[] retrieveClass(){ 
        return this.classes; 
    } 
   
    private void readFile() throws IOException {
        int headerValue = 3;
        
        File csv = new File(csvName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csv));
        String line = bufferedReader.readLine();
        String[] split = line.split(",");
        for (int i = 0; i < headerValue; i++){this.dataHeaders[i]=Integer.parseInt(split[i]);} //add data into global variables 
        int numberOfClasses = retrieveNumberOfClasses();
        int numberofAttributes = retrieveNumberOfAttributes();
        int numberofCases = retrieveNumberOfCases();
        
        this.binsCount = new int[numberofAttributes]; //set bins
        line = bufferedReader.readLine();
        split = line.split(",");
        for (int i=0; i<numberofAttributes; i++){
            this.binsCount[i] = Integer.parseInt(split[i + 2]); } //add bins to global variable
        for (int i=0; i<numberofAttributes; i++){
            this.binsCount[i] = Integer.parseInt(split[i + 2]); }
        
        this.classes = new String[numberOfClasses]; //set class array
        line = bufferedReader.readLine();
        split = line.split(",");
        for (int i = 0; i < numberOfClasses; i++){ 
            this.classes[i] = split[i]; } //add value to global variable
        for (int i=0; i<this.subsetCount; i++){ 
            this.subset[i] = new Set(numberOfClasses, numberofAttributes, this.binsCount, this.classes); }         // initialize each value in the subsets array
        for (int i=0; i<numberofCases; i++){         // populate case array by moving through file one line at a time.
            line = bufferedReader.readLine();
            Case temp = new Case(line, numberofAttributes);
            this.subset[temp.retrieveSubsetID()].addCase(temp);
        }
        bufferedReader.close();
    }
  
    public csvHandler(String csvName){
        this.csvName = csvName; // add csvName to the global variable
        try {readFile(); 
        } catch (IOException ex) {
            Logger.getLogger(csvHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}