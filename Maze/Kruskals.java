import java.util.ArrayList;
/**
 * Kruskal's randomized spanning tree
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class Kruskals extends MazeAlgorithm
{
    private int[] vertices;
    private ArrayList<Edge> edges;

    /**
     * Constructor for objects of class Kruskals
     * 
     * @param siz Size of the maze
     * @param seed Seed for the random
     */
    public Kruskals(int width, int height, String seed, MazeGUI maze) throws LargeSizeException, SmallSizeException
    {
        super(width, height, seed,maze);
    }

    public void generate(){
        vertices = new int[width*height];
        edges = new ArrayList<>();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                vertices[x+y*width] = x+y*width;
                if(x != width-1)
                    edges.add(new Edge(x+y*width,x+y*width+1,rand.nextInt(10)));
                if(y != height-1)
                    edges.add(new Edge(x+y*width,x+y*width+width,rand.nextInt(10)));
                //grid[x*2+1][y*2+1] = 0;
            }
        }

        for(int count = 0; count < 9; count++){
            for(int index = 0; index < edges.size(); index++){
                if(vertices[edges.get(index).getFirst()] == vertices[edges.get(index).getSecond()]){
                    edges.remove(index);
                    index--;
                } else if(edges.get(index).getWeight() == count){
                    grid[edges.get(index).getSecond()%width*2+1][edges.get(index).getSecond()/width*2+1] = 0;
                    grid[edges.get(index).getFirst()%width*2+1][edges.get(index).getFirst()/width*2+1] = 0;
                    grid[((edges.get(index).getSecond()%width)-(edges.get(index).getFirst()%width))+(edges.get(index).getFirst()%width)*2+1]
                    [((edges.get(index).getSecond()/width)-(edges.get(index).getFirst()/width))+(edges.get(index).getFirst()/width)*2+1] = 0;

                    int oldSet = vertices[edges.get(index).getSecond()];
                    int newSet = vertices[edges.get(index).getFirst()];
                    for(int x = 0; x < width*height; x++){
                        if(vertices[x] == oldSet)
                            vertices[x] = newSet;
                    }

                    edges.remove(index);
                    index--;

                    if(mazegui.realTime.getState())
                        mazegui.renderMaze();
                }
            }
        }
    }
}
