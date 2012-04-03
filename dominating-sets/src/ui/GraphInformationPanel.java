package ui;

import graph.Graph;
import graph.GraphMetrics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.EnumMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;

public class GraphInformationPanel extends JPanel{
	EnumMap<GraphMetrics,Object> data;
	public GraphInformationPanel() {
		super(new BorderLayout(5,5));
		//this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		data = new EnumMap<GraphMetrics,Object>(GraphMetrics.class);
		for(final GraphMetrics gm:GraphMetrics.values()){
			setInfo(gm, null);
		}
		JTable table = new JTable(new AbstractTableModel(){
			@Override
			public String getColumnName(int c) {
				return new String[]{"Property","Value"}[c];
			}
			@Override
			public int getColumnCount() {
				return 2;
			}
			@Override
			public int getRowCount() {
				return data.size();
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(columnIndex==0) return data.keySet().toArray()[rowIndex];
				return data.values().toArray()[rowIndex];
			}
		});
		table.setShowGrid(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.setRowHeight(18);
		//JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//pane.setPreferredSize(new Dimension(0,300));
		JPanel tablepane = new JPanel(new BorderLayout());
		tablepane.add(table,BorderLayout.CENTER);
		tablepane.setBorder(BorderFactory.createTitledBorder("Graph properties"));
		add(tablepane,BorderLayout.CENTER);
		
	}
	
	public void setInfo(final Graph g){
		for(final GraphMetrics gm:GraphMetrics.values()){
			setInfo(gm, g.getMetric(gm));
		}
	}
	public void setInfo(GraphMetrics gm,Object value){
		data.put(gm, value);
		invalidate();
		validate();
		revalidate();
		repaint();
	}
	public void reset(){
		for(GraphMetrics gm:data.keySet()){data.put(gm, null);}
	}
}
