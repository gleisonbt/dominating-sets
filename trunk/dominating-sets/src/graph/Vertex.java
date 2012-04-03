package graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex{
	private final List<Vertex>neighborVertices;
	private final String name;
	private final List<Edge>edges;
	private boolean visited;
	private boolean dominant;
	private double connectness;
	private int neighborPointer=0;
	private Vertex[]neighbors;
	
	public Vertex(String name){
		this.neighborVertices=Collections.synchronizedList(new ArrayList<Vertex>());
		this.edges=Collections.synchronizedList(new ArrayList<Edge>());
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public boolean isNeighbor(Vertex vertex){
		return this.neighborVertices.contains(vertex);
	}
	public void addEdge(Edge edge){
		if(this.edges.contains(edge)){
			throw new IllegalArgumentException("["+edge+"] has been already connected to this node ["+this+"]");
		}
		this.neighborVertices.add(edge.getVertex1()==this?edge.getVertex2():edge.getVertex1());
		this.edges.add(edge);
	}
	public Edge[] getEdges() {
		return this.edges.toArray(new Edge[edges.size()]);
	}
	public Vertex[]getNeighborVertecies(){
		return this.neighborVertices.toArray(new Vertex[neighborVertices.size()]);
	}
//	public void addNeighborVertix(Vertex vertix){
//		this.neighborVertices.add(vertix);
//	}
	public int degree(){
		return this.edges.size();
	}
	/**
	 * removes a neighbor vertex and returns the set of edges removed
	 */
	public Set<Edge> removeNeighbor(Vertex neighbor){
		this.neighborVertices.remove(neighbor);
		final List<Edge>es=new ArrayList<Edge>(edges);
		final Set<Edge>removed=Collections.synchronizedSet(new HashSet<Edge>());
		for(Edge e:es){
			if(e.getVertex1()==neighbor || e.getVertex2()==neighbor){
				edges.remove(e);
				removed.add(e);
			}
		}
		return removed;
	}
	
	public List<Vertex> getNeighborDominantVertecies(){
		final List<Vertex>list=new ArrayList<Vertex>();
		for(Vertex v:this.neighborVertices){
			if(v.isDominant()){list.add(v);}
		}
		return list;
	}
	public boolean isLinkedToDominantVertex(){
		for(Vertex n:neighborVertices){
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
	public Vertex nextNeighbor(){
		if(degree()==0) return null;
		return this.neighbors[neighborPointer++%neighbors.length];
	}
	public boolean hasUnvisitedNeighbor(){
		for(Vertex n:neighbors){if(!n.isVisited()){return true;}}
		return false;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	private Locateable viewable;

	public Locateable getViewableObject() {
		return viewable;
	}

	public void setViewableObject(Locateable viewable) {
		this.viewable=viewable;
	}
	
}
