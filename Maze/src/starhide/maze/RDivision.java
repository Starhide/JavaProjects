package starhide.maze;

/**
 * Recursive Division
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class RDivision extends MazeAlgorithm {
	/**
	 * Constructor for objects of class RDivision
	 * 
	 * @param siz  Size of the maze
	 * @param seed Seed for the random
	 */
	public RDivision(int width, int height, String seed, MazeGUI maze) throws LargeSizeException, SmallSizeException {
		super(width, height, seed, maze);
	}

	public void generate() {
		for (int x = 0; x < width * 2 + 1; x++) {
			for (int y = 0; y < height * 2 + 1; y++) {
				if (x == 0 || x == width * 2 || y == 0 || y == height * 2)
					grid[x][y] = 1;
				else
					grid[x][y] = 0;
			}
		}
		split(1, 1, width, height);
	}

	/**
	 * Recursive split of sections
	 */
	private void split(int x, int y, int w, int h) {
		if (mazegui.realTime.getState())
			mazegui.renderMaze();

		if (w == 1 || h == 1) {
			return;
		}

		int wall;
		int door;
		if (w > h) {
			wall = rand.nextInt(w - 1);
			door = rand.nextInt(h);
			for (int i = 0; i < h * 2; i++) {
				grid[x + wall * 2 + 1][y + i] = 1;
				if (i == door * 2)
					grid[x + wall * 2 + 1][y + i] = 0;
			}
			split(x, y, wall + 1, h);
			split(x + 2 * (wall + 1), y, w - wall - 1, h);
		} else {
			wall = rand.nextInt(h - 1);
			door = rand.nextInt(w);
			for (int i = 0; i < w * 2; i++) {
				grid[x + i][y + wall * 2 + 1] = 1;
				if (i == door * 2)
					grid[x + i][y + wall * 2 + 1] = 0;
			}
			split(x, y, w, wall + 1);
			split(x, y + 2 * (wall + 1), w, h - wall - 1);
		}

	}
}
