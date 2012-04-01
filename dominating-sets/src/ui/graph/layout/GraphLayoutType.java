package ui.graph.layout;

public enum GraphLayoutType {
	Circular {public Class<? extends GraphLayout> clazz() {return CircularGraphLayout.class;}},
	//Spiral 	 {public Class<? extends GraphLayout> clazz() {return SpiralGraphLayout.class;}},
	Random   {public Class<? extends GraphLayout> clazz() {return RandomGridGraphLayout.class;}},
	//Polygon  {public Class<? extends GraphLayout> clazz() {return PolygonGraphLayout.class;}},
	Planner  {public Class<? extends GraphLayout> clazz() {return PlannerGraphLayout.class;}},
	Maze     {public Class<? extends GraphLayout> clazz() {return MazeGraphLayout.class;}},
	Star     {public Class<? extends GraphLayout> clazz() {return StarGraphLayout.class;}};
	
	public abstract Class<? extends GraphLayout> clazz();
}
