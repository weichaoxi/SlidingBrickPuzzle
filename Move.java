import java.util.*;

//represents one move from one piece
public class Move {
    int piece;
    String direction;

    public Move(int p, String d) {
        piece = p;
        direction = d;
    }
    
    //the idea is that a piece can move up if for the topmost numbers
    //representing the piece, the number directly above is 0 (or -1 for master block)
    //for the same numbers below the bottom numbers, the above number needs to be that number
    //the same idea can be applied to moving in other directions
    public static boolean canMoveUp(GameState g, int piece) {
        //find array indices with value equal to piece
        //first try to find the piece on the board
        boolean pieceExists = false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) pieceExists = true;
            }
        }
        if(!pieceExists) return false;
        boolean canGoUp = true;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    if(piece==2) { //master block can move onto -1 whereas other blocks cannot
                        if(g.state[i-1][j] != -1 && g.state[i-1][j] != 0 && g.state[i-1][j] != piece) canGoUp = false;
                    }
                    else {
                        //the above entry can be equal to the piece because for large blocks, part
                        //of a piece might replace another part of the piece's position when moving
                        if(g.state[i-1][j] != 0 && g.state[i-1][j] != piece) canGoUp = false;
                    }
                }
            }
        }
        return canGoUp;
    }
    
    public static boolean canMoveDown(GameState g, int piece) {
        //find array indices with value equal to piece
        boolean canGoDown = true;
        boolean pieceExists = false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) pieceExists = true;
            }
        }
        if(!pieceExists) return false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    if(piece==2) { //master block can move onto -1 whereas other blocks cannot
                        if(g.state[i+1][j] != -1 && g.state[i+1][j] != 0 && g.state[i+1][j] != piece) canGoDown = false;
                    }
                    else {
                        //the above entry can be equal to the piece because for large blocks, part
                        //of a piece might replace another part of the piece's position when moving
                        if(g.state[i+1][j] != 0 && g.state[i+1][j] != piece) canGoDown = false;
                    }
                }
            }
        }
        return canGoDown;
    }
    
    public static boolean canMoveLeft(GameState g, int piece) {
        boolean canGoLeft = true;
        boolean pieceExists = false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) pieceExists = true;
            }
        }
        if(!pieceExists) return false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    if(piece==2) { //master block can move onto -1 whereas other blocks cannot
                        if(g.state[i][j-1] != -1 && g.state[i][j-1] != 0 && g.state[i][j-1] != piece) canGoLeft = false;
                    }
                    else {
                        //the above entry can be equal to the piece because for large blocks, part
                        //of a piece might replace another part of the piece's position when moving
                        if(g.state[i][j-1] != 0 && g.state[i][j-1] != piece) canGoLeft = false;
                    }
                }
            }
        }
        return canGoLeft;
    }
     
    public static boolean canMoveRight(GameState g, int piece) {
        boolean canGoRight = true;
        boolean pieceExists = false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) pieceExists = true;
            }
        }
        if(!pieceExists) return false;
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    if(piece==2) { //master block can move onto -1 whereas other blocks cannot
                        if(g.state[i][j+1] != -1 && g.state[i][j+1] != 0 && g.state[i][j+1] != piece) canGoRight = false;
                    }
                    else {
                        if(g.state[i][j+1] != 0 && g.state[i][j+1] != piece) canGoRight = false;
                    }
                }
            }
        }
        return canGoRight;
    }
    
    //returns list of moves a piece can perform
    public static List<Move> getPieceMoves(GameState g, int piece) {
        ArrayList<Move> moves = new ArrayList<>(); //change to stack/queue for searches?
        if(canMoveUp(g, piece)) moves.add(new Move(piece, "up"));
        if(canMoveDown(g, piece)) moves.add(new Move(piece, "down"));
        if(canMoveLeft(g, piece)) moves.add(new Move(piece, "left"));
        if(canMoveRight(g, piece)) moves.add(new Move(piece, "right"));
        return moves;
    }
    
    public static List<Move> getAllMoves(GameState g) {
        ArrayList<Move> allMoves = new ArrayList<>();
            for(int p = 2; p <= 9; p++) {
            List<Move> moves;
            moves = getPieceMoves(g, p);
            allMoves.addAll(moves);
            //moves = new ArrayList<Move>();
        }
        return allMoves;
    }
    
    public static void moveUp(GameState g, int piece) {
        for(int i = 0; i < g.height; i++) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    swap(g.state, i, j, i-1, j);
                    
                    //if -1 gets swapped that means master block arrived at exit
                    if(g.state[i][j] == -1) g.state[i][j] = 0;
                }
            }
        }
    }
    
    public static void moveDown(GameState g, int piece) {
        for(int i = g.height-1; i >= 0 ; i--) {
            for(int j = 0; j < g.width; j++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    swap(g.state, i, j, i+1, j);
                    if(g.state[i][j] == -1) g.state[i][j] = 0;
                }
            }
        }
    }
    
    public static void moveLeft(GameState g, int piece) {
        for(int j = 0; j < g.width; j++) {
            for(int i = 0; i < g.height; i++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    swap(g.state, i, j, i, j-1);
                    if(g.state[i][j] == -1) g.state[i][j] = 0;
                }
            }
        }
    }
    
    public static void moveRight(GameState g, int piece) {
        for(int j = g.width-1; j >= 0; j--) {
            for(int i = 0; i < g.height; i++) {
                if(g.state[i][j] == piece) { //found the piece, or part of the piece
                    swap(g.state, i, j, i, j+1);
                    if(g.state[i][j] == -1) g.state[i][j] = 0;
                }
            }
        }
    }
    
    public static void applyMove(GameState g, Move m) {
        switch (m.direction) {
            case "up":
                moveUp(g, m.piece);
                break;
            case "down":
                moveDown(g, m.piece);
                break;
            case "left":
                moveLeft(g, m.piece);
                break;
            case "right":
                moveRight(g, m.piece);
                break;
            default:
                System.out.println(m.piece + " was chosen but did not make any moves."); //for debugging
                break;
        }
    }
    
    public static GameState applyMoveCloning(GameState g, Move m) {
        GameState clone = g.cloneState();
        applyMove(clone, m);
        return clone;
    }
    
    public static void randomWalk(GameState g, int n) {
        Random r = new Random();
        g.printState();
        for(int count = 0; count < n; count++) {
            List<Move> moves = getAllMoves(g);
            Move chosen = moves.get(r.nextInt(moves.size()));
            System.out.println();
            System.out.println();
            System.out.println("(" + chosen.piece + "," + chosen.direction + ")" + "\n");
            applyMove(g, chosen);
            g.normalize();
            g.printState();
            if(g.isSolved()) return;
        }
    }
    
    //swap two elements in matrix
    public static void swap(int arr[][], int a1, int a2, int b1, int b2) {
        int temp = arr[a1][a2];
        arr[a1][a2] = arr[b1][b2];
        arr[b1][b2] = temp;
    }
}
