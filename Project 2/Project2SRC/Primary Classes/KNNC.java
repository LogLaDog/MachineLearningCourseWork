/*Logan Ladd and Asher Worley */
package test;
//K-NN algorithm to work with classification data sets

import java.util.ArrayList;

public class KNNC {
    private metric distanceMetric; 
    private Set N; //neighbors
    private int k; 
    public KNNC() {}

    public void setDistanceMetric(metric metric) {
        distanceMetric = metric;
    }
    public void K(int next) {
        k = next;
    }
    public void train(Set tSet) {
        N = tSet;
    }
    public double[] test(Set testSet) { //classifying each example
        double[] classif = new double[testSet.returnExamplesCount()];
        for (int i=0; i<testSet.returnExamplesCount(); i++) {
            classif[i] = classify(testSet.returnExample(i));
        }
        return classif;
    }
    
    public double classify(Example example) {
        ArrayList<Double> nearestNeighborClasses = new ArrayList<>();
        ArrayList<Double> nearestNeighborDistances = new ArrayList<>(); //init arrays and values to track NN
        for(int i=0; i<k; i++) { 
            nearestNeighborClasses.add(-1.0);
            nearestNeighborDistances.add(Double.MAX_VALUE);
        }

        for (int a=0; a<N.returnExamplesCount(); a++) { //process all neighbors, calculating distance from examples and comparing to current
            Example n = N.returnExample(a);
            double distance = distanceMetric.dist(example, n);
            for(int i=0; i<k; i++) { //check if distance is smaller than current NN
                if(distance < nearestNeighborDistances.get(i)) {
                    nearestNeighborClasses.add(i, n.returnValue()); //overwrite if need eb
                    nearestNeighborDistances.add(i, distance);
                    nearestNeighborClasses.remove(k);
                    nearestNeighborDistances.remove(k);
                    break; // stop
                } //continue if need be
            }
        }
        
        int num_classes = N.returnClassesCount();  
        return num_classes;
        }
    }   
