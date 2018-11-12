import java.io.FileInputStream;

/**
 * Write a description of class CellGraph here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class CellGraph
{
    private CellGraphGui gui;
    private Graph graph;

    /**
     * Constructor for objects of class CellGraph
     */
    public CellGraph()
    {
        try{
            graph = new Graph(new FileInputStream("tinyG.txt"));
        }catch(Exception e){

        }
        gui = new CellGraphGui(graph);
    }

    public static void main(String args[]){
        new CellGraph();
    }
}
