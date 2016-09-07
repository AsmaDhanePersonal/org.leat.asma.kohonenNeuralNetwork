package Utils;

import java.util.ArrayList;

import kohonenMap.LearningFunction;
import kohonenMap.Neuron;

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
	
}
