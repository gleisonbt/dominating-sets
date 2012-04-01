package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class AbstractGraphLayout implements GraphLayout {
	private Graph graph;
	private Map<Vertex,Point>locations;
	
	public AbstractGraphLayout() {
		super();
		this.locations=new HashMap<Vertex,Point>();
	}
	
	@Override
	public Graph getGraph() {
		return graph;
	}@Override
	public Vertex[] getVertecies() {
		return this.graph.getVerticies();
	}
	@Override
	public Point getVertexLocation(Vertex vertex) {
		return this.locations.get(vertex);
	}
	
	@Override
	public void setGraph(Graph graph) {
		this.graph=graph;
	}

	@Override
	public void setVertexLocation(Vertex vertex, Point location) {
		locations.put(vertex, location);
	}

}