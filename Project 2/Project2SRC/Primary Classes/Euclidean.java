package test;
/*Logan Ladd and Asher Worley */
import java.util.ArrayList;
//Class that implements computation of the distance between two examples using the Euclidean Metric
public class Euclidean {
    
    private final Matrix[] exmatrix;
    
    public int categoricalID(int ID){
        for (int i=0; i<this.exmatrix.length; i++){
            if (ID==this.exmatrix[i].returnAttributeID()){ 
                return i; 
            }
        }
        return 0;
    }
    
    public Euclidean(Matrix[] exmatrix){ 
        this.exmatrix = exmatrix;
    }

    public double distance(Example firstExample, Example secondExample){
        // initialize array lists with attributes
        ArrayList<Double> firstAttribute = firstExample.returnAttributes();
        ArrayList<Double> secondAttribute = secondExample.returnAttributes();
  
        double distanceSquared = 0.0;
        for (int i = 0; i < secondAttribute.size(); i++){ //loop through array
            double difference = 0.0; //store difference
            int ID = this.categoricalID(i);  // test if attribute is categorical
            if (ID > 0){
                int tmp1 = (int)Math.round(firstAttribute.get(i));    // create integers for matrix use
                int tmp2 = (int)Math.round(secondAttribute.get(i));
                difference = VDM.distance(tmp1, tmp2, this.exmatrix[ID]); //get difference
            }
            else{
                difference = (double)(secondAttribute.get(i)-firstAttribute.get(i)); //get difference
            }
            double differenceSquared = Math.pow(difference, 2); //square
            distanceSquared += differenceSquared; //add to distance
        }
        return distanceSquared;
    }
}
