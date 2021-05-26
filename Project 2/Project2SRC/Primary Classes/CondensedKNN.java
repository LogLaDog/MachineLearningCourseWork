/*Logan Ladd and Asher Worley */
package test;
import java.util.Random;
// This class implements the Condensed NN Algorithm to condense points of the same class that neighbor eachother, decreasing the number of points in a set.
public class CondensedKNN {
    Random rd = new Random();
    private final metric metric;
    public CondensedKNN(metric metric){ //metric handler
        this.metric = metric;
    }
    
    public Set reduce(Set init){ //Main method, takes in a set and retuns the reduce set after running condensing
        Set reduce = new Set(init.getNumAttributes(), init.returnClassesCount(), init.getClassNames());
        reduce.addEX(init.returnExample(rd.nextInt(init.returnExamplesCount())));
        
        Set originalSet = init.clone(); //clone the set
        Set copySet = init.clone();
        
        int max = 1000;
        int i = 0;
        
        while (!reduce.returnExample(i).equals(originalSet.returnExample(i))){ //iterating through the sets
            originalSet = reduce.clone();
            
            for (int j=0;j<copySet.returnExamplesCount(); j++){ 
                Example runningExample = copySet.returnExample(j);
                double min = Double.MAX_VALUE; //holding the minimum value for closeness to example
                Example minEx = copySet.returnExample(0);
                
                for (int k=0; k<reduce.returnExamplesCount(); k++) {
                    Example ex2 = reduce.returnExample(k);
                    
                    if (!runningExample.returnAttributes().equals(ex2.returnAttributes())){  //check for self examples
                        if (metric.dist(runningExample, ex2)<min){ //check running distance 
                            min = metric.dist(runningExample, ex2);
                            minEx = ex2;
                        }
                    }
                }
                if (minEx.returnValue() != runningExample.returnValue()){  //adds the closer examples to the reduced set if class is different than the example we are comparing
                    reduce.addEX(runningExample);                    
                    copySet.deleteEX(j);
                    j--;}}
            i++;}
        return reduce;}}