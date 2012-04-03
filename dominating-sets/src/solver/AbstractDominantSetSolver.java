package solver;

import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDominantSetSolver implements DominantSetSolver{
	private Graph g;
	private Set<Vertex>dominantSet;
	private int iterations = 0;
	private long elapsedTime = 0L;
	@Override
	public void setGraph(Graph g) {
		this.g=g;
		this.dominantSet=new HashSet<Vertex>();
	}

	@Override
	public int getIteratinos() {
		return iterations;
	}

	@Override
	public Vertex[] getDominantSet() {
		return this.dominantSet.toArray(new Vertex[this.dominantSet.size()]);
	}

	@Override
	public Graph getGraph() {
		return g;
	}
	
	@Override
	public int getDominationNumber() {
		return this.dominantSet.size();
	}

	@Override
	public void solve() {
		reset();
		startTimer();
		this.dominantSet = findDominantSet();
		stopTimer();
	}
	
	protected abstract Set<Vertex> findDominantSet();

	@Override
	public void incrementIterations() {
		iterations++;
	}
	
	@Override
	public long getElapsedTime() {
		return this.elapsedTime;
	}
	@Override
	public void startTimer() {
		this.elapsedTime = new Date().getTime();
	}
	@Override
	public void stopTimer() {
		this.elapsedTime = new Date().getTime() - this.elapsedTime;
	}

	private final void reset() {
		this.iterations=0;
		this.elapsedTime=-1;
		for(Vertex v:this.g.getVertecies()){
			v.setConnectness(0.0);
			v.setDominant(false);
			v.setVisited(false);
		}
	}
	@Override
	public void setDominantSet(Collection<Vertex> vertices) {
		this.dominantSet = new HashSet<Vertex>(vertices);
	}
}
