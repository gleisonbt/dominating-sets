package ui.graph.layout;

import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;

public class SpiralGraphLayout extends AbstractGraphLayout {
	int c = 1;
	int i = 0;
	double r,u=1;
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
		
		final int x = (int) (xc + r*u * Math.cos(i * 2 * Math.PI/c));
		final int y = (int) (yc + r*u * Math.sin(i * 2 * Math.PI/c));
		u=1-(0.1*(i%8));
		i=(i+1)%c;
		return new Point(x,y);
	}
}
