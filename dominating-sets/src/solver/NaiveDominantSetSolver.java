package solver;

import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
	protected Set<Vertex> findDominantSet(){
		final Graph g = getGraph();
		final Set<Vertex>dominantSet = new HashSet<Vertex>(Arrays.asList(g.getVertecies()));

		int size;
		
		do{
			size = dominantSet.size();
			final Collection<Vertex>dominantSetCopy = new HashSet<Vertex>(dominantSet);
			for(Vertex v:dominantSetCopy){
				v.setDominant(false);
				dominantSet.remove(v);
				if(!v.isLinkedToDominantVertex()){
					v.setDominant(true);
					dominantSet.add(v);	
				}else{
					for(Vertex u:v.getNeighborVertecies()){
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
		
		return dominantSet;
	}
	
	
}
