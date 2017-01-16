/**
 * a single move made by a player
 * @author miya_
 *
 */
public class Move {
	private int row;
	private int column;

	public Move(int row, int col) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.column = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setCol(int col) {
		this.column = col;
	}

}
