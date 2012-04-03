package graph;

public class Edge<E extends Locateable,V extends Locateable> extends AbstractViewable<E>{
	private final String name;
	private final Vertex<E,V> v1,v2;
	public Edge(String name,Vertex<E,V> v1,Vertex<E,V> v2){
		this.name = name;
		this.v1=v1;
		this.v2=v2;
//		v1.addNeighborVertix(v2);
//		v2.addNeighborVertix(v1);
		v1.addEdge(this);
		v2.addEdge(this);
	}
	@Override
	public String toString() {
		return "edge("+this.name+","+this.v1.getName()+","+this.v2.getName()+").";
	}
	public Vertex<E,V> getVertex1() {
		return v1;
	}
	public Vertex<E,V> getVertex2() {
		return v2;
	}
	public String getName() {
		return name;
	}
}
