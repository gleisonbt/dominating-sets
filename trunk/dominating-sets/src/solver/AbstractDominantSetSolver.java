package solver;

import graph.Edge;
import graph.Graph;
import graph.GraphMetrics;
import graph.Vertex;

import java.util.Date;
import java.util.Random;

public abstract class AbstractDominantSetSolver implements DominantSetSolver{
	private Graph G;
	private Vertex[]V;
	private Edge[]E;
	private int iterations = 0;
	private int dsCount = 0;
	private long elapsedTime = 0L;
	private boolean shuffleBeforeSolve;
	@Override
	public void setGraph(Graph g) {
		this.G = g;
		this.V = g.getVertecies();
		this.E = g.getEdges();
	}

	public Edge[] getEdges() {
		return E;
	}
	
	@Override
	public int getDominantSetCardinality(){
		reset();
		if(isShuffleBeforeSolve()){
			shuffle();
		}
		startTimer();
		findDominantSet();
		for(Vertex v:V){dsCount+=v.isDominant()?1:0;incrementIterations();}
		G.setMetric(GraphMetrics.isSolved, !G.hasVertexNotLinkedToDominantVertex());
		stopTimer();
		return dsCount;
	}
	
	@Override
	public int getIteratinos() {
		return iterations;
	}

//	@Override
	public Graph getGraph() {
		return G;
	}
	
	private final void shuffle() {
		final int I[]=new int[V.length];
		for(int i=0;i<I.length;++i){I[i]=i;}
		final Random r = new Random();
		for(int i=0;i<I.length;++i){
			int x=0,y=0;
			while(x==y){
				x = Math.abs(r.nextInt()) % I.length;
				y = Math.abs(r.nextInt()) % I.length;
			}
			int tmp = I[x];
			I[x]=I[y];
			I[y]=tmp;
		}
		//List<Vertex>list=new ArrayList<Vertex>();
		for(int i=0;i<I.length;i++){
			Vertex t = V[I[i]];
			V[I[i]]=V[i];
			V[i]=t;
		}
	}
	
	public Vertex[] getVertices() {
		return V;
	}
	
	
	private boolean isShuffleBeforeSolve() {
		return shuffleBeforeSolve;
	}

	@Override
	public void setShuffleBeforeSolve(boolean shuffleBeforeSolve) {
		this.shuffleBeforeSolve = shuffleBeforeSolve;
	}
	
	protected abstract void findDominantSet();

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
		this.dsCount=0;
		for(Vertex v:V){resetVertex(v);}
	}
	
	private void resetVertex(Vertex v) {
		v.setConnectness(0.0);
		v.setDominant(false);
		v.setVisited(false);
	}
}
