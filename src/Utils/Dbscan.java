package Utils;


import java.util.*;

import kohonenMap.Neuron;

/**
 * 
 * @author: Asma Dhane
 *  asmadhane@gmail.com
 *  
 *
 */

//This implementation of DBSCAN is inspired from an existing implementation

public class Dbscan
{
	
	public static int e;
	public static int minpt;
	
	public static ArrayList<ArrayList<Neuron>> resultList = new ArrayList<>();
	
	public static ArrayList<Neuron> NeuronList ;
	 	    
    public static ArrayList<Neuron> Neighbours ;
    
		
		public static ArrayList<ArrayList<Neuron>> applyDbscan(Neuron[] neurList)
		{
			resultList.clear();
			DBSCANUtils.VisitList.clear();
			
			NeuronList = DBSCANUtils.getList(neurList);
			
			int index2 =0;
			
						
			while (NeuronList.size()>index2){
				Neuron p =NeuronList.get(index2);
				 if(!DBSCANUtils.isVisited(p)){
				
					DBSCANUtils.Visited(p);
					
					Neighbours =DBSCANUtils.getNeighbours(p);
					
					
					if (Neighbours.size()>=minpt){
						
						
						int ind=0;
						while(Neighbours.size()>ind){
							
							Neuron r = Neighbours.get(ind);
							if(!DBSCANUtils.isVisited(r)){
								DBSCANUtils.Visited(r);
							ArrayList<Neuron> Neighbours2 = DBSCANUtils.getNeighbours(r);
							if (Neighbours2.size() >= minpt){
								Neighbours=DBSCANUtils.Merge(Neighbours, Neighbours2);
								}
							} ind++;

				
						System.out.println("N"+Neighbours.size());
				
						resultList.add(Neighbours);
						}
					}
				
				
				 }index2++;
			}return resultList;	
		}
		
}
		

	