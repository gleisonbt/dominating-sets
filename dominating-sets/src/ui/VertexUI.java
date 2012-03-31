package ui;

import graph.Vertex;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class VertexUI extends JComponent{
	private Vertex vertex;
	private Color vertexBackColor = Color.white;
	public VertexUI(Vertex vertex){
		super();
		this.vertex=vertex;
		this.setOpaque(true);
		this.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
		this.addMouseListener(new MouseAdapter() {
			private Color color;
			@Override
			public void mouseClicked(MouseEvent e) {
				clicked();
				super.mouseClicked(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				color = getVertexBackColor();
				setVertexBackColor(Color.orange);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				setVertexBackColor(color);
			}
		});
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
		
		graphics.setColor(vertex.isDominant()?Color.green:this.getVertexBackColor());
		graphics.fillOval(x, y, d, d);
		graphics.setColor(vertex.isDominant()?Color.white:Color.black);
		graphics.drawOval(x, y, d, d);	
		graphics.drawString(name, fx, fy);

	}

	public void setVertexBackColor(Color vertexBackColor) {
		this.vertexBackColor = vertexBackColor;
	}

	private Color getVertexBackColor() {
		return this.vertexBackColor;
	}

	public void clicked(){}
}
