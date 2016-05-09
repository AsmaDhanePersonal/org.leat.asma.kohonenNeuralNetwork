package kohonenMap;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author Amidala
 *
 */

//Kohonen neuron 
public class Neuron {
	
	protected int id; 

	protected double[] weights; //randomly initiated weights 

	//TODO: add activation fnction 
	
	public Neuron(int weightNumber, double[] maxWeight){
        if(weightNumber == maxWeight.length){
            Random rand = new Random();
            weights = new double[weightNumber];
            for(int i=0; i< weightNumber; i++){
                weights[i] = rand.nextDouble() * maxWeight[i];
            }
        }
    }
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
}
