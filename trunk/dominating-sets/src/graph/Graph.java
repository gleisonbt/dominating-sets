package graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.MapFunction;
import main.Utils;
/**
 * Graph data structure
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 */
public class Graph {
	private final Map<String,Edge>edges;
	private final Map<String,Vertex>vertecies;
	private final char name;
	private final String configuration;
	/**
	 * instantiate new graph with its data structure
	 * @param name the name of the graph
	 * @param configuration graph structure 
	 */
	public Graph(char name,String configuration) {
		this.configuration=configuration;
		this.name=name;
		this.edges=new HashMap<String, Edge>();
		this.vertecies=new HashMap<String, Vertex>();
		final String []pairs = configuration.split("\\s+");
		for(final String pair:pairs){
			//System.out.printf("%s-",pair);
			final String en = "e"+edges.size();
			final String[]vs= pair.split("[,]");
			final String vi = vs[0];
			final String vj = vs[1];

			final Vertex v1 = vertecies.containsKey(vi)? vertecies.get(vi) : new Vertex(vi);
			final Vertex v2 = vertecies.containsKey(vj)? vertecies.get(vj) : new Vertex(vj);
			edges.put(en, new Edge(en, v1, v2));
			vertecies.put(vi, v1);
			vertecies.put(vj, v2);
		}
	}
	public char getName(){return name;}
	public boolean isConnected(){
		//pick any vertex
		Vertex vertex = this.vertecies.values().iterator().next();
		//keep a set of the visited nodes so far
		final Set<Vertex>visited=new HashSet<Vertex>();
		//add the vertex to the visited set
		visited.add(vertex);
		while(visited.size()<this.vertecies.size()){
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
		return visited.size()==this.vertecies.size();
	}
	public boolean hasVertexNotLinkedToDominantVertex(){
		for(Vertex v:this.vertecies.values()){
			if(!(v.isDominant() || v.isLinkedToDominantVertex())){
				return true;
			}
		}
		return false;
	}
	
	public Vertex getVertix(String name){
		return this.vertecies.get(name);
	}
	public Edge getEdge(String name){
		return this.edges.get(name);
	}
	public Vertex[] getVertecies() {
		return vertecies.values().toArray(new Vertex[vertecies.size()]);
	}
	public Edge[] getEdges() {
		return edges.values().toArray(new Edge[edges.size()]);
	}
	public String getNeighborsPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		Utils.map(this.vertecies.values(), new MapFunction<Vertex>() {
			public void map(int index, Vertex v) {
				sb.append("neighbors("+v+","+Arrays.toString(v.getNeighborVertecies()) + ").\n");
			};
		});
		return sb.toString();
	}
	public String getVerteciesPrologSyntax(){
		final StringBuffer sb = new StringBuffer();
		Utils.map(this.vertecies.values(), new MapFunction<Vertex>() {
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
