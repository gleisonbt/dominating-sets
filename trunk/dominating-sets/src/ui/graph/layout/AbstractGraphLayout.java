package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Point;
import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGraphLayout implements GraphLayout {
	private Graph graph;
	private Map<Vertex,Point2D.Double>locations;
	
	public AbstractGraphLayout() {
		super();
		this.locations=new HashMap<Vertex,Point2D.Double>();
	}
	
	@Override
	public Graph getGraph() {
		return graph;
	}@Override
	public Vertex[] getVertecies() {
		return this.graph.getVertecies();
	}
	@Override
	public Point2D.Double getVertexLocation(Vertex vertex) {
		return this.locations.get(vertex);
	}
	
	@Override
	public void setGraph(Graph graph) {
		this.graph=graph;
	}

	@Override
	public void setVertexLocation(Vertex vertex, Point2D.Double point) {
		locations.put(vertex, point);
	}

}
