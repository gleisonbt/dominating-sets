package ui.graph.layout;

import graph.Graph;

import java.awt.Dimension;

import ui.graph.VertexUI;

public class LayoutAdaptor {
	public LayoutAdaptor(Graph graph,Dimension size,VertexUI[]vertecies,Class<? extends GraphLayout> graphLayout) {
		
		try {
			final GraphLayout layout = graphLayout.newInstance();
			layout.setGraph(graph);
			layout.setVertexUIs(vertecies);
			layout.computeLayout(size);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
