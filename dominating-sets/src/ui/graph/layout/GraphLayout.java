package ui.graph.layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

import ui.graph.VertexUI;

public interface GraphLayout {
	Point2D.Double getVertexLocation(Vertex vertex);
	void computeLayout(Dimension plane);
	Vertex[]getVertecies();
	void setGraph(Graph graph);
	Graph getGraph();
	void setVertexLocation(Vertex vertex,Point2D.Double location);
	Point2D.Double next();
	void setVertexUIs(VertexUI[] vertecies);
}
