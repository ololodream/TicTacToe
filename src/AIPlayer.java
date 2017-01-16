
public class AIPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AIPlayer(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	public AIPlayer(String userName, String familyName, String givenName) {
		super(userName, familyName, givenName);
		// TODO Auto-generated constructor stub
	}

	public AIPlayer(String username) {
		super(username);
		// TODO Auto-generated constructor stub
	}

	/**
	 * automatically generate next move, by scanning the game board from
	 * top-left to bottom-right row by row until an empty cell is found, and
	 * place a mark there.
	 * 
	 */
	@Override
	public Move makeMove(char[][] grid) {

		// TODO Auto-generated method stub
		int size = grid.length;
		int row, col;
		for (row = 0; row < size; row++)
			for (col = 0; col < size; col++)
				if (grid[row][col] == GameManager.MARK_EMPTY)
					return new Move(row, col);
		return null;
	}

}
