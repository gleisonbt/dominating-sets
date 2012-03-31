package ui;

import graph.Vertex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public abstract class VertexUI extends JComponent implements MouseMotionListener,MouseListener{
	private Vertex vertex;
	private Color vertexBackColor = Color.white;
	public VertexUI(Vertex vertex){
		super();
		this.vertex=vertex;
		
		this.setOpaque(true);
		this.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public String getName() {
		return vertex.getName();
	}
	
	private Color color;
	@Override
	public void mouseClicked(MouseEvent e) {
		clicked();
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	private Point start;
	@Override
	public void mousePressed(MouseEvent e) {
		color = getVertexBackColor();
		setVertexBackColor(Color.orange);
		start = e.getPoint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		setVertexBackColor(color);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		Component c = e.getComponent();
		c.setLocation(c.getX()+(int)(p.getX() - start.getX()), c.getY()+(int)(p.getY() - start.getY()));
		c.repaint();
	}
	
		
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final int d = (int)Math.sqrt(Math.pow(getSize().width, 2)+Math.pow(getSize().height, 2))/2;
		final int x = (this.getSize().width-d)/2;
		final int y = (this.getSize().height-d)/2;
		final String name = this.vertex.getName();
		
		this.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,(int)((d/2))));
		
		FontMetrics fm = graphics.getFontMetrics(this.getFont());
		final double w = fm.charsWidth(name.toCharArray(), 0, name.length());
		final double h = fm.getHeight()/2;
		
		final int fx = (int)(this.getSize().width-w)/2;
		final int fy = (int)(this.getSize().height+h)/2;
		
		graphics.setColor(vertex.isDominant()?Color.GREEN:this.getVertexBackColor());
		graphics.fillOval(x, y, d, d);
		graphics.setColor(vertex.isDominant()?Color.BLUE:Color.black);
		graphics.drawOval(x, y, d, d);	
		graphics.drawString(name, fx, fy);

	}

	public void setVertexBackColor(Color vertexBackColor) {
		this.vertexBackColor = vertexBackColor;
	}

	private Color getVertexBackColor() {
		return this.vertexBackColor;
	}

	public Vertex getVertex() {
		return vertex;
	}
	
	public abstract void clicked();
}
