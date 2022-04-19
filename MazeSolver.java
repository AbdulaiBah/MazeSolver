import java.util.ArrayList;
/**
 * MazeSolver.java
 * Program to solve an inputted maze using either a stack or queue
 * Part of Homework 5
 */
class MazeSolver{
	Agenda agenda;

	/**
	 * Passes in the agenda that will be used to solve the maze
	 * @param a agenda of locations
	 */
	public MazeSolver(Agenda a){
		agenda = a;
	}

	/**
	 * This method takes a Maze and determines whether it has a solution using either stack or queue
	 * @param m the maze that is being solved
	 * @param mg mazeGUI
	 * @return returns the correct path to solve the maze
	 */
	public ArrayList<MazeGridLocation> solveMaze(Maze m, MazeGUI mg){	
		
		ArrayList<MazeGridLocation> correctPath = new ArrayList<MazeGridLocation>();
		ArrayList<MazeGridLocation> backwardsPath = new ArrayList<MazeGridLocation>();
		
		int rows = m.getNumRows();
		int cols = m.getNumColumns();
		MazeGridLocation currLoc = m.getStartLocation();
		boolean goal = false;
		boolean complete = false;

		MazeGridLocation[][] maze = new MazeGridLocation[rows][cols];
		agenda.addLocation(currLoc);

		while(!goal){
			m.markVisited(currLoc);
			mg.addLocToAgenda(currLoc);
			mg.visitLoc(currLoc);
			agenda.removeLocation();

			//cheks to see if the current location is the final goal of the maze, if not adds it to path
			if(currLoc.getCharacter()!= '*'){ 
				for (MazeGridLocation loc: m.getOpenNeighbors(currLoc)){ 
					if (!loc.isWall() && !m.isVisited(loc) && (cols >= loc.getColumn()) && (rows >= loc.getRow())){ 
						agenda.addLocation(loc);
						mg.addLocToPath(currLoc);
						maze[loc.row][loc.col] = currLoc; 
					}
				}
				currLoc = agenda.getLocation();
			}
			else {
				goal = true; //once all paths to goal have been found returns true
			}
		}
		
		int temp = 0;
		int currRow = currLoc.row;
		int currCol = currLoc.col;
		
		backwardsPath.add(currLoc);
		while(!complete){
			backwardsPath.add(maze[currRow][currCol]);
			if (currRow == m.getStartLocation().row && currCol == m.getStartLocation().col) {
				complete = true;
			} else {
				temp = currRow;
				currRow = maze[currRow][currCol].row;
				currCol = maze[temp][currCol].col;
			}
		}
		
		backwardsPath.remove(backwardsPath.size()-1);
		for(int i = 0; i < backwardsPath.size(); i++){
			correctPath.add(backwardsPath.get(i));
		}

		return correctPath; //returns all grid spaces traveresed to reach goal
	}
}