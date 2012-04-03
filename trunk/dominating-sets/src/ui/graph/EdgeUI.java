package ui.graph;

import graph.Locateable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.JComponent;

public class EdgeUI extends JComponent implements Locateable{
	
	private EdgeDirection edgeDirection=EdgeDirection.NWSE;
	public EdgeUI() {
		super();
		setOpaque(true);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Graphics2D g = (Graphics2D)gr;
//		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
//		        new float[] { 3, 1 }, 2));
		g.setColor(new Color(0x9999FF));
		switch(edgeDirection){
		case NWSE:
			g.drawLine(0, 0, getSize().width, getSize().height);
			break;
		case NESW:
			g.drawLine(getSize().width, 0, 0, getSize().height);
			break;
		case SWNE:
			g.drawLine(0, getSize().height, getSize().width, 0);
			break;
		case SENW:
			g.drawLine(getSize().width, getSize().height, 0, 0);
			break;
		case NS:
			g.drawLine(getSize().width/2, getSize().height, getSize().width/2, 0);
			break;
		case WE:
			g.drawLine(getSize().width, getSize().height/2, 0, getSize().height/2);
			break;			
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public void setBounds2(int x1,int y1,int x2,int y2){
		final int x = min(x1,x2);
		final int y = min(y1,y2);
		int w = max(x1,x2) - x;
		int h = max(y1,y2) - y;
		
		     if(x1<x2 && y1<y2){edgeDirection=EdgeDirection.NWSE;}
		else if(x1<x2 && y1>y2){edgeDirection=EdgeDirection.SWNE;}
		else if(x1>x2 && y1<y2){edgeDirection=EdgeDirection.NESW;}
		else if(x1>x2 && y1>y2){edgeDirection=EdgeDirection.NWSE;}
		if(abs(x1-x2)<5){edgeDirection=EdgeDirection.NS;w=5;}
		if(abs(y1-y2)<5){edgeDirection=EdgeDirection.WE;h=5;}
		//else {throw new IllegalArgumentException();}
		     
		this.setBounds(x, y, w, h);		     
	}
	
	int abs(int x){return x>0?x:-x;}
	int min(int x, int y){return x<y?x:y;}
	int max(int x, int y){return x>y?x:y;}

	@Override
	public Double getLocationDouble() {
		return new Point2D.Double(getX(),getY());
	}

	@Override
	public void setLocation(Double point) {
		this.setLocation((int)point.x, (int)point.y);
	}
	
	
}
