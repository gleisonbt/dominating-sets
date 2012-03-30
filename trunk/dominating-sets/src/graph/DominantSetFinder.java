package graph;

public interface DominantSetFinder {
	public abstract Vertex[]getDominantSet();
	/**
	 * The domination number Y(G) is the number of vertices in a smallest dominating set for G.
	 * @return smallest possible number of vertices that are dominant
	 */
	public int getDominationNumber();
}
