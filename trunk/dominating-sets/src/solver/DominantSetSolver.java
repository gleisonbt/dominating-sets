package solver;

import graph.Graph;
import graph.Vertex;

import java.util.Collection;

public interface DominantSetSolver {
	Vertex[]getDominantSet();
	/**
	 * The domination number Y(G) is the number of vertices in a smallest dominating set for G.
	 * @return smallest possible number of vertices that are dominant
	 */
	int getDominationNumber();
	void setGraph(Graph graph);
	void solve();
	int getIteratinos();
	void incrementIterations();
	long getElapsedTime();
	Graph getGraph();
	void startTimer();
	void stopTimer();
	void setDominantSet(Collection<Vertex>vertices);
}
