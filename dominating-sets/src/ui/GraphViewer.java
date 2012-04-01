package ui;

import graph.DominantSetFinder;
import graph.Graph;
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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.graph.GraphUI;
import ui.graph.VertexUI;
import ui.graph.layout.AbstractGraphLayout;
import ui.graph.layout.GraphLayout;
import ui.text.MyEventQueue;

public abstract class GraphViewer extends JFrame{
	private JPanel panel1,panel2;
	public abstract void onClosed();
	private Class<? extends GraphLayout>selectedGraphLayout=AbstractGraphLayout.class;
	private final GraphUI graphUI;
	private final GraphInformationPanel graphInfo;
	private final GraphConfigurationPanel graphConf;
	private final GraphLayoutSelectionPanel graphLayout;
	private final DominantSetSolverSelectionPanel solverPanel;
	private String savedConfiguraton;
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
		this.panel1.setLayout(new BorderLayout(2,2));
		this.panel2.setLayout(new BorderLayout(2,2));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		this.graphLayout = new GraphLayoutSelectionPanel() {
			@Override
			void layoutChanged(Class<? extends GraphLayout> layout) {
				selectedGraphLayout = layout;
			}
		};
		
		this.graphUI = new GraphUI(){
			public void vertexClicked(Vertex vertex) {
				resetAllVerticesBackColor();
				Vertex[]neighbours = vertex.getNeighborVertecies();
				for(Vertex v:neighbours){
					graphUI.setVertexBackColor(v, Color.yellow);
				}
				
			}
			public Class<? extends GraphLayout> getGraphLayout(){
				return selectedGraphLayout;
			}
		};
		this.graphInfo = new GraphInformationPanel();
		
		this.graphConf = new GraphConfigurationPanel() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				final String action = ((JButton)ae.getSource()).getText();
				if(action.equals(GraphConfigurationAction.Render_Graph.toString())){
					renderGraph();
				}else if(action.equals(GraphConfigurationAction.Saved_layout.toString())){
					relocateVerticies(savedConfiguraton);
				}
				super.actionPerformed(ae);
			}
			@Override
			public void renderGraph() {
				graphInfo.reset();
				graphUI.setGraph(new Graph('u', getConfiguration()));
				graphInfo.setInfo(GraphData.Verticies,graphUI.getGraph().getVertecies().length);
				graphInfo.setInfo(GraphData.Edges,graphUI.getGraph().getEdges().length);
				graphInfo.setInfo(GraphData.isConnected,graphUI.getGraph().isConnected());
			}
			@Override
			public void fileOpened(String configuration) {
				renderGraph();
				setSavedConfiguraton(configuration);
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
		solverPanel = new DominantSetSolverSelectionPanel() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				findDominantSet();
			}
		};
		panel3.add(graphInfo,BorderLayout.NORTH);
		panel3.add(solverPanel,BorderLayout.CENTER);
		this.panel2.add(panel3,BorderLayout.WEST);
		this.panel2.add(graphUI,BorderLayout.CENTER);
		this.panel1.add(graphConf,BorderLayout.CENTER);
		this.panel1.add(graphLayout,BorderLayout.NORTH);
		this.panel1.setPreferredSize(new Dimension(200,0));
		this.panel1.setMaximumSize(panel1.getPreferredSize());
		this.getContentPane().add(panel1,BorderLayout.WEST);
		this.getContentPane().add(panel2,BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(800,550));
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
		try{
			long t = new Date().getTime();
			final DominantSetFinder ndsf = solverPanel.getSelectedSolver().newInstance();
			ndsf.setGraph(graphUI.getGraph());
			ndsf.findDominantSet();
			t = new Date().getTime()-t;
			graphInfo.setInfo(GraphData.Dominant,ndsf.getDominationNumber());
			graphInfo.setInfo(GraphData.Iterations, ndsf.getIteratinos());
			graphInfo.setInfo(GraphData.SolveTime,t+"ms");
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	public void setSavedConfiguraton(String savedConfiguraton) {
		this.savedConfiguraton = savedConfiguraton;
	}

}
