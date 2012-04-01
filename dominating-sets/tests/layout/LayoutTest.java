package layout;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
public class LayoutTest extends JFrame {
	enum dir{
		L,U,R,D
	}
	public LayoutTest() {
		super();
		setSize(400,404);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int x=getSize().width/2;
		int y=getSize().height/2;
		dir d = dir.L;
		int i = 0;
		int Cx = 20;
		int Cy = 15;
		int cap = 0;
		int j=0;
		int n=0;
		dir[] directions = dir.values();
		do{
			
			switch(d){
			case L:
				x+=Cx;
				break;
			case U:
				y-=Cy;
				break;
			case R:
				x-=Cx;
				break;
			case D:
				y+=Cy;
				break;
			}
			g.drawString(""+i, x, y);
			
			if(++j>cap){
				cap++;
				j=0;
				d=directions[n++%4];
				System.out.println(d+":"+i);
			}
			
		}while(i++<100);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LayoutTest().setVisible(true);
	}

}
