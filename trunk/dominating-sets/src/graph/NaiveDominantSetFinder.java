package graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NaiveDominantSetFinder implements DominantSetFinder {
	private Graph g;
	private Set<Vertex>dominantSet;
	
	@Override
	public void setGraph(Graph g) {
		this.g=g;
		this.dominantSet=new HashSet<Vertex>();
	}
	
	@Override
	public void findDominantSet(){
		
		for(Vertex v:g.getVertecies()){
			v.setDominant(true);
		}
		this.dominantSet.clear();
		this.dominantSet.addAll(Arrays.asList(g.getVertecies()));
		int size;
		int i=1;
		do{
			size = this.dominantSet.size();
			System.out.printf("iteration [%d] dominantion factor : %d\n",i++,size);
			final Collection<Vertex>dominantSetCopy = new HashSet<Vertex>(dominantSet);
			for(Vertex v:dominantSetCopy){
				v.setDominant(false);
				this.dominantSet.remove(v);
				if(g.hasVertexNotLinkedToDominantVertex()){
					v.setDominant(true);
					this.dominantSet.add(v);	
				}
				
			}
		}while(size!=this.dominantSet.size());
	}
	
	@Override
	public Vertex[] getDominantSet() {
		return this.dominantSet.toArray(new Vertex[this.dominantSet.size()]);
	}

	@Override
	public int getDominationNumber() {
		return this.dominantSet.size();
	}
	
}
