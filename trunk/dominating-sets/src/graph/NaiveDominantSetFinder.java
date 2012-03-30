package graph;

import java.util.HashSet;
import java.util.Set;

public class NaiveDominantSetFinder implements DominantSetFinder {
	private Graph g;
	private Set<Vertex>dominantSet;
	
	public NaiveDominantSetFinder(Graph g) {
		this.g=g;
		this.dominantSet=new HashSet<Vertex>();
	}
	
	public void findDominantSet(){
		
		for(Vertex v:g.getVerticies()){
			v.setDominant(true);
		}
		for(Vertex v:g.getVerticies()){
			v.setDominant(false);
			for(Vertex n:g.getVerticies()){
				if(!n.isLinkedToDominantVertex()){
					v.setDominant(true);
					break;
				}
			}
		}
		this.dominantSet.clear();
		for(Vertex v:g.getVerticies()){
			if(v.isDominant()){
				this.dominantSet.add(v);
			}
		}
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
