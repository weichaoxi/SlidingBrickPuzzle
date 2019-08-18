import java.util.*;

public class Bfs {
    
    //vns stands for visited not solved
    //it stores states discovered while searching that do not represent solved states
    //this info is used to avoid searching states that are already verified to not be solved
    //and thus avoid getting stuck in loops
    private static ArrayList<int[][]> vns;
    
    public static void bfs(GameState g) {
        long startTime  = System.currentTimeMillis();
        vns = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>(); //queue to store states that can be reached
        int nodesExplored = 1;
        Node start = new Node(g, 0, null, null);
        
        start.gameState.normalize();
        
        if(start.gameState.isSolved()) {
            System.out.println("#" + nodesExplored + " " + "0" + " " + start.depth);
        }
        else {
            vns.add(start.gameState.state);
            for(Move m : Move.getAllMoves(start.gameState)) {
                GameState childState = Move.applyMoveCloning(start.gameState, m);
                Node child = new Node(childState, start.depth + 1, start, m);
                
                //add nodes that represent the states reached after moves
                queue.add(child);
            }
            //at this point, the queue only contains the states accessible from 
            //the initial state in the next move
            while(!queue.isEmpty()) {
                //check if the next state in queue is already known to be not solved
                Node next = queue.remove();
                nodesExplored++;
                next.gameState.normalize();
                if(next.gameState.isSolved()) {
                    long endTime  = System.currentTimeMillis();
                    double totalTime = (double)(endTime - startTime)/1000;
                    Stack<String> messages = new Stack<>();
                    Node solution = next; //copy solution state for displaying
                   
                    System.out.println();
                    System.out.println("Breadth First Search");
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
                boolean alreadyExists = false;
                for(int[][] notSolved : vns) {
                    if(Arrays.deepEquals(next.gameState.state, notSolved)) alreadyExists = true;
                }
                if(!alreadyExists) { //add the new unsolved state to array so alg knows not to process it again
                    vns.add(next.gameState.state);
                    //System.out.println();
                    //System.out.println(); //for testing purposes
                    //next.gameState.printState();
                    for(Move m : Move.getAllMoves(next.gameState)) {
                        GameState child = Move.applyMoveCloning(next.gameState, m);
                        child.normalize();
                        boolean exists = false;
                        for(int[][] notSolved : vns) { //are any children states of current state known to be not solved?
                            if(Arrays.deepEquals(child.state, notSolved)) exists = true;
                        }
                        if(!exists) {
                            Node c = new Node(child, next.depth + 1, next, m);
                            //add nodes that represent the states reached after moves
                            queue.add(c);
                        }
                    }
                }
            }
        }
    }
}
