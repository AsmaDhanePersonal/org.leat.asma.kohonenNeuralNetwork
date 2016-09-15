package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 *
 */

public class InputGenerators {

	
	
	
	//vetcnum number of vectors that stimulate 4 different zones 
	//vetcnum number of vectors that stimulate 4 different zones 
public static ArrayList<double[]> heterogeneArrayVectorsRandomize(int vectNum)
{ 
	Random rand = new Random();
	ArrayList<double[]> vectors = new ArrayList<>() ;
	
	for(int i=0; i<(vectNum/4) ; i++)
	{	System.out.println("INPUT VALUES GENERATION:");	
		
	double[] vect = new double[2];
         vect[0] = rand.nextDouble() * 20; // between 0 . 20 
         vect[1] = rand.nextDouble() * 20;     //0..20
         System.out.println("vect" + vect[0] + "," + vect[1]);
         vectors.add(vect);
 
    double[] vect1 = new double[2];
 
         vect1[0] = (rand.nextDouble() * 20) + 80; // between 80 and 100
         vect1[1] = rand.nextDouble() * 20;    // 0 .. 20  
         System.out.println("vect" + vect1[0] + "," + vect1[1]);
         vectors.add(vect1);
        
    double[] vect2 = new double[2];

         vect2[0] = (rand.nextDouble() * 20 )+ 80; // 80..100
         vect2[1] = (rand.nextDouble() * 20) + 80;     //80..100
         vectors.add(vect2);
        System.out.println("vect" + vect2[0] + "," + vect2[1]);

    double[] vect3 = new double[2];

         vect3[0] = rand.nextDouble() * 20;		//0.20
         vect3[1] = (rand.nextDouble() * 20) + 80;     //80..100
         vectors.add(vect3);	     
         System.out.println("vect" + vect3[0] + "," + vect3[1]);
        // System.out.println("[" + inputVectors.get(i)[0] + "," + inputVectors.get(i)[1]);
         
	} 
	
	System.out.println("END OF GENERATION ~ ~");
	
	return vectors;
}


	private static void generateRand(String out, double maxWeight) {
		// TODO Auto-generated method stub
	PrintWriter writer;
	Random rand = new Random();
	try {
	writer = new PrintWriter(out, "UTF-8");
	for(int i = 0; i<300 ; i++){
	double[] v = new double[2];
	v[0] = rand.nextDouble() * maxWeight;
	v[1] = rand.nextDouble() * maxWeight; 

		writer.println(v[0]+" "+v[1]);
	}

	writer.close();
	}	
	catch(FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}



	private static String readFromSim(String in, int pos){
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
	
	
	// 4 random vectors from 4 different zones
	public static ArrayList<double[]> heterogeneArrayVectrosBy4()
	{
		ArrayList<double[]> vectors = new ArrayList<>() ;

		Random rand = new Random();
		double[] vect = new double[2];
	    vect[0] = rand.nextDouble() * 40; // between 0 . 40 
	    vect[1] = rand.nextDouble() * 40;     //0..40
	    System.out.println("vect" + vect[0] + "," + vect[1]);
	    vectors.add(vect);
		double[] vect1 = new double[2];

	    vect1[0] = (rand.nextDouble() * 40) + 60; // between 60 and 100
	    vect1[1] = rand.nextDouble() * 40;    // 0 .. 40  
	   System.out.println("vect" + vect1[0] + "," + vect1[1]);
	    vectors.add(vect1);
	    
		double[] vect2 = new double[2];

	    vect2[0] = (rand.nextDouble() * 40 )+ 60; // 60..100
	    vect2[1] = (rand.nextDouble() * 40) + 60;     //60..100
	    vectors.add(vect2);
	   System.out.println("vect" + vect2[0] + "," + vect2[1]);

		double[] vect3 = new double[2];

	    vect3[0] = rand.nextDouble() * 40;		//0.40
	    vect3[1] = (rand.nextDouble() * 40) + 60;     //60..100
	    vectors.add(vect3);	     
	    System.out.println("vect" + vect3[0] + "," + vect3[1]);
	   // System.out.println("[" + inputVectors.get(i)[0] + "," + inputVectors.get(i)[1]);
	    
	    return vectors;
	}
	
	//Random vectnum number of vectors
	public static ArrayList<double[]> homogeneArrayVectorsRandomize(double maxWeight, int vectNum) {
		
		Random rand = new Random();
		ArrayList<double[]> vectors = new ArrayList<>() ;
		for(int j=0; j<vectNum ; j++)
		{		
		 
		double[] vect = new double[2];
		     vect[0] = rand.nextDouble() * maxWeight;
		     vect[1] = rand.nextDouble() * maxWeight;     
		     vectors.add(vect);
		}
		
		return vectors;
	}

}
