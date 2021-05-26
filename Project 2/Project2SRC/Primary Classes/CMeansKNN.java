/*Logan Ladd and Asher Worley */
package test;
import java.util.ArrayList; 
import java.util.Random;
//Class to reduce the size of a set with KNN, splits data set into clusters, then finds averages. Then points are reclustered into new centers.
public class CMeansKNN{
    private final Random RD = new Random();
    private final int count;
    private final metric metric;
    
    private final ArrayList<ClusterHandler> clusters;
    private final ArrayList<Example> initialCenter;

    public CMeansKNN(int i, metric metric){
        this.count = i;
        this.metric = metric;
        this.clusters = new ArrayList<ClusterHandler>();
        this.initialCenter = new ArrayList<>();
    }

    private void createClusters(Set init){ //Create Clusters
        int[] tmp = new int[this.count];
        for (int i=0; i<count; i++){
            int generatedIndex = 0;
            boolean checkIndex = true;
            
            while (checkIndex){
                checkIndex = false;         
                generatedIndex = RD.nextInt(init.returnExamplesCount()); //generate random inedx for cluster
                
                for (int j=0; j<i; j++){
                    if (generatedIndex == tmp[j]){ checkIndex = true; } //set index
                }
            }
      
            Example newCenter = init.returnExample(generatedIndex);
            
            newCenter = new Example(newCenter.returnValue(), newCenter.returnAttributes());
            ClusterHandler cluster = new ClusterHandler(newCenter, init.returnClassesCount(), this.metric);
            
            
           clusters.add(cluster);
        }
    }

    public void addPoints(Set init){ //add points to the clusters
        for (int i=0; i<this.count; i++){ //clear first
            clusters.get(i).voidCluster();
        }

        for (int i=0; i<init.returnExamplesCount(); i++){ //process examples
            Example runningExample = init.returnExample(i);
            
            double low = Double.MAX_VALUE;
            ClusterHandler nearestC = clusters.get(0);
            
            
            for (int j=0; j<this.clusters.size(); j++){ //process centers
                double distance = metric.dist(runningExample, clusters.get(j).returnRepresentativeExample());
                if (distance<low){
                    low=distance;
                    nearestC=clusters.get(j);
                }
            }
            
            nearestC.addEX(runningExample);
        }
    }

    public void newCenters(){ //class to update the center of each cluster based off points
        for (int a=0; a<this.count; a++){ //process centers
            ClusterHandler cl = clusters.get(a);
            Example newCenter = cl.returnRepresentativeExample();
            ArrayList<Double> attributes = new ArrayList<Double>(newCenter.returnAttributes().size());    //New array to calculate average
            
            for (int i=0; i<newCenter.returnAttributes().size(); i++) { 
                attributes.add(0.0);
            }
            
            for (int i=0; i<newCenter.returnAttributes().size(); i++){
                double runningTotal;
                
                for (int j=0; j<cl.returnClusterSize(); j++){
                    ArrayList<Double> exampleAttribute = cl.returnExample(j).returnAttributes();
                    runningTotal = attributes.get(i) + exampleAttribute.get(i);
                    attributes.set(i, runningTotal);
                }
                
                double avg = attributes.get(i)/cl.returnClusterSize();
                attributes.set(i, avg);
            }
            
            Example newCenters = new Example(-1, attributes);      //put average in the new center
            clusters.get(a).setRepresentativeExample(newCenter);
        }
    }
    
    public Set reduce(Set init){ //Main function, does actual reduction
        Set newData = init.clone();
        initialCenter.clear(); //clear array and create clone for new data
        clusters.clear();

        int max = 1000;
        int i = 0;
        
        createClusters(newData); //create clusters
        addPoints(newData); //add points
        
        
        boolean check = true;
       
        while (i<=max&&check){    //update the clusters      
            clusters.forEach((cluster)->{initialCenter.add(cluster.returnRepresentativeExample());}); //compare changes in an arraylist);
            this.newCenters();
            addPoints(newData); //add points
            i++; //iterate
            
            for (int j=0; j<clusters.size(); j++){   //checks if any of the clusters have changed
                if(clusters.get(j).returnRepresentativeExample().returnAttributes().equals(initialCenter.get(j).returnAttributes())){
                    check = false;
                }}}

        Set reduced = new Set(newData.getNumAttributes(), newData.returnClassesCount(), newData.getClassNames()); //create a new set for the reduced data
        for (int j = 0; j < this.count; j++){ //process clusters
            ClusterHandler cluster = this.clusters.get(j);
            double center_val = cluster.findRepresentativeValue(); //compute new center
            ArrayList<Double> attr = cluster.returnRepresentativeExample().returnAttributes(); //get attr
            Example val = new Example(center_val, attr);
           // reduced.returnExample(val); //add value to new set
        }
        return reduced;
    }
}