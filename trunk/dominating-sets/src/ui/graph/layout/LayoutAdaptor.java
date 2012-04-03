package ui.graph.layout;

import graph.Graph;

import java.awt.Dimension;

public class LayoutAdaptor {
	public LayoutAdaptor(Graph graph,Dimension size,Class<? extends GraphLayout> graphLayout) {
		
		try {
			final GraphLayout layout = graphLayout.newInstance();
			layout.setGraph(graph);
			layout.computeLayout(size);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
