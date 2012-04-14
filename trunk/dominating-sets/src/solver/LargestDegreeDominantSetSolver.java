package solver;

import graph.Vertex;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class LargestDegreeDominantSetSolver extends AbstractDominantSetSolver {

	@Override
	protected void findDominantSet() {
		final SortedSet<Vertex>sorted=new TreeSet<Vertex>(new Comparator<Vertex>(){
			public int compare(Vertex a,Vertex b){
				return a.degree()>b.degree()?-1:1;
			}
		});
		sorted.addAll(Arrays.asList(getVertices()));
		
		for(final Iterator<Vertex>it=sorted.iterator();it.hasNext();){
			final Vertex v = it.next();
			v.setDominant();			// mark nodes dominant
			incrementIterations();
			if(!getGraph().hasVertexNotLinkedToDominantVertex()){
				break;					// if 
			}
		}
		for(final Iterator<Vertex>it=sorted.iterator();it.hasNext();){
			final Vertex v = it.next();
			v.setDominant(false);
			if(getGraph().hasVertexNotLinkedToDominantVertex()){
				v.setDominant();
			}
			incrementIterations();
		}
	
	}

}
