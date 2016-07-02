package kohonenMap;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Amidala
 *
 */

//Kohonen neuron Class
public class Neuron {
	
	 private static final AtomicInteger count = new AtomicInteger(0); 	
	 private final int id; 
	
	 protected double[] weights;
	 protected double maxWeight;
	 protected int weightsNumber; //randomly initiated weights 
	 // protected 

	//TODO: add activation function 
	
	public Neuron(int weightsNumber, double maxWeight){
		id= count.incrementAndGet(); 
		this.weightsNumber= weightsNumber;
		this.maxWeight = maxWeight;
            Random rand = new Random();
            weights = new double[weightsNumber];
            for(int i=0; i< weightsNumber; i++){
                weights[i] = rand.nextDouble() * maxWeight;
            }
    }
	
	
	public int getId() {
		return id;
	}

	public double[] getWeights() {
		return weights;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}


	@Override
	public String toString() {
		return "Neuron [id=" + id + ", weights=" + Arrays.toString(weights) + "]";
	}

	//the weights array without the [] of the basic toString  w1,w2,w3 .. 
	public String weightsToString(){
		String sb = String.valueOf(weights[0]);
				
		for(int i=1;i<this.getWeights().length;i++){
			sb += "," + String.valueOf(weights[i]);
		}
		return sb;
	}
	
	//a format that can be written into a CSV file id,w1,w2,w3 .. \n
	public String toStringForCSV() {
		return id + "," + weightsToString() + "\n";
	}
	

	
	
}
