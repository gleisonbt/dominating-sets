package ui;

import graph.Graph;
import graph.NaiveDominantSetFinder;
import graph.Vertex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.text.MyEventQueue;

public abstract class GraphViewer extends JFrame{
	private JPanel panel1,panel2;
	public abstract void onClosed();
	private final GraphUI graphUI;
	private final GraphInformation graphInfo;
	private final GraphConfigurationPanel graphConf;
	public GraphViewer() {
		super("Dominant set viewer");
		this.panel1=new JPanel();
		this.panel2=new JPanel();
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(new MyEventQueue()); 
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				onClosed();
			}
		});
		this.panel1.setLayout(new BorderLayout());
		this.panel2.setLayout(new BorderLayout());
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		
		this.graphUI = new GraphUI(){
			public void vertexClicked(Vertex vertex) {
				resetAllVerticesBackColor();
				Vertex[]neighbours = vertex.getNeighborVertecies();
				for(Vertex v:neighbours){
					graphUI.setVertexBackColor(v, Color.yellow);
				}	
			}
		};
		this.graphInfo = new GraphInformation();
		
		this.graphConf = new GraphConfigurationPanel() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				final String action = ((JButton)ae.getSource()).getText();
				if(action.equals(GraphConfigurationPanel.PROCESS_ACTION)){
					process();
				}else if(action.equals(GraphConfigurationPanel.FIND_DOMINANT_SET_ACTION)){
					findDominantSet();
				}
				super.actionPerformed(ae);
			}
			@Override
			public void process() {
				graphUI.setGraph(new Graph('u', getConfiguration()));
				graphInfo.setVerteciesCount(""+graphUI.getGraph().getVerticies().length);
				graphInfo.setEdgesCount(""+graphUI.getGraph().getEdges().length);
				graphInfo.setConnected(""+graphUI.getGraph().isConnected());
			}
			@Override
			public void fileOpened() {
				process();
			}
			@Override
			public void fileSaved() {
				
			}
		};
		
		panel3.add(graphInfo,BorderLayout.NORTH);
		this.panel2.add(panel3,BorderLayout.WEST);
		this.panel2.add(graphUI,BorderLayout.CENTER);
		this.panel1.add(graphConf,BorderLayout.CENTER);
		this.panel1.setPreferredSize(new Dimension(200,0));
		this.panel1.setMaximumSize(panel1.getPreferredSize());
		this.getContentPane().add(panel1,BorderLayout.WEST);
		this.getContentPane().add(panel2,BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(650,500));
		this.pack();
	}
	protected void findDominantSet() {
		if(graphConf.getSelectedSolver()==DominantSetSolverSelectionPanel.NAIVE){
			Graph g = graphUI.getGraph();
			NaiveDominantSetFinder ndsf = new NaiveDominantSetFinder(g);
			ndsf.findDominantSet();
//			Vertex[]dominantSet=ndsf.getDominantSet();
//			for(Vertex v:dominantSet){
//				g.getVertix(name)
//			}
		}
	}
	

}
