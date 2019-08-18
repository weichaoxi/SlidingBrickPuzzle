import java.util.*;

public class AStar {
    
    private static PriorityQueue<Node> open; //nodes that need to be expanded
    private static ArrayList<int[][]> closed; //nodes that have been expanded
    
    public static void aStar(GameState g) {
        long startTime  = System.currentTimeMillis();
        int nodesExplored = 0;
        //priority queue to determine which node to expand next
        open = new PriorityQueue<>(new NodeComparator());
        
        closed = new ArrayList<>();
        Node start = new Node(g, 0, null, null);
        open.add(start);
      
        while(!open.isEmpty()) {
            Node next = open.remove();
            nodesExplored++;
            next.gameState.normalize();
           
            if(next.gameState.isSolved()) {
                long endTime  = System.currentTimeMillis();
                double totalTime = (double)(endTime - startTime)/1000;
                Stack<String> messages = new Stack<>();
                Node solution = next; //copy solution state for displaying
                   
                System.out.println();
                System.out.println("A* Search");
                while(next.parent != null) { //this puts all the moves from beginning to end in reverse order
                    messages.push("(" + next.moveToReach.piece + ", " + next.moveToReach.direction + ")");
                    next = next.parent;
                }
                while(!messages.isEmpty()) { //pop everything in stack to print list of moves to solve state
                    System.out.println(messages.pop());
                }
                solution.gameState.printState();
                System.out.println();
                System.out.println("#" + nodesExplored + " " + totalTime + " " + solution.depth);
                return;
            }
            boolean alreadyIn = false;
            
            //check is current state has already been visited and known to not be solved
            for(int[][] a : closed) { 
                if(Arrays.deepEquals(next.gameState.state, a))alreadyIn = true;
            }
            if(!alreadyIn) {
                closed.add(next.gameState.state);
                for(Move m : Move.getAllMoves(next.gameState)) {
                    GameState child = Move.applyMoveCloning(next.gameState, m);
                    child.normalize();
                    boolean exists = false;
                    for(int[][] e : closed) {
                        if(Arrays.deepEquals(e, child.state)) exists = true;
                    }
                    if(!exists) {
                        Node c = new Node(child, next.depth+1, next, m);
                        open.add(c); //h(s) is computed when the node is constructed
                        //when the node is added to priority que, h(s) + depth determines
                        //where in the queue it is placed, with smaller totals going to the front
                }
            }
            }
            
        }
    }
}
