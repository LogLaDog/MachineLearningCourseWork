package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.util.ArrayList;

/**
 * This class creates a table object that will return the precision and mean square error
 */

public class TableGenerator{
    double meanSquareError; //mean squared error final
    int[][] table; //array for the table
    private final int caseTotal; 
 
    public double retrieveMeanSquaredError() { // variable returner
        return meanSquareError;
    }
    
    public double retrievePresicion() { //presicion calculation: total precise classifications/total # cases in the set.
        int preciseClasses = 0;
        
        for(int i=0; i<table.length; i++){
            preciseClasses += table[i][i];
        }
        
      return (double)preciseClasses*(1/(double)caseTotal);
    } 

    public TableGenerator(Set tmpSet, int[] pclasses) {
        table = new int[tmpSet.retrieveNumberOfClasses()][tmpSet.retrieveNumberOfClasses()];         // fill the table with 0s
        caseTotal = tmpSet.retrieveNumberOfCases();
        ArrayList<Case> cases = tmpSet.retrieveCase();
        double differentialTotal = 0;         //Find the difference
        int[] preciseNumberOfClasses = new int[tmpSet.retrieveNumberOfClasses()];         //Calculate the mean squared error, add predicted classes to actual
        int[] tmpNumberOfClasses = new int[tmpSet.retrieveNumberOfClasses()];

        for(int i=0; i<cases.size(); i++) {         // populate the table
            table[pclasses[i]][cases.get(i).retrieveClassID()]++;
        }

        for(int i=0; i<cases.size(); i++) {
            preciseNumberOfClasses[cases.get(i).retrieveClassID()]++; //Retrieve classes
            tmpNumberOfClasses[pclasses[i]]++;
        }

        for(int i = 0; i < preciseNumberOfClasses.length; i++) {
            differentialTotal += Math.pow((tmpNumberOfClasses[i] - preciseNumberOfClasses[i]), 2);
        }
        meanSquareError = differentialTotal / tmpSet.retrieveNumberOfClasses();         //Take the average (final mean square error
    }   
}
