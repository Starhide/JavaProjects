package starhide.maze;

/**
 * Write a description of class Edge here.
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class Edge {
	private int first;
	private int second;
	private int weight;

	/**
	 * Constructor for objects of class Edge
	 */
	public Edge(int first, int second, int weight) {
		this.first = first;
		this.second = second;
		this.weight = weight;
	}

	/**
	 * Returns the first vertex
	 * 
	 * @return first vertex
	 */
	public int getFirst() {
		return first;
	}

	/**
	 * Returns the second vertex
	 * 
	 * @return second vertex
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * The weight of the edge
	 * 
	 * @return weight of edge
	 */
	public int getWeight() {
		return weight;
	}
}
