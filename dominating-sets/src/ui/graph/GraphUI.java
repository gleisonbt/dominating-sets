package ui.graph;


import graph.Edge;
import graph.Graph;
import graph.Locateable;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import ui.graph.layout.GraphLayout;
import ui.graph.layout.LayoutAdaptor;

public abstract class GraphUI extends JPanel{
	public abstract Class<? extends GraphLayout>getGraphLayout();
	private Graph g;
	private int VERTEX_SIZE=30;
	
	public GraphUI() {
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				super.componentResized(arg0);
				redraw(REDRAW_ZOOM);				
			}
		});
		//this.setTransferHandler(new TransferHandler("location"));
	}


	public void redraw(){
		
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
	
	
	
	private void redrawEdges(){
		this.revalidate();
		this.repaint();
		for(final Edge edge:g.getEdges()){
			redrawEdge(edge);
		}
	}
	
	private void redrawEdge(Edge edge) {
		final VertexUI v1 = (VertexUI)edge.getVertex1().getViewableObject();
		final VertexUI v2 = (VertexUI)edge.getVertex2().getViewableObject();
		int x1 = v1.getLocation().x+VERTEX_SIZE/2;
		int y1 = v1.getLocation().y+VERTEX_SIZE/2;
		int x2 = v2.getLocation().x+VERTEX_SIZE/2;
		int y2 = v2.getLocation().y+VERTEX_SIZE/2;
		
		((EdgeUI)edge.getViewableObject()).setBounds2(x1, y1, x2, y2);
	}

	private void redrawVerticies() {
		new Thread(){
			public void run(){
				new LayoutAdaptor(g, getSize(), getGraphLayout());	
				System.out.println("finished");
			}
		}.start();
	}

	public void setVertexBackColor(Vertex vertex,Color color){
		vertex.getViewableObject().setBackColor(color);
	}

	public void setGraph(Graph g) {
		this.g=g;
		this.removeAll();
		this.invalidate();
		this.revalidate();
		this.validate();
		this.repaint();
		
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
					final Edge[] edges=vui.getVertex().getEdges();
					for(final Edge edge:edges){
						redrawEdge(edge);
					}
				}
			});
			vui.setSize(new Dimension(VERTEX_SIZE,VERTEX_SIZE));
			vertex.setViewableObject(vui);
			add(vui);
		}

		for(final Edge edge:g.getEdges()){
			edge.setViewableObject(new EdgeUI());
			add((EdgeUI)edge.getViewableObject());
		}

		
		redraw();
	}
	public Graph getGraph() {
		return g;
	}

	public void vertexClicked(Vertex vertex){}

	public void resetAllVerticesBackColor() {
		for(Vertex v:g.getVertecies()){
			v.getViewableObject().setBackColor(Color.white);
		}
	}
	public VertexUI getVertexUI(String name){
		return (VertexUI)g.getVertix(name).getViewableObject();
	}
	
	public void removeVertex(Vertex v){
		this.remove((VertexUI)v.getViewableObject());
		for(Edge e:v.getEdges()){
			this.remove((EdgeUI)e.getViewableObject());
		}
		g.removeVertex(v);
	}
}
