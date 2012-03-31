package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class GraphConfigurationPanel extends JPanel implements ActionListener{
	public static final String PROCESS_ACTION = "Process";
	public static final String FIND_DOMINANT_SET_ACTION = "Find dominant set";
	public static final String SAVE_ACTION = "Save";
	public static final String OPEN_ACTION = "Open";
	public static final String ROTATE_ACTION = "Rotate";
	public abstract void fileOpened(String positions);
	public abstract void fileSaved(String fileName);
	public abstract void process();
	final JPanel panel0,panel1,panel2;
	final JLabel label;
	final JTextArea field;
	final JButton button1,button2,button3,button4,button5;
	private String selectedSolver;
	public GraphConfigurationPanel() {
		super();
		this.panel0=new JPanel();
		this.panel1=new JPanel();
		this.panel2=new DominantSetSolverSelectionPanel(){
			@Override
			public void changed(String s) {
				setSelectedSolver(s);
			}
		};
		this.setLayout(new BorderLayout(5,5));
		this.panel0.setLayout(new BorderLayout(10,10));
		
		this.panel1.setLayout(new FlowLayout(FlowLayout.LEFT,3,5));
		this.add(panel0,BorderLayout.NORTH);
		this.add(panel1,BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		label = new JLabel("Enter graph configuration:");
		field = new JTextArea();
		field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		field.setLineWrap(true);
		field.setFont(label.getFont());
		button1 = new JButton(PROCESS_ACTION);
		button2 = new JButton(FIND_DOMINANT_SET_ACTION);
		button3 = new JButton(OPEN_ACTION);
		button4 = new JButton(SAVE_ACTION);
		button5 = new JButton(ROTATE_ACTION);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		final JScrollPane spane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spane.setPreferredSize(new Dimension(0,100));
		this.panel0.add(label,BorderLayout.NORTH);
		this.panel0.add(spane,BorderLayout.CENTER);
		this.panel0.add(panel2,BorderLayout.SOUTH);
		this.panel1.add(button3);
		this.panel1.add(button4);
		
		this.panel1.add(button1);
		//this.panel1.add(button5);
		this.panel1.add(button2);
		
		button5.setEnabled(false);
		
	}
	public String getSelectedSolver() {
		return selectedSolver;
	}
	private void setSelectedSolver(String solver) {
		this.selectedSolver = solver;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button3){
			openFile();
		}else if(e.getSource()==button4){
			saveFile();
		}
	}
	
	void openFile(){
		final JFileChooser fc = new JFileChooser(new File("./data/"));
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			try{
				field.setText("");
				final String fileName = fc.getSelectedFile().getAbsolutePath();
				final FileReader fr = new FileReader(fileName);
				final StringBuffer sb = new StringBuffer();
				for(int i;(i=fr.read())>-1;sb.append((char)i)){}
				String[]contents=sb.toString().split("(\\n|\\r)+CONFIGURATION(\\n|\\r)+");
				field.setText(contents[0]);
				fr.close();
				fileOpened(contents[1]);
			}catch(Throwable t){
				t.printStackTrace();
			}
		}
		
	}
	void saveFile(){
		final JFileChooser fc = new JFileChooser(new File("./data/"));
		if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
			try{
				final String fileName = fc.getSelectedFile().getAbsolutePath();
				final FileWriter fw = new FileWriter(fileName);
				fw.write(field.getText());
				fw.flush();
				fw.close();
				fileSaved(fileName);
			}catch(Throwable t){
				t.printStackTrace();
			}
		}
	}
	
	public String getConfiguration(){
		return this.field.getText();
	}
}
