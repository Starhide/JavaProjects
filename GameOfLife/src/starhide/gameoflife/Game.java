package starhide.gameoflife;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Game {

	private GameObject[][] objects;
	public boolean run = false;
	private boolean prevSpace;
	private boolean prevUP;
	private int px, py;

	public Game() {
		objects = new GameObject[64][64];

		int Dx = (Display.getWidth() / 64);
		int Dy = (Display.getHeight() / 64);
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 64; y++) {
				objects[x][y] = new GameObject(x * Dx, y * Dy, Dx - 1, Dy - 1);
			}
		}
	}

	public void getInput() {
		if (Mouse.isButtonDown(0)) {
			int x = (int) Mouse.getX() / (Display.getWidth() / 64);
			int y = (int) Mouse.getY() / (Display.getHeight() / 64);

			if (x != px) {
				if (objects[x][y].getStatus() == 0 || objects[x][y].getStatus() == 1) {
					objects[x][y].setStatus(3);
				} else if (objects[x][y].getStatus() == 3 || objects[x][y].getStatus() == 2) {
					objects[x][y].setStatus(0);
				}
				px = x;
				py = y;
			} else if (y != py) {
				if (objects[x][y].getStatus() == 0 || objects[x][y].getStatus() == 1) {
					objects[x][y].setStatus(3);
				} else if (objects[x][y].getStatus() == 3 || objects[x][y].getStatus() == 2) {
					objects[x][y].setStatus(0);
				}
				px = x;
				py = y;
			}
		} else {
			px = 65;
			py = 65;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (!prevSpace) {
				if (run) {
					run = false;
				} else {
					run = true;
				}
			}
			prevSpace = true;
		} else {
			prevSpace = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (!prevUP) {
				Update();
			}
			prevUP = true;
		} else {
			prevUP = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			Update();
		}
	}

	public void update() {
		if (run) {
			Update();
		}
	}

	private void Update() {
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 64; y++) {
				objects[x][y].update();
				if (objects[x][y].getStatus() == 1) {
					objects[x][y].setStatus(3);
				} else if (objects[x][y].getStatus() == 2) {
					objects[x][y].setStatus(0);
				}
			}
		}
	}

	public void render() {
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 64; y++) {
				renderstatus(x, y);
				objects[x][y].render();
			}
		}
	}

	private void renderstatus(int x, int y) {
		int N = 0, S = 0, E = 0, W = 0, NW = 0, NE = 0, SW = 0, SE = 0;
		try {
			N = objects[x][y + 1].getStatus();
			S = objects[x][y - 1].getStatus();
			E = objects[x + 1][y].getStatus();
			W = objects[x - 1][y].getStatus();
			NE = objects[x + 1][y + 1].getStatus();
			NW = objects[x - 1][y + 1].getStatus();
			SE = objects[x + 1][y - 1].getStatus();
			SW = objects[x - 1][y - 1].getStatus();
		} catch (Exception e) {
		}
		int NeighborCount = 0;

		if (N == 2 || N == 3) {
			NeighborCount++;
		}
		if (S == 2 || S == 3) {
			NeighborCount++;
		}
		if (E == 2 || E == 3) {
			NeighborCount++;
		}
		if (W == 2 || W == 3) {
			NeighborCount++;
		}
		if (NE == 2 || NE == 3) {
			NeighborCount++;
		}
		if (NW == 2 || NW == 3) {
			NeighborCount++;
		}
		if (SE == 2 || SE == 3) {
			NeighborCount++;
		}
		if (SW == 2 || SW == 3) {
			NeighborCount++;
		}

		if (NeighborCount == 3 && objects[x][y].getStatus() == 0) {
			objects[x][y].setStatus(1);
		} else if (NeighborCount != 2 && NeighborCount != 3 && objects[x][y].getStatus() == 3) {
			objects[x][y].setStatus(2);
		} else if ((NeighborCount == 2 || NeighborCount == 3) && objects[x][y].getStatus() == 2) {
			objects[x][y].setStatus(3);
		} else if (NeighborCount != 3 && objects[x][y].getStatus() == 1) {
			objects[x][y].setStatus(0);
		}
	}
}
