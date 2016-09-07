package topology;

import kohonenMap.KohonenNetwork;
import kohonenMap.Neuron;

 

public class Grid {
	
	Neuron[][] matrix ;
	KohonenNetwork kn;
	int len; 
	int wid;

	public Grid(int len , int wid, KohonenNetwork kn ) {
		super();
		
		this.len = len;
		this.wid = wid;
		this.kn = kn;
		
		matrix = new Neuron[len][wid];
		
		int k=0;
		
		for(int i=0; i<wid; i++){
			for(int j=0; j<len; j++){
				matrix[i][j] = kn.getNeuron(k);
				kn.getNeuron(k).setY(j*100/wid);
				kn.getNeuron(k).setX(i*100/len);
				double[] weights = {(double)j*100/wid ,(double)i*100/len};
				kn.getNeuron(k).setWeights(weights) ;
				
				
				k++;
			}
		}
		
		
	}
	
	public Neuron[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Neuron[][] matrix) {
		this.matrix = matrix;
	}

	public KohonenNetwork getKn() {
		return kn;
	}

	public void setKn(KohonenNetwork kn) {
		this.kn = kn;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}
	
    public Neuron getNeuronAt(int i, int j){
    	return matrix[i][j];
    }
	
}