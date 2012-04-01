package ui.graph.layout;

import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

public class RandomGridGraphLayout extends AbstractGraphLayout {
	boolean[][]grid;
	int Cx = 15,Cy = 15;
	@Override
	public void computeLayout(Dimension plane) {
		final int vs = (int) (Math.sqrt( getVertecies().length ));
		final int xs = plane.width/vs;
		final int ys = plane.height/vs;
		
		Cx = (int)(Math.sqrt(Math.PI*(xs*xs)/4));
		Cy = (int)(Math.sqrt(Math.PI*(ys*ys)/4));
		
		grid = new boolean[vs+1][vs+1];
		for(Vertex v:getVertecies()){
			setVertexLocation(v, next());
		}
	}
	public Point next(){
		final Random random = new Random();
		boolean notempty = true;
		int x=0,y=0; int counter=0;
		while(notempty && counter++<100){
			x = random.nextInt(grid.length);
			y = random.nextInt(grid[0].length);
			notempty = grid[x][y];
		}
		grid[x][y] = true;
		return new Point(x*Cx,y*Cy);
	}
}
