package ui.graph.layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import graph.Edge;
import graph.JungGraph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CircularGraphLayout extends AbstractGraphLayout{

	@Override
	public void computeLayout(Dimension plane) {
		final edu.uci.ics.jung.graph.Graph<Vertex, Edge> g = new JungGraph<Vertex, Edge>(getGraph()) ;
		final CircleLayout<Vertex, Edge> layout = new CircleLayout<Vertex, Edge>(g);
		layout.setSize(new Dimension(plane.width-30,plane.height-30));
		layout.initialize();
		layout.reset();
		for(Vertex v:getGraph().getVertecies()){
			this.setVertexLocation(v, new Double(layout.getX(v),layout.getY(v)) );
		}
	}

//	int c = 1;
//	int i = 0;
//	double r = 100;
//	double xc,yc;
//	
//
//	public void computeLayout(Dimension plane) {
//		xc = plane.width  /2;
//		yc = plane.height /2;
//		r  = Math.sqrt(xc*xc+yc*yc)/2;
//		Vertex[] vertices = getGraph().getVertecies();
//		c = vertices.length;
//		for(Vertex v:vertices){
//			setVertexLocation(v, next());
//		}
//	}

//	public Point2D.Double next(){
//		
//		final double x =  (xc + r * Math.cos(i * 2 * Math.PI/c));
//		final double y =  (yc + r * Math.sin(i * 2 * Math.PI/c));
//		
//		i=(i+1)%c;
//		return new Point2D.Double(x,y);
//	}

}
