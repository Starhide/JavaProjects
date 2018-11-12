import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Finds all paths from point s to all other points
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class DepthFirstSearch
{
    private Graph graph;
    private int start;
    private int count;

    /**
     * Contains all paths from s to all other points
     */
    private ArrayList<LinkedList<Integer>> paths;

    /**
     * Constructor for objects of class DepthFirstSearch
     */
    public DepthFirstSearch(Graph g, int s)
    {
        graph = g;
        start = s;
        paths = new ArrayList<LinkedList<Integer>>();
        dfs(s, new LinkedList<Integer>());
        paths.remove(paths.size()-1);
    }

    private void dfs(int pos, LinkedList<Integer> list){
        
        list.addLast(pos);
        Iterator<Integer> it = graph.adj(pos).iterator();
        while(it.hasNext()){
            Integer next = it.next();
            if(!list.contains(next)){
                dfs(next, new LinkedList<Integer>(list));
            }
        }
        paths.add(list);
    }

    public ArrayList<LinkedList<Integer>> getPaths(){
        return paths;
    }
    
    public boolean isMarked(int v) {
        return paths.stream().anyMatch(list -> list.contains(v)); //Java 8 ftw
    }
    
    public int count() {
        HashSet count = new HashSet<Integer>();
        paths.stream().forEach(p -> p.stream().forEach(k -> count.add(k)));
        return count.size();
    }
}
