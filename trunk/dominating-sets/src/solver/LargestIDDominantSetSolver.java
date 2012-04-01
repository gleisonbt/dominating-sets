package solver;

import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * this algorithm is based on the concept of a vertex v
 * sends a unique message (id) to its neighbors N(v), and every
 * vertex u in N(v) retransmit the message (id) along with their own
 * messages to their neighbors as well. at the end, nodes which
 * have the largest number of collected messages, are deemed as
 * part of the dominant set 
 * 
 * @author Hussain Al-Mutawa
 * @version 1.0
 *
 */
public class LargestIDDominantSetSolver extends AbstractDominantSetSolver {
	private Map<Vertex,Set<String>>ids;
	@Override
	protected Set<Vertex> findDominantSet() {
		ids = new HashMap<Vertex,Set<String>>();
		final Graph g = getGraph();
		final Set<Vertex>V = new HashSet<Vertex>(Arrays.asList(g.getVertecies()));
		final Set<Vertex>dominantSet = new HashSet<Vertex>();
		//initialize vertecis map
		for(Vertex v:V){
			ids.put(v, new HashSet<String>());
			incrementIterations();
		}
		int max=0;
		for(Vertex v:V){
			incrementIterations();
			for(Vertex u:v.getNeighborVertecies()){
				ids.get(u).add(v.getName());
				ids.get(u).addAll(ids.get(v));
				max = Math.max(max, ids.get(u).size());
				incrementIterations();
			}
			
		}
		final Iterator<Vertex>it=V.iterator();
		for(;it.hasNext();){
			final Vertex v = it.next();
			if(ids.get(v).size()==max){
				dominantSet.add(v);
				V.remove(v);
			}
			incrementIterations();
		}
		
		
		return dominantSet;
	}

}
