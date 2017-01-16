import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdvancedAIPlayer extends Player {

	private static final long serialVersionUID = 1L;
	
	// weight of decision
	final int willWin = 10000;
	final int voidLose = 1000;
	final int potientialWin = 100;
	final int potientialLose = 75;
	final int emptyLine = 10;

	public AdvancedAIPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvancedAIPlayer(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	public AdvancedAIPlayer(String userName, String familyName, String givenName) {
		super(userName, familyName, givenName);
		// TODO Auto-generated constructor stub
	}

	public AdvancedAIPlayer(String username) {
		super(username);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * make move by choosing the most largest weight cell
	 */
	public Move makeMove(char[][] gameBoard) {
		Move playerMove = null;
		int rowNumber;
		int colNumber;

		int stepCount = 0;
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard.length; col++) {
				if (gameBoard[row][col] != GameManager.MARK_EMPTY) {
					stepCount++;
				}
			}
		}
		int mark = stepCount % 2;
		int mid = gameBoard.length / 2;
		
		if (mark == 0) {
			// AI hold 'O' piece
			if (stepCount == 0) {
				// the fist step of game, choose corner
				playerMove = new Move(0, 0);
				return playerMove;
			}
			if (stepCount == 2) {
				if (gameBoard[mid][mid] != GameManager.MARK_O) {
					if (gameBoard[0][1] != GameManager.MARK_O && gameBoard[0][2] != GameManager.MARK_O) {
						playerMove = new Move(0, 2);
					} else {
						playerMove = new Move(2, 0);
					}
					return playerMove;
				}
			}

		}
		if (mark != 0) {
			// AI hold 'X' piece
			if (stepCount == 1) {
				// the X's first step of the game; choose the middle as long as it is empty
				if (gameBoard[mid][mid] == ' ') {
					playerMove = new Move(mid, mid);
				} else {
					playerMove = new Move(0, 0);
				}
				return playerMove;
			}

		}
		
		//calculate the weight of remaining cells 
		ArrayList<Cell> valueList = new ArrayList<Cell>();
		for (rowNumber = 0; rowNumber < gameBoard.length; rowNumber++) {
			for (colNumber = 0; colNumber < gameBoard.length; colNumber++) {
				if (gameBoard[rowNumber][colNumber] == GameManager.MARK_EMPTY) {
					valueList.add(new Cell(rowNumber, colNumber, 0));
				}
			}
		}
		valueOfMove(gameBoard, valueList);
		Comparator<Cell> comparator = new Comparator<Cell>() {
			public int compare(Cell n1, Cell n2) {
				return n2.getValue() - n1.getValue();
			}
		};
		
		Collections.sort(valueList, comparator);
		//choose the cell having largest weight
		playerMove = new Move(valueList.get(0).getRowNumber(), valueList.get(0).getColNumber());

		return playerMove;
	}
/**
 * calculate the weight of remaining cells 
 * @param gameBoard current game board
 * @param valueList value list of remaining cells
 */
	private void valueOfMove(char[][] gameBoard, ArrayList<Cell> valueList) {

		if (valueList.size() % 2 == 1) {
			for (Cell cell : valueList) {
				int value = calculateValue(gameBoard, cell.getRowNumber(), cell.getColNumber(), GameManager.MARK_O, GameManager.MARK_X);
				cell.setValue(value);
			}
		} else {
			// X
			for (Cell cell : valueList) {
				int value = calculateValue(gameBoard, cell.getRowNumber(), cell.getColNumber(), GameManager.MARK_X, GameManager.MARK_O);
				cell.setValue(value);
			}
		}

	}
	/**
	 * calculate value of a cell by following rules
	 * 1.detect whether put piece in this cell can lead to win
	 * 2.detect whether opponent put piece in this cell can lead to win
	 * 3.detect whether put piece in this cell can create a potential win situation,
	 * 			which means this piece will make up two lines having two current piece and a space
	 * 4.detect whether opponent put its piece in this cell can create a potential win situation,		
	 * 5.put piece in a empty line
	 * 
	 * @return value of this cell
	 */
	private int calculateValue(char[][] gameBoard, int row, int col, char mark, char diff) {
		int value = 0;
		char[][] cellMark = new char[3][3];

		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				cellMark[i][j] = gameBoard[i][j];
			}
		}

		cellMark[row][col] = mark;
		String[] lines = new String[8];
		lines[0] = String.valueOf(cellMark[0][0]) + "" + String.valueOf(cellMark[0][1]) + ""
				+ String.valueOf(cellMark[0][2]);
		lines[1] = String.valueOf(cellMark[1][0]) + "" + String.valueOf(cellMark[1][1]) + ""
				+ String.valueOf(cellMark[1][2]);
		lines[2] = String.valueOf(cellMark[2][0]) + "" + String.valueOf(cellMark[2][1]) + ""
				+ String.valueOf(cellMark[2][2]);
		lines[3] = String.valueOf(cellMark[0][0]) + "" + String.valueOf(cellMark[1][0]) + ""
				+ String.valueOf(cellMark[2][0]);
		lines[4] = String.valueOf(cellMark[0][1]) + "" + String.valueOf(cellMark[1][1]) + ""
				+ String.valueOf(cellMark[2][1]);
		lines[5] = String.valueOf(cellMark[0][2]) + "" + String.valueOf(cellMark[1][2]) + ""
				+ String.valueOf(cellMark[2][2]);
		lines[6] = String.valueOf(cellMark[0][0]) + "" + String.valueOf(cellMark[1][1]) + ""
				+ String.valueOf(cellMark[2][2]);
		lines[7] = String.valueOf(cellMark[0][2]) + "" + String.valueOf(cellMark[1][1]) + ""
				+ String.valueOf(cellMark[2][0]);
		int whiteSpaceNumber = 0;
		int markNumber = 0;
		int diffNumber = 0;

		for (int i = 0; i < 2 * gameBoard.length + 2; i++) {
			whiteSpaceNumber = searchCharNumber(lines[i], " ");
			markNumber = searchCharNumber(lines[i], mark + "");
			diffNumber = searchCharNumber(lines[i], diff + "");
			if (markNumber == gameBoard.length) {
				value = value + willWin;
			}
			if (diffNumber == gameBoard.length - 1 && markNumber == 1) {
				value = value + voidLose;
			}
			if (markNumber == gameBoard.length - 1 && whiteSpaceNumber == 1) {
				value = value + potientialWin;
			}
			if (markNumber == 1 && whiteSpaceNumber == 1 && diffNumber == 1) {
				value = value + potientialLose;
			}
			if (markNumber == 1 && whiteSpaceNumber == 2) {
				value = value + emptyLine;
			}
		}

		return value;

	}

	private int searchCharNumber(String line, String mark) {
		int counter = 0;
		if (line.indexOf(mark) == -1) {
			return 0;
		} else
			counter++;
		counter = counter + searchCharNumber(line.substring(line.indexOf(mark) + 1), mark);
		return counter;

	}

}
