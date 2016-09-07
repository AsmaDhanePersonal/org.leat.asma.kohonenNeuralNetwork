package kohonenMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import topology.Grid;



/** 
 * 
 * @author Asma DHANE
 *
 * This class is for the network creation and definition
 * The number of neurons is equal to the number of nodes in the WSN
 * so equal to the number of learning vectors 
 *
 */


public class KohonenNetwork {
	
	protected Neuron[] kneuron; 
	protected LearningFunction learningfunction; 
	protected int neuronNumber; //probably not known till we get the number of input vectors
	
	/* NOT SURE NEEDED
	public KohonenNetwork(Neuron[] kneur, LearningFunction lf, Topology topo) {
		super();
		kneuron = kneur;
		learningfunction = lf;
	//	topology = topo;
	}*/
	
	// =============  Constructor for network init ======== //
	
	//TODO: adjust weight num to input vectors. (dynamically)
	
	//neuron number comes from number of inputvectors .. 
	
	 public KohonenNetwork(int weightNumber, double maxWeight, int neurNum) {
	        kneuron = new Neuron[neurNum]; 
	        this.neuronNumber = neurNum;
	        for (int i=0; i<neurNum; i++){
         
	            kneuron[i] = new Neuron(weightNumber,maxWeight);
	            
	        }
	        
	        
	    }
	 
	      // ~ Just some kawaii GETTERS & SETTERS ~ //
///////// ========================================== ////////////
	 
	
	//to write all the neurons in a csv file
	public void writeFile(){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("C:/DataSet/out.csv"));
			System.out.println("Starting writing to CSV file!");
			for(int i=0; i<this.neuronNumber; i++){
		        pw.write(this.getNeuron(i).toStringForCSV());
			}
		        pw.close();
		        System.out.println("done!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}


	 public Neuron getNeuron(int index){
		 return kneuron[index];
	 }
	
	public Neuron[] getKneur() {
		return kneuron;
	}
	public void setKneur(Neuron[] kneur) {
		this.kneuron = kneur;
	}
	public LearningFunction getLf() {
		return learningfunction;
	}
	public void setLf(LearningFunction lf) {
		this.learningfunction = lf;
	}


	public int getNeuronNumber() {
		return neuronNumber;
	}

	public void setNeuronNumber(int neuronNumber) {
		this.neuronNumber = neuronNumber;
	} 
	
///////// ========================================== ////////////
	
}
