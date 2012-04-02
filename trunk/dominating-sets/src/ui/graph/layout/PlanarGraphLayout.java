package ui.graph.layout;

import graph.GraphMetrics;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * 
 * This algorithm tries to reposition nodes according to the 
 * attaching forces.
 * <pre>
 * 1. divide the set of nodes into outer face U, inner face W
 *    where V = U+W
 * 
 * 2. for each <em>u</em> in U (nodes with highest degree) place 
 *    it on a polygon of <em>k</em> sides, or a circle circumference
 *    while for each <em>w</em> in W is placed at the origin.
 * 
 * 3. apply the force function, cool off when required.
 * 
 * 4. iterate until the graph is relaxed and nothing can be 
 *    further moved significantly
 * </pre>
 * 
 * @author Hussain Al-Mutawa
 * @version 1.0
 */
public class PlanarGraphLayout extends AbstractGraphLayout{
	
	private Map<Vertex,Double>forces;
	private double C;
	private int n;
	double cool(int i){
		return (1+Math.pow(i*Math.PI/n,3/2))/C;
	}
	@Override
	public void computeLayout(Dimension plane) {
		final Vertex[]	  V = getVertecies();
		final Set<Vertex> U = new HashSet<Vertex>();
		final Set<Vertex> W = new HashSet<Vertex>();
		final double beta   = (Double)getGraph().getMetric(GraphMetrics.AverageDegree); 
		int xc = plane.width  /2;
		int yc = plane.height /2;
		this.forces=new HashMap<Vertex,Double>();
		
		for(Vertex v:V){
			forces.put(v, 0.0);		// relax all of the forces
			
			if(v.degree()>beta){	// those with degree above the average are face up 
				U.add(v);
			}else{					// below average degree are face down
				W.add(v);
				setVertexLocation(v, new Point2D.Double(xc,yc));
			}
		}
		
		
		//final Random r = new Random();
		int i=0;
		
		double r  = Math.sqrt(xc*xc+yc*yc)/2;
		n = getVertecies().length;
		
		for(Vertex u: U){
			final int x = (int) (xc + r * Math.cos(i * 2 * Math.PI/U.size()));
			final int y = (int) (yc + r * Math.sin(i * 2 * Math.PI/U.size()));
			i++;
			setVertexLocation(u, new Point2D.Double(x,y));
		}
		
		
		C = Math.sqrt(n/Math.PI);
		int iterations = 10;
		for(i=0;i<iterations;i++){
			for(Vertex v: W){
				for(Vertex u:v.getNeighborVertecies()){
					double Fuv = force(v,u);
					forces.put(u, forces.get(u)+Fuv);
					forces.put(v, forces.get(v)-Fuv);
				}
				for(Vertex w: U){
					final Point2D.Double wpos = getVertexLocation(w);
					final double Fw = forces.get(w);
					final double move = (Math.min(Math.abs(Fw), cool(i))*Fw/Math.abs(Fw));
					final double x = wpos.x+move;
					final double y = wpos.y+move;
					setVertexLocation(w,new Point2D.Double(x, y));
					System.out.println(move);
				}
			}
		}
	}
	public Point2D.Double next(){
		
		return null;
	}
	/**
	 * distance between two vertices, using trignometry formula
	 */
	double delta(Point2D.Double v,Point2D.Double u){
		final double x = v.x - u.x;
		final double y = v.y - u.y;
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	double force(Vertex v,Vertex u){
		final double d = delta(getVertexLocation(v), getVertexLocation(u));
		return C*Math.pow(d, 3);
	}
}
