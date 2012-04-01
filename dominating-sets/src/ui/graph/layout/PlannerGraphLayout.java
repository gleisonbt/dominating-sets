package ui.graph.layout;

import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlannerGraphLayout extends AbstractGraphLayout{
	private Map<Vertex,Double>forces;
	private double C;
	private int n;
	double cool(int i){
		return (1+Math.pow(i*Math.PI/n,3/2))/C;
	}
	@Override
	public void computeLayout(Dimension plane) {
		this.forces=new HashMap<Vertex,Double>();
		final Random r = new Random();
		for(Vertex v: getVertecies()){
			forces.put(v, 0.0);			// relax all of the forces
			final int x = (int)Math.abs(r.nextInt(plane.width-30));
			final int y = (int)Math.abs(r.nextInt(plane.height-30));
			setVertexLocation(v, new Point(x,y));
		}
		this.n = getVertecies().length;
		C = Math.sqrt(n/Math.PI);
		int iterations = 10;
		for(int i=0;i<iterations;i++){
			for(Vertex v: getVertecies()){
				forces.put(v, 0.0);
				for(Vertex u:v.getNeighborVertecies()){
					double Fuv = force(v,u);
					forces.put(u, forces.get(u)+Fuv);
					forces.put(v, forces.get(v)-Fuv);
				}
				Point vpos = getVertexLocation(v);
				double Fv = forces.get(v);
				int move = (int) (Math.min(Math.abs(Fv), cool(i))*Fv/Math.abs(Fv));
				final int x = vpos.x+move;
				final int y = vpos.y+move;
				setVertexLocation(v,new Point(x, y));
				System.out.println(x+";"+y);
			}
		}
	}
	public Point next(){
		return new Point();
	}
	/**
	 * distance between two vertices, using trignometry formula
	 */
	double delta(Point v,Point u){
		final int x = v.x - u.x;
		final int y = v.y - u.y;
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	double force(Vertex v,Vertex u){
		final double d = delta(getVertexLocation(v), getVertexLocation(u));
		return C*Math.pow(d, 3);
	}
}
