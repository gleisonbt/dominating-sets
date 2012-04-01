package ui.graph.layout;

import graph.Graph;

import java.awt.Dimension;
import java.awt.Point;

public class PlannerGraphLayout extends AbstractGraphLayout{
	private final double C;
	private final int n;
	private final Dimension SIZE;
	private final Graph graph;
	public PlannerGraphLayout(Graph g,Dimension size){
		this.SIZE = size;
		this.graph = g;
		this.n = g.getVerticies().length;
		C = Math.sqrt(n/Math.PI);
	}
	double cool(int i){
		return (1+Math.pow(i*Math.PI/n,3/2))/C;
	}
	@Override
	public void computeLayout(Dimension plane) {
	}
	public Point next(){
		return new Point();
	}
}
