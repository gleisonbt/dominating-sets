package ui.graph.layout;

public enum GraphLayoutType {
	
	//Spiral 	 {public Class<? extends GraphLayout> clazz() {return SpiralGraphLayout.class;}},
	Random   {public Class<? extends GraphLayout> clazz() {return RandomGridGraphLayout.class;}},
	//Polygon  {public Class<? extends GraphLayout> clazz() {return PolygonGraphLayout.class;}},
	Circular {public Class<? extends GraphLayout> clazz() {return CircularGraphLayout.class;}},
	Planner  {public Class<? extends GraphLayout> clazz() {return PlannerGraphLayout.class;}},
	Maze     {public Class<? extends GraphLayout> clazz() {return MazeGraphLayout.class;}},
	Star     {public Class<? extends GraphLayout> clazz() {return StarGraphLayout.class;}};
	
	public abstract Class<? extends GraphLayout> clazz();
}
