package ui;

import graph.Graph;
import graph.NaiveDominantSetFinder;
import graph.Vertex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			public void fileOpened(String configuration) {
				process();
				relocateVerticies(configuration);
			}
			@Override
			public void fileSaved(String fileName) {
				try{
					final StringBuffer sb = new StringBuffer();
					sb.append("\nCONFIGURATION\n");
					VertexUI[]verticies=graphUI.getVertices();
					if(verticies.length>0){
						for(final VertexUI vertex:verticies){
							sb.append(vertex.getName()+",x="+vertex.getX()+",y="+vertex.getY()+"\n");
						}
						sb.setLength(sb.length()-1);
					}
					final boolean append = true;
					final FileWriter fw = new FileWriter(fileName,append);
					fw.append(sb);
					fw.flush();
					fw.close();
				}catch(Throwable t){
					t.printStackTrace();
				}
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
		this.setPreferredSize(new Dimension(700,400));
		this.pack();
	}
	protected void relocateVerticies(String configuration) {
		System.out.println(configuration);
		final Pattern pattern = Pattern.compile("(?:([^\\,]+)[,]x[=]([-]?[0-9]+)[,]y[=]([-]?[0-9]+)(?:(\\r|\\n){0,2}))");
		final Matcher matcher = pattern.matcher(configuration);
		while(matcher.find()){
			System.out.println("Vertex : " + matcher.group(1));
			System.out.println("X : " + matcher.group(2));
			System.out.println("Y : " + matcher.group(3));
			final VertexUI vertex = this.graphUI.getVertex(matcher.group(1));
			final int x = Integer.valueOf(matcher.group(2));
			final int y = Integer.valueOf(matcher.group(3));
			final Point point = new Point(x,y);
			vertex.setLocation(point);
			
		}
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
