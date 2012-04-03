package solver;

import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class GrowingTreeDominantSetSolver extends AbstractDominantSetSolver{

	@Override
	protected Set<Vertex> findDominantSet() {
		final Graph g = getGraph();
		final Vertex[] V = g.getVertecies();
		
		final SortedSet<Vertex>set = new TreeSet<Vertex>(new Comparator<Vertex>(){
			@Override public int compare(Vertex o1, Vertex o2) {
				return o2.degree()>o1.degree()?1:-1;
			}
		});
		set.addAll(Arrays.asList(V));
		Vertex v=null;
		final Set<Vertex>dset=new HashSet<Vertex>();
		for(final Iterator<Vertex>it=set.iterator();it.hasNext();){
			v=it.next();
			incrementIterations();
			if(v.isVisited()){
				continue;
			}
			v.setVisited(true);
			if(!v.isLinkedToDominantVertex()){
				v.setDominant(true);
				dset.add(v);
				for(Vertex n:v.getNeighborVertecies()){
					n.setVisited(true);
					incrementIterations();
				}
			}
			
		}
		
		return dset;
	}
}
