package ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class GraphConfigurationPanel extends JPanel implements ActionListener{

	
	public abstract void fileOpened(String positions);
	public abstract void fileSaved(String fileName);
	public abstract void renderGraph();
	final JPanel panel0,panel1;
	final JTextArea field;
	final JButton button1,button3,button4,button5;

	public GraphConfigurationPanel() {
		super(new BorderLayout());
		this.panel0=new JPanel();
		this.panel1=new JPanel();

		this.panel0.setLayout(new BorderLayout());
		
		this.panel1.setLayout(new FlowLayout(FlowLayout.LEFT,3,5));
		this.add(panel0,BorderLayout.CENTER);
		
		
		field = new JTextArea();
		field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		field.setLineWrap(true);
		field.setFont(getFont());
		button1 = new JButton(GraphConfigurationAction.Render_Graph.toString());
		button3 = new JButton(GraphConfigurationAction.Open.toString());
		button4 = new JButton(GraphConfigurationAction.Save.toString());
		button5 = new JButton(GraphConfigurationAction.Saved_layout.toString());
		button1.addActionListener(this);
		
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		final JScrollPane spane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spane.setPreferredSize(new Dimension(0,100));
		spane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BorderLayout());
		
		JPanel spaneContainer = new JPanel();
		spaneContainer.setLayout(new BorderLayout());
		spaneContainer.add(spane,BorderLayout.CENTER);
		panel4.add(spaneContainer,BorderLayout.CENTER);
		spane.setBorder(BorderFactory.createEmptyBorder());
		spaneContainer.setBorder(BorderFactory.createTitledBorder("Enter graph configuration:"));
		
		
		this.panel1.add(button3);
		this.panel1.add(button4);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		
		panel3.add(button1,BorderLayout.NORTH);
		panel3.add(button5,BorderLayout.SOUTH);
		
		
		this.panel0.add(panel3,BorderLayout.NORTH);
		this.panel0.add(panel4,BorderLayout.CENTER);
		panel4.add(panel1,BorderLayout.NORTH);
		
		
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
