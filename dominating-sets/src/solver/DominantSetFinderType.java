package solver;


public enum DominantSetFinderType {
	Naive {public Class<? extends DominantSetFinder> clazz() {return NaiveDominantSetFinder.class;}};
	
	public abstract Class<? extends DominantSetFinder> clazz();
}
