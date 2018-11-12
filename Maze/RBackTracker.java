import java.util.ArrayList;
/**
 * Randomized Recursive Back-Tracker (Depth-first search)
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class RBackTracker extends MazeAlgorithm
{
    /**
     * Constructor for objects of class RBackTracker
     * 
     * @param siz Size of the maze
     * @param seed Seed for the random
     */
    public RBackTracker(int width, int height, String seed, MazeGUI maze) throws LargeSizeException, SmallSizeException
    {
        super(width, height, seed, maze);
    }

    public void generate(){
        grid[1][1]=0;
        step(1,1);
    }

    /**
     * Recursive step
     */
    private void step(int x, int y){
        //0 = y-1, 1 = y+1, 2 = x-1, 3 = x+1;
        if(mazegui.realTime.getState())
                mazegui.renderMaze();
        
        ArrayList<Integer> neighbors = new ArrayList();
        neighbors = getNeighbors(x,y);
        while(neighbors.size() > 0){
            int index = rand.nextInt(neighbors.size());
            switch(neighbors.get(index)){
                case 1:
                grid[x-1][y] = 0;
                grid[x-2][y] = 0;
                step(x-2,y);
                break;
                case 2:
                grid[x][y-1] = 0;
                grid[x][y-2] = 0;
                step(x,y-2);
                break;
                case 3:
                grid[x+1][y] = 0;
                grid[x+2][y] = 0;
                step(x+2,y);
                break;
                case 4:
                grid[x][y+1] = 0;
                grid[x][y+2] = 0;
                step(x,y+2);
                break;
            }
            neighbors = getNeighbors(x,y);
        }
    }

    private ArrayList<Integer> getNeighbors(int x, int y){
        ArrayList<Integer> neighbors = new ArrayList();
        if(x != 1){
            if(grid[x-2][y] == 1){
                neighbors.add(1);
            }
        }
        if(y != 1){
            if(grid[x][y-2] == 1){
                neighbors.add(2);
            }
        }
        if(x != width*2-1){
            if(grid[x+2][y] == 1){
                neighbors.add(3);
            }
        }        
        if(y != height*2-1){
            if(grid[x][y+2] == 1){
                neighbors.add(4);
            }
        }
        return neighbors;
    }
}
