package solver;

import graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * this algorithm is based on the concept that each node
 * is assigned a connectness factor based on its degree,
 * those with higher probability are candidates to be
 * in the dominant set 
 * 
 * @author Hussain Al-Mutawa
 * @version 1.0
 *
 */
public class ProportionalDominantSetSolver extends AbstractDominantSetSolver {

	@Override
	protected void findDominantSet() {
	
		// get the list of vertices
		final List<Vertex>V = new ArrayList<Vertex>(Arrays.asList(getVertices()));
		
		// get the cardinality of edges set
		int E = getEdges().length;
		
		Vertex max;
		do{
			max = V.get(0);						// get the first vertex in the list
			//iterate for each vertex v in the list of vertices
			for(final Vertex v:V){
				final double c = v.degree()*1.0/E;		// get the connectness of v (relative degree)
				final double m = max.getConnectness();	// get the connectness of max
				v.setConnectness(c);					// update the connectness of v to the new one
				if(c>m){								// if c is higher than m
					max=v;								// set the node with max as v
				}
				incrementIterations();
			}
		
			max.setDominant(true);						// mark max as dominant
			V.remove(max);								// remove max from the list, and its neighbors
			V.removeAll(Arrays.asList(max.getNeighborVertecies()));
		
		}while(!(V.isEmpty() ||  max==V.get(0)));		// repeat until the list is empty or max not changed
	}

}

