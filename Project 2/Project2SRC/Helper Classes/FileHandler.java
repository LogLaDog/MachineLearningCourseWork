/*Logan Ladd and Asher Worley */
package test;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
// This is just the data reader class that handles input files, basically reused from prgm1 populates examples and the datasum array with the same attributes, examples and classes 
public class FileHandler {
    private final int subsetsCount = 10; //variables
    private final String file_name;    
    private final int[] dataSum = new int[4]; //same attribute, set, and classes
    
    private String[] classID;
    private Set[] subsets = new Set[subsetsCount];
    private Set VS;
    private Matrix[] sMatrix;
    
    private int returnAttributesCount(){ //return methods
        return this.dataSum[0]; 
    } 
    private int returnClassesCount(){
        return this.dataSum[2]; 
    }
    private int returnCAttributesCount(){ 
        return this.dataSum[3]; 
    }
    
    public String[] returnClassNames(){ 
        return this.classID; 
    }
    public Set[] returnSubsets(){ 
        return this.subsets; 
    }
    public Set returnVS(){ 
        return this.VS;
    }
    public Matrix[] returnSMatrix(){
        return this.sMatrix; 
    }
    public int returnExamplesCount() {
        return this.dataSum[1]; 
    };
    
    public FileHandler(String file) throws IOException{ //set file name and open
        this.file_name = file;
        try{readFile();}
        catch(IOException e){}
    }
    
    private void readFile() throws IOException {
        File file = new File(file_name);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] split_line = line.split(",");
        
        for (int i=0; i<4; i++){ 
            this.dataSum[i] = Integer.parseInt(split_line[i]); 
        }

        int attrCount = this.returnAttributesCount();
        int examplesCount = this.returnExamplesCount();
        
        
        int classesCount = this.returnClassesCount();
        int CAttributeCount = this.returnCAttributesCount();
        
        this.sMatrix = new Matrix[CAttributeCount];
        for (int i=0; i<CAttributeCount; i++){
            try{ this.sMatrix[i] = readMatrix(br); }
            catch(IOException e){ System.err.println("Unexpected end of file"); }
        }
        
        if (classesCount == 0){ this.classID = null; } //set regression for classes count value

        else{ //classification
            this.classID = new String[classesCount];
            line = br.readLine();
            split_line = line.split(",");
            for (int i=0; i<classesCount; i++){ 
                this.classID[i] = split_line[i]; 
            }
        }
        
        for (int i=0; i<this.subsetsCount; i++){ 
            this.subsets[i] = new Set(attrCount, classesCount, this.classID); //populate array with class, examples, attr (same as prgm1)
        }
    
        for (int i=0; i<examplesCount; i++){ //populate examples
            line = br.readLine();
            Example tmp = new Example(line, attrCount);
            this.subsets[tmp.returnSubsetID()].addEX(tmp);
        } 
        this.VS = this.subsets[0];
        br.close();
    }

    private Matrix readMatrix(BufferedReader br) throws IOException { //populate matrix from input
        String line = br.readLine();
        String[] data = line.split(",");
        int attrID = Integer.parseInt(data[0]);
        int tmpCount = Integer.parseInt(data[1]);
        int binsCount = Integer.parseInt(data[2]);
        Matrix m = new Matrix(attrID, tmpCount, binsCount);

        for (int i=0; i<tmpCount; i++){
            line = br.readLine();
            m.addRow(i, line);
        }
        return m;
    }
}