package solver;

import graph.Vertex;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GrowingTreeDominantSetSolver extends AbstractDominantSetSolver{

	@Override
	protected void findDominantSet() {
		final Vertex[] V = getVertices();
		//CREATE a list of vertices sorted according to the cardinality in descending order
		final SortedSet<Vertex>set = new TreeSet<Vertex>(new Comparator<Vertex>(){
			@Override public int compare(Vertex o1, Vertex o2) {
				return o2.degree()>o1.degree()?1:-1;
			}
		});
		// add the vertices to the sorted list
		set.addAll(Arrays.asList(V));
		Vertex v=null;
		//iterate over all vertices in the sorted list
		for(final Iterator<Vertex>it=set.iterator();it.hasNext();){
			
			v=it.next();	// get a vertex v from the list
			
			incrementIterations();
			
			if(v.isVisited()){
				continue;		// skip visited
			}
			
			v.setVisited(true);	// set as visited
			
			if(!v.isLinkedToDominantVertex()){	// if the vertex v is 
												// not linked to a dominant vertex
				v.setDominant(true);			// set it as dominant
		
				for(Vertex n:v.getNeighborVertecies()){	
					n.setVisited(true);			// mark all of the neighbor nodes visited	
					incrementIterations();
				}
			}
			
		}
	}
}
