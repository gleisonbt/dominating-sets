package ui.graph.layout;

import graph.Graph;

import java.awt.Dimension;

import ui.graph.VertexUI;

public class LayoutAdaptor {
	public LayoutAdaptor(Graph graph,Dimension size,VertexUI[]vertecies,Class<? extends GraphLayout> graphLayout) {
		
		try {
			GraphLayout layout = graphLayout.newInstance();
			layout.setGraph(graph);
			layout.computeLayout(size);
			for(VertexUI vui:vertecies){
				vui.setLocation(layout.getVertexLocation(vui.getVertex()));
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
