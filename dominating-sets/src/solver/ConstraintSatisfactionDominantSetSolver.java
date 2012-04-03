/**
 * 
 */
package solver;

import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;

/**
 * constraint satisfaction solver for the dominant set problem
 * 
 * @author Hussain Al-Mutawa
 * @version 1.0
 */
public class ConstraintSatisfactionDominantSetSolver extends
		AbstractDominantSetSolver {

	@Override
	protected Set<Vertex<?,?>> findDominantSet() {
		Model m = new CPModel();
		
		final Graph<?,?> g = getGraph();
		final Map<Vertex<?,?>,IntegerVariable>vars=new HashMap<Vertex<?,?>,IntegerVariable>(g.getVertecies().size());

		for(Vertex<?,?> v:getGraph().getVertecies()){
			final IntegerVariable var = Choco.makeIntVar(v.getName(), 0,1);
			vars.put(v,var);
		}
		for(Vertex<?,?> v:getGraph().getVertecies()){
			IntegerVariable var = vars.get(v);
			for(Vertex<?,?>n:v.getNeighborVertecies()){
				if(v==n)continue;
				IntegerVariable nvar = vars.get(n);
				//Choco.neg(new IntegerExpressionVariable(var, IntegerExpressionVariable., variables))
			}
		}

//		Solution s = new DefaultSolver(network).getSolution();
//		
//		for(Vertex v:getGraph().getVertecies()){
//			System.out.println(vars.get(v));
//			v.setDominant(s.getIntValue(vars.get(v))==1);
//		}
//		Set<Vertex>set=new HashSet<Vertex>();
//		for(Vertex v:g.getVertecies()){
//			if(v.isDominant())set.add(v);
//		}

		return null;
	}

}
