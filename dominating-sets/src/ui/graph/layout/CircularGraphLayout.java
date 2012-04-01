package ui.graph.layout;

import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;

public class CircularGraphLayout extends AbstractGraphLayout implements GraphLayout {

	int c = 1;
	int i = 0;
	double r = 100;
	int xc,yc;
	
	@Override
	public void computeLayout(Dimension plane) {
		xc = plane.width  /2;
		yc = plane.height /2;
		r  = Math.sqrt(xc*xc+yc*yc)/2;
		Vertex[]vertices = getVertecies();
		c = vertices.length;
		for(Vertex v:vertices){
			setVertexLocation(v, next());
		}
	}
	@Override
	public Point next(){
		
		final int x = (int) (xc + r * Math.cos(i * 2 * Math.PI/c));
		final int y = (int) (yc + r * Math.sin(i * 2 * Math.PI/c));
		
		i=(i+1)%c;
		return new Point(x,y);
	}

}
