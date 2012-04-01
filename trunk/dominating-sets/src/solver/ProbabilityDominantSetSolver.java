package solver;

import graph.Graph;
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
	protected Set<Vertex> findDominantSet() {
		final Graph g = getGraph();
		final List<Vertex>V = new ArrayList<Vertex>(Arrays.asList(g.getVertecies()));
		final Set<Vertex>dominantSet = new HashSet<Vertex>();
		int E = g.getEdges().length;
		Vertex max;
		do{
			for(final Vertex v:V){
				if(v.isVisited()){break;}
				v.setConnectness(v.degree()*1.0/E);
				incrementIterations();
			}
			max = V.get(0);
			for(Vertex v:V){
				if(v.getConnectness()>max.getConnectness()){
					max=v;
				}
				incrementIterations();
			}
			max.setDominant(true);
			dominantSet.add(max);
			E-=max.getEdges().length/2;
			V.remove(max);
			for(Vertex v:max.getNeighborVertecies()){
				E-=v.getEdges().length/2;
				V.remove(v);
				incrementIterations();
			}
		}while(!(V.isEmpty() ||  max==V.get(0)));
		return dominantSet;
	}

}
