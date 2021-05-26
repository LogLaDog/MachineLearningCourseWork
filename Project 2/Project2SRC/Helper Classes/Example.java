/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList;
//Class to create an example in dataset, basically reused from prgm1. Each example contains a val, subset, and attrIDibute
public class Example {   
    private final double val;
    private final int subsetID;
    private ArrayList<Double> attrID;

        
    public double returnValue(){ //returner methods
        return this.val; 
    } 
    public int returnSubsetID(){
        return this.subsetID;
    }
    public ArrayList<Double> returnAttributes(){ 
        return this.attrID;
    }
    
    public Example(String line, int attrIDCount){
        this.attrID = new ArrayList<Double>(attrIDCount);
        String[] data = line.split(",");
        
        this.val = Double.parseDouble(data[0]);
        this.subsetID = Integer.parseInt(data[1]);
        
        for (int i=0; i<attrIDCount; i++){
            this.attrID.add(Double.parseDouble(data[i+2])); }
    }
    
    public Example(double val, ArrayList<Double> attrIDibutes){
        this.attrID = attrIDibutes;
        this.val = val;
        this.subsetID = 0;
    }
}
