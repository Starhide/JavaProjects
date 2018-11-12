
/**
 * Thrown when the maze is less than or equal to zero
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class SmallSizeException extends Exception
{
    /**
     * Constructor for objects of class SmallSizeException
     */
    public SmallSizeException()
    {
        super("Size cannot be less than or equal to zero");
    }
    
    
}
