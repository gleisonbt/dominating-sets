package graph;

import java.util.Collection;
import java.util.HashSet;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class JungGraph<V extends Vertex, E extends Edge>  implements
		edu.uci.ics.jung.graph.Graph<V, E> {
	
	private final graph.Graph G;
	public JungGraph(graph.Graph g) {
		G = g;
	}
	public JungGraph(char name,String conf,boolean rm) {
		this(new graph.Graph(name,conf,rm));
	}
	
	@Override
	public boolean addEdge(E arg0, Collection<? extends V> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E arg0, Collection<? extends V> arg1, EdgeType arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addVertex(V arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsEdge(E e) {
		return G.containsEdge(e);
	}

	@Override
	public boolean containsVertex(V v) {
		return G.containsVertex(v);
	}

	@Override
	public int degree(V v) {
		return v.degree();
	}

	@Override
	public E findEdge(V v, V u) {
		return (E)G.findEdge(v, u);
	}

	@Override
	public Collection<E> findEdgeSet(V v, V u) {
		Collection<E>es=(Collection<E>)G.findEdgeSet(v, u);
		return es;
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}

	@Override
	public int getEdgeCount() {
		return (Integer)G.getMetric(GraphMetrics.Edges);
	}

	@Override
	public int getEdgeCount(EdgeType et) {
		return getEdgeCount();
	}

	@Override
	public EdgeType getEdgeType(E e) {
		return EdgeType.UNDIRECTED;
	}

	@Override
	public Collection<E> getEdges() {
		Collection<E>es=(Collection<E>)G.asList(G.getEdges());
		return es;
	}

	@Override
	public Collection<E> getEdges(EdgeType et) {
		return getEdges();
	}

	@Override
	public int getIncidentCount(E e) {
		return 2;
	}

	@Override
	public Collection<E> getIncidentEdges(V v) {
		Collection<E> es = (Collection<E>)G.asList(v.getEdges());
		return es;
	}

	@Override
	public Collection<V> getIncidentVertices(E e) {
		Collection<V> vs = new HashSet<V>();
		vs.add((V)e.getVertex1());
		vs.add((V)e.getVertex2());
		return vs;
	}

	@Override
	public int getNeighborCount(V v) {
		return v.getNeighborVertecies().length;
	}

	@Override
	public Collection<V> getNeighbors(V v) {
		Collection<V> vs = (Collection<V>)G.asList(v.getNeighborVertecies());
		return vs;
	}

	@Override
	public int getVertexCount() {
		return (Integer)G.getMetric(GraphMetrics.Verticies);
	}

	@Override
	public Collection<V> getVertices() {
		Collection<V> vs = (Collection<V>)G.asList(G.getVertecies());
		return vs;
	}

	@Override
	public boolean isIncident(V v, E e) {
		return e.getVertex1()==v || e.getVertex2()==v;
	}

	@Override
	public boolean isNeighbor(V v, V u) {
		return v.isNeighbor(u);
	}

	@Override
	public boolean removeEdge(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeVertex(V v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E e, V v, V u) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E e, V v, V u, EdgeType et) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V getDest(E e) {
		return null;
	}

	@Override
	public Pair<V> getEndpoints(E e) {
		return new Pair<V>((V)e.getVertex1(),(V)e.getVertex2());
	}

	@Override
	public Collection<E> getInEdges(V v) {
		return getIncidentEdges(v);
	}

	@Override
	public V getOpposite(V v, E e) {
		return (V)(e.getVertex1()==v?e.getVertex2():e.getVertex1());
	}

	@Override
	public Collection<E> getOutEdges(V v) {
		return getIncidentEdges(v);
	}

	@Override
	public int getPredecessorCount(V v) {
		return this.getPredecessorCount(v);
	}

	@Override
	public Collection<V> getPredecessors(V v) {
		return this.getSuccessors(v);
	}

	@Override
	public V getSource(E e) {
		return null;
	}

	@Override
	public int getSuccessorCount(V v) {
		return getSuccessors(v).size();
	}

	@Override
	public Collection<V> getSuccessors(V v) {
		Collection<V> vs = new HashSet<V>();
		for(Vertex w:G.getVertecies()){w.setVisited(false);}
		boolean done=false;
		V u = v;
		do{
			u = (V)u.getUnvisitedNeighbor();
			if(u==null) done=true;
			else{
				u.setVisited();
				vs.add(u);
			}
		}while(!done);
		return vs;
	}

	@Override
	public int inDegree(V v) {
		return v.degree();
	}

	@Override
	public boolean isDest(V v, E e) {
		return isIncident(v, e);
	}

	@Override
	public boolean isPredecessor(V v, V u) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSource(V v, E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSuccessor(V v, V u) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int outDegree(V v) {
		return v.degree();
	}

}
