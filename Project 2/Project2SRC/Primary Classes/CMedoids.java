/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList;
import java.util.Random;
//Class to utilize K medoids algorithm and outputs set of Medoids for KNN algorithm
public class CMedoids{
    private final int count;
    private final double distortion;
    private final metric metric;
    private final ClusterHandler[] cls;
    
    public CMedoids(int count, metric metric) {
        this.count = count;
        this.metric = metric;
        this.distortion = 0.0;
        this.cls = new ClusterHandler[count];
    }
    private boolean swapMedoids(){ //method to swap Medoids
        boolean swap = false;
        double[] dsts = new double[this.count]; //distortion
        for (int i=0; i<this.count; i++){
            dsts[i] = this.cls[i].findDistortion();
        }
        
        for (int i=0; i<this.count; i++){ //test if swapping needed
            ClusterHandler cls = this.cls[i];
            for (int j=0; j<cls.returnClusterSize(); j++){ //process clusters
                Example medoid = cls.returnRepresentativeExample();
                Example ex = cls.returnExample(j);

                cls.replaceExample(j, medoid); //swap
                cls.setRepresentativeExample(ex);
                
                double dstsNew = cls.findDistortion(); //new distortion

                if (dsts[i] <= dstsNew){ //compare to old distortion
                    cls.replaceExample(j, ex);
                    cls.setRepresentativeExample(medoid);
                }
                else {
                    swap = true;
                    dsts[i] = dstsNew;
                }}}
        return swap;
    }
    
    private Set findReducedSet(Set data){
        Set reducedSet = new Set(data.getNumAttributes(), data.returnClassesCount(), data.getClassNames());
        Example[] medoids = this.returnMedoids();
        for (int i=0; i<this.count; i++){ //process medoids
            double medoidID = this.cls[i].findRepresentativeValue(); //compute value
            ArrayList<Double> attr = medoids[i].returnAttributes(); //get attr
            
            Example tmp = new Example(medoidID, attr);
            reducedSet.addEX(tmp);
        }
        return reducedSet; //return medoid set
    }

    private void cluster(Set data, Example[] medoids){
        for (int i=0; i<data.returnExamplesCount(); i++) { //process examples
            Example ex = data.returnExample(i);
            int min = 0;
            double lowD = Double.MAX_VALUE;

            for (int a=0; a<this.count; a++) { //process medoids
                Example medoid = medoids[a];
                double dist = metric.dist(ex, medoid); //find distance
                if (dist < lowD) { //set new distance if less than
                    lowD = dist;
                    min = a;
                }
            }
            this.cls[min].addEX(ex); //set example to Medois
        }
    }
    
    private void createMedoids(Set data){
        int classCount = data.returnClassesCount();
        Random rand = new Random();
        for (int i=0; i<this.count; i++) {
            int randID = rand.nextInt(data.returnExamplesCount());
            Example medoid = data.returnExample(randID);
            ClusterHandler tmp = new ClusterHandler(medoid, classCount, this.metric); //new cluster with medoid
            this.cls[i] = tmp; //add new cluster
            data.deleteEX(medoid); //clear
        }
    }

    private Example[] returnMedoids(){
        Example[] medoid = new Example[this.count];
        for (int i=0; i<count; i++){ 
            medoid[i] = this.cls[i].returnRepresentativeExample();
        }
        return medoid;
    }
    
    public Set reduce(Set orig) { //Main driver to reduce data
        Set NEW = orig.clone(); //clone data
        this.createMedoids(NEW); //init medoids
        boolean swap = true; //check for swap
        for (int i=0; i<1000&&swap; i++) {  //loop until no swap occurs          
            for (int j=0; j<this.count; j++){ this.cls[j].voidCluster(); }//clear
            this.cluster(NEW, this.returnMedoids());
            swap = this.swapMedoids();  //find the best fit          
        }
        Set reduced = this.findReducedSet(NEW);
        return reduced;
    }
}