package kohonenMap;


/** 
 * 
 * @author Asma DHANE
 *
 * This class is for the network creation and definition
 * The number of neurons is equal to the number of nodes in the WSN
 * so equal to the number of larning vectors 
 *
 */


public class KohonenNetwork {
	
	protected Neuron[] kneuron; 
	protected LearningFunction learningfunction; 
	protected Topology topology;
	protected int neuronNumber; 
	
	/* NOT SURE NEEDED
	public KohonenNetwork(Neuron[] kneur, LearningFunction lf, Topology topo) {
		super();
		kneuron = kneur;
		learningfunction = lf;
	//	topology = topo;
	}*/
	
	//Constructor for network init
	//TODO: adjust weight num to input vectors. (dynamically)
	
	 public KohonenNetwork(int weightNumber, double[] maxWeight, int neurNum) {
	        kneuron = new Neuron[neurNum]; 
	        this.neuronNumber = neurNum;
	        for (int i=0; i<neurNum; i++){
         
	            kneuron[i] = new Neuron(weightNumber,maxWeight) ;
	         
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
	public Topology getTopo() {
		return topology;
	}
	public void setTopo(Topology topo) {
		this.topology = topo;
	}

	public int getNeuronNumber() {
		return neuronNumber;
	}

	public void setNeuronNumber(int neuronNumber) {
		this.neuronNumber = neuronNumber;
	} 
	
	
	
}
