
public class Node {
    GameState gameState;
    int depth;
    Node parent; //parent node state --> moveToReach --> Node representing this state
    Move moveToReach; //the move that reached this node, used to trace path to solution
    double heuristic; //estimated distance to the goal
    
    public Node(GameState g, int d, Node n, Move m) {
        gameState = g;
        depth = d;
        parent = n;
        moveToReach = m;
        heuristic = computeHeuristic();
    }
    
    //the heuristic is the euclidean distance between -1 
    //and the 2 that is closer to the goal (as represented on state matrix)
    public final double computeHeuristic(){
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        
        label1:
            for(x1 = 0; x1 < gameState.height; x1++) {
            
                for(y1 = 0; y1 < gameState.width; y1++) {
                    if(gameState.state[x1][y1] == 2) break label1;
                }
            } //get the array index of the 2 closest to goal
        
        label2:
            for(x2 = 0; x2 < gameState.height; x2++) {
            
                for(y2 = 0; y2 < gameState.width; y2++) {
                    if(gameState.state[x2][y2] == -1) break label2;
                }
            } //find array index of goal
        //System.out.println((double) Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1))); //testing
        double h = (double) Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
        return h;
    }
}
