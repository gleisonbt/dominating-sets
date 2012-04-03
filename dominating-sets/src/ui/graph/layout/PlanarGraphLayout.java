package ui.graph.layout;

import graph.GraphMetrics;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
		return (1+Math.pow(i,3/2)*Math.PI/n)/C;
	}
	@Override
	public void computeLayout(Dimension plane) {
		final List<?> V = getGraph().getVertecies();
		final Set<Vertex>  U = new HashSet<Vertex>();
		final Set<Vertex>  W = new HashSet<Vertex>();
		//final double beta   = (Double)getGraph().getMetric(GraphMetrics.AverageDegree); 
		int xc = plane.width  /2;
		int yc = plane.height /2;
		this.forces=new HashMap<Vertex,Double>();
		int maxd = (Integer)getGraph().getMetric(GraphMetrics.MaxDegree);
		for(final Object v:V){
			forces.put((Vertex)v, 0.0);		// relax all of the forces
			
			if(((Vertex)v).degree()<maxd){	// those with degree above the average are face up 
				U.add((Vertex)v);
				setVertexLocation((Vertex)v, new Point2D.Double(xc,yc));
			}else{					// below average degree are face down
				W.add((Vertex)v);
			}
		}
		
		if(U.size()==0){
			U.add((Vertex)V.get(0));
			W.remove(V.get(0));
		}
		
		//final Random r = new Random();
		int i=0;
		
		double r  = Math.sqrt(xc*xc+yc*yc)/2;
		n = V.size();
		
		for(Vertex w: W){
			final int x = (int) (xc + r * Math.cos(i * 2 * Math.PI/W.size()));
			final int y = (int) (yc + r * Math.sin(i * 2 * Math.PI/W.size()));
			i++;
			setVertexLocation(w, new Point2D.Double(x,y));
		}
		
		
		C = Math.sqrt(n*1.0/Math.PI);
		
		//if(C>0)return;
		
		int iterations = 5;
		for(i=0;i<iterations;i++){
			for(Object o: V){
				Vertex v = (Vertex)o;
				forces.put(v, 0.0);
				List<Vertex>N=v.getNeighborVertecies();
				for(Vertex u:N){
					double Fuv = force(v,u);
					forces.put(u, forces.get(u)+Fuv);
					forces.put(v, forces.get(v)-Fuv);
				}
				double Fv = forces.get(v);
				for(Vertex m: U){
					if(m!=v){
						final Point2D.Double mpos = m.getViewableObject().getLocationDouble();
//						final Point2D.Double vpos = getVertexLocation(v);
						final double p = (Math.min(Math.abs(Fv), cool(i))*Fv/Math.abs(Fv));
						final double x1 = mpos.x;
						final double y1 = mpos.y;
//						final double x2 = vpos.x;
//						final double y2 = vpos.y;
						
						//double p = Math.sqrt(x1*x2+y1*y2)*Math.E/(x1+x2+y1+y2);
						if(x1+p<0 || y1+p<0)continue;
						setVertexLocation(m,new Point2D.Double(x1+p, y1+p));
						//setVertexLocation(v,new Point2D.Double(x2+p, y2+p));
					}
				}
			}
		}
	}
	public Point2D.Double next(){
		
		return null;
	}
	/**
	 * distance between two vertices, using trigonometry formula
	 */
	double delta(Point2D.Double v,Point2D.Double u){
		final double x = v.x - u.x;
		final double y = v.y - u.y;
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	double force(Vertex v,Vertex u){
		final double d = delta(v.getViewableObject().getLocationDouble(), u.getViewableObject().getLocationDouble());
		return C*Math.pow(d, 3);
	}
}
