package solver;

import graph.Graph;

public interface DominantSetSolver {
	int getDominantSetCardinality();
	/**
	 * The domination number Y(G) is the number of vertices in a smallest dominating set for G.
	 * @return smallest possible number of vertices that are dominant
	 */
	void setGraph(Graph graph);

	int getIteratinos();
	void incrementIterations();
	long getElapsedTime();
	Graph getGraph();
	void startTimer();
	void stopTimer();
	
	void setShuffleBeforeSolve(boolean shuffleBeforeSolve);
}
