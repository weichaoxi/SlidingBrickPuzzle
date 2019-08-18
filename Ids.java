import java.util.*;

public class Ids {
    
    private static ArrayList<int[][]> vns;
    private static long startTime;
    private static boolean solutionFound;
    private static int totalNodesReached; //note this counts repeats as 
    //a characteristic of iterative deepening search is that it revisists
    //previously visited nodes in each call of limited dfs
    
    public static void ids(GameState g) {
        totalNodesReached = 0;
        solutionFound = false;
        startTime  = System.currentTimeMillis();
        for(int i = 0; i < Integer.MAX_VALUE; i++) { //algorithm as described in textbook
            dfsLimited(g, i);
            if(solutionFound) return;
        }
        
    }
    
    public static void dfsLimited(GameState g, int depthLimit) {
        vns = new ArrayList<>();
        Stack<Node> stack = new Stack<>(); //stack to store states that can be reached
        totalNodesReached++;
        Node start = new Node(g, 0, null, null);
        start.gameState.normalize();
        
        if(start.gameState.isSolved()) {
            System.out.println("#" + totalNodesReached + " " + "0" + " " + start.depth);
        }
        else {
            vns.add(start.gameState.state);
            for(Move m : Move.getAllMoves(start.gameState)) {
                GameState childState = Move.applyMoveCloning(start.gameState, m);
                Node child = new Node(childState, start.depth + 1, start, m);
                
                //add nodes that represent the states reached after moves
                stack.push(child);
            }
            //at this point, the queue only contains the states accessible from 
            //the initial state in the next move
            while(!stack.isEmpty() && stack.peek().depth <= depthLimit) {
                //check if the next state in queue is already known to be not solved
                Node next = stack.pop();
                totalNodesReached++;
                next.gameState.normalize();
                if(next.gameState.isSolved()) {
                    solutionFound = true;
                    long endTime  = System.currentTimeMillis();
                    double totalTime = (double)(endTime - startTime)/1000;
                    Stack<String> messages = new Stack<>();
                    Node solution = next; //copy solution state for displaying
                   
                    System.out.println();
                    System.out.println("Iterative Deepening Search");
                    while(next.parent != null) { //this puts all the moves from beginning to end in reverse order
                        messages.push("(" + next.moveToReach.piece + ", " + next.moveToReach.direction + ")");
                        next = next.parent;
                    }
                    while(!messages.isEmpty()) { //pop everything in stack to print list of moves to solve state
                        System.out.println(messages.pop());
                    }
                    solution.gameState.printState();
                    System.out.println();
                    System.out.println("#" + totalNodesReached + " " + totalTime + " " + solution.depth);
                    return;
                }
                boolean alreadyExists = false;
                for(int[][] notSolved : vns) {
                    if(Arrays.deepEquals(next.gameState.state, notSolved)) alreadyExists = true;
                }
                if(!alreadyExists) { //add the new unsolved state to array so alg knows not to process it again
                    vns.add(next.gameState.state);
                    
                    for(Move m : Move.getAllMoves(next.gameState)) {
                        GameState child = Move.applyMoveCloning(next.gameState, m);
                        child.normalize();
                        boolean exists = false;
                        for(int[][] notSolved : vns) {
                            if(Arrays.deepEquals(child.state, notSolved)) exists = true;
                        }
                        if(!exists) {
                            Node c = new Node(child, next.depth + 1, next, m);
                            //add nodes that represent the states reached after moves
                            if(c.depth <= depthLimit) stack.push(c); //dont push a child node onto stack if its depth is over limit
                            
                        }
                    }
                }
            }
        }
    }
}
