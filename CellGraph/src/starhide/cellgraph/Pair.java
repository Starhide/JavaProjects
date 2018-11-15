package starhide.cellgraph;

/**
 * Write a description of class Edge here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class Pair {
	protected int x;
	protected int y;

	/**
	 * Constructor for objects of class Edge
	 */
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object o) {
		if (o instanceof Pair) {
			Pair p = (Pair) o;
			return (p.getX() == x && p.getY() == y) || (p.getX() == y && p.getY() == x);
		}
		return false;
	}

	public String toString() {
		return x + " " + y;
	}
}
