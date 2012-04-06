/**
 * 
 */
package solver;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JaCoP.constraints.XeqY;
import JaCoP.constraints.netflow.NetworkBuilder;
import JaCoP.constraints.netflow.NetworkFlow;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.Search;

/**
 * constraint satisfaction solver for the dominant set problem
 * 
 * @author Hussain Al-Mutawa
 * @version 1.0
 */
public class ConstraintSatisfactionDominantSetSolver extends
		AbstractDominantSetSolver {

	
	
	@Override
	protected void findDominantSet() {
		
		//final Graph  g = getGraph();
		final Vertex[] V = getVertecies();
		Store store  = new Store();
		NetworkBuilder net = new NetworkBuilder();
		List<IntVar>l=new ArrayList<IntVar>();
		for(Vertex v:V){
			IntVar A = new IntVar(store,v.getName(), 0, 1);
			l.add(A);
			List<Vertex>N=Graph.asList(v.getNeighborVertecies());
			for(Vertex u:V){
		
				if(!N.contains(u)){
					IntVar B = new IntVar(store,v.getName(), 0, 1);
					l.add(B);
					IntVar[]vars = {A,B};
					store.impose(new XeqY(A,B));
				}else{
					IntVar C = new IntVar(store,v.getName(), 1, 2);
					l.add(C);
					IntVar[]vars = {A,C};
					store.impose(new XeqY(A,C));
				}
			}
		}
		
		 Search<IntVar> search = new DepthFirstSearch<IntVar>(); 
 
	        boolean result = search.assignSolution(); 
	        
	        if ( result ) 
	            System.out.println("Solution: " + Arrays.toString(l.toArray())  ); 
	        else 
	            System.out.println("*** No");
		
		store.impose(new NetworkFlow(net));
		
	}

}
