/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList;
// This class is a helper class to set points in examples for algorithms
public class ClusterHandler {
    private Example representativeExample;
    private metric metric;
    private ArrayList<Example> ex;
    private int classCount;
    
    public void addEX(Example ex){ //ex handers
        this.ex.add(ex); 
    }
    public void addEX(int ID, Example ex){
        this.ex.add(ID, ex); 
    }
    public void deleteEX(Example ex){
        this.ex.remove(ex);
    }
    public void setRepresentativeExample(Example ex){
        this.representativeExample = ex;
    }
    public int returnClusterSize(){
        return this.ex.size(); 
    }
    public Example returnRepresentativeExample(){
        return this.representativeExample;
    }
    public Example returnExample(int ID){
        return this.ex.get(ID);
    
    }
    public ArrayList<Example> returnExamples(){ return this.ex; }

    public void replaceExample(int ID, Example ex){
        this.ex.remove(ID);
        this.ex.add(ID, ex);
    } 
    
    public void voidCluster(){ 
        this.ex.clear(); 
    }
    
    public ClusterHandler(Example representativeExample, int classCount, metric dist_met) {
        this.metric = dist_met;
        this.representativeExample = representativeExample;
        this.ex = new ArrayList<Example>();
        this.classCount = classCount;
    }

    public double findRepresentativeValue(){ //compute representative value
        if (this.ex.size() == 0){ 
            return this.representativeExample.returnValue();
        }  
        double assigned_value=0.0;
        if (this.classCount==0){ //for regression
            assigned_value=this.findRegressionValue();  // compute regression value for medoid
        }
        else{
            this.findClassificationValue();
        }
        return assigned_value;
    }

    private double findClassificationValue(){
        int[] class_count = new int[this.classCount];
        for (int i=0; i<this.ex.size(); i++){
            int classification = (int)this.ex.get(i).returnValue(); //get classification after iterating through arrays
            class_count[classification]++;
        }

        if (this.representativeExample.returnValue()!=0){ //differentiate medoids and means
            int classification = (int)this.representativeExample.returnValue();
            class_count[classification]++;
        }
        int max_index = 0;
        for (int i=1; i<class_count.length; i++){ //swap if class average is greater than max
            if (class_count[i]>class_count[max_index]){
                max_index = i; 
            }
        }
        return max_index;
    }
    
    private double findRegressionValue(){
        double average = 0.0;
        for (int i=0;i<this.ex.size(); i++){ //sum example values
            average += this.ex.get(i).returnValue();
        }
        
        if (this.representativeExample.returnValue() != 0){ //differentiate medoids and means
            average += this.representativeExample.returnValue();
            average /= (this.ex.size() + 1);
        }
        else{
            average /= this.ex.size();
        }
        return average;  
    }
    
    public double findDistortion(){ //Cmedoids helper
        double distortion = 0;
        for (int i=0; i<this.ex.size(); i++){  // total distortion of each example
            distortion+=this.metric.dist(this.representativeExample, this.ex.get(i));
        }
        return distortion;
    }}
