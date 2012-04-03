package graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public interface Locateable {
	Point2D.Double getLocationDouble();
	void setLocation(Point point);
	void setLocation(Point2D.Double point);
	int getX();
	int getY();
	void setBackColor(Color color);
}
