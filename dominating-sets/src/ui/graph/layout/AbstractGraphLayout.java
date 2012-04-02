package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ui.graph.VertexUI;

public abstract class AbstractGraphLayout implements GraphLayout {
	private Graph graph;
	//private Map<Vertex,Point2D.Double>locations;
	private Map<Vertex,VertexUI> vertices;
	public AbstractGraphLayout() {
		super();
		//this.locations=new HashMap<Vertex,Point2D.Double>();
		this.vertices=Collections.synchronizedMap(new HashMap<Vertex,VertexUI>());
	}
	@Override
	public void setVertexUIs(VertexUI[] vertecies) {
		for(VertexUI v:vertecies){
			this.vertices.put(v.getVertex(), v);
		}
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
		return this.vertices.get(vertex).getLocationDouble();
	}
	
	@Override
	public void setGraph(Graph graph) {
		this.graph=graph;
	}

	@Override
	public void setVertexLocation(Vertex vertex, Point2D.Double point) {
		final VertexUI vui = this.vertices.get(vertex);
		vui.setLocation(point);
//		try{
//			Thread.sleep(1);
//		}catch(Exception e){}
//		vui.invalidate();
//		vui.revalidate();
//		vui.validate();
//		vui.repaint();
	}

}
