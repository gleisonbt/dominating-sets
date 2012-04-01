package ui;

import java.awt.GridLayout;
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
		fields = new HashMap<GraphData, JTextField>();
		for(GraphData gd:GraphData.values()){
			addField(gd,new JLabel(),new JTextField());
		}
	}
	
	public void reset(){
		for(JTextField field:fields.values()){field.setText("");}
	}
	
	public void setInfo(GraphData gd, Object value){
		fields.get(gd).setText(""+value);
	}
	
	public String getInfo(GraphData gd){return fields.get(gd).getText();}
	
	private Map<GraphData,JTextField>fields;
	final void addField(GraphData gd,JLabel label,JTextField field){
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
