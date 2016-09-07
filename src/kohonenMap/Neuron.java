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
	 protected int cluster = -1;
	 protected double x;
	 protected double y;
	 

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
	
	@Override
	public String toString() {
		return "Neuron [id=" + id + ", weights=" + Arrays.toString(weights) + "]";
	}

	//the weights array without the [] of the basic toString  w1,w2,w3 .. 
        // modified to write " " instead of "," to write every vector in one case of the CSV
	public String weightsToString(){
		String sb = String.valueOf(weights[0]);
				
		for(int i=1;i<this.getWeights().length;i++){
			sb += " " + String.valueOf(weights[i]);
		}
		return sb;
	}
	
	//a format that can be written into a CSV file id,w1,w2,w3 .. \n
        //Mofified to write "," at the end
	public String toStringForCSV() {
            
            int cnt=0;
  
            if(cnt < 325){
                return id + " " + weightsToString() + "\n";		
	}else
            {return id + " " + weightsToString() + ",";}
        
        }
	
	 public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public int getCluster() {
		return cluster;
	}


	public void setCluster(int cluster) {
		this.cluster = cluster;
	}


	public double getMaxWeight() {
		return maxWeight;
	}


	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}


	public int getWeightsNumber() {
		return weightsNumber;
	}


	public void setWeightsNumber(int weightsNumber) {
		this.weightsNumber = weightsNumber;
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


	
	
	

	
	
}
