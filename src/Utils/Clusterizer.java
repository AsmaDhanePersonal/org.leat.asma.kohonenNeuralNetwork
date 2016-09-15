package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import kohonenMap.KohonenNetwork;
import kohonenMap.LearningFunction;
import kohonenMap.Neuron;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 *
 */

public class Clusterizer {
	
	Boolean verbose = false;
 
	LearningFunction lf = new LearningFunction();
 //chose cluster
 //Send data -> id node and their clusters
 //Define cluster head
	public Clusterizer(){
		super();
	}
	
	
	



	public double[] clusterHeadSelection(ArrayList<Neuron> neur){
		double[] dist = {0,0}; 
		double totalWx =0, totalWy =0;
		for(Neuron n : neur){
			for(int i=0 ; i<n.getWeightsNumber() ; i++)
				totalWx +=  n.getX();
				totalWy += n.getY();
		}
		
		double X = totalWx  / neur.size();
		double Y = totalWy/ neur.size();
		
		dist[0] = X;
		dist[1] = Y;
		
		System.out.println(dist.toString());
		
		return dist;
	}
	
	//Calculate Sum of distance between input vector and neural weights
	//Call normalizing funct 
	//if result>= threshold => neuron is part of cluster  ! 
	public int calculateNeuralActivity(LearningFunction lf ,double[] weights, double[] input, double threshold){
		this.lf = lf; 
		double dist = lf.euclideanDistance(input, weights);
		if(verbose) System.out.println("dist: "+dist);
		double activationFactor = logisticActivationFunction(dist);
		if (activationFactor >= 0){
			if(activationFactor > threshold && activationFactor <= 1)
			{return 1;}
			else{ return 0;}
			}
		else return -1;
	}
	
	//Logistic Activation function 
	//Normalized values
	//returns value between 0.0 and 1.0 
	protected double logisticActivationFunction(double distSum) {  
		double dist1 = linearActivationFunction(distSum);
		double res = 1/(1+Math.exp(-6*(dist1-0.5)));
		if(res >= 0 && res <= 1)
			{if(verbose) System.out.println(res);
			return res;}
		System.out.println("res: " + res);
		//else
		return -1;
    }
	
	//Linear function
	protected double linearActivationFunction(double distSum) {  
		double res = (distSum/Math.sqrt(20000) );
		if(res >= 0 && res <= 1)
			{if(verbose) System.out.println("distsqrt: "+ res);
			return res;}
		else return -1;
    }
	
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
						SimCommunicator.sendData(f,NbNodePerClass[r],clusterHPerNodeID[f]);
				
				 }
        	}
        	
	}
	
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
	
	
	private static void ClusterizeWithDBSCAN(ArrayList<ArrayList<Neuron>> resultClusters, LearningFunction lf, KohonenNetwork kn) {
		
		resultClusters = Dbscan.applyDbscan(kn.getKneur());
		for(int i=0; i<resultClusters.size();i++){
			ArrayList<Neuron> l = resultClusters.get(i);
			for(int j=0; j<l.size();j++){
				lf.getF().repaint(l.get(j).getX(), l.get(j).getY(), i);}
		}

		}
		
	


	
}


