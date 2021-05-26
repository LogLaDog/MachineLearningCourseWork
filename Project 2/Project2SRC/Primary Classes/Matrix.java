/*Logan Ladd and Asher Worley */
package test;
///Class to utilize a similarity matrix, each matirx contains of an attribute and an array that stores probabilities.
public class Matrix {
    private int attributeID; //attribute id
    private int options; //number of options
    private int binsCount; // number of bins
    private double[][] matrix; //actual matrix
        
    public int returnAttributeID(){ 
        return this.attributeID; } // return methods
    
    public int returnBinCount(){ 
        return this.binsCount; }
    
    public double returnProbability(int option, int classification){ 
        return this.matrix[option][classification]; 
    }
    
    public Matrix(int attributeID, int options, int classCount){
        this.attributeID = attributeID;
        this.options = options;
        this.binsCount = classCount;
        
        matrix = new double[this.options][this.binsCount];
    }
    
    public void addRow(int option, String line){ //Adds a row to the matrix, each row consisting of probabilites thats value appears in the nth class of line
        String[] data = line.split(","); //create string array 
        for (int i=0; i<data.length; i++){ // insert values of array
            matrix[option][i] = Double.parseDouble(data[i]); 
        } 
    }
}
