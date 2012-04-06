package ui.graph.layout;

import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import graph.Edge;
import graph.JungGraph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.geom.Point2D.Double;

public class FRGraphLayout extends AbstractGraphLayout {

	@Override
	public void computeLayout(Dimension plane) {
		final edu.uci.ics.jung.graph.Graph<Vertex, Edge> g = new JungGraph<Vertex, Edge>(getGraph()) ;
		final FRLayout2<Vertex, Edge> layout = new FRLayout2<Vertex, Edge>(g);
		layout.setSize(new Dimension(plane.width-30,plane.height-30));
		layout.initialize();
		layout.reset();
		for(Vertex v:getGraph().getVertecies()){
			this.setVertexLocation(v, new Double(layout.getX(v),layout.getY(v)) );
		}
	}

}
