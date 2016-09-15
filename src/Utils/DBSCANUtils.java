package Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import kohonenMap.Neuron;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 *
 */

public class DBSCANUtils{

	
	public static Vector<Neuron> VisitList = new Vector<Neuron>();

	public static double getDistance (Neuron p, Neuron q)
	{
		double[] weightsP = p.getWeights();
		double[] weightsQ = q.getWeights();

		double dx = weightsP[0] - weightsQ[0];

		double dy = weightsP[1] - weightsQ[1];

		double distance = Math.sqrt (dx * dx + dy * dy);

		return distance;

	}



/**
neighbourhood Neurons of any Neuron p
**/


	public static ArrayList<Neuron> getNeighbours(Neuron p)
	{
		ArrayList<Neuron> neigh = new ArrayList<>();
		Iterator<Neuron> Neurons = Dbscan.NeuronList.iterator();
	
		while(Neurons.hasNext()){
				Neuron q = Neurons.next();
				if(getDistance(p,q)<= Dbscan.e){
				neigh.add(q);		
				}
		}
		return neigh;
	}

	public static void Visited(Neuron d){
	VisitList.add(d);
	
	}

	public static boolean isVisited(Neuron c)
	{
		if (VisitList.contains(c))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static ArrayList<Neuron> Merge(ArrayList<Neuron> neighbours,ArrayList<Neuron> neighbours2)
	{
	
	for(Neuron n : neighbours2){
		int contains = 0;
		for(Neuron ne : neighbours)	{		
		if (ne.equals(n) ){
			contains = 1;
		}}
		if(contains == 0)
			neighbours.add(n);
	}
	return neighbours;
	}



//  Returns NeuronsList to DBscan.java 

	public static ArrayList<Neuron> getList(Neuron[] neurList) {
	
	ArrayList<Neuron> newList =new ArrayList<Neuron>();
	newList.clear();
	
	for(Neuron n : neurList)
	newList.add(n);
	
	return newList;
	}		

	public static Boolean equalNeurons(Neuron m , Neuron n) {
	if((m.getX()==n.getX())&&(m.getY()==n.getY()))
		return true;
	else
		return false;
	}	

	}
