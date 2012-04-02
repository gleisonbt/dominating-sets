package solver;


public enum DominantSetSolverType {
	Naive 		{public Class<? extends DominantSetSolver> clazz() {return NaiveDominantSetSolver.class;}},
	GrwingTree 	{public Class<? extends DominantSetSolver> clazz() {return GrowingTreeDominantSetSolver.class;}},
	Propotional {public Class<? extends DominantSetSolver> clazz() {return ProbabilityDominantSetSolver.class;}},
	Constraint  {public Class<? extends DominantSetSolver> clazz() {return ConstraintSatisfactionDominantSetSolver.class;}},
	LargestID 	{public Class<? extends DominantSetSolver> clazz() {return LargestIDDominantSetSolver.class;}};
	public abstract Class<? extends DominantSetSolver> clazz();
}
