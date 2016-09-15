package org.leat.asma.kohonenneuralnetwork;

import java.util.ArrayList;
import topology.Grid;
import kohonenMap.KohonenNetwork;
import kohonenMap.LearningFunction;
import kohonenMap.Neuron;
import Utils.Clusterizer;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 */

/*
 * This class is a main exemple of how the library might be used. 
 * These parametres have be chosen to fit the need and can be chnaged depending on future uses.
 */

public class KohonenMapMain {
	
	public static void main(String[] args) {
		
		//VARIBALES 
		double maxWeight= 100; // Maximal nb of weights
		int iMax =100; // Number of iterations
		int VectNum = 50; //number of random learning vectors to generate
		int weightNumber = 4; // Number of weights by neuron
		int neurNum = 50;  //Number of neurons in the NN
		double threshold = 0.6; //Cluster threshold
		String in = "C:/DataSet/CppToJava.txt"; //File path for communication between the lib and simulator
		int count =0;
		

		//Infinite loop -> should be adapted with the waiting time.
		
		while(true)
		{
		
		KohonenNetwork kn = new KohonenNetwork(weightNumber, maxWeight, neurNum); // The network
		int gridS = (int)Math.sqrt(neurNum);  //Width and height of the grid 
		Grid grid = new Grid(gridS, gridS, kn); // Grid topology 
		kn= grid.getKn(); //TO make sure to get the topology right
		ArrayList<double[]> inputVectors = new ArrayList<>(); //Vectors to learn
		
		LearningFunction lf = new LearningFunction(kn, neurNum, iMax, grid);
		
		System.out.println(" ++++++++++++  Learnin starts here ++++++++++++");
		
		
		lf.learnFromFile(VectNum, weightNumber, iMax, inputVectors, in, lf, count);
		count += VectNum;
		

		kn = lf.getKn();
		
		// LEARNING ENDS NOW LET'S CLUSTER! ~ 		
		
		Clusterizer.clusterWith4heterogeneVectors(kn, threshold, lf, in);
		
		
		}
		}
		
      
	

	
	
	
	





}
