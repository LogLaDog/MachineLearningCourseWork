/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList;
// K-NN algorithm to work with regression data sets
public class KNNR implements KNNHandler {
    private metric distanceMetric; 
    private Set N; //n
    private int k; 
    public KNNR() {}

    public void setDistanceMetric(metric metric) {
        distanceMetric = metric;
    }  
    public void K(int next){
        k = next;
    }
    public void train(Set tSet) {
        N = tSet;
    }
    public double[] test(Set testSet) { 
        double[] p = new double[testSet.returnExamplesCount()];
        for (int i=0; i<testSet.returnExamplesCount(); i++) {
            p[i] = predict(testSet.returnExample(i));
        }
        return p;
    }

    private double predict(Example example) { //predict the real value of an example by finding NN and choosing the average value among them
        ArrayList<Double> nearestNeighborValue = new ArrayList<>();
        ArrayList<Double> nearestNeighborDistance = new ArrayList<>(); //init arrays and values
        for(int i=0; i<k; i++) { 
            nearestNeighborValue.add(0.0);
            nearestNeighborDistance.add(Double.MAX_VALUE);
        }

        for (int l=0; l<N.returnExamplesCount(); l++) { //process N, calcuating distance from the examples and comparing it to the current nearest neighbor (n)
            Example n = N.returnExample(l);
            double distance = distanceMetric.dist(example, n);
            for(int i=0; i<k; i++) { //check if distance is smaller than current
                if(distance<nearestNeighborDistance.get(i)) { //overwrite KNN if need be
                    nearestNeighborValue.add(i,n.returnValue());
                    nearestNeighborDistance.add(i,distance);
                    nearestNeighborValue.remove(k);
                    nearestNeighborDistance.remove(k);
                    break; //stop
                } //continue if need be
            }
        }
        
        double total=0; //find the mean and return as average value
        double tmp=0; 
        for(int i=0; i<k; i++) {
            if(nearestNeighborDistance.get(i) < Double.MAX_VALUE) { //check for
                total += nearestNeighborValue.get(i);
                tmp++;
            } else { //compute the mean based on the nn that exist
                break;
            }
        }
        return total/tmp;
    }  
}