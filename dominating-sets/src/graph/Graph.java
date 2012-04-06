package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
public class Graph{
	private Set<Edge>edges;
	private Set<Vertex>vertecies;
	private final char name;
	private final String configuration;
	private final EnumMap<GraphMetrics, Object>metrics;
	private double density = -1.0d;
	
	private double averageDegree = -1.0d;
	private boolean[][] adjacencyMatrix = {};
	/**
	 * instantiate new graph with its data structure
	 * @param name the name of the graph
	 * @param configuration graph structure
	 */
	public Graph(char name,String configuration,boolean readMetrics) {
		super();
		this.metrics=new EnumMap<GraphMetrics, Object>(GraphMetrics.class);
		metrics.put(GraphMetrics.MinDegree, Integer.MAX_VALUE);
		metrics.put(GraphMetrics.MaxDegree, Integer.MIN_VALUE);
		this.configuration=configuration;
		this.name=name;
		this.edges=Collections.synchronizedSet(new HashSet<Edge >());
		this.vertecies=Collections.synchronizedSet(new HashSet<Vertex >());
		final String []pairs = configuration.split("\\s+");
		for(final String pair:pairs){
			//System.out.printf("%s-",pair);
			final String en = "e"+edges.size();
			final String[]vs= pair.split("[,]");
			final String vi = vs[0];
			final String vj = vs[1];

			final Vertex  v1 = getVertex(vi,true);
			final Vertex  v2 = getVertex(vj,true);
			final Edge e=new Edge (en, v1, v2);
			edges.add(e);edgeAdded(e);
			
			if(!vertecies.contains(v1)) {vertecies.add(v1);vertexAdded(v1);}
			if(!vertecies.contains(v2)) {vertecies.add(v2);vertexAdded(v2);}
			
			metrics.put(GraphMetrics.MinDegree, Math.min(v1.degree(), (Integer)metrics.get(GraphMetrics.MinDegree)));
			metrics.put(GraphMetrics.MaxDegree, Math.max(v1.degree(), (Integer)metrics.get(GraphMetrics.MaxDegree)));
			metrics.put(GraphMetrics.MinDegree, Math.min(v2.degree(), (Integer)metrics.get(GraphMetrics.MinDegree)));
			metrics.put(GraphMetrics.MaxDegree, Math.max(v2.degree(), (Integer)metrics.get(GraphMetrics.MaxDegree)));
		}
		if(readMetrics){
			updateMetrics();
		}
	}
	
	private Vertex  getVertex(String name,boolean createNewIfNotExists){
		for(Vertex  v:vertecies){
			if(name.equals(v.getName())){return v;}
		}
		return createNewIfNotExists?new Vertex (name):null;
	}
	
	public void vertexAdded(Vertex  vertex){}
	public void edgeAdded(Edge  edge){}
	
	public void updateMetrics(){
		final double V = vertecies.size();
		final double E = edges.size();
		averageDegree = 2*E/V;
		density = 2*E/(V*(V-1));
		metrics.put(GraphMetrics.Edges, (int)E);
		metrics.put(GraphMetrics.Verticies, (int)V);
		metrics.put(GraphMetrics.Density, density);
		metrics.put(GraphMetrics.AverageDegree, averageDegree);
		metrics.put(GraphMetrics.isConnected, isConnected(getAdjacencyMatrx()));
		metrics.put(GraphMetrics.Connectivity, E/V);
		metrics.put(GraphMetrics.Faces, (int)(2+E-V));
		
		//metrics.put(GraphMetrics.isPlanar, isPlanner() );
		
		// from graph theory, a tree has to satisfy the formula |e| = |v| - 1
		metrics.put(GraphMetrics.isTree, E==V-1); 
		
	}
	
	public char getName(){return name;}
	
//	public boolean isPlanner(){
//		// Euler's formula shows that for planar graph G=(V,E), |E| = 3 |V| - 6, so every planar graph contains a 
//		// linear number of edges, and further, every planar graph must contain a vertex of degree at most 5 after
//		// removing a vertex every time
//		//
//		// use the d-degenrate algorithm to detect k-5, k-3.3 subgraphs
//		// if a such subgraph exit, then the graph is not planar
//		// the node to be removed shall not render the graph into two segments
//		//
//		final Graph  G = new Graph ('o', getConfiguration(),false);
//		final List<Vertex> D = asList(G.getVertecies());
//		Iterator<Vertex >it;
//		
//		boolean done;
//		do{
//			done = true;
//			System.err.println(done);
//			for(it = D.iterator();it.hasNext();){
//				final Vertex  v = it.next();
//				if(v.degree()<6 && ! isCutVertex(v, D.toArray(new Vertex[D.size()])) ){
//					G.removeVertex(v);
//					done = false;
//					System.err.println(done);
//					break;
//				}
//			}
//		}while(!done);
//		return D.isEmpty();
//	}
	
	public static<T> List<T> asList(T[]t){
		return new ArrayList<T>(Arrays.asList(t));
	}
	
	public void setMetric(GraphMetrics gm,Object value){
		this.metrics.put(gm, value);
	}
	
	/**
	 * removes a vertex and all of the incident edges on it
	 * @param v
	 */
	public int removeVertex(final Vertex u){
		final Iterator<Vertex >it=vertecies.iterator();
		int count=0; 
		while(it.hasNext()){
			final Vertex  v = it.next();
			if(v!=u){
				final Set<Edge >re=v.removeNeighbor(u);
				if(!re.isEmpty()){
					System.out.println(Arrays.toString(re.toArray()));
					if(!this.edges.removeAll(re)){
						throw new RuntimeException(Arrays.toString(re.toArray()));
					}
					count++;
				}
			}
		}
		
		if(this.vertecies.contains(u) && !this.vertecies.remove(u)){
			throw new RuntimeException(u + " can not be removed");
		}
		
		return count;
	}
	
	/**
	 * a cut/critical vertex is a vertex when removed can leave the graph disconnected
	 * @return
	 */
	public boolean isCutVertex(final Vertex vertex,final Vertex[]all){
		final List<Vertex>_all=asList(all);
		
		_all.remove(vertex);
		for(Vertex u:_all){
			if(vertex.isNeighbor(u)){
				u.removeNeighbor(vertex);
			}
		}
		boolean result = !isConnected(buildAdjacencyMatrix(_all));
		System.out.println(vertex+"|"+result);
		return false;
	}
	
	public boolean[][] getAdjacencyMatrx(){
		if(adjacencyMatrix.length==0){
			adjacencyMatrix = buildAdjacencyMatrix(new ArrayList<Vertex>(this.vertecies));
		}
		return adjacencyMatrix;
	}
	
	
	private boolean[][] buildAdjacencyMatrix(final List<Vertex>nodes) {
		final int V = nodes.size();
		boolean[][]am=new boolean[V][V];
		for(int i=0;i<V;i++){
			for(int j=0;j<V;j++){
				am[j][i] = am[i][j] = nodes.get(i).isNeighbor(nodes.get(j));
			}
		}
		return am;
	}

	public boolean isConnected(boolean[][]matrix){
		boolean[][]am=new boolean[matrix.length][matrix.length];
		System.arraycopy(matrix, 0, am, 0, matrix.length);
		int connected = 0;
		int i=0;
		boolean done;
		do{
			done = true;
			for(int j=0;j<am.length;j++){
				if(am[i][j]){
					am[i][j] = am[j][i] = false;
					connected++;
					i=j;
					done=false;
					break;
				}
			}
		}while(!done);
		return connected>=am.length;
	}
	
//	public boolean isConnected(final List<Vertex>all){
//		if(all.isEmpty()) return true;
//		
//		for(Vertex v:all){v.setVisited(false);}
//		//pick any vertex
//		Vertex  vertex = all.iterator().next();
//		//keep a set of the visited nodes so far
//		final Set<Vertex >visited=new HashSet<Vertex >();
//		//add the vertex to the visited set
//		visited.add(vertex);
//		while(visited.size()<all.size()){
//			for(Vertex  v:vertex.getNeighborVertecies()){
//				visited.add(v);
//			}
//			vertex.setVisited(true);
//			boolean found=false;
//			for(final Vertex  v:visited){
//				if(!v.isVisited()){
//					vertex = v;
//					found = true;
//					break;
//				}
//			}
//			if(!found){
//				break;	// if all of the neighbor nodes are visited
//						// and there is no more nodes to visit, just quit
//			}
//		}
//		//check if the number of visited nodes
//		//is the same as the number of all nodes in
//		//the graph
//		return visited.size()==all.size();
//	}
	
	public Set<Vertex >getVertecisNotLinkedToDominantVertex(){
		Set<Vertex >set=new HashSet<Vertex >();
		for(Vertex  v:this.vertecies){
			if(!(v.isDominant() || v.isLinkedToDominantVertex())){
				set.add(v);
			}
		}
		return set;
	}
	public boolean hasVertexNotLinkedToDominantVertex(){
		for(Vertex  v:this.vertecies){
			if(!(v.isDominant() || v.isLinkedToDominantVertex())){
				return true;
			}
		}
		return false;
	}

	public Vertex  getVertix(String name){
		return getVertex(name,false);
	}
	public Edge  getEdge(String name){
		for(Edge  e:edges){
			if(name.equals(e.getName())){return e;}
		}
		return null;
	}
	public Vertex[] getVertecies() {
		return vertecies.toArray(new Vertex[vertecies.size()]);
	}
	public Edge[] getEdges() {
		return edges.toArray(new Edge[edges.size()]);
	}
	
	public String getConfiguration() {
		return configuration;
	}
	public Object getMetric(GraphMetrics metric){
		return this.metrics.get(metric);
	}
	public boolean containsEdge(Edge e) {
		return this.edges.contains(e);
	}
	public boolean containsVertex(Vertex v) {
		return this.vertecies.contains(v);
	}
	public Edge findEdge(Vertex v, Vertex u) {
		for(Iterator<Edge>it=this.edges.iterator();it.hasNext();){
			Edge e=it.next();
			if((e.getVertex1()==v && e.getVertex2()==u) || (e.getVertex1()==u && e.getVertex2()==v)){
				return e;
			}
		}
		return null;//TODO
	}
	public Collection<Edge> findEdgeSet(Vertex v, Vertex u) {
		Collection<Edge>es=new HashSet<Edge>();
		for(Iterator<Edge>it=this.edges.iterator();it.hasNext();){
			Edge e=it.next();
			if((e.getVertex1()==v && e.getVertex2()==u) || (e.getVertex1()==u && e.getVertex2()==v)){
				es.add(e);
			}
		}
		return es;
	}
}
