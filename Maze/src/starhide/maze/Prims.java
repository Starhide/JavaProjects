package starhide.maze;

import java.util.ArrayList;

/**
 * Prim's Spanning Tree Algorithm
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class Prims extends MazeAlgorithm {
	private ArrayList<Integer> vertices;
	private ArrayList<Edge> edges;

	/**
	 * Constructor for objects of class Prims
	 * 
	 * @param siz  Size of the maze
	 * @param seed Seed for the random
	 */
	public Prims(int width, int height, String seed, MazeGUI maze) throws LargeSizeException, SmallSizeException {
		super(width, height, seed, maze);
	}

	/**
	 * Generates the maze
	 */
	public void generate() {
		edges = new ArrayList<>();
		vertices = new ArrayList<>();
		vertices.add(0);

		edges.add(new Edge(0, 1, rand.nextInt(10)));
		edges.add(new Edge(0, width, rand.nextInt(10)));

		while (vertices.size() < width * height) {
			Edge smallest = null;
			for (Edge e : edges) { // Selects the smallest weighted edge
				if (smallest == null) {
					smallest = e;
				} else if (smallest.getWeight() > e.getWeight()) {
					smallest = e;
				}
			}

			// Adds the 3 cells to the grid
			grid[(smallest.getFirst() % width) * 2 + 1][(smallest.getFirst() / width) * 2 + 1] = 0;
			grid[((smallest.getSecond() % width) - (smallest.getFirst() % width)) + (smallest.getFirst() % width) * 2
					+ 1][((smallest.getSecond() / width) - (smallest.getFirst() / width))
							+ (smallest.getFirst() / width) * 2 + 1] = 0;
			grid[(smallest.getSecond() % width) * 2 + 1][(smallest.getSecond() / width) * 2 + 1] = 0;

			for (int x = 0; x < edges.size(); x++) {
				if (edges.get(x).getSecond() == smallest.getSecond()) {
					edges.remove(x);
					x--;
				}
			}

			vertices.add(smallest.getSecond());
			int value = smallest.getSecond();
			if (!(value % width == 0 || vertices.contains(value - 1))) {
				edges.add(new Edge(value, value - 1, rand.nextInt(10)));
			}
			if (!((value + 1) % width == 0 || vertices.contains(value + 1))) {
				edges.add(new Edge(value, value + 1, rand.nextInt(10)));
			}
			if (!(value < width || vertices.contains(value - width))) {
				edges.add(new Edge(value, value - width, rand.nextInt(10)));
			}
			if (!(value >= width * (height - 1) || vertices.contains(value + width))) {
				edges.add(new Edge(value, value + width, rand.nextInt(10)));
			}

			if (mazegui.realTime.getState())
				mazegui.renderMaze();
		}
	}

}
