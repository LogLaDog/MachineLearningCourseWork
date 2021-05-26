package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.text.DecimalFormat;
import java.io.FileNotFoundException;

/**
 * This main class is designed to open our data files, run them through the algorithm, and print the output in the console.
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"breast-cancer-wisconson.csv", "breast-cancer-wisconson-scrambled.csv", "glass.csv", "glass-scrambled.csv", "house-votes-84.csv", "house-votes-84-scrambled.csv", "iris.csv", "iris-scrambled.csv",  "soybean-small.csv", "soybean-small-scrambled.csv"};

        for(int i = 0; i < files.length; i++) { // loop to open data file
            double totalPresicion = 0;    // variables to store loss metrics
            double totalMeanSquaredError = 0;
            int fold = 10;
            
            System.out.println(files[i]);
            Algorithm alg = new Algorithm();              // Class object to run the NB Algorithm
            csvHandler tmp = new csvHandler(files[i]); // Class object to handle the excel files
            
            for(int j = 0; j < fold; j++) { // 10 Fold CV
                Set setTrain = new Set(tmp.retrieveSubsets(), j); // Combine 9 of the subsets
                alg.train(setTrain); // train with use of the NB algorithm
                Set testSet = tmp.retrieveSubsets()[j]; // test the remaining subsets with use of the csv handler
                int[] guess = alg.test(testSet); // continue tests.
                TableGenerator tg = new TableGenerator(testSet,guess); // Table generator is used to evaulate loss metrics with the use of a matrix
                
                double presicion = tg.retrievePresicion(); 
                double meanSquaredError = tg.retrieveMeanSquaredError();
                
                totalPresicion += presicion;
                totalMeanSquaredError += meanSquaredError;
            }
            System.out.println("Avg. presicion for " + files[i] + ": " + new DecimalFormat("###.#").format(totalPresicion/10*100)+ "%");//Final Print Statements
            System.out.println("Avg. Mean Squared Error for " + files[i] + ": " + new DecimalFormat("###.##").format(totalMeanSquaredError/10));
         
        }
                
    }  
}
