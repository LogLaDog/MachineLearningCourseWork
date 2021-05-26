/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList;
// Class that creates a set of examples from the datasets, basically reused from prgm1
public class Set {
    private final int attrCount;
    private final int classCount;
    private String[] classID;
    private ArrayList<Example> EX = new ArrayList<Example>();
    public Set(int attrCount, int classCount, String[] classID){
        this.attrCount = attrCount;
        this.classCount = classCount;
        this.classID = classID;
    }

    public void addEX(Example ex){ //add example
        this.EX.add(ex);
    }
    public void addEX(int index, Example ex){ 
        this.EX.add(index, ex);
    }
    public void deleteEX(int index){ //delete examples
        this.EX.remove(index);
    }
    public void deleteEX(Example ex){ 
        this.EX.remove(ex);
    }
    public void replaceEX(int index, Example ex){  //replace EX
        this.EX.set(index, ex);
    } 

    public Set clone(){
        // initialize new Set object
        Set clone = new Set(this.attrCount, this.classCount, this.classID);
        
        // add each example in this to clone
        for (Example ex: EX){ clone.addEX(ex); }
        
        return clone;
    }
    
    public Example returnExample(int index){ //return methods
        return this.EX.get(index);
    } 
    public int returnClassesCount(){ 
        return this.classCount;
    } 
    public int getNumAttributes(){ 
        return this.attrCount;
    } 
    public int returnExamplesCount(){ 
        return this.EX.size();
    } 
    public String[] getClassNames(){ 
        return this.classID;
    } 

    public void voidSet(){
       this.EX.clear();
    } 
    
    Object returnExampleE(int i) {
        throw new UnsupportedOperationException("");
    }
    
    public Set(Set[] subset, int tmpx, boolean VS){
        this.attrCount = subset[0].getNumAttributes();
        this.classCount = subset[0].returnClassesCount();
        this.classID = subset[0].getClassNames();      
        boolean indexCheck = false;  
        if (VS){
            if (tmpx>=1&&tmpx<subset.length){ 
                indexCheck = true; 
            } //subset[0] will not be used in 10-fold CV
        }        
        else if (tmpx>=0&&tmpx<subset.length){
            indexCheck = true; 
        }  
        if (indexCheck){
            int i = 0; //index in subset
            if (VS){ //ignore subset[0] if VS is required
                i = 1;
            }

            for ( ; i < subset.length; i++){ //process subset
                if (tmpx != i){
                    Set tmp = subset[i];
                    for (int j=0; j<tmp.returnExamplesCount(); j++){  //add example to subset
                        this.addEX(tmp.returnExample(j)); 
                    }
                }}}}


}