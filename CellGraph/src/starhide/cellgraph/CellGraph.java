package starhide.cellgraph;

import java.io.FileInputStream;

/**
 * Write a description of class CellGraph here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class CellGraph {
	private Graph graph;

	/**
	 * Constructor for objects of class CellGraph
	 */
	public CellGraph() {
		try {
			FileInputStream f = new FileInputStream("bin/assets/tinyG.txt");
			graph = new Graph(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new CellGraphGui(graph);
	}

	public static void main(String args[]) {
		new CellGraph();
	}
}
