package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;

public interface GraphLayout {
	Point getVertexLocation(Vertex vertex);
	void computeLayout(Dimension plane);
	Vertex[]getVertecies();
	void setGraph(Graph graph);
	Graph getGraph();
	void setVertexLocation(Vertex vertex,Point location);
	Point next();
}
