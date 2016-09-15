package kohonenMap;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Utils.SimCommunicator;
import neighborFunctions.Square;
import topology.Grid;
import visual.Grids;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
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
	boolean verbose = false;
	double initialLearningRate = 0.5; 
	double finalLearningRate =0.001;
	double initRadius = 100;
	double finalRadius = 0.001;
	private int numIteration;
	Square squareTopo ;
	int count = 0; 
	private KohonenNetwork kn; 
	Grids f;
	int iMax;
	double learningRate = initialLearningRate;

	
	double neighborWidth;
	double gaussVal;
	double distanceToNode;
	Grid grid;

   // Basic constructor
	
	public LearningFunction(){
		super();
	}
	
	
	public LearningFunction(KohonenNetwork kn , int numIteration , int iMax, Grid grid){
		this.iMax=iMax;
		this.kn = kn; 
		this.numIteration = numIteration; 
		this.grid = grid;
		squareTopo = new Square();
		
		JFrame frame = new JFrame();
		  f = new Grids(500,500,kn, grid);
	      frame.setTitle("Drawing Graphics in Frames");
	      frame.setBounds(100,00,600, 600);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.add(f);
	      frame.setVisible(true);
	      
	}
	
	//For each vector OR for the entire collection ? 
	//depends on the implementation with OMNET++ .. é_è
	
public void learn(double[] vector, int iteration, int iMax)
	{   	 	
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
	                changeNeuralWeight(bestNeuron, vector, iteration, iMax);
	                
	                double x1 = vector[0];
	                double y1 = vector[1];
	       		 
	       		 f.repaint(x1,y1);
	        if(verbose){
			System.out.println("neigh width: " + neighborWidth);
			System.out.println("learnRate: " + learningRate);}
			
			f.repaint();
		
	} 
	
	
	public void learn(ArrayList<double[]> vectors, int iteration, int iMax)
	{	
		
	
		//For each input vector, check for BMU and change neuralWeight
		for(double[] vector : vectors){
		 Neuron bestNeuron = null ; 
		 bestNeuron = bestMatchingUnit(vector);
		 changeNeuralWeight(bestNeuron, vector,iteration, iMax);
		 double x1 = vector[1];
		 double y1 = vector[2];
		 
		 f.repaint(x1,y1);
		}
		if(verbose){
		System.out.println("neigh width: " + neighborWidth);
		System.out.println("min squad err: " + getMinSquareError(vectors));
		System.out.println("learnRate: " + learningRate);
		}
		
		f.repaint();
		
		
	}
	
	// BMU with the actual Learning vector determination 
	public Neuron bestMatchingUnit(double[] inputVector){
		double minDist = -1, distance = -1;
		Neuron bestNeur = null; 
		for(Neuron neur : kn.getKneur())
		{
			distance = euclideanDistance(inputVector, neur.getWeights());
			  if((distance < minDist) || (minDist == -1)){
	                minDist = distance;
	                bestNeur = neur;
	            }		
		}
		//System.out.println("The BMU is:"+ bestNeur.getId()+ ". it's initial weight vector is:["+ bestNeur.weightsToString()+"]");
		if(verbose) System.out.println("BMU id=" + bestNeur.getId() + " weights:" + bestNeur.weightsToString());
		return bestNeur;
	}
	

	protected void changeNeuralWeight(Neuron neur, double[] inputvect, int it,int  iMax){
		//The BMU take the vector as weights
		int weights = neur.weights.length;
		for(int i=0; i< weights; i++ ){
			neur.weights[i] = inputvect[i];
		}
		
		//NEighbors weight shifting .. 
		
		 neighborWidth = getNeighbourhoodWidth(it);
		
		 List<Neuron> neurList = squareTopo.getNeighbors(kn.getKneur(), neur.getWeights(), neighborWidth );
		 
		 for(Neuron n : neurList){

		 distanceToNode =  (neur.getX()- n.getX())*(neur.getX()- n.getX())
					+(neur.getY())*(neur.getY());
		 
		 learningRate = getLearningRate(it);
		 
		 gaussVal = getGaussianNeighbeourhoodValue(distanceToNode, it, neighborWidth);
		for(int i=0; i< weights; i++ ){
			n.weights[i] +=  learningRate * gaussVal * (inputvect[i]-n.weights[i]);		
			
		}
		
		n.setX(n.getWeights()[1]);
	    n.setY(n.getWeights()[2]);
		
			 }


		
		
		
	}
	
	private double getLearningRate(int it) {
		float puiss = (float) it/iMax; 
		return  initialLearningRate * Math.pow((finalLearningRate/initialLearningRate), puiss);
		
	}

	
	public double potentiel(double[] inputVector, double[] neuralVector){
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
            sum += x*w;
        }
        
        distance = Math.sqrt(sum);
        return distance;
	
	}
	
	//Distance between the neural weight and the input vector
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
	
	//Gaussian value(interation, radius) 
	private double getGaussianNeighbeourhoodValue(double dist,int it, double neighborWidth){
			
	       return Math.exp((- Math.pow(dist, 2)) / (2*Math.pow(neighborWidth,2)));
	}
	
	double minsqrerr ;
	
	private double getMinSquareError(ArrayList<double[]> inputVectors){
		double distsum = 0; 
		for(Neuron n : kn.getKneur()){
			for(double[] vect : inputVectors){
				distsum += euclideanDistance(vect, n.getWeights());
			}
			

		}
		
		minsqrerr = distsum / inputVectors.size();
		
		return minsqrerr;
		
	}
	
	
	
	private double getNeighbourhoodWidth(int it) {
		float puiss = (float)it/iMax;
		return initRadius * Math.pow((finalRadius/initRadius),puiss);
	}
	
	

	public void learnFromFile(int VectNum, int weightNumber, int iMax, ArrayList<double[]> inputVectors , String in , LearningFunction lf, int count) {
		
		for(int j =0 ; j<VectNum-1 ; j++){
			String learnVectStr ;
			String[] strArray = new String[3];
			double[] learnVect = new double[weightNumber];
			do{
			learnVectStr = SimCommunicator.readFromSim(in, count);
			if(count==26)
			System.out.println(count);
			}while(learnVectStr.equals("AGAIN"));
			if(!learnVectStr.equals("NOP.")) 
			{count++;
			System.out.println(count);
			strArray = learnVectStr.split(" ");
			learnVect[0] = Double.parseDouble(strArray[0]);
			learnVect[1] = Double.parseDouble(strArray[1]);
			learnVect[2] = Double.parseDouble(strArray[2]);
			learnVect[3] = Double.parseDouble(strArray[3]);
			inputVectors.add(learnVect);}
			else{break;}
			}
		
		for(int i=0;i<iMax;i++){
			
			if (inputVectors.size() < 30) break;
			else{

	    //LEARNING !!  
		 lf.learn(inputVectors, i, iMax);	}
		//lf.learn(learnVect, i, iMax);
		}
		
	}


	//////======== local variables Getters and Setters =====
	
	
	public int getNumIteration() {
		return numIteration;
	}

	public double getInitialLearningRate() {
		return initialLearningRate;
	}


	public void setInitialLearningRate(double initialLearningRate) {
		this.initialLearningRate = initialLearningRate;
	}


	public double getFinalLearningRate() {
		return finalLearningRate;
	}


	public void setFinalLearningRate(double finalLearningRate) {
		this.finalLearningRate = finalLearningRate;
	}


	public double getInitRadius() {
		return initRadius;
	}


	public void setInitRadius(double initRadius) {
		this.initRadius = initRadius;
	}


	public double getFinalRadius() {
		return finalRadius;
	}


	public void setFinalRadius(double finalRadius) {
		this.finalRadius = finalRadius;
	}


	public Square getSquareTopo() {
		return squareTopo;
	}


	public void setSquareTopo(Square squareTopo) {
		this.squareTopo = squareTopo;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public double getGaussVal() {
		return gaussVal;
	}


	public void setGaussVal(double gaussVal) {
		this.gaussVal = gaussVal;
	}


	public double getDistanceToNode() {
		return distanceToNode;
	}


	public void setDistanceToNode(double distanceToNode) {
		this.distanceToNode = distanceToNode;
	}


	public void setNumIteration(int numIteration) {
		this.numIteration = numIteration;
	}
	
	public KohonenNetwork getKn() {
		return kn;
	}

	public void setKn(KohonenNetwork kn) {
		this.kn = kn;
	}
	
	public Grids getF() {
		return f;
	}


	public void setF(Grids f) {
		this.f = f;
	}


	public int getiMax() {
		return iMax;
	}


	public void setiMax(int iMax) {
		this.iMax = iMax;
	}


	public double getLearningRate() {
		return learningRate;
	}


	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}


	public double getNeighborWidth() {
		return neighborWidth;
	}


	public void setNeighborWidth(double neighborWidth) {
		this.neighborWidth = neighborWidth;
	}


	public Grid getGrid() {
		return grid;
	}


	public void setGrid(Grid grid) {
		this.grid = grid;
	}


	public double getMinsqrerr() {
		return minsqrerr;
	}


	public void setMinsqrerr(double minsqrerr) {
		this.minsqrerr = minsqrerr;
	}

}
