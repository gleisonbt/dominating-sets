package ui;

import graph.DominantSetFinderType;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class DominantSetSolverSelectionPanel extends JPanel {

	public DominantSetSolverSelectionPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEtchedBorder());
		
		ButtonGroup group = new ButtonGroup();
		for(final DominantSetFinderType dominantSetFinderType:DominantSetFinderType.values()){
			final JRadioButton rbutton = new JRadioButton(dominantSetFinderType.name());
			group.add(rbutton);
			rbutton.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					if(rbutton.isSelected()){
						dominantSetFinderChanged(dominantSetFinderType);
					}
				}
			});
			add(rbutton);
		}
		group.getElements().nextElement().setSelected(true);
	}
	public abstract void dominantSetFinderChanged(DominantSetFinderType dominantSetFinderType);
}
