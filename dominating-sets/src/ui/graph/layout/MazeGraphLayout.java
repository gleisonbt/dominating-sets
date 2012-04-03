package ui.graph.layout;

import graph.Vertex;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public class MazeGraphLayout extends AbstractGraphLayout {

	enum dir{
		L,U,R,D
	}
	double x;
	double y;
	dir d = dir.L;
	int i = 0;
	int Cx;
	int Cy;
	int cap = 0;
	int j=0;
	int n=0;
	dir[] directions = dir.values();
	@Override
	public void computeLayout(Dimension plane) {
		x = plane.width/3;
		y = plane.height/2;
		Cx = (int) Math.sqrt(Math.PI*x+y);
		Cy = (int) Math.sqrt(Math.PI*y+x);
		
		for(Vertex v:getGraph().getVertecies()){
			setVertexLocation(v, next());
		}
	}
	@Override
	public Point2D.Double next(){
		switch(d){
		case L:
			x+=Cx;
			break;
		case U:
			y-=Cy;
			break;
		case R:
			x-=Cx;
			break;
		case D:
			y+=Cy;
			break;
		}
		if(++j>cap){
			cap++;
			j=0;
			d=directions[n++%4];
		}
		i++;
		return new Point2D.Double(x,y);
	}
}