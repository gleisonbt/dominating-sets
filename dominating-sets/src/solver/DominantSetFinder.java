package solver;

import graph.Graph;
import graph.Vertex;

public interface DominantSetFinder {
	Vertex[]getDominantSet();
	/**
	 * The domination number Y(G) is the number of vertices in a smallest dominating set for G.
	 * @return smallest possible number of vertices that are dominant
	 */
	int getDominationNumber();
	void setGraph(Graph graph);
	void findDominantSet();
	int getIteratinos();
}
