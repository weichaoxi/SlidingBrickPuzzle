import java.util.Comparator;

//A* selects those with lowest h(f), and this comparator
//allows a priority queue to order by h(f) 
public class NodeComparator implements Comparator<Node> {
    
    @Override
    public int compare(Node a, Node b) {
        return Double.compare(a.heuristic + a.depth, b.heuristic + b.depth);
    }
}
