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

	KohonenNetwork kn; 
	int numIteration;
	
	
	public LearningFunction(KohonenNetwork kn , int numIteration){
		this.kn = kn; 
		this.numIteration = numIteration; 
	}
	
	//For each vector OR for the entire collection ? 
	//depends on the implementationwith OMNET++ .. é_è
	public void learn(double[] vector)
	{ 
		System.out.println("Learnin starts here ...");
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
	                changeNeuralWeight(bestNeuron, vector);
		
	}
	
	public void learn(ArrayList<double[]> vectors)
	{
		System.out.println("Learnin starts here ...");
		for(double[] vector : vectors){
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
	                changeNeuralWeight(bestNeuron, vector);
		}
		
	}
	
	protected void changeNeuralWeight(Neuron neur ,  double[] inputvect){
		//change neuron weight
		//change neighbors weight - Gaussian extension
		//For now it is winner takes all function (only the winning neuron wins)
		
		int weights = neur.weights.length;
		System.out.println("Initial weight:");
		System.out.println(neur.toString());
		for(int i=0; i< weights; i++ ){
			neur.weights[i]= inputvect[i];
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
	
	public KohonenNetwork getKn() {
		return kn;
	}

	public void setKn(KohonenNetwork kn) {
		this.kn = kn;
	}

}
