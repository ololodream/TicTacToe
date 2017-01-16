/**
 * HumanPlayer, get next move from input
 * @author miya_
 *
 */
public class HumanPlayer extends Player {

	private static final long serialVersionUID = 1L;

	public HumanPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HumanPlayer(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	public HumanPlayer(String userName, String familyName, String givenName) {
		super(userName, familyName, givenName);
		// TODO Auto-generated constructor stub
	}

	public HumanPlayer(String username) {
		super(username);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * get next move from input
	 */
	public Move makeMove(char[][] grid) {
		//
		// TODO Auto-generated method stub
		int row = TicTacToe.keyboard.nextInt();
		int col = TicTacToe.keyboard.nextInt();

		return new Move(row, col);
	}

}
