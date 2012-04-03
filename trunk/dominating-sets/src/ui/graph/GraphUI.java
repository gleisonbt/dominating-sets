package ui.graph;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import ui.graph.layout.GraphLayout;
import ui.graph.layout.LayoutAdaptor;

public abstract class GraphUI extends JPanel{
	public abstract Class<? extends GraphLayout>getGraphLayout();
	private Graph<EdgeUI,VertexUI> g;
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
		for(final Edge<EdgeUI,VertexUI> edge:g.getEdges()){
			redrawEdge(edge);
		}
	}
	
	private void redrawEdge(Edge<EdgeUI,VertexUI> edge) {
		final VertexUI v1 = edge.getVertex1().getViewableObject();
		final VertexUI v2 = edge.getVertex2().getViewableObject();
		int x1 = v1.getLocation().x+VERTEX_SIZE/2;
		int y1 = v1.getLocation().y+VERTEX_SIZE/2;
		int x2 = v2.getLocation().x+VERTEX_SIZE/2;
		int y2 = v2.getLocation().y+VERTEX_SIZE/2;
		
		edge.getViewableObject().setBounds2(x1, y1, x2, y2);
	}

	private void redrawVerticies() {
		new Thread(){
			public void run(){
				new LayoutAdaptor(g, getSize(), getGraphLayout());	
				System.out.println("finished");
			}
		}.start();
	}

	public void setVertexBackColor(Vertex<EdgeUI,VertexUI> vertex,Color color){
		vertex.getViewableObject().setVertexBackColor(color);
		//throw new UnsupportedOperationException();
	}

	public void setGraph(Graph<EdgeUI,VertexUI> g) {
		this.g=g;
		this.removeAll();
		this.invalidate();
		this.revalidate();
		this.validate();
		this.repaint();
		
		for(final Vertex<EdgeUI,VertexUI> vertex:g.getVertecies()){
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
					final List<Edge<EdgeUI,VertexUI>>edges=vui.getVertex().getEdges();
					for(final Edge<EdgeUI,VertexUI> edge:edges){
						redrawEdge(edge);
					}
				}
			});
			vui.setSize(new Dimension(VERTEX_SIZE,VERTEX_SIZE));
			vertex.setViewableObject(vui);
			add(vui);
		}

		for(final Edge<EdgeUI,VertexUI> edge:g.getEdges()){
			edge.setViewableObject(new EdgeUI());
			add(edge.getViewableObject());
		}

		
		redraw();
	}
	public Graph<EdgeUI,VertexUI> getGraph() {
		return g;
	}

	public void vertexClicked(Vertex<EdgeUI,VertexUI> vertex){}

	public void resetAllVerticesBackColor() {
		for(Vertex<EdgeUI,VertexUI> v:g.getVertecies()){
			v.getViewableObject().setVertexBackColor(Color.white);
		}
	}
	public VertexUI getVertexUI(String name){
		return g.getVertix(name).getViewableObject();
	}
	public void removeVertex(Vertex<EdgeUI,VertexUI> v){
		this.remove(v.getViewableObject());
		for(Edge<EdgeUI,VertexUI> e:v.getEdges()){
			this.remove(e.getViewableObject());
		}
		g.removeVertex(v);
	}
}
