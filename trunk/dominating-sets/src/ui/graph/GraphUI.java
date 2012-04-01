package ui.graph;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import ui.graph.layout.GraphLayout;
import ui.graph.layout.LayoutAdaptor;

public abstract class GraphUI extends JPanel{
	public abstract Class<? extends GraphLayout>getGraphLayout();
	private Graph g;
	private final Map<String,VertexUI>vertices;
	private final Map<String,EdgeUI>edges;
	private int vs;
	public GraphUI() {
		super();
		this.vertices=new HashMap<String, VertexUI>();
		this.edges=new HashMap<String, EdgeUI>();
		this.setLayout(null);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				super.componentResized(arg0);
				redraw(REDRAW_ZOOM);				
			}
		});
		//this.setTransferHandler(new TransferHandler("location"));
	}


	void redraw(){
		redraw(REDRAW_VERTICIES|REDRAW_EDGES);
	}

	private final static int REDRAW_VERTICIES = 0x0001;
	private final static int REDRAW_EDGES 	  = 0x0010;	
	private final static int REDRAW_ZOOM 	  = 0x0100;
	
	void redraw(int what){
		if(g==null){return;}

		if((what & REDRAW_VERTICIES) == REDRAW_VERTICIES){
			redrawVerticies();
		}
		if((what & REDRAW_EDGES) == REDRAW_EDGES){
			redrawEdges();
		}

		invalidate();
		revalidate();
		repaint();
	}
	
	private int x,y,vertexSize=30;
	
	private void redrawEdges(){
		
		for(final Edge edge:g.getEdges()){
			redrawEdge(edge);
		}
	}
	
	private void redrawEdge(Edge edge) {
		final VertexUI v1 = GraphUI.this.vertices.get(edge.getVertex1().getName());
		final VertexUI v2 = GraphUI.this.vertices.get(edge.getVertex2().getName());
		int x1 = v1.getLocation().x+vertexSize/2;
		int y1 = v1.getLocation().y+vertexSize/2;
		int x2 = v2.getLocation().x+vertexSize/2;
		int y2 = v2.getLocation().y+vertexSize/2;
		
		final EdgeUI e = this.edges.get(edge.getName());
		
		e.setBounds2(x1, y1, x2, y2);
	}

	private void redrawVerticies() {
		new LayoutAdaptor(g, getSize(), getVertices(), getGraphLayout());
	}

	public void setVertexBackColor(Vertex vertex,Color color){
		this.vertices.get(vertex.getName()).setVertexBackColor(color);
	}

	public void setGraph(Graph g) {
		this.vertices.clear();
		this.edges.clear();
		this.removeAll();
		for(final Vertex vertex:g.getVertecies()){
			final VertexUI vui = new VertexUI(vertex){
				
				@Override
				public void clicked() {
					vertexClicked(vertex);
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					super.mouseDragged(e);
				}

			};
			vui.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentMoved(ComponentEvent e) {
					super.componentMoved(e);
					final Edge[]edges=vui.getVertex().getEdges();
					for(final Edge edge:edges){
						redrawEdge(edge);
					}
				}
			});
			vertices.put(vertex.getName(),vui);
			vui.setSize(new Dimension(vertexSize,vertexSize));
			add(vui);
		}

		for(final Edge edge:g.getEdges()){
			final EdgeUI edgeUI = new EdgeUI(edge);
			this.edges.put(edge.getName(), edgeUI);
			add(edgeUI);
		}

		this.g=g;
		vs = g.getVertecies().length;
		redraw();
	}
	public Graph getGraph() {
		return g;
	}

	public VertexUI[] getVertices() {
		return vertices.values().toArray(new VertexUI[this.vertices.size()]);
	}
	
	public void vertexClicked(Vertex vertex){}

	public void resetAllVerticesBackColor() {
		for(VertexUI vui:this.vertices.values()){
			vui.setVertexBackColor(Color.white);
		}
	}

	public VertexUI getVertex(String vertexName) {
		return this.vertices.get(vertexName);
	}
}
