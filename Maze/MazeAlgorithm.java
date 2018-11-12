import java.util.Random;
/**
 * abstract MazeAlgorithm class
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public abstract class MazeAlgorithm
{
    protected int width;
    protected int height;
    protected int[][] grid;
    protected Random rand;
    protected MazeGUI mazegui;

    private String seed;

    /**
     * Constructor for a MazeAlgorithm
     * 
     * @param siz Size of the maze
     * @param seed Seed for the random
     */
    public MazeAlgorithm(int width, int height, String seed, MazeGUI maze) throws LargeSizeException, SmallSizeException{
        if(width <= 0 || height <= 0)
            throw new SmallSizeException();
        if(width > 9000 || height > 9000)
            throw new LargeSizeException();

        this.width = width;
        this.height = height;
        rand = new Random(seed.hashCode());
        this.seed = seed;
        this.mazegui = maze;

        grid = new int[width*2+1][height*2+1];

        for(int x = 0; x < width*2+1; x++){
            for(int y = 0; y < height*2+1; y++){
                grid[x][y] = 1;
            }
        }
    }

    /**
     * Width of the maze
     * 
     * @return width 
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Height of the maze
     * 
     * @return height 
     */
    public int getHeight(){
        return height;
    }

    /**
     * Returns the grid or the maze
     * 
     * @return int[][] maze
     */
    public int[][] getGrid(){
        return grid;
    }

    /**
     * Returns the seed
     * 
     * @return seed
     */
    public String getSeed(){
        return seed;
    }

    public abstract void generate();      
}
