package ui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphInformation extends JPanel{
	private final JLabel label1,label2,label3;
	private final JTextField text1,text2,text3;
	public GraphInformation() {
		super();
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new GridLayout(0, 1, 5, 5));
		this.label1=new JLabel("Verticies:");
		this.text1=new JTextField();
		this.addField(label1,text1);
		
		this.label2=new JLabel("Edges:");
		this.text2=new JTextField();
		this.addField(label2,text2);
		
		this.label3=new JLabel("Connected:");
		this.text3=new JTextField();
		this.addField(label3,text3);
		
	}
	
	public void setVerteciesCount(String v){this.text1.setText(v);}
	public void setEdgesCount(String e){this.text2.setText(e);}
	public void setConnected(String c){this.text3.setText(c);}
	
	final void addField(JLabel label,JComponent field){
		this.add(label);
		this.add(field);
		if(field instanceof JTextField){
			((JTextField)field).setEditable(false);
		}
		field.setBackground(this.getBackground());
	}
}
