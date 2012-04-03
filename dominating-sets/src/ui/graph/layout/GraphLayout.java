package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface GraphLayout{
	void computeLayout(Dimension plane);
	void setGraph(Graph graph);
	Graph getGraph();
	void setVertexLocation(Vertex vertex,Point2D.Double location);
	Point2D.Double next();
}
