/*Logan Ladd and Asher Worley */
package test;
//This class is the evaluator for Classification sets, returns the mean squared error and accuracy
public class ClassificationEvaluator {    
    double accuracy; //Holds the accuracy
    double mse; //Holds the mean squared error

    public double returnAccuracy() { //return methods
        return accuracy;
    }
    public double returnMSE() {
        return mse;
    }
    public double returnME() {
        return 0;
    }
    
    public ClassificationEvaluator(double[] prediction, Set act) {
        int examplesCount = act.returnExamplesCount();
        accuracy = 0;
        for (int i=0; i<act.returnExamplesCount(); i++) {
            if (Math.round((double) act.returnExampleE(i)) == Math.round(prediction[i])) {
                accuracy++;
            }
        }
        accuracy /= (double)examplesCount; //get final accuracy

        int[] actualClassCount = new int[act.returnClassesCount()]; //Methods to calculate mean squared error
        int[] predictionClassCount = new int[act.returnClassesCount()];
        
        for (int i=0; i<act.returnExamplesCount(); i++){
            actualClassCount[(int)act.returnExampleE(i)]++;
            predictionClassCount[(int)prediction[i]]++;
        }

        double distanceTotal = 0; //get Difference
        
        for(int i=0; i<actualClassCount.length; i++){
            distanceTotal += Math.pow((predictionClassCount[i]-actualClassCount[i]), 2);
        }
        mse=distanceTotal/act.returnClassesCount(); //get final MSE
    }
}