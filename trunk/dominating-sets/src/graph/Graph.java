package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * Graph data structure
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 */
public class Graph<E extends Locateable,V extends Locateable>{
	private final List<Edge<E,V>>edges;
	private final List<Vertex<E,V>>vertecies;
	private final char name;
	private final String configuration;
	private final EnumMap<GraphMetrics, Object>metrics;
	private double density = -1.0d;
	
	private double averageDegree = -1.0d;
	/**
	 * instantiate new graph with its data structure
	 * @param name the name of the graph
	 * @param configuration graph structure
	 */
	public Graph(char name,String configuration) {
		super();
		this.metrics=new EnumMap<GraphMetrics, Object>(GraphMetrics.class);
		metrics.put(GraphMetrics.MinDegree, Integer.MAX_VALUE);
		metrics.put(GraphMetrics.MaxDegree, Integer.MIN_VALUE);
		this.configuration=configuration;
		this.name=name;
		this.edges=Collections.synchronizedList(new ArrayList<Edge<E,V>>());
		this.vertecies=Collections.synchronizedList(new ArrayList<Vertex<E,V>>());
		final String []pairs = configuration.split("\\s+");
		for(final String pair:pairs){
			//System.out.printf("%s-",pair);
			final String en = "e"+edges.size();
			final String[]vs= pair.split("[,]");
			final String vi = vs[0];
			final String vj = vs[1];

			final Vertex<E,V> v1 = getVertex(vi,true);
			final Vertex<E,V> v2 = getVertex(vj,true);
			edges.add(new Edge<E,V>(en, v1, v2));
			if(!vertecies.contains(v1)) vertecies.add(v1);
			if(!vertecies.contains(v2)) vertecies.add(v2);
			
			metrics.put(GraphMetrics.MinDegree, Math.min(v1.degree(), (Integer)metrics.get(GraphMetrics.MinDegree)));
			metrics.put(GraphMetrics.MaxDegree, Math.max(v1.degree(), (Integer)metrics.get(GraphMetrics.MaxDegree)));
			metrics.put(GraphMetrics.MinDegree, Math.min(v2.degree(), (Integer)metrics.get(GraphMetrics.MinDegree)));
			metrics.put(GraphMetrics.MaxDegree, Math.max(v2.degree(), (Integer)metrics.get(GraphMetrics.MaxDegree)));
		}
		this.updateMetrics();
	}
	
	private Vertex<E,V> getVertex(String name,boolean createNewIfNotExists){
		for(Vertex<E,V> v:vertecies){
			if(name.equals(v.getName())){return v;}
		}
		return createNewIfNotExists?new Vertex<E,V>(name):null;
	}
	
	protected void updateMetrics(){
		final double V = vertecies.size();
		final double E = edges.size();
		averageDegree = 2*E/V;
		density = 2*E/(V*(V-1));
		metrics.put(GraphMetrics.Edges, E);
		metrics.put(GraphMetrics.Verticies, V);
		metrics.put(GraphMetrics.Density, density);
		metrics.put(GraphMetrics.AverageDegree, averageDegree);
		boolean connected = isConnected();
		metrics.put(GraphMetrics.isConnected, connected);
		metrics.put(GraphMetrics.Connectivity, E/V);
		metrics.put(GraphMetrics.Faces, 2+E-V);
		// Euler's formula shows that for planar graph G=(V,E), |E| = 3 |V| - 6, so every planar graph contains a 
		// linear number of edges, and further, every planar graph must contain a vertex of degree at most 5
		metrics.put(GraphMetrics.isPlanar, ((Integer)metrics.get(GraphMetrics.MaxDegree))<6 );
		
		// from graph theory, a tree has to satisfy the formula |e| = |v| - 1
		metrics.put(GraphMetrics.isTree, E==V-1); 
		
	}
	
	public char getName(){return name;}
	
	public boolean isPlanner(){
		//final Graph<E,V> u = new Graph<E,V>('o', getConfiguration());
		throw new UnsupportedOperationException();
		//return true;
	}
	
	/**
	 * removes a vertex and all of the incident edges on it
	 * @param v
	 */
	public int removeVertex(Vertex u){
		final Iterator<Vertex<E,V>>it=vertecies.iterator();
		int count=0; 
		while(it.hasNext()){
			final Vertex<E,V> v = it.next();
			if(v!=u){
				final Set<Edge<E,V>>re=v.removeNeighbor(u);
				if(!re.isEmpty()){
					System.out.println(Arrays.toString(re.toArray()));
					if(!this.edges.removeAll(re)){
						throw new RuntimeException(Arrays.toString(re.toArray()));
					}
					count++;
				}
			}
		}
		
		if(!this.vertecies.remove(u)){
			throw new RuntimeException(u + " can not be removed");
		}
		return count;
	}
	
	public boolean isConnected(){
		//if there is no vertices at all, the graph is empty and not connected
		if(this.vertecies.isEmpty()){
			return false;
		}
		//if there exist an orphan vertex, the graph is not connected
		if(((Integer)metrics.get(GraphMetrics.MinDegree))==0){
			return false;
		}
		//pick any vertex
		Vertex<E,V> vertex = this.vertecies.iterator().next();
		//keep a set of the visited nodes so far
		final Set<Vertex<E,V>>visited=new HashSet<Vertex<E,V>>();
		//add the vertex to the visited set
		visited.add(vertex);
		while(visited.size()<this.vertecies.size()){
			for(Vertex<E,V> v:vertex.getNeighborVertecies()){
				visited.add(v);
			}
			vertex.setVisited(true);
			boolean found=false;
			for(Vertex<E,V> v:visited){
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
	public Set<Vertex<E,V>>getVertecisNotLinkedToDominantVertex(){
		Set<Vertex<E,V>>set=new HashSet<Vertex<E,V>>();
		for(Vertex<E,V> v:this.vertecies){
			if(!(v.isDominant() || v.isLinkedToDominantVertex())){
				set.add(v);
			}
		}
		return set;
	}
	public boolean hasVertexNotLinkedToDominantVertex(){
		for(Vertex<E,V> v:this.vertecies){
			if(!(v.isDominant() || v.isLinkedToDominantVertex())){
				return true;
			}
		}
		return false;
	}

	public Vertex<E,V> getVertix(String name){
		return getVertex(name,false);
	}
	public Edge<E,V> getEdge(String name){
		for(Edge<E,V> e:edges){
			if(name.equals(e.getName())){return e;}
		}
		return null;
	}
	public List<Vertex<E,V>> getVertecies() {
		return vertecies;
	}
	public List<Edge<E,V>> getEdges() {
		return edges;
	}
	
	public String getConfiguration() {
		return configuration;
	}
	public Object getMetric(GraphMetrics metric){
		return this.metrics.get(metric);
	}
}
