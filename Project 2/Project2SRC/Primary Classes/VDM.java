/*Logan Ladd and Asher Worley */
package test;
// This class calcuates the difference between two categorical variables using the value difference metric
public class VDM {
    public static double distance(int value1, int value2, Matrix temp){
        if (value1 == value2){ // equal values yield no difference
            return 0.0;
        }

        else { // if values are equal, find the difference
            double distance = 0.0;

            for (int cls=0; cls<temp.returnBinCount(); cls++){ //loop through each class
                double difference = temp.returnProbability(value2, cls)-temp.returnProbability(value1, cls); //find difference
                difference = Math.abs(difference); //abs
                distance+=difference;
            }
            distance/=temp.returnBinCount();   //Divide by classes to regularize difference
            return distance;
        }
    }  
}
