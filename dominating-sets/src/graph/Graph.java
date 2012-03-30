package graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.MapFunction;
import main.Utils;
/**
 * Graph data structure
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 */
public class Graph {
	public static final String NAME_RX     = "([a-z]{1,2}[0-9]?)";
	public static final String RELATION_RX = "[(]"+NAME_RX+"(?:(?:\\s*[,]\\s*\\n*\\r*)?)"+NAME_RX+"[)]";
	public static final String CONFIG_RX   = "[{]?("+RELATION_RX+"(?:(?:\\s*[,]?\\s*\\n*\\r*)?))+[}]?";
	private final Map<String,Edge>edges;
	private final Map<String,Vertex>verticies;
	private final char name;
	private final String configuration;
	/**
	 * instantiate new graph with its data structure
	 * @param name the name of the graph
	 * @param configuration graph structure e.g. {(v1,v2),(v2,v3),...,(vi,vj)}
	 */
	public Graph(char name,String configuration) {
		this.configuration=configuration;
		this.name=name;
		if(!configuration.matches(CONFIG_RX)){
			throw new IllegalArgumentException("Invalid configuration");
		}
		Pattern pattern = Pattern.compile(RELATION_RX);
		Matcher matcher = pattern.matcher(configuration);
		this.edges=new HashMap<String, Edge>();
		this.verticies=new HashMap<String, Vertex>();
		while(matcher.find()){
			final String en = "e"+edges.size();
			final String vi = matcher.group(1);
			final String vj = matcher.group(2);

			final Vertex v1 = verticies.containsKey(vi)? verticies.get(vi) : new Vertex(vi);
			final Vertex v2 = verticies.containsKey(vj)? verticies.get(vj) : new Vertex(vj);
			edges.put(en, new Edge(en, v1, v2));
			verticies.put(vi, v1);
			verticies.put(vj, v2);
		}
	}
	public char getName(){return name;}
	public boolean isConnected(){
		//pick any vertex
		Vertex vertex = this.verticies.values().iterator().next();
		//keep a set of the visited nodes so far
		final Set<Vertex>visited=new HashSet<Vertex>();
		//add the vertex to the visited set
		visited.add(vertex);
		while(visited.size()<this.verticies.size()){
			for(Vertex v:vertex.getNeighborVertecies()){
				visited.add(v);
			}
			vertex.setVisited(true);
			boolean found=false;
			for(Vertex v:visited){
				if(!v.isVisited()){
					vertex = v;
					found = true;
					break;
				}
			}
			if(!found){
				break;	// if all of the neighbor nodes are visited
						// and there is no more nodes to visit, just quit
			}
		}
		//check if the number of visited nodes
		//is the same as the number of all nodes in
		//the graph
		return visited.size()==this.verticies.size();
	}
	
	public Vertex getVertix(String name){
		return this.verticies.get(name);
	}
	public Edge getEdge(String name){
		return this.edges.get(name);
	}
	public Vertex[] getVerticies() {
		return verticies.values().toArray(new Vertex[verticies.size()]);
	}
	public Edge[] getEdges() {
		return edges.values().toArray(new Edge[edges.size()]);
	}
	public String getNeighborsPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		Utils.map(this.verticies.values(), new MapFunction<Vertex>() {
			public void map(int index, Vertex v) {
				sb.append("neighbors("+v+","+Arrays.toString(v.getNeighborVertecies()) + ").\n");
			};
		});
		return sb.toString();
	}
	public String getVerteciesPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		Utils.map(this.verticies.values(), new MapFunction<Vertex>() {
			public void map(int index, Vertex v) {
				sb.append("vertex("+v+").\n");
			};
		});
		return sb.toString();
	}
	public String getEdgesPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		Utils.map(this.edges.values(), new MapFunction<Edge>() {
			public void map(int index, Edge e) {
				sb.append("edge("+e.v1+","+e.v2+").\n");
			};
		});
		return sb.toString();
	}

	public String getConnectedPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		
		sb.append((isConnected()?"":"not_")+ "connected([");
		Utils.map(this.edges.values(), new MapFunction<Edge>() {
			public void map(int index, Edge e) {
				sb.append("("+e.v1+","+e.v2+"),");
			};
		});
		sb.setLength(sb.toString().length()-1);
		sb.append("]).");
		return sb.toString();
		//return "connected("+this.name+")"+(this.isConnected()?".":":-fail.");
	}
	public String getConfiguration() {
		return configuration;
	}
}
