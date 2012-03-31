package ui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.MapFunction;
import main.Utils;

public class GraphUI extends JPanel{
	private Graph g;
	private final Map<Vertex,VertexUI>vertices;
	private final Map<Edge,EdgeUI>edges;
	private int vs;
	public GraphUI() {
		super();
		this.vertices=new HashMap<Vertex, VertexUI>();
		this.edges=new HashMap<Edge, EdgeUI>();
		this.setLayout(null);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				super.componentResized(arg0);
				redraw();				
			}
		});
	}

	void redraw(){
		if(g==null){return;}

		final int x = (int)(getSize().width/2.3);
		final int y = (int)(getSize().height/2.3);
		final double telda = Math.PI / vs;
		final double para  = Math.sqrt(x*x+y*y)/2;
		final int d = (int) para/2;

		Iterator<VertexUI>it=vertices.values().iterator();
		for (int i = 0; it.hasNext(); ++i){
			final int xi = (int) (x + para * Math.cos(i * 2 * telda));
			final int yi = (int) (y + para * Math.sin(i * 2 * telda));
			final VertexUI v = it.next();
			v.setLocation(xi, yi);
			v.setSize(new Dimension(d,d));
			//v.invalidate();
			//v.revalidate();
		}

		for(final Edge edge:g.getEdges()){
			final VertexUI v1 = GraphUI.this.vertices.get(edge.getVertex1());
			final VertexUI v2 = GraphUI.this.vertices.get(edge.getVertex2());
			int x1 = v1.getLocation().x+d/2;
			int y1 = v1.getLocation().y+d/2;
			int x2 = v2.getLocation().x+d/2;
			int y2 = v2.getLocation().y+d/2;
			
			final EdgeUI e = this.edges.get(edge);
			
			e.setBounds2(x1, y1, x2, y2);
			//e.invalidate();
			//e.revalidate();
		}
		invalidate();
		revalidate();
		repaint();
	}
	

	
	void setVertexBackColor(Vertex vertex,Color color){
		this.vertices.get(vertex).setVertexBackColor(color);
	}

	public void setGraph(Graph g) {
		this.vertices.clear();
		this.edges.clear();
		this.removeAll();
		
		for(final Vertex vertex:g.getVerticies()){
			final VertexUI vui = new VertexUI(vertex){
				@Override
				public void clicked() {
					vertexClicked(vertex);
					super.clicked();
				}
			};
			vertices.put(vertex,vui);
			add(vui);
		}

		for(final Edge edge:g.getEdges()){
			final EdgeUI edgeUI = new EdgeUI(edge);
			this.edges.put(edge, edgeUI);
			add(edgeUI);
		}

		this.g=g;
		vs = g.getVerticies().length;
		redraw();
	}
	public Graph getGraph() {
		return g;
	}

	public void vertexClicked(Vertex vertex){}

	public void resetAllVerticesBackColor() {
		for(VertexUI vui:this.vertices.values()){
			vui.setVertexBackColor(Color.white);
		}
	}
}
