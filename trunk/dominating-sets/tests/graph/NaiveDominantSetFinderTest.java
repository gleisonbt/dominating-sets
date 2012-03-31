package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NaiveDominantSetFinderTest {

	@Test
	public void testNaiveDominantSetFinder() {
		Graph g = new Graph('e', "(a,b)");
		assertEquals(0,new NaiveDominantSetFinder(g).getDominantSet().length);
	}

	@Test
	public void testFindDominantSet() {
		Graph g = new Graph('e', "(a,b),(b,c),(a,c)");
		System.out.println(g.getNeighborsPrologSyntax());
		NaiveDominantSetFinder ndsf = new NaiveDominantSetFinder(g);
		ndsf.findDominantSet();
		assertEquals(1,ndsf.getDominantSet().length);
	}

	@Test
	public void testGetDominantSet() {
		String configuration =  "(a,b), (j,y), (b,r)"+
								"(b,c), (b,j), (y,r)"+
								"(a,c), (d,b), (o,r)"+
								"(a,d), (y,j), (j,x)"+
								"(c,d), (u,y), (u,j)";	
		Graph g = new Graph('e', configuration);
		NaiveDominantSetFinder ndsf = new NaiveDominantSetFinder(g);
		ndsf.findDominantSet();
		Vertex[] ds = ndsf.getDominantSet();
		assertEquals(3,ds.length);
	}

	@Test
	public void testGetDominationNumber() {
		NaiveDominantSetFinder ndsf = new NaiveDominantSetFinder(new Graph('e', "(a,b),(b,c),(a,c)"));
		ndsf.findDominantSet();
		assertEquals(1,ndsf.getDominationNumber());
	}

}
