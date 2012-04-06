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
public class ProbabilityDominantSetSolver extends AbstractDominantSetSolver {

	@Override
	protected void findDominantSet() {
		final List<Vertex>V = new ArrayList<Vertex>(Arrays.asList(getVertecies()));
		final Set<Vertex>dominantSet = new HashSet<Vertex>();
		int E = getEdges().length;
		Vertex max;
		do{
			max = V.get(0);
			for(final Vertex v:V){
				final double d = v.degree()*1.0/E;
				final double m = max.getConnectness();
				v.setConnectness(d);
				if(d>m){
					max=v;
				}
				incrementIterations();
			}
			
			max.setDominant(true);
			dominantSet.add(max);
			V.remove(max);
			V.removeAll(Arrays.asList(max.getNeighborVertecies()));

		}while(!(V.isEmpty() ||  max==V.get(0)));
		//return dominantSet;
	}

}
