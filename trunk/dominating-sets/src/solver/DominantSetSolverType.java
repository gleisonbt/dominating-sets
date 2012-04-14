package solver;


public enum DominantSetSolverType {
	Naive 			(NaiveDominantSetSolver.class),
	GrowingTree 	(GrowingTreeDominantSetSolver.class),
	Proportional 	(ProportionalDominantSetSolver.class),
	LargestID 		(LargestIDDominantSetSolver.class),
	LargestDegree 	(LargestDegreeDominantSetSolver.class);
	
	final private Class<? extends DominantSetSolver>solver;
	
	DominantSetSolverType(Class<? extends DominantSetSolver> solver){
		this.solver=solver;
	}
	
	public Class<? extends DominantSetSolver> solver(){return solver;}
}
