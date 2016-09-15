package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 *
 */

public class SimCommunicator {
	
	static void sendData(int id, int slotduration, int ClustH){
		String out = "C:/DataSet/end_device_"+ id +".txt";
		
		if(slotduration == 0) slotduration = 1;
		
		System.out.println("slot duration:" + slotduration);
		System.out.println("cluster head"+ ClustH);
		
		slotduration = 0;

		String data = slotduration +" "+ClustH  ;
		
		writeToSim(out, data);
	}


public static String readFromSim(String in, int pos){
	BufferedReader bf = null;
	try {
		Optional<String> Optline;
		String line = "";
		System.out.println("LINES READ FROM FILE:: ");
		try (Stream<String> lines = Files.lines( Paths.get(in))){
			Optline = lines.skip(pos).findFirst();
			if(!Optline.isPresent()){
				return("AGAIN");
			}else{
			line = Optline.get();
		    System.out.println(line);
			return line;}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "NOP.";
		}			
	}
	finally {
		if(bf!= null)
			   try{
				bf.close();
			   }catch(IOException e){
				System.out.println("An exception occured while trying to close the file!! Here is the error: "+ e);
			}
	}
	
}
	
	private static void writeToSim(String out, String data) {
		// TODO Auto-generated method stub
	PrintWriter writer;
	try {
		writer = new PrintWriter(out, "UTF-8");
		writer.append(data);
		writer.close();
			
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	
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
         System.out.println("Vector from: \"" + fileName + "\" are importing...");
         while((line = input.readLine())!=null){ //reads one lien from the file
             rows ++;               
             tempTable = line.split(" "); //splits the line into variables 
             int tableLenght = tempTable.length; 
             tempList = new double[tableLenght];
              //List where the strings will be stored as metrics
             for(int i = 0; i< tableLenght; i++){ // Loop to convert strings into doubles 

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
