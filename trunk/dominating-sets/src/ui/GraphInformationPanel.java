package ui;

import graph.Graph;
import graph.GraphMetrics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class GraphInformationPanel extends JPanel{
	Graph graph;
	public GraphInformationPanel() {
		super(new BorderLayout(5,5));
		setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		JTable table = new JTable(new AbstractTableModel(){
			@Override
			public int getColumnCount() {
				return 2;
			}
			@Override
			public int getRowCount() {
				return GraphMetrics.values().length;
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(columnIndex==0) return GraphMetrics.values()[rowIndex].name();
				if(graph==null){return null;}
				return graph.getMetric(GraphMetrics.values()[rowIndex]);
			}
		});
		table.setShowGrid(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(18);
		//JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//pane.setPreferredSize(new Dimension(0,300));
		JPanel tablepane = new JPanel(new BorderLayout());
		tablepane.add(table,BorderLayout.CENTER);
		tablepane.setBorder(BorderFactory.createTitledBorder("Graph properties"));
		add(tablepane,BorderLayout.CENTER);
		
	}
	public void setGraph(final Graph graph) {
		this.graph = graph;
		refresh();
	}
	public void refresh(){
		this.repaint();
		this.validate();
	}
	public Graph getGraph() {
		return graph;
	}
}
