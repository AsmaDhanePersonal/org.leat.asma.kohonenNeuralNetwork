package kohonenMap;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 * @author Amidala
 *
 */
/*
 * This class defines the learning algorithm of the Kohonen Network
 * it uses Euclidian distance as a metric 
 * Step1: Load input vector
 * Step2: Get best matching unit by calculating the minimal distance between 
 * a node's weight vector and the input vector
 * Step 3: The winning node learns the data (weight modification)
 * Step 4: Modify the neighbors weights(the learning fades as a gaussian func)
 * Repeat until number of iteration reached (no more learning vectors)
 */

public class LearningFunction {
	//TODO weight modification 
	//TODO neighborhood function 
	//TODO: while more vectors coming -> learn. 
//While more learning vectors coming, this function takes them and then counts the number of neurons

	private KohonenNetwork kn; 
	public int getNumIteration() {
		return numIteration;
	}

	public void setNumIteration(int numIteration) {
		this.numIteration = numIteration;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	private int numIteration;
	private int radius = 4 ; 
	
	
	
	public LearningFunction(KohonenNetwork kn , int numIteration){
		this.kn = kn; 
		this.numIteration = numIteration; 
	}
	
	//For each vector OR for the entire collection ? 
	//depends on the implementation with OMNET++ .. �_�
	public void learn(double[] vector)
	{ 
		System.out.println("Learnin starts here ...");
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
	                changeNeuralWeight(bestNeuron, vector, radius);
		
	}
	
	public void learn(ArrayList<double[]> vectors)
	{
		System.out.println("Learnin starts here ...");
		for(double[] vector : vectors){
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
	                changeNeuralWeight(bestNeuron, vector, radius);
		}
		
	}
	
	protected void changeNeuralWeight(Neuron neur ,  double[] inputvect, int r){
		//change neuron weight
		//change neighbors weight - Gaussian extension
		
		int weights = neur.weights.length;
		System.out.println("Initial weight:");
		System.out.println(neur.toString());
		for(int i=0; i< weights; i++ ){
			neur.weights[i] += getGaussianValue(i,r) *  inputvect[i];
		}
		System.out.println("Modified weight:");
		System.out.println(neur.toString());
		
		
		
	}
	
	// BMU with the actual Learning vector determination 
	protected Neuron bestMatchingUnit(double[] inputVector){
		Neuron neur; 
		double minDist = -1, distance = -1;
		Neuron bestNeur = null; 
		int neurnum = kn.getNeuronNumber();
		for(int i = 0 ; i<neurnum ; i++ )
		{
			neur= kn.getNeuron(i);
			distance = euclideanDistance(inputVector, neur.getWeights());
			  if((distance < minDist) || (minDist == -1)){
	                minDist = distance;
	                bestNeur = neur;
	            }		
		}
		return bestNeur;
	}
	
	//Distance between the neural weight and the input vector
	protected double euclideanDistance(double[] inputVector, double[] neuralVector){
	        double x = 0,
	        		w =0 ,
	        		distance = 0, 
	        		sum  = 0;
	        
	        int weightSize = inputVector.length;
	        
	        if(weightSize != neuralVector.length)
	            return -1;
	        
	        for(int i=0; i< weightSize; i++){
	            w = inputVector[i]; 
	            x = neuralVector[i];
	            sum += (x - w) *( x - w);
	        }
	        
	        distance = Math.sqrt(sum);
	        return distance;
	}
	
	//Gaussian value    (interation, radius) 
	private double getGaussianValue(int k,int r){
	       return java.lang.Math.exp(-(java.lang.Math.pow(k,2))/ (2 * r * r));
	    }

	
	public KohonenNetwork getKn() {
		return kn;
	}

	public void setKn(KohonenNetwork kn) {
		this.kn = kn;
	}

}
