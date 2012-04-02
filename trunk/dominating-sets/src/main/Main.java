/**
 *
 */
package main;

import graph.Graph;
import ui.GraphViewer;

/**
 * Dominating set main class
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 */
public class Main {
	
	public static void main(String[]args){
		
//		String configuration = "a,b b,g b,c c,g g,d g,e f,e a,f g,f";
//		Graph g = new Graph('u',configuration);
//		System.out.println(g.getVerteciesPrologSyntax());
//		System.out.println(g.getEdgesPrologSyntax());
//		System.out.println(g.getNeighborsPrologSyntax());
//		System.out.println(g.getConnectedPrologSyntax());
		new GraphViewer(){
			@Override
			public void onClosed() {
				System.exit(0);
			}
		}.setVisible(true);
	}

}
