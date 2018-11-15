package starhide.maze;

/**
 * Thrown when the maze is less than or equal to zero
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class SmallSizeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8774828588703677883L;

	/**
	 * Constructor for objects of class SmallSizeException
	 */
	public SmallSizeException() {
		super("Size cannot be less than or equal to zero");
	}

}
