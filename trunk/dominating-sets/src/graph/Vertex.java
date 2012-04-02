package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {
	private final Set<Vertex>neighborVertices;
	private final String name;
	private final Set<Edge>edges;
	private boolean visited;
	private boolean dominant;
	private double connectness;
	private int neighborPointer=0;
	private Vertex[]neighbors;
	
	public Vertex(String name){
		this.neighborVertices=Collections.synchronizedSet(new HashSet<Vertex>());
		this.edges=Collections.synchronizedSet(new HashSet<Edge>());
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
		this.edges.add(edge);
	}
	public Edge[] getEdges() {
		return edges.toArray(new Edge[this.edges.size()]);
	}
	public Vertex[]getNeighborVertecies(){
		return neighbors;
	}
	public void addNeighborVertix(Vertex vertix){
		this.neighborVertices.add(vertix);
		this.neighbors = neighborVertices.toArray(new Vertex[this.neighborVertices.size()]);
	}
	public int degree(){
		return this.edges.size();
	}
	public Vertex[]getNeighborDominantVertecies(){
		final List<Vertex>list=new ArrayList<Vertex>();
		for(Vertex v:this.neighborVertices){
			if(v.isDominant()){list.add(v);}
		}
		return list.toArray(new Vertex[list.size()]);
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
}
