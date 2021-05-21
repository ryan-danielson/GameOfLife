/**
 * This is the starting point for the Game of Life. 
 * This follows the standard "rules" for the game as 
 * described by Conway.
 */

public class GameOfLife {
	 // declaration for the game of life board
    private int[][] board;
    
    // contains coordinates for birthing cells
    private int[] birth = new int[1024];
    
    // contains coordinates for dying cells
    private int[] death = new int[1024];
    
    /**
     * The value indicating a dead cell
     */
    public static final int DEAD = 0;
    /**
     * The value indicating a live cell
     */
    public static final int ALIVE = 1;
    /**
     * The size of the board, one side
     */
    public static final int BOARD_SIZE = 19;
    
    /**
     * The constructor will instantiate the board.
     * Initially all the cells will be dead. The 
     * working size of the board is 19x19, a nod to 
     * the Go board that Conway used when he was 
     * designing the Game of Life.
     */
    public GameOfLife() {
        // the board is "too big" since the row and
        // column values start at 1.
        board = new int[BOARD_SIZE+2][BOARD_SIZE+2];
    }
    
    /**
     * Sets the value in one cell of the board to ALIVE
     * @param row The row of the cell to set
     * @param col The column of the cell to set
     */
    public void setAlive(int row, int col) {
        board[row][col] = ALIVE;
    }
    
    /**
     * Sets the value in one cell of the board to DEAD.
     * @param row The row of the cell to set
     * @param col The column of the cell to set
     */
    public void setDead(int row, int col) {
        board[row][col] = DEAD;
    }
    
    /**
     * This method retrieves the value of one cell in the board.
     * @param row The row of the cell to get
     * @param col The column of the cell to get
     * @return The value of the indicated cell
     */
    public int getState(int row, int col) {
        return board[row][col];
    }
    
    /**
     *      . . . O . . . . . . . . O O . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . O O . . . . . . . . . . .
     *      . . . . . O . . O . . . . . . . . . .
     *      . . . . . . O O . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . . . . . . . O O O . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . O . . . . . . . . . . . . .
     *      . . . O . O . . . . . . . . . . . . .
     *      . . . . O O . . . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     *      . . . . . . . . . . . . . . . . . . .
     */

    public void initializeBoard() {
        // exit if an extremely small board
        if(BOARD_SIZE < 4) return;
        // two alive cells, they will die
        setAlive(3, 4);
        setAlive(4, 4);
        // exit if a very small board
        if(BOARD_SIZE < 9) return;
        // a beehive
        setAlive(8, 7);
        setAlive(8, 8);
        setAlive(9, 6);
        setAlive(9, 9);
        setAlive(10, 7);
        setAlive(10, 8);
        // exit if a small board
        if(BOARD_SIZE < 14) return;
        // a block
        setAlive(3, 13);
        setAlive(3, 14);
        setAlive(3, 15);
        setAlive(4, 13);
        setAlive(4, 14);
        setAlive(4, 15);
        setAlive(5, 13);
        setAlive(5, 14);
        setAlive(5, 15);
        // a blinker
        setAlive(13, 13);
        setAlive(13, 14);
        setAlive(13, 15);
        // a glider
        setAlive(15, 6);
        setAlive(16, 4);
        setAlive(16, 6);
        setAlive(17, 5);
        setAlive(17, 6);
    }
    
    /**
     * Clear all the cells, set them to DEAD.
     */
    public void clear() {
    	for(int row = 0; row < board.length; row++){
    		for(int column = 0; column < board[0].length; column++){
    			setDead(row,column);
    		}
    	}
    }
    
    /**
     * This method will print out the current board.
     */
    public void printBoard() {
        System.out.println(toString());
    }
    
    /**
     * This method updates the current board for the
     * next generation. 
     * <p>Note: Do <b>not</b> instantiate an array 
     * within this method.
     */
    public void nextGeneration() {
    	
    	// discover the current state of cell, check its surroundings
    	// if a dead cell has three neighbors, a new cell is born in the middle
    	// if an alive cell has 2-3 neighbors, nothing changes
    	// if a cell has 0-1 || >=4 neighbors, it dies from loneliness/overcrowd
    	
    	// iterate through board, check values
    	int alive = 0;
    	int birthAcc = 0;
    	int deathAcc = 0;    	
    	
    	for(int row = 0; row < board.length-1; row++) {
    		for(int column = 0; column < board[0].length-1; column++) {
    			
				if(board[row][column] == 1)
					alive--;	// we do this since the check algorithm includes the home cell, if it's
								// alive we don't want to include it in the count
				
    			// check surroundings to see what happens next. loop boundaries depend on current cell.
				
				if (column == 0) {
					for (int checkRow = 0; checkRow < row+2; checkRow++) {
    					for (int checkColumn = column; checkColumn < column+2; checkColumn++) {
    						if (board[checkRow][checkColumn] == 1)
    							alive++;
    					}
					}
				} else if (column == board[column].length) {
					for (int checkRow = row-1; checkRow < row+1; checkRow++) {
    					for (int checkColumn = column-1; checkColumn < column+1; checkColumn++) {
    						if (board[checkRow][checkColumn] == 1)
    							alive++;
    					}
					}
				} else if (row == 0) {
    				for (int checkRow = row; checkRow < row+2; checkRow++) {
    					for (int checkColumn = column-1; checkColumn < column+2; checkColumn++) {
    						if (board[checkRow][checkColumn] == 1)
    							alive++;
    					}    					
    				}
				} else if (row == board[row].length) {
					for (int checkRow = row-1; checkRow < row+1; checkRow++) {
    					for (int checkColumn = column-1; checkColumn < column+2; checkColumn++) {
    						if (board[checkRow][checkColumn] == 1)
    							alive++;
    					}
					}
				} else {
					for (int checkRow = row-1; checkRow < row+2; checkRow++ ) {
    					for (int checkColumn = column-1; checkColumn < column+2; checkColumn++) {
    						if (board[checkRow][checkColumn] == 1)
    							alive++;
    					}
					}
				}
    				
    			if (alive < 2 || alive > 4 && board[row][column] == 1) {
    				//kill cell from loneliness or overcrowding
    				death[deathAcc] = row;
    				death[deathAcc+1] = column; 
    				deathAcc+=2;
    				alive = 0; //reset counter
    					
    			} else if (alive == 3 && board[row][column] == 0) {
    				//birth cell at board[row][column]    					
    				birth[birthAcc] = row;
    				birth[birthAcc+1] = column;
    				birthAcc+=2;
   					alive = 0; // reset counter
    			}
    		}
    	}
    	
    	// populate board with updated state
    	
    	for (int i = 0; i < birth.length; i+=2)
    		setAlive(birth[i], birth[i+1]);

    	for(int i = 0; i < death.length; i+=2)
    		setDead(death[i], death[i+1]);
    }
    
    public String toString(){
    	// Create new string to hold board state as string
    	String boardAsString = new String();
    	// iterate through array to find alive and dead cells,
    	// concatenate with results.
    	for (int row = 0; row < board.length; row++) {
    		for (int column = 0; column < board[0].length; column++) {
    			if (board[row][column] == 1)
    				boardAsString += "0 ";
    			else
    				boardAsString += ". ";
    		}
    		boardAsString += "\n";
    	}    	
    	return boardAsString;
    }
    
    /**
     * The application method, instantiates a
     * GameOfLife object and then calls the above
     * methods.
     */
    public static void main(String[] args) {
        GameOfLife gol = new GameOfLife();
        gol.initializeBoard();
        System.out.println("Initial state, Generation 0:");
        gol.printBoard();
        for (int i = 0; i < 5; i ++) {
            gol.nextGeneration();
            System.out.println("\nGeneration " + (i + 1) + ":");
            gol.printBoard();
        }
    }
}
