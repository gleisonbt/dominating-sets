package ui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import main.MapFunction;
import main.Utils;

public class GraphUI extends JPanel{
	private Graph g;
	private final Map<String,VertexUI>vertices;
	private final List<Shape>edges;
	private final JPanel panel0,panel1;
	private int vs;
	public GraphUI() {
		super();
		this.edges = new ArrayList<Shape>();
		this.panel0 = new JPanel();
		this.panel1 = new JPanel();
		this.setLayout(null);
		panel0.setLayout(null);
		panel1.setLayout(null);
		this.add(panel0);
		this.add(panel1);
		this.panel0.setOpaque(true);
		this.panel1.setOpaque(true);
		this.vertices=new HashMap<String, VertexUI>();
	}
	
	@Override
	public void paint(final Graphics graphics) {
		super.paint(graphics);
		redraw();
		
	}
	private void redraw() {
		if(g==null){return;}
		this.panel0.setSize(this.getSize());
		this.panel1.setSize(this.getSize());
		
		final int x = (int)(this.getSize().width/2.3);
		final int y = (int)(this.getSize().height/2.3);
		final double telda = Math.PI / vs;
		final double para  = Math.sqrt(x*x+y*y)/2;
		final int d = (int) para/2;
		
		Iterator<VertexUI>it=this.vertices.values().iterator();
		for (int i = 0; it.hasNext(); ++i){
			final int xi = (int) (x + para * Math.cos(i * 2 * telda));
			final int yi = (int) (y + para * Math.sin(i * 2 * telda));
			final VertexUI v = it.next();
			v.setLocation(xi, yi);
			v.setSize(new Dimension(d,d));
			v.revalidate();
		}
		
		Utils.map(g.getEdges(), new MapFunction<Edge>() {
			@Override
			public void map(int index, Edge value) {
				final VertexUI v1 = GraphUI.this.vertices.get(value.getVertex1().getName());
				final VertexUI v2 = GraphUI.this.vertices.get(value.getVertex2().getName());
				double x1 = v1.getLocation().x+d/2;
				double y1 = v1.getLocation().y+d/2;
				double x2 = v2.getLocation().x+d/2;
				double y2 = v2.getLocation().y+d/2;
				((Graphics2D) panel1.getGraphics()).draw(new Line2D.Double(x1,y1,x2,y2));
			}
		});

	}
	
	void setVertexBackColor(Vertex vertex,Color color){
		this.vertices.get(vertex.getName()).setVertexBackColor(color);
	}

	public void setGraph(Graph g) {
		this.vertices.clear();
		this.panel0.removeAll();
		this.panel1.removeAll();
		Utils.map(g.getVerticies(), new MapFunction<Vertex>() {
			@Override
			public void map(final int index,final Vertex value) {
				final VertexUI vui = new VertexUI(value){
					@Override
					public void clicked() {
						vertexClicked(value);
						super.clicked();
					}
				};
				vertices.put(value.getName(),vui);
				panel0.add(vui);
			}
		});
		this.g=g;
		vs = g.getVerticies().length;
		redraw();
		revalidate();
		invalidate();
		validate();
		repaint();
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
