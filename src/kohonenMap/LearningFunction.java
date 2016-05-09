package kohonenMap;
/**
 * 
 * @author Amidala
 *
 */
/*
 * This class defines the learning algorithm of the Kohonen Network
 * it uses euclidean distance as a metric 
 * Step1: Load input vector
 * Step2: Get best matching unit by calculating the minimal distance between 
 * a node's weight vector and the input vector
 * Step 3: The winning node learns the data (weight modification)
 * Step 4: Modify the neighbours wights(the learning fades as a gaussian func)
 * Repeat until number of iteration reached (no more learning vectors)
 */

public class LearningFunction {
	//TODO
//While more learning vectors coming, this function takes them and then counts the number of neurons

	KohonenNetwork kn; 
	int numIteration;
	
	
	public LearningFunction(KohonenNetwork kn , int numIteration){
		this.kn = kn; 
		this.numIteration = numIteration; 
	}
	
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
	
	public double euclideanDistance(double[] inputVector, double[] neuralVector){
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
