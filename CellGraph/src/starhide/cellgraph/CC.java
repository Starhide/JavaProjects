package starhide.cellgraph;

import java.util.ArrayList;

/**
 * Calculates the number of connected components
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class CC {
	private ArrayList<ArrayList<Integer>> classes;

	/**
	 * Constructor for objects of class CC
	 */
	public CC(Graph G) {
		int[] vertices = new int[G.getVertexCount()];
		for (int x = 0; x < G.getVertexCount(); x++)
			vertices[x] = x;

		classes = new ArrayList<>();
		int current = -1;
		for (int x = 0; x < G.getVertexCount(); x++) {
			if (vertices[x] != -1) {
				classes.add(new ArrayList<>());
				current++;
				classes.get(current).add(x);
				DepthFirstSearch dfs = new DepthFirstSearch(G, x);
				for (int y = x + 1; y < G.getVertexCount(); y++) {
					if (dfs.isMarked(y)) {
						vertices[y] = -1;
						classes.get(current).add(y);
					}
				}
			}
		}
	}

	public boolean connected(int v, int w) {
		return id(v) == id(w);
	}

	public int count() {
		return classes.size();
	}

	public int id(int v) {
		for (ArrayList<Integer> a : classes) {
			if (a.contains(v)) {
				return classes.indexOf(a);
			}
		}
		return -1;
	}
}
