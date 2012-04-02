package layout;

import graph.Graph;
import graph.Vertex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

public class Planar extends JFrame{
	class Intersection extends Point2D.Double{
		public boolean v = false;
		public Intersection(double x,double y){
			super(x,y);
		}
		public Intersection(Line a, Line b){
			super();
			//a1x + b1y = d1
			//a2x + b2y = d2
			
			double a1 = a.y2 - a.y1;
			double a2 = b.y2 - b.y1;
			
			double b1 = a.x1 - a.x2;
			double b2 = b.x1 - b.x2;
			
			double d1 = (a.x2*a.y1)+(a.x1*a.y2);
			double d2 = (b.x2*b.y1)+(b.x1*b.y2);
			
			this.x = (b2*d1 - b1*d2)/(a1*b2 - a2*b1);
			this.y = (a1*d2 - a2*d1)/(a1*b2 - a2*b1);
		}
	}
	class Line extends Line2D.Double{
		public Map<Line,Point2D.Double>intersection=Collections.synchronizedMap(new HashMap<Line,Point2D.Double>(5));
		public Line(double x, double y, double w, double h) {
			super(x, y, w, h);
		}	
	}

	private Line[]lines;
	private Map<Intersection,Set<Line>>intersections;
	private Graph graph;
	public Planar() {
		super();
		intersections = new HashMap<Intersection,Set<Line>>();
		setSize(new Dimension(700,500));
		setPreferredSize(getSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		lines = new Line[1000];
		Random r = new Random();
		for(int i=0;i<lines.length;i++){
			int x1 = Math.abs(r.nextInt(getWidth()));
			int x2 = Math.abs(r.nextInt(getWidth()));
			lines[i] = new Line(x1,0,x2,getHeight());
		}
		graph = new Graph('l', "1,2 1,5 1,3 1,4 2,5 2,4 2,3 3,5 4,5");
		pack();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int y2 = getHeight();
		for(Line a:lines){
			g.setColor(Color.WHITE);
			((Graphics2D)g).draw(a);
			for(Line b:lines){
				if(a.intersectsLine(b)){
					Intersection p = new Intersection(a, b);
					a.intersection.put(b,p);
					b.intersection.put(a,p);
					if(!intersections.containsKey(p)){
						intersections.put(p, new HashSet<Line>(2));
					}
					intersections.get(p).add(a);
					intersections.get(p).add(b);
				}
			}
		}
		boolean done=false;
		Vertex v = graph.getVertecies()[0];
		
			Point2D.Double p=null;
			for(Intersection i:intersections.keySet()){
				if(i.v)continue;
				if(intersections.get(i).size()==v.degree()){
					p = i;
					int x = (int) p.x;
					int y = (int) p.y;
					if(x<10 || y<10 || x>getWidth()-10 || y>getHeight()-10)continue;
					g.setColor(Color.red);
					g.fillOval(x-5, y-5, 10, 10);	
					i.v=true;
					System.out.println(v+"-"+i);
					break;
				}
			}
			if(p==null)return;
			
		//}while(!done);
		


	}

	public static void main(String[]a){
		new Planar().setVisible(true);
	}
}
