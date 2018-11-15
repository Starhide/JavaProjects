package starhide.cellgraph;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Write a description of class Graph here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class Graph {
	private ArrayList<Pair> edges;
	private int vertices;

	/**
	 * Constructor for objects of class Graph
	 */
	public Graph(int V) {
		edges = new ArrayList<>();
		vertices = V;
	}

	public Graph(InputStream in) {
		int V = readInt(in);
		int E = readInt(in);
		int a = 0;
		int b = 0;

		edges = new ArrayList<>();

		for (int e = 0; e < E; e++) {
			a = readInt(in);
			b = readInt(in);
			edges.add(new Pair(a, b));
		}
		vertices = V;
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= vertices)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (vertices - 1));
	}

	public int getVertexCount() {
		return vertices;
	}

	public ArrayList<Pair> getEdges() {
		return edges;
	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		Pair p = new Pair(v, w);
		if (!edges.contains(p)) {
			edges.add(p);
		}
	}

	public Iterable<Integer> adj(int v) {
		ArrayList<Integer> adjacent = new ArrayList<>();
		for (Pair p : edges) {
			if (p.getX() == v) {
				adjacent.add(p.getY());
			} else if (p.getY() == v) {
				adjacent.add(p.getX());
			}
		}

		// Alternate java 8 way but its less readable
		// edges.stream().filter(p -> p.getX() == v).forEach(p ->
		// adjacent.add(p.getY()));
		// edges.stream().filter(p -> p.getY() == v).forEach(p ->
		// adjacent.add(p.getX()));

		return adjacent;
	}

	public int degree(int v) {
		int deg = 0;
		for (Pair p : edges) {
			if (p.getX() == v) {
				deg++;
			} else if (p.getY() == v) {
				deg++;
			}
		}

		return deg;
		// Alternate java 8 method
		// return (int) edges.stream().filter(p -> p.getY() == v || p.getX() ==
		// v).count();
	}

	public String toString() {
		String s = "";
		for (Pair p : edges) {
			s += "\n" + p.toString();
		}
		return vertices + "\n" + edges.size() + s;
	}

	public int readInt(InputStream in) {
		char[] a = new char[64];
		int index = 0;
		char temp = ' ';
		int output = 0;

		try {
			temp = (char) in.read();
			while (temp != ' ' && temp != '\r' && temp != '\n' && temp != 65535) {
				a[index] = temp;
				index++;
				temp = (char) in.read();
			}
			if (temp == '\r') {
				temp = (char) in.read();
			}
		} catch (Exception e) {

		}

		for (int x = 0; x < index; x++) {
			output += (a[x] - 48) * Math.pow(10, index - x - 1);
		}
		return output;
	}
}
