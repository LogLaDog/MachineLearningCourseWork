package csci447project1;

/**
 * Java Program to perform the NaiveBayes algorithm on multiple data sets
 * Project By Logan Ladd and Asher Worley
 */

import java.util.ArrayList;

/**
 * This class performs the actual Naive Bayes algorithm on the sets, after this class is used the train and test execution can occur.
 */

public class Algorithm {
    private int[] N; //Contains the number of cases in each of the classes
    private double[] Q; //Containes the number of cases in each class. They are divided by the total cases in the set.
    private double[][][] F; //Contains the # of cases in a class that have matching attributes, divided by the # of cases in the class with the number of attributes.
    private int classTotal; 
    private int attrTotal; 
    private int[] bins; 

    private int maxValue(int[] array) { //simple max finding function in array
        int counter = 0;
        for(int i=0; i<array.length; i++){
            if(array[i]>counter) {counter=array[i];}
        }
        return counter;
    }
    
    public void train(Set trainerSet) { //Trains the algorithm with a set.
        classTotal = trainerSet.retrieveNumberOfClasses();
        bins = trainerSet.retrieveNumberOfBins();
        attrTotal = trainerSet.retrieveNumberOfAttributes();
        ArrayList<Case> cases = trainerSet.retrieveCase();   //finds number of cases
        N = new int[classTotal];
        F = new double[classTotal][attrTotal][maxValue(bins)]; //initialize F
        Q = new double[N.length]; // Initialize Q N/total cases per the algorithm
        
        for(Case tmp : cases) {
            N[tmp.retrieveClassID()]++;
        }
        for(int i=0; i<Q.length; i++){
            Q[i] =(double)N[i]/(double)trainerSet.retrieveNumberOfCases();
        }
        for(int i=0;i<classTotal;i++){ //Find F for the attributes in the classes
            for(Case caseID : cases){
                if(caseID.retrieveClassID()==i){
                   for(int j=0; j<attrTotal; j++){ //iterate through class' attributes
                       F[i][j][caseID.retrieveAttributesID().get(j)]++;
        }}}}
        
        for(int i=0; i<classTotal; i++){ //Finalize F by doing F+1/(CasesinClass+Attributes)
            for(int j=0; j<attrTotal; j++){
                for(int k=0; k<bins[j]; k++){
                    F[i][j][k]=(double)(F[i][j][k]+1)/(double)(N[i]+attrTotal);
        }}}}

    public int[] test(Set testSet) { //Test the algorithm with sets and find loss metrics.
        int[] tmp = new int[testSet.retrieveNumberOfCases()];
        ArrayList<Case> cases=testSet.retrieveCase();
        
        for(int i = 0; i < cases.size(); i++) {
            ArrayList<Integer> attr = cases.get(i).retrieveAttributesID();
            double[] setC = new double[classTotal];
            for(int k=0; k<classTotal; k++){ //Find C for each class C = Q * F (Corresponding)
                double result = 1;
                for(int A=0; A<attrTotal; A++){   //Find F( Corresponding for each attribute
                    result *= F[k][A][attr.get(A)];
                }
                setC[k]=Q[k]*result; //set ClassID
            }
            int cClass = 0;             //Choose the class based on the maximum value of C in setC.
            for(int j = 1; j < setC.length; j++) { //Choose the class based on the maximum value of C in setC.
                if(setC[j] > setC[cClass]){
                    cClass = j;
                }}
            tmp[i] = cClass;
        }
        return tmp;
    }}