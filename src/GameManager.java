
public class GameManager {

	/* Layout of grid */
	public final static char DELIMITER_GAP = '|';
	public final static String DELIMITER_LINE = "\n-----\n";
	public final static char MARK_EMPTY = ' ';
	public final static char MARK_O = 'O';
	public final static char MARK_X = 'X';

	/* Scale of the grid */
	private final int SIZE = 3;
	private final int GRIDNUM = SIZE * SIZE;
	private final int WIN_CONDITION = SIZE;
	// Winning when one player has 3 marks in a sequence

	// private int[][] grid = new int[SIZE][SIZE];
	private char[][] grid = new char[SIZE][SIZE];
	/* Four cases of a game */
	private final static int O_WIN = 0;
	private final static int X_WIN = 1;
	private final static int CONTINUE = -1;
	private final static int DRAW = 2;

	private void updateStatistics(int gameState, Player playerO, Player playerX) {
		playerO.incriseGamePlayed();
		playerX.incriseGamePlayed();
		if (gameState == O_WIN) {
			playerO.incriseGameWon();
		}
		if (gameState == X_WIN) {
			playerX.incriseGameWon();
		}
		if (gameState == DRAW) {
			playerO.incriseGameDrawn();
			playerX.incriseGameDrawn();
		}

	}

	/**
	 * Initialize each grid cell
	 */
	private void intializeGrid() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++)
				// grid[i][j] = (char)-1;
				grid[i][j] = MARK_EMPTY;
		}
	}

	/**
	 * Play a game
	 */

	public void playGame(Player playerO, Player playerX) {

		int stepCount = 0;// count how many pieces have been placed

		intializeGrid();
		printGrid();
		

		// When a game unfinished, continue to play the game
		int gameState = CONTINUE;
		while (gameState == CONTINUE) {

			// Decide which player's turn
			System.out.println((stepCount % 2 == 0 ? playerO.getGivenName() : playerX.getGivenName()) + "'s move:");
			// Make move
			// System.out.println(playerO.getClass().toString());
			Move move = stepCount % 2 == 0 ? playerO.makeMove(grid) : playerX.makeMove(grid);
			// Move move= playerO.makemove(grid);
			if(move == null){
				System.out.println("move is empty");
			}
			int row = move.getRow();
			int col = move.getColumn();

			if (isValidMove(row, col)) {
				// grid[row][col] = (char) (stepCount % 2);// place piece
				grid[row][col] = stepCount % 2 == 0 ? MARK_O : MARK_X;
				stepCount++;
				printGrid();

				// Get current game state and show relevant information
				gameState = getGameState(row, col, stepCount);
				switch (gameState) {
				case CONTINUE: {
				}
					;
					break;
				case O_WIN: {
					System.out.println("Game over. " + playerO.getGivenName() + " won!");
				}
					;
					break;
				case X_WIN: {
					System.out.println("Game over. " + playerX.getGivenName() + " won!");
				}
					;
					break;
				case DRAW: {
					System.out.println("Game over. It was a draw!");
				}
					;
					break;
				}
			}

		}
		// when a single game finished update statistics
		updateStatistics(gameState, playerO, playerX);

	}

	private boolean isValidMove(int row, int col) {
		if (row > SIZE - 1 || col > SIZE - 1 || row < 0 || col < 0) {

			System.out.println("Invalid move. You must place at a cell within {0,1,2} {0,1,2}.");
			return false;
		}
		if (grid[row][col] != MARK_EMPTY) {
			System.out.println("Invalid move. The cell has been occupied.");
			return false;
		}
		return true;

	}

	/**
	 * Print out grids and pieces There is a gap between two grid cells There is
	 * a delimiter line between two lines
	 */
	private void printGrid() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == MARK_EMPTY) {
					System.out.print(MARK_EMPTY);
				} else {
					// Decide and show which mark it is
					System.out.print(grid[i][j] == MARK_O ? MARK_O : MARK_X);
				}
				if (j < SIZE - 1) {
					System.out.print(DELIMITER_GAP);
				}
			}
			if (i < SIZE - 1) {
				System.out.print(DELIMITER_LINE);
			}
		}
		System.out.println("");
	}

	/**
	 * Get game state, according to the placement of current piece
	 * 
	 * @param row
	 *            The row of current piece
	 * @param col
	 *            The column of current piece
	 * @param stepCount
	 *            Count how many pieces have been placed
	 * @return
	 */
	private int getGameState(int row, int col, int stepCount) {
		if (isWin(row, col)) {
			// If someone wins, return winners mark(=stepCount%2)
			return (stepCount+1)%2;
			//return grid[row][col];
		} else if (!isFull(stepCount)) {
			// If no one wins and the grid has not full, continue.
			return CONTINUE;
		} else
			// If grid is full and no one wins ,it is a draw
			return DRAW;
	}

	/**
	 * Detect whether someone wins ,according to four directions of current
	 * piece.
	 * 
	 * @param row
	 *            The row of current piece
	 * @param col
	 *            The column of current piece
	 * @return Return true when someone wins, otherwise return false
	 */
	private boolean isWin(int row, int col) {
		if (vertically(row, col) || horizontally(row, col) || diagonally(row, col) || antiDiagonally(row, col)) {
			return true;
		} else
			return false;
	}

	/**
	 * Detect whether grid is full.
	 * 
	 * @param stepCount
	 *            Count how many pieces have been placed
	 * @return Return true when gird is full, otherwise return false.
	 */
	private boolean isFull(int stepCount) {
		if (stepCount < GRIDNUM) {
			return false;
		}
		return true;
	}

	/**
	 * Judge whether one player has the number of WIN_CONDITION marks in a
	 * vertical sequence
	 * 
	 * @param row
	 *            The row of current piece
	 * @param col
	 *            The column of current piece
	 * @return Return true when there are the number of WIN_CONDITION marks in
	 *         current vertical line otherwise return false
	 */
	private boolean vertically(int row, int col) {

		for (int i = 0; i < WIN_CONDITION; i++) {
			if (grid[i][col] != grid[row][col]) {
				return false;
			}
		}
		return true;
	};

	/**
	 * Judge whether one player has the number of WIN_CONDITION marks in a
	 * horizontal sequence
	 * 
	 * @param row
	 *            The row of current piece
	 * @param col
	 *            The column of current piece
	 * @return Return true when there are the number of WIN_CONDITION marks in
	 *         current horizontal line otherwise return false
	 */
	private boolean horizontally(int row, int col) {

		for (int j = 0; j < WIN_CONDITION; j++) {
			if (grid[row][j] != grid[row][col]) {
				return false;
			}
		}
		return true;
	};

	/**
	 * Judge whether one player the number of WIN_CONDITION marks in a
	 * diagonally sequence
	 * 
	 * @param row
	 *            The row of current piece
	 * @param col
	 *            The column of current piece
	 * @return Return true when there are the number of WIN_CONDITION marks in
	 *         current diagonal line otherwise return false
	 */
	private boolean diagonally(int row, int col) {

		if (row != col) {
			return false;
		}
		for (int i = 0; i < WIN_CONDITION; i++) {
			// detect if every piece in the diagonal line is the same as current
			// piece
			if (grid[i][i] != grid[row][col]) {
				return false;
			}
		}
		return true;

	};

	/**
	 * Judge whether one player has the number of WIN_CONDITION marks in a
	 * anti-diagonally sequence
	 * 
	 * @param row
	 *            The row of current piec  e
	 * @param col
	 *            The column of current piece
	 * @return Return true when there are the number of WIN_CONDITION marks in
	 *         current anti-diagonal line otherwise return false
	 */
	private boolean antiDiagonally(int row, int col) {

		if (row + col != WIN_CONDITION - 1) {
			return false;
		}
		for (int i = 0; i < SIZE; i++) {
			if (grid[i][SIZE - 1 - i] != grid[row][col]) {
				return false;
			}
		}
		return true;
	};

}
