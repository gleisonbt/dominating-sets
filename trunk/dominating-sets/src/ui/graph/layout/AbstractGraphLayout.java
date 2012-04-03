package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.geom.Point2D;

public abstract class AbstractGraphLayout implements GraphLayout {
	private Graph<?,?> graph;
	public AbstractGraphLayout() {
		super();
	}
	@Override
	public Graph<?,?> getGraph() {
		return graph;
	}
	
	@Override
	public void setGraph(Graph<?,?> graph) {
		this.graph=graph;
	}

	@Override
	public void setVertexLocation(Vertex<?,?> vertex, Point2D.Double point) {
		vertex.getViewableObject().setLocation(point);
//		try{
//			Thread.sleep(1);
//		}catch(Exception e){}
//		vui.invalidate();
//		vui.revalidate();
//		vui.validate();
//		vui.repaint();
	}

}
