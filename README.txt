CS510 HW1
@author Weichao Xi

=========TO RUN THE PROGRAM=============
1) Ensure the following files are in the same directory
	makefile
	SBP-level0.txt
	SBP-level1.txt
	AStar.java
	Bfs.java
	Dfs.java
	Driver.java     <---- this is the class with the main method
	GameState.java
	Ids.java
	Move.java
	NodeComparator.java
	Node.java

2) type 'make' and hit enter. This:
	-compiles all the java files
	-creates an executable jar file named hw1 in the same directory (this is the executable produced)
	-runs the jar file
	-prints the output to a file "output-hw1.txt" which now appears in the same directory
	
3) you can examine the program output in "output-hw1.txt", which should now be there in the same directory
(use cat output-hw1.txt)

you can remove the .*class files with 'make clean'

I am using both my late days on this assignment.

=======PROGRAM DESCRIPTION========
The java programs above are used to solve sliding brick puzzles. The board 
of a sliding brick puzzle is represented by a two-dimensional array, 
whose initial state is described in text files such as "SBP-level0.txt" and "SBP-level1.txt"

A description of each class and their purpose is as follows:

AStar.java - Solves an initial state using the A* algorithm, taking
an initial gamestate as a parameter. This class utilizes a priority 
queue to determine which node to expand next. The use of a priority queue allows the function
to visit the node with the smallest function value as computed by f = g + h.

Bfs.java - Solves an initial state using breadth first search, taking an initial
gamestate as a parameter, and, if a solution is found, it outputs the final solved 
state, and the moves performed to reach that state. 

Dfs.java - Solves an initial state using depth first search, taking an initial gamestate
as a parameter, and, if a solution is found, it outputs the final solved 
state, and the moves performed to reach that state.

Driver.java - The main method that performs the acutal searching. It performs
random walks on level0 and solves level1 using each of depth first search,
breadth first search, iterative deepening, and A*.   

GameState.java - Represents a gamestate (board), including after making a move
A constructor takes a text file and adds the state information described in that
text file to form the state matrix (represented by 2D array), which represents
the initial game state.

Ids.java - Solves an initial state using iterative deepening, taking an initial gamestate
as a parameter, and, if a solution is found, it outputs the final solved 
state, and the moves performed to reach that state
Move.java

NodeComparator.java - This will order the position of nodes in a priority queue (for A*)
based on the sum of the depth and heuristic. It is only needed for A* search, and not
the others.

Node.java - Represents a node on the search graph, with each node representing a state. A node's 
parent is the node carrying the state-move pair that reached that node. Used for traversing
the graph while searching, and tracing the path to the goal by tracing back parents. 