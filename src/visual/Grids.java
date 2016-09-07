package visual;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;


import kohonenMap.KohonenNetwork;
import kohonenMap.Neuron;
import topology.Grid;

 @SuppressWarnings("serial")
public class Grids extends Canvas{
	 int width, height;
	 int id;
	 double x, y, x1,y1, x2, y2;
	 double deltaW , deltaH;
	 KohonenNetwork kn; 
	 Graphics g;
	 Grid grid;
	 
	 public Grids(int height , int width, KohonenNetwork kn, Grid grid){
		 this.kn= kn ;
		 this.width = width;
		 this.height = height;
		 this.grid = grid;
		 deltaW = width/100;
		 deltaH = height/100;
		 this.setBounds(110, 10 , height, width);
		 
		 
		 }
	 
	 public void paint(Graphics g) {
		 
		 Graphics2D gg = (Graphics2D) g;

		    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		    
		    Ellipse2D.Double ellipse = new Ellipse2D.Double(x+1, y-1, 5, 5);
		    
		    
		    gg.drawString(String.valueOf(id), (float)x, (float)y);
		    
		    gg.draw(ellipse);
		    gg.setColor(Color.BLACK);
		    gg.fill(ellipse);
		 
	 }
	 
	 public void repaint() {
			g = this.getGraphics();
			g.clearRect(0, 0, 600, 600);
			
				 
				  this.paint(g);
				  
			
	 
	    for(Neuron n : kn.getKneur())
		{ 

			x =   (n.getX() * deltaH)  ; //0,0
			y =  (n.getY() * deltaW ) ; // 0,0
			id = n.getId();
			
		paint(g);
	    		}
		}
	 
	 
	 public void repaint(double x, double y) {
			
		 	g= this.getGraphics();
			
			Graphics2D gg = (Graphics2D) g;

		    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		    
		    x =   x * deltaH  ; //0,0
			y =  y * deltaW  ; // 0,0
		    Ellipse2D.Double ellipse = new Ellipse2D.Double(x+1, y-1, 10, 10);
		    gg.draw(ellipse);
		    gg.setColor(Color.RED);
		    gg.fill(ellipse);
	    		
		}
	 
	 public void repaint(double x, double y, int clust) {
			
		 Color color;
		 switch(clust){
		 case 1: color = Color.GREEN; break;
		 case 2: color = Color.YELLOW; break;
		 case 3: color = Color.CYAN; break;
		 case 4: color = Color.RED; break;
		 default: color = Color.darkGray ; break;
		 }
			 
		 	g= this.getGraphics();
			
			Graphics2D gg = (Graphics2D) g;

		    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		    
		    x =   x * deltaH  ; //0,0
			y =  y * deltaW  ; // 0,0
		    Ellipse2D.Double ellipse = new Ellipse2D.Double(x+1, y-1, 5, 5);
		    gg.draw(ellipse);
		    
		    gg.setColor(color);
		    gg.fill(ellipse);
	    		
		}
	
	 
	 /*
	public void paint(Graphics g) {
		
		
		
		//System.out.println("pos: ("+x+","+y+")");
		Graphics2D gg = (Graphics2D) g;

	    /* Enable anti-aliasing and pure stroke 
	    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	   
	    
	    /* Construct a shape and draw it 
	    for(int i= 0 ; i < grid.getLen()-1; i++)
		{ 
		  for(int j=0; j< grid.getWid()-1 ; j++)
	    		{		
			x =   (grid.getNeuronAt(i, j).getX() * deltaH)  ; //0,0
			y =  (grid.getNeuronAt(i, j).getY() * deltaW )  ; // 0,0
			
		    if(i == grid.getLen()-1){
		    	
		    }
			x1 =  (grid.getNeuronAt(i ,j+1).getX() * deltaH)  ;  //0,1
			y1 =  (grid.getNeuronAt(i, j+1).getY() * deltaH)  ;//0,1
			x2 =  (grid.getNeuronAt(i+1, j).getX() * deltaH)  ;//1,0
			y2 =  (grid.getNeuronAt(i+1, j).getY() * deltaH)  ;//1,0
			
			gg.setColor(Color.BLACK);
	    Line2D.Double lineV = new Line2D.Double(x + 2.5, y + 2.5, x1+ 2.5, x1+ 2.5);
	    Line2D.Double lineH = new Line2D.Double(x+ 2.5, y + 2.5, x2+ 2.5, y2+ 2.5);
	    gg.draw(lineH);
	    gg.draw(lineV);
	    Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, 5, 5);
	    gg.draw(ellipse);
	    gg.setColor(Color.CYAN);
	    gg.fill(ellipse);
	    if(j == grid.getLen()-1){
	    	x =   (grid.getNeuronAt(i, j+1).getX() * deltaH)  ; //0,0
			y =  (grid.getNeuronAt(i, j+1).getY() * deltaW )  ; // 0,0
		  ellipse = new Ellipse2D.Double(x, y, 5, 5);
		    gg.draw(ellipse);
		    gg.setColor(Color.CYAN);
		    gg.fill(ellipse);
	    }
	    if(i == grid.getLen()-1){
	    	x =   (grid.getNeuronAt(i+1, j).getX() * deltaH)  ; //0,0
			y =  (grid.getNeuronAt(i+1, j).getY() * deltaW )  ; // 0,0
		  ellipse = new Ellipse2D.Double(x, y, 5, 5);
		    gg.draw(ellipse);
		    gg.setColor(Color.CYAN);
		    gg.fill(ellipse);
	    }}
	    		
	    		}
		
		
		}
	
	public void repaint() {
		g = this.getGraphics();
		g.clearRect(0, 0, 500, 500);
		
			 
			  this.paint(g);
			  
		}
		
	
	*/

 }
	 


