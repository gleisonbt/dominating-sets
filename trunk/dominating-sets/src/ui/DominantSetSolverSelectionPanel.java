package ui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import solver.DominantSetSolver;
import solver.DominantSetSolverType;

public abstract class DominantSetSolverSelectionPanel extends JPanel implements ActionListener{
	
	public DominantSetSolverSelectionPanel() {
		super();
		this.setLayout(new BorderLayout());
		JPanel optionsPane = new JPanel(new GridLayout(0, 1,2,2));
		optionsPane.setBorder(BorderFactory.createTitledBorder("Dominant set solver"));
		
		ButtonGroup group = new ButtonGroup();
		for(final DominantSetSolverType dominantSetFinderType:DominantSetSolverType.values()){
			final JRadioButton rbutton = new JRadioButton(dominantSetFinderType.name());
			group.add(rbutton);
			rbutton.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					if(rbutton.isSelected()){
						selectedSolver=dominantSetFinderType.solver();
					}
				}
			});
			optionsPane.add(rbutton);
		}
		
		group.getElements().nextElement().setSelected(true);
		
		final JPanel p = new JPanel(new BorderLayout());
		final JButton b = new JButton("Solve");
		b.addActionListener(this);
		p.add(b,BorderLayout.SOUTH);
		
		final JCheckBox c = new JCheckBox("Shuffle before solve");
		c.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ae) {
				shuffleBeforeSolve=c.isSelected();
			}
		});
		p.add(c,BorderLayout.NORTH);
		add(p,BorderLayout.SOUTH);
		add(optionsPane,BorderLayout.NORTH);

	}
	private Class<? extends DominantSetSolver> selectedSolver;
	private boolean shuffleBeforeSolve;
	public Class<? extends DominantSetSolver> getSelectedSolver() {
		return selectedSolver;
	}
	public boolean isShuffleBeforeSolve(){
		return shuffleBeforeSolve;
	}
}
