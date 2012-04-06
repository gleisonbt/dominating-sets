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
		final Vertex[] V = getVertecies();
		final List<Vertex>dominantSet = Graph.asList(V);
		int size;
		
		do{
			size = dominantSet.size();
			final List<Vertex>dominantSetCopy = new ArrayList<Vertex>(dominantSet);
			for(Vertex v:dominantSetCopy){
				v.setDominant(false);
				dominantSet.remove(v);
				if(!v.isLinkedToDominantVertex()){
					v.setDominant(true);
					dominantSet.add(v);	
				}else{
					Vertex[]N=v.getNeighborVertecies();
					for(Vertex u:N){
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
		
		//return new HashSet<Vertex>(dominantSet);
	}


	
	
	
}
