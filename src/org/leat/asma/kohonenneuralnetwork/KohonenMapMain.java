package org.leat.asma.kohonenneuralnetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthSeparatorUI;

import topology.Grid;
import kohonenMap.KohonenNetwork;
import kohonenMap.LearningFunction;
import kohonenMap.Neuron;
import Utils.Clusterizer;

/**
 * 
 * @author Amidala
 *
 */

public class KohonenMapMain {
	
	public static void main(String[] args) {
		
		// GLOBAL VARIBALES 
		double maxWeight= 100; // Maximal of weights
		int iMax =100; // Number of iterations
		int VectNum = 50; //number of random learning vectors to generate
		int weightNumber = 2; // Number of weights by neuron
		int neurNum = 50;  //Number of neurons in the NN
		double threshold = 0.6; //Cluster threshold
		String in = "C:/DataSet/CppToJava.txt";
		int count =0;
		//Instantiations
		while(true)
		{
		
		KohonenNetwork kn = new KohonenNetwork(weightNumber, maxWeight, neurNum); // The network
		int gridS = (int)Math.sqrt(neurNum);  //Width and height of the grid 
		Grid grid = new Grid(gridS, gridS, kn); // Grid topology 
		kn= grid.getKn(); //TO make sure to get the topology right
		ArrayList<double[]> inputVectors = new ArrayList<>(); //Vectors to learn
		
		LearningFunction lf = new LearningFunction(kn, neurNum, iMax, grid);
		
		System.out.println(" ++++++++++++  Learnin starts here ++++++++++++");
		
		
		// learnFromFile(VectNum,weightNumber, iMax,inputVectors , in , lf , count);
		count += VectNum;
		// LEARNING ENDS NOW LET'S CLUSTER! ~ 
		
		inputVectors = heterogeneArrayVectorsRandomize(50);
		
		//learnFromFile(VectNum, weightNumber, iMax, inputVectors, in, lf, count);
		
		for(int i= 0 ; i< 100; i++)
		lf.learn(inputVectors, i, iMax);	
		
		
		kn = lf.getKn();
		
		clusterWith4heterogeneVectors(kn, threshold, lf, in);}
		
		
		//kn.writeFile();
		}
		
	
	
	
	static String[] str;
	
	
private static void determineSlotDurationAndClusterHeads(KohonenNetwork kn, Neuron[] CH, String in) {
	
	    ArrayList<double[]> inputVectors = new ArrayList<>();
	    
	    BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    try {
	        String line = br.readLine();
	        String[] strArray;        
	        while (line != null) {
	        	double[]  vect = new double[3];
	        	strArray = line.split(" ");
	        	vect[0] = Double.parseDouble(strArray[0]);
				vect[1] = Double.parseDouble(strArray[1]);
				vect[2] = Double.parseDouble(strArray[2]);
				inputVectors.add(vect);    
				line = br.readLine();
	    } 
	        br.close();
	        }catch (Exception e) {
			e.printStackTrace();
		}
		
	    
		int[] clustHid= new int[4];
		int[] NbNodePerClass = new int[4];
		int[] clusterHPerNodeID = new int[15];
		int IdNode;
        for(double[] vec : inputVectors)
        {	
        	IdNode = (int)vec[0];
        	
        	for(int g = 0; g < CH.length ; g++)
        	{
        		
        	
        		   		
        		for(Neuron n : kn.getKneur() )
        		{ 
        			// weight = cluster head weight !!!! -> looking for the node ID !   
        			if(CH[g].getWeights()[0] == vec[1] && CH[g].getWeights()[1] == vec[2] )
            		{	clustHid[g] = IdNode;	 
            		System.out.println(" ... CLUSTER HEAD ID ... : " + IdNode);}
            		
        			if(n.getWeights()[0] == vec[1] && CH[g].getWeights()[1] == vec[2] ){
        				if(n.getCluster() != -1)
        				NbNodePerClass[n.getCluster()-1] ++;     				
        		}	
        			if(n.getCluster() != -1)
        				clusterHPerNodeID[IdNode] = clustHid[n.getCluster()-1];	
        		}
        	}
		}
        
        
        	for(int f=0 ; f < clusterHPerNodeID.length ; f++){
        		
				System.out.println("From determineSlotDuration() : ");
				System.out.println("id of the node:" + f);
				
				System.out.println(" ******** WRITING TO SIMULATOR!! ********* ");
				System.out.println(" ******** WRITING TO SIMULATOR!! ********* ");
				
				for(int r = 0 ; r<clustHid.length ; r++)
				{
					if(clustHid[r]==clusterHPerNodeID[f])
						sendData(f,NbNodePerClass[r],clusterHPerNodeID[f]);
				
				 }
        	}
        	
	}

private static void sendData(int id, int slotduration, int ClustH){
	String out = "C:/DataSet/end_device_"+ id +".txt";
	
	if(slotduration == 0) slotduration = 1;
	
	System.out.println("slot duration:" + slotduration);
	System.out.println("cluster head"+ ClustH);
	
	slotduration = 0;

	String data = slotduration +" "+ClustH  ;
	
	writeToSim(out, data);
}
        
	

	public static void learnFromFile(int VectNum, int weightNumber, int iMax, ArrayList<double[]> inputVectors , String in , LearningFunction lf, int count) {
		
		for(int j =0 ; j<VectNum-1 ; j++){
			String learnVectStr ;
			String[] strArray = new String[3];
			double[] learnVect = new double[weightNumber];
			do{
			learnVectStr = readFromSim(in, count);
			if(count==26)
			System.out.println(count);
			}while(learnVectStr.equals("AGAIN"));
			if(!learnVectStr.equals("NOP.")) 
			{count++;
			//System.out.println(count);
			strArray = learnVectStr.split(" ");
			learnVect[0] = Double.parseDouble(strArray[1]);
			learnVect[1] = Double.parseDouble(strArray[2]);
			inputVectors.add(learnVect);}
			else{break;}
			}
		
		for(int i=0;i<iMax;i++){
			
			if (inputVectors.size() < 30) break;
			else{
		System.out.println("iteration: " + i);
		System.out.println("INPUT VALUES GENERATION:");	
		//Vector generation
		//inputVectors = homogeneArrayVectorsRandomize(maxWeight, VectNum);
		
	    //LEARNING !!  
		 lf.learn(inputVectors, i, iMax);	}
		//lf.learn(learnVect, i, iMax);
		}
		
	}
	
	//CLUSTERIZING METHODS
	
	public static Neuron[] clusterWith4heterogeneVectors(KohonenNetwork kn, double threshold , LearningFunction lf, String in) {
		Neuron CH1;
		Neuron CH2;
		Neuron CH3;
		Neuron CH4;
		
		Clusterizer clust= new Clusterizer();
		
		// Cluster TEST vectors
		double[] sampleVect1 = {10,10}; //C1
		double[] sampleVect2 = {90,10}; //C2
		double[] sampleVect3 = {90,90}; //C3
		double[] sampleVect4 = {10,90}; //C4
		
		ArrayList<Neuron> clust1 = new ArrayList<>();
		ArrayList<Neuron> clust2 = new ArrayList<>();
		ArrayList<Neuron> clust3 = new ArrayList<>();
		ArrayList<Neuron> clust4 = new ArrayList<>();
		
		
		for(Neuron neuron : kn.getKneur()){
			double[] weights = {neuron.getWeights()[0], neuron.getWeights()[1]};
			if(clust.calculateNeuralActivity(lf,weights, sampleVect1 , threshold)== 1)
				{System.out.println("the neural weights:" + neuron.weightsToString());
				neuron.setCluster(1);
				clust1.add(neuron);}
			else if(clust.calculateNeuralActivity(lf, weights, sampleVect2, threshold)==1)
				{System.out.println("the neural weights:" + neuron.weightsToString());
				neuron.setCluster(2);
				clust2.add(neuron);}
			else if(clust.calculateNeuralActivity(lf, weights, sampleVect3, threshold)==1)
				{System.out.println("the neural weights:" + neuron.weightsToString());
				neuron.setCluster(3);
				clust3.add(neuron);}
			else if(clust.calculateNeuralActivity(lf, weights, sampleVect4, threshold)==1)
			{System.out.println("the neural weights:" + neuron.weightsToString());
				neuron.setCluster(4);
				clust4.add(neuron);}
			if(neuron.getCluster()== -1) System.out.println("the neural weights:" + neuron.weightsToString());
			System.out.println("The neuron n°=" + neuron.getId()+ " was classified within cluster " + neuron.getCluster());
		
			lf.getF().repaint(neuron.getX(), neuron.getY(), neuron.getCluster());
			lf.getF().repaint(10, 10);
			lf.getF().repaint(90, 10);
			lf.getF().repaint(90, 90);
			lf.getF().repaint(10, 90);
		} 
		
		
		double[] ch1coor = clust.clusterHeadSelection(clust1);
		CH1 = lf.bestMatchingUnit(ch1coor);
		System.out.println("CH1 =" + CH1.getId());
		double[] ch2coor = clust.clusterHeadSelection(clust2);
		CH2 = lf.bestMatchingUnit(ch2coor);
		System.out.println("CH2 =" + CH2.getId());
		double[] ch3coor = clust.clusterHeadSelection(clust3);
		CH3 = lf.bestMatchingUnit(ch3coor);
		System.out.println("CH3 =" + CH3.getId());
		double[] ch4coor = clust.clusterHeadSelection(clust4);
		CH4 = lf.bestMatchingUnit(ch4coor);
		System.out.println("CH4 =" + CH4.getId());
		
		Neuron[] ClusterHeads = {CH1,CH2,CH3,CH4};
		
		determineSlotDurationAndClusterHeads(kn,ClusterHeads, in);
		
		return ClusterHeads ;
	
		
	}
	
	
	//VECTOR GENERATION METHODS 
	
	//vetcnum number of vectors that stimulate 4 different zones 
public static ArrayList<double[]> heterogeneArrayVectorsRandomize(int vectNum)
{  	Random rand = new Random();
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

}
