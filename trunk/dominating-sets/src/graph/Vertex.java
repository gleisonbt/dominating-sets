package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex<E extends Locateable,V extends Locateable> extends AbstractViewable<V>{
	private final List<Vertex<E,V>>neighborVertices;
	private final String name;
	private final List<Edge<E,V>>edges;
	private boolean visited;
	private boolean dominant;
	private double connectness;
	private int neighborPointer=0;
	private Vertex<E,V>[]neighbors;
	
	public Vertex(String name){
		this.neighborVertices=Collections.synchronizedList(new ArrayList<Vertex<E,V>>());
		this.edges=Collections.synchronizedList(new ArrayList<Edge<E,V>>());
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public boolean isNeighbor(Vertex<E,V> vertex){
		return this.neighborVertices.contains(vertex);
	}
	public void addEdge(Edge<E,V> edge){
		if(this.edges.contains(edge)){
			throw new IllegalArgumentException("["+edge+"] has been already connected to this node ["+this+"]");
		}
		this.neighborVertices.add(edge.getVertex1()==this?edge.getVertex2():edge.getVertex1());
		this.edges.add(edge);
	}
	public List<Edge<E,V>> getEdges() {
		return this.edges;
	}
	public List<Vertex<E,V>>getNeighborVertecies(){
		return this.neighborVertices;
	}
//	public void addNeighborVertix(Vertex<E,V> vertix){
//		this.neighborVertices.add(vertix);
//	}
	public int degree(){
		return this.edges.size();
	}
	/**
	 * removes a neighbor vertex and returns the set of edges removed
	 */
	public Set<Edge<E,V>> removeNeighbor(Vertex<E,V> neighbor){
		this.neighborVertices.remove(neighbor);
		final List<Edge<E,V>>es=new ArrayList<Edge<E,V>>(edges);
		final Set<Edge<E,V>>removed=Collections.synchronizedSet(new HashSet<Edge<E,V>>());
		for(Edge<E,V> e:es){
			if(e.getVertex1()==neighbor || e.getVertex2()==neighbor){
				edges.remove(e);
				removed.add(e);
			}
		}
		return removed;
	}
	
	public List<Vertex<E,V>> getNeighborDominantVertecies(){
		final List<Vertex<E,V>>list=new ArrayList<Vertex<E,V>>();
		for(Vertex<E,V> v:this.neighborVertices){
			if(v.isDominant()){list.add(v);}
		}
		return list;
	}
	public boolean isLinkedToDominantVertex(){
		for(Vertex<E,V> n:neighborVertices){
			if(n.isDominant()) return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return this.name;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited() {
		this.visited = true;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isDominant() {
		return dominant;
	}
	public void setDominant(boolean dominant) {
		this.dominant = dominant;
	}
	public double getConnectness() {
		return connectness;
	}
	public void setConnectness(double connectness) {
		this.connectness = connectness;
	}
	public void setDominant() {
		this.setDominant(true);
	}
	public Vertex<E,V> nextNeighbor(){
		if(degree()==0) return null;
		return this.neighbors[neighborPointer++%neighbors.length];
	}
	public boolean hasUnvisitedNeighbor(){
		for(Vertex<E,V> n:neighbors){if(!n.isVisited()){return true;}}
		return false;
	}
}
