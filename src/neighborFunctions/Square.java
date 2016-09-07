package neighborFunctions;

import java.util.ArrayList;
import java.util.List;

import kohonenMap.Neuron;

public class Square {
		/* 
		//Get neighbors
		//Radius = Grid size. 
		//neighbours = 
		Calculate manhattan distance
		|x0-x1| = r or |y0-y1| = r
		 */
		protected List<Neuron> neighbors = new ArrayList<>();
		
		public  List<Neuron> getNeighbors() {
			return neighbors;
		}
		public void setNeighbors(List<Neuron> neighbors) {
			this.neighbors = neighbors;
		}
		
		public List<Neuron> getNeighbors(Neuron[] kn, double x, double y, double radius){
			
			for(Neuron n : kn){
				for(int r=0;r<radius;r++) {
				if(Math.abs((x-n.getX())+ (y-n.getY()))== r){
					neighbors.add(n);
				}		
				}
			}
					
			return neighbors;
		}
		

}
