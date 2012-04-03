package graph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import solver.DominantSetSolver;
import solver.NaiveDominantSetSolver;

public class NaiveDominantSetSolverTest {

	@Test
	public void testNaiveDominantSetSolver() {
		Graph g = new Graph('e', "a,b",false);
		DominantSetSolver solver = new NaiveDominantSetSolver();
		solver.setGraph(g);
		assertEquals(0,solver.getDominantSet().length);
	}

	@Test
	public void testFindDominantSet() {
		Graph g = new Graph('e', "a,b	b,c		a,c",false);
		NaiveDominantSetSolver ndsf = new NaiveDominantSetSolver();
		ndsf.setGraph(g);
		ndsf.solve();
		assertEquals(1,ndsf.getDominantSet().length);
	}

	@Test
	public void testGetDominantSet() {
		String configuration =  "a,b 	j,y 	b,r "+
								"b,c 	b,j 	y,r "+
								"a,c 	d,b 	o,r "+
								"a,d 	j,x		    "+
								"c,d 	u,y 	u,j ";
		Graph g = new Graph('e', configuration,false);
		NaiveDominantSetSolver ndsf = new NaiveDominantSetSolver();
		ndsf.setGraph(g);
		ndsf.solve();
		Vertex[] ds = ndsf.getDominantSet();
		assertEquals(3,ds.length);
		//System.out.print(ds.length);
	}

	@Test
	public void testGetDominationNumber() {
		NaiveDominantSetSolver ndsf = new NaiveDominantSetSolver();
		ndsf.setGraph(new Graph('e', "a,b		b,c		a,c",false));
		ndsf.solve();
		assertEquals(1,ndsf.getDominationNumber());
	}

}
