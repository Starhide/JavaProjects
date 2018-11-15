package starhide.gameoflife;

public class GameObject {

	protected float x;
	protected float y;
	protected float sx;
	protected float sy;
	/**
	 * 0 = Dead 1 = Going to Live -- 3 N 2 = Going to Die -- N < 2 || N > 3 3 =
	 * Alive -- 2 N or 3 N
	 */
	private int Status;

	public GameObject(float x, float y, float sx, float sy) {
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
		this.Status = 0;
	}

	public void update() {
	}

	public void render() {
		Color temp = new Color(1, 1, 1);
		switch (this.Status) {
		case 0:
			temp = new Color(1, 1, 1);
			break;
		case 1:
			temp = new Color(0.65f, 1f, 0.65f);
			break;
		case 2:
			temp = new Color(1, 0, 0);
			break;
		case 3:
			temp = new Color(0.05f, 0.9f, 0.05f);
			break;
		}
		Draw.rect(x, y, sx, sy, temp);
	}

	public float getSX() {
		return sx;
	}

	public float getSY() {
		return sy;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getCenterY() {
		return y + sy / 2;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int Status) {
		this.Status = Status;
	}
}