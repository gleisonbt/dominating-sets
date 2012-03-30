package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class DominantSetSolverSelectionPanel extends JPanel {
	public static final String NAIVE = "Naive solver";
	private String solver = NAIVE;
	public DominantSetSolverSelectionPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEtchedBorder());
		String[] options = {NAIVE};
		boolean first=true;
		for(final String s:options){
			final JRadioButton rbutton = new JRadioButton(s);
			rbutton.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					if(rbutton.isSelected()){
						setSolver(s);
					}
				}
			});
			rbutton.setSelected(first);
			first=false;
			add(rbutton);
		}
		
	}
	public abstract void changed(String s);
	protected void setSolver(String s) {
		solver=s;
		changed(s);
	}
	public String getSelectedSolver(){
		return this.solver;
	}
}
