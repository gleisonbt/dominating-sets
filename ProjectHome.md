# Dominating Sets #
Definition: A Dominating Set DS is a subset of nodes such that each node is either in DS or has a neighbor in DS. [Wikipedia link](http://en.wikipedia.org/wiki/Dominating_set)

![http://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Dominating-set.svg/200px-Dominating-set.svg.png](http://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Dominating-set.svg/200px-Dominating-set.svg.png)

This project is done as an academic research for the course 159402 Programming Languages, taught at Massey University, New Zealand. The project uses different algorithms to solve the dominating set problem.

From wikipedia _In graph theory, a dominating set for a graph G = (V, E) is a subset D of V such that every vertex not in D is joined to at least one member of D by some edge. The domination number γ(G) is the number of vertices in a smallest dominating set for G._

This project is aimed to implement a dominating set solver and provide graph visualization as well. The reason of not using existing libraries for visualizing graphs is that most of them are resource greedy and may contain extra features which are not required, or worse may not contain a required feature.

Therefore, the graph visualization utility used in this project is light weight and able to render up to 3000 vertices linked to each other within a blink. The graph visualizer can render undirected graphs at the moment, loops are not yet implemented. However, will add these features soon. The visualizer has the following rendering layouts:
  * Random : which is based on a grid where vertices are placed randomly.
  * Circular : which displays all of the vertices distant equally from a center point.
  * Maze : which render the nodes as a maze
  * Star : which displays vertices in a circular way but not equally distant from the center.
  * Planner : this layout is expected to show planner graph as per the Fruchterman-Rheingold algorithm (FR) however, it is not accurate at the moment.



![http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%2010.38.12%20PM.png](http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%2010.38.12%20PM.png)
## . ##

![http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%204.54.52%20AM.png](http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%204.54.52%20AM.png)
## . ##
![http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%2012.57.09%20AM.png](http://dominating-sets.googlecode.com/svn/wiki/Screen%20Shot%202012-04-01%20at%2012.57.09%20AM.png)