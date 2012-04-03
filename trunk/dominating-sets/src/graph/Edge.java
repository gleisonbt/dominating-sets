package graph;



public class Edge implements Viewable{
	private final String name;
	private final Vertex v1,v2;
	public Edge(String name,Vertex v1,Vertex v2){
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
	public Vertex getVertex1() {
		return v1;
	}
	public Vertex getVertex2() {
		return v2;
	}
	public String getName() {
		return name;
	}
	
	private Locateable viewable;

	public Locateable getViewableObject() {
		return viewable;
	}

	public void setViewableObject(Locateable viewable) {
		this.viewable=viewable;
	}
}
