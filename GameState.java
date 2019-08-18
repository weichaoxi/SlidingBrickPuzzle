import java.io.*;
import java.util.*;

public class GameState {
    protected int[][] state; //represents current game board
    protected int width; //width of game board
    protected int height; //height of game board
    
    public GameState() { }
    
    //Constructor that loads a game state to state matrix
    //@param file the name of a file 
    public GameState(String file) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        int[] currentLine;
        boolean firstLine = true;
        int currentRow = 0;
        
        while((line = in.readLine()) != null) {
            String[] a = line.split(","); //read the numbers from file, ignoring commas
            currentLine = new int[a.length]; //convert file numbers to ints
            for(int i = 0; i < a.length; i++) {
                currentLine[i] = Integer.parseInt(a[i]);
                //System.out.print(currentLine[i] + ","); //testing
            }
            if(!firstLine) {
                for(int i = 0; i < width; i++) {
                    state[currentRow][i] = currentLine[i];
                }
                currentRow++;
            }
            else {
                height = currentLine[1];
                width = currentLine[0];
                state = new int[height][width]; //create the state array if reading first line
                //System.out.println(height + " " + width); //verify matrix dimensions
                firstLine = false;
            }
        }
        in.close();
        
    }
    
    //outputs game state on the screen
    public void printState() {
        System.out.print(width + "," + height + ",");
        for(int i = 0; i < height; i++) {
            System.out.println();
            for(int j = 0; j < width; j++) {
                System.out.print(state[i][j] + ",");
            }
        }
    }
    
    //returns a separate GameState object identical to the original
    public GameState cloneState() {
        GameState clone = new GameState();
        clone.height = this.height;
        clone.width = this.width;
        clone.state = new int[clone.height][clone.width];
        for(int i = 0; i < clone.height; i++) {
            System.arraycopy(this.state[i], 0, clone.state[i], 0, clone.width);
        }
        return clone;
    }
    
    //checks if state represents solved game state
    public boolean isSolved() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(state[i][j] == -1) return false;
            }
        }
        return true;
    }
    
    //compares two states to see if they are identical
    public boolean equals(GameState other) {
        return Arrays.deepEquals(this.state, other.state);
    }
    
    public void normalize() {
        int nextIdx = 3;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if (state[i][j]==nextIdx) {
                    nextIdx++;
                } 
                else if (state[i][j] > nextIdx) {
                    swapIdx(nextIdx, state[i][j]);
                    nextIdx++;
                }
            }
        }
    }
    
    public void swapIdx(int idx1, int idx2) {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if (state[i][j] == idx1) {
                    state[i][j] = idx2;
                } 
                else if (state[i][j] == idx2) {
                    state[i][j]=idx1;
                }
            }
        }
    }
    
    
}

