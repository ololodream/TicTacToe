
public class Cell {
	private int row;
	private int col;
	private int value;
	
	public Cell() {
		super();
	}

	public Cell(int row, int col, int value) {
		super();
		this.row = row;
		this.col = col;
		this.value = value;
	}

	public int getRowNumber() {
		return row;
	}

	public void setRowNumber(int rowNumber) {
		this.row = rowNumber;
	}

	public int getColNumber() {
		return col;
	}

	public void setColNumber(int colNumber) {
		this.col = colNumber;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	
}
