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
		boolean more = false;
		do{
			//initialize vertecis map
			for(Vertex v:V){
				if(v.isVisited()) continue;
				ids.put(v, new HashSet<String>());
				incrementIterations();
			}
			int max=-1;
			for(Vertex v:V){
				if(v.isVisited()) continue;
				incrementIterations();
				Vertex[]N=v.getNeighborVertecies();
				for(Vertex u:N){
					if(u.isVisited()) continue;
					ids.get(u).add(v.getName());
					ids.get(u).addAll(ids.get(v));
					max = Math.max(max, ids.get(u).size());
					incrementIterations();
				}

			}
			if(max==-1) break;
			final Iterator<Vertex>it=V.iterator();
			while(it.hasNext()){
				final Vertex v = it.next();
				if(v.isVisited()) continue;
				if(ids.get(v).size()==max){
					v.setVisited();
					more=true;
				}
				incrementIterations();
			}
		}while(more);

		for(final Iterator<Vertex> it=V.iterator();it.hasNext();){
			final Vertex v = it.next();
			if(!v.isVisited()) { v.setDominant(); dominantSet.add(v);}
			incrementIterations();
		}
		return dominantSet;
	}

}
