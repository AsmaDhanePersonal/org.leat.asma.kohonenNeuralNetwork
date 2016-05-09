package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 
 * @author Amidala
 *
 */

public class FileReading {
	
	
	
	public ArrayList<double[]> readFile(String fileName){
	File file = new File(fileName);
	int rows=0;
	 String[] tempTable;
	 double[] tempList = null;
	 ArrayList<double[]> vectors = new ArrayList<>();
	 try{
         FileReader fr = new FileReader(file);
         BufferedReader input = new BufferedReader(fr);
         String line;
         System.out.println("Vector drom: \"" + fileName + "\" are importing...");
         while((line = input.readLine()) != null){ //reads one lien from the file
             rows ++;               
             tempTable = line.split(" "); //splits the line into variables 
             int tableLenght = tempTable.length; 
             tempList = new double[tableLenght];
              //List where the strings will be stored as metrics
             for(int i = 0; i< tableLenght; i++){ // Loop to convert strings into doubles 
                 double weightVal = 0;

                     

                 tempList[i] = Double.parseDouble(tempTable[i]);
                 System.out.println("THESE ARE THE CONVERTED DOUBLES:");
                 System.out.println(tempList[i]);
             }
             
             vectors.add(tempList);
         }
             
             fr.close();
             System.out.println(rows + " vectors were imported");
         }catch(IOException e){
        	   System.out.println("File can not be read!. Error: " + e);
         }
	 

	 return vectors;
	}
}

