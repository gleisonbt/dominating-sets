package solver;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * A naive solver based on the idea that all of the vertices are 
 * marked as dominant, then remove a vertex and test if there is a
 * neighbor vertex which is left alone not being dominant nor linked
 * directly to another dominant vertex
 *  
 * @author Hussain Al-Mutawa
 * @version 1.0
 */
public class NaiveDominantSetSolver extends AbstractDominantSetSolver {

	@Override
	protected void findDominantSet(){
		final Vertex[] V = getVertices();
		final List<Vertex>dominantSet = Graph.asList(V);
		int size;
		do{
			size = dominantSet.size();	// get the cardinality of dominant set
			final List<Vertex>dominantSetCopy = new ArrayList<Vertex>(dominantSet);
			for(final Vertex v:dominantSetCopy){	//iterate over all vertices
				v.setDominant(false);				//unmark vertex v
				dominantSet.remove(v);				//remove it from the set 
				if(!v.isLinkedToDominantVertex()){	// if the vertex is not linked 
					v.setDominant(true);			// to a dominant vertex 
					dominantSet.add(v);				// mark it again
				}else{
					// if unmarking v has not made any of the neighbor to be affected
					// carry on, otherwize, mark v again as dominant
					final Vertex[]N=v.getNeighborVertecies();	// get neighbors of v
					for(final Vertex u:N){
						if(!(u.isDominant() || u.isLinkedToDominantVertex())){
							v.setDominant(true);
							dominantSet.add(v);	
						}
						incrementIterations();
					}
				}
				incrementIterations();
			}
		}while(size!=dominantSet.size());
	}
}
