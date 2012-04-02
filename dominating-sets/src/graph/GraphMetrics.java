package graph;

public enum GraphMetrics{
	Verticies		{public Class<?> type(){return Integer.class;}},
	Edges			{public Class<?> type(){return Integer.class;}},
	Faces			{public Class<?> type(){return Integer.class;}},
	GammaIndex		{public Class<?> type(){return Double.class;}},
	Connectivity	{public Class<?> type(){return Double.class;}},
	AverageDegree	{public Class<?> type(){return Double.class;}},
	Density			{public Class<?> type(){return Double.class;}},
	DominantVertices{public Class<?> type(){return Integer.class;}}, 
	Iterations		{public Class<?> type(){return Integer.class;}}, 
	SolveTime		{public Class<?> type(){return Long.class;}}, 
	isConnected		{public Class<?> type(){return Boolean.class;}}, 
	isSolved		{public Class<?> type(){return Boolean.class;}},
	isPlanar		{public Class<?> type(){return Boolean.class;}};
	
	public abstract Class<?>type();
}