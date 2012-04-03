package ui;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.graph.layout.CircularGraphLayout;
import ui.graph.layout.GraphLayout;
import ui.graph.layout.MazeGraphLayout;
import ui.graph.layout.RandomGridGraphLayout;
import ui.graph.layout.StarGraphLayout;

public abstract class GraphLayoutSelectionPanel extends JPanel {
	private final ButtonGroup group;
	public static Map<String,Class<? extends GraphLayout>> layouts = new TreeMap<String,Class<? extends GraphLayout>>();
	static{
		layouts.put("Random",	RandomGridGraphLayout.class);
		layouts.put("Circular",	CircularGraphLayout.class);
		//layouts.put("Planar",	PlanarGraphLayout.class);
		layouts.put("Maze",		MazeGraphLayout.class);
		layouts.put("Star",		StarGraphLayout.class);		
	}
	public GraphLayoutSelectionPanel() {
		super();
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		add(panel,BorderLayout.CENTER);
		group = new ButtonGroup();
		panel.setBorder(BorderFactory.createTitledBorder("Graph layout"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		for(final Entry<String,Class<? extends GraphLayout>> layout:layouts.entrySet()){
			final JRadioButton rb = new JRadioButton(layout.getKey());
			rb.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(rb.isSelected()){
						layoutChanged(layout.getValue());
					}
				}
			});
			group.add(rb);
			panel.add(rb);
		}
		group.getElements().nextElement().setSelected(true);
	}
	public void setFirst(){
		((JRadioButton)group.getElements().nextElement()).setSelected(true);
	}
	public abstract void layoutChanged(Class<? extends GraphLayout> layout);
	
}
