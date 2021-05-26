/*Logan Ladd and Asher Worley */
package test;
//Class to implement reduction of the data set by removing examples from the data that are misclassified by KNN, process is repreated until performance decreases on a validation set
import java.util.ArrayList;

public class EditedKNN {
    private final int K;
    private final metric metric;
    private final KNNC learner;
    private final Set VS;    

    public EditedKNN(int k, metric metric, Set VS){
        this.K = k;
        this.metric = metric;
        this.learner = new KNNC();
        this.learner.K(k);       
        this.VS = VS;
    }

    private double findAccuracy(){ //method for finding accuracy
        double[] prediciton = this.learner.test(this.VS);
        ClassificationEvaluator CE = new ClassificationEvaluator(prediciton, this.VS);
        double tmp = CE.returnAccuracy(); //find accuracy
        return tmp;
    }

    private ArrayList<Example> findMisclassified(Set orig){
        Set NEW = orig.clone();
        ArrayList<Example> mClass = new ArrayList<Example>();
        for (int i = 0; i < NEW.returnExamplesCount(); i++){ //use learner to classify points
            Example example = NEW.returnExample(i);
            double act = example.returnValue();
            NEW.deleteEX(example);
            double prediciton = learner.classify(example); //find prediction
            NEW.addEX(i, example);
            if (prediciton != act){ 
                mClass.add(example);
            }
        }
        return mClass;
    }
    
    public Set reduce(Set orig){ //main reduction function
        Set NEW = orig.clone(); //clone
        Set tmp;    
        this.learner.train(NEW);        
        double init = findAccuracy();
        boolean changed = true;
        int i = 0;
        do {
            i++;
            this.learner.train(NEW);
            ArrayList<Example> mClass = findMisclassified(NEW); //compute accuracy for validation set
            tmp = NEW.clone();
            for (Example example: mClass){  //delete misc examples
               tmp.deleteEX(example);
            }
            this.learner.train(tmp); // train learner
            double changedT; //find accuracy
            changedT = findAccuracy();
          
            if (i==10||(double)tmp.returnExamplesCount()<=orig.returnExamplesCount()*0.25){ //stop when performance is not useful anymore
                changed = false;
                tmp = NEW;
            }
            else{ 
                NEW = tmp; //run again if not true
            } 
        } 
        
        while(changed);
        return tmp;
    }
}