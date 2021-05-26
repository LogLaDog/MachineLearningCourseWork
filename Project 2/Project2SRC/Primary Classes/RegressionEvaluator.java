/*Logan Ladd and Asher Worley */
package test;
public class RegressionEvaluator {
    //This class is the evaluator for regression sets, returns the mean squared error and mean error
    double mse; //Holds the mean squared error
    double me; //Holds the mean error 
    
    public double returnAccuracy() { //do nothing
        return 0;
    }
    public double returnMSE() { //return Mean Squared Error
        return mse;
    }
    public double returnME() { //return Mean Error
        return me;
    }
    
    public RegressionEvaluator(double[] prediction, Set act) {
        int examplesCount = act.returnExamplesCount();
        mse = 0;
        me = 0;
        for (int i=0; i<act.returnExamplesCount(); i++){         //Calculate the mean squared error 
            double difference = prediction[i]-(double)act.returnExampleE(i);
            me += difference;
            mse += Math.pow(difference, 2);
        }
        mse /= examplesCount; //Get average
        me /= examplesCount;
    }
}
