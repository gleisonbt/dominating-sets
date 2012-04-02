package ui;

import graph.Graph;
import graph.GraphMetrics;

import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphInformationPanel extends JPanel{
	
	
	public GraphInformationPanel() {
		super();
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setLayout(new GridLayout(0, 2, 1, 1));
		fields = new HashMap<GraphMetrics, JTextField>();
		for(GraphMetrics gd:GraphMetrics.values()){
			addField(gd,new JLabel(),new JTextField());
		}
	}
	
	public void setInfo(final Graph g){
		for(final GraphMetrics gm:GraphMetrics.values()){
			setInfo(gm, g.getMetric(gm));
		}
	}
	
	public void reset(){
		for(JTextField field:fields.values()){field.setText("");}
	}
	
	public void setInfo(GraphMetrics gd, Object value){
		JTextField txt = fields.get(gd);
		if(value==null){
			txt.setText("");
		}else{
			final Class<?>type=gd.type();
			
			if(type==Integer.class || type==Double.class || type==Long.class){
				try{
					txt.setText(NumberFormat.getNumberInstance().format(value));
				}catch(Exception r){
					System.out.println(type.getName());
					System.out.println(value.getClass().getName());
					System.exit(0);
				}					
			}else if(type==Boolean.class){
				txt.setText(((Boolean)value)?"yes":"no");
			}else{
				txt.setText(value+"");
			}
		}
		
	}
	
	public String getInfo(GraphMetrics gd){return fields.get(gd).getText();}
	
	private Map<GraphMetrics,JTextField>fields;
	final void addField(GraphMetrics gd,JLabel label,JTextField field){
		add(label);
		label.setText(gd.name());
		add(field);
		if(field instanceof JTextField){
			((JTextField)field).setEditable(false);
		}
		fields.put(gd, field);
		field.setBackground(getBackground());
	}
}
