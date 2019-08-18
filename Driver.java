//CS510 HW1
//@author Weichao Xi
//Solves sliding brick puzzles using various search strategies

import java.io.*;

//main class that runs the program
public class Driver {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        PrintStream fileStream = new PrintStream("output-hw1.txt");
        System.setOut(fileStream);
        GameState rw = new GameState("SBP-level0.txt");
        GameState b = new GameState("SBP-level1.txt");
        GameState d = new GameState("SBP-level1.txt");
        GameState i = new GameState("SBP-level1.txt");
        GameState a = new GameState("SBP-level1.txt"); 
        
        System.out.println("Random Walk");
        Move.randomWalk(rw, 3);
        System.out.println();
     
        Bfs.bfs(b); //breadth first search
        Dfs.dfs(d); //depth first search
        Ids.ids(i); //iterative deepening search
        AStar.aStar(a); //A* seach
    }
    
}
